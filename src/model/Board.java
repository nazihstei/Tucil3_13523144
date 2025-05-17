package model;

import java.util.ArrayList;
import java.util.HashMap;

public class Board {
    
    /* ATTRIBUTE */
    private int row;
    private int col;
    private HashMap<Character, Piece> pieces;
    private ArrayList<ArrayList<Block>> map;
    private ArrayList<Block> emptyBlocks;
    private Block goal;
    
    /* CONSTRUCTOR */
    public Board(ArrayList<String> text) {
        // set row and col
        this.row = text.size();
        this.col = text.stream().mapToInt(String::length).max().orElse(0);
        this.pieces = new HashMap<>();
        this.map = new ArrayList<>();
        this.emptyBlocks = new ArrayList<>();
        
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
                // add empties
                if (addedBlock.isEmpty()) {
                    this.emptyBlocks.add(addedBlock);
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
        this.emptyBlocks = new ArrayList<>();
        
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
                // add empties
                if (addedBlock.isEmpty()) {
                    this.emptyBlocks.add(addedBlock);
                }
            }
        }
    }
    
    /* BLOCK MANAGEMENT */
    public void addEmptyBlock(Block b) {
        if (!b.isEmpty()) return;
        this.emptyBlocks.add(b);
    }
    public void removeEmptyBlocks() {
        this.emptyBlocks.removeIf(b -> !b.isEmpty());
    }
    
    /* PRINT BOARD */
    public String toString() {
        String result = "";
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
}
