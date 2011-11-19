import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * The Class Driver.
 */
public class Driver {

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

    try {
      BufferedReader in = new BufferedReader(new FileReader("input.txt"));
      String str;
      while ((str = in.readLine()) != null) {
        String[] tokens = parseInputLine(str);
        if (tokens.length == 0)
          continue;
        String command = tokens[0];
        if (command.equals("run")) {
          if (tokens.length != 2)
            continue;
          String timeString = tokens[3];
          long time = -1;
          try {
            time = Long.parseLong(timeString);
          }
          catch (NumberFormatException nfe) {
            System.err.println("could not parse time " + timeString);
          }

          runRunCommand(time);

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

          runScheduleCommand(process, deadline, duration);

        }
      }
      in.close();
    }
    catch (IOException ioe) {
      System.err.println("problem with file: " + ioe);
    }

  }

  private static void runRunCommand(long time) {
    // TODO Auto-generated method stub

  }

  private static void runScheduleCommand(String process, long deadline, long duration) {
    // TODO Auto-generated method stub

  }

}
