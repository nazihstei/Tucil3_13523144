import java.util.ArrayList;
import java.util.Arrays;

import puzzle.Board;

public class Driver {
    public static void main(String[] args) {
        System.out.println("[==========[ Program Berjalan ]==========]");
        // String inputString ="AAA.\n"  +
        //                     "..B.\n"  +
        //                     "PPB.K\n" +
        //                     "CCC.";
        // String inputString ="   K  \n" +
        //                     ".AAPB.\n" +
        //                     ".D.PB.\n" +
        //                     ".D.CCC\n" +
        //                     "......";
        String inputString =" ....\n" +
                            "KA.PP\n" +
                            " A..C\n" +
                            " BB.C\n" +
                            " .DDD";
        ArrayList<String> input = new ArrayList<>(Arrays.asList(inputString.split("\n")));
        Board testBoard = new Board(input);
        System.out.println(testBoard);
        System.out.println("[==========[ Program Selesai. ]==========]");
    }
}
