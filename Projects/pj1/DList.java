/* DList.java */

public class DList {

  public DListNode head;
  public DListNode tail;
  public long size;

  public DList() {
    head = null;
    tail = null;
    size = 0;
  }

  public DList(int red, int green, int blue, int number) {
    head = new DListNode(red, green, blue, number);
    tail = head;
    size = 1;
  }

  public void insertFront(int red, int green, int blue, int number) {
    // Your solution here.
    if(size == 0){
      head = new DListNode(red, green, blue, number);
      tail = head;
    }
    else{
      DListNode temp = new DListNode(red, green, blue, number);
      temp.next = head;
      head.prev = temp;
      head = temp;
    }
    ++ size;
  }

  public void removeFront() {
    // Your solution here.
    if(size == 0) return;
    else if(size == 1){
      head = head.next;
      tail = head;
      -- size;
    }
    else{
      head = head.next;
      head.prev = null;
      -- size;
    }
  }

  public String toString() {
    String result = "";
    DListNode current = head;
    while (current != null) {
      result = result + "[" + current.red + "," + current.green + "," + current.blue + "," + current.number + "]" + "\n";
      current = current.next;
    }
    return result;
  }
}
