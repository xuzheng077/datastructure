package homework6;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Stack;

/**
 * This class is the BST class.
 * <p>
 * Andrew ID: xuzheng
 *
 * @param <T> Generic type
 * @author Xu Zheng
 */
public class BST<T extends Comparable<T>> implements Iterable<T>, BSTInterface<T> {
    /**
     * root of BST.
     */
    private Node<T> root;
    /**
     * comparator used to compare nodes.
     */
    private Comparator<T> comparator;

    /**
     * Default constructor.
     */
    public BST() {
        this(null);
    }

    /**
     * constructor with a comparator.
     *
     * @param comp comparator
     */
    public BST(Comparator<T> comp) {
        comparator = comp;
        root = null;
    }

    /**
     * get comparator.
     *
     * @return the comparator
     */
    public Comparator<T> comparator() {
        return comparator;
    }

    /**
     * get root.
     *
     * @return the root
     */
    public T getRoot() {
        if (root == null) {
            return null;
        }
        return root.data;
    }

    /**
     * get the height of BST.
     *
     * @return height of BST
     */
    public int getHeight() {
        //root is null
        if (root == null) {
            return 0;
        }
        //call helper on left and right
        int leftHeight = getHeightHelper(root.left);
        int rightHeight = getHeightHelper(root.right);
        //return height of higher subtree
        return Math.max(leftHeight, rightHeight);
    }

    /**
     * getHeight helper using recursion.
     *
     * @param node one node
     * @return height of the subtree with the param node as the root
     */
    private int getHeightHelper(Node<T> node) {
        //base case
        if (node == null) {
            return 0;
        }
        //recursion
        int leftHeight = getHeightHelper(node.left);
        int rightHeight = getHeightHelper(node.right);
        //return max
        return Math.max(leftHeight, rightHeight) + 1;
    }

    /**
     * get the number of nodes in the BST.
     *
     * @return number of nodes
     */
    public int getNumberOfNodes() {
        return getNumberOfNodesHelper(root);
    }

    /**
     * helper method for getNumberOfNodes.
     *
     * @param node current node
     * @return number of node with current node as root
     */
    private int getNumberOfNodesHelper(Node<T> node) {
        //base case
        if (node == null) {
            return 0;
        }
        //call recursion on left and right
        int numberOfNodeLeft = getNumberOfNodesHelper(node.left);
        int numberOfNodeRight = getNumberOfNodesHelper(node.right);
        //counting current level, return
        return numberOfNodeLeft + numberOfNodeRight + 1;
    }

    /**
     * Given the value (object), tries to find it.
     *
     * @param toSearch Object value to search
     * @return The value (object) of the search result. If not found, null.
     */
    @Override
    public T search(T toSearch) {
        //check input
        if (toSearch == null) {
            return null;
        }
        return searchHelper(root, toSearch);
    }

    /**
     * helper method for search.
     *
     * @param node     current node
     * @param toSearch Object value to search
     * @return return the Object value found, null if not found
     */
    private T searchHelper(Node<T> node, T toSearch) {
        //base cases
        //if node is null
        if (node == null) {
            return null;
        }
        //recursive call
        if (comparator == null) {
            if (node.data.compareTo(toSearch) == 0) {
                //if found
                return node.data;
            } else if (node.data.compareTo(toSearch) < 0) {
                //go right
                return searchHelper(node.right, toSearch);
            } else {
                //go left
                return searchHelper(node.left, toSearch);
            }
        } else {
            if (comparator.compare(node.data, toSearch) == 0) {
                //if found
                return node.data;
            } else if (comparator.compare(node.data, toSearch) < 0) {
                //go right
                return searchHelper(node.right, toSearch);
            } else {
                //go left
                return searchHelper(node.left, toSearch);
            }
        }
    }

    /**
     * Inserts a value (object) to the tree.
     * No duplicates allowed.
     *
     * @param toInsert a value (object) to insert into the tree.
     */
    @Override
    public void insert(T toInsert) {
        //check input
        if (toInsert == null) {
            return;
        }

        Node<T> newNode = new Node<T>(toInsert);
        // empty tree
        if (root == null) {
            root = newNode;
            return;
        }
        //call helper
        insertHelper(root, null, newNode, true);
    }

    /**
     * Inserts a value (object) to the tree.
     * No duplicates allowed.
     *
     * @param newNode     a new node contains the value (object) to insert into the tree.
     * @param current     current node
     * @param isLeftChild is current a left child of parent
     * @param parent      parent node
     */
    private void insertHelper(Node<T> current, Node<T> parent, Node<T> newNode, boolean isLeftChild) {
        //base case
        if (current == null) {
            if (isLeftChild) {
                parent.left = newNode;
            } else {
                parent.right = newNode;
            }
            return;
        }
        parent = current;
        //recursion
        if (comparator == null) {
            if (current.data.compareTo(newNode.data) == 0) {
                //if found the same value
                return;
            } else if (current.data.compareTo(newNode.data) < 0) {
                //go right
                insertHelper(current.right, parent, newNode, false);
            } else {
                //go left
                insertHelper(current.left, parent, newNode, true);
            }
        } else {
            if (comparator.compare(current.data, newNode.data) == 0) {
                //if found the same value
                return;
            } else if (comparator.compare(current.data, newNode.data) < 0) {
                //go right
                insertHelper(current.right, parent, newNode, false);
            } else {
                //go left
                insertHelper(current.left, parent, newNode, true);
            }
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new BSTIterator();
    }

    /**
     * BST Iterator class.
     */
    private class BSTIterator implements Iterator<T> {
        /**
         * Use a stack to traverse iteratively.
         */
        private Stack<Node<T>> stack;

        /**
         * Constructor.
         */
        BSTIterator() {
            stack = new Stack<>();
            Node<T> node = root;
            //push left node recursively
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
        }

        /**
         * hasNext method.
         *
         * @return true if iterator has next, false otherwise
         */
        @Override
        public boolean hasNext() {
            return !stack.empty();
        }

        /**
         * get next element.
         *
         * @return next element
         */
        @Override
        public T next() {
            if (!hasNext()) {
                return null;
            }
            Node<T> node = stack.pop();
            Node<T> next = node.right;
            //check if node has right subtree
            while (next != null) {
                //push the subtree's left node recursively
                stack.push(next);
                next = next.left;
            }
            return node.data;
        }
    }

    /**
     * Node class for BST.
     *
     * @param <T> Generic type
     */
    private static class Node<T> {
        /**
         * node data.
         */
        private T data;
        /**
         * left child.
         */
        private Node<T> left;
        /**
         * right child.
         */
        private Node<T> right;

        /**
         * Constructor.
         *
         * @param d data
         */
        Node(T d) {
            this(d, null, null);
        }

        /**
         * Constructor.
         *
         * @param d data
         * @param l left child
         * @param r right child
         */
        Node(T d, Node<T> l, Node<T> r) {
            data = d;
            left = l;
            right = r;
        }
    }

}
