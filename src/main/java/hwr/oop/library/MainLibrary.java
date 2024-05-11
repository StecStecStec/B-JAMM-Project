package hwr.oop.library;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class MainLibrary {
    public static void main(String[] args) throws FileNotFoundException {
        List<String> argList = Arrays.asList(args);
        CSVAdapter csvAdapter = new CSVAdapter(".\\src\\main\\resources\\csvFiles\\");
        CLI cli = new CLI(System.out);
        cli.handle(argList, csvAdapter);
    }
}