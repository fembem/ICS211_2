import java.util.List;
import java.util.NoSuchElementException;


public class MinHeap<E extends Comparable<E>> {

  int length = 1000;
  Object [] data = new Object[length];
  int maxIndex = -1;
  
  public boolean add(E e) throws IllegalStateException {
    if (maxIndex < length - 1) {
      maxIndex++;
      data[maxIndex] = e;
      heapifyUp(maxIndex);
      checkMinHeapProperty(0);
      return true;
    } else {
      throw new IllegalStateException("cannot exceed maximum capacity of " + length);
    }
  }
  
  private void heapifyUp(int index) {
    while (index > 0) {
      int parentIndex = getParentIndex(index);
      E parentObject = (E)data[parentIndex];
      E thisObject = (E)data[index];
      if (thisObject.compareTo(parentObject) < 0) {
        swapElements(index, parentIndex);
        index = parentIndex;
      } else break;
    }
  }

  private int getParentIndex(int index) {
    return (index -1) / 2;
  }
  
  private int getLeftChildIndex(int index) {
    return 2*index + 1;
  }
  
  private int getRightChildIndex(int index) {
    return 2*index + 2;
  }
  
  private void swapElements(int i , int j) {
    E temp = (E)data[i];
    data[i] = data[j];
    data[j] = temp;
  }
 
  public E remove() throws NoSuchElementException {
     if (maxIndex >= 0) {
       E result = (E)data[0];
       data[0] = data[maxIndex];
       maxIndex--;
       heapifyDown(0);
       checkMinHeapProperty(0);
       return result;
     }
     else {
       throw new NoSuchElementException("heap is empty");
     }
  }

  private void heapifyDown(int index) {
    //don't heapify down on last element or indices not corresponding to elements
    if (index >= maxIndex) return;
    E thisObject = (E)data[index];
    int leftIndex = getLeftChildIndex(index);
    int rightIndex = getRightChildIndex(index);
    
    //is there a left child?  there can be now right child without a left child
    if (leftIndex > maxIndex) return;
    E leftObject = (E)data[leftIndex];
    int orderLeft = thisObject.compareTo(leftObject);
    
    //start out assuming the left child is the smaller child index
    //there may not even be a right child
    int smallerChildIndex = leftIndex;
    
    //given that there is a left child index, is there a right child index too?
    if (rightIndex <= maxIndex) {
      E rightObject = (E)data[rightIndex];
      int orderRight = thisObject.compareTo(rightObject);
      //return if both children are greater or equal
      if (orderLeft <= 0 && orderRight <=0)
        return;
      //find the smaller child index
      int orderLeftRight = leftObject.compareTo(rightObject);
      if (orderLeftRight > 0) //the right child is smaller
        smallerChildIndex = rightIndex;
    } else if (orderLeft <= 0) {
      return; //there is no right child and the node is smaller than its left child
    }
    
    swapElements(index, smallerChildIndex);
    heapifyDown(smallerChildIndex);
    
  }
  
  private void checkMinHeapProperty(int index) {
    if (index >= maxIndex) return;
    int leftIndex = getLeftChildIndex(index);
    if (leftIndex > maxIndex) return;
    E thisObject = (E)data[index];
    E leftObject = (E)data[leftIndex];
    int orderLeft = thisObject.compareTo(leftObject);
    assert(orderLeft <= 0);
    checkMinHeapProperty(leftIndex);
    
    int rightIndex = getRightChildIndex(index);
    if (rightIndex > maxIndex) return;
    E rightObject = (E)data[rightIndex];
    int orderRight = thisObject.compareTo(rightObject);
    assert(orderRight <= 0); 
    checkMinHeapProperty(rightIndex);
  }
  
  public boolean containsAllElements(List<E> elements) {
    for (E element : elements) {
      boolean containsElement = containsElement(element);
      if ( !containsElement ) {
        System.out.println("missing: " + element);
        return false;
      }
    }
    return true;
  }
  
  public boolean containsElement(E element) {
    return containsElement(element, 0);
  }
  
  private boolean containsElement(E element, int index) {
    if(index > maxIndex) return false;
    if (((E)data[index]).equals(element)) return true;
    int leftIndex = getLeftChildIndex(index);
    int rightIndex = getRightChildIndex(index);
    return containsElement(element, leftIndex) || containsElement(element, rightIndex);
  }
  
  public int getSize() {
    return maxIndex + 1;
  }
  
}
