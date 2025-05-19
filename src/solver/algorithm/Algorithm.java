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
    protected ArrayList<BoardTree> trash;

    /* CONSTRUCTOR */
    public Algorithm(Board b) {
        this.tree = new BoardTree(b);
        this.queue = new PriorityQueue<>();
        this.solution = null;
        this.trash = new ArrayList<>();
    }

    /* GETTER */
    public BoardTree getTree() {
        return this.tree;
    }

    /* SOLVER */
    public ArrayList<Board> solve() {
        Boolean hasSolution = this.solver();
        if (hasSolution == false) {
            System.err.println("DON'T HAVE SOLUTION");
            return null;
        }

        // find goal in leaf
        BoardTree goal = null;
        for (BoardTree leaf : BoardTree.getLeaves(this.tree)) {
            if (leaf.getNode().isSolved()) {
                if (goal == null) {
                    goal = leaf;
                } else if (leaf.getDepth() < goal.getDepth()) {
                    goal = leaf;
                }
            }
        }

        if (goal == null) {
            System.err.println("GOAL RESULT IS NULL");
            try {
               Thread.sleep(10000);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
        }

        // tracing to root
        ArrayList<Board> result = new ArrayList<>();
        BoardTree node = goal;
        while (!node.isRoot()) {
            result.addFirst(node.getNode());
            node = node.getRoot();
        }
        result.addFirst(this.tree.getNode());
        this.solution = result;
        return result;
    }
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

            System.out.println("depth: " + b.getDepth());
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
                        this.trash.add(node);
                        found = true;
                        break;
                    }
                }
                if (found) branches.remove(node);
            }
            this.trash.add(b);
            this.queue.addAll(branches);
        }
        return b.getNode().isSolved();
    }

    /* GET: g(n) */
    public static Long g(BoardTree b) {
        return b.getDepth();
    }

    /* PRINT RESULT */
    public String toString() {
        if (this.solution == null) {
            System.err.println("ERROR: SOLUTION IS NULL");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (this.solution.size() == 0) {
            System.err.println("ERROR: SOLUTION IS EMPTY");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (this.tree == null) {
            System.err.println("ERROR: TREE IS NULL");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        String result = "";
        
        for (Board step : this.solution) {
            result = result + String.format("\n\nGerakan %d :\n%s", this.solution.indexOf(step), step.toString());
        }
        
        return result;
    }
}
