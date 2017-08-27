import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;


public class HashTagCounterManager {
	private IMaxFibonacciHeap maxFibonacciHeap;
	private Hashtable<String,MaxFibonacciHeapNode> hashTagMap = new Hashtable<String,MaxFibonacciHeapNode>();

	public HashTagCounterManager(){
		this.maxFibonacciHeap = new MaxFibonacciHeap();
		this.hashTagMap = new Hashtable<String, MaxFibonacciHeapNode>();
		// creates a new File if the file does not exist
	}

	public IMaxFibonacciHeap processLine(String hashTag, int frequency){
		//check if HashTag is already present
		MaxFibonacciHeapNode nodeToBeUpdated = hashTagMap.get(hashTag);

		// If present then call for increaseKey operation else add new Node in the heap and make an entry of the new node in the hash table 
		if(nodeToBeUpdated != null){				
			Boolean insertResult = maxFibonacciHeap.updateHashTagFrequency(nodeToBeUpdated, frequency);				
		}else{				
			MaxFibonacciHeapNode insertedNode = maxFibonacciHeap.insertNewHashTagIntoHeap(hashTag, frequency);				
			insertIntoMap(hashTag, insertedNode);			
		}
		return maxFibonacciHeap;
	}
	// This block is executed when the program encounters just a single integer which denotes that many number of max removes
	public String getRemovedNodes(String lineToBeProcessed){
		//System.out.println("Before the call given by query string");
		//maxFibonacciHeap.displayNodes();
		List<MaxFibonacciHeapNode> removedNodes = new ArrayList<MaxFibonacciHeapNode>();
		StringBuffer hashTagToBePrinted = new StringBuffer();
		int queryNumber = Integer.parseInt(lineToBeProcessed.trim());
		while(queryNumber > 0){
			MaxFibonacciHeapNode node = maxFibonacciHeap.removeMaxNode();
			hashTagToBePrinted.append(node.getHashTagName());
			hashTagToBePrinted.append(node.getHashtagFreq());
			hashTagToBePrinted.append(",");
			removedNodes.add(node);
			--queryNumber;
		}
		//System.out.println(hashTagToBePrinted);
		// reinsert the removed nodes back in the fibonacci heap as single nodes without pair wise combine.
		for(MaxFibonacciHeapNode node : removedNodes){
			MaxFibonacciHeapNode insertedNode = maxFibonacciHeap.insertNewHashTagIntoHeap(node.getHashTagName(), node.getHashtagFreq());				
			insertIntoMap(insertedNode.getHashTagName(), insertedNode);	
		}

		//words_to_write.add(hashTagToBePrinted.substring(0,hashTagToBePrinted.length()-1));
		return hashTagToBePrinted.substring(0,hashTagToBePrinted.length()-1);
	}

	// inserts the hashtag into map which is not yet seen
	public void insertIntoMap(String hashTag, MaxFibonacciHeapNode newlyInsertedTag){
		hashTagMap.put(hashTag, newlyInsertedTag);
	}

	public void hashTableContent(){
		Enumeration<String>mapKeyList = hashTagMap.keys();
		while(mapKeyList.hasMoreElements()) {
			String keyVal = mapKeyList.nextElement();
			System.out.println("Key is : " + keyVal );
			System.out.println("Value: " + hashTagMap.get(keyVal));
		}
	}
}
