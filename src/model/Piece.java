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
    public Piece(Piece p) {
        this.tag = p.tag;
        this.blocks = new ArrayDeque<>();
        for (Block b : p.blocks) { // make new Deque, but shallow copy the blocks
            this.blocks.addLast(b);
        }
        this.direction = p.direction;
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
    public Direction getDirection() {
        return this.direction;
    }

    /* BLOCK ACCESS */
    public Block nextForward(Board b) {
        try {
            if (this.direction.equals(Direction.VERTICAL) || this.direction.equals(Direction.BOTH)) {
                Block head = this.getHead();
                return b.getMap().get(head.getRow()-1).get(head.getCol());
            } else {
                Block tail = this.getTail();
                return b.getMap().get(tail.getRow()).get(tail.getCol()+1);
            }
        } catch (Exception e) {
            return null;
        }
    }
    public Block nextBackward(Board b) {
        try {
            if (this.direction.equals(Direction.VERTICAL) || this.direction.equals(Direction.BOTH)) {
                Block tail = this.getTail();
                return b.getMap().get(tail.getRow()+1).get(tail.getCol());
            } else {
                Block head = this.getHead();
                return b.getMap().get(head.getRow()).get(head.getCol()-1);
            }
        } catch (Exception e) {
            return null;
        }    
    }

    /* MOVEMENT */
    public Boolean canMove(Block b, Board B) {
        // validity check
        if (b==null || !b.isValid() || this.blocks.isEmpty()) return false;
        // System.out.printf("%c is valid\n", this.tag);
        
        // conditional preparation
        // Boolean vertical    = ((this.direction.equals(Direction.VERTICAL) || this.direction.equals(Direction.BOTH)) && this.getHead().getCol() == b.getCol());
        // Boolean horizontal  = ((this.direction.equals(Direction.HORIZONTAL) || this.direction.equals(Direction.BOTH)) && this.getHead().getRow() == b.getRow());
        Boolean forward     = (b == this.nextForward(B) && b.isEmpty());
        Boolean backward    = (b == this.nextBackward(B) && b.isEmpty());
        
        // System.out.printf("%c (v: %b) (h: %b) (f: %b) (b: %b) \n", this.tag, vertical, horizontal, forward, backward);
        
        // return  (((vertical && forward) || (horizontal && forward)) 
        // || ((vertical && backward) || (horizontal && backward)));
        return (forward || backward);
    }
    // public void move(Block b, Board B) {
    //     // validity check
    //     if (!this.canMove(b, B)) return;
        
    //     // conditional preparation
    //     Boolean vertical    = ((this.direction.equals(Direction.VERTICAL) || this.direction.equals(Direction.BOTH)) && this.getHead().getCol() == b.getCol());
    //     Boolean horizontal  = ((this.direction.equals(Direction.HORIZONTAL) || this.direction.equals(Direction.BOTH)) && this.getHead().getRow() == b.getRow());
    //     Boolean forward     = (b == this.nextForward(B) && b.isEmpty());
    //     Boolean backward    = (b == this.nextBackward(B) && b.isEmpty());
        
    //     // execution
    //     if ((vertical && forward) || (horizontal && forward)) {
    //         b.setTag(this.getHead().getTag());
    //         this.blocks.addFirst(b);
    //         this.getTail().setTag('.');
    //         this.blocks.removeLast();
    //     } else if ((vertical && backward) || (horizontal && backward)) {
    //         b.setTag(this.getHead().getTag());
    //         this.blocks.addLast(b);
    //         this.getHead().setTag('.');
    //         this.blocks.removeFirst();
    //     }
    // }
    public Piece moveForward(Board B) {
        if (!this.canMove(this.nextForward(B), B)) {
            return this;
        }
        if (this.blocks.isEmpty()) {
            return this;
        } else {
            Deque<Block> temp = new ArrayDeque<>();
            this.nextForward(B).setTag(this.tag);
            temp.addLast(this.nextForward(B));
            this.getHead().setTag('.');
            this.blocks.removeFirst();
            temp.addAll(this.moveForward(B).blocks);
            this.blocks = temp;
            return this;
        }
    }
    public Piece moveBackward(Board B) {
        if (!this.canMove(this.nextBackward(B), B)) {
            return this;
        }
        if (this.blocks.isEmpty()) {
            return this;
        } else {
            Deque<Block> temp = new ArrayDeque<>();
            this.nextBackward(B).setTag(this.tag);
            temp.addFirst(this.nextBackward(B));
            this.getTail().setTag('.');
            this.blocks.removeLast();
            for (Block blok : this.moveBackward(B).blocks) {
                temp.addFirst(blok);
            }
            this.blocks = temp;
            return this;
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
