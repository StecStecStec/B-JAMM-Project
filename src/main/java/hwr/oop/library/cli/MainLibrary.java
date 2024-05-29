package hwr.oop.library.cli;

import hwr.oop.library.domain.Library;
import hwr.oop.library.persistence.CSVAdapter;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class MainLibrary {
    @SuppressWarnings("java:S106")
    public static void main(String[] args) {
        List<String> argList = Arrays.asList(args);
        URL resourceUrl = MainLibrary.class.getClassLoader().getResource("csvFiles");
        assert resourceUrl != null;
        File directory = new File(resourceUrl.getFile());
        String path = directory.getAbsolutePath() + "/";
        CSVAdapter csvAdapter = new CSVAdapter(path);
        CLI cli = new CLI(System.out);
        cli.handle(argList, Library.createNewLibrary(), csvAdapter);
    }
}
