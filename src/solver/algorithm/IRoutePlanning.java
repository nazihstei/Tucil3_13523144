package solver.algorithm;

import java.util.Comparator;

import model.BoardTree;

public interface IRoutePlanning extends Comparator<BoardTree> {

    /* COMPARATOR ALGORITHM */
    public int compare(BoardTree b1, BoardTree b2);

}
