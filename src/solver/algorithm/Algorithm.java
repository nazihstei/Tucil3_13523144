package solver.algorithm;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeSet;

import model.Board;
import model.BoardTree;

public class Algorithm {
    
    /* ATTRIBUTE */
    protected BoardTree tree;
    protected PriorityQueue<BoardTree> queue;
    protected ArrayList<Board> solution;
    protected ArrayList<BoardTree> trash;
    protected Comparator<BoardTree> comparator;
    protected Comparator<BoardTree> setComparator;

    /* CONSTRUCTOR */
    public Algorithm(Board b) {
        this.tree = new BoardTree(b);
        this.queue = new PriorityQueue<>();
        this.solution = null;
        this.trash = new ArrayList<>();
    }
    public Algorithm(Board b, Comparator<BoardTree> comparator) {
        this.tree = new BoardTree(b);
        this.queue = new PriorityQueue<>(comparator);
        this.solution = null;
        this.trash = new ArrayList<>();
        this.comparator = comparator;
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

            // System.out.println("depth: " + b.getDepth());
            // System.out.println(b.getNode());
            // System.out.println();

            if (b.getNode().isSolved()) {
                break;
            }
            b.generateBranches();
            
            // filter
            Set<BoardTree> temp1;
            if (this.setComparator == null) {
                temp1 = new TreeSet<>(this.queue);
            } else {
                temp1 = new TreeSet<>(this.setComparator);
                temp1.addAll(this.queue);
            }
            Set<BoardTree> temp2 = new TreeSet<>(this.trash);
            Set<BoardTree> temp3 = new TreeSet<>(b.getBranches());
            
            if (this.comparator == null) {
                this.queue = new PriorityQueue<>(temp1);
            } else {
                this.queue = new PriorityQueue<>(this.comparator); this.queue.addAll(temp1);
            }
            this.trash = new ArrayList<>(temp2);
            List<BoardTree> branches = new ArrayList<>(temp3);
            
            // System.out.println("SEBELUM DIHAPUS: " + branches.size());
            BoardTree[] nodeRemove = new BoardTree[branches.size()]; int j = 0;
            for (int i = 0; i<branches.size(); i++) {
                BoardTree node = branches.get(i);
                for (BoardTree checkNode : this.trash) {
                    if (Board.isSame(node.getNode(), checkNode.getNode())) {
                        // System.out.println("IS SAME DIPANGGIL DI SISNI  : " + node);
                        nodeRemove[j] = node;
                        j++;
                        break;
                    }
                }
            }
            for (int i = 0; i < j; i++) {
                BoardTree node = nodeRemove[i];
                this.trash.add(node);
                branches.remove(node);
                // System.out.println("REMOVE SUCCESS              : " + node);
            }
            // System.out.println("SETELAH DIHAPUS: " + branches.size());
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
