import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class LinkedListQueueTest {

  ArrayQueue<String> queue;

  @Before
  public void setUp() {

    queue = new ArrayQueue<String>();
  }

  @Test
  public void testOffer() {
    assertTrue(queue.offer("a"));
  }

  @Test
  public void testPoll() {
    assertNull("poll empty queue", queue.poll());
    queue.offer("a");
    assertEquals("a", queue.poll());
  }
  
  @Test
  public void testRejectsNullElement() {
    assertFalse(queue.offer(null));
  }
  
  @Test
  public void testRejectsDuplicates() {
    assertTrue(queue.offer("a"));
    assertFalse(queue.offer("a"));
    assertEquals("a", queue.poll());
    assertTrue(queue.offer("a"));
  }

  @Test
  public void testOfferingAndPolling() {
    assertTrue(queue.offer("1"));
    // 1
    assertTrue(queue.offer("2"));
    // 1,2
    assertEquals("1", queue.poll());
    // 2
    assertTrue(queue.offer("3"));
    assertTrue(queue.offer("4"));
    assertTrue(queue.offer("5"));
    // 2,3,4,5
    assertEquals("2", queue.poll());
    assertEquals("3", queue.poll());
    // 4, 5
    assertTrue(queue.offer("6"));
    assertTrue(queue.offer("7"));
    assertTrue(queue.offer("8"));
    assertTrue(queue.offer("9"));
    assertTrue(queue.offer("10"));
    assertTrue(queue.offer("11"));
    assertTrue(queue.offer("12"));
    // 4, 5, 6, 7, 8, 9, 10, 11, 12
    assertEquals("4", queue.poll());
    assertEquals("5", queue.poll());
    assertEquals("6", queue.poll());
    assertEquals("7", queue.poll());
    assertEquals("8", queue.poll());
    assertEquals("9", queue.poll());
    assertEquals("10", queue.poll());
    assertEquals("11", queue.poll());
    assertEquals("12", queue.poll());
    // empty queue
    assertNull("poll empty queue", queue.poll());
  }

}
