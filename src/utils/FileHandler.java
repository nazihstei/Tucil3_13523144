package utils;

import model.Board;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

    /* STATIC METHOD */
    public static Board loadFile(String filepath) {
        Path path = Paths.get(filepath);
        try {
            List<String> tempList = Files.readAllLines(path);
            ArrayList<String> fileRows = new ArrayList<>(tempList.subList(2, tempList.size()));
            return new Board(fileRows);
        } catch (IOException e) {
            System.err.println("[X] ERROR ketika membaca file: " + e.getMessage());
            return null;
        }
    }

}