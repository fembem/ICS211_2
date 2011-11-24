import java.util.Set;

/**
 * Simple hash table implementation.
 * @author deC, Leo
 * @assignment ICS 211 Assignment 12
 * @date November 24, 2011
 * @bugs None
 */
public class SimpleHashTable<K, V> {

  public static class Entry<K, V>{
    K key;
    V value;
    public K getKey() {
      return key;
    }
    public V getValue() {
      return value;
    }
    public Entry(K key, V value) {
      super();
      this.key = key;
      this.value = value;
    }
  }
  
  public Set<SimpleHashTable.Entry<K,V>> entrySet() {
    return null;
  }
  
  public  V get(Object key) {
    return null;
  }
  
  public V put(K key, V value) {
    return null;
  }
  
  public V remove(Object key) {
    return null;
  }
  
}
