import java.util.*;

/**
 * @author Xu Zheng
 * @description
 */
public class Test {
    public static void main(String[] args) {
//        Integer[] a = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
//        ArrayList<Integer> b = new ArrayList<Integer>(Arrays.asList(a));
//        for (int i = b.size() - 1; i >= 0; i--) {
//            if (b.get(i) % 2 == 0) {
//                b.remove(i);
//            }
//        }
//        System.out.println(b);
//        TreeMap<Integer, String> map = new TreeMap<>();
//        map.put(1,"a");
//        map.put(2,"b");
//        map.put(3,"c");
//        map.put(4,"d");
//        System.out.println(map);
//        System.out.println(map.lastEntry());
//        System.out.println(map.descendingKeySet());
//        System.out.println(map);
//        System.out.println(map);

//        TreeSet<Integer> set = new TreeSet<>();
//        set.add(1);
//        set.add(2);
//        set.add(3);
//        set.add(4);
//        System.out.println(set.tailSet(2));
//        System.out.println("aaabbb".hashCode());
//        System.out.println("aababb".hashCode());
//        System.out.println("abbbaa".hashCode());
//        System.out.println("ababab".hashCode());
        PriorityQueue<Integer> p = new PriorityQueue<>();
        p.add(1);
        p.add(1000);
        p.add(5);
        System.out.println(p);
        System.out.println(p.poll());
        System.out.println(p.poll());
        System.out.println(p.poll());
        int[] nums = {1,2};
        Arrays.sort(nums, new Comparator<int>(){
            @Override
            public int compare(Integer o1, Integer o2) {
                return 0;
            }
        });
    }
}
