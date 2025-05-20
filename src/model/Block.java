package model;

public class Block implements Comparable<Block> {

    /* ATTRIBUTE */
    private char tag;
    private int row;
    private int col;
    
    /* CONSTRUCTOR */
    public Block(int row, int col) {
        this.tag = ' ';
        this.row = row;
        this.col = col;
    }
    public Block(char tag, int row, int col) {
        this.tag = tag;
        this.row = row;
        this.col = col;
    }
    
    /* COPY CONSTRUCTOR */
    public Block(Block b) {
        this.tag = b.tag;
        this.row = b.row;
        this.col = b.col;
    }

    /* GETTER and SETTER */
    public char getTag() {
        return this.tag;
    }
    public int getRow() {
        return this.row;
    }
    public int getCol() {
        return this.col;
    }
    public void setTag(char tag) {
        this.tag = tag;
    }
    public void setRow(int row) {
        this.row = row;
    }
    public void setCol(int col) {
        this.col = col;
    }

    /* CHECK */
    public Boolean isValid() {
        return this.tag != ' ';
    }
    public Boolean isEmpty() {
        return this.tag == '.';
    }
    public Boolean isExit() {
        return this.tag == 'K';
    }
    public Boolean isPiece() {
        return this.isValid() && !(this.isEmpty() || this.isExit());
    }
    public Boolean isPrimary() {
        return this.tag == 'P';
    }

    /* DISTANCE OPERATION */
    public int rowDistanceTo(Block b) {
        return this.row - b.row;
    }
    public int colDistanceTo(Block b) {
        return this.col - b.col;
    }
    public Double euclideanDistanceTo(Block b) {
        return Math.sqrt(Math.pow(this.rowDistanceTo(b), 2) + Math.pow(this.colDistanceTo(b), 2));
    }
    public int ManhattanDistance(Block b) {
        return Math.abs(this.row - b.row) + Math.abs(this.col - b.col);
    }

    /* PRINT BLOCK */
    @Override
    public String toString() {
        return String.format("(%c: %d, %d)", this.tag, this.row, this.col);
    }

    /* COMPARE */
    @Override
    public int compareTo(Block b) {
        int rowDiff = this.row - b.row;
        int colDiff = this.col - b.col;
        if (rowDiff < 0) {
            return -1;
        } else if (rowDiff > 0) {
            return 1;
        } else {
            if (colDiff < 0) {
                return -1;
            } else if (colDiff > 0) {
                return 1;
            } else {
                return 0;
            }
        }
    }

}
