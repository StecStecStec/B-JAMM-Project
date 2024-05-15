package hwr.oop.library.cli;

import hwr.oop.library.persistence.CSVAdapter;

import java.util.Arrays;
import java.util.List;

public class MainLibrary {
    @SuppressWarnings("java:S106")
    public static void main(String[] args) {
        List<String> argList = Arrays.asList(args);
        CSVAdapter csvAdapter = new CSVAdapter(".\\src\\main\\resources\\csvFiles\\");
        CLI cli = new CLI(System.out);
        cli.handle(argList, csvAdapter);
    }
}
