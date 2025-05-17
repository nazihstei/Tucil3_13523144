import java.util.Scanner;

import model.*;
import utils.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Scanner input = new Scanner(System.in);

        Terminal.clearScreen();
        System.out.println();
        System.out.println("[=====================================================]");
        System.out.println("[============[  Rush Hour Solver Master  ]============]");
        System.out.println("[=====================================================]");
        System.out.println("[  Selamat datang di Solver permainan Rush Hour no.1  ]");
        System.out.println("[  di dunia. Terdapat pilihan algoritma keren dengan  ]");
        System.out.println("[  pilihan heuristik yang menarik. Selamat mencoba!   ]");
        System.out.println("[=====================================================]");
        System.out.println();
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
        System.out.println("[=====================================================]");
        
        input.close();
    }
}
