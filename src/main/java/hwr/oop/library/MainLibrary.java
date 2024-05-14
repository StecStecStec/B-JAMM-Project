package hwr.oop.library;

import hwr.oop.library.persistance.CSVAdapter;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

public class MainLibrary {
    @SuppressWarnings("java:S106")
    public static void main(String[] args) throws FileNotFoundException {
        List<String> argList = Arrays.asList(args);
        CSVAdapter csvAdapter = new CSVAdapter(".\\src\\main\\resources\\csvFiles\\");
        CLI cli = new CLI(System.out);
        cli.handle(argList, csvAdapter);
    }
}
