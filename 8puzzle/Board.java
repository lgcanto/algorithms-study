public class Board {

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        // TODO
    }
                                           
    // string representation of this board
    public String toString() {
        // TODO
        return "";
    }

    // board dimension n
    public int dimension() {
        // TODO
        return 0;
    }

    // number of tiles out of place
    public int hamming() {
        // TODO
        return 0;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        // TODO
        return 0;
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