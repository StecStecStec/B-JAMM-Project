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

class CLI {
    private List<String> args;

    public CLI(List<String> args) {
        this.args = args;
        // Initialisierungslogik hier
    }

    public void handle(List<String> arguments) {

    }

    public void check(List<String> arguments, int limit, String option) {
        String options = "";

    }
}
