/*
 * Interface which contains all the important function that are performed by Fibonacci Heaps 
 * The functions include : RemoveMax, IncreaseKey, Insert , delete
 */
public interface IMaxFibonacciHeap {

	// remove max
	public MaxFibonacciHeapNode removeMaxNode();
	// cascading cut
	public Boolean performParentChildCutCheck(MaxFibonacciHeapNode parentNode);
	//display 
	public void displayNodes();
	//increase key
	public Boolean increaseFrequency(MaxFibonacciHeapNode nodeToBeUpdated, int freq);
	//cut
	public Boolean detachTreeAndInsertAtRoot(MaxFibonacciHeapNode parentNode, MaxFibonacciHeapNode updatedNode);
	//inserts new nodes
	public MaxFibonacciHeapNode insertNewHashTagIntoHeap(String hashTagName, int frequency);	
	// update the existing node data by calling increase key
	public Boolean updateHashTagFrequency(MaxFibonacciHeapNode nodeToBeUpdated, int freq);
}
