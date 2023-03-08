import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    // the top
    private Node first;
    // the bottom
    private Node last;
    // size of the deque
    private int n;

    private class Node {
        // current item
        private Item item;
        // next item
        private Node next;
        // previous item
        private Node previous;
    }

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        this.n = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return (first == null || last == null);
    }

    // return the number of items on the deque
    public int size() {
        return n;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Invalid");
        }
        // saving first into old first
        Node oldFirst = first;
        // creating a new node
        first = new Node();
        // after creating a new node assign oldfirst.previous to first
        if (isEmpty()) {
            last = first;
        }
        else {
            // linking old first with first
            oldFirst.previous = first;
        }
        // assigning item to first
        first.item = item;
        // first.next being assigned to the old first
        first.next = oldFirst;
        // defining previous
        first.previous = null;
        // increments n
        n++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Invalid");
        }
        // saving last into old last
        Node oldLast = last;
        // creating a new node
        last = new Node();
        // assigning item to last
        last.item = item;
        if (isEmpty()) {
            first = last;
        }
        else {
            // assigning old.last to last
            oldLast.next = last;
        }
        // corner case. turning the previous into old last
        last.previous = oldLast;
        last.next = null;
        // increments n
        n++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack underflow");
        }
        // saving item to return
        Item item = first.item;
        // unlinking first item
        first = first.next;
        if (n > 1) {
            first.previous = null;
        }
        // decrement n
        n--;
        if (n == 0) {
            last = null;
        }
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack underflow");
        }
        // saving item to return
        Item item = last.item;
        // unlinking last item
        last = last.previous;
        if (n > 1) {
            // since we removed last its now null
            last.next = null;
        }
        n--;
        // corner case if n is 0
        if (n == 0) {
            first = null;
        }
        // returning item
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new LinkedlistIterator();
    }

    private class LinkedlistIterator implements Iterator<Item> {
        // current position
        private Node current;

        // the contract
        public LinkedlistIterator() {
            current = first;
        }

        // returns true if next() should return a value
        public boolean hasNext() {
            return current != null;
        }

        // returns the current item and advances current
        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();
            Item x = current.item;
            current = current.next;
            return x;
        }
    }

    // private method to print queue
    private void printQueue() {
        Node current = first;
        while (current != null) {
            StdOut.println(current.item + " ");
            current = current.next;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> testing = new Deque<Integer>();
        testing.addFirst(1);
        testing.addLast(2);
        testing.addFirst(2);
        testing.addFirst(3);
        testing.addFirst(4);
        testing.addLast(5);
        testing.addLast(4);
        testing.addLast(7);
        testing.printQueue();
        testing.removeFirst();
        testing.removeLast();
        System.out.println("Is Deque empty: " + testing.isEmpty());
        System.out.println("What is the size: " + testing.size());
    }
}
