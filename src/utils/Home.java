package utils;

import model.Board;
import model.BoardTree;
import solver.algorithm.Algorithm;

/* ADDITIONAL STATIC METHOD */
public class Home {

    /* PUBLIC METHOD */
    public static void Header() {
        Terminal.clearScreen();
        System.out.println();
        System.out.println("[===========================================================]");
        System.out.println("[===============[  RUSH HOUR SOLVER MASTER  ]===============]");
        System.out.println("[===========================================================]");
        System.out.println("[     Selamat datang di Solver permainan Rush Hour no.1     ]");
        System.out.println("[  di dunia. Terdapat pilihan algorithma yang keren dengan  ]");
        System.out.println("[  beragam pilihan heuristik yang menarik. Selamat mencoba! ]");
        System.out.println("[===========================================================]");
        System.out.println();
    }
    public static void Footer() throws InterruptedException {
        Home.noSleepFooter();
        Thread.sleep(2000);
    }
    public static void Loading(BoardTree checkedNode, Algorithm solver, long startDuration) throws InterruptedException {
        Home.Header();
        System.out.println(Home.getMemoryInfo());
        Home.printSearchInfo(checkedNode, solver, startDuration);
        Home.noSleepFooter();
    }
    public static void Solution(Algorithm solver, long duration, Boolean color) throws InterruptedException {
        Home.Header();
        System.out.println(    "[*] Berikut adalah informasi pencarian");
        System.out.printf("    [+] Node Count  : %d node\n", solver.getTree().nodeCount());
        System.out.printf("    [+] Max Depth   : %d level\n", solver.getTree().getMaxDepth());
        System.out.printf("    [+] Duration    : %d ms\n", duration);
        System.out.println();
        System.out.println("[===========================================================]");
        System.out.println("[===============[   THIS IS YOUR SOLUTION   ]===============]");
        System.out.println("[===========================================================]");
        System.out.println(solver.toString(4, color));
        if (color != null && color) {
            Home.Footer();
        } else {
            Home.noSleepFooter();
        }
    }
    public static void AnimateSolution(Algorithm solver, long duration) {
        try {
            while (true) {
                for (Board b : solver.getSolution()) {
                    Home.Header();
                    System.out.println(    "[*] Berikut adalah informasi pencarian");
                    System.out.printf("    [+] Node Count  : %d node\n", solver.getTree().nodeCount());
                    System.out.printf("    [+] Max Depth   : %d level\n", solver.getTree().getMaxDepth());
                    System.out.printf("    [+] Duration    : %d ms\n", duration);
                    System.out.println();
                    if (b.isSolved()) {
                        System.out.printf("[*] Gerakan %d (SOLVED)\n", solver.getSolution().indexOf(b));
                        System.out.println(b.toStringColor(4, false));
                        Home.noSleepFooter();
                        Thread.sleep(2000);
                    } else {
                        System.out.printf("[*] Gerakan %d\n", solver.getSolution().indexOf(b));
                        System.out.println(b.toStringColor(4, false));
                        Home.noSleepFooter();
                        Thread.sleep(250);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("[*] Program selesai");
        } finally {
            Terminal.clearScreen();
        }
    }
    
    /* PRIVATE METHOD */
    private static void noSleepFooter() {
        System.out.println();
        System.out.println("[===========================================================]");
    }
    
    private static void printSearchInfo(BoardTree checkedNode, Algorithm solver, long startDuration) {
        System.out.println("[*] Dynamic Tree Information");
        System.out.printf("    [+] Node Count    : %d node\n", solver.getTree().nodeCount());
        System.out.printf("    [+] Current Depth : %d level\n", checkedNode.getDepth());
        System.out.printf("    [+] Exec Time     : %d ms\n", System.currentTimeMillis() - startDuration);
        System.out.println();
        System.out.println("[*] Checked Node");
        System.out.println(checkedNode.getNode());
    }
    private static String getMemoryInfo() {
        Runtime runtime = Runtime.getRuntime();

        long totalMemory = runtime.totalMemory(); // Total memory allocated to the heap
        long freeMemory = runtime.freeMemory();   // Free memory available in the heap
        long maxMemory = runtime.maxMemory();    // Maximum memory the heap can grow to
        
        String info = "";
        
        info = info + String.format("[*] Heap Memory Information\n");
        info = info + String.format("    [+] Total Heap    : %s\n", formatSize(totalMemory));
        info = info + String.format("    [+] Free Heap     : %s\n", formatSize(freeMemory));
        info = info + String.format("    [+] Used Heap     : %s\n", formatSize(totalMemory - freeMemory));
        info = info + String.format("    [+] Max Heap      : %s\n", formatSize(maxMemory));
        
        return info;
    }
    private static String formatSize(long bytes) {
        if (bytes < 1024) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(1024));
        char unit = "KMGTPE".charAt(exp - 1);
        return String.format("%.2f %sB", bytes / Math.pow(1024, exp), unit);
    }
}