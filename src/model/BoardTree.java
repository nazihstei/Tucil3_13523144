package model;

import java.util.ArrayList;

public class BoardTree {
    
    /* ATTRIBUTE */
    private Board node;
    private ArrayList<Board> branches;

    /* CONSTRUCTOR */
    public BoardTree(Board node) {
        this.node = node;
        this.branches = new ArrayList<>();
    }

    /* GETTER and SETTER */
    public Board getNode() {
        return this.node;
    }
    public void setNode(Board b) {
        this.node = b;
    }

    /* BRANCHES */
    public void addBranch(Board b) {
        if (b == null) return;
        this.branches.add(b);
    }
    public void addBranch(ArrayList<Board> data) {
        if (data == null) return;
        this.branches = data;
    }

    /* CONDITIONAL CHECK */
    public Boolean isLeaf() {
        return this.branches.size() == 0;
    }
}
