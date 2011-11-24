/* a class for an array based min heap
 * @author  Dec, Leo
 * @assignment  11
 * @date  November 20, 2011
 * 
 */

import java.util.List;
import java.util.NoSuchElementException;

/**
 * The Class MinHeap.
 * @author deC, Leo
 * @param <E> the element type
 */
public class MinHeap<E extends Comparable<E>> {

  /** The number of elements that can be in the heap. */
  int length = 1000;
  
  /** The data array used to store heap elements. */
  Object[] data = new Object[length];
  
  /** The index of the last heap element. */
  int maxIndex = -1;

  /**
   * Adds the.
   *
   * @param e the elemenst to add
   * @return true, if the item successfully added to the heap
   * @throws IllegalStateException the illegal state exception
   */
  public boolean add(E e) throws IllegalStateException {
    long start = System.nanoTime();
    if (maxIndex < length - 1) {
      maxIndex++;
      data[maxIndex] = e;
      heapifyUp(maxIndex);
      checkMinHeapProperty(0);
      long end = System.nanoTime();
      //System.out.println("add() took time " + (end - start));
      return true;
    }
    else {
      throw new IllegalStateException("cannot exceed maximum capacity of " + length);
    }
  }

  /**
   * Heapify up.
   *
   * @param index the index for which we need to check it and its ancestors for the
   * min-heap property
   */
  private void heapifyUp(int index) {
    while (index > 0) {
      int parentIndex = getParentIndex(index);
      E parentObject = (E) data[parentIndex];
      E thisObject = (E) data[index];
      if (thisObject.compareTo(parentObject) < 0) {
        swapElements(index, parentIndex);
        index = parentIndex;
      }
      else
        break;
    }
  }

  /**
   * Gets the parent index.
   *
   * @param index the index of a node in the tree
   * @return the index of its parent node
   */
  private int getParentIndex(int index) {
    return (index - 1) / 2;
  }

  /**
   * Gets the left child index.
   *
   * @param index the index of a node in the tree
   * @return the index of its left child or that its left child would have
   * if it existed.
   */
  private int getLeftChildIndex(int index) {
    return 2 * index + 1;
  }

  /**
   * Gets the right child index.
   *
   * @param index the index
   * @return the index of its right child or that its right child would have
   * if it existed.
   */
  private int getRightChildIndex(int index) {
    return 2 * index + 2;
  }

  /**
   * Swap elements.
   *
   * @param i the index of the first element to swap
   * @param j the index of the second element to swap
   */
  private void swapElements(int i, int j) {
    E temp = (E) data[i];
    data[i] = data[j];
    data[j] = temp;
  }

  /**
   * Removes the.
   *
   * @return the element at the root of the tree, i.e. the smallest element
   * @throws NoSuchElementException if there are no elements in the tree
   */
  public E remove() throws NoSuchElementException {

    long start = System.nanoTime();
    if (maxIndex >= 0) {
      E result = (E) data[0];
      data[0] = data[maxIndex];
      maxIndex--;
      heapifyDown(0);
      checkMinHeapProperty(0);
      long end = System.nanoTime();
      //System.out.println("remove() took time " + (end - start));
      return result;
    }
    else {
      throw new NoSuchElementException("heap is empty");
    }
  }

  /**
   * Heapify down.
   *
   * @param index the index for which we need to restore the heap property
   * to it and its descendants.
   */
  private void heapifyDown(int index) {
    // don't heapify down on last element or indices not corresponding to elements
    if (index >= maxIndex)
      return;
    E thisObject = (E) data[index];
    int leftIndex = getLeftChildIndex(index);
    int rightIndex = getRightChildIndex(index);

    // is there a left child? there can be now right child without a left child
    if (leftIndex > maxIndex)
      return;
    E leftObject = (E) data[leftIndex];
    int orderLeft = thisObject.compareTo(leftObject);

    // start out assuming the left child is the smaller child index
    // there may not even be a right child
    int smallerChildIndex = leftIndex;

    // given that there is a left child index, is there a right child index too?
    if (rightIndex <= maxIndex) {
      E rightObject = (E) data[rightIndex];
      int orderRight = thisObject.compareTo(rightObject);
      // return if both children are greater or equal
      if (orderLeft <= 0 && orderRight <= 0)
        return;
      // find the smaller child index
      int orderLeftRight = leftObject.compareTo(rightObject);
      if (orderLeftRight > 0) // the right child is smaller
        smallerChildIndex = rightIndex;
    }
    else if (orderLeft <= 0) {
      return; // there is no right child and the node is smaller than its left child
    }

    swapElements(index, smallerChildIndex);
    heapifyDown(smallerChildIndex);

  }

  /**
   * Check min heap property.
   *
   * @param index the index of the subtree to check for the min-heap property
   */
  private void checkMinHeapProperty(int index) {
    if (index >= maxIndex)
      return;
    int leftIndex = getLeftChildIndex(index);
    if (leftIndex > maxIndex)
      return;
    E thisObject = (E) data[index];
    E leftObject = (E) data[leftIndex];
    int orderLeft = thisObject.compareTo(leftObject);
    assert (orderLeft <= 0);
    checkMinHeapProperty(leftIndex);

    int rightIndex = getRightChildIndex(index);
    if (rightIndex > maxIndex)
      return;
    E rightObject = (E) data[rightIndex];
    int orderRight = thisObject.compareTo(rightObject);
    assert (orderRight <= 0);
    checkMinHeapProperty(rightIndex);
  }

  /**
   * Contains all elements.
   *
   * @param elements a list for elements of type E.
   * @return true, if the elements in the list are all in the heap
   */
  public boolean containsAllElements(List<E> elements) {
    for (E element : elements) {
      boolean containsElement = containsElement(element);
      if (!containsElement) {
        System.out.println("missing: " + element);
        return false;
      }
    }
    return true;
  }

  /**
   * Contains element.
   *
   * @param element an element of type E.
   * @return true, if E is in the heap.
   */
  public boolean containsElement(E element) {
    return containsElement(element, 0);
  }

  /**
   * Contains element.  Recursive helper method for public method.
   *
   * @param element the element to search for.
   * @param index the index of the subtree to search in
   * @return true, if the element is in the subtree with root at the index
   */
  private boolean containsElement(E element, int index) {
    if (index > maxIndex)
      return false;
    if (((E) data[index]).equals(element))
      return true;
    int leftIndex = getLeftChildIndex(index);
    int rightIndex = getRightChildIndex(index);
    return containsElement(element, leftIndex) || containsElement(element, rightIndex);
  }

  /**
   * Gets the number of elements in the heap.
   *
   * @return the size
   */
  public int getSize() {
    return maxIndex + 1;
  }

}
