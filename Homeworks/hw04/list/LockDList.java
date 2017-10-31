package list;

public class LockDList extends DList{
	/**
 	 * override some methods
 	 */
	protected LockDListNode newNode(Object item, DListNode prev, DListNode next){
		return new LockDListNode(item, (LockDListNode)prev, (LockDListNode)next);
	}

	public void remove(DListNode node){
		if(node == null) return;
		if(((LockDListNode)node).locked == true) return;

		super.remove(node);
	}

	public LockDListNode front(){
		return (LockDListNode)(super.front());
	}

	public LockDListNode back(){
		return (LockDListNode)(super.back());
	}
	
	public LockDListNode next(DListNode node){
		return (LockDListNode)(super.next(node));
	}

	public LockDListNode prev(DListNode node){
		return (LockDListNode)(super.prev(node));
	}

	public void lockNode(DListNode node){
		((LockDListNode)node).locked = true;
	}

	public static void main(String[] args){
		LockDList list = new LockDList();
		System.out.println("Create new LockDList " + list + " , the list is empty:" + list.isEmpty());

    	list.insertFront("are");
    	list.insertFront("Here");
    	System.out.println("Test insertFront(): " + list);

    	list.insertBack("three");
    	list.insertBack("dogs");
    	System.out.println("Test insertBack(): " + list);

    	LockDListNode front = list.front();
    	System.out.println("Test front(): " + front.item);

    	LockDListNode back = list.back();
    	System.out.println("Test back(): " + back.item);

    	LockDListNode next = list.next(front);
    	System.out.println("Test next(): " + next.item);

    	LockDListNode prev = list.prev(back);
    	System.out.println("Test prev(): " + prev.item);

    	next = list.next(next);
   		list.remove(next);
    	System.out.println("Test remove(): " + list);

    	list.insertBefore("so" ,back);
    	System.out.println("Test insertBefore(): " + list);
    	next = list.next(front);
    	list.insertAfter("many", next);
    	System.out.println("Test insertAfter(): " + list);
    	System.out.println("The size of list: " + list.length());

    	list.lockNode(next);
    	list.remove(next);
    	System.out.println("Test lockNode and remove: " + list);
	}
}