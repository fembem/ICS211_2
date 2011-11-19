import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class MinHeapTest {

  int size = 15;
  MinHeap<Integer> integerMinHeap;
  
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
  }

  @Before
  public void setUp() throws Exception {
    
    integerMinHeap = new MinHeap<Integer>();
    
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
      System.out.print(integerList.get(j) + ",");
    }
    System.out.println("");
    for (int j = 0; j < size; j++) {
      int result = integerMinHeap.remove();
      assert result == j;
      System.out.print(result + ",");
    }
    System.out.println("");
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
