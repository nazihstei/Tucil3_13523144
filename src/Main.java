import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.*;
import solver.algorithm.ASTAR;
import solver.algorithm.Algorithm;
import solver.algorithm.BNB;
import solver.algorithm.GBFS;
import solver.algorithm.UCS;
import solver.heuristic.BlockingPieceCount;
import solver.heuristic.CombinedHeuristic;
import solver.heuristic.DistanceToExit;
import solver.heuristic.Heuristic;
import solver.heuristic.SolidnessBlockingPiece;
import utils.*;

public class Main {

    /* MAIN PROGRAM */
    public static void main(String[] args) throws InterruptedException {
        
        /* PREPARATION */
        Scanner input = new Scanner(System.in);
        DualOutput.activate("../log.txt");
        
        /* LOAD CONFIGURATION FILE */
        Main.Header();
        Board rootBoard = null;
        while (rootBoard == null) {
            System.out.println("[*] Silahkan masukkan file konfigurasi");
            System.out.print  ("    >> ");
            String filepath = input.nextLine();
            System.out.println("[*] Memuat konfigurasi ...");
            System.out.print("    ");
            rootBoard = FileHandler.loadFile(filepath);
            System.out.println();
        }
        Thread.sleep(2000);
        System.out.println("[*] Konfigurasi berhasil dimuat!");
        System.out.println();
        Main.Footer();
        
        /* INPUT ALGORITHM AND HAURISTIC */
        Main.Header();
        String algoritmInput = null;
        String heuristicInput = null;
        while (algoritmInput == null) {
            System.out.println("[*] Berikut adalah algoritma yang dapat dipilih:");
            System.out.println("    [1] UCS  : Uniform Cost Search");
            System.out.println("    [2] GBFS : Geedy Best-First Search");
            System.out.println("    [3] A*   : A star");
            System.out.println("    [4] B&B  : Branch and Bound");
            System.out.println();
            System.out.println("[*] Silahkan pilih algoritma yang ingin digunakan");
            System.out.println("[-] Keterangan  : masukkan nomornya");
            System.out.print  ("    >> ");
            algoritmInput = input.nextLine().trim();
            System.out.println();
            if (!(algoritmInput=="1" || algoritmInput=="2" || algoritmInput=="3" || algoritmInput=="4")) algoritmInput = null;
        }
        switch (algoritmInput) {
            case "1" -> {
                System.out.println("[*] Algoritma UCS tidak menggunakan heuristik");
                break;
            }
            case "4" -> {
                String useHeuristicChoice = null;
                while (useHeuristicChoice == null) {
                    System.out.println("[*] Apakah ingin menggunakan heuristik? (Y/N)");
                    System.out.print  ("    >> ");
                    System.out.println();
                    useHeuristicChoice = input.nextLine().toUpperCase().trim();
                    if (!(useHeuristicChoice=="Y" || useHeuristicChoice=="N")) useHeuristicChoice = null;
                }
                if (useHeuristicChoice == "N") break;
            }
            default -> {
                heuristicInput = null;
                while (heuristicInput == null) {
                    System.out.println("[*] Berikut adalah heuristik yang dapat dipilih:");
                    System.out.println("    [1] Distance to Exit");
                    System.out.println("    [2] Count of Blocking Piece");
                    System.out.println("    [3] Solidness of Blocking Piece");
                    System.out.println("    [4] Kombinasi dari heuristik di atas");
                    System.out.println();
                    System.out.println("[*] Silahkan pilih heuristik yang ingin digunakan");
                    System.out.println("[-] Keterangan  : masukkan nomornya");
                    System.out.print  ("    >> ");
                    heuristicInput = input.nextLine().trim();
                    System.out.println();
                    if (!(heuristicInput=="1" || heuristicInput=="2" || heuristicInput=="3" || heuristicInput=="4")) heuristicInput = null;
                }
                while (heuristicInput == "4") {
                    System.out.println("[*] Silahkan pilih kombinasi heuristik yang ingin digunakan");
                    System.out.println("[+] Pilih juga bagaimana kombinasinya (MAX/MIN/SUM/AVG)");
                    System.out.println("[-] format input: mode h1 h2 h3");
                    System.out.println("[-] Contoh      : MAX 1 3");
                    System.out.print  ("    >> ");
                    heuristicInput = input.nextLine().trim();
                    System.out.println();
                    Pattern pattern = Pattern.compile("^(MAX|MIN|SUM|AVG)\\s+(?:(?!([123])\\s+\\2\\s*$).*?(?:[123])(?:\\s+|$)){2,3}$");
                    Matcher matcher  = pattern.matcher(heuristicInput);
                    if (!matcher.matches()) heuristicInput = "4";
                }
                break;
            }
        }
        Main.Footer();
        
        /* SET ALGORITHM AND HEURISTIC */
        Algorithm solver = null;
        Heuristic heuristic = null;
        switch (heuristicInput) {
            case "1" -> {heuristic = new DistanceToExit(); break;}
            case "2" -> {heuristic = new BlockingPieceCount(); break;}
            case "3" -> {heuristic = new SolidnessBlockingPiece(); break;}
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
                    }
                    heuList[i] = h;
                }
                heuristic = new CombinedHeuristic(heuList, heuConf.getFirst());
            }
        }
        switch (algoritmInput) {
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
        Main.Header();
        long startTime = System.currentTimeMillis();
        solver.solve();
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.out.println();
        System.out.println(solver);
        System.out.println();
        System.out.println(    "[*] Berikut adalah informasi pencarian");
        System.out.printf("    [+] Node Count  : %d node\n", solver.getTree().nodeCount());
        System.out.printf("    [+] Duration    : %d ms\n", duration);
        Main.Footer();
        
        /* TERMINATION */
        DualOutput.deactivate();
        input.close();
    }
    
    
    
    /* ADDITIONAL STATIC METHOD */
    
    public static void Header() {
        Terminal.clearScreen();
        System.out.println();
        System.out.println("[===========================================================]");
        System.out.println("[===============[  Rush Hour Solver Master  ]===============]");
        System.out.println("[===========================================================]");
        System.out.println("[     Selamat datang di Solver permainan Rush Hour no.1     ]");
        System.out.println("[   di dunia. Terdapat pilihan algoritma yang keren dengan  ]");
        System.out.println("[  beragam pilihan heuristik yang menarik. Selamat mencoba! ]");
        System.out.println("[===========================================================]");
        System.out.println();
    }
    public static void Footer() throws InterruptedException {
        System.out.println("[===========================================================]");
        Thread.sleep(2000);
    }
}
