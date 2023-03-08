import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class Autocomplete {
    // instances varaible of the array Terms
    private Term[] terms;
    private int first;
    private int last;

    // Initializes the data structure from the given array of terms.
    public Autocomplete(Term[] terms) {
        // corner case
        if (terms == null) {
            throw new IllegalArgumentException("o_O");
        }
        // constructing the new array term with the length of it ?? is this explanation right
        this.terms = new Term[terms.length];
        this.first = 0;
        this.last = 0;
        // copying the items
        for (int i = 0; i < terms.length; i++) {
            // corner case if any element is null
            if (terms[i] == null) {
                throw new IllegalArgumentException("o_O");
            }
            this.terms[i] = terms[i];
        }
        // sorting all items in the array
        Arrays.sort(this.terms);
    }

    // Returns all terms that start with the given prefix,
    // in descending order of weight.
    public Term[] allMatches(String prefix) {
        // corner case if prefix is null
        if (prefix == null) {
            throw new IllegalArgumentException("o_O");
        }
        // creating a new term that takes in prefix as an argument to use for binarysearch
        Term pre = new Term(prefix, 0);
        // size of matches array
        int size = 0;
        // corner case if the prefix length is 0 return everything in array
        if (prefix.length() == 0) {
            Term[] matches = new Term[terms.length];
            for (int i = 0; i < terms.length; i++) {
                matches[i] = terms[i];
            }
            Arrays.sort(matches, Term.byReverseWeightOrder());
            return matches;
        }
        // getting the first index of the prefix
        first = BinarySearchDeluxe.firstIndexOf(terms, pre,
                                                Term.byPrefixOrder(prefix.length()));
        // getting the last index of the prefix
        last = BinarySearchDeluxe.lastIndexOf(terms, pre,
                                              Term.byPrefixOrder(prefix.length()));
        if (first != -1 && last != -1) {
            size = (last - first) + 1;
        }
        // creating a new array with the size of the number of matches
        Term[] matches = new Term[size];
        // looping through to copy all the matches to new array
        for (int i = 0; i < matches.length; i++) {
            matches[i] = terms[first];
            // increment from first because that's where the first match is at
            first++;
        }
        // returning the matches by the reverse weight order
        Arrays.sort(matches, Term.byReverseWeightOrder());
        return matches;
    }

    // Returns the number of terms that start with the given prefix.
    public int numberOfMatches(String prefix) {
        // corner case if the prefix is null
        if (prefix == null) {
            throw new IllegalArgumentException("o_O");
        }
        // corner case if the prefix is 0
        if (prefix.length() == 0) {
            return terms.length;
        }
        Term pre = new Term(prefix, 0);
        last = BinarySearchDeluxe.lastIndexOf(terms, pre, Term.byPrefixOrder(prefix.length()));
        first = BinarySearchDeluxe.firstIndexOf(terms, pre,
                                                Term.byPrefixOrder(prefix.length()));
        if (last == -1 && first == -1) {
            return 0;
        }
        return (last - first) + 1;

    }

    // unit testing (required)
    public static void main(String[] args) {

        // read in the terms from a file
        String filename = args[0];
        In in = new In(filename);
        int n = in.readInt();
        Term[] terms = new Term[n];
        for (int i = 0; i < n; i++) {
            long weight = in.readLong();           // read the next weight
            in.readChar();                         // scan past the tab
            String query = in.readLine();          // read the next query
            terms[i] = new Term(query, weight);    // construct the term
        }

        // read in queries from standard input and print the top k matching terms
        int k = Integer.parseInt(args[1]);
        Autocomplete autocomplete = new Autocomplete(terms);
        while (StdIn.hasNextLine()) {
            String prefix = StdIn.readLine();
            Term[] results = autocomplete.allMatches(prefix);
            StdOut.printf("%d matches\n", autocomplete.numberOfMatches(prefix));
            for (int i = 0; i < Math.min(k, results.length); i++)
                StdOut.println(results[i]);
        }
    }
}
