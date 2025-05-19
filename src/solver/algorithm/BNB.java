package solver.algorithm;

import model.Board;
import model.BoardTree;
import solver.heuristic.Heuristic;

public class BNB extends Algorithm {
    
    /* ATTRIBUTE */
    private Double bestCost;

    /* CONSTRUCTOR */
    public BNB(Board b) {
        super(b, new BNBcomparator(null));
        this.bestCost = null;
        this.setComparator = new BNBSetComparator(null, this);
    }
    public BNB(Board b, Heuristic h) {
        super(b, new BNBcomparator(h));
        this.bestCost = null;
        this.setComparator = new BNBSetComparator(h, this);
    }

    /* GETTER */
    public Double getBestCost() {
        return this.bestCost;
    }
    public void setBestCost(Double val) {
        this.bestCost = val;
    }

}

class BNBcomparator implements IRoutePlanning {

    /* ATTRIBUTE */
    private Heuristic h;

    /* CONSTRUCTOR */
    public BNBcomparator(Heuristic h) {
        this.h = h;
    }

    /* COST ALGORITHM: f(n) = g(n) + h(n) */
    public Double COST(BoardTree b) {
        Long g;
        Double h;
        if (this.h == null) {
            h = 0.0;
        } else {
            h = this.h.calculate(b.getNode());
        }
        g = Algorithm.g(b);
        return g + h;
    }

    /* COMPARATOR ALGORITHM: f(n) = g(n) + h(n) */
    public int compare(BoardTree b1, BoardTree b2) {
        Double f1 = this.COST(b1);
        Double f2 = this.COST(b2);
        if (f1 - f2 == 0) return 0;
        return (int) ((f1 - f2)/Math.abs(f1 - f2));
    }

}

class BNBSetComparator extends BNBcomparator {

    /* ATTRIBUTE */
    private BNB ref;

    /* CONSTRUCTOR */
    public BNBSetComparator(Heuristic h, BNB ref) {
        super(h);
        this.ref = ref;
    }

    /* ADDITIONAL COMPARATOR */
    @Override
    public int compare(BoardTree b1, BoardTree b2) {
        if (Board.isSame(b1.getNode(), b2.getNode())) {
            return 0;
        }
        if (this.ref.getBestCost() == null) {
            return -1;
        }
        if (this.COST(b1) <= this.ref.getBestCost()) {
            this.ref.setBestCost(this.COST(b1));
            return -1;
        }
        if (this.COST(b1) < this.COST(b2)) {
            return -1;
        }
        return 0;
    }
}
