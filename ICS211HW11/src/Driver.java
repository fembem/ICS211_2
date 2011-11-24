/* a class to simulate a job queue process.
 * @author  Dec, Leo
 * @assignment  11
 * @date  November 20, 2011
 * 
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * The Class Driver.
 * @author  Dec, Leo
 */
public class Driver {

  
  /**
   * The Class Process.  Conveneince tuple class.
   * @author leo deC, Leo
   */
  static class Process implements Comparable<Process>{
    
    /** The name of the process. */
    String name;
    
    /** The deadline of the process. */
    long deadline;
    
    /** The time the process takes to complete. */
    long duration;
    
    /* 
     * @param other the process we are comparing to
     * @return -1 if this<other, 0 if this == other, 1 if this > other
     */
    @Override
    public int compareTo(Process other) {
      return new Long(deadline - duration).compareTo(new Long(other.deadline - other.duration));
    }

    /**
     * Instantiates a new process.
     *
     * @param name the name of the process
     * @param deadline the process's deadline
     * @param duration the process's duration
     */
    public Process(String name, long deadline, long duration) {
      super();
      this.name = name;
      this.deadline = deadline;
      this.duration = duration;
    }
    
  }
  
  /** The priority queue that will hold all unfinished processes. */
  PriorityQueue<Process> queue = new PriorityQueue<Process>();
  
  /** The simulation time. */
  long simulationTime = 0;
  
  /**
   * Parses one input line.
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
   * @param args the arguments to the program
   */
  public static void main(String[] args) {

    Driver driver = new Driver();
    String fileName = "src/hw11test.txt";
    
    if (args.length > 0) {
      fileName = args[0];
    }
    
    System.out.println("using file: " + fileName);
    System.out.println("If you wish to use another file, enter its name as a program argument.");
    System.out.println("i.e. java Driver <filename>\n\n");
    
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

  /**
   * Run run command.
   *
   * @param advanceToTime satisfy requests until simulation time reaches this time
   */
  private void runRunCommand(long advanceToTime) {
    while (advanceToTime > simulationTime) {
      
      long timeLeftInThisCycle = advanceToTime - simulationTime;
      
      Process process = queue.poll();
      if (process == null) {  //nothing left in the queue
        //if there is any time left over advance the clock to advanceToTime
        simulationTime = advanceToTime;
        return;
      }
      
      System.out.println(simulationTime + ": busy with " + process.name + " with deadline " +
      		process.deadline + " and duration " + process.duration);
      
      long processDuration = process.duration;
      
      if (processDuration <= timeLeftInThisCycle) {
        simulationTime += processDuration;
        String late = simulationTime > process.deadline ? " (late)" : "";
        System.out.println(simulationTime + ": done with " + process.name + late);
      }
      else {
        long processCompleteTime = processDuration - timeLeftInThisCycle;
        simulationTime += timeLeftInThisCycle;
        Process newProcess = new Process(process.name, process.deadline, processCompleteTime);
        queue.add(newProcess);
        System.out.println(simulationTime + ": adding " + newProcess.name +
            " with deadline " + newProcess.deadline + " and duration " + newProcess.duration);
      }
    }

  }

  /**
   * Run schedule command.
   *
   * @param name the process name
   * @param deadline the process deadline
   * @param duration the process duration
   */
  private void runScheduleCommand(String name, long deadline, long duration) {
    Process process = new Process(name, deadline, duration);
    queue.add(process);
    System.out.println(simulationTime + ": adding " + process.name +
        " with deadline " + process.deadline + " and duration " + process.duration);
  }

}
