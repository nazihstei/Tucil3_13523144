package solver.algorithm;

import java.util.ArrayList;
import java.util.PriorityQueue;

import model.Board;
import model.BoardTree;
import solver.heuristic.Heuristic;

public class BNB extends Algorithm {
    
    /* ATTRIBUTE */
    private Double bestCost;
    private BNBcomparator comparator;

    /* CONSTRUCTOR */
    public BNB(Board b) {
        super(b);
        this.bestCost = null;
        this.comparator = new BNBcomparator(null);
        this.queue = new PriorityQueue<>(this.comparator);
    }
    public BNB(Board b, Heuristic h) {
        super(b);
        this.bestCost = null;
        this.comparator = new BNBcomparator(h);
        this.queue = new PriorityQueue<>(this.comparator);
    }

    /* SOLVER */
    @Override
    public void solver(BoardTree b) {
        // update best cost
        if (!b.isRoot() && this.bestCost == null) {
            this.bestCost = this.comparator.COST(b);
        }

        // process queue head
        b.generateBranches();
        ArrayList<BoardTree> branches = b.getBranches();
        branches.stream().filter(node -> this.comparator.COST(node) <= this.bestCost);
        this.queue.addAll(branches);

        // process next head
        if (!this.queue.isEmpty()) {
            solver(this.queue.poll());
        }
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
            h = this.h.calculate(b);
        }
        g = Algorithm.g(b);
        return g + h;
    }

    /* COMPARATOR ALGORITHM: f(n) = g(n) + h(n) */
    public int compare(BoardTree b1, BoardTree b2) {
        Double f1 = this.COST(b1);
        Double f2 = this.COST(b2);
        return (int) ((f1 - f2)/Math.abs(f1 - f2));
    }

}
