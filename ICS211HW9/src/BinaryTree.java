import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * A program to create and print a binary tree.
 *
 * @author Leo deC
 * @assignment 9
 * @date October 30, 2011
 * @bugs none
 */
public class BinaryTree{

  /** The head of the tree. */
  private BinaryNode<String> head = null;
  
  /**
   * The main method.
   *
   * @param args the arguments
   */
  public static void main(String[] args) {

    Scanner scan;
    InputStreamReader in = new InputStreamReader(System.in);
    BufferedReader input = new BufferedReader(in);
    BinaryTree tree = new BinaryTree();
    
    //process root node
    System.out.println("Enter root node string");
    String inputLine = null;
    try{
      inputLine = input.readLine();
    } catch (IOException ioe) {
      System.out.println("Error reading next line: " + ioe);
      System.exit(1);
    }
    scan = new Scanner(inputLine);
    String newNodeName = scan.next();
    tree.head = new BinaryNode<String>(newNodeName);
    //done processing root noide
    
    while (true) {
      System.out.println("the tree is:");
      System.out.println(tree.toString() + "\n");
      
      System.out.println("Enter string to insert followed by insertion path");
      System.out.println("i.e. \"baz l r\" to insert string \"baz\" as right node of left subtree of the root");
      inputLine = null;
      try{
        inputLine = input.readLine();
      } catch (IOException ioe) {
        System.out.println("Error reading next line: " + ioe);
        System.exit(1);
      }
      scan = new Scanner(inputLine);
      newNodeName = scan.next();
      if ("quit".equals(newNodeName)) {
        break;
      }
      BinaryNode<String> current = tree.head;
      boolean noPath = true;  //is any path specified?
      boolean validPath = true; //is the path specified valid?
      StringBuffer path = new StringBuffer(); //accumulate string representing the path
      boolean isLeft = false; //is the past path specifier of left type?
      while (scan.hasNext()) {
        String direction = scan.next();
        if ( !direction.equals("l") && !direction.equals("r") ) {
          System.out.println("\"" + direction + "\" is not a path specifier!");
          System.out.println("path specifiers are l and r");
          validPath = false;
          break;
        }
        path.append(direction + " ");
        isLeft = direction.equals("l");
        noPath = false;
        
        if (scan.hasNext() && 
            (isLeft && current.left == null || !isLeft && current.right == null)
            ) {
          System.out.println(path + " is not a valid sub-path");
          break;
        }
        //only update the current node if this is not the last path specifier
        //the last path specifier tells where to insert the new node
        //with respect to the current node
        if (scan.hasNext()) {
          current = isLeft ? current.left : current.right;
        }
      }
      
      if (!validPath) {continue;}
      if (noPath) {
        System.out.println(path + "no path specified");
        continue;
      }
      if (!scan.hasNext()) {   //only add a node if this is the last path specifier
        
        if (isLeft) {   //left case
          if (current.left == null) {
            BinaryNode<String> newNode = new BinaryNode<String>(newNodeName);
            current.left = newNode;
            System.out.println("Added node at path " + path);
          } else {
            System.out.println("There is already a node at " + path);
          }
        }
        else {  //right case
          if (current.right == null) {
            BinaryNode<String> newNode = new BinaryNode<String>(newNodeName);
            current.right = newNode;
            System.out.println("Added node at path " + path);
          } else {
            System.out.println("There is already a node at " + path);
          }
        }
      }
    }
    

  }
  
  /* 
   * Prints the tree in directory printing style with indents
   * corresponding to node depth.
   */
  @Override
  public String toString(){
    return treeToString(head, 0);
  }
  
  
/**
 * Converts the tree to a string.
 *
 * @param node the node
 * @param depth the depth
 * @return the string
 */
private String treeToString(BinaryNode<String> node, int depth) {
    if (node == null) return "";
    StringBuffer indentString = new StringBuffer();
    for (int i = 0; i < depth; i++) {
      indentString.append("    ");
    }
    return indentString 
      + node.item + "\n" 
      + treeToString(node.left, depth + 1) 
      + treeToString(node.right, depth + 1);
  }


/**
 * A node in a binary tree.
 *
 * @param <T> the generic type
 * @author         Edo Biagioni
 * @lecture        ICS 211 Mar 15 or later
 * @date           March 14, 2011
 * @bugs           private class: include this code within a larger class
 */
  private static class BinaryNode<T> {
    
    /** The item. */
    private T item;
    
    /** The left. */
    private BinaryNode<T> left;
    
    /** The right. */
    private BinaryNode<T> right;
  
  
  /**
   * constructor to build a node with no subtrees.
   *
   * @param value the value
   */
    private BinaryNode(T value) {
      item = value;
      left = null;
      right = null;
    }
  
  
  /**
   * constructor to build a node with a specified (perhaps null) subtrees.
   *
   * @param value the value
   * @param l the l
   * @param r the r
   */
    private BinaryNode(T value, BinaryNode<T> l, BinaryNode<T> r) {
      item = value;
      left = l;
      right = r;
    }
  }
}