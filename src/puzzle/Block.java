package puzzle;

public class Block {
    public char tag;
    public int row;
    public int col;

    public Block(int row, int col) {
        this.row = row;
        this.col = col;
        this.tag = ' ';
    }
    public Block(int row, int col, char tag) {
        this.row = row;
        this.col = col;
        this.tag = tag;
    }

    public Boolean isValid() {
        return this.tag != ' ';
    }
    public Boolean isEmpty() {
        return this.tag == '.';
    }
    public Boolean isExit() {
        return this.tag == 'K';
    }
    public static Boolean isSame(Block b1, Block b2) {
        return b1.row == b2.row && b1.col == b2.col && b1.tag == b2.tag;
    }

    public static Direction direction(Block b1, Block b2) {
        if (isSame(b1, b2)) {
            return null;
        }
        if (b1.row == b2.row) {
            return Direction.HORIZONTAL;
        }
        if (b1.col == b2.col) {
            return Direction.VERTICAL;
        }
        return null;
    }
}
