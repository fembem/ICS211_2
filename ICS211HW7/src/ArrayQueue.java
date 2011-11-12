/**
 * Implements a queue of maximum size 10 with an array.
 * @author deC, Leo
 * @assignment ICS 613 Assignment 7
 * @date October 16, 2011
 * @bugs None
 * @param <E> the element type
 */
public class ArrayQueue<E> {

  /** The size of the array. */
  private static final int SIZE = 10;
  
  /** The storage array. */
  private Object[] array = new Object[SIZE];
  
  /** The index of the first element in the array. */
  private int first = 0;
  
  /** The number of elements currently in the array. */
  private int numElements = 0;

  // returns true if the insertion succeeds
  /**
   * Offer.
   *
   * @param value the value to be inserted
   * @return true, if successfully inserted
   */
  public boolean offer(E value) {

    //reject null
    if (value == null) {
      return false;
    }

    //reject duplicates
    for (int i = 0; i < numElements; i++) {
      int index = (first + i) % SIZE;
      if (array[index].equals(value)) {
        return false;
      }
    }

    //reject if full
    if (numElements == SIZE) {
      return false;
    }
    else {  //insert element
      int index = (first + numElements) % SIZE;
      array[index] = value;
      numElements++;
      return true;
    }

  }

  // returns the first object, or null if there are none
  /**
   * Poll the queue for its head value.
   *
   * @return the e
   */
  public E poll() {
    
    if (numElements == 0) { //empty queue
      return null;
    }
    else {  //return the head and update the head pointer
      E result = (E) array[first];
      first = (first + 1) % SIZE;
      numElements--;
      return result;
    }

  }

}
