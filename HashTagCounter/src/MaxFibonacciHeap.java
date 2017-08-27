import java.util.HashMap;


public class MaxFibonacciHeap implements IMaxFibonacciHeap {
	private MaxFibonacciHeapNode rootNode;
	private MaxFibonacciHeapNode maximumHashTagNode;
	private int totalNodes = 0;
	private int totalNoOfRootNodes = 0;

	/*
	 * Inserts a new node with a specific hashtagName and frequency at the root level by adjusting
	 * the pointers of the doubly circular linked list
	 * @see IMaxFibonacciHeap#insertNewHashTagIntoHeap(java.lang.String, int)
	 */
	public MaxFibonacciHeapNode insertNewHashTagIntoHeap(String hashTagName, int frequency){
		MaxFibonacciHeapNode newHashTag = new MaxFibonacciHeapNode();
		//set the hashtagName and freq
		newHashTag.setHashTagName(hashTagName);
		newHashTag.setHashtagFreq(frequency);

		// adjusting the pointer for the root and newly inserted node
		if(rootNode != null){
			this.insertNodeAtRootLevel(newHashTag);

			// updating the maximumHashTagNode to point to max freq
			if(maximumHashTagNode.getHashtagFreq() < frequency){
				maximumHashTagNode = newHashTag;
			}
		}
		// pointing the rootnode to the new element and updating the max pointer
		else{
			rootNode = newHashTag;
			maximumHashTagNode = rootNode;	
			++totalNoOfRootNodes;
		}

		totalNodes ++;		
		return newHashTag;
	}

	// Inserts a new node at the front of the node pointed by the rootNode
	public void insertNodeAtRootLevel(MaxFibonacciHeapNode nodeToBeInserted){
		// point left sibling pointer to root node
		nodeToBeInserted.setLeftSibling(rootNode);
		// point right sibling pointer to the node previously pointed by the root node 
		nodeToBeInserted.setRightSibling(rootNode.getRightSibling());
		// set the right sibling pointer of the rootnode to the newly inserted node
		rootNode.setRightSibling(nodeToBeInserted);
		// changes the left sibling pointer of the node pointed by the right sibling of new inserted node
		nodeToBeInserted.getRightSibling().setLeftSibling(nodeToBeInserted);	
		++totalNoOfRootNodes;
	}

	/*
	 * method used to display the nodes in the tree
	 * not used for our implementation 
	 * @see IMaxFibonacciHeap#displayNodes()
	 */
	public void displayNodes(){
		if(rootNode!= null){
			MaxFibonacciHeapNode moveTemp = rootNode;
			do{
				//System.out.println("HashTag " + moveTemp.getHashTagName() + " leftSib---" + moveTemp.getLeftSibling().getHashTagName()+ " rightSib--- " + moveTemp.getRightSibling().getHashTagName() +" is " + moveTemp.getHashtagFreq());
				if(moveTemp.getChild()!= null){
					MaxFibonacciHeapNode currentChild = moveTemp.getChild();
					do{
						//System.out.println(currentChild.getHashTagName() + "(" + currentChild.getHashtagFreq() + ")");
						currentChild = currentChild.getRightSibling();
					}while(currentChild != moveTemp.getChild());							
				}
				
				moveTemp = moveTemp.getRightSibling();
			}while(moveTemp != rootNode);
		}

		//System.out.println("The maximum Node value is "+ maximumHashTagNode.getHashTagName() + maximumHashTagNode.getHashtagFreq());
		//System.out.println("The total number of nodes in the tree " + totalNodes);
		//System.out.println("The total number of root nodes in the tree " + totalNoOfRootNodes);
	}

	// called when the hastag value is already present in the hashtable for updation
	public Boolean updateHashTagFrequency(MaxFibonacciHeapNode nodeToBeUpdated, int freq){
		// increases the value of the key by a amount specified by freq 
		return increaseFrequency(nodeToBeUpdated, freq);
	}

	/// Perform the increase key function
	public Boolean increaseFrequency(MaxFibonacciHeapNode nodeToBeUpdated, int freq){
		// if the freq to be increased with is negative then we dont increase the key
		if(freq <= 0){
			return false;
		}

		MaxFibonacciHeapNode parentForNode = nodeToBeUpdated.getParent();

		// updating the frequency of the node by the amount passed in the function 
		int previousFreq = nodeToBeUpdated.getHashtagFreq();
		int newFreq = previousFreq + freq;
		nodeToBeUpdated.setHashtagFreq(newFreq);
		
		// check if the frequency after increase if greater than its parent 
		if(parentForNode != null && parentForNode.getHashtagFreq() < newFreq){
			// remove the subtree and insert it at the root level.
			Boolean insertionResult = detachTreeAndInsertAtRoot(parentForNode, nodeToBeUpdated);
			if(insertionResult){
				performParentChildCutCheck(parentForNode);
			}
		}

		//update the max pointer if was a single node with no children
		if (maximumHashTagNode.getHashtagFreq()< newFreq){
			maximumHashTagNode = nodeToBeUpdated;
		}

		return true;		
	}

	// performs the detach only from existing tree or cut function
	public Boolean detachTreeAndInsertAtRoot(MaxFibonacciHeapNode parentNode, MaxFibonacciHeapNode updatedNode){

		// Detach tree rooted at the updateNode from its siblings circular doubly linked list		
		detachNodeFromSiblingList(updatedNode);

		// update the child pointer of parent if the node to be removed was indeed the child pointer
		if (parentNode.getChild() == updatedNode){
			parentNode.setChild(updatedNode.getRightSibling());
		}

		// decrease the degree field of the parent by 1
		int newDegree = parentNode.getDegree() - 1;
		parentNode.setDegree(newDegree);
		if(newDegree == 0){
			parentNode.setChild(null);
		}

		// Add the subtree rooted at updatedNode to the root level
		this.insertNodeAtRootLevel(updatedNode);

		// update the parent and childCut fields 
		updatedNode.setParent(null);
		updatedNode.setChildCut(false);		
		return true;
	}

	// pointer manipulations done when the node is removed from its sibling list other than the root node.
	public void detachNodeFromSiblingList(MaxFibonacciHeapNode rootNodeToBeDeleted){
		rootNodeToBeDeleted.getRightSibling().setLeftSibling(rootNodeToBeDeleted.getLeftSibling());
		rootNodeToBeDeleted.getLeftSibling().setRightSibling(rootNodeToBeDeleted.getRightSibling());
	}

	// Performs the cascading cut
	public Boolean performParentChildCutCheck(MaxFibonacciHeapNode parentNode){
		// Get the reference to the grand parent of the node which is removed.
		MaxFibonacciHeapNode parentNodeOfParent = parentNode.getParent();
		
		if(parentNodeOfParent != null){
			if(!parentNode.getChildCut()){
				// if the child cut field of child cut of the parent node is false then set it true
				parentNode.setChildCut(true);
			}else{
				// Otherwise detach the subtree rooted at the parent node and insert it at the root level.
				Boolean insertionResult = detachTreeAndInsertAtRoot(parentNodeOfParent, parentNode);
				if(insertionResult){
					// recursively call this until we reach a node which has child cut equal to false or reach the root level node.
					return performParentChildCutCheck(parentNodeOfParent);
				}				
			}
		}
		/// the parentNode has no parent and thus the child cut remains as false: no need to update
		return true;
	}

	/************************************************ RemoveMax *****************************************************/

	public MaxFibonacciHeapNode removeMaxNode(){

		MaxFibonacciHeapNode maximumFreqNode = maximumHashTagNode;		
		MaxFibonacciHeapNode childSiblings;
		int totalNoOfChildren = maximumFreqNode.getDegree();
		MaxFibonacciHeapNode childNodeTobeMoved = totalNoOfChildren > 0 ? maximumFreqNode.getChild() : null;
		while(totalNoOfChildren > 0){
			childSiblings = childNodeTobeMoved.getRightSibling();
			//remove each of the child nodes which are children of the maximum node from its circular doubly list
			detachNodeFromSiblingList(childNodeTobeMoved);
			// insert the child node to the root level
			insertNodeAtRootLevel(childNodeTobeMoved);
			// update the parent field of the newly inserted child node
			childNodeTobeMoved.setParent(null);
			childNodeTobeMoved = childSiblings;
			// decrease the no of child nodes of the max node
			--totalNoOfChildren;
		}
		
		// Remove the maximum node from the root level circular doubly list
		detachNodeFromSiblingList(maximumFreqNode);
		// update the root node if it was pointing to the node which was deleted
		updateRootNode(maximumFreqNode);
		// decrease the total number of nodes and total no of root nodes by 1
		--totalNodes;
		--totalNoOfRootNodes;
		maximumHashTagNode = null;		
		performPairWiseCombine();

		return maximumFreqNode;
	}

	// function to join trees having same degrees
	public void performPairWiseCombine(){
		// Initialize a hash map to keep the degree as the key and the root node level as the value
		HashMap<Integer, MaxFibonacciHeapNode> degreeMapper = new HashMap<Integer, MaxFibonacciHeapNode>();
		MaxFibonacciHeapNode node = rootNode;		
		int totalNoOfRootNodesbackUp = totalNoOfRootNodes;
		while(totalNoOfRootNodesbackUp > 0){
			int degree = node.getDegree();
			MaxFibonacciHeapNode pointerRightSibling = node.getRightSibling();
			// infinite loop to continue till the Hashmap has all distinct degree values
			while(true){
				// checks if the table has another tree having same degree
				MaxFibonacciHeapNode node2 = degreeMapper.get(degree);
				// if not found the max pointer is updated and respective degree and node is saved in the Hashmap
				if(node2 == null){
					setMaxHashTagNode(node);					
					break;
				}	
				
				// if some other tree is having the same degree we check for combine them based on the frequency values
				if(node.getHashtagFreq() >= node2.getHashtagFreq()){
					node = meld(node,node2);
				}else{
					node = meld(node2, node);
				}
				// once the combine is completed the previous degree value is removed from the map
				degreeMapper.remove(degree);
				// and the degree value is increased since one was made the child of the other
				++degree;
			}
			/* once a tree is formed by the process of pair wise combine and there is no conflict of that degree
			 * with  any entry in the hash map then make an entry in th hashmap with the degree value and the root node of that tree
			 */
			degreeMapper.put(degree,node);
			node = pointerRightSibling;		
			--totalNoOfRootNodesbackUp;
		}		
	}

	public MaxFibonacciHeapNode meld(MaxFibonacciHeapNode toBeParentNode, MaxFibonacciHeapNode toBeChildNode){
		// the to be child node is removed from the root level circular doubly linked list 
		detachNodeFromSiblingList(toBeChildNode);
		// since one of the root node is removed we decrease the count of total number of root nodes
		--totalNoOfRootNodes;

		// sets the parent field of the child node to point to the parent
		toBeChildNode.setParent(toBeParentNode);
		
		// if the parent does not have a child we set the child field to point to the child node
		if(toBeParentNode.getChild() == null){
			toBeParentNode.setChild(toBeChildNode);
			toBeChildNode.setRightSibling(toBeChildNode);
			toBeChildNode.setLeftSibling(toBeChildNode);			
		}
		// if the parent already have a child node then we just attach the new child node to the circular doubly list
		else{
			MaxFibonacciHeapNode currentChildPointer = toBeParentNode.getChild();
			toBeChildNode.setLeftSibling(currentChildPointer);
			toBeChildNode.setRightSibling(currentChildPointer.getRightSibling());
			currentChildPointer.setRightSibling(toBeChildNode);
			toBeChildNode.getRightSibling().setLeftSibling(toBeChildNode);			
		}

		// updating of the root node may be required if the root was pointing to the child node which was removed
		updateRootNode(toBeChildNode);
		// simultaneously updating the max pointer
		setMaxHashTagNode(toBeParentNode);
		// increases the degree of the parent node by 1		
		toBeParentNode.setDegree(toBeParentNode.getDegree()+1);
		return toBeParentNode;
	}

	// updates the max pointer while doing the pair waise combine and meld operations
	public void setMaxHashTagNode(MaxFibonacciHeapNode newNode){
		if(maximumHashTagNode != null){
			if(maximumHashTagNode.getHashtagFreq() < newNode.getHashtagFreq()){
				maximumHashTagNode = newNode;
			}
		}else{
			maximumHashTagNode = newNode;
		}
	}

	public void updateRootNode(MaxFibonacciHeapNode currentNodeToBeDeleted){
		
		if(rootNode == currentNodeToBeDeleted){
			// case when root was pointing to child node which was removed and made the child of its parent
			if(currentNodeToBeDeleted.getParent()!= null){
				rootNode = currentNodeToBeDeleted.getParent();				
			}
			else{
				rootNode = currentNodeToBeDeleted.getRightSibling();
			}			
		}
	}
}

