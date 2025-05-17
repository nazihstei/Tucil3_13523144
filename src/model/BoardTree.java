package model;

import java.util.ArrayList;
import java.util.Collection;

public class BoardTree {
    
    /* ATTRIBUTE */
    private BoardTree root;
    private Board node;
    private ArrayList<BoardTree> branches;
    private long depth;

    /* CONSTRUCTOR */
    public BoardTree(Board node) {
        this.root = null;
        this.node = node;
        this.depth = 0;
        this.branches = new ArrayList<>();
    }
    public BoardTree(Board node, BoardTree root) {
        this.root = root;
        this.node = node;
        this.depth = root.getDepth() + 1;
        this.branches = new ArrayList<>();
    }

    /* GETTER and SETTER */
    public long getDepth() {
        return this.depth;
    }
    public Board getNode() {
        return this.node;
    }
    public void setNode(Board b) {
        this.node = b;
    }

    /* BRANCHES */
    public void addBranch(Board b) {
        if (b == null) return;
        this.branches.add(new BoardTree(b, this));
    }
    public void addBranch(Collection<Board> data) {
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
}
