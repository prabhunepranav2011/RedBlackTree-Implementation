
// tree.java
// demonstrates binary tree
// to run this program: C>java TreeApp
import java.io.*;
import java.util.*; // for Stack class
////////////////////////////////////////////////////////////////

class Node {
	public int iData; // data item (key)
	public Node leftChild; // this node’s left child
	public char color;
	public Node rightChild; // this node’s right child

	public void displayNode() // display ourself
	{
		System.out.print('{');
		System.out.print(iData);
		System.out.print(", ");
		System.out.print(color);
		System.out.print("} ");
	}
} // end class Node
////////////////////////////////////////////////////////////////

class Tree {
	private Node root; // first node of tree
// -------------------------------------------------------------

	public Tree() // constructor
	{
		root = null;
		// root.color = 'b';
	} // no nodes in tree yet
//-------------------------------------------------------------

	public void insert(int id) {
		Node newNode = new Node();
		Node localParent;
		newNode.iData = id;
		newNode.color = 'r';
		if (root == null) {
			root = newNode;
			newNode.color = 'b';
		} else {
			Node current = root;
			while (true) {
				localParent = current;
				if (isLeftChild(current) && isRightChild(current)) {
					if (current.leftChild.color == 'r' && current.rightChild.color == 'r' && current.color == 'b') {
						flipColor(current);
						/* adjustAfterInsertion(current); */
						findRR(current);
					}
				}
				// stepC(current);
				if (id < current.iData) {
					current = current.leftChild;
					// stepC(current);
					if (current == null) {
						localParent.leftChild = newNode;
						// stepC(newNode);
						if (localParent.color == 'b')
							return;
						else
							adjustAfterInsertion(newNode);
						return;
					}
				} else {
					current = current.rightChild;
					// stepC(current);
					if (current == null) {
						localParent.rightChild = newNode;
						// stepC(newNode);
						if (localParent.color == 'b')
							return;
						else
							adjustAfterInsertion(newNode);
						return;
					}
				} // end else go right
			} // end while
		} // end else not root
		displayTree();
	} // end insert()
		// -------------------------------------------------------------

	public void adjustAfterInsertion(Node newNode) {
		Node localParent = FindParent(newNode);
		Node localGrandParent = FindParent(localParent);
		if (localParent != null && localGrandParent != null) 
		{
			if (newNode == localParent.leftChild && localParent == localGrandParent.leftChild) {
				changeColor(localParent);
				changeColor(localGrandParent);
				rotateRight(localGrandParent);
			}

			else if (newNode == localParent.rightChild && localParent == localGrandParent.rightChild) {
				changeColor(localParent);
				changeColor(localGrandParent);
				rotateLeft(localGrandParent);
			}

			else if (newNode == localParent.leftChild && localParent == localGrandParent.rightChild) {
				changeColor(newNode);
				changeColor(localGrandParent);
				rotateRight(localParent);
				rotateLeft(localGrandParent);
			}

			else if (newNode == localParent.rightChild && localParent == localGrandParent.leftChild) {
				changeColor(newNode);
				changeColor(localGrandParent);
				rotateLeft(localParent);
				rotateRight(localGrandParent);
			}
		}
		return;
	}

	// --------------------------------------------------------------
	public void stepC(Node conflict) {
		if (conflict != null) {
			Node localParent = FindParent(conflict);
			Node localGrandParent = FindParent(localParent);
			if (localParent != null && localParent.color == 'r' && conflict.color == 'r') 
			{
				if (conflict == localParent.leftChild && localGrandParent != null
						&& localParent == localGrandParent.leftChild) {
					changeColor(localParent);
					changeColor(localGrandParent);
					rotateRight(localGrandParent);
				}
				if (conflict == localParent.rightChild && localGrandParent != null
						&& localParent == localGrandParent.rightChild) {
					changeColor(localParent);
					changeColor(localGrandParent);
					rotateLeft(localGrandParent);
				}
				if (conflict == localParent.rightChild && localGrandParent != null
						&& localParent == localGrandParent.leftChild) {
					changeColor(conflict);
					changeColor(localGrandParent);
					rotateLeft(localParent);
					rotateRight(localGrandParent);
				}
				if (conflict == localParent.leftChild && localGrandParent != null
						&& localParent == localGrandParent.rightChild) {
					changeColor(conflict);
					changeColor(localGrandParent);
					rotateRight(localParent);
					rotateLeft(localGrandParent);
				}
			}
		}
		return;
	}

	// --------------------------------------------------------------
	private void findRR(Node current) {

		Node localparent = FindParent(current);
		if (localparent != null && localparent.color == 'r' && current.color == 'r')
			stepC(current);
		displayTree();
		return;
	}

	// --------------------------------------------------------------
	public boolean isRoot(Node p) {
		if (root == p)
			return true;
		else
			return false;
	}

	// --------------------------------------------------------------
	public Node FindParent(Node p) {
		return parentHelper(root, p);
	}

	private Node parentHelper(Node currentRoot, Node p) {
		if (isRoot(p) || currentRoot == null) {
			return null;
		} else {
			if (currentRoot.leftChild == p || currentRoot.rightChild == p)
				return currentRoot;
			else {
				if (currentRoot.iData < p.iData) {
					return parentHelper(currentRoot.rightChild, p);
				} else {
					return parentHelper(currentRoot.leftChild, p);
				}
			}
		}
	}
	// --------------------------------------------------------------
	public void changeColor(Node abc) {
		if (abc.color == 'b')
			abc.color = 'r';
		else
			abc.color = 'b';
		return;
	}

	// --------------------------------------------------------------
	public void flipColor(Node abc) {
		if (abc != root) {
			if (abc.color == 'r')
				abc.color = 'b';
			else
				abc.color = 'r';
		}
		if (abc.rightChild.color == 'r')
			abc.rightChild.color = 'b';
		else
			abc.rightChild.color = 'r';
		if (abc.leftChild.color == 'r')
			abc.leftChild.color = 'b';
		else
			abc.leftChild.color = 'r';
		return;
	}

	// --------------------------------------------------------------
	public void rotateRight(Node newNode) {
		Node localParent = FindParent(newNode);

		if (newNode == root) {
			root = newNode.leftChild;
			if (isRightChild(newNode.leftChild)) {
				root = newNode.leftChild;
				Node temp = newNode.leftChild.rightChild;
				root.rightChild = newNode;
				newNode.leftChild = temp;

			} else
			{
				root.rightChild = newNode;
				newNode.leftChild = null;
			}
		} else {

			if (!isRightChild(newNode.leftChild)) 
			{
				localParent.leftChild = newNode.leftChild;
				localParent.leftChild.rightChild = newNode;
				newNode.leftChild = null;
				
			} else {
				Node temp = newNode.leftChild.rightChild;
				localParent.rightChild = newNode.leftChild;
				newNode.leftChild = temp;				
				localParent.rightChild.rightChild = newNode;
				
			}
		}
	}

	// --------------------------------------------------------------
	public void rotateLeft(Node newNode) {

		Node localParent = FindParent(newNode);
	
		if (newNode == root) {
			root = newNode.rightChild;
			if (isLeftChild(newNode.rightChild)) {
				Node temp = newNode.rightChild.leftChild;
				root.leftChild = newNode;
				newNode.rightChild = temp;

			} else
			{
				root.leftChild = newNode;
				newNode.rightChild = null;
			}

		} else {
			if (!isLeftChild(newNode.rightChild)) {
				localParent.rightChild = newNode.rightChild;
				localParent.rightChild.leftChild = newNode;
				newNode.rightChild = null;			
			} else {
				Node temp = newNode.rightChild.leftChild;
				localParent.leftChild = newNode.rightChild;
				newNode.rightChild = temp;				
				localParent.leftChild.leftChild = newNode;		
			}
		}
	}
	// return localParent;

	// --------------------------------------------------------------
	public boolean isLeftChild(Node abc) {
		Node current = abc;
		if (current.leftChild != null)
			return true;
		else
			return false;
	}

	// --------------------------------------------------------------
	public boolean isRightChild(Node abc) {
		Node current = abc;
		if (current.rightChild != null)
			return true;
		else
			return false;
	}

	// --------------------------------------------------------------
	public void displayTree() {
		Stack globalStack = new Stack();
		globalStack.push(root);
		int nBlanks = 32;
		boolean isRowEmpty = false;
		System.out.println("......................................................");
		while (isRowEmpty == false) {
			Stack localStack = new Stack();
			isRowEmpty = true;
			for (int j = 0; j < nBlanks; j++)
				System.out.print(' ');
			while (globalStack.isEmpty() == false) {
				Node temp = (Node) globalStack.pop();
				if (temp != null) {
					System.out.print(temp.iData + " " + Character.toString(temp.color));
					localStack.push(temp.leftChild);
					localStack.push(temp.rightChild);
					if (temp.leftChild != null || temp.rightChild != null)
						isRowEmpty = false;
				} else {
					System.out.print("--");
					localStack.push(null);
					localStack.push(null);
				}
				for (int j = 0; j < nBlanks * 2 - 2; j++)
					System.out.print(' ');
			} // end while globalStack not empty
			System.out.println();
			nBlanks /= 2;
			while (localStack.isEmpty() == false)
				globalStack.push(localStack.pop());
		} // end while isRowEmpty is false
		System.out.println("......................................................");
	} // end displayTree()
		// -------------------------------------------------------------
} // end class Tree
	////////////////////////////////////////////////////////////////

class RedBlackTree {
	public static void main(String[] args) throws IOException {
		int value;
		Tree theTree = new Tree();
		/*theTree.insert(50);
		theTree.insert(25);
		theTree.displayTree();
		theTree.insert(75);
		theTree.displayTree();
		theTree.insert(12);
		theTree.displayTree();
		theTree.insert(37);
		theTree.displayTree();
		theTree.insert(43);
		theTree.displayTree();
		theTree.insert(30);
		theTree.displayTree();
		theTree.insert(33);
		theTree.displayTree();
		theTree.insert(87);
		theTree.displayTree();
		theTree.insert(93);
		theTree.displayTree();
		theTree.insert(97);
		theTree.displayTree();*/
		while(true)
		{
			System.out.print("Enter first letter of show or insert ");
			int choice = getChar();
			switch (choice) {
			case 's':
				theTree.displayTree();
				break;
			case 'i':
				System.out.print("Enter value to insert: ");
				value = getInt();
				theTree.insert(value);
				break;
			}
		} // end while
	} // end main()
		// -------------------------------------------------------------

	public static String getString() throws IOException
	{
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		String s = br.readLine();
		return s;
	}

	// -------------------------------------------------------------
	public static char getChar() throws IOException {
		String s = getString();
		return s.charAt(0);
	}
	// -------------------------------------------------------------
	public static int getInt() throws IOException {
		String s = getString();
		return Integer.parseInt(s);
	}
	// -------------------------------------------------------------
} // end class TreeApp
	////////////////////////////////////////////////////////////////
