package homework3;

/**
 * This class is singly linked list implementing homework3.MyListInterface.
 * <p>
 * Andrew ID: xuzheng
 *
 * @author Xu Zheng
 */
public class SortedLinkedList implements MyListInterface {
    /**
     * reference to the first node.
     */
    private Node head;

    /**
     * no arg constructor.
     */
    public SortedLinkedList() {
        head = null;
    }

    /**
     * constructor which takes an array and add all it's elements.
     *
     * @param data unsorted input array
     */
    public SortedLinkedList(String[] data) {
        //call recursive constructor
        recursiveConstructor(data, 0);
    }

    /**
     * recursive constructor.
     * it increments the index by one at a time, call add() to add an element.
     * if reaches the end, return.
     *
     * @param data  unsorted data
     * @param index the index of data being added in current recursion call
     */
    private void recursiveConstructor(String[] data, int index) {
        //base case
        if (index == data.length) {
            return;
        }
        //add
        add(data[index]);
        //recursive call, add next element
        recursiveConstructor(data, index + 1);
    }

    /**
     * Inserts a new String.
     * Do not throw exceptions if invalid word is added (Gently ignore it).
     * No duplicates allowed and maintain the order in ascending order.
     *
     * @param value String to be added.
     */
    @Override
    public void add(String value) {
        //only add valid word
        if (validateWord(value)) {
            //if there is no duplicate
            if (!recursiveHasDup(head, value)) {
                //if head is null
                if (head == null) {
                    head = new Node(value, null);
                    return;
                }
                //less than head, put value before head
                if (head.data.compareTo(value) > 0) {
                    head = new Node(value, head);
                    return;
                }
                //recursive add
                recursiveAdd(head, head.next, value);
            }
        }
    }

    /**
     * check whether there is duplicate.
     *
     * @param node  current node
     * @param value value being added
     * @return true if there is duplicate, false otherwise
     */
    private boolean recursiveHasDup(Node node, String value) {
        //base case
        if (node == null) {
            return false;
        }
        if (node.data.equals(value)) {
            //find duplicate
            return true;
        }
        //recursive call, check next node
        return recursiveHasDup(node.next, value);
    }

    /**
     * recursively add.
     * check if the value is less than node.data, if so, put it between prev and node.
     * otherwise recurse to next node.
     *
     * @param prev  previous node
     * @param node  current node
     * @param value value being added
     */
    private void recursiveAdd(Node prev, Node node, String value) {
        //base case
        if (node == null) {
            prev.next = new Node(value, null);
            return;
        }
        //if less than node, put value before node
        if (node.data.compareTo(value) > 0) {
            prev.next = new Node(value, node);
            return;
        }
        //recursive call
        recursiveAdd(node, node.next, value);
    }

    /**
     * Checks the size (number of data items) of the list.
     *
     * @return the size of the list
     */
    @Override
    public int size() {
        return recursiveSize(head);
    }

    /**
     * recursively calculate size.
     *
     * @param node current node
     * @return size starting from current node
     */
    private int recursiveSize(Node node) {
        //base case
        if (node == null) {
            return 0;
        }
        //recursive call
        //1 is counting current node, recursiveSize(node.next) is the number of node behind it
        return 1 + recursiveSize(node.next);
    }

    /**
     * Displays the values of the list.
     */
    @Override
    public void display() {
        System.out.print("[");
        recursiveDisplay(head);
        System.out.println("]");
    }

    /**
     * recursively display.
     *
     * @param node the current node
     */
    private void recursiveDisplay(Node node) {
        //base case
        if (node == null) {
            return;
        }
        //append current node's data
        System.out.print(node.data);
        //if next is null, return
        if (node.next == null) {
            return;
        }
        //otherwise, append ", " and continue recursion
        System.out.print(", ");
        recursiveDisplay(node.next);
    }

    /**
     * Returns true if the key value is in the list.
     *
     * @param key String key to search
     * @return true if found, false if not found
     */
    @Override
    public boolean contains(String key) {
        return recursiveContains(head, key);
    }

    /**
     * check whether a key exists.
     *
     * @param node current node
     * @param key  key being checked
     * @return true if the key exists, false otherwise
     */
    private boolean recursiveContains(Node node, String key) {
        //base case
        if (node == null) {
            return false;
        }
        if (node.data.equals(key)) {
            //find the key
            return true;
        }
        //recursive call
        return recursiveHasDup(node.next, key);
    }

    /**
     * Returns true is the list is empty.
     *
     * @return true if it is empty, false if it is not empty
     */
    @Override
    public boolean isEmpty() {
        //if head is null, it's empty
        return head == null;
    }

    /**
     * Removes and returns the first String object of the list.
     *
     * @return String object that is removed. If the list is empty, returns null
     */
    @Override
    public String removeFirst() {
        if (isEmpty()) {
            return null;
        }
        String temp = head.data;
        head = head.next;
        return temp;
    }

    /**
     * Removes and returns String object at the specified index.
     *
     * @param index index to remove String object
     * @return String object that is removed
     * @throws RuntimeException for invalid index value (index < 0 || index >= size())
     */
    @Override
    public String removeAt(int index) {
        //invalid index, throw RuntimeException
        if (index < 0 || index >= size()) {
            throw new RuntimeException();
        }
        //if index == 0, removeFirst
        if (index == 0) {
            return removeFirst();
        }
        //recursion
        return recursiveRemoveAt(head, head.next, index - 1);
    }

    /**
     * recursive removeAt helper.
     *
     * @param prev  previous node
     * @param node  current node
     * @param index index where node is being removed
     * @return data of the node removed
     */
    private String recursiveRemoveAt(Node prev, Node node, int index) {
        //base case
        if (index == 0) {
            //store the data temporarily
            String temp = node.data;
            //remove the current node
            prev.next = node.next;
            //return the data
            return temp;
        }
        //recursive call
        return recursiveRemoveAt(node, node.next, index - 1);
    }

    /**
     * nested Node class.
     */
    private static class Node {
        /**
         * node's data.
         */
        private String data;
        /**
         * reference to next node.
         */
        private Node next;

        /**
         * constructor for Node.
         *
         * @param d data
         * @param n reference to next node
         */
        Node(String d, Node n) {
            data = d;
            next = n;
        }
    }

    /**
     * this method validate a word before adding it.
     *
     * @param word the word being added
     * @return true if the word only contains letters from a-z and A-Z, otherwise false
     */
    private boolean validateWord(String word) {
        if (word == null) {
            return false;
        }
        return word.matches("[a-zA-Z]+");
    }
}
