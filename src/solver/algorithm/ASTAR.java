package solver.algorithm;

import java.util.ArrayList;
import java.util.PriorityQueue;

import model.Board;
import model.BoardTree;
import solver.heuristic.Heuristic;

public class ASTAR extends Algorithm {

    /* ATTRIBUTE */
    protected ASTARcomparator comparator;
    
    /* CONSTRUCTOR */
    public ASTAR(Board b, Heuristic h) {
        super(b);
        this.queue = new PriorityQueue<>(new ASTARcomparator(h));
        this.comparator = new ASTARcomparator(h);
    }

    /* METHOD */
    @Override
    public Boolean solver() {

        if (this.tree == null) {
            return false;
        }
        if (this.tree.getNode().isSolved()) {
            return true;
        }
        BoardTree b = this.tree;
        this.queue.offer(b);
        while (!this.queue.isEmpty()) {
            b = this.queue.poll();

            System.out.println(b.getNode());
            System.out.println();

            if (b.getNode().isSolved()) {
                break;
            }
            b.generateBranches();
            ArrayList<BoardTree> branches = b.getBranches();

                // filter
                for (int i = 0; i<branches.size(); i++) {
                    BoardTree node = branches.get(i);
                    Boolean found = false;
                    for (BoardTree checkNode : this.trash) {
                        if (Board.isSame(node.getNode(), checkNode.getNode())) {
                            if (this.comparator.f(node) >= this.comparator.f(checkNode)) {
                                this.trash.add(node);
                                found = true;
                                break;
                            }
                        }
                    }
                    if (found) branches.remove(node);
                }

            this.queue.addAll(branches);
        }
        return b.getNode().isSolved();
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
