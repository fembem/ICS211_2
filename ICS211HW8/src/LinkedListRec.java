/**
 * A list implemented using a singly-linked list and using recursive methods.
 *
 * @param <E> the element type
 * @author Edo Biagioni
 * @author Leo deC
 * @date October 23, 2011
 */

public class LinkedListRec<E> {

  // here, include the LinkedNode definition

  /**
   * A node in a singly-linked list.
   *
   * @param <T> the generic type
   * @author Edo Biagioni
   * @lecture ICS 211 Jan 27 or later
   * @date January 26, 2010
   */
  private static class LinkedNode<T> {
    
    /** The item. */
    private T item;
    
    /** The next. */
    private LinkedNode<T> next;

    /**
     * constructor to build a node with no successor.
     *
     * @param value the value
     */
    private LinkedNode(T value) {
      item = value;
      next = null;
    }

    /**
     * constructor to build a node with specified (maybe null) successor.
     *
     * @param value the value
     * @param reference the reference
     */
    private LinkedNode(T value, LinkedNode<T> reference) {
      item = value;
      next = reference;
    }
  }

  // end of the LinkedNode definition

  // this is the start of the linked list. If the list is empty, it is null
  /** The head. */
  protected LinkedNode<E> head;

  /**
   * initializes an empty linked list.
   */
  public LinkedListRec() {
    head = null;
  }

  /**
   * recursive private method, called by the public wrapper method.
   *
   * @param current the current
   * @return the size of the list
   */
  private int size(LinkedNode<E> current) {
    if (current == null) {
      return 0; // an empty list has size 0
    } // a non-empty list has size 1 more than the rest of the list:
    return 1 + size(current.next);
  }

  /**
   * public wrapper method, calls the private recursive method.
   *
   * @return the size of the list
   */
  public int size() {
    return size(head);
  }

  /**
   * recursive private method, called by the public wrapper method.
   *
   * @param node the node
   * @param value the value
   * @return the list, with the value added
   */
  private LinkedNode<E> addAtEnd(LinkedNode<E> node, E value) {
    if (node == null) {
      return new LinkedNode<E>(value);
    }
    node.next = addAtEnd(node.next, value);
    return node;
  }

  /**
   * public wrapper method, calls the private recursive method.
   *
   * @param value the value
   */
  public void add(E value) {
    head = addAtEnd(head, value);
  }

  /**
   * recursive private method, called by the public wrapper method.
   *
   * @param node the node
   * @param skip the skip
   * @param value the value
   * @return the list, with the value added
   */
  private LinkedNode<E> addAtPosition(LinkedNode<E> node, int skip, E value) {
    if (skip == 0) {
      return new LinkedNode<E>(value, node);
    }
    if (node == null) { // node is null but skip > 0 -- bad index
      throw new IndexOutOfBoundsException("bad index for add");
    }
    node.next = addAtPosition(node.next, skip - 1, value);
    return node;
  }

  /**
   * public wrapper method, calls the private recursive method.
   *
   * @param index the index
   * @param value the value
   */
  public void add(int index, E value) {
    head = addAtPosition(head, index, value);
  }

  /**
   * recursive private method, called by the public wrapper method.
   *
   * @param node the node
   * @param value the value
   * @return the list, with the value removed
   */
  private LinkedNode<E> remove(LinkedNode<E> node, E value) {
    if (node == null) { // node is null but skip > 0 -- bad index
      return node;
    }
    if (node.item.equals(value)) {
      return node.next; // match, so remove this node
    }
    node.next = remove(node.next, value);
    return node;
  }

  /**
   * public wrapper method, calls the private recursive method.
   *
   * @param value the value
   */
  public void remove(E value) {
    head = remove(head, value);
  }

  /**
   * recursive private method, called by the public wrapper method.
   *
   * @param node the node
   * @return the string representing the list
   */
  private String toString(LinkedNode<E> node) {
    if (node == null) {
      return "";
    }
    if (node.next == null) {
      return node.item.toString();
    }
    return node.item.toString() + " ==> " + toString(node.next);
  }

  /**
   * concatenates the elements of the linked list, separated by " ==> ".
   *
   * @return the string representation of the list
   */
  public String toString() {
    return toString(head);
  }

  
  /**
   * Adds duplicates to this linked list.
   *
   * @param value the value for which duplicates should be created
   */
  public void addDuplicates(E value) {
    head = addDuplicates(head, value);
  }

  /**
   * Adds the duplicates recursively.
   *
   * @param node the node at the head of the sublist to be modified
   * @param value the special value for which we duplicate nodes
   * @return the the node at the head the modified sublist
   */
  private LinkedNode<E> addDuplicates(LinkedNode<E> node, E value) {

    if (node == null) {
      return null;
    }
    if (value.equals(node.item)) {
      LinkedNode<E> duplicate = new LinkedNode<E>(value, node.next);
      node.next = duplicate;
      duplicate.next = addDuplicates(duplicate.next, value);
      return node;
    }
    node.next = addDuplicates(node.next, value);
    return node;
  }

  /**
   * unit test method -- basic testing of the functionality.
   *
   * @param arguments the arguments
   */
  public static void main(String[] arguments) {
    LinkedListRec<String> ll = new LinkedListRec<String>();
    String special = arguments[0];
    if (arguments.length < 1) {
      System.out.println("not enough arguments");
      System.out.println("enter element to duplicate followed by source");
      System.out.println(" list elements with spaces in between.");
      System.out.println("e.g. \"java LinkedListRec foo hello foo world bar foo baz\"");
    }
    for (int i = 1; i < arguments.length; i++) {
      ll.add(arguments[i]);
    }
    System.out.println("you entered the list:");
    System.out.println(ll);
    ll.addDuplicates(special);
    System.out.println("Added duplicates...");
    System.out.println("the modified list is:");
    System.out.println(ll);
  }

}