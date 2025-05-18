package model;

import java.util.ArrayList;
import java.util.Collection;

public class BoardTree {
    
    /* ATTRIBUTE */
    private BoardTree root;
    private Board node;
    private ArrayList<BoardTree> branches;
    private long depth;
    private static ArrayList<BoardTree> leaves;

    /* CONSTRUCTOR */
    public BoardTree(Board node) {
        this.root = null;
        this.node = node;
        this.depth = 0;
        this.branches = new ArrayList<>();
        BoardTree.leaves = new ArrayList<>();
    }
    public BoardTree(Board node, BoardTree root) {
        this.root = root;
        this.node = node;
        this.depth = root.getDepth() + 1;
        this.branches = new ArrayList<>();
        BoardTree.leaves.add(this);
    }

    /* GETTER and SETTER */
    public BoardTree getRoot() {
        return this.root;
    }
    public long getDepth() {
        return this.depth;
    }
    public Board getNode() {
        return this.node;
    }
    public ArrayList<BoardTree> getBranches() {
        return this.branches;
    }
    public static ArrayList<BoardTree> getLeaves() {
        return BoardTree.leaves;
    }
    public void setNode(Board b) {
        this.node = b;
    }

    /* NODE COUNT */
    public long nodeCount() {
        long temp = 0;
        for (BoardTree branch : this.branches) {
            temp += branch.nodeCount();
        }
        if (this.isRoot()) {   
            return 1 + this.branches.size() + temp;
        } else {
            return this.branches.size() + temp;
        }
    }

    /* BRANCHES */
    public void addBranch(Board b) {
        if (b == null) return;
        this.branches.add(new BoardTree(b, this));
        BoardTree.leaves.remove(this);
    }
    public void addBranches(Collection<Board> data) {
        if (data == null) return;
        for (Board b : data) {
            this.addBranch(b);
        }
    }

    /* CONDITIONAL CHECK */
    public Boolean isRoot() {
        return this.root == null;
    }
    public Boolean isLeaf() {
        return this.branches.size() == 0;
    }

    /* GENERATE BRANCHES */
    public void generateBranches() {
        ArrayList<Board> nextMovement = this.node.moveAllPieces();
        this.addBranches(nextMovement);
    }
}
