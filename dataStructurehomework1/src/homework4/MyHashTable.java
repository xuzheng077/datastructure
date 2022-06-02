package homework4;

/**
 * 17683 Data Structures for Application Programmers.
 * Homework Assignment 4: HashTable Implementation with linear probing.
 * <p>
 * Andrew ID: xuzheng
 *
 * @author Xu Zheng
 */
public class MyHashTable implements MyHTInterface {
    /**
     * Constant for default capacity of the array.
     */
    private static final int DEFAULT_CAPACITY = 10;

    /**
     * Constant for default load factor.
     */
    private static final double LOAD_FACTOR = 0.5;

    /**
     * Deleted flag.
     */
    private static final DataItem DELETED = new DataItem("#DEL#", 0);

    /**
     * Base for hashFunc.
     */
    private static final int HASH_BASE = 27;

    /**
     * ASCII number used to map lowercase letter.
     * For example, 'a' is 97, we map it to 1 using 'a' - 96.
     */
    private static final int OFFSET = 96;

    /**
     * The DataItem array of the table.
     */
    private DataItem[] hashArray;

    /**
     * Number of collisions.
     */
    private int numOfCollisions;

    /**
     * Number of items in the hashArray.
     */
    private int size;

    /**
     * Constructor with no initial capacity.
     */
    public MyHashTable() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Constructor with initial capacity.
     *
     * @param initialCapacity initial capacity
     */
    public MyHashTable(int initialCapacity) {
        //check initialCapacity
        if (initialCapacity <= 0) {
            throw new RuntimeException("Initial capacity must be greater than 0.");
        }
        //initialize hashArray
        hashArray = new DataItem[initialCapacity];
        //set size and numOfCollisions to 0
        size = 0;
        numOfCollisions = 0;
    }

    /**
     * Instead of using String's hashCode, you are to implement your own here.
     * You need to take the table length into your account in this method.
     * <p>
     * In other words, you are to combine the following two steps into one step.
     * 1. converting Object into integer value
     * 2. compress into the table using modular hashing (division method)
     * <p>
     * Helper method to hash a string for English lowercase alphabet and blank,
     * we have 27 total. But, you can assume that blank will not be added into
     * your table. Refer to the instructions for the definition of words.
     * <p>
     * For example, "cats" : 3*27^3 + 1*27^2 + 20*27^1 + 19*27^0 = 60,337
     * <p>
     * But, to make the hash process faster, Horner's method should be applied as follows;
     * <p>
     * var4*n^4 + var3*n^3 + var2*n^2 + var1*n^1 + var0*n^0 can be rewritten as
     * (((var4*n + var3)*n + var2)*n + var1)*n + var0
     * <p>
     * Note: You must use 27 for this homework.
     * <p>
     * However, if you have time, I would encourage you to try with other
     * constant values than 27 and compare the results but it is not required.
     *
     * @param input input string for which the hash value needs to be calculated
     * @return int hash value of the input string
     */
    private int hashFunc(String input) {
        int hash = 0;
        char[] arr = input.toCharArray();
        //compute hash using mod
        for (int i = 0; i < arr.length; i++) {
            hash = (hash * HASH_BASE + arr[i] - OFFSET) % hashArray.length;
        }
        return hash;
    }

    /**
     * doubles array length and rehash items whenever the load factor is reached.
     * Note: do not include the number of deleted spaces to check the load factor.
     * Remember that deleted spaces are available for insertion.
     */
    private void rehash() {
        //reset numOfCollisions to 0
        numOfCollisions = 0;
        //get new length, which must be a prime
        int newLength = hashArray.length * 2 + 1;
        while (!isPrime(newLength)) {
            newLength++;
        }
        //use temp to store old hashArray
        DataItem[] temp = hashArray;
        //hashArray with new length
        hashArray = new DataItem[newLength];
        for (DataItem dataItem : temp) {
            if (dataItem != null && dataItem != DELETED) {
                //compute hashValue
                int hashValue = hashFunc(dataItem.getValue());
                //handle numOfCollisions
                int p = hashValue;
                while (hashArray[p] != null) {
                    if (hashFunc(hashArray[p].getValue()) == hashValue) {
                        numOfCollisions++;
                        break;
                    }
                    p++;
                    p = p % hashArray.length;
                }
                //find the right position
                while (hashArray[hashValue] != null) {
                    hashValue++;
                    hashValue = hashValue % hashArray.length;
                }
                //insert
                hashArray[hashValue] = dataItem;
            }
        }
        System.out.println("Rehashing " + size + " items, new length is " + newLength);
    }

    /**
     * Inserts a new String value (word).
     * Frequency of each word to be stored too.
     *
     * @param value String value to add
     */
    @Override
    public void insert(String value) {
        if (validateWord(value)) {
            //compute hashValue
            int hashValue = hashFunc(value);
            //check if the same string exists
            int searchResult = search(hashValue, value);
            if (searchResult != -1) {
                //if exists, update the frequency
                int newFreq = hashArray[searchResult].getFrequency() + 1;
                hashArray[searchResult].setFrequency(newFreq);
                //finish insertion
                return;
            }
            //if not exists, the initial position is hashValue
            //check collision
            int p = hashValue;
            int counter = 0;
            while (hashArray[p] != null && counter < hashArray.length) {
                if (hashFunc(hashArray[p].getValue()) == hashValue) {
                    numOfCollisions++;
                    break;
                }
                p++;
                counter++;
                p = p % hashArray.length;
            }
            //if index occupied, probe
            while (hashArray[hashValue] != null && hashArray[hashValue] != DELETED) {
                hashValue++;
                hashValue = hashValue % hashArray.length;
            }
            //find one not occupied, insert
            hashArray[hashValue] = new DataItem(value, 1);
            size++;
            //check load factor, rehash if needed
            if ((1.0 * size / hashArray.length) > LOAD_FACTOR) {
                rehash();
            }
        }
    }

    /**
     * search for a DataItem with the same value.
     *
     * @param hashValue hashValue of the value
     * @param value     the value to search
     * @return a DataItem
     */
    private int search(int hashValue, String value) {
        int counter = 0;
        while (hashArray[hashValue] != null && counter < hashArray.length) {
            if (hashArray[hashValue].getValue().equals(value)) {
                //find the same string already added
                return hashValue;
            }
            hashValue++;
            counter++;
            hashValue = hashValue % hashArray.length;
        }
        return -1;
    }

    /**
     * Returns the size, number of items, of the table.
     *
     * @return the number of items in the table
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Displays the values of the table.
     * If an index is empty, it shows **
     * If previously existed data item got deleted, then it should show #DEL#
     */
    @Override
    public void display() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < hashArray.length; i++) {
            //if null
            if (hashArray[i] == null) {
                sb.append("**");
            } else if (hashArray[i] == DELETED) {
                //if deleted
                sb.append("#DEL#");
            } else {
                //if not null and not deleted
                sb.append("[").append(hashArray[i].getValue()).append(", ").append(hashArray[i].getFrequency()).append("]");
            }
            sb.append(" ");
        }
        //print
        System.out.println(sb.toString().substring(0, sb.length() - 1));
    }

    /**
     * Returns true if value is contained in the table.
     *
     * @param key String key value to search
     * @return true if found, false if not found.
     */
    @Override
    public boolean contains(String key) {
        if (validateWord(key)) {
            int hashValue = hashFunc(key);
            int counter = 0;
            //probe
            while (hashArray[hashValue] != null && counter < hashArray.length) {
                if (hashArray[hashValue].getValue().equals(key)) {
                    //find the key
                    return true;
                }
                hashValue++;
                counter++;
                hashValue = hashValue % hashArray.length;
            }
        }
        //not found
        return false;
    }

    /**
     * Returns the number of collisions in relation to insert and rehash.
     * When rehashing process happens, the number of collisions should be properly updated.
     * <p>
     * The definition of collision is "two different keys map to the same hash value."
     * Be careful with the situation where you could overcount.
     * Try to think as if you are using separate chaining.
     * "How would you count the number of collisions?" when using separate chaining.
     *
     * @return number of collisions
     */
    @Override
    public int numOfCollisions() {
        return numOfCollisions;
    }

    /**
     * Returns the hash value of a String.
     * Assume that String value is going to be a word with all lowercase letters.
     *
     * @param value value for which the hash value should be calculated
     * @return int hash value of a String
     */
    @Override
    public int hashValue(String value) {
        //call hashFunc
        if (validateWord(value)) {
            return hashFunc(value);
        }
        return -1;
    }

    /**
     * Returns the frequency of a key String.
     *
     * @param key string value to find its frequency
     * @return frequency value if found. If not found, return 0
     */
    @Override
    public int showFrequency(String key) {
        if (validateWord(key)) {
            int hashValue = hashFunc(key);
            int counter = 0;
            //probe
            while (hashArray[hashValue] != null && counter < hashArray.length) {
                if (hashArray[hashValue].getValue().equals(key)) {
                    //found, return frequency
                    return hashArray[hashValue].getFrequency();
                }
                hashValue++;
                counter++;
                hashValue = hashValue % hashArray.length;
            }
        }
        //not found
        return 0;
    }

    /**
     * Removes and returns removed value.
     *
     * @param key String to remove
     * @return value that is removed. If not found, return null
     */
    @Override
    public String remove(String key) {
        if (!validateWord(key)) {
            return null;
        }
        //compute hashValue
        int hashValue = hashFunc(key);
        //temp used to store the value
        String temp = null;
        int counter = 0;
        while (hashArray[hashValue] != null && counter < hashArray.length) {
            if (hashArray[hashValue].getValue().equals(key)) {
                //found
                temp = hashArray[hashValue].getValue();
                //set to DELETED
                hashArray[hashValue] = DELETED;
                size--;
                //return temp
                return temp;
            }
            hashValue++;
            counter++;
            hashValue = hashValue % hashArray.length;
        }
        //not found
        return null;
    }

    /**
     * private static data item nested class.
     */
    private static class DataItem {
        /**
         * String value.
         */
        private String value;
        /**
         * String value's frequency.
         */
        private int frequency;

        /**
         * Constructor.
         *
         * @param val  value
         * @param freq frequency
         */
        DataItem(String val, int freq) {
            value = val;
            frequency = freq;
        }

        /**
         * getter for value.
         *
         * @return value
         */
        public String getValue() {
            return value;
        }

        /**
         * setter for value.
         *
         * @param val val
         */
        public void setValue(String val) {
            this.value = val;
        }

        /**
         * getter for frequency.
         *
         * @return frequency
         */
        public int getFrequency() {
            return frequency;
        }

        /**
         * setter for frequency.
         *
         * @param freq freq
         */
        public void setFrequency(int freq) {
            this.frequency = freq;
        }
    }

    /**
     * Helper method to check if a number is prime.
     *
     * @param x number to check
     * @return true if it's prime, false if not
     */
    private boolean isPrime(int x) {
        if (x < 2) {
            return false;
        } else {
            for (int i = 2; i <= Math.sqrt(x); i++) {
                if (x % i == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * this method validate a word before adding it.
     *
     * @param word the word being added
     * @return true if the word only contains letters from a-z, otherwise false
     */
    private boolean validateWord(String word) {
        if (word == null) {
            return false;
        }
        return word.matches("[a-z]+");
    }

}
