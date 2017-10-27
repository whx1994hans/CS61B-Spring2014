/* RunLengthEncoding.java */

/**
 *  The RunLengthEncoding class defines an object that run-length encodes
 *  a PixImage object.  Descriptions of the methods you must implement appear
 *  below.  They include constructors of the form
 *
 *      public RunLengthEncoding(int width, int height);
 *      public RunLengthEncoding(int width, int height, int[] red, int[] green,
 *                               int[] blue, int[] runLengths) {
 *      public RunLengthEncoding(PixImage image) {
 *
 *  that create a run-length encoding of a PixImage having the specified width
 *  and height.
 *
 *  The first constructor creates a run-length encoding of a PixImage in which
 *  every pixel is black.  The second constructor creates a run-length encoding
 *  for which the runs are provided as parameters.  The third constructor
 *  converts a PixImage object into a run-length encoding of that image.
 *
 *  See the README file accompanying this project for additional details.
 */

import java.util.Iterator;

public class RunLengthEncoding implements Iterable {

  /**
   *  Define any variables associated with a RunLengthEncoding object here.
   *  These variables MUST be private.
   */
  private DList runLengthList;
  private int width;
  private int height;

  /**
   *  The following methods are required for Part II.
   */

  /**
   *  RunLengthEncoding() (with two parameters) constructs a run-length
   *  encoding of a black PixImage of the specified width and height, in which
   *  every pixel has red, green, and blue intensities of zero.
   *
   *  @param width the width of the image.
   *  @param height the height of the image.
   */

  public RunLengthEncoding(int width, int height) {
    // Your solution here.
    this.width = width;
    this.height = height;
    runLengthList = new DList(0, 0, 0, width*height);
  }

  /**
   *  RunLengthEncoding() (with six parameters) constructs a run-length
   *  encoding of a PixImage of the specified width and height.  The runs of
   *  the run-length encoding are taken from four input arrays of equal length.
   *  Run i has length runLengths[i] and RGB intensities red[i], green[i], and
   *  blue[i].
   *
   *  @param width the width of the image.
   *  @param height the height of the image.
   *  @param red is an array that specifies the red intensity of each run.
   *  @param green is an array that specifies the green intensity of each run.
   *  @param blue is an array that specifies the blue intensity of each run.
   *  @param runLengths is an array that specifies the length of each run.
   *
   *  NOTE:  All four input arrays should have the same length (not zero).
   *  All pixel intensities in the first three arrays should be in the range
   *  0...255.  The sum of all the elements of the runLengths array should be
   *  width * height.  (Feel free to quit with an error message if any of these
   *  conditions are not met--though we won't be testing that.)
   */

  public RunLengthEncoding(int width, int height, int[] red, int[] green,
                           int[] blue, int[] runLengths) {
    // Your solution here.
    this.width = width;
    this.height = height;
    runLengthList = new DList();

    int sumOfEle = 0;
    for(int i = 0; i < runLengths.length; ++ i){
        sumOfEle += runLengths[i];
        runLengthList.insertFront(red[i], green[i], blue[i], runLengths[i]);
    }

    if(sumOfEle != width * height){
      System.out.println("The size is not match");
    }
  }

  /**
   *  getWidth() returns the width of the image that this run-length encoding
   *  represents.
   *
   *  @return the width of the image that this run-length encoding represents.
   */

  public int getWidth() {
    // Replace the following line with your solution.
    return this.width;
  }

  /**
   *  getHeight() returns the height of the image that this run-length encoding
   *  represents.
   *
   *  @return the height of the image that this run-length encoding represents.
   */
  public int getHeight() {
    // Replace the following line with your solution.
    return this.height;
  }

  /**
   *  iterator() returns a newly created RunIterator that can iterate through
   *  the runs of this RunLengthEncoding.
   *
   *  @return a newly created RunIterator object set to the first run of this
   *  RunLengthEncoding.
   */
  public RunIterator iterator() {
    // Replace the following line with your solution.
    return new RunIterator(this.runLengthList);
    // You'll want to construct a new RunIterator, but first you'll need to
    // write a constructor in the RunIterator class.
  }

  /**
   *  toPixImage() converts a run-length encoding of an image into a PixImage
   *  object.
   *
   *  @return the PixImage that this RunLengthEncoding encodes.
   */
  public PixImage toPixImage() {
    // Replace the following line with your solution.
    PixImage image = new PixImage(width, height);
    int i = 0;
    int j = 0;

    for(Object runs : this){
      int[] run = (int[])runs;
      for(int n = 0; n < run[0]; ++ n){
        image.setPixel(i, j, (short)run[1], (short)run[2], (short)run[3]);
        ++ j;
        if(j == height){
          j = 0;
          ++ i;
        }
      }
    }

    return image;
  }

  /**
   *  toString() returns a String representation of this RunLengthEncoding.
   *
   *  This method isn't required, but it should be very useful to you when
   *  you're debugging your code.  It's up to you how you represent
   *  a RunLengthEncoding as a String.
   *
   *  @return a String representation of this RunLengthEncoding.
   */
  public String toString() {
    // Replace the following line with your solution.
    String result = "";
    for(Object runs : this){
      int[] run = (int[])runs;
      result += "[ num:" + run[0] + " red:" + run[1] + " green:" + run[2] + " blue:" + run[3] + "]\n";
    }
    return result;
  }


  /**
   *  The following methods are required for Part III.
   */

  /**
   *  RunLengthEncoding() (with one parameter) is a constructor that creates
   *  a run-length encoding of a specified PixImage.
   * 
   *  Note that you must encode the image in row-major format, i.e., the second
   *  pixel should be (1, 0) and not (0, 1).
   *
   *  @param image is the PixImage to run-length encode.
   */
  public RunLengthEncoding(PixImage image) {
    // Your solution here, but you should probably leave the following line
    // at the end.
    width = image.getWidth();
    height = image.getHeight();
    runLengthList = new DList();
    int i = 0;
    int j = 0;
    int num = 1;
    short red, green, blue;
    red = green = blue = 0;
    while(i * j < (width-1) * (height-1)){
      short oldRed = image.getRed(i, j);
      short oldGreen = image.getGreen(i, j);
      short oldBlue = image.getBlue(i, j);

      ++ j;
      if(j == height){
        ++ i;
        j = 0;
      }

      short newRed = image.getRed(i, j);
      short newGreen = image.getGreen(i, j);
      short newBlue = image.getBlue(i, j);

      if((oldRed == newRed) && (oldGreen == newGreen) && (oldBlue == newBlue)) ++ num;
      else{
        runLengthList.insertFront(oldRed, oldGreen, oldBlue, num);
        num = 1;
      }

      red = newRed;
      green = newGreen;
      blue = newBlue;
    }
    runLengthList.insertFront(red, green, blue, num);

    check();
  }

  /**
   *  check() walks through the run-length encoding and prints an error message
   *  if two consecutive runs have the same RGB intensities, or if the sum of
   *  all run lengths does not equal the number of pixels in the image.
   */
  public void check() {
    // Your solution here.
    int[] oldRun = null;
    int[] newRun = null;
    int numOfRuns = 0;
    boolean isFirst = true;
    for(Iterator i = iterator(); i.hasNext();){
      if(isFirst){
        oldRun = (int[])(i.next());
        isFirst = false;
        continue;
      }
      newRun = (int[])(i.next());

      if(oldRun[1] == newRun[1] && oldRun[2] == newRun[2] && oldRun[3] == newRun[3]){
        System.out.println("Two consecutive runs have the same type of contents\n");
        return;
      }

      numOfRuns += oldRun[0];
      oldRun = newRun;
    }
    numOfRuns += oldRun[0];

    if(numOfRuns != width*height){
      System.out.println("The sum of all runs does not match the number of pixels in image\n");
    }
    return;
  }


  /**
   *  The following method is required for Part IV.
   */

  /**
   *  setPixel() modifies this run-length encoding so that the specified color
   *  is stored at the given (x, y) coordinates.  The old pixel value at that
   *  coordinate should be overwritten and all others should remain the same.
   *  The updated run-length encoding should be compressed as much as possible;
   *  there should not be two consecutive runs with exactly the same RGB color.
   *
   *  @param x the x-coordinate of the pixel to modify.
   *  @param y the y-coordinate of the pixel to modify.
   *  @param red the new red intensity to store at coordinate (x, y).
   *  @param green the new green intensity to store at coordinate (x, y).
   *  @param blue the new blue intensity to store at coordinate (x, y).
   */
  public void setPixel(int x, int y, short red, short green, short blue) {
    // Your solution here, but you should probably leave the following line
    //   at the end.
    int orderOfPixel = x * height + y + 1;
    DListNode start = runLengthList.tail;
    if(start == null) return;
    while(orderOfPixel > 0 && orderOfPixel != 1){
      orderOfPixel -= start.number;
      start = start.prev;
    }

    if(orderOfPixel == 0){
      -- start.next.number;
      if(red == (short)start.red && green == (short)start.green && blue == (short)start.blue) ++ start.number;
      else{
        DListNode newNode = new DListNode(red, green, blue, 1);
        newNode.prev = start;
        newNode.next = start.next;
        start.next.prev = newNode;
        start.next = newNode;
      }
    }
    else if(orderOfPixel == 1 && start != runLengthList.tail){
      -- start.number;
      if(start.number == 0){
        DListNode newNode = new DListNode(red, green, blue, 1);
        if(start.prev == null){
          if(red == (short)start.next.red && green == (short)start.next.green && blue == (short)start.next.blue){
            ++ start.next.number;
            runLengthList.head = start.next;
            start.next.prev = null;
          }
          else{
            newNode.next = start.next;
            newNode.prev = null;
            start.next.prev = newNode;
            runLengthList.head = newNode;
          }
        }
        else{
          boolean equalsNext = false;
          boolean equalsPrev = false;
          if(red == (short)start.next.red && green == (short)start.next.green && blue == (short)start.next.blue) equalsNext = true;
          if(red == (short)start.prev.red && green == (short)start.prev.green && blue == (short)start.prev.blue) equalsPrev = true;

          if(equalsNext && equalsPrev){
            start.next.number += start.prev.number + 1;
            start.next.prev = start.prev.prev;
            start.prev.prev.next = start.next;
          }
          else if(equalsNext){
            ++ start.next.number;
            start.prev.next = start.next;
            start.next.prev = start.prev;
          }
          else if(equalsPrev){
            ++ start.prev.number;
            start.prev.next = start.next;
            start.next.prev = start.prev;
          }
          else{
            newNode.prev = start.prev;
            start.prev.next = newNode;
            newNode.next = start.next;
            start.next.prev = newNode;
          }
        }
      }
      else{
        if(red == (short)start.next.red && green == (short)start.next.green && blue == (short)start.next.blue) ++ start.next.number;
        else{
          DListNode newNode = new DListNode(red, green, blue, 1);
          newNode.prev = start;
          newNode.next = start.next;
          start.next.prev = newNode;
          start.next = newNode;
        }
      }
    }
    else if(start == runLengthList.tail){
      -- start.number;
      DListNode newNode = new DListNode(red, green, blue, 1);
      if(start.number == 0){
        if(red == (short)start.prev.red && green == (short)start.prev.green && blue == (short)start.prev.blue){
          ++ start.prev.number;
          start.prev.next = null;
          runLengthList.tail = start.prev;
        }
        else{
          newNode.prev = start.prev;
          start.prev.next = newNode;
          runLengthList.tail = newNode;
          newNode.next = null;
        }
      }
      else{
        newNode.prev = start;
        start.next = newNode;
        runLengthList.tail = newNode;
        newNode.next = null;
      }
    }
    else{
      DListNode newNode1 = new DListNode(red, green, blue, 1);
      DListNode newNode2 = new DListNode(start.next.red, start.next.green, start.next.blue, -orderOfPixel);
      newNode1.prev = newNode2;
      newNode2.next = newNode1;
      start.next.number = start.next.number + orderOfPixel - 1;
      newNode1.next = start.next;
      start.next.prev = newNode1;
      start.next = newNode2;
      newNode2.prev = start;
    }

    check();
  }


  /**
   * TEST CODE:  YOU DO NOT NEED TO FILL IN ANY METHODS BELOW THIS POINT.
   * You are welcome to add tests, though.  Methods below this point will not
   * be tested.  This is not the autograder, which will be provided separately.
   */


  /**
   * doTest() checks whether the condition is true and prints the given error
   * message if it is not.
   *
   * @param b the condition to check.
   * @param msg the error message to print if the condition is false.
   */
  private static void doTest(boolean b, String msg) {
    if (b) {
      System.out.println("Good.");
    } else {
      System.err.println(msg);
    }
  }

  /**
   * array2PixImage() converts a 2D array of grayscale intensities to
   * a grayscale PixImage.
   *
   * @param pixels a 2D array of grayscale intensities in the range 0...255.
   * @return a new PixImage whose red, green, and blue values are equal to
   * the input grayscale intensities.
   */
  private static PixImage array2PixImage(int[][] pixels) {
    int width = pixels.length;
    int height = pixels[0].length;
    PixImage image = new PixImage(width, height);

    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        image.setPixel(x, y, (short) pixels[x][y], (short) pixels[x][y],
                       (short) pixels[x][y]);
      }
    }

    return image;
  }

  /**
   * setAndCheckRLE() sets the given coordinate in the given run-length
   * encoding to the given value and then checks whether the resulting
   * run-length encoding is correct.
   *
   * @param rle the run-length encoding to modify.
   * @param x the x-coordinate to set.
   * @param y the y-coordinate to set.
   * @param intensity the grayscale intensity to assign to pixel (x, y).
   */
  private static void setAndCheckRLE(RunLengthEncoding rle,
                                     int x, int y, int intensity) {
    rle.setPixel(x, y,
                 (short) intensity, (short) intensity, (short) intensity);
    rle.check();
  }

  /**
   * main() runs a series of tests of the run-length encoding code.
   */
  public static void main(String[] args) {
    // Be forwarned that when you write arrays directly in Java as below,
    // each "row" of text is a column of your image--the numbers get
    // transposed.
    PixImage image1 = array2PixImage(new int[][] { { 0, 3, 6 },
                                                   { 1, 4, 7 },
                                                   { 2, 5, 8 } });

    System.out.println("Testing one-parameter RunLengthEncoding constuctor " +
                       "on a 3x3 image.  Input image:");
    System.out.print(image1);
    RunLengthEncoding rle1 = new RunLengthEncoding(image1);
    rle1.check();
    System.out.println("Testing getWidth/getHeight on a 3x3 encoding.");
    doTest(rle1.getWidth() == 3 && rle1.getHeight() == 3,
           "RLE1 has wrong dimensions");

    System.out.println("Testing toPixImage() on a 3x3 encoding.");
    doTest(image1.equals(rle1.toPixImage()),
           "image1 -> RLE1 -> image does not reconstruct the original image");

    System.out.println("Testing setPixel() on a 3x3 encoding.");
    setAndCheckRLE(rle1, 0, 0, 42);

    System.out.println(rle1);

    image1.setPixel(0, 0, (short) 42, (short) 42, (short) 42);
    doTest(rle1.toPixImage().equals(image1),
           /*
                       array2PixImage(new int[][] { { 42, 3, 6 },
                                                    { 1, 4, 7 },
                                                    { 2, 5, 8 } })),
           */
           "Setting RLE1[0][0] = 42 fails.");

    System.out.println("Testing setPixel() on a 3x3 encoding.");
    setAndCheckRLE(rle1, 1, 0, 42);
    image1.setPixel(1, 0, (short) 42, (short) 42, (short) 42);
    doTest(rle1.toPixImage().equals(image1),
           "Setting RLE1[1][0] = 42 fails.");

    System.out.println("Testing setPixel() on a 3x3 encoding.");
    setAndCheckRLE(rle1, 0, 1, 2);
    image1.setPixel(0, 1, (short) 2, (short) 2, (short) 2);
    doTest(rle1.toPixImage().equals(image1),
           "Setting RLE1[0][1] = 2 fails.");

    System.out.println("Testing setPixel() on a 3x3 encoding.");
    setAndCheckRLE(rle1, 0, 0, 0);
    image1.setPixel(0, 0, (short) 0, (short) 0, (short) 0);
    doTest(rle1.toPixImage().equals(image1),
           "Setting RLE1[0][0] = 0 fails.");

    System.out.println("Testing setPixel() on a 3x3 encoding.");
    setAndCheckRLE(rle1, 2, 2, 7);
    image1.setPixel(2, 2, (short) 7, (short) 7, (short) 7);
    doTest(rle1.toPixImage().equals(image1),
           "Setting RLE1[2][2] = 7 fails.");

    System.out.println("Testing setPixel() on a 3x3 encoding.");
    setAndCheckRLE(rle1, 2, 2, 42);
    image1.setPixel(2, 2, (short) 42, (short) 42, (short) 42);
    doTest(rle1.toPixImage().equals(image1),
           "Setting RLE1[2][2] = 42 fails.");

    System.out.println("Testing setPixel() on a 3x3 encoding.");
    setAndCheckRLE(rle1, 1, 2, 42);
    image1.setPixel(1, 2, (short) 42, (short) 42, (short) 42);
    doTest(rle1.toPixImage().equals(image1),
           "Setting RLE1[1][2] = 42 fails.");


    PixImage image2 = array2PixImage(new int[][] { { 2, 3, 5 },
                                                   { 2, 4, 5 },
                                                   { 3, 4, 6 } });

    System.out.println("Testing one-parameter RunLengthEncoding constuctor " +
                       "on another 3x3 image.  Input image:");
    System.out.print(image2);
    RunLengthEncoding rle2 = new RunLengthEncoding(image2);
    rle2.check();
    System.out.println("Testing getWidth/getHeight on a 3x3 encoding.");
    doTest(rle2.getWidth() == 3 && rle2.getHeight() == 3,
           "RLE2 has wrong dimensions");

    System.out.println("Testing toPixImage() on a 3x3 encoding.");
    doTest(rle2.toPixImage().equals(image2),
           "image2 -> RLE2 -> image does not reconstruct the original image");

    System.out.println("Testing setPixel() on a 3x3 encoding.");
    setAndCheckRLE(rle2, 0, 1, 2);
    image2.setPixel(0, 1, (short) 2, (short) 2, (short) 2);
    doTest(rle2.toPixImage().equals(image2),
           "Setting RLE2[0][1] = 2 fails.");

    System.out.println("Testing setPixel() on a 3x3 encoding.");
    setAndCheckRLE(rle2, 2, 0, 2);
    image2.setPixel(2, 0, (short) 2, (short) 2, (short) 2);
    doTest(rle2.toPixImage().equals(image2),
           "Setting RLE2[2][0] = 2 fails.");


    PixImage image3 = array2PixImage(new int[][] { { 0, 5 },
                                                   { 1, 6 },
                                                   { 2, 7 },
                                                   { 3, 8 },
                                                   { 4, 9 } });

    System.out.println("Testing one-parameter RunLengthEncoding constuctor " +
                       "on a 5x2 image.  Input image:");
    System.out.print(image3);
    RunLengthEncoding rle3 = new RunLengthEncoding(image3);
    rle3.check();
    System.out.println("Testing getWidth/getHeight on a 5x2 encoding.");
    doTest(rle3.getWidth() == 5 && rle3.getHeight() == 2,
           "RLE3 has wrong dimensions");

    System.out.println("Testing toPixImage() on a 5x2 encoding.");
    doTest(rle3.toPixImage().equals(image3),
           "image3 -> RLE3 -> image does not reconstruct the original image");

    System.out.println("Testing setPixel() on a 5x2 encoding.");
    setAndCheckRLE(rle3, 4, 0, 6);
    image3.setPixel(4, 0, (short) 6, (short) 6, (short) 6);
    doTest(rle3.toPixImage().equals(image3),
           "Setting RLE3[4][0] = 6 fails.");

    System.out.println("Testing setPixel() on a 5x2 encoding.");
    setAndCheckRLE(rle3, 0, 1, 6);
    image3.setPixel(0, 1, (short) 6, (short) 6, (short) 6);
    doTest(rle3.toPixImage().equals(image3),
           "Setting RLE3[0][1] = 6 fails.");

    System.out.println("Testing setPixel() on a 5x2 encoding.");
    setAndCheckRLE(rle3, 0, 0, 1);
    image3.setPixel(0, 0, (short) 1, (short) 1, (short) 1);
    doTest(rle3.toPixImage().equals(image3),
           "Setting RLE3[0][0] = 1 fails.");


    PixImage image4 = array2PixImage(new int[][] { { 0, 3 },
                                                   { 1, 4 },
                                                   { 2, 5 } });

    System.out.println("Testing one-parameter RunLengthEncoding constuctor " +
                       "on a 3x2 image.  Input image:");
    System.out.print(image4);
    RunLengthEncoding rle4 = new RunLengthEncoding(image4);
    rle4.check();
    System.out.println("Testing getWidth/getHeight on a 3x2 encoding.");
    doTest(rle4.getWidth() == 3 && rle4.getHeight() == 2,
           "RLE4 has wrong dimensions");

    System.out.println("Testing toPixImage() on a 3x2 encoding.");
    doTest(rle4.toPixImage().equals(image4),
           "image4 -> RLE4 -> image does not reconstruct the original image");

    System.out.println("Testing setPixel() on a 3x2 encoding.");
    setAndCheckRLE(rle4, 2, 0, 0);
    image4.setPixel(2, 0, (short) 0, (short) 0, (short) 0);
    doTest(rle4.toPixImage().equals(image4),
           "Setting RLE4[2][0] = 0 fails.");

    System.out.println("Testing setPixel() on a 3x2 encoding.");
    setAndCheckRLE(rle4, 1, 0, 0);
    image4.setPixel(1, 0, (short) 0, (short) 0, (short) 0);
    doTest(rle4.toPixImage().equals(image4),
           "Setting RLE4[1][0] = 0 fails.");

    System.out.println("Testing setPixel() on a 3x2 encoding.");
    setAndCheckRLE(rle4, 1, 0, 1);
    image4.setPixel(1, 0, (short) 1, (short) 1, (short) 1);
    doTest(rle4.toPixImage().equals(image4),
           "Setting RLE4[1][0] = 1 fails.");






    System.out.println("------------------------------------\n");
    System.out.println("------------------------------------\n");
    System.out.println("------------------------------------\n");
    PixImage image5 = array2PixImage(new int[][] { { 0, 3, 0 },
                                                   { 1, 4, 2 },
                                                   { 3, 5, 8 }});

    System.out.println("Testing one-parameter RunLengthEncoding constuctor " +
                       "on a 3x3 image.  Input image:");
    System.out.print(image5);
    RunLengthEncoding rle5 = new RunLengthEncoding(image5);
    rle5.check();
    System.out.println("Testing getWidth/getHeight on a 3x3 encoding.");
    doTest(rle5.getWidth() == 3 && rle5.getHeight() == 3,
           "RLE5 has wrong dimensions");

    System.out.println("Testing toPixImage() on a 3x3 encoding.");
    doTest(rle5.toPixImage().equals(image5),
           "image5 -> RLE5 -> image does not reconstruct the original image");

    System.out.println("Testing setPixel() on a 3x3 encoding.");
    setAndCheckRLE(rle5, 0, 1, 0);
    System.out.println(rle5);
    image5.setPixel(0, 1, (short) 0, (short) 0, (short) 0);
    doTest(rle5.toPixImage().equals(image5),
           "Setting RLE5[0][1] = 0 fails.");

    System.out.println("Testing setPixel() on a 3x3 encoding.");
    setAndCheckRLE(rle5, 2, 2, 7);
    System.out.println(rle5);
    image5.setPixel(2, 2, (short) 7, (short) 7, (short) 7);
    doTest(rle5.toPixImage().equals(image5),
           "Setting RLE5[2][2] = 7 fails.");

    System.out.println("Testing setPixel() on a 3x3 encoding.");
    setAndCheckRLE(rle5, 1, 1, 2);
    System.out.println(rle5);
    image5.setPixel(1, 1, (short) 2, (short) 2, (short) 2);
    doTest(rle5.toPixImage().equals(image5),
           "Setting RLE5[1][1] = 2 fails.");
  }
}
