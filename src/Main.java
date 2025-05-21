
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.fusesource.jansi.AnsiConsole;

import model.*;
import solver.algorithm.*;
import solver.heuristic.*;
import utils.*;

public class Main {

    /* MAIN PROGRAM */
    public static void main(String[] args) throws InterruptedException {
        
        /* PREPARATION */
        AnsiConsole.systemInstall();
        Scanner input = new Scanner(System.in);
        
        try {
            /* LOAD CONFIGURATION FILE */
            Thread.sleep(0);
            Board rootBoard = null;
            while (rootBoard == null) {
                Home.Header();
                System.out.println("[*] Silahkan masukkan file konfigurasi");
                System.out.print  ("    >> ./test/");
                String filepath = "./test/" + input.nextLine();
                System.out.println();
                System.out.println("[*] Memuat konfigurasi ...");
                Thread.sleep(1000);
                rootBoard = FileHandler.loadFile(filepath);
                if (rootBoard!= null && !rootBoard.isExitValid()) {
                    System.err.println("[X] EXIT TIDAK VALID");
                    rootBoard = null;
                }
                Home.Footer();
            }
            Home.Header();
            System.out.println("[*] Konfigurasi berhasil dimuat!");
            Home.Footer();
            
            /* INPUT ALGORITHM AND HAURISTIC */
            String algorithmInput = null;
            String heuristicInput = null;
            while (algorithmInput == null) {
                Home.Header();
                System.out.println("[*] Berikut adalah algorithma yang dapat dipilih:");
                System.out.println("    [1] UCS  : Uniform Cost Search");
                System.out.println("    [2] GBFS : Greedy Best-First Search");
                System.out.println("    [3] A*   : A star");
                System.out.println("    [4] B&B  : Branch and Bound");
                System.out.println();
                System.out.println("[*] Silahkan pilih algorithma yang ingin digunakan");
                System.out.println("[-] Keterangan  : masukkan nomornya");
                System.out.print  ("    >> ");
                algorithmInput = input.nextLine().trim();
                // System.out.println();
                Home.Footer();
                if (!(  algorithmInput.equals("1") || 
                        algorithmInput.equals("2") || 
                        algorithmInput.equals("3") ||
                        algorithmInput.equals("4"))) algorithmInput = null;
            }
            Home.Header();
            switch (algorithmInput) {
                case "1" :
                    System.out.println("[*] Algorithma UCS tidak menggunakan heuristik");
                    Home.Footer();
                    break;
                case "4" :
                    String useHeuristicChoice = null;
                    while (useHeuristicChoice == null) {
                        System.out.println("[*] Apakah ingin menggunakan heuristik? (Y/N)");
                        System.out.print  ("    >> ");
                        useHeuristicChoice = input.nextLine().toUpperCase().trim();
                        if (!(  useHeuristicChoice.equals("Y") || 
                                useHeuristicChoice.equals("N"))) useHeuristicChoice = null;
                    }
                    Home.Footer();
                    if (useHeuristicChoice.equals("N")) {
                        break;
                    }
                default :
                    heuristicInput = null;
                    while (heuristicInput == null) {
                        Home.Header();
                        System.out.println("[*] Berikut adalah heuristik yang dapat dipilih:");
                        System.out.println("    [1] Distance to Exit");
                        System.out.println("    [2] Count of Blocking Piece");
                        System.out.println("    [3] Solidness of Blocking Piece");
                        System.out.println("    [4] Movement to Make Way");
                        System.out.println("    [5] Kombinasi dari heuristik di atas");
                        System.out.println();
                        System.out.println("[*] Silahkan pilih heuristik yang ingin digunakan");
                        System.out.println("[-] Keterangan  : masukkan nomornya");
                        System.out.print  ("    >> ");
                        heuristicInput = input.nextLine().trim();
                        if (!(  heuristicInput.equals("1") || 
                        heuristicInput.equals("2") || 
                        heuristicInput.equals("3") || 
                        heuristicInput.equals("4") || 
                        heuristicInput.equals("5"))) heuristicInput = null;
                        if (heuristicInput!=null && heuristicInput.equals("5")) {
                            System.out.println();
                            System.out.println("[*] Silahkan pilih kombinasi heuristik yang ingin digunakan");
                            System.out.println("[+] Pilih juga bagaimana kombinasinya (MAX/MIN/SUM/AVG)");
                            System.out.println("[-] format input: mode h1 h2 h3 h4");
                            System.out.println("[-] Contoh      : MAX 1 3");
                            System.out.print  ("    >> ");
                            heuristicInput = input.nextLine().trim();
                            Pattern pattern = Pattern.compile("^(MAX|MIN|SUM|AVG)\\s+([1-4](?:\\s+[1-4]){0,3})$");
                            Matcher matcher  = pattern.matcher(heuristicInput);
                            if (!matcher.matches()) heuristicInput = null;
                        }
                        Home.Footer();
                    }
                    break;
            }
            
            /* SET ALGORITHM AND HEURISTIC */
            Algorithm solver = null;
            Heuristic heuristic = null;
            switch (heuristicInput) {
                case "1" -> {heuristic = new DistanceToExit(); break;}
                case "2" -> {heuristic = new BlockingPieceCount(); break;}
                case "3" -> {heuristic = new SolidnessBlockingPiece(); break;}
                case "4" -> {heuristic = new MovementToMakeWay(); break;}
                case null-> {break;}
                default  -> {
                    ArrayList<String> heuConf = new ArrayList<>(Arrays.asList(heuristicInput.split(" ")));
                    Heuristic[] heuList = new Heuristic[heuConf.size()-1];
                    for (int i=0; i<heuConf.size()-1; i++) {
                        Heuristic h = null;
                        switch (heuConf.get(i+1)) {
                            case "1" -> {h = new DistanceToExit(); break;}
                            case "2" -> {h = new BlockingPieceCount(); break;}
                            case "3" -> {h = new SolidnessBlockingPiece(); break;}
                            case "4" -> {h = new MovementToMakeWay(); break;}
                        }
                        heuList[i] = h;
                    }
                    heuristic = new CombinedHeuristic(heuList, heuConf.getFirst());
                }
            }
            switch (algorithmInput) {
                case "1" -> {solver = new UCS(rootBoard); break;}
                case "2" -> {solver = new GBFS(rootBoard, heuristic); break;}
                case "3" -> {solver = new ASTAR(rootBoard, heuristic); break;}
                default  -> {
                    if (heuristic == null)  solver = new BNB(rootBoard); 
                    else                    solver = new BNB(rootBoard, heuristic);
                    break;
                }
            }
            
            /* RUN SOLVER */
            Home.Header();
            String realtimeChoice = null;
            while (realtimeChoice == null) {
                System.out.println  ("[*] Aktifkan RealTime mode? (Y/N)");
                System.out.println  ("[-] Note: mode ini dapat memperlambat eksekusi");
                System.out.print    ("    >> ");
                realtimeChoice = input.nextLine().toUpperCase().trim();
                if (!(  realtimeChoice.equals("Y") || 
                        realtimeChoice.equals("N"))) realtimeChoice = null;
            }
            long startTime = 0;
            switch (realtimeChoice) {
                case "Y" -> {
                    startTime = System.currentTimeMillis();
                    solver.solve(true);
                }
                case "N" -> {
                    Home.Header();
                    System.out.println("[*] Please Wait ...");
                    Home.Footer();
                    startTime = System.currentTimeMillis();
                    solver.solve(false);
                }
            }
            long endTime = System.currentTimeMillis();
            long durantion = endTime - startTime;

            /* PRINT OUTPUT TO FILE */
            Home.Header();
            System.out.println("[*] Menulis ke file output.txt ...");
            Home.Footer();
            DualOutput.activate("output.txt");
            Home.Solution(solver, durantion, false);
            DualOutput.deactivate();

            /* PRINT RESULT TO TERMINAL */
            Home.Solution(solver, durantion, true);
            System.out.println();
            System.out.println  ("[*] Tekan enter untuk menampilkan CLI animation");
            System.out.print    ("    >> ");
            input.nextLine();

            /* PRINT RESULT ANIMATION */
            Home.AnimateSolution(solver, durantion);

        } catch (Exception e) {
            DualOutput.deactivate();  
            e.printStackTrace();
            
        } finally {
            /* TERMINATION */
            input.close();
        }
    }
}


