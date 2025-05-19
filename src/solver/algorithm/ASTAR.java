package solver.algorithm;

import model.Board;
import model.BoardTree;
import solver.heuristic.Heuristic;

public class ASTAR extends Algorithm {

    /* CONSTRUCTOR */
    public ASTAR(Board b, Heuristic h) {
        super(b, new ASTARcomparator(h));
        this.setComparator = new ASTARSetComparator(h);
    }
    
}

class ASTARcomparator implements IRoutePlanning {
    
    /* ATTRIBUTE */
    private Heuristic h;
    
    /* CONSCTRUCTOR */
    public ASTARcomparator(Heuristic h) {
        this.h = h;
    }

    /* f(n) */
    public Double f(BoardTree b) {
        return Algorithm.g(b) + this.h.calculate(b.getNode());
    }

    /* COMPARATOR ALGORITHM: f(n) = g(n) + h(n) */
    public int compare(BoardTree b1, BoardTree b2) {
        Double f1 = this.f(b1);
        Double f2 = this.f(b2);
        if (f1 - f2 == 0) return 0;
        return (int) ((f1 - f2)/Math.abs(f1 - f2));
    }

}

class ASTARSetComparator extends ASTARcomparator {

    /* CONSTRUCTOR */
    public ASTARSetComparator(Heuristic h) {
        super(h);
    }

    /* ADDITIONAL COMPARATOR */
    @Override
    public int compare(BoardTree b1, BoardTree b2) {
        if (this.f(b1) < this.f(b2)) {
            return -1;
        }
        if (Board.isSame(b1.getNode(), b2.getNode())) {
            return 0;
        }
        return 1;
    }
}
