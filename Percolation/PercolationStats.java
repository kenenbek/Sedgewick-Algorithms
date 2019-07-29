/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private int trials;
    private double mean, stdDev;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials){
        if (n <= 0 || trials <= 0){
            throw new java.lang.IllegalArgumentException();
        }

        this.trials = trials;
        double[] percolations = new double[trials];

        for (int i = 0; i < trials; i++) {

            Percolation p = new Percolation(n);
            int j = 0;
            while (!p.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                if (!p.isOpen(row, col)) {
                    p.open(row, col);
                    //System.out.printf("num-%d %d %d\n", j++, row, col);
                }
            }
            percolations[i] = p.numberOfOpenSites() / (double) (n * n);
        }
        this.mean = StdStats.mean(percolations);
        this.stdDev = StdStats.stddev(percolations);
    }

    // sample mean of percolation threshold
    public double mean(){
        return this.mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev(){
        return this.stdDev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo(){
        return this.mean - 1.96 * this.stdDev / java.lang.Math.sqrt(this.trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi(){
        return this.mean + 1.96 * this.stdDev / java.lang.Math.sqrt(this.trials);
    }


    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);

        PercolationStats stats = new PercolationStats(n, T);

        System.out.printf("mean %f \n", stats.mean());
        System.out.printf("std %f \n", stats.stddev());
        System.out.printf("confidence  [%f, %f] \n", stats.confidenceLo(), stats.confidenceHi());

    }
}
