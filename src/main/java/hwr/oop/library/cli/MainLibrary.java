package hwr.oop.library.cli;


import hwr.oop.library.domain.Library;
import hwr.oop.library.persistence.CSVAdapter;
import hwr.oop.library.persistence.Persistence;

import java.net.URISyntaxException;
import java.util.*;

public class MainLibrary {

    private static String pathToDirectory() throws URISyntaxException {
        return Objects.requireNonNull(MainLibrary.class.getClassLoader().getResource("csvFiles")).toURI().getPath();
    }

    @SuppressWarnings("java:S106")
    public static void main(String[] args) throws URISyntaxException {
        List<String> argList = Arrays.asList(args);
        CLI cli = new CLI(System.out);
        String path = pathToDirectory();
        Persistence persistence = new CSVAdapter(path + "/");
        Library library;
        library = persistence.loadLibrary();
        cli.handle(argList, library, persistence);
    }
}
