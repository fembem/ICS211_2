/**
 * A program to compute Ackermann numbers.
 *
 * @author Leo deC
 * @assignment 8
 * @date October 23, 2011
 * @bugs none
 */
public class Ackermann {

  /**
   * The main method.
   *
   * @param arguments the arguments
   */
  public static void main(String[] arguments) {
    int[] aAndB = new int[2];
    for (int i : new int[] { 0, 1 }) {
      try {
        aAndB[i] = Integer.parseInt(arguments[i]);
      }
      catch (java.lang.NumberFormatException e) {
        System.out.println("error: argument '" + (i + 1) + "' is not an integer");
      }
    }
    int result = ack(aAndB[0], aAndB[1]);
    System.out.println("result: " + result);
  }

  /**
   * Recursively compute the Ackermann value.
   *
   * @param a the 1st parameter
   * @param b the 2nd parameter
   * @return the result value
   */
  private static int ack(int a, int b) {
    /*
    ack(a, b) = b + 1 if a == 0                           (1)
    ack(a, b) = ack(a - 1, 1) if a > 0 and b == 0         (2)
    ack(a, b) = ack(a - 1, ack(a, b - 1)) otherwise       (3)
    */
    if (a == 0) { // (1)
      return b + 1;
    }
    else if (a > 0 && b == 0) { // (2)
      return ack(a - 1, 1);
    }
    else {
      return ack(a - 1, ack(a, b - 1)); // (3)
    }
  }
}