package solver.algorithm;

import java.util.PriorityQueue;

import model.Board;
import model.BoardTree;

public class UCS extends Algorithm {
    
    /* CONSTRUCTOR */
    public UCS(Board b) {
        super(b);
        this.queue = new PriorityQueue<>(new UCScomparator());
    }

}

class UCScomparator implements IRoutePlanning {

    /* COMPARATOR ALGORITHM: f(n) = g(n) */
    public int compare(BoardTree b1, BoardTree b2) {
        Long f1 = Algorithm.g(b1);
        Long f2 = Algorithm.g(b2);
        return (int) ((f1 - f2)/Math.abs(f1 - f2));
    }
    
}
