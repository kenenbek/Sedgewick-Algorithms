import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

public class Board {
    private final int[][] tiles;
    private final int N;
    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles){
        this.tiles = tiles;
        N = tiles.length;
    }

    // string representation of this board
    public String toString(){
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                sb.append(i);
                sb.append(" ");
            }
            sb.replace(sb.length()-1, sb.length(), "\n");
        }
        sb.delete(sb.length()-1, sb.length());
        return sb.toString();
    }

    // board dimension n
    public int dimension(){
        return N;
    }

    // number of tiles out of place
    public int hamming(){
        int error = 0;
        int number = 1;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tiles[i][j] == 0 || number == 10) continue;
                if (tiles[i][j] != number++) error++;
            }
        }
        return error;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int number = 1;
        int error = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tiles[i][j] == 0) continue;
                if (tiles[i][j] != number++ || number == 10) {
                    int m = (number-1) / N ;
                    int n = (number-1) % N ;
                    error += Math.abs(m - i) + Math.abs(n - j);
                }
            }
        }
        return error;
    }

    // is this board the goal board?
    public boolean isGoal() {
        int number = 1;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tiles[i][j] != number++) return false;
                if (number == 10) number = 0;
            }
        }
        return true;
    }

    // does this board equal y?
    public boolean equals(Object y){
        if (getClass() != y.getClass())
            return false;
        Board b_y = (Board) y;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tiles[i][j] != b_y.tiles[i][j]) return false;
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        LinkedList<Board> neighbors = new LinkedList<>();
        // up down left right
        int[] zeroLocs = getZeroLocation();
        int x = zeroLocs[0];
        int y = zeroLocs[1];

        // get up neighbor
        if (x - 1 >= 0){
            Board neighbor = createNeighbor(x, y, x-1, y);
            neighbors.add(neighbor);
        }
        // get down neighbor
        if (x + 1 < N){
            Board neighbor = createNeighbor(x, y, x+1, y);
            neighbors.add(neighbor);
        }
        // get right neighbor
        if (y + 1 < N){
            Board neighbor = createNeighbor(x, y, x, y+1);
            neighbors.add(neighbor);
        }

        // get left neighbor
        if (y - 1 >= 0){
            Board neighbor = createNeighbor(x, y, x, y-1);
            neighbors.add(neighbor);
        }
        return neighbors;
    }


    private int[] getZeroLocation(){
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tiles[i][j] == 0) return new int[]{i, j};
            }
        }
        return new int[2];
    }


    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] copyTiles = createCopyArray();
        int temp = copyTiles[0][0];
        copyTiles[0][0] = copyTiles[0][1];
        copyTiles[0][1] = temp;
        return new Board(copyTiles);
    }

    private int[][] createCopyArray(){
        int[][] copy = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                copy[i][j] = tiles[i][j];
            }
    }
        return copy;
    }



    private Board createNeighbor(int rOld, int cOld, int rNew, int cNew){
        int[][] copyTiles = createCopyArray();
        int temp = copyTiles[rNew][cNew];
        copyTiles[rNew][cNew] = 0;
        copyTiles[rOld][cOld] = temp;

        return new Board(copyTiles);
    }

    // unit testing (not graded)
    public static void main(String[] args){}

}