package homework5;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * homework5.Similarity class for analyzing similarity.
 * <p>
 * Andrew ID: xuzheng
 *
 * @author Xu Zheng
 * <p>
 * I used the HashMap data structure from the collections Framework.
 * The reason is that it satisfy the need to store the word and its frequency by using the word as the key
 * and the frequency as the value.
 * And it provides O(1) fast search when access the frequency using the word as the key, which helps
 * to calculate the dotProduct and the distance.
 */
public class Similarity {

    /**
     * map to store the word and its frequency.
     */
    private Map<String, BigInteger> wordMap = new HashMap<>();

    /**
     * number of lines.
     */
    private int numOfLines;

    /**
     * Constructor that takes a string input.
     *
     * @param string string input
     */
    public Similarity(String string) {
        if (string == null || string.length() == 0) {
            return;
        }
        String[] words = string.split("\\W");
        numOfLines = 1;
        for (String word : words) {
            if (validateWord(word)) {
                word = word.toLowerCase();
                if (wordMap.containsKey(word)) {
                    BigInteger freq = wordMap.get(word).add(BigInteger.ONE);
                    wordMap.put(word, freq);
                } else {
                    wordMap.put(word, BigInteger.ONE);
                }
            }
        }
    }

    /**
     * constructor that takes a file input.
     *
     * @param file file input
     */
    public Similarity(File file) {
        if (file == null) {
            return;
        }
        Scanner scanner = null;
        try {
            scanner = new Scanner(file, "latin1");
            //scan every line
            while (scanner.hasNextLine()) {
                numOfLines++;
                String line = scanner.nextLine();
                //split into words
                String[] words = line.split("\\W");
                for (String word : words) {
                    //check if it's valid word
                    if (validateWord(word)) {
                        //convert to lowercase
                        word = word.toLowerCase();
                        //add to map
                        if (wordMap.containsKey(word)) {
                            BigInteger freq = wordMap.get(word).add(BigInteger.ONE);
                            wordMap.put(word, freq);
                        } else {
                            wordMap.put(word, BigInteger.ONE);
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Cannot find the file");
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }

    /**
     * get number of line in the input.
     *
     * @return number of line in the input
     */
    public int numOfLines() {
        return numOfLines;
    }

    /**
     * get number of words in the input.
     *
     * @return number of words in the input
     */
    public BigInteger numOfWords() {
        BigInteger numOfWords = new BigInteger("0");
        for (BigInteger value : wordMap.values()) {
            numOfWords = numOfWords.add(value);
        }
        return numOfWords;
    }

    /**
     * get number of no-dup words in the input.
     *
     * @return number of words in the input
     */
    public int numOfWordsNoDups() {
        return wordMap.keySet().size();
    }

    /**
     * get the euclideanNorm.
     *
     * @return euclideanNorm
     */
    public double euclideanNorm() {
        BigInteger sum = new BigInteger("0");
        for (BigInteger value : wordMap.values()) {
            sum = sum.add(value.pow(2));
        }
        return Math.sqrt(sum.doubleValue());
    }

    /**
     * calculate the dotProduct.
     * This is O(n) time but not quadratic time because it only iterates through this.wordMap once,
     * For each key, it uses containsKey() of another map to determine whether they have the common key( or common word)
     * The containsKey() and get() method both have O(1) time complexity.
     * So the overall time complexity of this method is O(n)
     *
     * @param map the wordMap of another homework5.Similarity object
     * @return dotProduct
     */
    public double dotProduct(Map<String, BigInteger> map) {
        if (map == null) {
            return 0.0;
        }
        double dotProduct = 0.0;
        for (String key : wordMap.keySet()) {
            if (map.containsKey(key)) {
                dotProduct += wordMap.get(key).multiply(map.get(key)).doubleValue();
            }
        }
        return dotProduct;
    }

    /**
     * calculate the distance.
     *
     * @param map the wordMap of another homework5.Similarity object
     * @return the distance
     */
    public double distance(Map<String, BigInteger> map) {
        if (dotProduct(map) == 0.0) {
            return Math.PI / 2;
        }
        double otherSum = 0.0;
        for (BigInteger value : map.values()) {
            otherSum += Math.pow(value.doubleValue(), 2);
        }
        double otherEuclideanNorm = Math.sqrt(otherSum);
        double distance = Math.acos(dotProduct(map) / (euclideanNorm() * otherEuclideanNorm));
        if (Double.isNaN(distance)) {
            return 0.0;
        }
        return distance;
    }

    /**
     * get the wordMap.
     *
     * @return wordMap
     */
    public Map<String, BigInteger> getMap() {
        return new HashMap<>(wordMap);
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
