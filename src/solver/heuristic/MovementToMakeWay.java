package solver.heuristic;

import java.util.ArrayList;

import model.Block;
import model.Board;
import model.Piece;
import model.Piece.Direction;

public class MovementToMakeWay extends Heuristic {
    
    /* METHOD */
    public Double calculate(Board b) {
        // find data
        ArrayList<Piece> blockingPieces = b.getBlockingPieces();
        int count = 0;
        for (Piece p : blockingPieces) {
            Block intercept = intercept(b.getPrimaryPiece(), p);
            // if (p.canMove(p.nextForward(b), b) && p.canMove(p.nextBackward(b), b)) {
            //     count += Math.min(intercept.ManhattanDistance(p.getHead()), intercept.ManhattanDistance(p.getTail()));
            // } else if (p.canMove(p.nextForward(b), b)) {
            //     count += intercept.ManhattanDistance(p.getTail());
            // } else if (p.canMove(p.nextBackward(b), b)) {
            //     count += intercept.ManhattanDistance(p.getHead());
            // }
            if (
                p.nextForward(b) != null && p.nextForward(b).isValid() && 
                p.nextBackward(b) != null && p.nextBackward(b).isValid()
            ) {
                count += Math.min(intercept.ManhattanDistance(p.getHead()), intercept.ManhattanDistance(p.getTail()));
            } else if (p.nextForward(b) != null && p.nextForward(b).isValid()) {
                count += intercept.ManhattanDistance(p.getTail());
            } else if (p.nextBackward(b) != null && p.nextBackward(b).isValid()) {
                count += intercept.ManhattanDistance(p.getHead());
            } else {
                count += 100000;
            }
        }

        return Double.valueOf(count);
    }

    /* HELPER */
    private static Block intercept(Piece primary, Piece other) {
        if (primary.getDirection().equals(other.getDirection())) {
            return null;
        }
        if (primary.getDirection().equals(Direction.HORIZONTAL)) {
            return other.getBlocks().get(primary.getHead().getRow() - other.getHead().getRow());
        } else { // VERTICAL
            return other.getBlocks().get(primary.getHead().getCol() - other.getHead().getCol());
        }
    }

}
