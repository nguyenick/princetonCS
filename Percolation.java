import edu.princeton.cs.algs4.QuickUnionUF;
import edu.princeton.cs.algs4.Stopwatch;

public class Percolation {
    // length of the matrix
    private final int n;
    // grid[row][col]
    private boolean[][] matrix;
    // creating a virtual top at index 0
    private final int virtualTop;
    // creating a virtual bottom that connects the last row
    private final int virtualBot;
    // variable to count open sites
    private int count;
    // initializing UF
    private QuickUnionUF uf;


    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n is less than 0 ");
        }
        this.n = n;
        this.matrix = new boolean[n][n];
        // the virtual bottom will be one more index in the matrix
        this.virtualBot = n * n + 1;
        // the virtual top is n * n. ex: 3*3 = 9
        this.virtualTop = n * n;
        this.count = 0;
        // adding 2 to make room for the virtual bottom and top
        // creating uf object
        uf = new QuickUnionUF(n * n + 2);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (!illgalException(row, col)) {
            throw new IllegalArgumentException("Invalid");
        }
        if (isOpen(row, col)) return;
        // opens the box by setting it to true
        matrix[row][col] = true;
        // increment the number of open sites open
        count++;
        // union the first row to the virtual top if its 0
        if (row == 0) {
            uf.union(getIndex(row, col), virtualTop);
        }
        // union the bottom row to the virtual bottom if its n - 1. ex 3-1 = 2
        if (row == n - 1) {
            uf.union(getIndex(row, col), virtualBot);
        }
        // checking down if its open, if so, union
        if (row < n - 1 && isOpen(row + 1, col)) {
            uf.union(getIndex(row, col), getIndex(row + 1, col));
        }
        // checking up if its open, if so, union
        if (row > 0 && isOpen(row - 1, col)) {
            uf.union(getIndex(row, col), getIndex(row - 1, col));
        }
        // checking right if its open, if so, union
        if (col < n - 1 && isOpen(row, col + 1)) {
            uf.union(getIndex(row, col), getIndex(row, col + 1));
        }
        // checking left if its open, if so, union
        if (col > 0 && isOpen(row, col - 1)) {
            uf.union(getIndex(row, col), getIndex(row, col - 1));
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (!illgalException(row, col)) {
            throw new IllegalArgumentException("Invalid");
        }
        // determining if the site is open in the row/col
        return matrix[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (!illgalException(row, col)) {
            throw new IllegalArgumentException("Invalid");
        }
        return uf.find(virtualTop) == uf.find(getIndex(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return count;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.find(virtualTop) == uf.find(virtualBot);
    }

    // creating a method that determines the index
    private int getIndex(int row, int col) {
        return (n * row) + col;
    }

    // creating a method that checks for illegal exceptions
    private boolean illgalException(int row, int col) {
        if (row < 0 || row > n - 1 || col < 0 || col > n - 1) {
            return false;
        }
        return true;
    }

    // unit testing (required)
    public static void main(String[] args) {
        Percolation percolation1 = new Percolation(5);
        Stopwatch stopwatch = new Stopwatch();
        double elapsedTime = stopwatch.elapsedTime();
        System.out.println("Does it percolate? " + percolation1.percolates());
        System.out.println("Testing is full method (2,2): " + percolation1.isFull(2, 2));
        System.out.println("Testing numberOfOpenSites method: " + percolation1.numberOfOpenSites());
        System.out.println("Testing is open method: " + percolation1.isOpen(2, 2));
        System.out.println("elapsed time: " + elapsedTime);
    }
}
