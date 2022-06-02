package homework6;

import java.util.Comparator;

/**
 * Sorts words by case insensitive alphabetical order.
 * <p>
 * Andrew ID: xuzheng
 *
 * @author Xu Zheng
 */
public class IgnoreCase implements Comparator<Word> {

    /**
     * override compare method.
     * @param o1 Word 1
     * @param o2 Word 2
     * @return negative if o1 comes before o2, 0 if equals, otherwise positive
     */
    @Override
    public int compare(Word o1, Word o2) {
        return o1.getWord().compareToIgnoreCase(o2.getWord());
    }
}
