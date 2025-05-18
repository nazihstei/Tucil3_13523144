package utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class DualOutput {

    private static PrintStream originalOut = System.out;
    private static PrintStream fileOut = null;
    private static PrintStream dualOut = null;

    public static void activate(String filePath) {
        if (dualOut == null) {
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(filePath, true); // Append ke file
                fileOut = new PrintStream(fileOutputStream);
                dualOut = new PrintStream(new DualOutputStream(originalOut, fileOut));
                System.setOut(dualOut);
                System.out.println("[DualOutput] Dual output activated. Logging to console and: " + filePath);
            } catch (FileNotFoundException e) {
                System.err.println("[DualOutput] Error activating dual output: " + e.getMessage());
            }
        } else {
            System.out.println("[DualOutput] Dual output is already active.");
        }
    }

    public static void deactivate() {
        if (dualOut != null) {
            System.setOut(originalOut);
            if (fileOut != null) {
                fileOut.close();
            }
            dualOut = null;
            fileOut = null;
            System.out.println("[DualOutput] Dual output deactivated.");
        } else {
            System.out.println("[DualOutput] Dual output is not active.");
        }
    }

    // Kelas pembantu untuk menggabungkan dua OutputStream
    private static class DualOutputStream extends java.io.OutputStream {
        private java.io.OutputStream one;
        private java.io.OutputStream two;

        public DualOutputStream(java.io.OutputStream one, java.io.OutputStream two) {
            this.one = one;
            this.two = two;
        }

        @Override
        public void write(int b) throws java.io.IOException {
            one.write(b);
            two.write(b);
        }

        @Override
        public void flush() throws java.io.IOException {
            one.flush();
            two.flush();
        }

        @Override
        public void close() throws java.io.IOException {
            // Jangan menutup originalOut (System.out)
            two.close();
        }
    }

    public static void main(String[] args) {
        System.out.println("Output sebelum aktivasi.");

        // Mengaktifkan dual output ke file "log.txt"
        DualOutput.activate("log.txt");

        System.out.println("Output setelah aktivasi - akan muncul di terminal dan log.txt.");
        int angka = 789;
        System.out.printf("Nilai angka: %d\n", angka);
        System.err.println("Ini juga akan masuk ke log file (jika System.err tidak dialihkan).");

        // Menonaktifkan dual output
        DualOutput.deactivate();

        System.out.println("Output setelah deaktivasi - hanya akan muncul di terminal.");
    }
}