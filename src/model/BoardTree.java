package model;

import java.util.ArrayList;
import java.util.HashMap;

public class BoardTree implements Comparable<BoardTree> {
    
    /* ATTRIBUTE */
    private BoardTree root;
    private Board node;
    private ArrayList<BoardTree> branches;
    private long depth;
    private static HashMap<BoardTree, ArrayList<BoardTree>> leaves = new HashMap<>();

    /* CONSTRUCTOR */
    public BoardTree(Board node) {
        this.root = null;
        this.node = node;
        this.depth = 0;
        this.branches = new ArrayList<>();
        BoardTree.leaves.put(this, new ArrayList<>());
        BoardTree.leaves.get(this).add(this);
    }
    public BoardTree(Board node, BoardTree root) {
        this.root = root;
        this.node = node;
        this.depth = root.getDepth() + 1;
        this.branches = new ArrayList<>();
        BoardTree.leaves.get(this.getFirstRoot()).add(this);
    }

    /* GETTER and SETTER */
    public BoardTree getRoot() {
        return this.root;
    }
    public BoardTree getFirstRoot() {
        if (this.isRoot()) return this;
        return this.root.getFirstRoot();
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
    public static ArrayList<BoardTree> getLeaves(BoardTree root) {
        return BoardTree.leaves.get(root);
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
        if (BoardTree.leaves.containsKey(this)) BoardTree.leaves.get(this).remove(this);
    }
    public void addBranches(ArrayList<Board> data) {
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

    /* COMPARE */
    @Override
    public int compareTo(BoardTree b) {
        if (Board.isSame(this.getNode(), b.getNode())) {
            return 0;
        }
        return 1;
    }
}
