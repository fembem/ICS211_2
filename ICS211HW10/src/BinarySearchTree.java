/* a class for binary tree nodes
 * @author  Biagioni, Edoardo
 * @assignment  lecture 17
 * @date  March 12, 2008
 * @inspiration William Albritton's binary search tree class,
 http://www2.hawaii.edu/~walbritt/ics211/treeBinarySearch/BinarySearchTree.java
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class BinarySearchTree<T extends java.lang.Comparable<T>> {

  /**
   * A node in a binary tree
   * 
   * @author Edo Biagioni
   * @lecture ICS 211 Mar 15 or later
   * @date March 14, 2011
   * @bugs private class: include this code within a larger class
   */

  private static class BinaryNode<T> {
    private T item;
    private BinaryNode<T> left;
    private BinaryNode<T> right;

    /**
     * constructor to build a node with no subtrees
     * 
     * @param the value to be stored by this node
     */
    private BinaryNode(T value) {
      item = value;
      left = null;
      right = null;
    }

    /**
     * constructor to build a node with a specified (perhaps null) subtrees
     * 
     * @param the value to be stored by this node
     * @param the left subtree for this node
     * @param the right subtree for this node
     */
    private BinaryNode(T value, BinaryNode<T> l, BinaryNode<T> r) {
      item = value;
      left = l;
      right = r;
    }
  }
  
  private BinaryNode<T> findNode(BinaryNode<T> node, T key, 
      BinaryNode<T> parent, List<BinaryNode<T>> path) {
    if (node == null) {
      return null;
    }
    
    if (parent != null) {
      path.add(parent);
    }
    
    int order = node.item.compareTo(key);
    if (order == 0) {
      return node;
    }
    else if( order < 0) { //node.item < key
      return findNode(node.right, key, node, path);
    }
    else {  //node.item > key
      return findNode(node.left, key, node, path);
    }
  }
  
  private BinaryNode<T> findFirstAncestorWithLeftChild
    (BinaryNode<T> node, List<BinaryNode<T>> path) {
    
    path.add(node);
    for (int i = path.size() - 1; i >=1; i--) {
      //check if the node at i is the left child of its parent node at i-1
      if (path.get(i).equals(path.get(i-1).left)){
        //return the parent
        return path.get(i-1);
      }
    }
    return null;
    
  }
  
  private BinaryNode<T> findFirstAncestorWithRightChild
  (BinaryNode<T> node, List<BinaryNode<T>> path) {
  
  path.add(node);
  for (int i = path.size() - 1; i >=1; i--) {
    //check if the node at i is the left child of its parent node at i-1
    if (path.get(i).equals(path.get(i-1).right)){
      //return the parent
      return path.get(i-1);
    }
  }
  return null;
  
}

  public T findNext(T key) throws java.util.NoSuchElementException {
    
    List<BinaryNode<T>> path = new ArrayList<BinaryNode<T>>();
    BinaryNode<T> targetNode = findNode(root, key, null, path);
    if (targetNode == null) {
      return null;
    }
    if (targetNode.right != null) { //node has a right child, so next node is in right subtree
      targetNode = targetNode.right;  //go to right child
      //now go as far left as we can
      while (targetNode.left != null) {
        targetNode = targetNode.left;
      }
      return targetNode.item;
    } // there is only a next node if the node  or one of its ancestors
    //is the left child of another node
    else  {
      BinaryNode<T> next = findFirstAncestorWithLeftChild(targetNode, path);
      if (next != null) {
        return next.item;
      }
      else {
        throw new java.util.NoSuchElementException();
      }
    }
    
  }
  
  public T findPrevious(T key) throws java.util.NoSuchElementException {

    List<BinaryNode<T>> path = new ArrayList<BinaryNode<T>>();
    BinaryNode<T> targetNode = findNode(root, key, null, path);
    if (targetNode == null) {
      return null;
    }
    if (targetNode.left != null) { //node has a right child, so previous node is in left subtree
      targetNode = targetNode.left;  //go to left child
      //now go as far right as we can
      while (targetNode.right != null) {
        targetNode = targetNode.right;
      }
      return targetNode.item;
    } // there is only a previous node if the node is the right of another node
    else  {
      BinaryNode<T> next = findFirstAncestorWithRightChild(targetNode, path);
      if (next != null) {
        return next.item;
      }
      else {
        throw new java.util.NoSuchElementException();
      }
    }
    
  }
  
  
  /* the root of the tree is the only data field needed */
  protected BinaryNode<T> root = null; // null when tree is empty

  /*
   * constructs an empty tree
   */
  public BinarySearchTree() {
    super();
  }

  /*
   * constructs a tree with one element, as given
   * 
   * @param value to be used for the one element in the tree
   */
  public BinarySearchTree(T value) {
    super();
    root = new BinaryNode<T>(value);
  }

  /*
   * constructs a tree with the given node as root
   * 
   * @param newRoot to be used as the root of the new tree
   */
  public BinarySearchTree(BinaryNode<T> newRoot) {
    super();
    root = newRoot;
  }

  /*
   * find a value in the tree
   * 
   * @param key identifies the node value desired
   * 
   * @return the node value found, or null if not found
   */
  public T get(T key) {
    BinaryNode<T> node = root;
    while (node != null) {
      if (key.compareTo(node.item) == 0) {
        return node.item;
      }
      if (key.compareTo(node.item) < 0) {
        node = node.left;
      }
      else {
        node = node.right;
      }
    }
    return null;
  }

  /*
   * add a value to the tree, replacing an existing value if necessary
   * 
   * @param value to be inserted
   */
  public void add(T value) {
    root = add(value, root);
  }

  /*
   * add a value to the tree, replacing an existing value if necessary
   * 
   * @param value to be inserted
   * 
   * @param node that is the root of the subtree in which to insert
   * 
   * @return the subtree with the node inserted
   */
  protected BinaryNode<T> add(T value, BinaryNode<T> node) {
    if (node == null) {
      return new BinaryNode<T>(value);
    }
    if (value.compareTo(node.item) == 0) {
      // replace the value in this node with a new value
      node.item = value;
      // alternative code creates new node, leaves old node unchanged:
      // return new BinaryNode<T>(value, node.left, node.right);
    }
    else {
      if (value.compareTo(node.item) < 0) { // add to left subtree
        node.left = add(value, node.left);
      }
      else { // add to right subtree
        node.right = add(value, node.right);
      }
    }
    return node;
  }

  /*
   * remove a value from the tree, if it exists
   * 
   * @param key such that value.compareTo(key) == 0 for the node to remove
   */
  public void remove(T key) {
    root = remove(key, root);
  }

  /*
   * remove a value from the tree, if it exists
   * 
   * @param key such that value.compareTo(key) == 0 for the node to remove
   * 
   * @param node the root of the subtree from which to remove the value
   * 
   * @return the new tree with the value removed
   */
  protected BinaryNode<T> remove(T value, BinaryNode<T> node) {
    if (node == null) { // key not in tree
      return null;
    }
    if (value.compareTo(node.item) == 0) { // remove this node
      if (node.left == null) { // replace this node with right child
        return node.right;
      }
      else if (node.right == null) { // replace with left child
        return node.left;
      }
      else {
        // replace the value in this node with the value in the
        // rightmost node of the left subtree
        node.item = getRightmost(node.left);
        // now remove the rightmost node in the left subtree,
        // by calling "remove" recursively
        node.left = remove(node.item, node.left);
        // return node; -- done below
      }
    }
    else { // remove from left or right subtree
      if (value.compareTo(node.item) < 0) {
        // remove from left subtree
        node.left = remove(value, node.left);
      }
      else { // remove from right subtree
        node.right = remove(value, node.right);
      }
    }
    return node;
  }

  protected T getRightmost(BinaryNode<T> node) {
    assert (node != null);
    BinaryNode<T> right = node.right;
    if (right == null) {
      return node.item;
    }
    else {
      return getRightmost(right);
    }
  }

  /* iterator, traverses the tree in order */
  public Iterator<T> iterator() {
    return new TreeIterator(root);
  }

  /* traverses the tree in pre-order */
  public Iterator<T> preIterator() {
    return new TreeIterator(root, true);
  }

  /* traverses the tree in post-order */
  public Iterator<T> postIterator() {
    return new TreeIterator(root, false);
  }

  /*
   * toString
   * 
   * @returns the string representation of the tree.
   */
  public String toString() {
    return toString(root);
  }

  protected String toString(BinaryNode<T> node) {
    if (node == null) {
      return "";
    }
    return node.item.toString() + "(" + toString(node.left) + ", " + toString(node.right) + ")";
  }

  /*
   * unit test
   * 
   * @param arguments, ignored
   */
  public static void main(String[] arguments) {
    BinarySearchTree<Integer> tree = new BinarySearchTree<Integer>();

    tree.add(5);
    tree.add(7);
    tree.add(9);
    tree.add(3);
    tree.add(1);
    tree.add(2);
    tree.add(4);
    tree.add(6);
    tree.add(8);
    tree.add(10);

    System.out.println(tree);

    Iterator<Integer> it = tree.preIterator();
    System.out.print("pre-order: ");
    while (it.hasNext()) {
      System.out.print(it.next() + ", ");
    }
    System.out.println("");

    it = tree.iterator();
    System.out.print("in-order: ");
    while (it.hasNext()) {
      System.out.print(it.next() + ", ");
    }
    System.out.println("");

    it = tree.postIterator();
    System.out.print("post-order: ");
    while (it.hasNext()) {
      System.out.print(it.next() + ", ");
    }
    System.out.println("");

    if (!tree.get(5).equals(5)) {
      System.out.println("error: tree does not have 5");
    }
    if (tree.get(13) != null) {
      System.out.println("error: tree has 13, should not");
    }
    if (!tree.get(10).equals(10)) {
      System.out.println("error: tree does not have 10");
    }

    tree.add(10);
    System.out.println(tree);

    tree.remove(10);
    tree.remove(2);
    tree.remove(5);
    tree.remove(9);
    tree.remove(10);
    tree.remove(9);
    System.out.println(tree);

    if (tree.get(5) != null) {
      System.out.println("error: tree has 5, should not");
    }
    if (tree.get(13) != null) {
      System.out.println("error: tree has 13, should not");
    }
    if (!tree.get(3).equals(3)) {
      System.out.println("error: tree does not have 3");
    }

  }

  /*
   * an iterator class to iterate over binary trees
   * 
   * @author Biagioni, Edoardo
   * 
   * @assignment lecture 17
   * 
   * @date March 12, 2008
   */

  private class TreeIterator implements Iterator<T> {
    /*
     * the class variables keep track of how much the iterator has done so far, and what remains to
     * be done. root is null when the iterator has not been initialized, or the entire tree has been
     * visited. the first stack keeps track of the last node to return and all its ancestors the
     * second stack keeps track of whether the node visited is to the left (false) or right (true)
     * of its parent
     */
    protected BinaryNode<T> root = null;
    protected Stack<BinaryNode<T>> visiting = new Stack<BinaryNode<T>>();
    protected Stack<Boolean> visitingRightChild = new Stack<Boolean>();
    /* only one of these booleans can be true */
    boolean preorder = false;
    boolean inorder = true;
    boolean postorder = false;

    /*
     * constructor for in-order traversal
     * 
     * @param root of the tree to traverse
     */
    public TreeIterator(BinaryNode<T> root) {
      this.root = root;
      visiting = new Stack<BinaryNode<T>>();
      visitingRightChild = new Stack<Boolean>();
      preorder = false;
      inorder = true;
      postorder = false;
    }

    /*
     * constructor for pre-order or post-order traversal
     * 
     * @param root of the tree to traverse
     * 
     * @param inPreorder true if pre-order, false if post-order
     */
    public TreeIterator(BinaryNode<T> root, boolean inPreorder) {
      this.root = root;
      visiting = new Stack<BinaryNode<T>>();
      visitingRightChild = new Stack<Boolean>();
      preorder = inPreorder;
      inorder = false;
      postorder = !preorder;
    }

    public boolean hasNext() {
      return (root != null);
    }

    public T next() {
      if (!hasNext()) {
        throw new java.util.NoSuchElementException("no more elements");
      }
      if (preorder) {
        return preorderNext();
      }
      else if (inorder) {
        return inorderNext();
      }
      else if (postorder) {
        return postorderNext();
      }
      else {
        assert (false);
        return null;
      }
    }

    // return the node at the top of the stack, push the next node if any
    private T preorderNext() {
      if (visiting.empty()) { // at beginning of iterator
        visiting.push(root);
      }
      BinaryNode<T> node = visiting.pop();
      T result = node.item;
      // need to visit the left subtree first, then the right
      // since a stack is a LIFO, push the right subtree first, then
      // the left. Only push non-null trees
      if (node.right != null) {
        visiting.push(node.right);
      }
      if (node.left != null) {
        visiting.push(node.left);
      }
      // may not have pushed anything. If so, we are at the end
      if (visiting.empty()) { // no more nodes to visit
        root = null;
      }
      return node.item;
    }

    /*
     * find the leftmost node from this root, pushing all the intermediate nodes onto the visiting
     * stack
     * 
     * @param node the root of the subtree for which we are trying to reach the leftmost node
     * 
     * @changes visiting takes all nodes between node and the leftmost
     */
    private void pushLeftmostNode(BinaryNode<T> node) {
      // find the leftmost node
      if (node != null) {
        visiting.push(node); // push this node
        pushLeftmostNode(node.left); // recurse on next left node
      }
    }

    /*
     * return the leftmost node that has not yet been visited that node is normally on top of the
     * stack inorder traversal doesn't use the visitingRightChild stack
     */
    private T inorderNext() {
      if (visiting.empty()) { // at beginning of iterator
        // find the leftmost node, pushing all the intermediate nodes
        // onto the visiting stack
        pushLeftmostNode(root);
      } // now the leftmost unvisited node is on top of the visiting stack
      BinaryNode<T> node = visiting.pop();
      T result = node.item; // this is the value to return
      // if the node has a right child, its leftmost node is next
      if (node.right != null) {
        BinaryNode<T> right = node.right;
        // find the leftmost node of the right child
        pushLeftmostNode(right);
        // note "node" has been replaced on the stack by its right child
      } // else: no right subtree, go back up the stack
        // next node on stack will be next returned
      if (visiting.empty()) { // no next node left
        root = null;
      }
      return result;
    }

    /*
     * find the leftmost node from this root, pushing all the intermediate nodes onto the visiting
     * stack and also stating that each is a left child of its parent
     * 
     * @param node the root of the subtree for which we are trying to reach the leftmost node
     * 
     * @changes visiting takes all nodes between node and the leftmost
     */
    private void pushLeftmostNodeRecord(BinaryNode<T> node) {
      // find the leftmost node
      if (node != null) {
        visiting.push(node); // push this node
        visitingRightChild.push(false); // record that it is on the left
        pushLeftmostNodeRecord(node.left); // continue looping
      }
    }

    //
    private T postorderNext() {
      if (visiting.empty()) { // at beginning of iterator
        // find the leftmost node, pushing all the intermediate nodes
        // onto the visiting stack
        pushLeftmostNodeRecord(root);
      } // the node on top of the visiting stack is the next one to be
        // visited, unless it has a right subtree
      if ((visiting.peek().right == null) || // no right subtree, or
          (visitingRightChild.peek())) { // right subtree already visited
        // already visited right child, time to visit the node on top
        T result = visiting.pop().item;
        visitingRightChild.pop();
        if (visiting.empty()) {
          root = null;
        }
        return result;
      }
      else { // now visit this node's right subtree
        // pop false and push true for visiting right child
        if (visitingRightChild.pop()) {
          assert (false);
        }
        visitingRightChild.push(true);
        // now push everything down to the leftmost node
        // in the right subtree
        BinaryNode<T> right = visiting.peek().right;
        assert (right != null);
        pushLeftmostNodeRecord(right);
        // use recursive call to visit that node
        return postorderNext();
      }
    }

    /* not implemented */
    public void remove() {
      throw new java.lang.UnsupportedOperationException("remove");
    }

    /* give the entire state of the iterator: the tree and the two stacks */
    public String toString() {
      if (preorder) {
        return "pre: " + toString(root) + "\n" + visiting + "\n";
      }
      if (inorder) {
        return "in: " + toString(root) + "\n" + visiting + "\n";
      }
      if (postorder) {
        return "post: " + toString(root) + "\n" + visiting + "\n" + visitingRightChild;
      }
      return "none of pre-order, in-order, or post-order are true";
    }

    private String toString(BinaryNode<T> node) {
      if (node == null) {
        return "";
      }
      else {
        return node.toString() + "(" + toString(node.left) + ", " + toString(node.right) + ")";
      }
    }

    // /* unit test
    // * @param arguments, ignored
    // */
    // public static void main(String[] arguments) {
    // BinaryNode<String> x = new BinaryNode<String>("x");
    // BinaryNode<String> z = new BinaryNode<String>("z");
    // BinaryNode<String> y = new BinaryNode<String>("y", x, z);
    // testIterator(new TreeIterator<String>(y));
    //
    // testIterator(new TreeIterator<String>(y, true));
    // testIterator(new TreeIterator<String>(y, false));
    //
    // BinaryNode<String> a = new BinaryNode<String>("a");
    // BinaryNode<String> c = new BinaryNode<String>("c");
    // BinaryNode<String> b = new BinaryNode<String>("b", a, null);
    // BinaryNode<String> m = new BinaryNode<String>("m", b, y);
    // testIterator(new TreeIterator<String>(m));
    //
    // testIterator(new TreeIterator<String>(m, true));
    // testIterator(new TreeIterator<String>(m, false));
    //
    // }
    //
    // public static void testIterator(Iterator<String> it) {
    // System.out.println("it = " + it);
    // while (it.hasNext()) {
    // String result = it.next();
    // System.out.println("it.next gives " + result + "\n it = " + it);
    // }
    // }
  }
}
