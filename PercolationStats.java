import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {
    // number of experiments
    private final int trialsNum;
    private final double[] matrix;
    private static final double THRESHOLD = 1.96;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("Invalid");
        }
        this.trialsNum = trials;
        this.matrix = new double[trialsNum];
        for (int i = 0; i < trialsNum; i++) {
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                int row = StdRandom.uniformInt(n);
                int col = StdRandom.uniformInt(n);
                percolation.open(row, col);
            }
            matrix[i] = (double) percolation.numberOfOpenSites() / (n * n);
        }


    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(matrix);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(matrix);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        return mean() - (THRESHOLD * stddev()) / Math.sqrt(trialsNum);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        return mean() + (THRESHOLD * stddev() / Math.sqrt(trialsNum));
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        Stopwatch stopwatch = new Stopwatch();
        PercolationStats percolation = new PercolationStats(n, trials);
        double elapsedTime = stopwatch.elapsedTime();
        System.out.println("mean()           = " + percolation.mean());
        System.out.println("stddev()         = " + percolation.stddev());
        System.out.println("confidenceLow()  = " + percolation.confidenceLow());
        System.out.println("confidenceHigh() = " + percolation.confidenceHigh());
        System.out.println("elapsed time     = " + elapsedTime);
    }

}
