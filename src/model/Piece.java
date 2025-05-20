package model;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.PriorityQueue;


public class Piece {
    
    /* CONST ENUMERATE */
    public enum Direction {
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
    /* SORT PIECE BLOCKS */
    public void sort() {
        PriorityQueue<Block> temp = new PriorityQueue<>(this.blocks);
        this.blocks = new ArrayDeque<>(temp);
    }

    /* GETTER and SETTER */
    public char getTag() {
        return this.tag;
    }
    public void addBlock(Block b) {
        this.blocks.add(b);
        this.setDirection();
        this.sort();
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
    public ArrayList<Block> getBlocks() {
        ArrayList<Block> result = new ArrayList<>();
        Deque<Block> temp = new ArrayDeque<>();
        while (!this.blocks.isEmpty()) {
            Block b = this.blocks.poll();
            result.addLast(b);
            temp.addLast(b);
        }
        this.blocks = temp;
        return result;
    }

    /* BLOCK ACCESS */
        public Block nextForward(Board b) {
        try {
            if (this.direction.equals(Direction.VERTICAL) || this.direction.equals(Direction.BOTH)) {
                Block head = this.getHead();
                return b.getMap().get(head.getRow()-1).get(head.getCol());
            } else {
                Block head = this.getHead();
                return b.getMap().get(head.getRow()).get(head.getCol()-1);
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
                Block tail = this.getTail();
                return b.getMap().get(tail.getRow()).get(tail.getCol()+1);
            }
        } catch (Exception e) {
            return null;
        }    
    }

    /* MOVEMENT */
    public Boolean canMove(Block b, Board B) {
        // validity check
        if (b==null || !b.isValid() || this.blocks.isEmpty()) return false;
       
        Boolean forward     = (b == this.nextForward(B) && b.isEmpty());
        Boolean backward    = (b == this.nextBackward(B) && b.isEmpty());
        return (forward || backward);
    }
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
            this.sort();
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
            this.sort();
            return this;
        }
        
    }

    /* PRINT PIECE */
    @Override
    public String toString() {
        String result = String.format("[%s] %c : ", this.direction, this.tag);
        for (Block blok : this.blocks) {
            result = result + " " + blok.toString();
        }
        return result;
    }
    
}
