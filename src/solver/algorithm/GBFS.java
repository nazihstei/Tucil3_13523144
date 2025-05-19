package solver.algorithm;

import model.Board;
import model.BoardTree;
import solver.heuristic.Heuristic;

public class GBFS extends Algorithm {
    
    /* CONSTRUCTOR */
    public GBFS(Board b, Heuristic h) {
        super(b, new GBFScomparator(h));
    }

}

class GBFScomparator implements IRoutePlanning {

    /* ATTRIBUTE */
    private Heuristic h;

    /* CONSTRUCTOR */
    public GBFScomparator(Heuristic h) {
        this.h = h;
    }

    /* COMAPATOR ALGORITHM: f(n) = h(n) */
    public int compare(BoardTree b1, BoardTree b2) {
        Double f1 = this.h.calculate(b1.getNode());
        Double f2 = this.h.calculate(b2.getNode());
        if (f1 - f2 == 0) return 0;
        return (int) ((f1 - f2)/Math.abs(f1 - f2));
    }

}
