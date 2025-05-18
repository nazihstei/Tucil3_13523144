package solver.heuristic;

import java.util.ArrayList;

import model.Board;
import model.Piece;

public class SolidnessBlockingPiece extends Heuristic {
    
    /* Menghutung jumlah piece yang menghalangi jalan yang tidak dapat digerakkan */

    /* METHOD */
    public Double calculate(Board b) {
        // find data
        ArrayList<Piece> blockingPieces = b.getBlockingPieces();
        int count = 0;
        for (Piece p : blockingPieces) {
            if (p.canMove(p.nextForward(b))) continue;
            if (p.canMove(p.nextBackward(b))) continue;
            count++;
        }

        return Double.valueOf(count);
    }

}
