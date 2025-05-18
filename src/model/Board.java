package model;

import java.util.ArrayList;
import java.util.HashMap;

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
                && (this.row < row)
                && (this.col < col);
    }
    public Boolean isExitValid() {
        return  (this.goal.getRow()<=0 && this.goal.getCol()<=0) ||
                (this.goal.getRow()<=0 && this.goal.getCol()>=this.col-1) ||
                (this.goal.getRow()>=this.row-1 && this.goal.getCol()<=0) ||
                (this.goal.getRow()>=this.row-1 && this.goal.getCol()>=this.col-1);
    }
    public Boolean isSolved() {
        if (!this.isExitValid()) return false;
        int goalRow = this.goal.getRow();
        int goalCol = this.goal.getCol();
        Block checkedBlock = null;
        Boolean isFit = false;
        if (goalRow==0) {
            checkedBlock = this.map.get(goalRow + 1).get(goalCol);
            isFit = this.pieces.get('P').getDirection() == "VERTICAL";
        }
        if (goalCol==0) {
            checkedBlock = this.map.get(goalRow).get(goalCol + 1);
            isFit = this.pieces.get('P').getDirection() == "HORIZONTAL";
        }
        if (goalRow==this.row-1) {
            checkedBlock = this.map.get(goalRow - 1).get(goalCol);
            isFit = this.pieces.get('P').getDirection() == "VERTICAL";
        }
        if (goalCol==this.col-1) {
            checkedBlock = this.map.get(goalRow).get(goalCol - 1);
            isFit = this.pieces.get('P').getDirection() == "HORIZONTAL";
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
        
        if (this.pieces.get(tag).getDirection() == "VERTICAL" || this.pieces.get(tag).getDirection() == "BOTH") {
            int colVertical = this.pieces.get(tag).getHead().getCol();
            int rowUp = this.pieces.get(tag).getHead().getRow() - 1;
            int rowDown = this.pieces.get(tag).getTail().getRow() + 1;

            // move up
            if (this.haveCoordinate(rowUp, colVertical)) {
                moveUp = new Board(this);
                Block blockUp = moveUp.map.get(rowUp).get(colVertical);
                moveUp.pieces.get(tag).move(blockUp, moveUp);
                moveUp.moveTag = tag;
                moveUp.moveDirection = "UP";
            }
            // move down
            if (this.haveCoordinate(rowDown, colVertical)) {
                moveDown = new Board(this);
                Block blockDown = moveDown.map.get(rowDown).get(colVertical);
                moveDown.pieces.get(tag).move(blockDown, moveUp);
                moveDown.moveTag = tag;
                moveDown.moveDirection = "DOWN";
            }
            
        } else if (this.pieces.get(tag).getDirection() == "HORIZONTAL" || this.pieces.get(tag).getDirection() == "BOTH") {
            int rowHorizontal = this.pieces.get(tag).getHead().getRow();
            int colLeft = this.pieces.get(tag).getHead().getCol() - 1;
            int colRight = this.pieces.get(tag).getTail().getCol() + 1;
            
            // move left
            if (this.haveCoordinate(rowHorizontal, colLeft)) {
                moveLeft = new Board(this);
                Block blockLeft = moveLeft.map.get(rowHorizontal).get(colLeft);
                moveLeft.pieces.get(tag).move(blockLeft, moveLeft);
                moveLeft.moveTag = tag;
                moveLeft.moveDirection = "LEFT";
            }
            // move right
            if (this.haveCoordinate(rowHorizontal, colRight)) {
                moveRight = new Board(this);
                Block blockRight = moveRight.map.get(rowHorizontal).get(colRight);
                moveRight.pieces.get(tag).move(blockRight, moveRight);
                moveRight.moveTag = tag;
                moveRight.moveDirection = "RIGHT";
            }
        }

        // check before push
        if (moveUp != null) result.add(moveUp);
        if (moveDown != null) result.add(moveDown);
        if (moveLeft != null) result.add(moveLeft);
        if (moveRight != null) result.add(moveRight);
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
        String result = String.format("[ %c ] %s", this.moveTag, this.moveDirection);
        for (ArrayList<Block> mapRow : this.map) {
            for (Block blok : mapRow) {
                result = result + blok.getTag() + " ";
            }
            if (result.length() > 1) result = result.substring(0, result.length()-1);
            result = result + "\n";
        }
        if (result.length() > 1) result = result.substring(0, result.length()-1);
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
}