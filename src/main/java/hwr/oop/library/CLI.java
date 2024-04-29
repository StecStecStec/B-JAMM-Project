package hwr.oop.library;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;

class CLI {
    private PrintStream out = System.out;

    public CLI(OutputStream out) {
        this.out = new PrintStream(out);
    }

    public CLI(List<String> argList) {

    }

    public void handle(List<String> arguments) {
        String result = switch (arguments.get(0)) {
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
            case "viewBorrowedBooks" -> {
                CSVAdapter csvAdapter = new CSVAdapter("csvFies\\csvVisitor.csv");
                csvAdapter.
            }

                default -> throw new IllegalStateException("Unexpected value: " + arguments.get(0));
        };

    }

    public boolean check(List<String> arguments, int limit, String option) {

        //Shortcuts??? createVisitor = cV  ???
        String options = "createVisitor, createLibrarian, deleteVisitor, deleteLibrarian, addBook, deleteBook, searchBook, returnBook, restoreBook, viewBorrowedBooks, viewOpenPayments,"/*Visitor*/ + ", viewOpenPaymentsLibrarian" /*Librarian*/;
        if (arguments.size() != limit) {
            String result = switch (option) {
                case "createVisitor", "createLibrarian", "deleteVisitor", "deleteLibrarian" -> {
                    yield "Usage: [option] [Name] [Surname] [Birthday] [Email]" + options;
                }
                case "addBook", "deleteBook" -> {
                    yield "Usage: [option] [Title] [Genre]" + options;
                }
                case "searchBook", "returnBook", "restoreBook" ->{
                    yield "Usage: [option] [Title]" + options;
                }
                case "viewBoorowedBookS", "viewOpenPayments", "viewOpenPaymentsLibrarian" -> {
                    yield "Usage: [option] [Email]" + options;
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
