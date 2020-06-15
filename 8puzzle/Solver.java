import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;
import java.util.Comparator;
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
        boolean executeTwinThread = true;

        // can be changed to use manhattan priority
        MinPQ pq = new MinPQ(SearchNode.BY_HAMMING);
        MinPQ twinPq = new MinPQ(SearchNode.BY_HAMMING);
        int moves = 0;
        int twinMoves = 0;
        List<Board> solutionList = new ArrayList<>();
        SearchNode initialSearchNode = new SearchNode(initial, null, 0);
        SearchNode twinInitialSearchNode = new SearchNode(initial.twin(), null, 0);
        pq.insert(initialSearchNode);
        twinPq.insert(twinInitialSearchNode);

        while(!solutionFound) {
            if (!executeTwinThread) {
                SearchNode deletedSearchNode = (SearchNode) pq.delMin();
                solutionList.add(deletedSearchNode.board);
                if (deletedSearchNode.board.isGoal()) {
                    solutionFound = true;
                }
                else {
                    moves++;
                    Iterator<Board> neighborsIterator = deletedSearchNode.board.neighbors().iterator();
                    while (neighborsIterator.hasNext()) {
                        pq.insert(new SearchNode(neighborsIterator.next(), deletedSearchNode, moves));
                    }
                }
            }
            else {
                SearchNode deletedTwinSearchNode = (SearchNode) twinPq.delMin();
                if (deletedTwinSearchNode.board.isGoal()) {
                    solutionFound = true;
                    twinSolutionFound = true;
                }
                else {
                    twinMoves++;
                    Iterator<Board> twinNeighborsIterator = deletedTwinSearchNode.board.neighbors().iterator();
                    while (twinNeighborsIterator.hasNext()) {
                        twinPq.insert(new SearchNode(twinNeighborsIterator.next(), deletedTwinSearchNode, twinMoves));
                    }
                }
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
            this.moves = moves;
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

    private static class SearchNode {
        private final Board board;
        private final int moves;
        private final int hammingPriority;
        private final int manhattanPriority;
        private final SearchNode previousSearchNode;
        private static final Comparator<SearchNode> BY_HAMMING = new HammingComparator();
        private static final Comparator<SearchNode> BY_MANHATTAN = new ManhattanComparator();

        private SearchNode(Board board, SearchNode previousSearchNode, int moves) {
            this.board = board;
            this.moves = moves;
            this.previousSearchNode = previousSearchNode;
            this.hammingPriority = board.hamming() + moves;
            this.manhattanPriority = board.manhattan() + moves;
        }

        private static class HammingComparator implements Comparator<SearchNode> {
            public int compare (SearchNode a, SearchNode b) {
                return a.hammingPriority - b.hammingPriority;
            }
        }

        private static class ManhattanComparator implements Comparator<SearchNode> {
            public int compare (SearchNode a, SearchNode b) {
                return a.manhattanPriority - b.manhattanPriority;
            }
        }
    }
}