/* a class to provide a priority queue service.
 * @author  Dec, Leo
 * @assignment  11
 * @date  November 20, 2011
 * 
 */

import java.util.NoSuchElementException;

/**
 * The Class PriorityQueue.
 * @author deC, Leo
 */
public class PriorityQueue<E extends Comparable<E>> {

  MinHeap<E> minHeap = new MinHeap<E>();
  
  /**
   * Adds a non null element to the queue.
   *
   * @param e the element of type E to add
   * @return true, if the element was successfully added
   * @throws NullPointerException if the element to be added is null.
   */
  public boolean add(E e) throws NullPointerException {
    
    if (e == null)
      throw new NullPointerException("can't add null to priority queue");
    
    try {
      minHeap.add(e);
      return true;
    } 
    catch (IllegalStateException ise) {
      return false;
    }
  }
  
  /**
   * Poll the queue for the highest priority elemnt.
   *
   * @return the head of this queue, or null if this queue is empty.
   */
  public E poll() {
    try {
      return minHeap.remove();
    }
    catch (NoSuchElementException nsee) {
      return null;
    }
  }
}
