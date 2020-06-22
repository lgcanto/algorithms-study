import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

import java.util.Iterator;

public class Solver {

    private final int moves;
    private final boolean solvable;
    private final Iterable<Board> solution;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException();
        }

        boolean solutionFound = false;
        boolean twinSolutionFound = false;
        boolean executeTwinThread = false;

        MinPQ<SearchNode> pq = new MinPQ<>();
        MinPQ<SearchNode> twinPq = new MinPQ<>();
        int movesHolder = 0;
        int twinMovesHolder = 0;
        SearchNode finalSearchNode = null;
        SearchNode initialSearchNode = new SearchNode(initial, null, 0);
        SearchNode twinInitialSearchNode = new SearchNode(initial.twin(), null, 0);
        SearchNode previousSearchNode = null;
        SearchNode twinPreviousSearchNode = null;
        pq.insert(initialSearchNode);
        twinPq.insert(twinInitialSearchNode);

        while (!solutionFound) {
            if (!executeTwinThread) {
                SearchNode deletedSearchNode = pq.delMin();

                if (deletedSearchNode.board.isGoal()) {
                    solutionFound = true;
                    finalSearchNode = deletedSearchNode;
                    movesHolder = deletedSearchNode.move;
                }
                else {
                    movesHolder = deletedSearchNode.move + 1;
                    Iterator<Board> neighborsIterator = deletedSearchNode.board.neighbors().iterator();
                    while (neighborsIterator.hasNext()) {
                        Board board = neighborsIterator.next();
                        if (previousSearchNode == null || !board.equals(previousSearchNode.board)) {
                          pq.insert(new SearchNode(board, deletedSearchNode, movesHolder));
                        }
                    }
                }
                previousSearchNode = deletedSearchNode;
            }
            else {
                SearchNode twinDeletedSearchNode = twinPq.delMin();
                if (twinDeletedSearchNode.board.isGoal()) {
                    solutionFound = true;
                    twinSolutionFound = true;
                }
                else {
                    twinMovesHolder = twinDeletedSearchNode.move + 1;
                    Iterator<Board> twinNeighborsIterator = twinDeletedSearchNode.board.neighbors().iterator();
                    while (twinNeighborsIterator.hasNext()) {
                      Board twinBoard = twinNeighborsIterator.next();
                      if (twinPreviousSearchNode == null || !twinBoard.equals(twinPreviousSearchNode.board)) {
                        twinPq.insert(new SearchNode(twinBoard, twinDeletedSearchNode, twinMovesHolder));
                      }
                    }
                }
                twinPreviousSearchNode = twinDeletedSearchNode;
            }
            executeTwinThread = !executeTwinThread;
        }

        if (twinSolutionFound) {
            this.solvable = false;
            this.moves = -1;
            this.solution = null;
        }
        else {
            this.solvable = true;
            this.moves = movesHolder;
            Stack<Board> solutionStack = new Stack<>();
            while (finalSearchNode.previousSearchNode != null) {
                solutionStack.push(finalSearchNode.board);
                finalSearchNode = finalSearchNode.previousSearchNode;
            }
            solutionStack.push(finalSearchNode.board);
            this.solution = solutionStack;
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return this.solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return this.moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return this.solution;
    }

    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

    private static class SearchNode implements Comparable<SearchNode> {
        private final Board board;
        private final SearchNode previousSearchNode;
        //  private final int hammingPriority;
        private final int manhattanPriority;
        private final int move;

        private SearchNode(Board board, SearchNode previousSearchNode, int move) {
            this.board = board;
            this.previousSearchNode = previousSearchNode;
            this.move = move;
            // this.hammingPriority = board.hamming() + move;
            this.manhattanPriority = board.manhattan() + this.move;
        }

        // public int compareTo(SearchNode that) {
        //   return this.hammingPriority - that.hammingPriority;
        // }

        public int compareTo(SearchNode that) {
          return this.manhattanPriority - that.manhattanPriority;
        }
    }
}