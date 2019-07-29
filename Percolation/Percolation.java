/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private boolean[]open;
    private int n;

    private int numberOpenSites;
    private WeightedQuickUnionUF wquf, onlyTopUF;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n){
        if (n <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        open = new boolean[n * n + 2];
        open[n * n] = true;
        open[n * n + 1] = true;

        this.n = n;
        this.wquf = new WeightedQuickUnionUF(n * n + 2);
        this.onlyTopUF = new WeightedQuickUnionUF(n * n + 1);

    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col){
        checker(row, col);

        if (isOpen(row, col)){
            return;
        }

        int idx = this.getId(row, col);
        open[idx] = true;
        this.numberOpenSites += 1;


        int up = row != 1 ? idx - n : n * n;
        int down = row != n ? idx + n : n * n + 1;
        int left = col != 1 ? idx - 1 : idx;
        int right = col != n ? idx + 1 : idx;

        connect(idx, up);
        connect(idx, down);
        connect(idx, left);
        connect(idx, right);

    }

    private void connect(int idx, int neighbor){
        if (open[neighbor]){
            wquf.union(idx, neighbor);
            if (neighbor != n*n+1){
                onlyTopUF.union(idx, neighbor);
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col){
        checker(row, col);

        int idx = this.getId(row, col);
        return open[idx];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col){
        checker(row, col);
        int idx = this.getId(row, col);
        return wquf.connected(idx, n * n) && onlyTopUF.connected(idx, n*n);
    }

    // returns the number of open sites
    public int numberOfOpenSites(){
        return this.numberOpenSites;
    }

    // does the system percolate?
    public boolean percolates()
    {
        return wquf.connected(n * n, n * n + 1);
    }

    private int getId(int row, int col){
        return (row - 1) * n + (col - 1);
    }
    private void checker(int x, int y){
        if (x < 1 || x > n || y < 1 || y > n )  {
            throw new java.lang.IllegalArgumentException();
        }
    }


    public static void main(String[] args) {
        Percolation p = new Percolation(4);
        p.open(4, 1);
        p.open(2, 1);
        p.open(3, 1);
        p.open(1, 1);

        System.out.println(p.percolates());
        System.out.println(p.isFull(4, 1));

    }
}
