/* record in a database of string keys and an integer value for each
 * @author  Biagioni, Edoardo
 * @assignment  10
 * @date  April 5, 2011
 */

public class Record implements Comparable<Record> {
  String key;
  int value;

  public Record(String k) { // this is a key -- a record with only
    key = k; // a valid key, no value
    value = 0;
  }

  public Record(String k, int v) {
    key = k;
    value = v;
  }

  public boolean equals(Record r) {
    return key.equals(r.key);
  }

  public int compareTo(Record r) {
    return key.compareTo(r.key);
  }

  public String toString() {
    return key + "," + value;
  }
}
