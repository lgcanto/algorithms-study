import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
        List<Board> solutionList = new ArrayList<>();
        SearchNode initialSearchNode = new SearchNode(initial, 0);
        SearchNode twinInitialSearchNode = new SearchNode(initial.twin(), 0);
        SearchNode previousSearchNode = null;
        SearchNode twinPreviousSearchNode = null;
        pq.insert(initialSearchNode);
        twinPq.insert(twinInitialSearchNode);

        while (!solutionFound) {
            if (!executeTwinThread) {
                SearchNode deletedSearchNode = pq.delMin();
                solutionList.add(deletedSearchNode.board);
                if (deletedSearchNode.board.isGoal()) {
                    solutionFound = true;
                }
                else {
                    movesHolder++;
                    Iterator<Board> neighborsIterator = deletedSearchNode.board.neighbors().iterator();
                    while (neighborsIterator.hasNext()) {
                        Board board = neighborsIterator.next();
                        if (previousSearchNode == null || !board.equals(previousSearchNode.board)) {
                          pq.insert(new SearchNode(board, movesHolder));
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
                    twinMovesHolder++;
                    Iterator<Board> twinNeighborsIterator = twinDeletedSearchNode.board.neighbors().iterator();
                    while (twinNeighborsIterator.hasNext()) {
                      Board twinBoard = twinNeighborsIterator.next();
                      if (twinPreviousSearchNode == null || !twinBoard.equals(twinPreviousSearchNode.board)) {
                        twinPq.insert(new SearchNode(twinBoard, twinMovesHolder));
                      }
                    }
                }
                twinPreviousSearchNode = twinDeletedSearchNode;
            }
            // TODO: remove hack
            if (movesHolder > initial.dimension()*initial.dimension()) {
                twinSolutionFound = true;
                break;
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
            this.solution = solutionList;
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
        //  private final int hammingPriority;
        private final int manhattanPriority;

        private SearchNode(Board board, int moves) {
            this.board = board;
            // this.hammingPriority = board.hamming() + moves;
            this.manhattanPriority = board.manhattan() + moves;
        }

        // public int compareTo(SearchNode that) {
        //   return this.hammingPriority - that.hammingPriority;
        // }

        public int compareTo(SearchNode that) {
          return this.manhattanPriority - that.manhattanPriority;
        }
    }
}