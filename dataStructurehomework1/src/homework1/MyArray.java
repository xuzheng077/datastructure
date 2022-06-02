package homework1;

/**
 * This class is self-created array called homework1.homework3.MyArray.
 * <p>
 * Andrew ID: xuzheng
 *
 * @author Xu Zheng
 */
public class MyArray {

    /**
     * default capacity.
     */
    private static final int DEFAULT_CAPACITY = 10;

    /**
     * used when initial capacity is 0.
     */
    private static final String[] EMPTY_DATA = {};

    /**
     * store the words.
     */
    private String[] data;

    /**
     * number of words stored.
     */
    private int size;


    /**
     * this is no-arg constructor.
     * worst-case running time complexity: O(1)
     */
    public MyArray() {
        data = new String[DEFAULT_CAPACITY];
    }

    /**
     * this is the constructor with initialCapacity parameter.
     * worst-case running time complexity: O(n)
     *
     * @param initialCapacity initial capacity
     */
    public MyArray(int initialCapacity) {
        if (initialCapacity > 0) {
            data = new String[initialCapacity];
        } else if (initialCapacity == 0) {
            data = EMPTY_DATA;
        } else {
            data = new String[DEFAULT_CAPACITY];
        }
    }

    /**
     * Add a word to homework1.homework3.MyArray.
     * worst-case running time complexity: O(n)
     *
     * @param text the word added
     */
    public void add(String text) {
        boolean isValid = validateWord(text);
        //if the word is valid, add it to homework1.homework3.MyArray, otherwise ignore it
        if (isValid) {
            if (size == data.length) {
                //data is full, need to double capacity
                doubleCapacity();
            }
            //add the word and increase size
            data[size++] = text;
        }
    }

    /**
     * this method searches a word in homework1.homework3.MyArray.
     * worst-case running time complexity: O(n)
     *
     * @param key the word to search
     * @return true if the word exists, false otherwise
     */
    public boolean search(String key) {
        if (!validateWord(key)) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (data[i].equals(key)) {
                return true;
            }
        }
        return false;
    }

    /**
     * get the number of words in homework1.homework3.MyArray.
     * worst-case running time complexity:O(1)
     *
     * @return size
     */
    public int size() {
        return this.size;
    }

    /**
     * get the capacity of homework1.homework3.MyArray.
     * worst-case running time complexity:O(1)
     *
     * @return capacity
     */
    public int getCapacity() {
        return data.length;
    }

    /**
     * This method prints all words in a line, seperated by a space.
     * worst-case running time complexity: O(n)
     */
    public void display() {
        StringBuilder sb = new StringBuilder();
        if (size > 0) {
            for (int i = 0; i < size - 1; i++) {
                sb.append(data[i]).append(" ");
            }
            sb.append(data[size - 1]);
        }
        System.out.println(sb.toString());
    }

    /**
     * this method removes duplicate words in homework1.homework3.MyArray.
     * worst-case running time complexity: O(n^2)
     */
    public void removeDups() {
        //used to keep count of duplicate words
        int counter = 0;
        //set duplicate word to null
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                if (data[i] != null && data[i].equals(data[j])) {
                    //set to null
                    data[j] = null;
                    //increment counter
                    counter++;
                }
            }
        }
        //used to find position that has null value
        int m = 0;
        //used to find position that is not null value
        int n = 0;
        //loop ends when all valid values are copied to the front of the array
        while (n < size) {
            //increment m and n until position m has null value
            while (m < size && data[m] != null) {
                m++;
                n++;
            }
            //increment n until found a position having not null value
            while (n < size && data[n] == null) {
                n++;
            }
            //if we have iterated the whole array, break
            if (m >= size || n >= size) {
                break;
            }
            //copy valid value to null position
            data[m] = data[n];
            //set the valid value to null
            data[n] = null;
        }
        //update size
        size -= counter;
    }

    /**
     * this method validate a word before adding it.
     *
     * @param word the word being added
     * @return true if the word only contains letters from a-z and A-Z, otherwise false
     */
    private boolean validateWord(String word) {
        if (word == null || word.length() == 0) {
            return false;
        }
        for (int i = 0; i < word.length(); i++) {
            if (!(word.charAt(i) >= 'a' && word.charAt(i) <= 'z') && !(word.charAt(i) >= 'A' && word.charAt(i) <= 'Z')) {
                //contains other characters, invalid
                return false;
            }
        }
        //if reach here, the word is valid
        return true;
    }

    /**
     * this method doubles the capacity.
     */
    private void doubleCapacity() {
        int newCapacity = 0;
        if (data.length == 0) {
            newCapacity = 1;
        } else {
            newCapacity = data.length * 2;
        }
        String[] newData = new String[newCapacity];
        System.arraycopy(data, 0, newData, 0, data.length);
        data = newData;
    }
}
