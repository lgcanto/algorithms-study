import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.princeton.cs.algs4.StdOut;

public class Board {

    private final int dimension;
    private final int[][] tiles;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.tiles = tiles;
        dimension = tiles.length;
    }
                                           
    // string representation of this board
    public String toString() {
        String boardString = String.valueOf(dimension);
        for (int i = 0; i < dimension; i++) {
            boardString = boardString.concat("\n ");
            for (int j = 0; j < dimension; j++) {
                boardString = boardString.concat(" ");
                boardString = boardString.concat(String.valueOf(tiles[i][j]));
                boardString = boardString.concat(" ");
            }
        }
        return boardString;
    }

    // board dimension n
    public int dimension() {
        return dimension;
    }

    // number of tiles out of place
    public int hamming() {
        int hamming = 0;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                int tileValue = tiles[i][j];
                int correctTileValue = j + i*dimension + 1;
                if (tileValue != 0 && tileValue != correctTileValue) {
                    hamming++;
                }
            }
        }
        return hamming;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int manhattan = 0;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                int tileValue = tiles[i][j];
                int correctTileValue = j + i*dimension + 1;
                if (tileValue != 0 && tileValue != correctTileValue) {
                    int[] correctIJPosition = getCorrectIJPosition(tileValue);
                    int mDistance = Math.abs(correctIJPosition[0] - i) + Math.abs(correctIJPosition[1] - j);
                    manhattan = manhattan + mDistance;
                }
            }
        }
        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        int counter = 0;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                boolean emptyIsRight = tiles[i][j] == 0 && i == j && j == (dimension - 1);
                if (++counter != tiles[i][j] && !emptyIsRight) {
                    return false;
                }
            }
        }
        return true;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y instanceof Board) {
            Board that = (Board) y;
            if (this.dimension == that.dimension) {
                for (int i = 0; i < dimension; i++) {
                    for (int j = 0; j < dimension; j++) {
                        if (this.tiles[i][j] != that.tiles[i][j]) {
                            return false;
                        }
                    }
                }
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        int[] emptyIJPosition = getIJPosition(0);
        int emptyI = emptyIJPosition[0];
        int emptyJ = emptyIJPosition[1];

        List<Board> neighbors = new ArrayList<>();

        if (emptyI > 0) {
            int[][] topNeighborTiles = copyAndReturnTiles(tiles);
            replaceTile(topNeighborTiles, emptyI, emptyJ, emptyI - 1, emptyJ);
            Board topNeighbor = new Board(topNeighborTiles);
            neighbors.add(topNeighbor);
        }

        if (emptyI < (dimension - 1)) {
            int[][] bottomNeighborTiles = copyAndReturnTiles(tiles);
            replaceTile(bottomNeighborTiles, emptyI, emptyJ, emptyI + 1, emptyJ);
            Board bottomNeighbor = new Board(bottomNeighborTiles);
            neighbors.add(bottomNeighbor);
        }

        if (emptyJ > 0) {
            int[][] leftNeighborTiles = copyAndReturnTiles(tiles);
            replaceTile(leftNeighborTiles, emptyI, emptyJ, emptyI, emptyJ - 1);
            Board leftNeighbor = new Board(leftNeighborTiles);
            neighbors.add(leftNeighbor);
        }

        if (emptyJ < (dimension - 1)) {
            int[][] rightNeighborTiles = copyAndReturnTiles(tiles);
            replaceTile(rightNeighborTiles, emptyI, emptyJ, emptyI, emptyJ + 1);
            Board rightNeighbor = new Board(rightNeighborTiles);
            neighbors.add(rightNeighbor);
        }

        return neighbors;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        Iterable<Board> neighbors = neighbors();
        if (neighbors.iterator().hasNext()) {
            return neighbors.iterator().next();
        }
        else {
            return null;
        }
    }

    private int[][] copyAndReturnTiles(int[][] tiles) {
        int[][] tilesCopy = new int[dimension][dimension];
        for (int i = 0; i < dimension; i++)
        {
            for (int j = 0; j < dimension; j++)
            {
                tilesCopy[i][j] = tiles[i][j];
            }
        }
        return tilesCopy;
    }

    private void replaceTile(int[][] tiles, int aI, int aJ, int bI, int bJ) {
        int aHold = tiles[aI][aJ];
        tiles[aI][aJ] = tiles[bI][bJ];
        tiles[bI][bJ] = aHold;
    }

    private int[] getIJPosition(int tileValue) {
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (tiles[i][j] == tileValue) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    private int[] getCorrectIJPosition(int correctTileValue) {
        int counter = 0;
        if (correctTileValue == 0) {
            return new int[]{dimension - 1, dimension - 1};
        }
        else {
            for (int i = 0; i < dimension; i++) {
                for (int j = 0; j < dimension; j++) {
                    if (++counter == correctTileValue) {
                        return new int[]{i, j};
                    }
                }
            }
        }
        return null;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] aInitialTiles = new int[3][3];
        aInitialTiles[0][0] = 1;
        aInitialTiles[0][1] = 2;
        aInitialTiles[0][2] = 3;
        aInitialTiles[1][0] = 4;
        aInitialTiles[1][1] = 5;
        aInitialTiles[1][2] = 6;
        aInitialTiles[2][0] = 7;
        aInitialTiles[2][1] = 8;
        aInitialTiles[2][2] = 0;
        Board aBoard = new Board(aInitialTiles);
        int[][] bInitialTiles = new int[3][3];
        bInitialTiles[0][0] = 8;
        bInitialTiles[0][1] = 1;
        bInitialTiles[0][2] = 3;
        bInitialTiles[1][0] = 4;
        bInitialTiles[1][1] = 0;
        bInitialTiles[1][2] = 2;
        bInitialTiles[2][0] = 7;
        bInitialTiles[2][1] = 6;
        bInitialTiles[2][2] = 5;
        Board bBoard = new Board(bInitialTiles);
        StdOut.println("Board A toString():");
        StdOut.println(aBoard.toString());
        StdOut.println("Board B toString():");
        StdOut.println(bBoard.toString());
        StdOut.println("Board A dimension():");
        StdOut.println(aBoard.dimension());
        StdOut.println("Board A hamming():");
        StdOut.println(aBoard.hamming());
        StdOut.println("Board B hamming():");
        StdOut.println(bBoard.hamming());
        StdOut.println("Board A manhattan():");
        StdOut.println(aBoard.manhattan());
        StdOut.println("Board B manhattan():");
        StdOut.println(bBoard.manhattan());
        StdOut.println("Board A isGoal():");
        StdOut.println(aBoard.isGoal());
        StdOut.println("Board B isGoal():");
        StdOut.println(bBoard.isGoal());
        StdOut.println("A.equals(A):");
        StdOut.println(aBoard.equals(aBoard));
        StdOut.println("A.equals(B):");
        StdOut.println(aBoard.equals(bBoard));
        StdOut.println("B neighbors():");
        Iterator<Board> bNeighborsIterator = bBoard.neighbors().iterator();
        while (bNeighborsIterator.hasNext()) {
            StdOut.println(bNeighborsIterator.next().toString());
        }
        StdOut.println("B twin():");
        StdOut.println(bBoard.twin());
    }

}