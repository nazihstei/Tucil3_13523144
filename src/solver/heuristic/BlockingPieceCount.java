package solver.heuristic;

import java.util.ArrayList;
import java.util.HashMap;

import model.Block;
import model.Board;
import model.Piece;

public class BlockingPieceCount extends Heuristic {
    
    /* METHOD */
    public Double calculate(Board b) {
        // find data
        Piece p = b.getPrimaryPiece();
        Block goal = b.getGoal();
        ArrayList<ArrayList<Block>> map = b.getcopyMap();
        
        // find row and col distance
        int rowD = goal.rowDistanceTo(p.getHead());
        int colD = goal.colDistanceTo(p.getHead());

        // iterate to check tag
        HashMap<Character, Integer> pieceCount = new HashMap<>(); 
        
        // exit on left
        if (rowD == 0 && colD < 0) {
            int i = goal.getRow();
            for (int j = goal.getCol(); j < p.getHead().getCol(); j++) {
                Block blok = map.get(i).get(j);
                if (blok.isPiece() && !blok.isPrimary()) {
                    pieceCount.forEach((k, v) -> {
                        if (k == blok.getTag()) v++;
                    });
                    pieceCount.putIfAbsent(blok.getTag(), 1);
                }
            }

        // exit on right
        } else if (rowD == 0 && colD > 0) {
            int i = goal.getRow();
            for (int j = p.getTail().getCol(); j < goal.getCol(); j++) {
                Block blok = map.get(i).get(j);
                if (blok.isPiece() && !blok.isPrimary()) {
                    pieceCount.forEach((k, v) -> {
                        if (k == blok.getTag()) v++;
                    });
                    pieceCount.putIfAbsent(blok.getTag(), 1);
                }
            }
            
        // exit on top
        } else if (rowD < 0 && colD == 0) {
            int j = goal.getCol();
            for (int i = goal.getRow(); i < p.getHead().getRow(); i++) {
                Block blok = map.get(i).get(j);
                if (blok.isPiece() && !blok.isPrimary()) {
                    pieceCount.forEach((k, v) -> {
                        if (k == blok.getTag()) v++;
                    });
                    pieceCount.putIfAbsent(blok.getTag(), 1);
                }    
            }
            
        // exit on below
        } else if (rowD > 0 && colD == 0) {
            int j = goal.getCol();
            for (int i = p.getTail().getRow(); i < goal.getRow(); i++) {
                Block blok = map.get(i).get(j);
                if (blok.isPiece() && !blok.isPrimary()) {
                    pieceCount.forEach((k, v) -> {
                        if (k == blok.getTag()) v++;
                    });
                    pieceCount.putIfAbsent(blok.getTag(), 1);
                }    
            }
            
        }

        // calculate total piece
        Integer result = pieceCount.values().stream().reduce(0, Integer::sum);
        return Double.valueOf(result);
    }

}
