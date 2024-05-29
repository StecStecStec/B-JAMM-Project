package hwr.oop.library.cli;

<<<<<<< HEAD
import hwr.oop.library.domain.Book;
import hwr.oop.library.domain.Librarian;
import hwr.oop.library.domain.Shelf;
import hwr.oop.library.domain.Visitor;
import hwr.oop.library.persistence.CSVAdapter;
=======
import hwr.oop.library.domain.*;
import hwr.oop.library.persistence.CSVAdapter;
import hwr.oop.library.persistence.Persistence;
>>>>>>> 0e4c06e (refactored persistence by adding library class)

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@SuppressWarnings("java:S106")
public class CLI {
    private PrintStream out = System.out;
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

    public void handle(List<String> arguments, CSVAdapter csvAdapter) {

        String result = switch (arguments.getFirst()) {
            case CREATE_VISITOR -> createVisitor(arguments, csvAdapter);
            case CREATE_LIBRARIAN -> createLibrarian(arguments, csvAdapter);
            case DELETE_VISITOR -> deleteVisitor(arguments, csvAdapter);
            case DELETE_LIBRARIAN -> deleteLibrarian(arguments, csvAdapter);
            case ADD_BOOK -> addBook(arguments, csvAdapter);
            case VIEW_BOOKS -> viewBooks(arguments, csvAdapter);
            case DELETE_BOOK -> deleteBook(arguments, csvAdapter);
            case SEARCH_BOOK -> searchBook(arguments, csvAdapter);
            case BORROW_BOOK -> borrowBook(arguments, csvAdapter);
            case RETURN_BOOK -> returnBook(arguments, csvAdapter);
            case RESTORE_BOOK -> restoreBook(arguments, csvAdapter);
            case VIEW_BORROWED_BOOKS -> viewBorrowedBooks(arguments, csvAdapter);
            default -> throw new IllegalStateException("Unexpected value: " + arguments.get(0));
        };

        out.println(result);

    }

<<<<<<< HEAD
    private String createVisitor(List<String> arguments, CSVAdapter csvAdapter) {
        if (check(arguments, 5, CREATE_VISITOR)) {
            csvAdapter.loadCSV();
=======
    private String createVisitor(List<String> arguments, Persistence persistence) {
        if (check(arguments, 5, CREATE_VISITOR)) {
            Library library = persistence.loadLibrary();
>>>>>>> 0e4c06e (refactored persistence by adding library class)
            int i = 0;
            String name = arguments.get(1);
            String surname = arguments.get(2);
            String birthday = arguments.get(3);
            String email = arguments.get(4);

<<<<<<< HEAD
            while (i < csvAdapter.getVisitorList().size()) {
                if (Objects.equals(csvAdapter.getVisitorList().get(i).getVisitorEmailAddress(), email)) {
                    csvAdapter.saveCSV();
=======
            while (i < library.getVisitorList().size()) {
                if (Objects.equals(library.getVisitorList().get(i).getVisitorEmailAddress(), email)) {
                    persistence.saveLibrary(library);
>>>>>>> 0e4c06e (refactored persistence by adding library class)
                    return "Mail already exists";
                }
                i++;
            }

<<<<<<< HEAD
            Visitor.createNewVisitor(csvAdapter, name, surname, birthday, email);
            csvAdapter.saveCSV();
=======
            Visitor.createNewVisitor(library, name, surname, birthday, email);
            persistence.saveLibrary(library);
>>>>>>> 0e4c06e (refactored persistence by adding library class)
            return "Visitor created";
        } else {
            return INVALID_INPUT;
        }
    }

<<<<<<< HEAD
    private String createLibrarian(List<String> arguments, CSVAdapter csvAdapter) {
        if (check(arguments, 4, CREATE_LIBRARIAN)) {
            int i = 0;
            csvAdapter.loadCSV();
=======
    private String createLibrarian(List<String> arguments, Persistence persistence) {
        if (check(arguments, 4, CREATE_LIBRARIAN)) {
            int i = 0;
            Library library = persistence.loadLibrary();
>>>>>>> 0e4c06e (refactored persistence by adding library class)
            String name = arguments.get(1);
            String surname = arguments.get(2);
            String birthday = arguments.get(3);

<<<<<<< HEAD
            while (i < csvAdapter.getLibrarianList().size()) {
                if (Objects.equals(csvAdapter.getLibrarianList().get(i).getLibrarianName(), name) && Objects.equals(csvAdapter.getLibrarianList().get(i).getLibrarianSurname(), surname)) {
                    csvAdapter.saveCSV();
=======
            while (i < library.getLibrarianList().size()) {
                if (Objects.equals(library.getLibrarianList().get(i).getLibrarianName(), name) && Objects.equals(library.getLibrarianList().get(i).getLibrarianSurname(), surname)) {
                    persistence.saveLibrary(library);
>>>>>>> 0e4c06e (refactored persistence by adding library class)
                    return "Librarian already exists";
                }
                i++;
            }

<<<<<<< HEAD
            Librarian.createNewLibrarian(csvAdapter, name, surname, birthday);
            csvAdapter.saveCSV();
=======
            Librarian.createNewLibrarian(library, name, surname, birthday);
            persistence.saveLibrary(library);
>>>>>>> 0e4c06e (refactored persistence by adding library class)
            return "Librarian created";
        } else {
            return INVALID_INPUT;
        }
    }

<<<<<<< HEAD
    private String deleteVisitor(List<String> arguments, CSVAdapter csvAdapter) {
        if (check(arguments, 2, DELETE_VISITOR)) {
            csvAdapter.loadCSV();
            String mail = arguments.get(1);
            int i = 0;
            while (i < csvAdapter.getVisitorList().size()) {
                if (Objects.equals(csvAdapter.getVisitorList().get(i).getVisitorEmailAddress(), mail)) {
                    csvAdapter.deleteVisitor(csvAdapter.getVisitorList().get(i));
                    csvAdapter.saveCSV();
=======
    private String deleteVisitor(List<String> arguments, Persistence persistence) {
        if (check(arguments, 2, DELETE_VISITOR)) {
            Library library = persistence.loadLibrary();
            String mail = arguments.get(1);
            int i = 0;
            while (i < library.getVisitorList().size()) {
                if (Objects.equals(library.getVisitorList().get(i).getVisitorEmailAddress(), mail)) {
                    library.deleteVisitor(library.getVisitorList().get(i));
                    persistence.saveLibrary(library);
>>>>>>> 0e4c06e (refactored persistence by adding library class)
                    return "Visitor deleted";
                }
                i++;
            }
<<<<<<< HEAD
            csvAdapter.saveCSV();
=======
            persistence.saveLibrary(library);
>>>>>>> 0e4c06e (refactored persistence by adding library class)
            return "Visitor wasn't found";
        } else {
            return INVALID_INPUT;
        }
    }

<<<<<<< HEAD
    private String deleteLibrarian(List<String> arguments, CSVAdapter csvAdapter) {
        int i = 0;
        if (check(arguments, 4, DELETE_LIBRARIAN)) {
            csvAdapter.loadCSV();
            String name = arguments.get(1);
            String surname = arguments.get(2);
            String birthday = arguments.get(3);
            while (i < csvAdapter.getLibrarianList().size()) {
                if (Objects.equals(csvAdapter.getLibrarianList().get(i).getLibrarianName(), name) && Objects.equals(csvAdapter.getLibrarianList().get(i).getLibrarianSurname(), surname) && Objects.equals(csvAdapter.getLibrarianList().get(i).getLibrarianBirthday(), birthday)) {
                    csvAdapter.deleteLibrarian(csvAdapter.getLibrarianList().get(i));
                    csvAdapter.saveCSV();
=======
    private String deleteLibrarian(List<String> arguments, Persistence persistence) {
        int i = 0;
        if (check(arguments, 4, DELETE_LIBRARIAN)) {
            Library library = persistence.loadLibrary();
            String name = arguments.get(1);
            String surname = arguments.get(2);
            String birthday = arguments.get(3);
            while (i < library.getLibrarianList().size()) {
                if (Objects.equals(library.getLibrarianList().get(i).getLibrarianName(), name) && Objects.equals(library.getLibrarianList().get(i).getLibrarianSurname(), surname) && Objects.equals(library.getLibrarianList().get(i).getLibrarianBirthday(), birthday)) {
                    library.deleteLibrarian(library.getLibrarianList().get(i));
                    persistence.saveLibrary(library);
>>>>>>> 0e4c06e (refactored persistence by adding library class)
                    return "Librarian deleted";
                }
                i++;
            }
<<<<<<< HEAD
            csvAdapter.saveCSV();
=======
            persistence.saveLibrary(library);
>>>>>>> 0e4c06e (refactored persistence by adding library class)
            return "Librarian wasn't found";
        } else {
            return INVALID_INPUT;
        }
    }

<<<<<<< HEAD
    private String addBook(List<String> arguments, CSVAdapter csvAdapter) {
        if (check(arguments, 6, ADD_BOOK)) {
            csvAdapter.loadCSV();
=======
    private String addBook(List<String> arguments, Persistence persistence) {
        if (check(arguments, 6, ADD_BOOK)) {
            Library library = persistence.loadLibrary();
>>>>>>> 0e4c06e (refactored persistence by adding library class)
            int i = 0;
            String title = arguments.get(1);
            String author = arguments.get(2);
            String genre = arguments.get(3);
            int bookCondition = Integer.parseInt(arguments.get(4));
            int bookWidth = Integer.parseInt(arguments.get(5));

<<<<<<< HEAD
            while (i < csvAdapter.getShelfList().size()) {
                if (Objects.equals(csvAdapter.getShelfList().get(i).getGenre(), genre)) {
                    Shelf shelf = csvAdapter.getShelfList().get(i);
                    Book.createNewBook(csvAdapter, title, author, genre, shelf, bookCondition, bookWidth);
                    csvAdapter.saveCSV();
=======
            while (i < library.getShelfList().size()) {
                if (Objects.equals(library.getShelfList().get(i).getGenre(), genre)) {
                    Shelf shelf = library.getShelfList().get(i);
                    Book.createNewBook(library, title, author, genre, shelf, bookCondition, bookWidth);
                    persistence.saveLibrary(library);
>>>>>>> 0e4c06e (refactored persistence by adding library class)
                    return "Book added";
                }
                i++;
            }
<<<<<<< HEAD
            csvAdapter.saveCSV();
=======
            persistence.saveLibrary(library);
>>>>>>> 0e4c06e (refactored persistence by adding library class)
            return "No Shelf found";
        } else {
            return INVALID_INPUT;
        }
    }

<<<<<<< HEAD
    private String viewBooks(List<String> arguments, CSVAdapter csvAdapter) {
        if (check(arguments, 1, VIEW_BOOKS)) {
            csvAdapter.loadCSV();
            int i = 0;

            out.println("BookID\t\t\t\t\tTitle\tAuthor\tGenre");
            while (i < csvAdapter.getBookList().size()) {
                out.print(csvAdapter.getBookList().get(i).getBookID() + "\t");
                out.print(csvAdapter.getBookList().get(i).getBookTitle() + "\t");
                out.print(csvAdapter.getBookList().get(i).getBookAuthor() + "\t");
                out.print(csvAdapter.getBookList().get(i).getBookGenre() + "\n");
                i++;
            }
            csvAdapter.saveCSV();
=======
    private String viewBooks(List<String> arguments, Persistence persistence) {
        if (check(arguments, 1, VIEW_BOOKS)) {
            Library library = persistence.loadLibrary();
            int i = 0;

            out.println("BookID\t\t\t\t\tTitle\tAuthor\tGenre");
            while (i < library.getBookList().size()) {
                out.print(library.getBookList().get(i).getBookID() + "\t");
                out.print(library.getBookList().get(i).getBookTitle() + "\t");
                out.print(library.getBookList().get(i).getBookAuthor() + "\t");
                out.print(library.getBookList().get(i).getBookGenre() + "\n");
                i++;
            }
            persistence.saveLibrary(library);
>>>>>>> 0e4c06e (refactored persistence by adding library class)
            return "Books viewed";
        } else {
            return INVALID_INPUT;
        }
    }

<<<<<<< HEAD
    private String deleteBook(List<String> arguments, CSVAdapter csvAdapter) {
        if (check(arguments, 2, DELETE_BOOK)) {
            csvAdapter.loadCSV();
            int i = 0;
            UUID uuid = UUID.fromString(arguments.get(1));

            while (i < csvAdapter.getBookList().size()) {
                if (Objects.equals(csvAdapter.getBookList().get(i).getBookID(), uuid)) {
                    Book book = csvAdapter.getBookList().get(i);
                    csvAdapter.deleteBook(book);
                    csvAdapter.saveCSV();
=======
    private String deleteBook(List<String> arguments, Persistence persistence) {
        if (check(arguments, 2, DELETE_BOOK)) {
            Library library = persistence.loadLibrary();
            int i = 0;
            UUID uuid = UUID.fromString(arguments.get(1));

            while (i < library.getBookList().size()) {
                if (Objects.equals(library.getBookList().get(i).getBookID(), uuid)) {
                    Book book = library.getBookList().get(i);
                    library.deleteBook(book);
                    persistence.saveLibrary(library);
>>>>>>> 0e4c06e (refactored persistence by adding library class)
                    return "Book deleted";
                }
                i++;
            }
<<<<<<< HEAD
            csvAdapter.saveCSV();
=======
            persistence.saveLibrary(library);
>>>>>>> 0e4c06e (refactored persistence by adding library class)
            return "No Book found";
        } else {
            return INVALID_INPUT;
        }
    }

<<<<<<< HEAD
    private String searchBook(List<String> arguments, CSVAdapter csvAdapter) {
        if (check(arguments, 2, SEARCH_BOOK)) {
            csvAdapter.loadCSV();
            int i = 0;

            out.println("BookID\t\t\t\t\tTitle\tAuthor\tGenre");
            while (i < csvAdapter.getBookList().size()) {
                if (Objects.equals(csvAdapter.getBookList().get(i).getBookTitle(), arguments.get(1))) {
                    out.print(csvAdapter.getBookList().get(i).getBookID() + "\t");
                    out.print(csvAdapter.getBookList().get(i).getBookTitle() + "\t");
                    out.print(csvAdapter.getBookList().get(i).getBookAuthor() + "\t");
                    out.print(csvAdapter.getBookList().get(i).getBookGenre() + "\n");
                }
                i++;
            }
            csvAdapter.saveCSV();
=======
    private String searchBook(List<String> arguments, Persistence persistence) {
        if (check(arguments, 2, SEARCH_BOOK)) {
            Library library = persistence.loadLibrary();
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
            persistence.saveLibrary(library);
>>>>>>> 0e4c06e (refactored persistence by adding library class)
            return "Books searched";
        }
        return INVALID_INPUT;
    }

<<<<<<< HEAD
    private String borrowBook(List<String> arguments, CSVAdapter csvAdapter) {
        if (check(arguments, 3, BORROW_BOOK)) {
            csvAdapter.loadCSV();
=======
    private String borrowBook(List<String> arguments, Persistence persistence) {
        if (check(arguments, 3, BORROW_BOOK)) {
            Library library = persistence.loadLibrary();
>>>>>>> 0e4c06e (refactored persistence by adding library class)
            UUID uuid = UUID.fromString(arguments.get(1));
            String email = arguments.get(2);
            int i = 0;
            int j = 0;
<<<<<<< HEAD
            while (i < csvAdapter.getBookList().size()) {
                while (j < csvAdapter.getVisitorList().size() && Objects.equals(csvAdapter.getBookList().get(i).getBookID(), uuid)) {
                    if (Objects.equals(csvAdapter.getVisitorList().get(j).getVisitorEmailAddress(), email)) {
                        csvAdapter.getBookList().get(i).borrow(csvAdapter.getVisitorList().get(j));
                        csvAdapter.saveCSV();
=======
            while (i < library.getBookList().size()) {
                while (j < library.getVisitorList().size() && Objects.equals(library.getBookList().get(i).getBookID(), uuid)) {
                    if (Objects.equals(library.getVisitorList().get(j).getVisitorEmailAddress(), email)) {
                        library.getBookList().get(i).borrow(library.getVisitorList().get(j));
                        persistence.saveLibrary(library);
>>>>>>> 0e4c06e (refactored persistence by adding library class)
                        return "Book borrowed";
                    }
                    j++;
                }
                i++;
            }
<<<<<<< HEAD
            csvAdapter.saveCSV();
=======
            persistence.saveLibrary(library);
>>>>>>> 0e4c06e (refactored persistence by adding library class)
            return BOOK_WASNT_FOUND;
        } else {
            return INVALID_INPUT;
        }
    }

<<<<<<< HEAD
    private String returnBook(List<String> arguments, CSVAdapter csvAdapter) {
        if (check(arguments, 2, RETURN_BOOK)) {
            csvAdapter.loadCSV();
=======
    private String returnBook(List<String> arguments, Persistence persistence) {
        if (check(arguments, 2, RETURN_BOOK)) {
            Library library = persistence.loadLibrary();
>>>>>>> 0e4c06e (refactored persistence by adding library class)
            int i = 0;
            int j = 0;

            UUID uuid = UUID.fromString(arguments.get(1));
<<<<<<< HEAD
            while (i < csvAdapter.getBookList().size()) {
                while (j < csvAdapter.getShelfList().size() && csvAdapter.getBookList().get(i).getBookID().equals(uuid)) {
                    if (Objects.equals(csvAdapter.getShelfList().get(j).getGenre(), csvAdapter.getBookList().get(i).getBookGenre())) {
                        Shelf shelf = csvAdapter.getShelfList().get(j);
                        csvAdapter.getBookList().get(i).returnBook(shelf);
                        csvAdapter.saveCSV();
=======
            while (i < library.getBookList().size()) {
                while (j < library.getShelfList().size() && library.getBookList().get(i).getBookID().equals(uuid)) {
                    if (Objects.equals(library.getShelfList().get(j).getGenre(), library.getBookList().get(i).getBookGenre())) {
                        Shelf shelf = library.getShelfList().get(j);
                        library.getBookList().get(i).returnBook(shelf);
                        persistence.saveLibrary(library);
>>>>>>> 0e4c06e (refactored persistence by adding library class)
                        return "Book returned";
                    }
                    j++;
                }
                i++;
            }
<<<<<<< HEAD
            csvAdapter.saveCSV();
=======
            persistence.saveLibrary(library);
>>>>>>> 0e4c06e (refactored persistence by adding library class)
            return BOOK_WASNT_FOUND;
        } else {
            return INVALID_INPUT;
        }
    }

<<<<<<< HEAD
    private String restoreBook(List<String> arguments, CSVAdapter csvAdapter) {
        if (check(arguments, 2, RESTORE_BOOK)) {
            csvAdapter.loadCSV();
            int i = 0;

            UUID uuid = UUID.fromString(arguments.get(1));
            while (i < csvAdapter.getBookList().size()) {
                if (csvAdapter.getBookList().get(i).getBookID().equals(uuid)) {
                    Book book = csvAdapter.getBookList().get(i);
                    book.restoreBook();
                    csvAdapter.saveCSV();
=======
    private String restoreBook(List<String> arguments, Persistence persistence) {
        if (check(arguments, 2, RESTORE_BOOK)) {
             Library library = persistence.loadLibrary();
            int i = 0;

            UUID uuid = UUID.fromString(arguments.get(1));
            while (i < library.getBookList().size()) {
                if (library.getBookList().get(i).getBookID().equals(uuid)) {
                    Book book = library.getBookList().get(i);
                    book.restoreBook();
                    persistence.saveLibrary(library);
>>>>>>> 0e4c06e (refactored persistence by adding library class)
                    return "Book restored";
                }
                i++;
            }

            return BOOK_WASNT_FOUND;
        } else {
            return INVALID_INPUT;
        }
    }

<<<<<<< HEAD
    private String viewBorrowedBooks(List<String> arguments, CSVAdapter csvAdapter) {
        if (check(arguments, 1, VIEW_BORROWED_BOOKS)) {
            csvAdapter.loadCSV();
            int i = 0;
            boolean borrowed = false;

            while (i < csvAdapter.getBookList().size()) {
                if (csvAdapter.getBookList().get(i).getBorrowedBy() != null) {
=======
    private String viewBorrowedBooks(List<String> arguments, Persistence persistence) {
        if (check(arguments, 1, VIEW_BORROWED_BOOKS)) {
            Library library = persistence.loadLibrary();
            int i = 0;
            boolean borrowed = false;

            while (i < library.getBookList().size()) {
                if (library.getBookList().get(i).getBorrowedBy() != null) {
>>>>>>> 0e4c06e (refactored persistence by adding library class)
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
<<<<<<< HEAD
            while (i < csvAdapter.getBookList().size()) {
                if (csvAdapter.getBookList().get(i).getBorrowedBy() != null && csvAdapter.getBookList().get(i).getShelf() == null) {
                    out.print(csvAdapter.getBookList().get(i).getBookID() + "\t");
                    out.print(csvAdapter.getBookList().get(i).getBookTitle() + "\t");
                    out.print(csvAdapter.getBookList().get(i).getBookAuthor() + "\t");
                    out.print(csvAdapter.getBookList().get(i).getBookGenre() + "\t");
                    out.print(csvAdapter.getBookList().get(i).getBorrowedBy().getVisitorName() + " " + csvAdapter.getBookList().get(i).getBorrowedBy().getVisitorSurname() + "\t");
                    out.print(csvAdapter.getBookList().get(i).getBorrowedBy().getVisitorEmailAddress() + "\n");
=======
            while (i < library.getBookList().size()) {
                if (library.getBookList().get(i).getBorrowedBy() != null && library.getBookList().get(i).getShelf() == null) {
                    out.print(library.getBookList().get(i).getBookID() + "\t");
                    out.print(library.getBookList().get(i).getBookTitle() + "\t");
                    out.print(library.getBookList().get(i).getBookAuthor() + "\t");
                    out.print(library.getBookList().get(i).getBookGenre() + "\t");
                    out.print(library.getBookList().get(i).getBorrowedBy().getVisitorName() + " " + library.getBookList().get(i).getBorrowedBy().getVisitorSurname() + "\t");
                    out.print(library.getBookList().get(i).getBorrowedBy().getVisitorEmailAddress() + "\n");
>>>>>>> 0e4c06e (refactored persistence by adding library class)
                }
                i++;
            }

<<<<<<< HEAD
            csvAdapter.saveCSV();
=======
            persistence.saveLibrary(library);
>>>>>>> 0e4c06e (refactored persistence by adding library class)
            return "Borrowed books viewed";
        } else {
            return INVALID_INPUT;
        }
    }

    public boolean check(List<String> arguments, int limit, String option) {

        String options = "createVisitor, createLibrarian, deleteVisitor, deleteLibrarian, addBook, viewBooks, deleteBook, searchBook, borrowBook, returnBook, restoreBook, viewBorrowedBooks, viewOpenPayments,"/*Visitor*/ + " viewOpenPaymentsLibrarian" /*Librarian*/;
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
