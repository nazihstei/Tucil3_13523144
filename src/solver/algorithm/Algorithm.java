package solver.algorithm;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeSet;

import model.Board;
import model.BoardTree;
import utils.Home;

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
    public ArrayList<Board> getSolution() {
        return this.solution;
    }

    /* SOLVER */
    public ArrayList<Board> solve(Boolean debug) {
        this.solver(System.currentTimeMillis(), debug);

        // find goal
        BoardTree goal = this.tree.getGoal();

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
    public Boolean solver(long startDuration, Boolean debug) {

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

            // DEBUG
            if (debug) {
                printSolverState(b, this, startDuration);
            }

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
    @Override
    public String toString() {
        return this.toString(4, true);
    }
    public String toString(int indent, Boolean color) {
        
        StringBuilder result = new StringBuilder();
        String indentString = "";
        for (int i = 0; i < indent; i++) {
            indentString = indentString + " ";
        }
        
        if (this.solution == null) {
            result.append(indentString).append("DON'T HAVE SOLUTION");
        } else if (this.solution.size() == 0) {
            result.append(indentString).append("DON'T HAVE SOLUTION");
        } else {
            for (Board step : this.solution) {
                result.append(String.format("\n[*] Gerakan %d\n", this.solution.indexOf(step)));
                if (color) {
                    result.append(step);
                } else {
                    result.append(step.toString(indent));
                }
                result.append("\n");
            }
            if (result.length() > 0) {
                result.deleteCharAt(result.length() - 1); // Hapus newline terakhir
            }
        }
        return result.toString();
    }

    /* STATIC METHOD */
    public void printSolverState(BoardTree checkedNode, Algorithm solver, long startDuration) {
        try {
            Home.Loading(checkedNode, solver, startDuration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
