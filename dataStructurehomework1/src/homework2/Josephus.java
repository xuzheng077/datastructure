package homework2;

import java.util.ArrayDeque;
import java.util.LinkedList;

/**
 * 17-683 Data Structures for Application Programmers.
 * Homework Assignment 2 Solve homework2.Josephus problem using different data structures
 * and different algorithms and compare running times.
 *
 * <p>
 * Andrew ID: xuzheng
 *
 * @author Roy Zheng
 * <p>
 * I would choose playWithLLAt() method because it's faster than the other two.
 * Reason:
 * 1. For ArrayDeque, each rotation, it has to remove from head and add to tail until meet the one to execute,
 * which are both O(1) operation.
 * Initializing the ArrayDeque capacity with size will remove the overhead for resizing, but the overhead for removing
 * and addition still exists. It's faster than LinkedList as queue but slower than LinkedList as list
 * <p>
 * 2. For LinkedList as queue, each rotation, it has to remove from head and add to tail until meet the one to execute,
 * which are both O(1) operation.
 * However, for each removal and addition, it has to create a Node object, which takes time.
 * So the total overhead contains removal, addtion and creating Node object, So it's the slowest.
 * <p>
 * 3. For LinkedList as list, each rotation, it only moves a pointer and remove the one being pointed at,
 * which is O(1) operation.
 * So it doesn't have the overhead to remove and add or to create Node object
 * So it's the fastest.
 */
public class Josephus {

    /**
     * Uses ArrayDeque class as Queue/Deque to find the survivor's position.
     * use addLast() as enqueue since this adds an element to the last, which is what enqueue requires
     * use poll() as dequeue since this removes an element from the head and return it, which is what dequeue requires
     *
     * @param size     Number of people in the circle that is bigger than 0
     * @param rotation Elimination order in the circle. The value has to be greater than 0
     * @return The position value of the survivor
     */
    public int playWithAD(int size, int rotation) {
        // TODO your implementation here
        // size <= 0, throw exception
        if (size <= 0) {
            throw new RuntimeException("size must be greater than 0");
        }
        // rotation <= 0, throw exception
        if (rotation <= 0) {
            throw new RuntimeException("rotation must be greater than 0");
        }
        //enqueue all the numbers
        ArrayDeque<Integer> queue = new ArrayDeque<>(size);
        for (int i = 1; i <= size; i++) {
            queue.addLast(i);
        }
        //eliminate until only one left
        while (queue.size() > 1) {
            int temp = rotation;
            //only need to rotate rotation % queue.size() times if rotation > queue.size()
            //but if rotation % queue.size()==0, then should rotate queue.size() times
            if (rotation > queue.size()) {
                temp = temp % queue.size() == 0 ? queue.size() : temp % queue.size();
            }

            //dequeue and then enqueue 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20
            while (temp > 1 && !queue.isEmpty()) {
                int num = queue.pollFirst();
                queue.addLast(num);
                temp--;
            }
            //dequeue but not enqueue, eliminated in this run
            queue.pollFirst();
        }
        //return the remaining one
        return queue.getFirst();
    }

    /**
     * Uses LinkedList class as Queue/Deque to find the survivor's position.
     * use addLast() as enqueue since this adds an element to the last, which is what enqueue requires
     * use pollFirst() as dequeue since this removes an element from the head and return it, which is what dequeue requires
     *
     * @param size     Number of people in the circle that is bigger than 0
     * @param rotation Elimination order in the circle. The value has to be greater than 0
     * @return The position value of the survivor
     */
    public int playWithLL(int size, int rotation) {
        // TODO your implementation here
        // size <= 0, throw exception
        if (size <= 0) {
            throw new RuntimeException("size must be greater than 0");
        }
        // rotation <= 0, throw exception
        if (rotation <= 0) {
            throw new RuntimeException("rotation must be greater than 0");
        }
        //enqueue all the numbers
        LinkedList<Integer> queue = new LinkedList<>();
        for (int i = 1; i <= size; i++) {
            queue.addLast(i);
        }
        //eliminate until only one left
        while (queue.size() > 1) {
            int temp = rotation;
            //only need to rotate rotation % queue.size() times if rotation > queue.size()
            //but if rotation % queue.size()==0, then should rotate queue.size() times
            if (rotation > queue.size()) {
                temp = temp % queue.size() == 0 ? queue.size() : temp % queue.size();
            }

            //dequeue and then enqueue
            while (temp > 1 && !queue.isEmpty()) {
                int num = queue.pollFirst();
                queue.addLast(num);
                temp--;
            }
            //dequeue but not enqueue, eliminated in this run
            queue.pollFirst();
        }
        //return the remaining one
        return queue.getFirst();
    }

    /**
     * Uses LinkedList class to find the survivor's position.
     * <p>
     * However, do NOT use the LinkedList as Queue/Deque
     * Instead, use the LinkedList as "List"
     * That means, it uses index value to find and remove a person to be executed in the circle
     * <p>
     * Note: Think carefully about this method!!
     * When in doubt, please visit one of the office hours!!
     * <p>
     * use the index to locate and remove person from list, each time step forward rotation - 1 steps
     * use % if out of bound
     *
     * @param size     Number of people in the circle that is bigger than 0
     * @param rotation Elimination order in the circle. The value has to be greater than 0
     * @return The position value of the survivor
     */
    public int playWithLLAt(int size, int rotation) {
        // TODO your implementation here
        // size <= 0, throw exception
        if (size <= 0) {
            throw new RuntimeException("size must be greater than 0");
        }
        // rotation <= 0, throw exception
        if (rotation <= 0) {
            throw new RuntimeException("rotation must be greater than 0");
        }
        //enqueue all the numbers
        LinkedList<Integer> list = new LinkedList<>();
        for (int i = 1; i <= size; i++) {
            list.add(i);
        }
        //the index of the person to remove, start iterating from 0
        int indexToRemove = 0;
        //each time step forward rotation - 1 step
        int step = rotation - 1;
        while (list.size() > 1) {
            //get the index to remove
            indexToRemove = (indexToRemove + step) % list.size();
            //remove that person
            list.remove(indexToRemove);
        }
        //return the only one left
        return list.get(0);
    }
}
