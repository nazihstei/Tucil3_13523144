package utils;

public class MemoryInfo {
    public static String get() {
        Runtime runtime = Runtime.getRuntime();

        long totalMemory = runtime.totalMemory(); // Total memory allocated to the heap
        long freeMemory = runtime.freeMemory();   // Free memory available in the heap
        long maxMemory = runtime.maxMemory();    // Maximum memory the heap can grow to
        
        String info = "";
        
        info = info + String.format("[*] Heap Memory Information\n");
        info = info + String.format("    [+] Total Heap : %s\n", formatSize(totalMemory));
        info = info + String.format("    [+] Free Heap  : %s\n", formatSize(freeMemory));
        info = info + String.format("    [+] Used Heap  : %s\n", formatSize(totalMemory - freeMemory));
        info = info + String.format("    [+] Max Heap   : %s\n", formatSize(maxMemory));
        
        return info;
    }

    private static String formatSize(long bytes) {
        if (bytes < 1024) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(1024));
        char unit = "KMGTPE".charAt(exp - 1);
        return String.format("%.2f %sB", bytes / Math.pow(1024, exp), unit);
    }
}