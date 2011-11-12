/**
 * Implements a queue of unlimited size with a linked list.
 * @author deC, Leo
 * @assignment ICS 613 Assignment 7
 * @date October 16, 2011
 * @bugs None
 * @param <E> the element type
 */
public class LinkedListQueue<E> {

  /** The head of the queue. */
  private LinkedNode<E> head;
  
  /** The tail of the queue. */
  private LinkedNode<E> tail;

  // returns true if the insertion succeeds
  /**
   * Offer.
   *
   * @param value the value to be inserted
   * @return true, is always successfully inserted
   */
  public boolean offer(E value) {

    //reject null insertion value
    if (value == null) {
      return false;
    }

    //reject duplicates
    for (LinkedNode<E> node = head; node != null; node = node.next) {
      if (node.item.equals(value)) {
        return false;
      }
    }

    if (head == null) { //queue was empty; update head and tail
      head = new LinkedNode<E>(value, null);
      tail = head;
      return true;
    }
    else {  //insert node at tail and update tail
      tail.next = new LinkedNode<E>(value, null);
      tail = tail.next;
      return true;
    }

  }

  // returns the first object, or null if there are none
  /**
   * Poll.
   *
   * @return the element at the tail of the queue, if there is one
   */
  public E poll() {
    if (head == null) { //empty queue, return null
      return null;
    }
    else {  //queue is not empty
      E result = head.item;
      if (head == tail) { //if this was the last element, set tail to null
                          //or it will point to a dnagling element
        tail = null;
      }
      head = head.next;
      return result;
    }
  }
}
