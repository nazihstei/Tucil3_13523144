package model;

public class Block {

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

    /* PRINT BLOCK */
    public String toString() {
        return String.format("(%c: %d, %d)", this.tag, this.row, this.col);
    }

}
