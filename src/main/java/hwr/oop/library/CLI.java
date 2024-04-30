package hwr.oop.library;

import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;

class CLI {
    private PrintStream out = System.out;

    public CLI(OutputStream out) {
        this.out = new PrintStream(out);
    }

    public void handle(List<String> arguments) throws FileNotFoundException {
        String result = switch (arguments.get(0)) {
            case "createVisitor" -> {
                if (check(arguments, 5, "createVisitor")) {
                    String name = arguments.get(1);
                    String surname = arguments.get(2);
                    String birthday = arguments.get(3);
                    String email = arguments.get(4);

                    Visitor.createNewVisitor(new CSVAdapter("csvFiles\\"),name, surname, birthday, email);
                    yield "Visitor created";
                } else {
                    yield "Visitor not created";
                }
            }
            case "viewBorrowedBooks" -> {
                CSVAdapter csvAdapter = new CSVAdapter("csvFiles\\");
                csvAdapter.loadCSV();out.println(csvAdapter.getVisitorList());
                yield "";

            }
                default -> throw new IllegalStateException("Unexpected value: " + arguments.get(0));
        };

        out.println(result);

    }

    public boolean check(List<String> arguments, int limit, String option) {

        //Shortcuts??? createVisitor = cV  ???
        String options = "createVisitor, createLibrarian, deleteVisitor, deleteLibrarian, addBook, deleteBook, searchBook, returnBook, restoreBook, viewBorrowedBooks, viewOpenPayments,"/*Visitor*/ + ", viewOpenPaymentsLibrarian" /*Librarian*/;
        if (arguments.size() != limit) {
            String result = switch (option) {
                case "createVisitor", "createLibrarian", "deleteVisitor", "deleteLibrarian" -> "Usage: [option] [Name] [Surname] [Birthday] [Email]" + options;
                case "addBook", "deleteBook" -> "Usage: [option] [Title] [Genre]" + options;
                case "searchBook", "returnBook", "restoreBook" -> "Usage: [option] [Title]" + options;
                case "viewBoorowedBookS", "viewOpenPayments", "viewOpenPaymentsLibrarian" -> "Usage: [option] [Email]" + options;
                default -> "Invalid option";
            };

            out.println(result);
            return false;
        }
        return true;

    }
}
