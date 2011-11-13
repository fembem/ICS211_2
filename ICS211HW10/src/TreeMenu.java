/* maintain a database of string keys and an integer value for each
 * @author  Biagioni, Edoardo
 * @assignment  10
 * @date  April 5, 2011
 * @bugs  no error message if you remove a record not in the database
 * @inspiration William Albritton's Menu.java,
 * http://www2.hawaii.edu/~walbritt/ics211/treeBinarySearch/Menu.java
 * @inspiration ShoppingMenu.java
 * http://www2.ics.hawaii.edu/~esb/2008spring.ics211/ShoppingMenu.java.html
 * @inspiration IRSMenu.java
 * http://www2.ics.hawaii.edu/~esb/2008spring.ics211/IRSMenu.java.html
 */

import javax.swing.*;
import java.util.Scanner;
import java.util.Iterator;
import java.util.regex.*;
import java.io.File;
import java.io.FileWriter;

public class TreeMenu {
  final static String databaseName = "database.csv";

  /* do nothing, data fields are already initialized */
  public TreeMenu() {
  }

  /*
   * main method is invoked when the program is started
   * 
   * @param arguments if a single command-line argument is given, it is the name of the file
   * containing the database
   */
  public static void main(String[] arguments) {
    String thisDatabaseName = databaseName;
    if (arguments.length == 1) {
      thisDatabaseName = arguments[0];
    }
    BinarySearchTree<Record> database = null;
    database = readFromDatabase(thisDatabaseName);
    while (executeMenu(database)) {
      // after every non-exit command, save to the database
      saveToDatabase(database, thisDatabaseName);
    }
  }

  /*
   * @param database reference to a database
   * 
   * @return whether to continue execution
   */
  private static boolean executeMenu(BinarySearchTree<Record> db) {
    String[] choices =
        { "add a new record", "change value for a record", "remove a record",
            "display the database", "exit program" };
    // ask the user to tell us what they want to do
    int choice =
        JOptionPane.showOptionDialog(null, "please enter", "taxpayers", JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE, null, choices, -1);
    switch (choice) {
    case 0:
      addNewRecord(db);
      break;
    case 1:
      changeValue(db);
      break;
    case 2:
      removeRecord(db);
      break;
    case 3:
      System.out.println(db.toString());
      break;
    // case 4: handled below
    }

    // whatever we did is done, save the database and return
    return (choice != (choices.length - 1));
  }

  /*
   * read a database from a file
   * 
   * @return database read from a file, or throws exception
   */
  private static BinarySearchTree<Record> readFromDatabase(String file) {
    BinarySearchTree<Record> db = new BinarySearchTree<Record>();
    int readSuccessfully = 0;
    try {
      // database is assumed to be a sequence of lines, one item per line
      File dbFile = new File(file);
      Scanner dbRead = new Scanner(dbFile);
      while (dbRead.hasNextLine()) {
        String line = dbRead.nextLine();
        Scanner lineScan = new Scanner(line);
        // the record is comma-separated
        lineScan.useDelimiter(",");
        // each line should have the key string, followed
        // by the integer value
        // e.g. "Foo Bar,123456
        String key = lineScan.next();
        // read the value
        int value = lineScan.nextInt();
        db.add(new Record(key, value));
        readSuccessfully++;
      }
    }
    catch (Exception e) {
      if (readSuccessfully > 0) {
        System.out.println("error " + e + " reading database after reading " + readSuccessfully
            + " entries");
      }
      else {
        System.out.println("error " + e + " reading database " + file
            + "\nstarting with empty database");
      }
    }
    return db;
  }

  /*
   * @param database to save to the file
   */
  private static void saveToDatabase(BinarySearchTree<Record> db, String name) {
    try {
      // preorder traversal, so the same tree is built again
      // when we read it back
      Iterator<Record> it = db.preIterator();
      FileWriter out = new FileWriter(name);
      while (it.hasNext()) {
        Record record = it.next();
        out.write(record.key + "," + record.value + "\n");
      }
      out.close();
    }
    catch (Exception e) {
      /* this is an error. Notify the user */
      String message = "unable to save to file " + name;
      JOptionPane.showMessageDialog(null, message);
    }
  }

  /*
   * get a record from the user, save it in the database
   * 
   * @param db to which the item is added
   */
  private static void addNewRecord(BinarySearchTree<Record> db) {
    final String prompt1 = "enter key";
    String key = JOptionPane.showInputDialog(null, prompt1);
    if (key != null) { // null name if canceled
      final String prompt2 = "enter value for " + key;
      Integer value = getUserInteger(prompt2);
      if (value != null) { // null string means not a valid number
        db.add(new Record(key, value));
      }
    }
  }

  /*
   * change the value for a record in the database
   * 
   * @param db in which the record is changed
   */
  private static void changeValue(BinarySearchTree<Record> db) {
    final String prompt1 = "enter key";
    String string = getUserString(prompt1);
    if (string != null) { // null string means not a valid number
      // build a key, that is, a record where only the key is valid
      Record key = new Record(string);
      Record record = db.get(key);
      if (record == null) { // not in database, cannot change value
        JOptionPane.showMessageDialog(null, "key " + key + " not found in database");
      }
      else { // is in database, get the new value
        final String prompt2 = "enter new value for " + string;
        record.value = getUserInteger(prompt2);
        // this addition should replace the original record
        db.add(record);
      }
    }
  }

  /*
   * remove a record from the database given the key
   * 
   * @param db from which the record is removed
   */
  private static void removeRecord(BinarySearchTree<Record> db) {
    final String prompt = "enter key of record to remove from database";
    String string = getUserString(prompt);
    if (string != null) { // null string means not a valid number
      // build a key, that is, a record where only the SSN is valid
      Record key = new Record(string);
      db.remove(key);
    }
  }

  /*
   * @param prompt for the user
   * 
   * @return null, or the string the user entered
   */
  private static String getUserString(String prompt) {
    String userChoice = JOptionPane.showInputDialog(null, prompt);
    if (userChoice == null) { // cancel returns a null string
      return null;
    }
    return userChoice;
  }

  /*
   * @param prompt for the user
   * 
   * @return null, or the integer the user entered
   */
  private static Integer getUserInteger(String prompt) {
    String userChoice = JOptionPane.showInputDialog(null, prompt);
    if (userChoice == null) { // cancel returns a null string
      return null;
    }
    Scanner scan = new Scanner(userChoice);
    if (!scan.hasNextInt()) { // might not be an int
      JOptionPane.showMessageDialog(null, "did not enter a number");
      return null;
    }
    Integer choice = scan.nextInt();
    return choice;
  }

  /*
   * @param prompt for the user
   * 
   * @return null, or the double the user entered
   */
  private static Double getUserDouble(String prompt) {
    String userChoice = JOptionPane.showInputDialog(null, prompt);
    if (userChoice == null) { // cancel returns a null string
      return null;
    }
    Scanner scan = new Scanner(userChoice);
    if (!scan.hasNextDouble()) { // might not be a double
      JOptionPane.showMessageDialog(null, "did not enter a number");
      return null;
    }
    return scan.nextDouble();
  }

}
