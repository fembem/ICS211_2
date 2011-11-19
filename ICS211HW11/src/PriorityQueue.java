import java.util.NoSuchElementException;


/**
 * The Class PriorityQueue.
 */
public class PriorityQueue<E extends Comparable<E>> {

  MinHeap<E> minHeap = new MinHeap<E>();
  
  /**
   * Adds the.
   *
   * @param e the e
   * @return true, if successful
   * @throws NullPointerException the null pointer exception
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
   * Poll.
   *
   * @return the head of this queue, or null if this queue is empty
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
