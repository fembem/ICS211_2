import java.util.Scanner;
import java.util.Set;
/**
 * Allows interactive manipulation of a telephone directory.
 * @author deC, Leo
 * @assignment ICS 211 Assignment 12
 * @date November 24, 2011
 * @bugs None
 */
public class TelephoneDirectory {

  /** The hash table to story the directory contents. */
  static SimpleHashTable<String, String> table = new SimpleHashTable<String, String>();

  /**
   * The main method.
   *
   * @param args no arguments expected
   */
  public static void main(String[] args) {

    Scanner scan = new Scanner(System.in);
    while (true) {
      System.out.println("Enter 'find', 'new', 'delete', 'all', or 'quit'");
      String input = scan.next();
      if ("quit".equals(input)) {
        break;
      }
      else if ("find".equals(input)) {
        System.out.println("Enter name to find:");
        String name = scan.next();
        String number = table.get(name);
        if(number != null)
          System.out.println("the number for " + name + " is " + number);
        else
          System.out.println("'" + name + "' is not in the database.");
      }
      else if ("new".equals(input)) {
        System.out.println("Enter name for new entry:");
        String name = scan.next();
        System.out.println("Enter number for new entry:");
        String number = scan.next();
        table.put(name, number);
        System.out.println("entry for (" + name + ", " + number + ") inserted in database.");
      }
      else if ("delete".equals(input)) {
        System.out.println("Enter name to delete:");
        String name = scan.next();
        String number = table.remove(name);
        if (number != null)
          System.out.println("removed entry for ('" + name + "', " +
              number + ") delete from databasase.");
        else
          System.out.println("no entry for '" + name + "' found in database.");
      }
      else if ("all".equals(input)) {
        Set<SimpleHashTable.Entry<String, String>> entries = table.entrySet();
        System.out.println("Printing entire directory....");
        for (SimpleHashTable.Entry<String, String> entry: entries) {
          System.out.println("('" + entry.getKey() + ", " + entry.getValue() + ")");
        }
      }
      
    }

  }

}
