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
    public Boolean solver() {
        Boolean solved = false;
        if (this.tree == null) {
            return false;
        }
        if (this.tree.getNode().isSolved()) {
            return true;
        }
        BoardTree b = this.tree;
        this.queue.offer(b);
        // process
        while (!this.queue.isEmpty()) {
            b = this.queue.poll();
            b.generateBranches();
            ArrayList<BoardTree> branches = b.getBranches();
            
            System.out.println("depth: " + b.getDepth());
            System.out.println(b.getNode());
            System.out.println();

            if (b.getNode().isSolved()) {
                solved = true;
                break;
            }

            if (!b.isRoot()) {
                
                // filter
                for (int i = 0; i<branches.size(); i++) {
                    BoardTree node = branches.get(i);
                    Boolean found = false;
                    for (BoardTree checkNode : this.trash) {
                        if (Board.isSame(node.getNode(), checkNode.getNode())) {
                            this.trash.add(node);
                            found = true;
                            break;
                        }
                    }
                    if (found) branches.remove(node);
                }
                
                branches.stream().filter(node -> this.comparator.COST(node) <= this.bestCost);
            }
            // update best cost
            this.trash.add(b);
            this.queue.addAll(branches);
            if (!this.queue.isEmpty()) {
                this.bestCost = this.comparator.COST(this.queue.peek());
            }
        }
        return solved;
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
