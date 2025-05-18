package solver.heuristic;

import java.util.ArrayList;
import java.util.Collections;

import model.Board;

public class CombinedHeuristic extends Heuristic{
    
    /* CONSTANT ENUM */
    enum HEURISTIC_TYPE {
        MAX,
        MIN,
        SUM,
        AVG
    }

    /* ATTRIBUTE */
    private ArrayList<Heuristic> heuristic;
    private HEURISTIC_TYPE mode;

    /* CONSTRUCTOR */
    public CombinedHeuristic(Heuristic[] h, String mode) {
        this.heuristic = new ArrayList<>();
        for (int i=0; i<h.length; i++) {
            this.heuristic.add(h[i]);
        }
        switch (mode) {
            case "MAX" -> {this.mode = HEURISTIC_TYPE.MAX; break;}
            case "MIN" -> {this.mode = HEURISTIC_TYPE.MIN; break;}
            case "SUM" -> {this.mode = HEURISTIC_TYPE.SUM; break;}
            case "AVG" -> {this.mode = HEURISTIC_TYPE.AVG; break;}
        }
    }

    /* METHOD */
    public Double calculate(Board b) {
        ArrayList<Double> allResults = new ArrayList<>();
        for (Heuristic h : this.heuristic) {
            allResults.add(h.calculate(b));
        }
        Double finalResult = 0.0;
        switch (this.mode) {
            case HEURISTIC_TYPE.MAX -> {finalResult = Collections.max(allResults);}
            case HEURISTIC_TYPE.MIN -> {finalResult = Collections.min(allResults);}
            case HEURISTIC_TYPE.SUM -> {
                Double sum = 0.0;
                for (Double result : allResults) {sum += result;}
                finalResult = sum;
            }
            case HEURISTIC_TYPE.AVG -> {
                Double sum = 0.0;
                for (Double result : allResults) {sum += result;}
                finalResult = sum / allResults.size();
            }
        }
        return finalResult;
    }

}
