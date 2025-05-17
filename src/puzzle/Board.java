package puzzle;

import java.util.ArrayList;
import java.util.HashMap;

public class Board {
    private int row;
    private int col;
    private HashMap<Character, Piece> pieces;
    private ArrayList<ArrayList<Block>> map;
    private Block goal;

    public int getRow() {
        return this.row;
    }
    public int getCol() {
        return this.col;
    }

    public Board(ArrayList<String> text) {
        this.row = text.size();
        this.col = text.stream().mapToInt(String::length).max().orElse(0);
        
        this.map = new ArrayList<>(row);
        HashMap<Character, ArrayList<Block>> pieceMap = new HashMap<>();
        for (int i=0; i<row; i++) {
            ArrayList<Block> rowInput = new ArrayList<>(col);
            for (int j=0; j<col; j++) {
                Block blok;
                if (text.get(i).length() <= j) {
                    blok = new Block(i, j);
                } else {
                    blok = new Block(i, j, text.get(i).charAt(j));
                }
                // System.out.println("blok: " + blok.tag + ", " + blok.row + ", " + blok.col);
                
                rowInput.add(blok);
                if (blok.tag != ' ' && blok.tag != '.' && blok.tag != 'K') {
                    if (! pieceMap.containsKey(blok.tag)) {
                        pieceMap.put(blok.tag, new ArrayList<>());
                    }
                    pieceMap.get(blok.tag).add(blok);
                }
                if (blok.tag == 'K') {
                    this.goal = blok;
                }
            }
            this.map.add(rowInput);
        }

        this.pieces = new HashMap<>();
        for (Character key : pieceMap.keySet()) {
            ArrayList<Block> arrayBlock = pieceMap.get(key);
            Piece newPiece = new Piece(
                key, 
                arrayBlock.get(0).row, 
                arrayBlock.get(0).col, 
                arrayBlock.size(), 
                Block.direction(arrayBlock.get(0), arrayBlock.get(1))
            );
            this.pieces.put(key, newPiece);
        }
    }

    /* Print Board */
    public String toString() {
        String result = "";
        for (ArrayList<Block> mapRow : this.map) {
            for (Block blok : mapRow) {
                result = result + blok.tag + " ";
            }
            result = result + "\n";
        }
        return result;
    }
}