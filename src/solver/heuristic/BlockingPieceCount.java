package solver.heuristic;

import java.util.ArrayList;

import model.Board;
import model.Piece;

public class BlockingPieceCount extends Heuristic {
    
    /* Menghitung jumlah piece yang menghalangi jalan ke Exit */

    /* METHOD */
    public Double calculate(Board b) {
        // find data
        ArrayList<Piece> blockingPiece = b.getBlockingPieces();
        return Double.valueOf(blockingPiece.size());
    }

}
