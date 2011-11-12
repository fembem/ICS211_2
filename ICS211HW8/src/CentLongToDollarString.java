/**
 * Computes a string dollar/cents amount from a long integer.
 * @author Leo deC
 * @assignment 8
 * @date October 23, 2011
 * @bugs none
 */
public class CentLongToDollarString {

  /**
   * Prints the dollars/cents amount recursively.
   * Digits are computed from right to left but printed
   * left to right by having print statement precede recursive call.
   *
   * @param fragment the fragment to the left remaining to be processed
   * @param index the index of the digit produced, counting from the right
   */
  private static void printDollars(long fragment, int index) {
    
    //print the decimal if there are less than 3 digits
    if (index == 0 && fragment < 100) System.out.print(".");
    //print an extra 0 if there are less than 2 digits
    if (index == 0 && fragment < 10) System.out.print("0");
    
    if (fragment < 10) { //base case, single digit fragment(leftmost digit)
      System.out.print(fragment);
    }
    else {
      //produce digits to be printed from right to left
      //but print them in the opposite order by printing each digit
      //after the recursive call to print the remaining digits
      
      // print the digits before (to the left of) this one
      printDollars(fragment / 10, index + 1);
      ///then print this digit
      System.out.print(fragment % 10 + "");
      
    }
    //if this is the 3rd time this function is called it
    //means we are about to print the third digit from the right
    if(index == 2) System.out.print(".");
    
  }
  
  private static void printDollarsAndCents(long cents){
    System.out.print("$");
    printDollars(cents, 0);
  }

  /**
   * The main method.
   *
   * @param args the arguments
   */
  public static void main(String[] args) {
    if(args.length != 1){
      System.out.println("Incorrect usage!");
      System.out.println("correct usage:  java CentLongToDollarString <long>");
      System.exit(0);
    }
    String s = args[0];
    try {
      long cents = Long.parseLong(args[0]);
      if(cents < 0) throw new NumberFormatException();
      printDollarsAndCents(cents);
      System.out.println();
    }
    catch (NumberFormatException e) {
      System.out.println("error: argument '" + s + "' is not a non-negative long");
    }
      
  }
  
}