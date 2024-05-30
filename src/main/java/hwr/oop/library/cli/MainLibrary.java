package hwr.oop.library.cli;


import hwr.oop.library.domain.Library;
import hwr.oop.library.persistence.CSVAdapter;

import java.nio.file.*;
import java.util.*;
import java.util.stream.Stream;

public class MainLibrary {

    private static String pathToDirectory () {
        try {
            Path currentDirectory = Paths.get(System.getProperty("user.dir"));

            try (Stream<Path> stream = Files.walk(currentDirectory)) {
                Optional<Path> directory = stream
                        .filter(Files::isDirectory)
                        .filter(path -> path.getFileName().toString().equals("csvFiles"))
                        .findFirst();

                return directory.map(Path::toString).orElse(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @SuppressWarnings("java:S106")
    public static void main(String[] args) {
        List<String> argList = Arrays.asList(args);
        String path = pathToDirectory();
        CSVAdapter csvAdapter = new CSVAdapter(path);
        CLI cli = new CLI(System.out);
        cli.handle(argList, Library.createNewLibrary());
    }
}
