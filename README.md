# PROJECT DESCRIPTION
Find certain number of most popular hashtags appearing on social media such as Facebook or Twitter.

The project requires implementing a max Fibonacci Heap to find the N most popular hash tags
appearing on social media. As a part of the input to the program, we read one line at a time from
an input file and generate a max Fibonacci Heap by calling the functions of insert and increase
key (hash tag already present inside the hash table).For every query that is encountered by the
program we perform the max remove operation along with pair wise combine and writes the
node value having the maximum frequency in the output_file.txt.

# STRUCTURE OF THE PROGRAM AND FUNCTION PROTOTYPE DESCRIPTIONS

## The source code for the entire project is divided into 5 classes
* IMaxFibonacciHeap.java
* HashTagCounterManager.java
* MaxFibonacci Heap.java
* MaxFibonacciHeapNode.java
* hashtagcounter.java

**IMaxFibonacciHeap.java**

The Interface IMaxFibonacciHeap.java describes the necessary abstract functions performed by
a max Fibonacci Heap such as removeMax, insert, delete, increasekey, displayNodes. Any
class implementing this interface should provide a concrete implementation of these methods.

**MaxFibonacciHeapNode.java**

Each instance of this class represents an individual node entity required in building the
Fibonacci tree structure. Each node object will have the following instance variables along with
their getters and setters.

**Instance variables are:**
* degree

  _Type_: Integer
  
  _Function_: denotes the number of child nodes a particular node is having in the immediate next level.
* hashtagfreq

  _Type_ : Integer
  
  _Function_ : Represents the frequency of a particular hash tag node.
* hashTagName

 _Type_ : String
 
 _Function_: Represents the hash tag name
* childCut

 _Type_ : Boolean
 
 _Function_ : Child Cut field of the particular node accounts to true if any of its child node has been
removed in the past and false if all its child nodes are intact or the tree rooted at this node is inserted
into the root level of the Fibonacci Heap.
* child

 _Type_ : MaxFibonacciHeapNode
 
 _Function_: pointer to the node's child node
* parent

 _Type_ : MaxFibonacciHeapNode
 
 _Function_ : pointer to the node's parent node. This value is null if the node is at the root level of the
* Fibonacci Heap
* leftSibling & rightSibling

  _Type_ : MaxFibonacciHeapNode
  
  _Function_ : pointer to the node's left and right siblings which forms a part of the doubly circular
linked list.

**HashTagCounterManager.java**

This class manages the functioning of the max Fibonacci heap by calling the insert, increase key
functions on it.

**Instance variables are:**
* The object of Max Fibonacci Heap
* Hash Table object which stores all the hash tags names as key and node references as value.
* Hashtable<String,MaxFibonacciHeapNode> hashTagMap

* Output file name
 _Type_ : string
**Methods defined in the above class:**
* public IMaxFibonacciHeap processLine(String lineToBeProcessed)
_Return type_ : IMaxFibonacciHeap

_Parameters_: String lineToBeProcessed

This method takes the string "linetoBeProcessed" as input from the main method and
split in on the basis of space character. If the length of string array formed after split is
more than one then it performs the insert or increase key function else goes for remove
max, multiple number of times equal to the integer value obtained from the line.
Before insert operation it checks if the hash tag is present in the HashTable. If it is
present then calls the insertNewHashTagIntoHeap () method which insert a new node at
the root level, else it just calls updateHashTagFrequency() which does the increase key
operation.

When the remove max returns the maximum node reference then it saves them inside an
ArrayList which is later used to insert them back into the heap once all the remove max
operation has completed.

* public void insertIntoMap (String hashTag, MaxFibonacciHeapNode
newlyInsertedTag)
_Return type_ : void

_Parameters_: String hashTag, MaxFibonacciHeapNode newlyInsertedTag

This function inserts the node reference against the hash tag's name in the Hash table .
* public void writeTopHashTagsInFile(String lineToPrint)
_Return type_ : void

_Parameters_: String lineToPrint

The above method writes the lineToPrint into the output_file.txt file which contains the N
maximum hash tag names. It outputs a single line for each query string that is
encountered.

**MaxFibonacciHeap.java**

This class defines the implementation for all the important functions of Max Fibonacci Heap.
**Instance Variables:**
* rootNode

 _Type_ : MaxFibonacciHeapNode
 
 _Function_: Points to a node at the root level of the circular doubly linked list.
 
* maximumHashTagNode

 _Type_: MaxFibonacciHeapNode
 
 _Function_: Points to the node having the maximum frequency.
* totalNodes

 _Type_: Integer
 
_Function_: keeps a count of the total number of nodes in the tree.

