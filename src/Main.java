import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.*;
import solver.algorithm.*;
import solver.heuristic.*;
import utils.*;

public class Main {

    /* MAIN PROGRAM */
    public static void main(String[] args) throws InterruptedException {
        
        /* PREPARATION */
        Scanner input = new Scanner(System.in);
        
        try {
            /* LOAD CONFIGURATION FILE */
            Board rootBoard = null;
            while (rootBoard == null) {
                Home.Header();
                System.out.println("[*] Silahkan masukkan file konfigurasi");
                System.out.print  ("    >> ");
                String filepath = "../test/" + input.nextLine() + ".txt";
                System.out.println("[*] Memuat konfigurasi ...");
                rootBoard = FileHandler.loadFile(filepath);
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
                System.out.println("    [2] GBFS : Geedy Best-First Search");
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
                    if (useHeuristicChoice.equals("N")) {
                        Home.Footer();
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
                        System.out.println("    [4] Kombinasi dari heuristik di atas");
                        System.out.println();
                        System.out.println("[*] Silahkan pilih heuristik yang ingin digunakan");
                        System.out.println("[-] Keterangan  : masukkan nomornya");
                        System.out.print  ("    >> ");
                        heuristicInput = input.nextLine().trim();
                        if (!(  heuristicInput.equals("1") || 
                        heuristicInput.equals("2") || 
                        heuristicInput.equals("3") || 
                        heuristicInput.equals("4"))) heuristicInput = null;
                        if (heuristicInput!=null && heuristicInput.equals("4")) {
                            System.out.println();
                            System.out.println("[*] Silahkan pilih kombinasi heuristik yang ingin digunakan");
                            System.out.println("[+] Pilih juga bagaimana kombinasinya (MAX/MIN/SUM/AVG)");
                            System.out.println("[-] format input: mode h1 h2 h3");
                            System.out.println("[-] Contoh      : MAX 1 3");
                            System.out.print  ("    >> ");
                            heuristicInput = input.nextLine().trim();
                            Pattern pattern = Pattern.compile("^(MAX|MIN|SUM|AVG)\\s+(?:(?!([123])\\s+\\2\\s*$).*?(?:[123])(?:\\s+|$)){2,3}$");
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
            DualOutput.activate("../result.txt");
            Home.Header();
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
            Home.Footer();
            DualOutput.deactivate();
            
        } catch (Exception e) {
            e.printStackTrace();
            
        } finally {
            /* TERMINATION */
            DualOutput.deactivate();  
            input.close();
        }
    }
}

/* ADDITIONAL STATIC METHOD */
class Home {
    public static void Header() {
        Terminal.clearScreen();
        System.out.println();
        System.out.println("[===========================================================]");
        System.out.println("[===============[  Rush Hour Solver Master  ]===============]");
        System.out.println("[===========================================================]");
        System.out.println("[     Selamat datang di Solver permainan Rush Hour no.1     ]");
        System.out.println("[  di dunia. Terdapat pilihan algorithma yang keren dengan  ]");
        System.out.println("[  beragam pilihan heuristik yang menarik. Selamat mencoba! ]");
        System.out.println("[===========================================================]");
        System.out.println();
    }
    public static void Footer() throws InterruptedException {
        System.out.println();
        System.out.println("[===========================================================]");
        Thread.sleep(1000);
    }
}
