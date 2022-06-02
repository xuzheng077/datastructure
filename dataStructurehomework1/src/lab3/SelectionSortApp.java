package lab3;

import lab3.Employee;

/**
 * 17683 Data Structures for Application Programmers.
 * Lab 3 Simple Sorting and Stability.
 * Selection Sort Implementation.
 * <p>
 * Andrew ID: xuzheng
 *
 * @author Xu Zheng
 */
public class SelectionSortApp {

    /**
     * test selection sort and its stability.
     *
     * @param args arguments
     */
    public static void main(String[] args) {
        Employee[] list = new Employee[10];

        // employee data : first name, last name, zip
        list[0] = new Employee("Patty", "Evans", 15213);
        list[1] = new Employee("Doc", "Smith", 15214);
        list[2] = new Employee("Lorraine", "Smith", 15216);
        list[3] = new Employee("Paul", "Smith", 15216);
        list[4] = new Employee("Tom", "Yee", 15216);
        list[5] = new Employee("Sato", "Hashimoto", 15218);
        list[6] = new Employee("Henry", "Stimson", 15215);
        list[7] = new Employee("Jose", "Vela", 15211);
        list[8] = new Employee("Minh", "Vela", 15211);
        list[9] = new Employee("Lucinda", "Craswell", 15210);

        System.out.println("Before Selection Sorting: ");
        for (Employee e : list) {
            System.out.println(e);
        }
        System.out.println("");

        selectionSort(list, "last");

        System.out.println("After Selection Sorting by last name: ");
        for (Employee e : list) {
            System.out.println(e);
        }
        System.out.println("");

        selectionSort(list, "zip");

        System.out.println("After Selection Sorting by zip code: ");
        for (Employee e : list) {
            System.out.println(e);
        }
    }

    /**
     * Sorts employees either by last name or zip using Selection Sort.
     *
     * @param list list of employee objects
     * @param key  key param value should be either "last" or "zip"
     */
    public static void selectionSort(Employee[] list, String key) {
        // TODO implement selection sort here
        int min; // index of min
        if (key.equals("last")) {
            // if based on last name
            for (int out = 0; out < list.length - 1; out++) {
                min = out;
                //find smallest
                for (int in = out + 1; in < list.length; in++) {
                    if (list[in].getLastName().compareToIgnoreCase(list[min].getLastName()) < 0) {
                        min = in;
                    }
                }
                //swap
                if (out != min) {
                    swap(list, out, min);
                }
            }
        } else if (key.equals("zip")) {
            //if based on zip
            for (int out = 0; out < list.length - 1; out++) {
                min = out;
                //find smallest
                for (int in = out + 1; in < list.length; in++) {
                    if (list[in].getZipCode() < list[min].getZipCode()) {
                        min = in;
                    }
                }
                //swap
                if (out != min) {
                    swap(list, out, min);
                }
            }
        }
    }

    /**
     * private helper method to swap elements in an array.
     *
     * @param list list of employee objects
     * @param one  first index position
     * @param two  second index position
     */
    private static void swap(Employee[] list, int one, int two) {
        Employee temp = list[one];
        list[one] = list[two];
        list[two] = temp;
    }

}
