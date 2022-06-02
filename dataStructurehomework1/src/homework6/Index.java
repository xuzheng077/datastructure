package homework6;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Comparator;
import java.util.Iterator;

/**
 * This class is responsible for building an index tree in three different ways.
 * <p>
 * Andrew ID: xuzheng
 *
 * @author Xu Zheng
 */
public class Index {

    /**
     * Build an index from a file.
     *
     * @param fileName file name
     * @return a BST index
     */
    public BST<Word> buildIndex(String fileName) {
        return buildIndex(fileName, null);
    }

    /**
     * Build an index from a file using a specified comparator.
     *
     * @param fileName   file name
     * @param comparator specified comparator
     * @return a BST index
     */
    public BST<Word> buildIndex(String fileName, Comparator<Word> comparator) {
        Scanner scanner = null;
        Word wordObject = null;
        BST<Word> bst = new BST<>(comparator);
        Integer lineNumber = 0;
        try {
            scanner = new Scanner(new File(fileName), "latin1");
            //scan every line
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                //increment lineNumber
                lineNumber++;
                //split into words
                String[] words = line.split("\\W");

                for (String word : words) {
                    //check if it's a valid word
                    if (validateWord(word)) {
                        if (comparator instanceof IgnoreCase) {
                            word = word.toLowerCase();
                        }
                        wordObject = new Word(word);
                        Word searchResult = bst.search(wordObject);
                        if (searchResult == null) {
                            wordObject.addToIndex(lineNumber);
                            //add to BST
                            bst.insert(wordObject);
                        } else {
                            searchResult.addToIndex(lineNumber);
                            searchResult.setFrequency(searchResult.getFrequency() + 1);
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
        return bst;
    }

    /**
     * Build an index from a file using a specified comparator.
     *
     * @param list       word list
     * @param comparator comparator
     * @return a BST index
     */
    public BST<Word> buildIndex(ArrayList<Word> list, Comparator<Word> comparator) {
        BST<Word> bst = new BST<>(comparator);
        Word wordObject = null;
        //go through list
        for (Word word : list) {
            //if IgnoreCase, transform to lower case
            if (comparator instanceof IgnoreCase) {
                wordObject = new Word(word.getWord().toLowerCase());
            } else {
                wordObject = word;
            }
            //search bst, if not exist, add
            Word searchResult = bst.search(wordObject);
            if (searchResult == null) {
                //add to BST
                bst.insert(word);
            }
        }

        return bst;
    }

    public ArrayList<Word> sortByAlpha(BST<Word> tree) {
        /*
         * Even though there should be no ties with regard to words in BST,
         * in the spirit of using what you wrote,
         * use AlphaFreq comparator in this method.
         */
        ArrayList<Word> result = new ArrayList<>();
        //check input
        if (tree == null) {
            return result;
        }
        //add using iterator
        Iterator<Word> iterator = tree.iterator();
        while (iterator.hasNext()) {
            result.add(iterator.next());
        }
        //sort
        result.sort(new AlphaFreq());
        return result;
    }

    public ArrayList<Word> sortByFrequency(BST<Word> tree) {
        ArrayList<Word> result = new ArrayList<>();
        //check input
        if (tree == null) {
            return result;
        }
        //add using iterator
        Iterator<Word> iterator = tree.iterator();
        while (iterator.hasNext()) {
            result.add(iterator.next());
        }
        //sort
        result.sort(new Frequency());
        return result;
    }

    public ArrayList<Word> getHighestFrequency(BST<Word> tree) {
        ArrayList<Word> result = new ArrayList<>();
        //check input
        if (tree == null) {
            return result;
        }
        //sortByFrequency
        ArrayList<Word> sorted = sortByFrequency(tree);
        if (sorted.size() > 0) {
            //add the highest frequency word
            int highestFreq = sorted.get(0).getFrequency();
            for (Word word : sorted) {
                if (word.getFrequency() == highestFreq) {
                    result.add((word));
                } else {
                    break;
                }
            }
        }
        return result;
    }

    /**
     * this method validate a word.
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
