package hwr.oop.library.cli;

import hwr.oop.library.domain.*;
import hwr.oop.library.persistence.CSVAdapter;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@SuppressWarnings("java:S106")
public class CLI {
    private final PrintStream out;
    private static final String CREATE_VISITOR = "createVisitor";
    private static final String CREATE_LIBRARIAN = "createLibrarian";
    private static final String DELETE_VISITOR = "deleteVisitor";
    private static final String DELETE_LIBRARIAN = "deleteLibrarian";
    private static final String ADD_BOOK = "addBook";
    private static final String VIEW_BOOKS = "viewBooks";
    private static final String DELETE_BOOK = "deleteBook";
    private static final String SEARCH_BOOK = "searchBook";
    private static final String BORROW_BOOK = "borrowBook";
    private static final String RETURN_BOOK = "returnBook";
    private static final String RESTORE_BOOK = "restoreBook";
    private static final String VIEW_BORROWED_BOOKS = "viewBorrowedBooks";
    private static final String VIEW_OPEN_PAYMENTS = "viewOpenPayments"; //Visitor
    private static final String VIEW_OPEN_PAYMENTS_LIBRARIAN = "viewOpenPaymentsLibrarian";
    private static final String INVALID_INPUT = "Invalid Input";
    private static final String BOOK_WASNT_FOUND = "Book wasn't found";

    public CLI(OutputStream out) {
        this.out = new PrintStream(out);
    }

    public void handle(List<String> arguments, Library library, CSVAdapter csvAdapter) {

        String result = switch (arguments.getFirst()) {
            case CREATE_VISITOR -> createVisitor(arguments, library);
            case CREATE_LIBRARIAN -> createLibrarian(arguments, library);
            case DELETE_VISITOR -> deleteVisitor(arguments, library);
            case DELETE_LIBRARIAN -> deleteLibrarian(arguments, library);
            case ADD_BOOK -> addBook(arguments, library);
            case VIEW_BOOKS -> viewBooks(arguments, library);
            case DELETE_BOOK -> deleteBook(arguments, library);
            case SEARCH_BOOK -> searchBook(arguments, library);
            case BORROW_BOOK -> borrowBook(arguments, library);
            case RETURN_BOOK -> returnBook(arguments, library);
            case RESTORE_BOOK -> restoreBook(arguments, library);
            case VIEW_BORROWED_BOOKS -> viewBorrowedBooks(arguments, library);
            default -> throw new IllegalStateException("Unexpected value: " + arguments.getFirst());
        };

        out.println(result);
    }

    private String createVisitor(List<String> arguments, Library library) {
        if (check(arguments, 5, CREATE_VISITOR)) {
            int i = 0;
            String name = arguments.get(1);
            String surname = arguments.get(2);
            String birthday = arguments.get(3);
            String email = arguments.get(4);
            while (i < library.getVisitorList().size()) {
                if (Objects.equals(library.getVisitorList().get(i).getVisitorEmailAddress(), email)) {
                    return "Mail already exists";
                }
                i++;
            }
            Visitor.createNewVisitor(library, name, surname, birthday, email);

            return "Visitor created";
        } else {
            return INVALID_INPUT;
        }
    }

    private String createLibrarian(List<String> arguments, Library library) {
        if (check(arguments, 4, CREATE_LIBRARIAN)) {
            int i = 0;
            String name = arguments.get(1);
            String surname = arguments.get(2);
            String birthday = arguments.get(3);
            while (i < library.getLibrarianList().size()) {
                if (Objects.equals(library.getLibrarianList().get(i).getLibrarianName(), name) && Objects.equals(library.getLibrarianList().get(i).getLibrarianSurname(), surname)) {
                    return "Librarian already exists";
                }
                i++;
            }
            Librarian.createNewLibrarian(library, name, surname, birthday);
            return "Librarian created";
        } else {
            return INVALID_INPUT;
        }
    }

    private String deleteVisitor(List<String> arguments, Library library) {
        if (check(arguments, 2, DELETE_VISITOR)) {
            String mail = arguments.get(1);
            int i = 0;
            while (i < library.getVisitorList().size()) {
                if (Objects.equals(library.getVisitorList().get(i).getVisitorEmailAddress(), mail)) {
                    library.deleteVisitor(library.getVisitorList().get(i));
                    return "Visitor deleted";
                }
                i++;
            }
            return "Visitor wasn't found";
        } else {
            return INVALID_INPUT;
        }
    }

    private String deleteLibrarian(List<String> arguments, Library library) {
        int i = 0;
        if (check(arguments, 4, DELETE_LIBRARIAN)) {
            String name = arguments.get(1);
            String surname = arguments.get(2);
            String birthday = arguments.get(3);
            while (i < library.getLibrarianList().size()) {
                if (Objects.equals(library.getLibrarianList().get(i).getLibrarianName(), name) && Objects.equals(library.getLibrarianList().get(i).getLibrarianSurname(), surname) && Objects.equals(library.getLibrarianList().get(i).getLibrarianBirthday(), birthday)) {
                    library.deleteLibrarian(library.getLibrarianList().get(i));
                    return "Librarian deleted";
                }
                i++;
            }
            return "Librarian wasn't found";
        } else {
            return INVALID_INPUT;
        }
    }

    private String addBook(List<String> arguments, Library library) {
        if (check(arguments, 6, ADD_BOOK)) {
            int i = 0;
            String title = arguments.get(1);
            String author = arguments.get(2);
            String genre = arguments.get(3);
            int bookCondition = Integer.parseInt(arguments.get(4));
            int bookWidth = Integer.parseInt(arguments.get(5));
            while (i < library.getShelfList().size()) {
                if (Objects.equals(library.getShelfList().get(i).getGenre(), genre)) {
                    Shelf shelf = library.getShelfList().get(i);
                    Book.createNewBook(library, title, author, genre, shelf, bookCondition, bookWidth);
                    return "Book added";
                }
                i++;
            }
            return "No Shelf found";
        } else {
            return INVALID_INPUT;
        }
    }

    private String viewBooks(List<String> arguments, Library library) {
        if (check(arguments, 1, VIEW_BOOKS)) {
            int i = 0;

            out.println("BookID\t\t\t\t\tTitle\tAuthor\tGenre");
            while (i < library.getBookList().size()) {
                out.print(library.getBookList().get(i).getBookID() + "\t");
                out.print(library.getBookList().get(i).getBookTitle() + "\t");
                out.print(library.getBookList().get(i).getBookAuthor() + "\t");
                out.print(library.getBookList().get(i).getBookGenre() + "\n");
                i++;
            }
            return "Books viewed";
        } else {
            return INVALID_INPUT;
        }
    }

    private String deleteBook(List<String> arguments, Library library) {
        if (check(arguments, 2, DELETE_BOOK)) {
            int i = 0;
            UUID uuid = UUID.fromString(arguments.get(1));

            while (i < library.getBookList().size()) {
                if (Objects.equals(library.getBookList().get(i).getBookID(), uuid)) {
                    Book book = library.getBookList().get(i);
                    library.deleteBook(book);
                    return "Book deleted";
                }
                i++;
            }
            return "No Book found";
        } else {
            return INVALID_INPUT;
        }
    }

    private String searchBook(List<String> arguments, Library library) {
        if (check(arguments, 2, SEARCH_BOOK)) {
            int i = 0;

            out.println("BookID\t\t\t\t\tTitle\tAuthor\tGenre");
            while (i < library.getBookList().size()) {
                if (Objects.equals(library.getBookList().get(i).getBookTitle(), arguments.get(1))) {
                    out.print(library.getBookList().get(i).getBookID() + "\t");
                    out.print(library.getBookList().get(i).getBookTitle() + "\t");
                    out.print(library.getBookList().get(i).getBookAuthor() + "\t");
                    out.print(library.getBookList().get(i).getBookGenre() + "\n");
                }
                i++;
            }
            return "Books searched";
        }
        return INVALID_INPUT;
    }

    private String borrowBook(List<String> arguments, Library library) {
        if (check(arguments, 3, BORROW_BOOK)) {
            UUID uuid = UUID.fromString(arguments.get(1));
            String email = arguments.get(2);
            int i = 0;
            int j = 0;
            while (i < library.getBookList().size()) {
                while (j < library.getVisitorList().size() && Objects.equals(library.getBookList().get(i).getBookID(), uuid)) {
                    if (Objects.equals(library.getVisitorList().get(j).getVisitorEmailAddress(), email)) {
                        library.getBookList().get(i).borrow(library.getVisitorList().get(j));
                        return "Book borrowed";
                    }
                    j++;
                }
                i++;
            }
            return BOOK_WASNT_FOUND;
        } else {
            return INVALID_INPUT;
        }
    }

    private String returnBook(List<String> arguments, Library library) {
        if (check(arguments, 2, RETURN_BOOK)) {
            int i = 0;
            int j = 0;

            UUID uuid = UUID.fromString(arguments.get(1));
            while (i < library.getBookList().size()) {
                while (j < library.getShelfList().size() && library.getBookList().get(i).getBookID().equals(uuid)) {
                    if (Objects.equals(library.getShelfList().get(j).getGenre(), library.getBookList().get(i).getBookGenre())) {
                        Shelf shelf = library.getShelfList().get(j);
                        library.getBookList().get(i).returnBook(shelf);
                        return "Book returned";
                    }
                    j++;
                }
                i++;
            }
            return BOOK_WASNT_FOUND;
        } else {
            return INVALID_INPUT;
        }
    }

    private String restoreBook(List<String> arguments, Library library) {
        if (check(arguments, 2, RESTORE_BOOK)) {
            int i = 0;

            UUID uuid = UUID.fromString(arguments.get(1));
            while (i < library.getBookList().size()) {
                if (library.getBookList().get(i).getBookID().equals(uuid)) {
                    Book book = library.getBookList().get(i);
                    book.restoreBook();
                    return "Book restored";
                }
                i++;
            }
            return BOOK_WASNT_FOUND;
        } else {
            return INVALID_INPUT;
        }
    }

    private String viewBorrowedBooks(List<String> arguments, Library library) {
        if (check(arguments, 1, VIEW_BORROWED_BOOKS)) {
            int i = 0;
            boolean borrowed = false;

            while (i < library.getBookList().size()) {
                if (library.getBookList().get(i).getBorrowedBy() != null) {
                    borrowed = true;
                    break;
                }
                i++;
            }
            if (!borrowed) {
                return "No Books borrowed";
            }

            i = 0;
            out.println("BookID\t\t\t\t\tTitle\tAuthor\tGenre\tBorrowed by\tEmail");
            while (i < library.getBookList().size()) {
                if (library.getBookList().get(i).getBorrowedBy() != null && library.getBookList().get(i).getShelf() == null) {
                    out.print(library.getBookList().get(i).getBookID() + "\t");
                    out.print(library.getBookList().get(i).getBookTitle() + "\t");
                    out.print(library.getBookList().get(i).getBookAuthor() + "\t");
                    out.print(library.getBookList().get(i).getBookGenre() + "\t");
                    out.print(library.getBookList().get(i).getBorrowedBy().getVisitorName() + " " + library.getBookList().get(i).getBorrowedBy().getVisitorSurname() + "\t");
                    out.print(library.getBookList().get(i).getBorrowedBy().getVisitorEmailAddress() + "\n");
                }
                i++;
            }
            return "Borrowed books viewed";
        } else {
            return INVALID_INPUT;
        }
    }

    public boolean check(List<String> arguments, int limit, String option) {

        String options = "createVisitor, createLibrarian, deleteVisitor, deleteLibrarian, addBook, viewBooks, deleteBook, searchBook, borrowBook, returnBook, restoreBook, viewBorrowedBooks";
        if (arguments.size() != limit) {
            String result = switch (option) {
                case CREATE_VISITOR, CREATE_LIBRARIAN ->
                        "Usage: [option] [Name] [Surname] [Birthday] [Email]\n" + options;
                case DELETE_LIBRARIAN -> "Usage: [option] [Name] [Surname] [Birthday]\n" + options;
                case ADD_BOOK, DELETE_BOOK ->
                        "Usage: [option] [Title] [Author] [Genre] [BookCondition] [BookWidth]\n" + options;
                case SEARCH_BOOK -> "Usage: [option] [Title]\n" + options;
                case VIEW_OPEN_PAYMENTS, VIEW_OPEN_PAYMENTS_LIBRARIAN, DELETE_VISITOR ->
                        "Usage: [option] [Email]\n" + options;
                case RETURN_BOOK, RESTORE_BOOK -> "Usage: [option] [BookID]\n" + options;
                case VIEW_BOOKS, VIEW_BORROWED_BOOKS -> "Usage: [option]\n" + option;
                case BORROW_BOOK -> "Usage: [option] [BookID] [Email]\n" + options;
                default -> "Invalid option";
            };
            out.println(result);
            return false;
        }
        return true;

    }
}
