package model;

import java.util.ArrayList;
import java.util.HashMap;

import model.Piece.Direction;

public class Board {
    
    /* ATTRIBUTE */
    private int row;
    private int col;
    private HashMap<Character, Piece> pieces;
    private ArrayList<ArrayList<Block>> map;
    private Block goal;
    private char moveTag;
    private String moveDirection;

    /* GETTER */
    public Piece getPrimaryPiece() {
        return this.pieces.get('P');
    }
    public Block getGoal() {
        return this.goal;
    }
    public ArrayList<ArrayList<Block>> getcopyMap() {
        ArrayList<ArrayList<Block>> result = new ArrayList<>();
        for (ArrayList<Block> mapRow : this.map) {
            ArrayList<Block> newMapRow = new ArrayList<>();
            for (Block blok : mapRow) {
                newMapRow.add(new Block(blok));
            }
            result.add(newMapRow);
        }
        return result;
    }
    public ArrayList<ArrayList<Block>> getMap() {
        return this.map;
    }
    
    /* CONSTRUCTOR */
    public Board(ArrayList<String> text) {
        // set row and col
        this.row = text.size();
        this.col = text.stream().mapToInt(String::length).max().orElse(0);
        this.pieces = new HashMap<>();
        this.map = new ArrayList<>();
        this.moveTag = '\0';
        this.moveDirection = "";
        
        // set pieces and map
        for (int i=0; i<this.row; i++) {
            this.map.add(new ArrayList<>());

            for (int j=0; j<this.col; j++) {
                
                // check if K in right side
                if (text.get(i).length() <= j) {
                    this.map.get(i).add(j, new Block(i, j));
                    // regular map
                } else {
                    this.map.get(i).add(j, new Block(text.get(i).charAt(j), i, j));
                }
                
                // add pieces
                Block addedBlock = this.map.get(i).get(j);
                if (addedBlock.isPiece()) {
                    if (this.pieces.containsKey(addedBlock.getTag())) {
                        this.pieces.get(addedBlock.getTag()).addBlock(addedBlock);
                    } else {
                        this.pieces.put(addedBlock.getTag(), new Piece(addedBlock));
                    }
                }
                // add goal
                if (addedBlock.isExit()) {
                    this.goal = addedBlock;
                }
            }
        }
    }
    
    /* COPY CONSTRUCTOR */
    public Board(Board b) {
        this.row = b.row;
        this.col = b.col;
        this.pieces = new HashMap<>();
        this.map = new ArrayList<>();
        this.moveTag = b.moveTag;
        this.moveDirection = b.moveDirection;
        
        // set pieces and map
        for (int i=0; i<this.row; i++) {
            this.map.add(new ArrayList<>());
            
            for (int j=0; j<this.col; j++) {
                this.map.get(i).add(j, new Block(b.map.get(i).get(j)));
                
                // add pieces
                Block addedBlock = this.map.get(i).get(j);
                if (addedBlock.isPiece()) {
                    if (this.pieces.containsKey(addedBlock.getTag())) {
                        this.pieces.get(addedBlock.getTag()).addBlock(addedBlock);
                    } else {
                        this.pieces.put(addedBlock.getTag(), new Piece(addedBlock));
                    }
                }
                // add goal
                if (addedBlock.isExit()) {
                    this.goal = addedBlock;
                }
            }
        }
    }

    /* VALIDITY CHECK */
    public Boolean haveCoordinate(int row, int col) {
        return  (row >= 0 && col >= 0)
                && (row < this.row)
                && (col < this.col);
    }
    public Boolean isExitValid() {
        if (this.goal == null) {
            return false;
        }
        int r = this.goal.getRow();
        int c = this.goal.getCol();
        int R = this.row-1;
        int C = this.col-1;
        return  (r == 0 && this.map.get(r+1).get(c).isValid()) ||
                (r == R && this.map.get(r-1).get(c).isValid()) ||
                (c == 0 && this.map.get(r).get(c+1).isValid()) ||
                (c == C && this.map.get(r).get(c-1).isValid());
    }
    public Boolean isSolved() {
        if (!this.isExitValid()) return false;
        int goalRow = this.goal.getRow();
        int goalCol = this.goal.getCol();
        Block checkedBlock = null;
        Boolean isFit = false;
        if (goalRow==0) {
            checkedBlock = this.map.get(goalRow + 1).get(goalCol);
            isFit = this.getPrimaryPiece().getDirection().equals(Direction.VERTICAL);
        }
        if (goalCol==0) {
            checkedBlock = this.map.get(goalRow).get(goalCol + 1);
            isFit = this.getPrimaryPiece().getDirection().equals(Direction.HORIZONTAL);
        }
        if (goalRow==this.row-1) {
            checkedBlock = this.map.get(goalRow - 1).get(goalCol);
            isFit = this.getPrimaryPiece().getDirection().equals(Direction.VERTICAL);
        }
        if (goalCol==this.col-1) {
            checkedBlock = this.map.get(goalRow).get(goalCol - 1);
            isFit = this.getPrimaryPiece().getDirection().equals(Direction.HORIZONTAL);
        }
        return checkedBlock.isPrimary() && isFit;
    }

    /* PIECES MOVEMENT */
    public ArrayList<Board> movePiece(char tag) {
        ArrayList<Board> result = new ArrayList<>();
        Board moveUp = null;
        Board moveDown = null;
        Board moveLeft = null;
        Board moveRight = null;

        Block forward = this.pieces.get(tag).nextForward(this);
        Block backward = this.pieces.get(tag).nextBackward(this);
        
        // System.out.printf("%c forward : %b\n", tag, this.pieces.get(tag).canMove(forward, this));
        // System.out.printf("%c backward: %b\n", tag, this.pieces.get(tag).canMove(backward, this));
        // System.out.println(this.pieces.get(tag));

        if (this.pieces.get(tag).canMove(forward, this)) {
            // move up
            if (this.pieces.get(tag).getDirection().equals(Direction.VERTICAL) ||
            this.pieces.get(tag).getDirection().equals(Direction.BOTH)) {
                moveUp = new Board(this);
                // Block blockUp = moveUp.pieces.get(tag).nextForward(moveUp);
                // moveUp.pieces.get(tag).move(blockUp, moveUp);
                moveUp.pieces.get(tag).moveForward(moveUp);
                moveUp.moveTag = tag;
                moveUp.moveDirection = "UP";
            }
            // move left
            if (this.pieces.get(tag).getDirection().equals(Direction.HORIZONTAL) ||
            this.pieces.get(tag).getDirection().equals(Direction.BOTH)) {
                moveLeft = new Board(this);
                // Block blockLeft = moveLeft.pieces.get(tag).nextForward(moveLeft);
                // moveLeft.pieces.get(tag).move(blockLeft, moveLeft);
                moveLeft.pieces.get(tag).moveForward(moveLeft);
                moveLeft.moveTag = tag;
                moveLeft.moveDirection = "LEFT";
            }
        }
        if (this.pieces.get(tag).canMove(backward, this)) {
            // move down
            if (this.pieces.get(tag).getDirection().equals(Direction.VERTICAL) ||
            this.pieces.get(tag).getDirection().equals(Direction.BOTH)) {
                moveDown = new Board(this);
                // Block blockDown = moveDown.pieces.get(tag).nextBackward(moveDown);
                // moveDown.pieces.get(tag).move(blockDown, moveDown);
                moveDown.pieces.get(tag).moveBackward(moveDown);
                moveDown.moveTag = tag;
                moveDown.moveDirection = "DOWN";
            }
            // move right
            if (this.pieces.get(tag).getDirection().equals(Direction.HORIZONTAL) ||
            this.pieces.get(tag).getDirection().equals(Direction.BOTH)) {
                moveRight = new Board(this);
                // Block blockRight = moveRight.pieces.get(tag).nextBackward(moveRight);
                // moveRight.pieces.get(tag).move(blockRight, moveRight);
                moveRight.pieces.get(tag).moveBackward(moveRight);
                moveRight.moveTag = tag;
                moveRight.moveDirection = "RIGHT";
            }
        }

        // check before push
        if (moveUp != null) result.add(moveUp);
        if (moveDown != null) result.add(moveDown);
        if (moveLeft != null) result.add(moveLeft);
        if (moveRight != null) result.add(moveRight);
        // for (Board b : result) System.out.println(b);
        return result;
    }
    public ArrayList<Board> moveAllPieces() {
        ArrayList<Board> result = new ArrayList<>();
        for (Character key : this.pieces.keySet()) {
            result.addAll(this.movePiece(key));
        }
        return result;
    }
        
    /* PRINT BOARD */
    public String toString() {
        String result = String.format("[ %c ] %s\n", this.moveTag, this.moveDirection);
        for (ArrayList<Block> mapRow : this.map) {
            for (Block blok : mapRow) {
                result = result + blok.getTag() + " ";
            }
            if (result.length() > 1) result = result.substring(0, result.length()-1);
            result = result + "\n";
        }
        if (result.length() > 1) result = result.substring(0, result.length()-1);
        // String pieces = "";
        // for (Character k : this.pieces.keySet()) {
        //     pieces = pieces + this.pieces.get(k).toString() + "\n";
        // }
        // return pieces + result;
        return result;
    }

    /* ADDITIONAL */
    public ArrayList<Piece> getBlockingPieces() {
        ArrayList<Piece> result = new ArrayList<>();
        ArrayList<Block> line = new ArrayList<>();
        if (this.goal.getCol()==0 || this.goal.getCol()==this.col-1) {
            line = this.map.get(this.goal.getRow());
        }
        if (this.goal.getRow()==0 || this.goal.getRow()==this.row-1) {
            for (int i=0; i<this.row; i++) {
                line.add(this.map.get(i).get(this.goal.getCol()));
            }
        }
        if (line.getLast().isExit()) {
            line = new ArrayList<>(line.reversed());
        }
        for (Block blok : line) {
            if (!blok.isPiece()) continue;
            if (blok.isPrimary()) break;
            if (result.contains(this.pieces.get(blok.getTag()))) continue;
            result.add(this.pieces.get(blok.getTag()));
        }
        return result;
    }

    /* COMPARATOR */
    public static Boolean isSame(Board b1, Board b2) {
        if (b1 == null || b2 == null) {
            return false;
        }
        if (
            b1.row != b2.row || b1.col != b2.col || 
            b1.goal.getRow() != b2.goal.getRow() ||
            b1.goal.getCol() != b2.goal.getCol()) {
            return false;
        }
        for (int i=0; i<b1.row; i++) {
            for (int j=0; j<b2.row; j++) {
                if (b1.map.get(i).get(j).getTag() != b2.map.get(i).get(j).getTag()) {
                    return false;
                }
            }
        }
        return true;
    }
}