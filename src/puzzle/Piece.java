package puzzle;

enum Direction {
    VERTICAL,
    HORIZONTAL
}

public class Piece {
    private char tag;
    private int row;
    private int col;
    private int size;
    private Direction direction;

    public Piece(char tag, int row, int col, int size, Direction dir) {
        this.tag = tag;
        this.row = row;
        this.col = col;
        this.size = size;
        this.direction = dir;
    }

    public Boolean isValidPosition() {
        return true;
        // nanti diganti
    }
}
