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
      return true;
    } else {
      throw new IllegalStateException("cannot exceed maximum capacity of " + length);
    }
  }
  
  private void heapifyUp(int index) {
    if(index <= 0) return;
    int parentIndex = getParentIndex(index);
    E parentObject = (E)data[parentIndex];
    E thisObject = (E)data[index];
    if (thisObject.compareTo(parentObject) < 0) {
      swapElements(index, parentIndex);
    }
    heapifyUp(parentIndex);
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
       E result = (E)data[maxIndex];
       maxIndex--;
       heapifyDown();
       return result;
     }
     else {
       throw new NoSuchElementException("heap is empty");
     }
  }

  private void heapifyDown() {
    // TODO Auto-generated method stub
    
  }
  
}
