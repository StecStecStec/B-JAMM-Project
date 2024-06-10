package hwr.oop.library.cli;


import hwr.oop.library.domain.Library;
import hwr.oop.library.persistence.CSVAdapter;
import hwr.oop.library.persistence.Persistence;

import java.util.*;

public class MainLibrary {

    @SuppressWarnings("java:S106")
    public static void main(String[] args) {
        List<String> argList = Arrays.asList(args);
        CLI cli = new CLI(System.out);
        Persistence persistence = new CSVAdapter(argList, "main");
        Library library;
        library = persistence.loadLibrary();
        cli.handle(argList, library, persistence);
    }
}
