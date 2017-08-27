/*
 * class that represents a node having the properties of degree, hashtagfrequency,
 * childcut , hashtagName, pointer to child, parents and siblings
 * along with the respective getters and setters for these instance variables
 */
public class MaxFibonacciHeapNode {

	private int degree;
	private int hashtagFreq;
	private Boolean childCut;
	private String hashTagName;
	private MaxFibonacciHeapNode child;
	private MaxFibonacciHeapNode parent;
	private MaxFibonacciHeapNode leftSibling;
	private MaxFibonacciHeapNode rightSibling;
	
	public MaxFibonacciHeapNode(){
		this.childCut = false;
		this.degree = 0;
		this.hashtagFreq = 0;
		this.leftSibling = this;
		this.rightSibling = this;
	}	
	
	public int getDegree() {
		return degree;
	}

	public void setDegree(int degree) {
		this.degree = degree;
	}

	public int getHashtagFreq() {
		return hashtagFreq;
	}

	public void setHashtagFreq(int hashtagFreq) {
		this.hashtagFreq = hashtagFreq;
	}

	public Boolean getChildCut() {
		return childCut;
	}

	public void setChildCut(Boolean childCut) {
		this.childCut = childCut;
	}

	public String getHashTagName() {
		return hashTagName;
	}

	public void setHashTagName(String hashTagName) {
		this.hashTagName = hashTagName;
	}

	public MaxFibonacciHeapNode getChild() {
		return child;
	}

	public void setChild(MaxFibonacciHeapNode child) {
		this.child = child;
	}

	public MaxFibonacciHeapNode getParent() {
		return parent;
	}

	public void setParent(MaxFibonacciHeapNode parent) {
		this.parent = parent;
	}

	public MaxFibonacciHeapNode getLeftSibling() {
		return leftSibling;
	}

	public void setLeftSibling(MaxFibonacciHeapNode leftSibling) {
		this.leftSibling = leftSibling;
	}

	public MaxFibonacciHeapNode getRightSibling() {
		return rightSibling;
	}

	public void setRightSibling(MaxFibonacciHeapNode rightSibling) {
		this.rightSibling = rightSibling;
	}
}
