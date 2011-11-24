import static org.junit.Assert.*;
import java.util.Set;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class SimpleHashTableTest {

  SimpleHashTable<String, Integer> table;
  
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
  }

  @Before
  public void setUp() throws Exception {
    
    table = new SimpleHashTable<String, Integer>();
    table.put("Leo", 1);
    table.put("John", 2);
    table.put("Bob", 3);
    table.put("Joe", 4);
    table.put("Bruce", 5);
    
  }

  @Test
  public void testEntrySet() {
    Set<SimpleHashTable.Entry<String, Integer>> set = table.entrySet();
    assertTrue(set.contains( new SimpleHashTable.Entry<String, Integer>("Leo", 1)));
    assertTrue(set.contains( new SimpleHashTable.Entry<String, Integer>("John", 2)));
    assertTrue(set.contains( new SimpleHashTable.Entry<String, Integer>("Bob", 3)));
    assertTrue(set.contains( new SimpleHashTable.Entry<String, Integer>("Joe", 4)));
    assertTrue(set.contains( new SimpleHashTable.Entry<String, Integer>("Bruce", 5)));
  }

  @Test
  public void testGet() {
    assertTrue(new Integer(2).equals(table.get("John")));
  }

  @Test
  public void testPut() {
    table.put("Arty", 8);
  }

  @Test
  public void testRemove() {
    assertEquals(new Integer(3), table.remove("Bob"));
    assertNull( table.remove("Harry") );
  }
  
  @Test
  public void testLargeDataSet() {
    table.remove("Leo");
    table.remove("John");
    table.remove("Bob");
    table.remove("Joe");
    table.remove("Bruce");
    for (int i = 0; i < 10000; i++) {
      table.put("number" + i, i);
    }
  }

}
