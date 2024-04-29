package hwr.oop.library;

import java.io.PrintStream;
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
    PrintStream out = System.out;

    public CLI(List<String> argList) {

    }

    public void handle(List<String> arguments) {
        String result = switch (arguments.get(0)){
            case "createVisitor" -> {
                if (check(arguments, 5, "createVisitor")) {
                    String name = arguments.get(1);
                    String surname = arguments.get(2);
                    String birthday = arguments.get(3);
                    String email = arguments.get(4);

                    Visitor visitor = Visitor.createNewVisitor(new CSVAdapter(), surname, birthday, email);
                    yield "Visitor created";
                } else {
                    yield "Visitor not created";
                }
            }
            case ""

            default -> throw new IllegalStateException("Unexpected value: " + arguments.get(0));
        };

    }

    public boolean check(List<String> arguments, int limit, String option) {

        //Shortcuts??? createVisitor = cV  ???
        String options = "createVisitor, createLibrarian, addBook, deleteVisitor, deleteLibrarian, deleteBook, searchBook, returnBook, viewOpenPayment /*Visitor*/, viewBorrowedBooks, viewOpenPayments/*Librarian*/, "+"";
        if(arguments.size() != limit) {
            String result = switch (option) {
                case "createVisitor", "createLibrarian" -> {
                    yield "Usage: [option] [name] [surname] [birthday] [email]";
                }
                default -> {
                    yield "Invalid option";
                }
            };

            out.println(result);
            return false;
        }
        return true;

    }
}
