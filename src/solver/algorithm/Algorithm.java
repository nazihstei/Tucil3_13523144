package solver.algorithm;

import java.util.ArrayList;
import java.util.PriorityQueue;

import model.Board;
import model.BoardTree;

public class Algorithm {
    
    /* ATTRIBUTE */
    protected BoardTree tree;
    protected PriorityQueue<BoardTree> queue;
    protected ArrayList<Board> solution;

    /* CONSTRUCTOR */
    public Algorithm(Board b) {
        this.tree = new BoardTree(b);
        this.queue = new PriorityQueue<>();
        this.solution = null;
    }

    /* GETTER */
    public BoardTree getTree() {
        return this.tree;
    }

    /* SOLVER */
    public ArrayList<Board> solve() {
        this.solver(this.tree);
        // find goal in leaf
        BoardTree goal = null;
        for (BoardTree leaf : BoardTree.getLeaves()) {
            if (leaf.getNode().isSolved()) {
                if (goal == null) {
                    goal = leaf;
                } else if (leaf.getDepth() < goal.getDepth()) {
                    goal = leaf;
                }
            }
        }
        // tracing to root
        ArrayList<Board> result = new ArrayList<>();
        BoardTree node = goal;
        while (!node.isRoot()) {
            result.addFirst(node.getNode());
            node = node.getRoot();
        }
        this.solution = result;
        return result;
    }
    public void solver(BoardTree b) {
        // process queue head
        b.generateBranches();
        this.queue.addAll(b.getBranches());

        // process next head
        if (!b.getNode().isSolved()) {
            solver(this.queue.poll());
        }
    }

    /* GET: g(n) */
    public static Long g(BoardTree b) {
        return b.getDepth();
    }

    /* PRINT RESULT */
    public String toString() {
        if (this.solution == null) return "";
        String result = "";
        
        for (Board step : this.solution) {
            result = result + String.format("Gerakan %d : %s", this.solution.indexOf(step), step.toString());
        }
        
        return result;
    }
}
