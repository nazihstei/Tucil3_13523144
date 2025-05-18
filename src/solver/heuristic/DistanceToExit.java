package solver.heuristic;

import model.Block;
import model.Board;
import model.Piece;

public class DistanceToExit extends Heuristic {

    /* Menghitung jarak dari Primary ke Exit */
    
    /* METHOD */
    public Double calculate(Board b) {
        // find data
        Piece p = b.getPrimaryPiece();
        Block goal = b.getGoal();
    
        // calculate manhattan distance
        int distance = goal.ManhattanDistance(p.getHead());
        return Double.valueOf(distance);
    }

}
