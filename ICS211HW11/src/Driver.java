import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * The Class Driver.
 */
public class Driver {

  static class Process implements Comparable<Process>{
    String name;
    long deadline;
    long duration;
    
    @Override
    public int compareTo(Process other) {
      return new Long(deadline - duration).compareTo(new Long(other.deadline - other.duration));
    }

    public Process(String name, long deadline, long duration) {
      super();
      this.name = name;
      this.deadline = deadline;
      this.duration = duration;
    }
    
  }
  
  PriorityQueue<Process> queue = new PriorityQueue<Process>();
  
  long simulationTime = 0;
  
  /**
   * Parses the input line.
   * 
   * @param inputLine the last input line
   * @return all the tokens parsed from the input line
   */
  private static String[] parseInputLine(String inputLine) {
    String delims = "[ ]+";
    return inputLine.split(delims);
  }

  /**
   * The main method.
   * 
   * @param args the arguments
   */
  public static void main(String[] args) {

    Driver driver = new Driver();
    String fileName = "src/hw11test.txt";
    
    if (args.length > 0) {
      fileName = args[0];
    }
    
    System.out.println("using file: " + fileName);
    
    try {
      BufferedReader in = new BufferedReader(new FileReader(fileName));
      String str;
      while ((str = in.readLine()) != null) {
        String[] tokens = parseInputLine(str);
        if (tokens.length == 0)
          continue;
        String command = tokens[0];
        if (command.equals("run")) {
          if (tokens.length != 2)
            continue;
          String timeString = tokens[1];
          long time = -1;
          try {
            time = Long.parseLong(timeString);
          }
          catch (NumberFormatException nfe) {
            System.err.println("could not parse time " + timeString);
          }

          driver.runRunCommand(time);

        }
        else if (command.equals("schedule")) {
          if (tokens.length != 4)
            continue;
          String process = tokens[1];
          String deadlineString = tokens[2];
          String durationString = tokens[3];
          long deadline = -1;
          long duration = -1;

          try {
            deadline = Long.parseLong(deadlineString);
          }
          catch (NumberFormatException nfe) {
            System.err.println("could not parse deadline " + deadlineString);
          }

          try {
            duration = Long.parseLong(durationString);
          }
          catch (NumberFormatException nfe) {
            System.err.println("could not parse duration " + durationString);
          }

          driver.runScheduleCommand(process, deadline, duration);

        }
      }
      in.close();
    }
    catch (FileNotFoundException fnfe) {
      System.err.println("can't open file: " + fileName);
    }
    catch (IOException ioe) {
      System.err.println("problem with file: " + ioe);
    }

  }

  private void runRunCommand(long advanceToTime) {
    while (advanceToTime > simulationTime) {
      
      long timeLeftInThisCycle = advanceToTime - simulationTime;
      
      Process process = queue.poll();
      if (process == null) return;
      
      System.out.println(simulationTime + ": busy with " + process.name + " with deadline " +
      		process.deadline + " and duration " + process.duration);
      
      long processDuration = process.duration;
      
      if (processDuration <= timeLeftInThisCycle) {
        simulationTime += processDuration;
        String late = simulationTime > process.deadline ? " (late)" : "";
        System.out.println(simulationTime + ": done with process " + process.name + late);
      }
      else {
        long processCompleteTime = processDuration - timeLeftInThisCycle;
        simulationTime += timeLeftInThisCycle;
        Process newProcess = new Process(process.name, process.deadline, processCompleteTime);
        queue.add(newProcess);
        System.out.println(simulationTime + ": adding process " + newProcess.name +
            " with deadline " + newProcess.deadline + " and duration " + newProcess.duration);
      }
    }

  }

  private void runScheduleCommand(String name, long deadline, long duration) {
    Process process = new Process(name, deadline, duration);
    queue.add(process);
    System.out.println(simulationTime + ": adding process " + process.name +
        " with deadline " + process.deadline + " and duration " + process.duration);
  }

}
