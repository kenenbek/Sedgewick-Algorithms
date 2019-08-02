import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.LinkedList;

public class Solver {
    private SearchNode goalNode;
    private boolean isSolvable;

    private class SearchNode implements Comparable<SearchNode>{
        private Board current;
        private SearchNode previous;
        private int moves;
        private int manhattan;

        public SearchNode(Board b, int moves, SearchNode p){
            current = b;
            this.moves = moves;
            previous = p;

            manhattan = b.manhattan();
        }

        private Board getCurrentBoard(){
            return current;
        }

        private SearchNode getPreviousSearchNode(){
            return previous;
        }

        private int getMoves(){
            return moves;
        }

        private int priority(){
            return manhattan + moves;
        }


        @Override
        public int compareTo(SearchNode o) {
            return this.priority() - o.priority();
        }
    }
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial){
        if (initial == null) {
            throw new java.lang.IllegalArgumentException();
        }
        MinPQ<SearchNode> queue = new MinPQ<>();
        queue.insert(new SearchNode(initial, 0, null));


        while (queue.size() > 0){
            SearchNode node = queue.delMin();
            Board currentBoard = node.getCurrentBoard();
            int n = node.getMoves();
            if (currentBoard.isGoal()){
                isSolvable = true;
                goalNode = node;
                break;
            }

            if (currentBoard.hamming() == 2 && currentBoard.twin().isGoal()){
                isSolvable = false;
                break;
            }

            SearchNode previousSearchNode = node.getPreviousSearchNode();
            Board previousBoard = previousSearchNode != null ? previousSearchNode.getCurrentBoard() : null;

            for (Board nextBoard: currentBoard.neighbors()) {
                if (previousBoard != null && previousBoard.equals(nextBoard)){
                    continue;
                }
                queue.insert(new SearchNode(nextBoard, n+1, node));
            }
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable(){
        return isSolvable;
    }

    // min number of moves to solve initial board
    public int moves(){
        return isSolvable() ? goalNode.getMoves() : -1;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution(){
        if (!isSolvable()) {
            return null;
        }
        LinkedList<Board> answer = new LinkedList<>();
        SearchNode s = goalNode;
        while (s != null){
            answer.addFirst(s.getCurrentBoard());
            s = s.getPreviousSearchNode();
        }
        return answer;
    }

    // test client (see below)
    public static void main(String[] args){
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        System.out.println(initial);
        System.out.println(initial.twin());
       return;

        // // solve the puzzle
        // Solver solver = new Solver(initial);
        //
        // // print solution to standard output
        // if (!solver.isSolvable())
        //     StdOut.println("No solution possible");
        // else {
        //     StdOut.println("Minimum number of moves = " + solver.moves());
        //     for (Board board : solver.solution())
        //         StdOut.println(board);
        // }
    }

}
