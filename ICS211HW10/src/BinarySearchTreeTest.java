import static org.junit.Assert.*;
import java.util.NoSuchElementException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class BinarySearchTreeTest {

  BinarySearchTree<Record> tree;
  
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
  }

  @Before
  public void setUp() throws Exception {
    tree = new BinarySearchTree<Record>();
    tree.add(new Record("m"));
    tree.add(new Record("g"));
    tree.add(new Record("j"));
    tree.add(new Record("d"));
    tree.add(new Record("k"));
    tree.add(new Record("i"));
    
  }

  @Test
  public void testFindNextOnNoTargetRecord() {
    Record next = tree.findNext(new Record("z"));
    assertNull(next);
  }
  
  @Test
  public void testFindNext() {
    Record next = tree.findNext(new Record("g"));
    assertEquals(new Record("i").key, next.key);
  }

  @Test(expected=NoSuchElementException.class)
  public void testFindNonExistingPrevious() {
    tree.findPrevious(new Record("d"));
  }
  
  @Test(expected=NoSuchElementException.class)
  public void testFindNextOnRoot() {
    Record next = tree.findNext(new Record("m"));
    System.out.println(next);
  }

}
