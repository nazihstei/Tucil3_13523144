package puzzle;

public class Piece {
    private char tag;
    private int row;
    private int col;
    private int size;
    private Direction direction;

    /* GETTER */
    public char getTag(){
        return this.tag;
    }
    public int getRow() {
        return this.row;
    }
    public int getCol() {
        return this.col;
    }
    public int getSize() {
        return this.size;
    }
    public Direction getDirection() {
        return this.direction;
    }

    /* CONSTRUCTOR */
    public Piece(char tag, int row, int col, int size, Direction dir) {
        this.tag = tag;
        this.row = row;
        this.col = col;
        this.size = size;
        this.direction = dir;
    }

    /* MOVEMENT */
    public void putOn(Board b) {

    }
    public void moveUp(Board b) {
        if (this.direction == Direction.HORIZONTAL) {
            return;
        }
        this.row++;
        b.
    }

}
