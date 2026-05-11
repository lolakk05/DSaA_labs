package List8;

import java.util.Objects;

public class BST<T extends Comparable<T>>{
	private class Node{
		T value;
		Node left,right,parent;
		public Node(T v) {
			value=v;
		}
		public Node(T value, Node left, Node right, Node parent) {
			super();
			this.value = value;
			this.left = left;
			this.right = right;
			this.parent = parent;
		}
	}		
	private Node root=null;
    private int size = 0;

	public BST() {
	}

	public T getElement(T toFind) {
        Node foundNode = findSpecifiedNode(toFind);
        return foundNode != null ? foundNode.value : null;
	}

    private Node findSpecifiedNode(T elem) {
        Node current = this.root;
        while(current != null) {
            int compareResult = elem.compareTo(current.value);
            if(compareResult < 0) {
                current = current.left;
            } else if(compareResult > 0) {
                current = current.right;
            } else {
                return current;
            }
        }
        return null;
    }

	public T successor(T elem) {
		Node current = findSpecifiedNode(elem);
        if(current == null) {
            return null;
        }
        if(current.right != null) {
            current = current.right;
            while(current.left != null) {
                current = current.left;
            }
            return current.value;
        } else {
            while(current.parent != null && current == current.parent.right) {
                current = current.parent;
            }
            return current.parent !=  null ? current.parent.value : null;
        }
	}


	public String toStringInOrder() {
		StringBuilder inOrder = new StringBuilder();
        inOrderTraversal(root, inOrder);
        if(size() == 0) {
            return " ";
        }
		return inOrder.substring(0, inOrder.length() - 2);
	}

    public void inOrderTraversal(Node node, StringBuilder inOrder) {
        if(node == null) {
            return;
        }

        inOrderTraversal(node.left, inOrder);
        inOrder.append(node.value).append(", ");
        inOrderTraversal(node.right, inOrder);
    }

	public String toStringPreOrder() {
		StringBuilder preOrder = new StringBuilder();
        preOrderTraversal(root, preOrder);
        if(size() == 0) {
            return " ";
        }
		return preOrder.substring(0, preOrder.length() - 2);
	}

    public void preOrderTraversal(Node node, StringBuilder preOrder) {
        if(node == null) {
            return;
        }

        preOrder.append(node.value).append(", ");
        preOrderTraversal(node.left, preOrder);
        preOrderTraversal(node.right, preOrder);
    }

	public String toStringPostOrder() {
		StringBuilder postOrder = new StringBuilder();
        postOrderTraversal(root, postOrder);
        if(size() == 0) {
            return " ";
        }
		return postOrder.substring(0, postOrder.length() - 2);
	}

    public void postOrderTraversal(Node node, StringBuilder postOrder) {
        if(node == null) {
            return;
        }

        postOrderTraversal(node.left, postOrder);
        postOrderTraversal(node.right, postOrder);
        postOrder.append(node.value).append(", ");
    }


	public boolean add(T elem) {
        if (root == null) {
            root = new Node(elem);
            size++;
            return true;
        }

        Node current = root;
        while (current != null) {
            int compareResult = elem.compareTo(current.value);
            if (compareResult == 0) {
                return false;
            }
            if (compareResult < 0) {
                if (current.left == null) {
                    current.left = new Node(elem, null, null, current);
                    size++;
                    return true;
                }
                current = current.left;
            } else {
                if (current.right == null) {
                    current.right = new Node(elem, null, null, current);
                    size++;
                    return true;
                }
                current = current.right;
            }
        }
        return false;
    }

	public T remove(T value) {
		Node toRemove = findSpecifiedNode(value);
        if(toRemove == null) {
            return null;
        }

        T removedValue = toRemove.value;

        if(toRemove.left != null && toRemove.right != null) {
            Node succesor = findSpecifiedNode(successor(removedValue));
            toRemove.value = succesor.value;
            removeNode(succesor);
        } else {
            removeNode(toRemove);
        }

        size--;
        return removedValue;
	}

    private void removeNode(Node node) {
        Node child = (node.left != null) ? node.left : node.right;
        if(node.parent == null) {
            root = child;
        } else if(node == node.parent.left) {
            node.parent.left = child;
        } else {
            node.parent.right = child;
        }

        if(child != null) {
            child.parent = node.parent;
        }
    }
	
	public void clear() {
		this.root = null;
	}

	public int size() {
		return this.size;
	}

}
