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

  //d
  @Test(expected=NoSuchElementException.class)
  public void testFindNonExistingPrevious() {
    tree.findPrevious(new Record("d"));
  }
  
  //d
  @Test
  public void testFindNextAsParent() {
    Record next = tree.findNext(new Record("d"));
    assertEquals(new Record("g").key, next.key);
  }
  
  //g
  @Test
  public void testFindNext() {
    Record next = tree.findNext(new Record("g"));
    assertEquals(new Record("i").key, next.key);
  }
  
  //g
  @Test
  public void testFindPreviousAsLeftChild() {
    Record next = tree.findPrevious(new Record("g"));
    assertEquals(new Record("d").key, next.key);
  }
  
  //i
  @Test
  public void testFindNextAsParentI() {
    Record next = tree.findNext(new Record("i"));
    assertEquals(new Record("j").key, next.key);
  }
  
  //i
  @Test
  public void testFindPreviousAsAncestor() {
    Record next = tree.findPrevious(new Record("i"));
    assertEquals(new Record("g").key, next.key);
  }
  
  //j
  @Test
  public void testFindNextAsRightChild() {
    Record next = tree.findNext(new Record("j"));
    assertEquals(new Record("k").key, next.key);
  }
  
  //j
  @Test
  public void testFindPreviousAsLeftChildJ() {
    Record next = tree.findPrevious(new Record("j"));
    assertEquals(new Record("i").key, next.key);
  }
  
  //k
  @Test
  public void testFindNextThatIsADistantAncestor() {
    Record next = tree.findNext(new Record("k"));
    assertEquals(new Record("m").key, next.key);
  }
  
  //k
  @Test
  public void testFindPreviousOnRightChildWithNoChildren() {
    Record next = tree.findPrevious(new Record("k"));
    assertEquals(new Record("j").key, next.key);
  }
  
  //m
  @Test(expected=NoSuchElementException.class)
  public void testFindNextOnRoot() {
    Record next = tree.findNext(new Record("m"));
    System.out.println(next);
  }
  
  //m
  @Test
  public void testFindPreviousOnRoot() {
    Record next = tree.findPrevious(new Record("m"));
    assertEquals(new Record("k").key, next.key);
  }
  
  //z
  @Test
  public void testFindNextOnNoTargetRecord() {
    Record next = tree.findNext(new Record("z"));
    assertNull(next);
  }

}
