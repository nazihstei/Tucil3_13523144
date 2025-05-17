package model;

import java.util.ArrayDeque;
import java.util.Deque;


public class Piece {
    
    /* CONST ENUMERATE */
    enum Direction {
        VERTICAL,
        HORIZONTAL,
        BOTH
    }
    
    /* ATTRIBUTE */
    private Deque<Block> blocks;
    private char tag;
    private Direction direction;

    /* CONSTRUCTOR */
    public Piece(char tag) {
        this.tag = tag;
        this.blocks = new ArrayDeque<>();
    }
    public Piece(char tag, Deque<Block> blocks) {
        this.tag = tag;
        this.blocks = blocks;
        this.setDirection();
    }
    public Piece(Block block) {
        if (block.getTag() == '.' || block.getTag() == ' ' || block.getTag() == 'K') return;
        this.tag = block.getTag();
        this.blocks = new ArrayDeque<>();
        this.blocks.add(block);
        this.setDirection();
    }

    /* SET DIRECTION */
    public void setDirection() {
        if (blocks.size() == 1) {
            this.direction = Direction.BOTH;
        } else {
            if (blocks.getFirst().getRow() == blocks.getLast().getRow()) {
                this.direction = Direction.HORIZONTAL;
            } else {
                this.direction = Direction.VERTICAL;
            }
        }
    }

    /* GETTER and SETTER */
    public char getTag() {
        return this.tag;
    }
    public void addBlock(Block b) {
        this.blocks.add(b);
        this.setDirection();
    }

    /* ADDITONAL GETTER */
    public Block getHead() {
        return this.blocks.getFirst();
    }
    public Block getTail() {
        return this.blocks.getLast();
    }
    public String getDirection() {
        return this.direction.name();
    }

    /* MOVEMENT */
    public Boolean canMove(Block b) {
        // conditional preparation
        Boolean vertical    = ((this.direction == Direction.VERTICAL || this.direction == Direction.BOTH) && this.getHead().getCol() == b.getCol());
        Boolean horizontal  = ((this.direction == Direction.HORIZONTAL || this.direction == Direction.BOTH) && this.getHead().getRow() == b.getRow());
        Boolean forward     = (this.getHead().colDistanceTo(b) == -1 || this.getHead().rowDistanceTo(b) == -1);
        Boolean backward    = (this.getTail().colDistanceTo(b) == 1 || this.getTail().rowDistanceTo(b) == 1);
        return  b.isValid() 
        && (((vertical && forward) || (horizontal && forward)) 
        || ((vertical && backward) || (horizontal && backward)));
    }
    public void move(Block b, Board B) {
        // validity check
        if (!b.isValid()) return;
        
        // conditional preparation
        Boolean vertical    = ((this.direction == Direction.VERTICAL || this.direction == Direction.BOTH) && this.getHead().getCol() == b.getCol());
        Boolean horizontal  = ((this.direction == Direction.HORIZONTAL || this.direction == Direction.BOTH) && this.getHead().getRow() == b.getRow());
        Boolean forward     = (this.getHead().colDistanceTo(b) == -1 || this.getHead().rowDistanceTo(b) == -1);
        Boolean backward    = (this.getTail().colDistanceTo(b) == 1 || this.getTail().rowDistanceTo(b) == 1);
        
        // execution
        if ((vertical && forward) || (horizontal && forward)) {
            b.setTag(this.getHead().getTag());
            this.blocks.addFirst(b);
            this.getTail().setTag('.');
            B.addEmptyBlock(b);
            B.removeEmptyBlocks();
            this.blocks.removeLast();
        } else if ((vertical && backward) || (horizontal && backward)) {
            b.setTag(this.getHead().getTag());
            this.blocks.addLast(b);
            this.getHead().setTag('.');
            B.addEmptyBlock(b);
            B.removeEmptyBlocks();
            this.blocks.removeFirst();
        }
        
    }

    /* PRINT PIECE */
    public String toString() {
        String result = String.format("[%s] %c : ", this.direction, this.tag);
        for (Block blok : this.blocks) {
            result = result + " " + blok.toString();
        }
        return result;
    }
    
}
