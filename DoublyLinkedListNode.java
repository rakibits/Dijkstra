public class DoublyLinkedListNode 
{

	private DoublyLinkedListNode next, previous;
	private int data;
	
	public DoublyLinkedListNode(DoublyLinkedListNode next, DoublyLinkedListNode previous, int data) 
	{
		this.next = next;
		this.previous = previous;
		this.data = data;
	}
		
	public DoublyLinkedListNode(int data) {
		this.data = data;
	}

	public DoublyLinkedListNode() {
	}

	public DoublyLinkedListNode getNext() {
		return next;
	}
	public void setNext(DoublyLinkedListNode next) {
		this.next = next;
	}
	public DoublyLinkedListNode getPrevious() {
		return previous;
	}
	public void setPrevious(DoublyLinkedListNode previous) {
		this.previous = previous;
	}
	public int getData() 
	{
		return data;
	}
	public void setData(int data) 
	{
		this.data = data;
	}
}
