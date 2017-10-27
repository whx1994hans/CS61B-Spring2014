/* DListNode.java */
public class DListNode {

  public int red;
  public int green;
  public int blue;
  public int number;
  public DListNode prev;
  public DListNode next;

  DListNode() {
    red = 0;
    green = 0;
    blue = 0;
    number = 0;
    prev = null;
    next = null;
  }

  DListNode(int red, int green, int blue, int number) {
    this.red = red;
    this.green = green;
    this.blue = blue;
    this.number = number;
    prev = null;
    next = null;
  }
}
