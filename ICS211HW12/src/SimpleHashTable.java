import java.util.Set;
import java.util.TreeSet;

/**
 * Simple hash table implementation.
 *
 * @param <K> the key type
 * @param <V> the value type
 * @author deC, Leo
 * @assignment ICS 211 Assignment 12
 * @date November 24, 2011
 * @bugs None
 */
public class SimpleHashTable<K, V> {

  /**
   * The Class Entry.
   *
   * @param <K> the key type
   * @param <V> the value type
   */
  public static class Entry<K, V> implements Comparable<Entry<Comparable<K>, V>>{
    
    /** The key. */
    K key;
    
    /** The value. */
    V value;
    
    /**
     * Gets the key.
     *
     * @return the key
     */
    public K getKey() {
      return key;
    }
    
    /**
     * Gets the value.
     *
     * @return the value
     */
    public V getValue() {
      return value;
    }
    
    /**
     * Instantiates a new entry.
     *
     * @param key the key
     * @param value the value
     */
    public Entry(K key, V value) {
      super();
      this.key = key;
      this.value = value;
    }

    @Override
    public int compareTo(Entry<Comparable<K>, V> other) {
      return - other.key.compareTo(this.key);
    }


  }
  
  /** The initial data array size. */
  final int INITIAL_ARRAY_SIZE = 3;
  
  /** The current array length. */
  int currentArrayLength = INITIAL_ARRAY_SIZE;
  
  /** The data array. */
  //Entry<K, V>[] data = (Entry<K, V>[]) new Object[currentArrayLength];
  Object[] data = new Object[currentArrayLength];
      
  /**
   * Get all entries in the hash table.
   *
   * @return the set of all entries
   */
  public Set<Entry<K,V>> entrySet() {
    Set<Entry<K,V>> result = new TreeSet<Entry<K,V>>();
    for(int i = 0; i < currentArrayLength; i++) {
      if (data[i] != null) {
         result.add((Entry<K, V>)data[i]);
      }
    }
    return result;
  }
  
  /**
   * Gets the value for a given key.
   *
   * @param key the key
   * @return the value for that key or null if no entry for that key exists
   */
  public  V get(Object key) {
    int index = abs (key.hashCode() ) % currentArrayLength;
    if (data[index] != null)
      return ((Entry<K, V>)data[index]).getValue();
    else
      return null;
  }
  
  /**
   * Put an entry into the hash table.
   *
   * @param key the key
   * @param value the value
   * @return the value previously associated with that key if there is one.
   */
  public V put(K key, V value) {
    
    if (key == null)
      throw new NullPointerException("null keys not allowed");
    
    int index = abs(key.hashCode()) % currentArrayLength;
    if(index < 0) {
      System.out.println("negative has for key: " + key + " with hash " + index);
      index = -index;
    }
    if (data[index] == null) {
      data[index] = new Entry<K, V>(key, value);
      return null;
    }
    else {  //there is already an entry
      //is this a collision of an entry with the same key?
      if(((Entry<K, V>)data[index]).getKey().equals(key)) {  //it's the same key
        //store the previous value to return it to the caller
        V result = ((Entry<K, V>)data[index]).getValue();
        data[index] = new Entry(key, value);
        return result;
      } else {  //this is a collision
        System.out.println("reallocation collision: " + 
            ((Entry<K, V>)data[index]).key +
            " with hash = " + abs( ((Entry<K, V>)data[index]).key.hashCode() ) +
            " % " + currentArrayLength + " = " + 
            abs( ((Entry<K, V>)data[index]).key.hashCode() ) % currentArrayLength + 
            "\n\tand " + key +
            " with hash = " + abs( key.hashCode() ) + 
            " % " + currentArrayLength + " = " + abs( key.hashCode() % currentArrayLength )
                );
        reallocate (new Entry(key, value), currentArrayLength);
        return null;
      }
      
    }
  }
  
  
  /**
   * Reallocate.
   *
   * @param newEntry the new entry that originally caused the reallocation
   * @param previousArraySize the last attempted new size for the data array
   */
  private void reallocate (Entry<K, V> newEntry, int previousArraySize) {
    
    int newSize = 2 * previousArraySize + 1;
    
    System.out.println("reallocating:  attempting array of size " + newSize);
    
    //Entry<K, V>[] newDataArray = (Entry<K, V>[]) new Object[newSize];
    Object[] newDataArray = new Object[newSize];
    boolean collisionDetected = false;
    for(int i = 0; i < currentArrayLength; i++) {
      if (data[i] != null) {
        K key = ((Entry<K, V>)data[i]).key;
        int newIndex = abs( key.hashCode() ) % newSize;
        if (newDataArray[newIndex] == null) {
          newDataArray[newIndex] = (Entry<K, V>)data[i];
        } else {
          System.out.println("reallocation collision: " + 
              ((Entry<K, V>)data[i]).key +
              " with hash = " + abs( ((Entry<K, V>)data[i]).key.hashCode() ) +
              " % " + newSize + " = " + abs( ((Entry<K, V>)data[i]).key.hashCode() ) % newSize + 
              "\n\tand " + key +
              " with hash = " + abs( key.hashCode() ) + 
              " % " + newSize + " = " + abs( key.hashCode() ) % newSize);
          collisionDetected = true;
          break;
        }
      }
    }
    //now make sure the new entry causes no collision
    int newIndex = abs( newEntry.key.hashCode() ) % newSize;
    if (newDataArray[newIndex] == null) {
      newDataArray[newIndex] = newEntry;
    } else {
      collisionDetected = true;
    }
    
    if(collisionDetected) {
      reallocate (newEntry, newSize);
    } else {
      this.data = newDataArray;
      this.currentArrayLength = newDataArray.length;      
    }
    
  }
  
  /**
   * Removes the entry for a given key.
   *
   * @param key the key for the entry to be removed.
   * @return the value of the removed entry or null if there was
   * no entry with the given key.
   */
  public V remove(Object key) {
    int index = abs( key.hashCode() ) % currentArrayLength;
    if (data[index] != null) {
      V result = ((Entry<K, V>)data[index]).getValue();
      data[index] = null;
      return result;
    }
    else
      return null;
  }
  
  private int abs(int i) {
    if (i > 0)
      return i;
    else 
      return -i;
  }
  
}
