/** 
  * A node in a singly-linked list
  * @author         Edo Biagioni
  * @lecture        ICS 211 Jan 27 or later
  * @date           January 26, 2011
  * @bugs           private class: include this code within a larger class
  */

public class LinkedNode<T> {
  public T item;
  public LinkedNode<T> next;


/** 
  * constructor to build a node with no successor
  * @param the value to be stored by this node
  */
  public LinkedNode(T value) {
    item = value;
    next = null;
  }


/** 
  * constructor to build a node with a specified (perhaps null) successor
  * @param the value to be stored by this node
  * @param the next field for this node
  */
  public LinkedNode(T value, LinkedNode<T> reference) {
    item = value;
    next = reference;
  }
}
