import java.util.ArrayList;
import java.util.List;

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
        String boardString = String.valueOf(dimension());
        for (int i = 0; i < dimension; i++) {
            boardString.concat("\n ");
            for (int j = 0; j < dimension; j++) {
                boardString.concat(" ");
                boardString.concat(String.valueOf(tiles[i][j]));
                boardString.concat(" ");
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
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                int tileValue = tiles[i][j];
                int correctTileValue = j + i*dimension + 1;
                if (tileValue != 0 && tileValue != correctTileValue) {
                    int[] correctIJPosition = getCorrectIJPosition(correctTileValue);
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
                if (++counter != tiles[i][j] || emptyIsRight) {
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
            int[][] topNeighborTiles = tiles.clone();
            replaceTile(topNeighborTiles, emptyI, emptyJ, emptyI - 1, emptyJ);
            Board topNeighbor = new Board(topNeighborTiles);
            neighbors.add(topNeighbor);
        }

        if (emptyI < (dimension - 1)) {
            int[][] bottomNeighborTiles = tiles.clone();
            replaceTile(bottomNeighborTiles, emptyI, emptyJ, emptyI + 1, emptyJ);
            Board bottomNeighbor = new Board(bottomNeighborTiles);
            neighbors.add(bottomNeighbor);
        }

        if (emptyJ > 0) {
            int[][] leftNeighborTiles = tiles.clone();
            replaceTile(leftNeighborTiles, emptyI, emptyJ, emptyI, emptyJ - 1);
            Board leftNeighbor = new Board(leftNeighborTiles);
            neighbors.add(leftNeighbor);
        }

        if (emptyJ < (dimension - 1)) {
            int[][] rightNeighborTiles = tiles.clone();
            replaceTile(rightNeighborTiles, emptyI, emptyJ, emptyI, emptyJ + 1);
            Board rightNeighbor = new Board(rightNeighborTiles);
            neighbors.add(rightNeighbor);
        }

        return neighbors;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        // TODO
        return new Board(new int[1][1]);
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
        // TODO
    }

}