package hwr.oop.library.cli;


import hwr.oop.library.domain.Library;
import hwr.oop.library.persistence.CSVAdapter;
import hwr.oop.library.persistence.Persistence;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class MainLibrary {

    private static String pathToDirectory() {
        try {
            Path currentDirectory = Paths.get(System.getProperty("user.dir"));

            try (Stream<Path> stream = Files.walk(currentDirectory, 3)) {
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
        CLI cli = new CLI(System.out);
        String path = pathToDirectory();
        Persistence persistence = new CSVAdapter(path + "/");
        Library library;
        library = persistence.loadLibrary();
        cli.handle(argList, library, persistence);
    }
}
