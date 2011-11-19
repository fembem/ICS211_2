import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class MinHeapTest {

  int size = 35;
  MinHeap<Integer> integerMinHeap;
  
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
  }

  @Before
  public void setUp() throws Exception {
    
    integerMinHeap = new MinHeap<Integer>();
    
  }
  
  @Test
  public void testInsertOneElement(){
    integerMinHeap.add(1);
    assertEquals(1, integerMinHeap.data[0]);
  }
  
  @Test
  public void testInsertThreeElements(){
    integerMinHeap.add(5);
    assertEquals(5, integerMinHeap.data[0]);
    integerMinHeap.add(3);
    assertEquals(3, integerMinHeap.data[0]);
    assertEquals(5, integerMinHeap.data[1]);
    integerMinHeap.add(2);
    assertEquals(2, integerMinHeap.data[0]);
    assertEquals(5, integerMinHeap.data[1]);
    assertEquals(3, integerMinHeap.data[2]);
  }
  
  @Test
  public void testAddAndRemovePostConditions(){
    integerMinHeap.add(5);
    assertTrue( integerMinHeap.containsElement(5) );
    assertTrue( integerMinHeap.containsAllElements(Arrays.asList(new Integer[]{5})) );
    assertEquals(1,  integerMinHeap.getSize() );
    assertEquals(5, integerMinHeap.data[0]);
    
    integerMinHeap.add(10);
    assertTrue( integerMinHeap.containsElement(5) );
    assertTrue( integerMinHeap.containsElement(10) );
    assertTrue( integerMinHeap.containsAllElements(Arrays.asList(new Integer[]{5,10})) );
    assertEquals(2, integerMinHeap.getSize() );
    assertEquals(5, integerMinHeap.data[0]);
    assertEquals(10, integerMinHeap.data[1]);
    
    integerMinHeap.add(7);
    assertTrue( integerMinHeap.containsAllElements(Arrays.asList(new Integer[]{5,10,7})) );
    assertEquals(3, integerMinHeap.getSize() );
    assertEquals(5, integerMinHeap.data[0]);
    assertEquals(10, integerMinHeap.data[1]);
    assertEquals(7, integerMinHeap.data[2]);
    
    integerMinHeap.add(3);
    assertTrue( integerMinHeap.containsAllElements(Arrays.asList(new Integer[]{5,10,7,3})) );
    assertEquals(4, integerMinHeap.getSize() );
    assertEquals(3, integerMinHeap.data[0]);
    assertEquals(5, integerMinHeap.data[1]);
    assertEquals(7, integerMinHeap.data[2]);
    assertEquals(10, integerMinHeap.data[3]);
    
    integerMinHeap.add(15);
    assertTrue( integerMinHeap.containsAllElements(Arrays.asList(new Integer[]{5,10,7,3,15})) );
    assertEquals(5, integerMinHeap.getSize() );
    assertEquals(3, integerMinHeap.data[0]);
    assertEquals(5, integerMinHeap.data[1]);
    assertEquals(7, integerMinHeap.data[2]);
    assertEquals(10, integerMinHeap.data[3]);
    assertEquals(15, integerMinHeap.data[4]);
    
    integerMinHeap.add(4);
    assertTrue( integerMinHeap.containsAllElements(Arrays.asList(new Integer[]{5,10,7,3,15,4})) );
    assertEquals(6, integerMinHeap.getSize() );
    assertEquals(3, integerMinHeap.data[0]);
    assertEquals(5, integerMinHeap.data[1]);
    assertEquals(4, integerMinHeap.data[2]);
    assertEquals(10, integerMinHeap.data[3]);
    assertEquals(15, integerMinHeap.data[4]);
    assertEquals(7, integerMinHeap.data[5]);
    
    //remove the 3
    assertEquals(new Integer(3), integerMinHeap.remove());
    assertTrue( integerMinHeap.containsAllElements(Arrays.asList(new Integer[]{5,10,7,15,4})) );
    assertEquals(5, integerMinHeap.getSize() );
    assertEquals(4, integerMinHeap.data[0]);
    assertEquals(5, integerMinHeap.data[1]);
    assertEquals(7, integerMinHeap.data[2]);
    assertEquals(10, integerMinHeap.data[3]);
    assertEquals(15, integerMinHeap.data[4]);
    
  //remove the 4
    assertEquals(new Integer(4), integerMinHeap.remove());
    assertTrue( integerMinHeap.containsAllElements(Arrays.asList(new Integer[]{5,10,7,15})) );
    assertEquals(4, integerMinHeap.getSize() );
    
    //remove the 5
    assertEquals(new Integer(5), integerMinHeap.remove());
    assertTrue( integerMinHeap.containsAllElements(Arrays.asList(new Integer[]{10,7,15})) );
    assertEquals(3, integerMinHeap.getSize() );
    
    //remove the 7
    assertEquals(new Integer(7), integerMinHeap.remove());
    assertTrue( integerMinHeap.containsAllElements(Arrays.asList(new Integer[]{10,15})) );
    assertEquals(2, integerMinHeap.getSize() );
    
    //remove the 10
    assertEquals(new Integer(10), integerMinHeap.remove());
    assertTrue( integerMinHeap.containsAllElements(Arrays.asList(new Integer[]{15})) );
    assertEquals(1, integerMinHeap.getSize() );
    
    //remove the 15
    assertEquals(new Integer(15), integerMinHeap.remove());
    assertTrue( integerMinHeap.containsAllElements(Arrays.asList(new Integer[]{})) );
    assertEquals(0, integerMinHeap.getSize() );
  }
  
  @Test
  public void testSortedPropertyRemoval(){
    List<Integer> integerList = new ArrayList<Integer>();
    // put Integers 0..(N-1) into the tree
    for (int j = 0; j < size; j++) {
      integerList.add(j);
      
    }
    Collections.shuffle(integerList);

    for (int j = 0; j < size; j++) {
      integerMinHeap.add(integerList.get(j));
      //System.out.print(integerList.get(j) + ",");
    }
    //System.out.println("");
    for (int j = 0; j < size; j++) {
      int result = integerMinHeap.remove();
      assert result == j;
      //System.out.print(result + ",");
    }
    //System.out.println("");
  }

  @Test
  public void testAdd() {
    integerMinHeap.add(1);
  }

  @Test(expected = NoSuchElementException.class)
  public void testRemove() {
    integerMinHeap.remove();
  }

}
