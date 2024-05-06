package hwr.oop.library;

import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

class CLI {
    private PrintStream out = System.out;
    private final String createVisitor = "createVisitor";
    private final String createLibrarian = "createLibrarian";
    private final String deleteVisitor = "deleteVisitor";
    private final String deleteLibrarian = "deleteLibrarian";
    private final String addBook = "addBook";
    private final String viewBooks = "viewBooks";
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
                    csvAdapter.loadCSV();
                    int i = 0;
                    String name = arguments.get(1);
                    String surname = arguments.get(2);
                    String birthday = arguments.get(3);
                    String email = arguments.get(4);

                    while (i < csvAdapter.getVisitorList().size()) {
                        if (Objects.equals(csvAdapter.getVisitorList().get(i).getVisitorEmailAddress(), email)) {
                            yield "Mail already exists";
                        }
                        i++;
                    }

                    Visitor.createNewVisitor(csvAdapter, name, surname, birthday, email);
                    out.println(csvAdapter.getVisitorList());
                    csvAdapter.saveCSV();
                    yield "Visitor created";
                } else {
                    yield "Invalid Input";
                }
            }
            case createLibrarian -> {
                if (check(arguments, 4, createLibrarian)) {
                    int i = 0;
                    csvAdapter.loadCSV();
                    String name = arguments.get(1);
                    String surname = arguments.get(2);
                    String birthday = arguments.get(3);

                    while (i < csvAdapter.getLibrarianList().size()) {
                        if (Objects.equals(csvAdapter.getLibrarianList().get(i).getLibrarianName(), name) && Objects.equals(csvAdapter.getLibrarianList().get(i).getLibrarianSurname(), surname)) {
                            yield "Librarian already exists";
                        }
                        i++;
                    }

                    Librarian.createNewLibrarian(csvAdapter, name, surname, birthday);
                    csvAdapter.saveCSV();
                    yield "Librarian created";
                } else {
                    yield "Invalid Input";
                }
            }
            case deleteVisitor -> {
                if (check(arguments, 2, deleteVisitor)) {
                    csvAdapter.loadCSV();
                    String mail = arguments.get(1);
                    int i = 0;
                    while (i < csvAdapter.getVisitorList().size()) {
                        if (Objects.equals(csvAdapter.getVisitorList().get(i).getVisitorEmailAddress(), mail)) {
                            csvAdapter.deleteVisitor(csvAdapter.getVisitorList().get(i));
                        }
                        i++;
                    }
                    csvAdapter.saveCSV();
                    yield "Visitor deleted";
                } else {
                    yield "Invalid Input";
                }
            }
            case deleteLibrarian -> {
                int i = 0;
                if (check(arguments, 4, deleteLibrarian)) {
                    csvAdapter.loadCSV();
                    String name = arguments.get(1);
                    String surname = arguments.get(2);
                    String birthday = arguments.get(3);
                    while (i < csvAdapter.getLibrarianList().size()) {
                        if (Objects.equals(csvAdapter.getLibrarianList().get(i).getLibrarianName(), name) && Objects.equals(csvAdapter.getLibrarianList().get(i).getLibrarianSurname(), surname) && Objects.equals(csvAdapter.getLibrarianList().get(i).getLibrarianBirthday(), birthday)) {
                            csvAdapter.deleteLibrarian(csvAdapter.getLibrarianList().get(i));
                        }
                        i++;
                    }
                    csvAdapter.saveCSV();
                    yield "Librarian deleted";
                } else {
                    yield "Invalid Input";
                }

            }
            case addBook -> {
                if (check(arguments, 6, addBook)) {
                    csvAdapter.loadCSV();
                    int i = 0;
                    String title = arguments.get(1);
                    String author = arguments.get(2);
                    String genre = arguments.get(3);
                    int bookCondition = Integer.parseInt(arguments.get(4));
                    int bookWidth = Integer.parseInt(arguments.get(5));

                    while (i < csvAdapter.getShelfList().size()) {
                        if (Objects.equals(csvAdapter.getShelfList().get(i).getGenre(), genre)) {
                            Shelf shelf = csvAdapter.getShelfList().get(i);
                            Book.createNewBook(csvAdapter, title, author, genre, shelf, bookCondition, bookWidth);
                            csvAdapter.saveCSV();
                            yield "Book added";
                        }
                        i++;
                    }
                    csvAdapter.saveCSV();
                    yield "No Shelf found";
                } else {
                    yield "Invalid Input";
                }

            }
            case viewBooks -> {
                if (check(arguments, 1, viewBooks)) {
                    csvAdapter.loadCSV();
                    int i = 0;

                    while (i < csvAdapter.getBookList().size()) {
                        out.println(csvAdapter.getBookList().get(i).getBookTitle());
                        i++;
                    }
                    csvAdapter.saveCSV();
                    yield "Books viewed";
                } else {
                    yield "Invalid Input";
                }

            }
            case deleteBook -> {
                if (check(arguments, 6, deleteBook)) {
                    csvAdapter.loadCSV();
                    int i = 0;
                    UUID uuid = UUID.fromString(arguments.get(1));

                    while (i < csvAdapter.getShelfList().size()) {
                        if (Objects.equals(csvAdapter.getBookList().get(i).getBookID(), uuid)) {
                            Book book = csvAdapter.getBookList().get(i);
                            csvAdapter.deleteBook(book);
                            csvAdapter.saveCSV();
                            yield "Book added";
                        }
                        i++;
                    }
                    csvAdapter.saveCSV();
                    yield "No Shelf found";
                } else {
                    yield "Invalid Input";
                }
            }
            case searchBook -> {
                yield "Invalid Input";
            }
            case returnBook -> {
                yield "Invalid Input";
            }
            case restoreBook -> {
                yield "Invalid Input";
            }
            case viewBorrowedBooks -> {
                yield "Invalid Input";
            }
            case viewOpenPayments -> {
                yield "Invalid Input";
            }
            case viewOpenPaymentsLibrarian -> {
                yield "Invalid Input";
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
                case createVisitor, createLibrarian ->
                        "Usage: [option] [Name] [Surname] [Birthday] [Email]\n" + options;
                case deleteLibrarian -> "Usage: [option] [Name] [Surname] [Birthday]\n" + options;
                case addBook, deleteBook -> "Usage: [option] [Title] [Author] [Genre] [BookCondition] [BookWidth]\n" + options;
                case searchBook, returnBook, restoreBook -> "Usage: [option] [Title]\n" + options;
                case viewBorrowedBooks, viewOpenPayments, viewOpenPaymentsLibrarian, deleteVisitor ->
                        "Usage: [option] [Email]\n" + options;
                case viewBooks -> "Usage: [option]\n" + option;
                default -> "Invalid option";
            };

            out.println(result);
            return false;
        }
        return true;

    }
}
