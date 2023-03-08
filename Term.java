import edu.princeton.cs.algs4.Selection;

import java.util.Comparator;

public class Term implements Comparable<Term> {
    // input query string
    private String query;
    // input weight for string
    private final long weight;

    // Initializes a term with the given query string and weight.
    public Term(String query, long weight) {
        if (query == null) {
            throw new IllegalArgumentException("No item");
        }
        if (weight < 0) {
            throw new IllegalArgumentException("Weight is less than 0");
        }
        this.query = query;
        this.weight = weight;
    }

    // Compares the two terms in descending order by weight.
    public static Comparator<Term> byReverseWeightOrder() {
        return new ReverseWeightOrder();
    }

    private static class ReverseWeightOrder implements Comparator<Term> {
        public int compare(Term term1, Term term2) {
            if (term2.weight - term1.weight > 0) {
                return 1;
            }
            else if (term2.weight - term1.weight < 0) {
                return -1;
            }
            else return 0;
        }
    }

    // Compares the two terms in lexicographic order,
    // but using only the first r characters of each query.
    public static Comparator<Term> byPrefixOrder(int r) {
        // corner case
        if (r < 0) {
            throw new IllegalArgumentException("o_O");
        }
        return new PrefixOrder(r);
    }

    private static class PrefixOrder implements Comparator<Term> {
        // creating an instance variable r
        private int r;

        // creating a constructor to pass through r
        private PrefixOrder(int r) {
            this.r = r;
        }

        public int compare(Term term1, Term term2) {
            if (term1.query.length() < r || term2.query.length() < r) {
                return term1.query.compareTo(term2.query);
            }

            for (int i = 0; i < r; i++) {
                if (term1.query.charAt(i) < term2.query.charAt(i)) {
                    return -1;
                }
                if (term1.query.charAt(i) > term2.query.charAt(i)) {
                    return 1;
                }
            }
            return 0;
        }
    }

    // Compares the two terms in lexicographic order by query.
    public int compareTo(Term that) {
        return this.query.compareTo(that.query);
    }

    // Returns a string representation of this term in the following format:
    // the weight, followed by a tab, followed by the query.
    public String toString() {
        return this.weight + "\t" + this.query;
    }

    // unit testing (required)
    public static void main(String[] args) {
        Term[] testing = new Term[] {
                new Term("smog", 1234),
                new Term("buck", 4321),
                new Term("sad", 7890),
                new Term("spite", 1000),
                new Term("spit", 2000),
                new Term("spy", 100),
                new Term("ash", 20)
        };

        System.out.println("---------");
        Selection.sort(testing, Term.byPrefixOrder(2));
        for (int i = 0; i < testing.length; i++) {
            System.out.println("Testing to by prefix order: " + testing[i].toString());
        }
        System.out.println("---------");
        Selection.sort(testing, Term.byReverseWeightOrder());
        for (int i = 0; i < testing.length; i++) {
            System.out.println("Testing to weight order: " + testing[i].toString());
        }
    }
}

