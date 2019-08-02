import java.util.Arrays;
import java.util.LinkedList;

public final class Board {
    private final int[][] tiles;
    private final int N;
    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles){
        N = tiles.length;
        this.tiles = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                this.tiles[i][j] = tiles[i][j];
            }
        }
    }

    // string representation of this board
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(N);
        sb.append("\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                sb.append(tiles[i][j]);
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
                if (number == N * N) continue;
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
                if (tiles[i][j] == 0){
                    number++;
                    continue;
                }
                if (tiles[i][j] != number) {
                    int m = (tiles[i][j]-1) / N ;
                    int n = (tiles[i][j]-1) % N ;
                    int manh = Math.abs(m - i) + Math.abs(n - j);
                    error += manh;
                }
                number++;
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
                if (number == N * N) number = 0;
            }
        }
        return true;
    }

    // does this board equal y?
    public boolean equals(Object y){
        if (y == null || getClass() != y.getClass())
            return false;
        Board b_y = (Board) y;
        return Arrays.deepEquals(tiles, b_y.tiles);
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

        for (int i = 0; i < N; i++) {
            for (int j = 1; j < N; j++) {
                if (tiles[i][j] != 0 && tiles[i][j-1] != 0){
                    int temp = copyTiles[i][j-1];
                    copyTiles[i][j-1] = copyTiles[i][j];
                    copyTiles[i][j] = temp;
                    return new Board(copyTiles);
                }
            }
        }


        return new Board(new int[N][N]);
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