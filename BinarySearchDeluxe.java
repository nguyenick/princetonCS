import java.util.Comparator;

public class BinarySearchDeluxe {


    // Returns the index of the first key in the sorted array a[]
    // that is equal to the search key, or -1 if no such key.
    public static <Key> int firstIndexOf(Key[] a, Key key, Comparator<Key> comparator) {
        // corner cases
        if (a == null || key == null || comparator == null) {
            throw new IllegalArgumentException("O_o");
        }
        // setting low to 0
        int low = 0;
        // setting high to the end of the array
        int high = a.length - 1;
        int searchKey = -1;
        while (low <= high) {
            int middle = low + (high - low) / 2;
            int compare = comparator.compare(key, a[middle]);
            // checking if key is less than middle
            if (compare < 0) {
                high = middle - 1;
            }
            // checking if key is greater than middle
            else if (compare > 0) {
                low = middle + 1;
            }
            // checking when middle is equals to key
            else { // (compare == 0)
                // keeping track of the search key
                searchKey = middle;
                // running another binary search in case theres another key before
                high = middle - 1;
            }
        }
        return searchKey;
    }

    // Returns the index of the last key in the sorted array a[]
    // that is equal to the search key, or -1 if no such key.
    public static <Key> int lastIndexOf(Key[] a, Key key, Comparator<Key> comparator) {
        // corner cases
        if (a == null || key == null || comparator == null) {
            throw new IllegalArgumentException("O_o");
        }
        int searchKey = -1;
        int low = 0;
        int high = a.length - 1;
        // keeping track of the middle index
        while (low <= high) {
            int middle = low + (high - low) / 2;
            int compare = comparator.compare(key, a[middle]);
            if (compare < 0) {
                high = middle - 1;
            }
            else if (compare > 0) {
                low = middle + 1;
            }
            // if it matches the key
            else {
                searchKey = middle;
                low = middle + 1;
            }
        }
        return searchKey;
    }

    // unit testing (required)
    public static void main(String[] args) {
        String[] a = {
                "A", "A", "A", "B", "B", "B", "C", "C"
        };
        int index = BinarySearchDeluxe.firstIndexOf(a, "A", String.CASE_INSENSITIVE_ORDER);
        int lastindex = BinarySearchDeluxe.lastIndexOf(a, "A", String.CASE_INSENSITIVE_ORDER);
        System.out.println("The first index is: " + index);
        System.out.println("The last index is: " + lastindex);
    }
}
