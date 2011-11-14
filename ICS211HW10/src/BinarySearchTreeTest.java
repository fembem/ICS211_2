import static org.junit.Assert.*;
import java.util.NoSuchElementException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class BinarySearchTreeTest {

  BinarySearchTree<Record> tree;
  int N = 8;
  
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
  public void testOnTreesOfSizeUpToN() {
    //generate trees of all size from 1 to N
    for (int size = 1; size <= N; size++) {
      Integer[] integerArray = new Integer[size];
        //put Integers 0..(N-1) into the tree
        for (int j = 0; j < size; j++) {
          integerArray[j] = j;
        }
        //generate all the permutations of the integers [0..(N-1)]
        Permute permutations = new Permute(integerArray);
        //for each permutation add elements to the tree in that order
        while (permutations.hasNext()) {
          
          //provide some feedback on progress
          System.out.println("working on array :" + permutations);
          
          //put the Integers into the tree in the order given by the current permutation
          BinarySearchTree<Integer> integerTree = new BinarySearchTree<Integer>();
          for (int i = 0; i < size; i++) {
            integerTree.add(i);
          }
          
          //there is no (size) element in the tree
          assertNull(integerTree.findNext(size));
          assertNull(integerTree.findPrevious(size));
          
          //for integers between 1 and (size-2) there is both a previous
          //and a next in the tree
          for (int i = 1; i < size - 1 ; i++) {
            Integer next = integerTree.findNext(i);
            assertEquals(new Integer(i+1), next);
            Integer previous = integerTree.findPrevious(i);
            assertEquals(new Integer(i-1), previous);
          }
          //for (size-1) there is no next element in the tree
          try {
            Integer next = integerTree.findNext(size-1);
            System.out.println("next(" + (size-1) + ") = " + next);
            fail();
          } catch (NoSuchElementException nsee) {
            //good!
          }
          //for 0 there is no previous element in the tree
          try {
            Integer next = integerTree.findPrevious(0);
            fail();
          } catch (NoSuchElementException nsee) {
            //good!
          }
        }
    }
  }
  
  //test on tree with only one node
  @Test(expected=NoSuchElementException.class)
  public void testFindNextOnOneNodeTree() {
    tree = new BinarySearchTree<Record>();
    tree.add(new Record("a"));
    Record next = tree.findNext(new Record("a"));
  }
  
  //test on tree with two nodes
  @Test(expected=NoSuchElementException.class)
  public void testFindNextOnTwoNodeTreeNoSuchElement() {
    tree = new BinarySearchTree<Record>();
    tree.add(new Record("a"));
    tree.add(new Record("g"));
    Record next = tree.findNext(new Record("g"));
  }  
  
  //test on tree with two nodes
  @Test
  public void testFindPreviousOnTwoNodeTree() {
    tree = new BinarySearchTree<Record>();
    tree.add(new Record("a"));
    tree.add(new Record("g"));
    Record next = tree.findPrevious(new Record("g"));
    assertEquals(new Record("a").key, next.key);
  }  
  
  //test on tree with two nodes
  @Test
  public void testFindNextOnTwoNodeTree() {
    tree = new BinarySearchTree<Record>();
    tree.add(new Record("a"));
    tree.add(new Record("g"));
    Record next = tree.findNext(new Record("a"));
    assertEquals(new Record("g").key, next.key);
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
