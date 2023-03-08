import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    // creating new items array
    private Item[] items;
    // size
    private int n;
    // setting capacity to 8
    private int capacityInitial = 8;

    // construct an empty randomized queue
    public RandomizedQueue() {
        items = (Item[]) new Object[capacityInitial];
        this.n = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return (n == 0);
    }

    // return the number of items on the randomized queue
    public int size() {
        return n;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Invalid");
        }
        if (n == items.length) {
            resize(2 * items.length);
        }
        items[n] = item;
        n++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (n == 0) {
            throw new NoSuchElementException("No such element");
        }
        // getting a random number in between 0 - n-1
        int entry = StdRandom.uniformInt(n);
        // assigning outputs to the items array at entry
        Item outputs = items[entry];
        // deleting entry and moving n-1 to where entry was at
        items[entry] = items[n - 1];
        // assigning n-1 to null;
        items[n - 1] = null;
        // decrement n
        n--;
        if (n > 0 && n == items.length / 4) {
            resize(items.length / 2);
        }
        return outputs;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (n == 0) {
            throw new NoSuchElementException("No such element");
        }
        int entry = StdRandom.uniformInt(n);
        Item outputs = items[entry];
        return outputs;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomIterator();
    }

    private class RandomIterator implements Iterator<Item> {
        // index
        private int i;
        // the randomized queue
        private Item randomizedQueue[];

        // the contract
        public RandomIterator() {
            this.i = 0;
            this.randomizedQueue = (Item[]) new Object[n];
            for (int j = 0; j < n; j++) {
                this.randomizedQueue[j] = items[j];
            }
            StdRandom.shuffle(this.randomizedQueue);
        }

        // returns true if next() should return a value
        public boolean hasNext() {
            return i < n;
        }

        // returns the current item and advances current
        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException("No such element");
            Item output = randomizedQueue[i];
            i++;
            return output;
        }
    }

    // private method to resize
    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            copy[i] = items[i];
        }
        items = copy;
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> testing = new RandomizedQueue<Integer>();
        testing.enqueue(2);
        System.out.println("The size is: " + testing.size());
        testing.enqueue(3);
        System.out.println("The size is: " + testing.size());
        testing.enqueue(4);
        System.out.println("The size is: " + testing.size());
        testing.enqueue(5);
        System.out.println("The size is: " + testing.size());
        for (int i : testing) {
            System.out.println(i);
        }
        System.out.println(testing.dequeue());
        System.out.println("The size is: " + testing.size());
        for (int i : testing) {
            System.out.println(i);
        }
        System.out.println(testing.dequeue());
        System.out.println("The size is: " + testing.size());
        for (int i : testing) {
            System.out.println(i);
        }
        System.out.println("The sample is: " + testing.sample());
        System.out.println("The size is: " + testing.size());
        System.out.println("Is it empty: " + testing.isEmpty());
    }
}
