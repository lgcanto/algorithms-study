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
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
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
                    int mDistance = 0;
                    int correctI = 0; //TODO: use dimension and correctTileValue as inputs
                    int correctJ = 0; //TODO: use dimension and correctTileValue as inputs
                    mDistance = Math.abs(correctI - i) + Math.abs(correctJ - j);
                    manhattan = manhattan + mDistance;
                }
            }
        }
        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        // TODO
        return false;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        // TODO
        return false;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        // TODO
        return (Iterable<Board>) new Object();
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        // TODO
        return new Board(new int[1][1]);
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        // TODO
    }

}