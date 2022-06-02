package homework6;

import java.util.Set;
import java.util.TreeSet;

/**
 * This class is a simple Word class.
 * <p>
 * Andrew ID: xuzheng
 *
 * @author Xu Zheng
 */
public class Word implements Comparable<Word> {
    /**
     * the word String.
     */
    private String word;
    /**
     * the line numbers of the word.
     */
    private Set<Integer> index;
    /**
     * frequency of the word.
     */
    private int frequency;

    /**
     * Constructor with a String parameter.
     *
     * @param w word
     */
    public Word(String w) {
        this.word = w;
        this.frequency = 1;
        index = new TreeSet<>();
    }

    /**
     * Add a new line number for the word.
     *
     * @param line
     */
    public void addToIndex(Integer line) {
        index.add(line);
    }

    /**
     * Getter for index.
     *
     * @return index
     */
    public Set<Integer> getIndex() {
        //return a copy of the index
        return new TreeSet<>(index);
    }

    /**
     * toString method.
     *
     * @return String representation of Word objects.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(word).append(" ").append(frequency).append(" ").append(index);
        return sb.toString();
    }

    @Override
    public int compareTo(Word o) {
        return getWord().compareTo(o.getWord());
    }

    /**
     * Getter for word.
     *
     * @return word
     */
    public String getWord() {
        return word;
    }

    /**
     * Setter for word.
     *
     * @param newWord new word
     */
    public void setWord(String newWord) {
        this.word = newWord;
    }

    /**
     * Getter for frequency.
     *
     * @return frequency
     */
    public int getFrequency() {
        return frequency;
    }

    /**
     * Setter for frequency.
     *
     * @param freq freq
     */
    public void setFrequency(int freq) {
        this.frequency = freq;
    }

    /**
     * override equals() to be consistent with compareTo().
     *
     * @param o Another object to be compared with
     * @return true if word is equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Word word1 = (Word) o;

        return word.equals(word1.word);
    }

    /**
     * override hashCode() to be consistent with equals().
     *
     * @return word's hashCode
     */
    @Override
    public int hashCode() {
        return word.hashCode();
    }
}
