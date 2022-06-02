package homework6;

import java.util.Comparator;

/**
 * sorts words according to their frequencies.
 * a word with highest frequency comes first.
 * <p>
 * Andrew ID: xuzheng
 *
 * @author Xu Zheng
 */
public class Frequency implements Comparator<Word> {

    /**
     * Override compare method.
     * @param o1 Word 1
     * @param o2 Word 2
     * @return negative if o1 comes before o2, 0 if equals, otherwise positive
     */
    @Override
    public int compare(Word o1, Word o2) {
        return Integer.compare(o2.getFrequency(), o1.getFrequency());
    }
}
