import java.util.Scanner;
/**
 * Allows interactive testing of queue implementations.
 * @author deC, Leo
 * @assignment ICS 613 Assignment 7
 * @date October 16, 2011
 * @bugs None
 */
public class QueueServicer {

  // toggle the comments to switch queue implementation type
  // static ArrayQueue<String> queue = new ArrayQueue<String>();
  static LinkedListQueue<String> queue = new LinkedListQueue<String>();

  public static void main(String[] args) {

    Scanner scan = new Scanner(System.in);
    while (true) {
      System.out.println("Enter 'poll', 'null', 'quit', or a string to insert");
      String input = scan.next();
      if ("quit".equals(input)) {
        break;
      }
      else if ("poll".equals(input)) {
        String result = queue.poll();
        System.out.println("poll() returned: " + result);
      }
      else if ("null".equals(input)) {
        boolean result = queue.offer(null);
        System.out.println("offer(null) returned: " + result);
      }
      else {
        boolean result = queue.offer(input);
        System.out.println("offer(\"" + input + "\") returned: " + result);
      }
    }

  }

}
