/* ListSorts.java */

import list.*;

public class ListSorts {

  private final static int SORTSIZE = 10000000;

  /**
   *  makeQueueOfQueues() makes a queue of queues, each containing one item
   *  of q.  Upon completion of this method, q is empty.
   *  @param q is a LinkedQueue of objects.
   *  @return a LinkedQueue containing LinkedQueue objects, each of which
   *    contains one object from q.
   **/
  public static LinkedQueue makeQueueOfQueues(LinkedQueue q) {
    // Replace the following line with your solution.
    LinkedQueue queueOfQ = new LinkedQueue();
    int size = q.size();

    try{
      for(int i = 0; i < size; ++ i){
        Object o = q.dequeue();
        LinkedQueue queue = new LinkedQueue();
        queue.enqueue(o);
        queueOfQ.enqueue(queue);
      }
    }catch(QueueEmptyException e){
      System.err.println("Queue is empty in function makeQueueOfQueues()");
    }

    return queueOfQ;
  }

  /**
   *  mergeSortedQueues() merges two sorted queues into a third.  On completion
   *  of this method, q1 and q2 are empty, and their items have been merged
   *  into the returned queue.
   *  @param q1 is LinkedQueue of Comparable objects, sorted from smallest 
   *    to largest.
   *  @param q2 is LinkedQueue of Comparable objects, sorted from smallest 
   *    to largest.
   *  @return a LinkedQueue containing all the Comparable objects from q1 
   *   and q2 (and nothing else), sorted from smallest to largest.
   **/
  public static LinkedQueue mergeSortedQueues(LinkedQueue q1, LinkedQueue q2) {
    // Replace the following line with your solution.
    LinkedQueue newSortedQueue = new LinkedQueue();

    try{
      int q1Size = q1.size();
      int q2Size = q2.size();
      int sizeOfTwo = q1Size + q2Size;
      Object o1 = q1.dequeue();
      Object o2 = q2.dequeue();
      int q1Index = 1;
      int q2Index = 1;

      for(int i = 0; i < sizeOfTwo; ++ i){

        if(q1Index > q1Size){
          newSortedQueue.enqueue(o2);
          if(!q2.isEmpty()) o2 = q2.dequeue();
          ++ q2Index;
          continue;
        }

        if(q2Index > q2Size){
          newSortedQueue.enqueue(o1);
          if(!q1.isEmpty()) o1 = q1.dequeue();
          ++ q1Index;
          continue;
        }

        if(((Comparable)o1).compareTo(o2) < 0){
          newSortedQueue.enqueue(o1);
          if(!q1.isEmpty()) o1 = q1.dequeue();
          ++ q1Index;
        }
        else{
          newSortedQueue.enqueue(o2);
          if(!q2.isEmpty()) o2 = q2.dequeue();
          ++ q2Index;
        }
      }
    }catch(QueueEmptyException e){
      System.err.println("Queue is empty in function mergeSortedQueues()");
    }

    return newSortedQueue;
  }

  /**
   *  partition() partitions qIn using the pivot item.  On completion of
   *  this method, qIn is empty, and its items have been moved to qSmall,
   *  qEquals, and qLarge, according to their relationship to the pivot.
   *  @param qIn is a LinkedQueue of Comparable objects.
   *  @param pivot is a Comparable item used for partitioning.
   *  @param qSmall is a LinkedQueue, in which all items less than pivot
   *    will be enqueued.
   *  @param qEquals is a LinkedQueue, in which all items equal to the pivot
   *    will be enqueued.
   *  @param qLarge is a LinkedQueue, in which all items greater than pivot
   *    will be enqueued.  
   **/   
  public static void partition(LinkedQueue qIn, Comparable pivot, 
                               LinkedQueue qSmall, LinkedQueue qEquals, 
                               LinkedQueue qLarge) {
    // Your solution here.
    try{
      while(qIn.size() > 0){
        Object o = qIn.dequeue();

        if(pivot.compareTo(o) < 0) qLarge.enqueue(o);
        else if(pivot.compareTo(o) == 0) qEquals.enqueue(o);
        else qSmall.enqueue(o);
      }
    }catch(QueueEmptyException e){
      System.err.println("Queue is empty in function partition()");
    }
  }

  /**
   *  mergeSort() sorts q from smallest to largest using mergesort.
   *  @param q is a LinkedQueue of Comparable objects.
   **/
  public static void mergeSort(LinkedQueue q) {
    // Your solution here.
    try{
      LinkedQueue queueOfQ = makeQueueOfQueues(q);

      while(queueOfQ.size() > 1){
        LinkedQueue q1 = (LinkedQueue)queueOfQ.dequeue();
        LinkedQueue q2 = (LinkedQueue)queueOfQ.dequeue();
        LinkedQueue q3 = mergeSortedQueues(q1, q2);
        queueOfQ.enqueue(q3);
      }

      q.append((LinkedQueue)queueOfQ.dequeue());
    }catch(QueueEmptyException e){
      System.err.println("Queue is empty in function mergeSort()");
    }
  }

  /**
   *  quickSort() sorts q from smallest to largest using quicksort.
   *  @param q is a LinkedQueue of Comparable objects.
   **/
  public static void quickSort(LinkedQueue q) {
    // Your solution here.
    if(q.size() <= 1) return;

    int pivotIndex = (int)(q.size() * Math.random());
    Comparable pivot = (Comparable)q.nth(pivotIndex);

    LinkedQueue qSmall = new LinkedQueue();
    LinkedQueue qEquals = new LinkedQueue();
    LinkedQueue qLarge = new LinkedQueue();

    partition(q, pivot, qSmall, qEquals, qLarge);

    quickSort(qSmall);
    quickSort(qLarge);

    q.append(qSmall);
    q.append(qEquals);
    q.append(qLarge);
  }

  /**
   *  makeRandom() builds a LinkedQueue of the indicated size containing
   *  Integer items.  The items are randomly chosen between 0 and size - 1.
   *  @param size is the size of the resulting LinkedQueue.
   **/
  public static LinkedQueue makeRandom(int size) {
    LinkedQueue q = new LinkedQueue();
    for (int i = 0; i < size; i++) {
      q.enqueue(new Integer((int) (size * Math.random())));
    }
    return q;
  }

  /**
   *  main() performs some tests on mergesort and quicksort.  Feel free to add
   *  more tests of your own to make sure your algorithms works on boundary
   *  cases.  Your test code will not be graded.
   **/
  public static void main(String [] args) {

    LinkedQueue q = makeRandom(10);

    //Test MergeSort in Queue size 0 and 1
    //LinkedQueue q = new LinkedQueue();
    //q.enqueue(new Integer(3));

    //System.out.println(q.toString());
    //mergeSort(q);
    //System.out.println(q.toString());


    //Test QuickSort in Queue size 0 and 1
    //LinkedQueue q = new LinkedQueue();
    //q.enqueue(new Integer(9));

    //q = makeRandom(10);
    //System.out.println(q.toString());
    //quickSort(q);
    //System.out.println(q.toString());

    /* Remove these comments for Part III.*/
    Timer stopWatch = new Timer();
    q = makeRandom(SORTSIZE);
    stopWatch.start();
    mergeSort(q);
    stopWatch.stop();
    System.out.println("Mergesort time, " + SORTSIZE + " Integers:  " +
                       stopWatch.elapsed() + " msec.");

    stopWatch.reset();
    q = makeRandom(SORTSIZE);
    stopWatch.start();
    quickSort(q);
    stopWatch.stop();
    System.out.println("Quicksort time, " + SORTSIZE + " Integers:  " +
                       stopWatch.elapsed() + " msec.");
  }

}
