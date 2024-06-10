package hwr.oop.library.cli;


import hwr.oop.library.domain.Library;
import hwr.oop.library.persistence.CSVAdapter;
import hwr.oop.library.persistence.Persistence;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class MainLibrary {

    private static String pathToDirectory() throws IOException {

            String directoryNameToFind = "csvFiles"; // Ändern Sie dies in den gesuchten Ordnernamen

            try (Stream<Path> paths = Files.find(
                    Paths.get(System.getProperty("user.dir")).resolve("src"),
                    Integer.MAX_VALUE,
                    (p, attr) -> Files.isReadable(p) && attr.isDirectory() && p.getFileName().toString().equals(directoryNameToFind)
            )) {
                return paths.findFirst().get().toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "";
    }

    @SuppressWarnings("java:S106")
    public static void main(String[] args) throws URISyntaxException, IOException {
        List<String> argList = Arrays.asList(args);
        CLI cli = new CLI(System.out);
        String path = pathToDirectory();
        Persistence persistence = new CSVAdapter(path + "/");
        Library library;
        library = persistence.loadLibrary();
        cli.handle(argList, library, persistence);
    }
}
