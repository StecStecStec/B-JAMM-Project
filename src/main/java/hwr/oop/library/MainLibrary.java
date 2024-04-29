package hwr.oop.library;

import java.util.Arrays;
import java.util.List;

public class MainLibrary {
    public static void main(String[] args) {
        List<String> argList = Arrays.asList(args);
        CLI cli = new CLI(argList);
        cli.handle(argList);
    }
}

