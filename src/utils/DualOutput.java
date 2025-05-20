package utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class DualOutput {

    private static PrintStream originalOut = System.out;
    private static PrintStream fileOut = null;
    // private static PrintStream dualOut = null;
    private static boolean isActive = false;

    public static void activate(String filePath) {
        if (!isActive) {
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(filePath, false);
                fileOut = new PrintStream(fileOutputStream);
                // dualOut = new PrintStream(new DualOutputStream(originalOut, fileOut));
                // System.setOut(dualOut);
                System.setOut(fileOut);
                isActive = true;
                // System.out.println("[DualOutput] Dual output activated. Logging to console and: " + filePath);
            } catch (FileNotFoundException e) {
                System.err.println("[DualOutput] Error activating dual output: " + e.getMessage());
            }
        } else {
            System.out.println("[DualOutput] Dual output is already active.");
        }
    }

    public static void deactivate() {
        if (isActive) {
            System.setOut(originalOut);
            if (fileOut != null) {
                fileOut.close();
            }
            // dualOut = null;
            fileOut = null;
            isActive = false;
            // System.out.println("[DualOutput] Dual output deactivated.");
        } else {
            System.out.println("[DualOutput] Dual output is not active.");
        }
    }

    // private static class DualOutputStream extends java.io.OutputStream {
    //     private java.io.OutputStream one;
    //     private java.io.OutputStream two;

    //     public DualOutputStream(java.io.OutputStream one, java.io.OutputStream two) {
    //         this.one = one;
    //         this.two = two;
    //     }

    //     @Override
    //     public void write(int b) throws java.io.IOException {
    //         one.write(b);
    //         two.write(b);
    //     }

    //     @Override
    //     public void flush() throws java.io.IOException {
    //         one.flush();
    //         two.flush();
    //     }

    //     @Override
    //     public void close() throws java.io.IOException {
    //         two.close();
    //     }
    // }

}