package solver.algorithm;

import java.util.PriorityQueue;

import model.Board;
import model.BoardTree;
import solver.heuristic.Heuristic;

public class ASTAR extends Algorithm {
    
    /* CONSTRUCTOR */
    public ASTAR(Board b, Heuristic h) {
        super(b);
        this.queue = new PriorityQueue<>(new ASTARcomparator(h));
    }
    
}

class ASTARcomparator implements IRoutePlanning {
    
    /* ATTRIBUTE */
    private Heuristic h;
    
    /* CONSCTRUCTOR */
    public ASTARcomparator(Heuristic h) {
        this.h = h;
    }

    /* COMPARATOR ALGORITHM: f(n) = g(n) + h(n) */
    public int compare(BoardTree b1, BoardTree b2) {
        Double f1 = Algorithm.g(b1) + this.h.calculate(b1);
        Double f2 = Algorithm.g(b2) + this.h.calculate(b2);
        return (int) ((f1 - f2)/Math.abs(f1 - f2));
    }

}
