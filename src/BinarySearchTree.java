
/**Name: Zimu Cui.  zimucui@gmail.com
 *Class: COSI 21A, Fall 2015
 *Programming Assignment #2, 10/28/2015
 *Description: This program forms a BinarySearchTree that contains some elements
 */
public class BinarySearchTree<E extends Comparable<E>> {
	private E data;
	private BinarySearchTree<E> parent;
	private BinarySearchTree<E> left;
	private BinarySearchTree<E> right;
	private int balancefactor;
	
	
	/**
	 * This is the constructor 
	 * O(1);
	 * 
	 */
	public BinarySearchTree(E e){
		this.data = e;
		this.parent = null;
		this.left = null;
		this.right = null;
		this.balancefactor = 0;
		
	}
	
	/**
	 * This method returns true if the tree has a left child
	 * O(1)
	 */
	public boolean hasLeft() {
		if (this.left != null) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * This method returns true if the tree has a right child
	 * O(1)
	 */
	public boolean hasRight() {
		if (this.right != null) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * This method returns true if the tree has no right and no left child
	 * O(1)
	 */
	public boolean isLeaf() {
		if (this.left == null && this.right == null) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * This method returns true if the tree has no data in it
	 * O(1)
	 */
	public boolean isEmpty() {
		if (isRoot() && this.data == null && isLeaf()) {         
			return true;
		} else {
			return false;
		}
	}
	/**
	 * This method returns true if the tree has no parent
	 * O(1)
	 */
	public boolean isRoot() {
		if (this.parent == null) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * This method returns true if the tree has a right child
	 * O(1)
	 */
	public boolean isLeftChild() {
		if (this == this.parent.left) {   
			return true;
		} else {
			return false;
		}
	}
	/**
	 * This method returns true if the tree has a left child
	 * O(1)
	 */
	public boolean isRightChild() {
		if (this == this.parent.right) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * This method returns true if the tree has parent
	 * O(1)
	 */
	public boolean hasParent() {
		if (this.parent != null) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * This method returns the node that contains e, or null. And it is the same thing as search( ) method
	 * O(log n)
	 */
	public BinarySearchTree<E> findNode(E e) {       
		return search(e);
	}
	/**
	 * This method returns the node with the minimum value in the tree.
	 * O(log n)
	 */
	public BinarySearchTree<E> findMin() {
		BinarySearchTree<E> v = this;
		
		while (v.left != null) {
			v = v.left;
		}
		
		return v;
	}
	/**
	 * This method returns the node that is the successor to the current node.
	 * O(log n)
	 */
	public BinarySearchTree<E> findSuccessor() {
		BinarySearchTree<E> v = this;
		if (v.right != null) {
			return v.right.findMin();
		} else {
			BinarySearchTree<E> w = v.parent;
			while (w != null && v == w.right) {
				v = w;
				w = w.parent;
			}
			return w;
		}
	} 
	/**
	 * This method adds a root containing e to the tree if the tree is empty (throw exception if it isn¡¯t).
	 * O(1)
	 */
	public BinarySearchTree<E> addRoot(E e) {
		if (isEmpty()) {
			this.data = e;
			return this;
		} else {
			throw new IllegalArgumentException("we already have a root");
		}
	}
	/**
	 * This method  Starting from the root,
	 * walk down the tree, searching for the correct location for e.
	 * If the tree already contains a node in that location, throw an exception.
	 * Otherwise, insert a node containing into the tree.
	 * Lastly, return the newly updated tree
	 * O(log n)
	 */
	public BinarySearchTree<E> insert(E e) {
		BinarySearchTree<E> v = null;
		BinarySearchTree<E> w = this;  //refer to the root node
		BinarySearchTree<E> temp = new BinarySearchTree<E>(e);
		while (w != null) {
			v = w;
			if ( e.compareTo((E) w.data) == -1) {
				w = w.left;
			} else if (e.compareTo((E) w.data) == 1) {
				w = w.right;
			} else {
				throw new IllegalArgumentException("We already have this node");
			}
		}
		temp.parent = v;
		
		if (v == null) {      
			return temp;
		} else {          //when v is not null
			if (e.compareTo((E) v.data) == -1) {
				
				v.left = temp;	   //insert e
			    int n = v.balanceFactor();      //immediately check v's balancefactor to see whether we need to rotate or not
			    
			    BinarySearchTree<E> firstturn = v.parent;
			    
			    while (firstturn != null) {
			    	int x = firstturn.balanceFactor();           //change balancefactor for every ancestor of v's parent
			    	firstturn = firstturn.parent;
			    }
			    // now, every node's balancefactor above temp is changed

			    while (!v.isRoot() && n != 2 && n != -2) {   //skip nodes where it has parent and balancefactor is not 2 or not -2!!!
			    	v = v.parent;
			    	n = v.balanceFactor();
			    }
			    
			    
			    if (v.isRoot()) {
			    	if (n == 2 || n == -2) {            //when v is root and its balancefactor is 2 or -2
			    		v.balance();                
			    	}
			    } else {          //when v is not root and has balancefactor of 2 or -2
			    
			    	BinarySearchTree<E> secondturn = v.parent;
			    	
			    	v.balance();    //balance v
			    	
			    	 while (secondturn != null) {
					    	int y = secondturn.balanceFactor();           //change balancefactor for every ancestor of v's parent
					    	secondturn = secondturn.parent;
					    }
			    }
				// now, after two rounds of change, all balancefactors should be ok.
			} else {          //when temp is added to the right side of v

				v.right = temp;	   //insert e
			    int n = v.balanceFactor();
			    
			    BinarySearchTree<E> firstturn = v.parent;
			    while (firstturn != null) {
			    	int x = firstturn.balanceFactor();           //change balancefactor for every ancestor of v's parent
			    	firstturn = firstturn.parent;
			    }
			    

			    while (!v.isRoot() && (n != 2 && n != -2)) {   
			    	v = v.parent;
			    	n = v.balanceFactor();
			    }
			    
			    if (v.isRoot()) {
			    	if (n == 2 || n == -2) {
			    		v.balance();
			    	}
			    } else {          //when v is not root and has balancefactor of 2 or -2
			    
			    	BinarySearchTree<E> secondturn = v.parent;
			    	
			    	v.balance();
			    	
			    	 while (secondturn != null) {
					    	int x = secondturn.balanceFactor();           //change balancefactor for every ancestor of v's parent
					    	secondturn = secondturn.parent;
					    }
			    	}
				}
			
				while (v.parent != null) {      //return back to the root node
					v = v.parent;
				}
			return v;
		}
	}
	
	
	/**
	 * This method returns the node containing e from the tree. Returns null if nothing is stored at that location. 
	 * This method needs to call an auxiliary method called AVLsearch
	 * O(log n)
	 */
	public BinarySearchTree<E> search(E e) { 
		BinarySearchTree<E> v = this;
		return AVLsearch(e, v);
	}
	
	/**
	 * This method completes search(E e) method.
	 * O(log n)
	 */
	public BinarySearchTree<E> AVLsearch(E e, BinarySearchTree<E> v) {
		if (v == null || e.compareTo((E) v.data) == 0) {
			return v;
		}
		if (e.compareTo((E) v.data) == -1) {
			return AVLsearch(e, v.left);
		} else {
			return AVLsearch(e, v.right);
		}
	}
			
	
	/**
	 * This method removes the node containing e from the tree. Throws an exception if there is no node containing e. 
	 * It needs to call an auxiliary method
	 * O(log n)
	 */
	public BinarySearchTree<E> delete(E e) {
		if (search(e) == null) {
			throw new IllegalArgumentException("we don't have this node at all");
		} else {
			return removeFoundNode(search(e));
		}
	}
	
	/**
	 * This method complete delete(E e) method. I write this because I want delete(E e) method to be clear and easy to understand.
	 *O(log n)
	 */
	public BinarySearchTree<E> removeFoundNode(BinarySearchTree<E> z) {
		BinarySearchTree<E> y;
		BinarySearchTree<E> x;
		BinarySearchTree<E> last = z.parent;     //use lastly to trace back to the root;
		
		if (z.left == null || z.right == null) {
			y = z;
		} else {
			y = z.findSuccessor();               //note!!!!!!!!!!!!!
		}
		
		if (y.left != null) {
			x = y.left;
		} else {
			x = y.right;
		}
		
		if (x != null) {
			x.parent = y.parent;
		}
		
		if (y.parent == null) {
			return x;      //now, root becomes x
		} else {
			if (y.isLeftChild()) {
				y.parent.left = x;          //remove y from the tree
				BinarySearchTree<E> temp = y.parent;
				int n = temp.balanceFactor();
				
				BinarySearchTree<E> firstturn = temp.parent;
				   while (firstturn != null) {
					   int number = firstturn.balanceFactor();           //change balancefactor for every ancestor of temp's parent
					   firstturn = firstturn.parent;
				   }
				
				while (!temp.isRoot()) {
					if (n == 2 || n ==-2) {     //the case when we need to rotate
						BinarySearchTree<E> secondrun = temp.parent;
						BinarySearchTree<E> tempparent = temp.parent;
						
						temp.balance();

						 
						while(secondrun != null) {                //change the balancefactor of temp's parent
							int number = secondrun.balanceFactor();
							secondrun = secondrun.parent;
						}
						
						temp = tempparent;     //renew temp to its original parent
						n = temp.balanceFactor(); //renew  n;
					} else {                     //in this case we do not need to rotate
						temp = temp.parent;
						n = temp.balanceFactor();
					}
				}
			
				//now, temp reaches the root
				n = temp.balanceFactor();
				if (n == 2 || n == -2) {              //the case where the root needs to be rotated
					temp.balance();
				}

			} else {
				y.parent.right = x;
				BinarySearchTree<E> temp = y.parent;
				int n = temp.balanceFactor();
				
				BinarySearchTree<E> firstturn = temp.parent;
				   while (firstturn != null) {
					   int number = firstturn.balanceFactor();           //change balancefactor for every ancestor of temp's parent
					   firstturn = firstturn.parent;
				   }
				
				while (!temp.isRoot()) {
					if (n == 2 || n ==-2) {     //the case when we need to rotate
						BinarySearchTree<E> secondrun = temp.parent;
						BinarySearchTree<E> tempparent = temp.parent;
						
						temp.balance();

						 
						while(secondrun != null) {                //change the balancefactor of temp's parent
							int number = secondrun.balanceFactor();
							secondrun = secondrun.parent;
						}
						
						temp = tempparent;     //renew temp to its original parent
						n = temp.balanceFactor(); //renew  n;
					} else {                     //in this case we do not need to rotate
						temp = temp.parent;
						n = temp.balanceFactor();
					}
				}
			
				//now, temp reaches the root
				n = temp.balanceFactor();
				if (n == 2 || n == -2) {              //the case where the root needs to be rotated
					temp.balance();
				}
			}
		}

		if (((Comparable<E>) y.data).compareTo((E) z.data) != 0) {    //which means y and z are not the same node
			z.data = y.data;
		}
		
		while (last.parent != null) {
			last = last.parent;               //trace back the the root
		}
		
		return last;
			
	}
		
	
	/**
	 * This method returns an integer that is the number of nodes currently in the tree. The method is recursive.
	 * O(1)
	 */
	public int size() {
		if (isEmpty()) {
			return 0;
		} else {
			return (this.left.size() + this.right.size() + 1);
		}
	}
	
	/**
	 * This method returns an integer that is the balancefactor of the tree.
	 * O(1)
	 */
	public int balanceFactor() {
		int l;
		int r;
		if (this.left == null) {
			l = -1;
		} else {
			l = this.left.height();
		}
		if (this.right == null) {
			r = -1;
		} else {
			r = this.right.height();
		}
		int number = l - r;
		this.balancefactor = number;
		return number;
	}
	
	/**
	 * This method returns the height of the tree
	 * O(1)
	 */
	public int height() {
		if (isLeaf()) {
			return 0;
		} else if (this.right == null) {
			return (1 + this.left.height());
		} else if (this.left == null) {
			return (1 + this.right.height());
		} else if (this.left != null && this.right != null) {
			return (1 + Math.max(this.left.height(), this.right.height()));
		}
		return -1; 
	}
	
	/**
	 * This method returns the depth of the tree
	 * O(1)
	 */
	public int depth() {
		if (this.parent == null) {
			return 0;
		} else {
			return (1 + this.parent.depth());
		}
	}
	
	/**
	 * This method will judge what kind of rotation we need to do for the tree.
	 * It contains two new auxiliary methods which are doubleRotateLeftRight() and doubleRotateRightLeft().
	 * O(1)
	 */
	public void balance() {
		if (balanceFactor() == 2) {
			if (this.left.balancefactor < 0) {
				doubleRotateLeftRight();
			} else {
				rightRotation();
			}
		} else if (balanceFactor() == -2) {
			if (this.right.balancefactor > 0) {
				doubleRotateRightLeft();
			} else {
				leftRotation();
			}
		}	
	}
	
	/**
	 * This method rotates the tree to the right around the calling node and returns the newly updated tree.
	 * O(1)
	 */
	public void rightRotation() {
		BinarySearchTree<E> v = this;
		BinarySearchTree<E> u = this.parent;
		BinarySearchTree<E> w = this.left;
		BinarySearchTree<E> x = w.right;
		
		w.right = v;
		v.parent = w;
		v.left = x;
		
		if (x != null) {
			x.parent = v;
		}
		
		if (u != null) {
			if (u.left == v) {
				u.left = w;
			} else {
				u.right = w;
			}
		}
		w.parent = u;
		
		int n1 = v.balanceFactor();      //recheck balancefactors;
		int n2 = w.balanceFactor();
	}
	
	/**
	 * This method rotates the tree to the left around the calling node and returns the newly updated tree.
	 * O(1)
	 */
	public void leftRotation() {
		BinarySearchTree<E> w = this;
		BinarySearchTree<E> u = this.parent;
		BinarySearchTree<E> v = this.right;
		BinarySearchTree<E> x = v.left;
		
		w.right = x;
		if (x != null) {
			x.parent = w;
		} 
		v.left = w;
		w.parent = v;
		
		if (u != null) {
			if (u.right == w) {
				u.right = v;
			} else {
				u.left = v;
			}
		}
		v.parent = u;
		
		int n1 = v.balanceFactor();      //recheck balancefactors;
		int n2 = w.balanceFactor();
	}
	
	
	
	/**
	 * This method first rotates the tree to the left around the left child of the calling node 
	 * and rotate the tree to the right around the calling node
	 * and finally returns the newly updated tree.
	 * O(1)
	 */
	public void doubleRotateLeftRight() {
		this.left.leftRotation();
		rightRotation();       //right rotate itself in this step
	}
	
	/**
	 * This method first rotates the tree to the right around the right child of the calling node 
	 * and rotate the tree to the left around the calling node
	 * and finally returns the newly updated tree.
	 * O(1)
	 */
	public void doubleRotateRightLeft() {
		this.right.rightRotation();
		leftRotation();       //right rotate itself in this step
	}
	
	/**
	 * This method returns the String generated from a post-order traversal.
	 * O(log n)
	 */
	public String postorder() {
		if (this.hasLeft() && this.hasRight()) {
			return this.left.postorder() + this.right.postorder() + this.data.toString();
		}
		if (this.hasLeft() && !this.hasRight()) {
			return this.left.postorder() + this.data.toString();
		}
		if (!this.hasLeft() && this.hasRight()) {
			return this.right.postorder() + this.data.toString();
		} else {
			return this.data.toString();
		}
	}
}


