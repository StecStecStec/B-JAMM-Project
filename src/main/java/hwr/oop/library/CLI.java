package hwr.oop.library;

import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;

class CLI {
    private PrintStream out = System.out;
    private final String createVisitor = "createVisitor";
    private final String createLibrarian = "createLibrarian";
    private final String deleteVisitor = "deleteVisitor";
    private final String deleteLibrarian = "deleteLibrarian";
    private final String addBook = "addBook";
    private final String deleteBook = "deleteBook";
    private final String searchBook = "searchBook";
    private final String returnBook = "returnBook";
    private final String restoreBook = "restoreBook";
    private final String viewBorrowedBooks = "viewBorrowedBooks";
    private final String viewOpenPayments = "viewOpenPayments"; //Visitor
    private final String viewOpenPaymentsLibrarian = "viewOpenPaymentsLibrarian";

    public CLI(OutputStream out) {
        this.out = new PrintStream(out);
    }

    public void handle(List<String> arguments, CSVAdapter csvAdapter) throws FileNotFoundException {


        String result = switch (arguments.get(0)) {
            case createVisitor -> {
                if (check(arguments, 5, createVisitor)) {
                    String name = arguments.get(1);
                    String surname = arguments.get(2);
                    String birthday = arguments.get(3);
                    String email = arguments.get(4);

                    Visitor visitor = Visitor.createNewVisitor(csvAdapter, name, surname, birthday, email);
                    out.println(csvAdapter.getVisitorList());
                    //csvAdapter.saveCSV();
                    yield "Visitor created";
                } else {
                    yield "No Visitor created";
                }
            }
            case createLibrarian -> {
                if (check(arguments, 5, createLibrarian)) {
                    String name = arguments.get(1);
                    String surname = arguments.get(2);
                    String birthday = arguments.get(4);

                    Librarian librarian = Librarian.createNewLibrarian(csvAdapter, name, surname, birthday);
                    out.println(csvAdapter.getVisitorList());
                    yield "Librarian created";
                } else {
                    yield "No Librarian created";
                }
            }
            case deleteVisitor -> {
                yield "";
            }
            case deleteLibrarian -> {
                yield "";
            }
            case addBook -> {
                yield "";
            }
            case deleteBook -> {
                yield "";
            }
            case searchBook -> {
                yield "";
            }
            case returnBook -> {
                yield "";
            }
            case restoreBook -> {
                yield "";
            }
            case viewBorrowedBooks -> {
                yield "";
            }
            case viewOpenPayments -> {
                yield "";
            }
            case viewOpenPaymentsLibrarian -> {
                yield "";
            }
            default -> throw new IllegalStateException("Unexpected value: " + arguments.get(0));
        };

        out.println(result);

    }

    public boolean check(List<String> arguments, int limit, String option) {

        //Shortcuts??? createVisitor = cV  ???
        String options = "createVisitor, createLibrarian, deleteVisitor, deleteLibrarian, addBook, deleteBook, searchBook, returnBook, restoreBook, viewBorrowedBooks, viewOpenPayments,"/*Visitor*/ + " viewOpenPaymentsLibrarian" /*Librarian*/;
        if (arguments.size() != limit) {
            String result = switch (option) {
                case createVisitor, createLibrarian, deleteVisitor, deleteLibrarian ->
                        "Usage: [option] [Name] [Surname] [Birthday] [Email]\n" + options;
                case addBook, deleteBook -> "Usage: [option] [Title] [Genre]\n" + options;
                case searchBook, returnBook, restoreBook -> "Usage: [option] [Title]\n" + options;
                case viewBorrowedBooks, viewOpenPayments, viewOpenPaymentsLibrarian ->
                        "Usage: [option] [Email]\n" + options;
                default -> "Invalid option";
            };

            out.println(result);
            return false;
        }
        return true;

    }
}
