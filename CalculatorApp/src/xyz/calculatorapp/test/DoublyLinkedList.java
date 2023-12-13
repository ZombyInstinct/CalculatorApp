package xyz.calculatorapp.test;

public class DoublyLinkedList {
	
	//Node definition
	class Node
	{
		String operation;	 // mathematical operator
		double leftVal; 	 // left side value
		Node next; 			 // next node
		Node prev; 			 // prev node
		
		//default constructor
		public Node()
		{
			this.operation = null;
			this.leftVal = 0;
			this.next = null;
			this.prev = null;
		}
		//constructor
		public Node(String op, double leftVal)
		{
			this.operation = op;
			this.leftVal = leftVal;
		}
	}
	
	// head and tail nodes
	Node head, tail = null;
	
	//adding a new node to the end
	public void addNode(String op, double leftVal)
	{
		Node newNode = new Node(op, leftVal);
		
		if (head == null)
		{
			head = tail = newNode;
		}
		else
		{
			tail.next = newNode;
			newNode.prev = tail;
			tail = newNode;
		}
	}
	
	//removes node
	public void removeNode(Node node)
	{
		//head case
		if (node == head)
		{
			//if last node, empty head
			if (head.next == null)
			{
				head = null;
			}
			else //if not empty, set next node as head
			{
				head = head.next;
				head.prev = null;
			}
		}
		else if(node == tail) //tail case
		{
			tail = node.prev;
			tail.next = null;
		}
		else //normal case
		{
			node.prev.next = node.next;
		}
	}

	public void replaceLatestOperator(String op)
	{
		tail.operation = op;
	}
	
	//determine how to do this
	public double calculate()
	{
		Node current = head;
		double result = current.leftVal;
		
		//first we need to sift through the nodes to find any operation with x or /
		while(current != null)
		{
			Node next = current.next;
			
			if (current.operation == "x")
			{
				next.leftVal = current.leftVal * next.leftVal;
				removeNode(current);
			}
			else if(current.operation == "/")
			{
				next.leftVal = current.leftVal / next.leftVal; //TODO check for div by zero
				removeNode(current);
			}
			
			current = next;
		}
		
		current = head;
		
		while (current != null)
		{
			Node next = current.next;
			
			if (current.operation == "+")
			{
				next.leftVal = current.leftVal + next.leftVal;
				removeNode(current);
			}
			else if (current.operation == "-")
			{
				next.leftVal = current.leftVal - next.leftVal;
				removeNode(current);
			}
			
			current = next;
		}
		
		if (head.operation == "=")
		{
			result = head.leftVal;
			head = null;
			return result;
		}
		else return -1;
		
	}
	
	public void printList()
	{
		Node current = head;
		
		if (head == null)
		{
			System.out.println("empty");
		}
		else
		{
			while(current!=null)
			{
				System.out.print(current.leftVal + " " + current.operation + " ");
				current = current.next;
			}
		}
		System.out.println();
	}
	
}
