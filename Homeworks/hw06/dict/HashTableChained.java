/* HashTableChained.java */

package dict;
import list.*;

/**
 *  HashTableChained implements a Dictionary as a hash table with chaining.
 *  All objects used as keys must have a valid hashCode() method, which is
 *  used to determine which bucket of the hash table an entry is stored in.
 *  Each object's hashCode() is presumed to return an int between
 *  Integer.MIN_VALUE and Integer.MAX_VALUE.  The HashTableChained class
 *  implements only the compression function, which maps the hash code to
 *  a bucket in the table's range.
 *
 *  DO NOT CHANGE ANY PROTOTYPES IN THIS FILE.
 **/

public class HashTableChained implements Dictionary {

  /**
   *  Place any data fields here.
   **/
  private Object[] hashTable;
  private int size;
  private int lengthForTable;

  /**
   * Function to generate a suitable prime length for hashtable 
   */

  private int sieveForPrime(int sizeEstimate){
    boolean[] prime = new boolean[2*sizeEstimate + 1];
    for(int i = 0; i <= 2*sizeEstimate; ++ i) prime[i] = true;

    for(int divisor = 2; divisor * divisor <= 2*sizeEstimate; ++ divisor){
      if(prime[divisor]){
        for(int i = 2*divisor; i <= 2*sizeEstimate; i += divisor) prime[i] = false;
      }
    }

    double loadFactor;
    for(int i = 0; i <= 2*sizeEstimate; ++ i){
      if(prime[i]){
        loadFactor = (double)sizeEstimate / i;
        if(loadFactor >= 0.5 && loadFactor <= 1) return i;
      }
    }

    return 0;
  }

  /**
   * Function to return length of hashtable
   */

  public int lengthForTable(){
    return lengthForTable;
  }

  /** 
   *  Construct a new empty hash table intended to hold roughly sizeEstimate
   *  entries.  (The precise number of buckets is up to you, but we recommend
   *  you use a prime number, and shoot for a load factor between 0.5 and 1.)
   **/

  public HashTableChained(int sizeEstimate) {
    // Your solution here.
    lengthForTable = sieveForPrime(sizeEstimate);
    hashTable = new Object[lengthForTable];
    size = 0;
  }

  /** 
   *  Construct a new empty hash table with a default size.  Say, a prime in
   *  the neighborhood of 100.
   **/

  public HashTableChained() {
    // Your solution here.
    lengthForTable = 101;
    hashTable = new Object[lengthForTable];
    size = 0;
  }

  /**
   *  Converts a hash code in the range Integer.MIN_VALUE...Integer.MAX_VALUE
   *  to a value in the range 0...(size of hash table) - 1.
   *
   *  This function should have package protection (so we can test it), and
   *  should be used by insert, find, and remove.
   **/

  int compFunction(int code) {
    // Replace the following line with your solution.
    int index = Math.abs(((127 * code + 128) % 16908799)) % lengthForTable;
    return index;
  }

  /** 
   *  Returns the number of entries stored in the dictionary.  Entries with
   *  the same key (or even the same key and value) each still count as
   *  a separate entry.
   *  @return number of entries in the dictionary.
   **/

  public int size() {
    // Replace the following line with your solution.
    return size;
  }

  /** 
   *  Tests if the dictionary is empty.
   *
   *  @return true if the dictionary has no entries; false otherwise.
   **/

  public boolean isEmpty() {
    // Replace the following line with your solution.
    return size == 0;
  }

  /**
   *  Create a new Entry object referencing the input key and associated value,
   *  and insert the entry into the dictionary.  Return a reference to the new
   *  entry.  Multiple entries with the same key (or even the same key and
   *  value) can coexist in the dictionary.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the key by which the entry can be retrieved.
   *  @param value an arbitrary object.
   *  @return an entry containing the key and value.
   **/

  public Entry insert(Object key, Object value) {
    // Replace the following line with your solution.
    Entry newEntry = new Entry();
    newEntry.key = key;
    newEntry.value = value;
    int index = compFunction(key.hashCode());

    if(hashTable[index] == null) hashTable[index] = new DList();
    ((DList)hashTable[index]).insertFront(newEntry);
    ++ size;

    return newEntry;
  }

  /** 
   *  Search for an entry with the specified key.  If such an entry is found,
   *  return it; otherwise return null.  If several entries have the specified
   *  key, choose one arbitrarily and return it.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the search key.
   *  @return an entry containing the key and an associated value, or null if
   *          no entry contains the specified key.
   **/

  public Entry find(Object key) {
    // Replace the following line with your solution.
    int index = compFunction(key.hashCode());
    if(hashTable[index] == null) return null;

    DListNode temp = ((DList)hashTable[index]).front();
    while(temp != null){
      Entry entry = (Entry)temp.item;
      if(entry.key() == key) return entry;
      temp = ((DList)hashTable[index]).next(temp);
    }

    return null;
  }

  /** 
   *  Remove an entry with the specified key.  If such an entry is found,
   *  remove it from the table and return it; otherwise return null.
   *  If several entries have the specified key, choose one arbitrarily, then
   *  remove and return it.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the search key.
   *  @return an entry containing the key and an associated value, or null if
   *          no entry contains the specified key.
   */

  public Entry remove(Object key) {
    // Replace the following line with your solution.
    int index = compFunction(key.hashCode());
    if(hashTable[index] == null) return null;

    DListNode temp = ((DList)hashTable[index]).front();
    while(temp != null){
      Entry entry = (Entry)temp.item;
      if(entry.key() == key){
        ((DList)hashTable[index]).remove(temp);
        -- size;
        return entry;
      }
      temp = ((DList)hashTable[index]).next(temp);
    }

    return null;
  }

  /**
   *  Remove all entries from the dictionary.
   */

  public void makeEmpty() {
    // Your solution here.
    for(int i = 0; i < lengthForTable; ++ i) hashTable[i] = null;

    size = 0;
    return;
  }

  /**
   * Function to convert the hashtable to a string
   */

  public String toString(){
    String result = "";
    for(int i = 0; i < lengthForTable; ++ i){
      if(hashTable[i] != null){
        DListNode node = ((DList)hashTable[i]).front();
        while(node != null){
          result = result + "Index: " + i + " Entries: ";
          Entry entry = (Entry)node.item;
          result = result + "[ " + entry.key() + "  " + entry.value() + " ]";
          node = ((DList)hashTable[i]).next(node);
        }
        result += "\n";
      }
    }
    return result;
  }

  /**
   * Test code for hashtable
   */

  public static void main(String[] args){
    System.out.println("----- Test for Construct a empty hashTable -----");
    HashTableChained hashTable1 = new HashTableChained();
    System.out.println("The number of entries in hashTable1 is: " + hashTable1.size());
    System.out.println("The hashTable1 is empty? " + hashTable1.isEmpty());
    System.out.println("The size of hashTable1 is: " + hashTable1.lengthForTable());

    System.out.println();
    System.out.println("----- Test for insert entries to hashTable -----");
    hashTable1.insert("Bob", 13356307);
    hashTable1.insert("Alice", 13349097);
    hashTable1.insert("Jack", 13389012);
    hashTable1.insert("Park", 19920809);
    hashTable1.insert("Bob", 13356306);
    System.out.println(hashTable1);
    System.out.println("The number of entries in hashTable1 is: " + hashTable1.size());
    System.out.println("The hashTable1 is empty? " + hashTable1.isEmpty());
    System.out.println("The size of hashTable1 is: " + hashTable1.lengthForTable());

    System.out.println();
    System.out.println("----- Test for find entry in hashTable -----");
    Entry findEntry = hashTable1.find("Bob");
    System.out.println("We find entry in dict that: key is " + findEntry.key() + " value is " + findEntry.value());
    findEntry = hashTable1.find("Mark");
    System.out.println("We try to find the entry is not in dict, it should be null: " + findEntry);

    System.out.println();
    System.out.println("----- Test for remove entry in hashTable -----");
    Entry removeEntry = hashTable1.remove("Park");
    System.out.println("The entry be removed from the dict is: " + removeEntry.key() + " " + removeEntry.value());
    removeEntry = hashTable1.remove("Bush");
    System.out.println("We try to remove the entry is not in the dict, it should return null: " + removeEntry);
    System.out.println(hashTable1);

    System.out.println();
    System.out.println("----- Test for remove all the entries in hashTable -----");
    hashTable1.makeEmpty();
    System.out.println("ALL the entries in the dict will be removed: " + hashTable1);
    System.out.println("The number of entries in hashTable1 is: " + hashTable1.size());
    System.out.println("The hashTable1 is empty? " + hashTable1.isEmpty());
    System.out.println("The size of hashTable1 is: " + hashTable1.lengthForTable());

    System.out.println();
    System.out.println("----- Test for another Constructor for hashTable -----");
    HashTableChained hashTable2 = new HashTableChained(10440);
    System.out.println("The number of entries in hashTable2 is: " + hashTable2.size());
    System.out.println("The hashTable2 is empty? " + hashTable2.isEmpty());
    System.out.println("The size of hashTable2 is: " + hashTable2.lengthForTable());

    System.out.println();
    System.out.println("----- Test for insert entries to hashTable -----");
    hashTable2.insert("Bob", 13356307);
    hashTable2.insert("Alice", 13349097);
    hashTable2.insert("Jack", 13389012);
    hashTable2.insert("Park", 19920809);
    hashTable2.insert("Bob", 13356306);
    System.out.println(hashTable2);
    System.out.println("The number of entries in hashTable2 is: " + hashTable2.size());
    System.out.println("The hashTable2 is empty? " + hashTable2.isEmpty());
    System.out.println("The size of hashTable2 is: " + hashTable2.lengthForTable());

    System.out.println();
    System.out.println("----- Test for find entry in hashTable -----");
    findEntry = hashTable2.find("Bob");
    System.out.println("We find entry in dict that: key is " + findEntry.key() + " value is " + findEntry.value());
    findEntry = hashTable2.find("Mark");
    System.out.println("We try to find the entry is not in dict, it should be null: " + findEntry);

    System.out.println();
    System.out.println("----- Test for remove entry in hashTable -----");
    removeEntry = hashTable2.remove("Bob");
    System.out.println("The entry be removed from the dict is: " + removeEntry.key() + " " + removeEntry.value());
    removeEntry = hashTable2.remove("Bush");
    System.out.println("We try to remove the entry is not in the dict, it should return null: " + removeEntry);
    System.out.println(hashTable2);

    System.out.println();
    System.out.println("----- Test for remove all the entries in hashTable -----");
    hashTable2.makeEmpty();
    System.out.println("ALL the entries in the dict will be removed: " + hashTable2);
    System.out.println("The number of entries in hashTable2 is: " + hashTable2.size());
    System.out.println("The hashTable2 is empty? " + hashTable2.isEmpty());
    System.out.println("The size of hashTable2 is: " + hashTable2.lengthForTable());

  }
}
