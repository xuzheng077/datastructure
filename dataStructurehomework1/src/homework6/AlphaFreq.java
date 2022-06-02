package homework6;

import java.util.Comparator;

/**
 * Sorts words according to alphabets first.
 * and if there is a tie, then words are sorted by their frequencies in ascending order .
 * (a word with lowest frequency comes first).
 * <p>
 * Andrew ID: xuzheng
 *
 * @author Xu Zheng
 */
public class AlphaFreq implements Comparator<Word> {

    /**
     * override compare method.
     * @param o1 Word 1
     * @param o2 Word 2
     * @return negative if o1 comes before o2, 0 if equals, otherwise positive
     */
    @Override
    public int compare(Word o1, Word o2) {
        //compare alphabetically first
        int alpha = o1.compareTo(o2);
        if (alpha != 0) {
            return alpha;
        }
        //compare frequency if there is a tie
        return Integer.compare(o1.getFrequency(), o2.getFrequency());
    }
}
