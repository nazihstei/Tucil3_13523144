package utils;

import java.io.IOException;

public class Terminal {

    /* STATIC METHOD */
    public static void clearScreen() {
        try {
            String os = System.getProperty("os.name").toLowerCase();

            // Membersihkan layar di Windows
            if (os.contains("win")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            
            // Membersihkan layar di Unix-like (Linux, macOS)
            } else if (os.contains("nix") || os.contains("nux") || os.contains("mac")) {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            
            // OS tidak dikenali
            } else {
                System.out.println("Sistem operasi tidak dikenali, tidak dapat membersihkan layar.");
            }

        } catch (IOException | InterruptedException e) {
            System.err.println("Gagal membersihkan layar: " + e.getMessage());
        }
    }    
}
