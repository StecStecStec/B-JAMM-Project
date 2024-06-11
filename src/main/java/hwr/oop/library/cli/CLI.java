package hwr.oop.library.cli;

import hwr.oop.library.domain.*;
import hwr.oop.library.persistence.Persistence;

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
    private static final String CREATE_SHELF = "createShelf";
    private static final String CREATE_ROOM = "createRoom";
    private static final String DELETE_VISITOR = "deleteVisitor";
    private static final String DELETE_LIBRARIAN = "deleteLibrarian";
    private static final String DELETE_SHELF = "deleteShelf";
    private static final String DELETE_ROOM = "deleteRoom";
    private static final String ADD_BOOK = "addBook";
    private static final String VIEW_BOOKS = "viewBooks";
    private static final String DELETE_BOOK = "deleteBook";
    private static final String SEARCH_BOOK = "searchBook";
    private static final String BORROW_BOOK = "borrowBook";
    private static final String RETURN_BOOK = "returnBook";
    private static final String RESTORE_BOOK = "restoreBook";
    private static final String VIEW_BORROWED_BOOKS = "viewBorrowedBooks";
    private static final String BOOK_WASNT_FOUND = "Book wasn't found";

    public CLI(OutputStream out) {
        this.out = new PrintStream(out);
    }

    public void handle(List<String> arguments, Library library, Persistence persistence) {
        String checkResult = check(arguments);
        if (checkResult != null) {
            out.println(checkResult);
            return;
        }

        String result = switch (arguments.getFirst()) {
            case CREATE_VISITOR -> createVisitor(arguments, library);
            case CREATE_LIBRARIAN -> createLibrarian(arguments, library);
            case CREATE_SHELF -> createShelf(arguments, library);
            case CREATE_ROOM -> createRoom(arguments, library);
            case DELETE_VISITOR -> deleteVisitor(arguments, library);
            case DELETE_LIBRARIAN -> deleteLibrarian(arguments, library);
            case DELETE_SHELF -> deleteShelf(arguments, library);
            case DELETE_ROOM -> deleteRoom(arguments, library);
            case ADD_BOOK -> addBook(arguments, library);
            case VIEW_BOOKS -> viewBooks(library);
            case DELETE_BOOK -> deleteBook(arguments, library);
            case SEARCH_BOOK -> searchBook(arguments, library);
            case BORROW_BOOK -> borrowBook(arguments, library);
            case RETURN_BOOK -> returnBook(arguments, library);
            case RESTORE_BOOK -> restoreBook(arguments, library);
            case VIEW_BORROWED_BOOKS -> viewBorrowedBooks(library);
            default -> throw new IllegalStateException("Unexpected value: " + arguments.getFirst());
        };
        persistence.saveLibrary(library);
        out.println(result);
    }

    private String createVisitor(List<String> arguments, Library library) {
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
    }

    private String createLibrarian(List<String> arguments, Library library) {
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
    }

    private String createShelf(List<String> arguments, Library library) {
        int i = 0;
        String genre = arguments.get(1);
        int shelfWidth = Integer.parseInt(arguments.get(2));
        int boardNumber = Integer.parseInt(arguments.get(3));


        while (i < library.getRoomList().size()) {
            Room room = library.getRoomList().get(i);
            if (room.getShelfLimit() > room.getShelfList().size()) {
                Shelf shelf1 = Shelf.createNewShelf(library, room, genre, shelfWidth, boardNumber);
                room.roomAddShelf(shelf1);
                return "Shelf created";
            }
            i++;
        }
        return "Error: No room available to create shelf.";
    }

    private String createRoom(List<String> arguments, Library library) {
        int shelfLimit = Integer.parseInt(arguments.get(1));

        Room.createNewRoom(library, shelfLimit);
        return "Room created";
    }


    private String deleteVisitor(List<String> arguments, Library library) {
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
    }

    private String deleteLibrarian(List<String> arguments, Library library) {
        int i = 0;
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
    }

    private String deleteShelf(List<String> arguments, Library library) {
        UUID shelfId = UUID.fromString(arguments.get(1));
        int i = 0;
        while (i < library.getShelfList().size()) {
            Shelf shelf = library.getShelfList().get(i);
            if (shelfId.equals(shelf.getShelfID())) {
                library.deleteShelf(shelf);
                return "Shelf deleted";
            }
            i++;
        }
        return "Shelf wasn't found";
    }

    private String deleteRoom(List<String> arguments, Library library) {
        UUID roomId = UUID.fromString(arguments.get(1));
        int i = 0;
        while (i < library.getRoomList().size()) {
            Room room = library.getRoomList().get(i);
            if (roomId.equals(room.getRoomID())) {
                library.deleteRoom(room);
                return "Room deleted";
            }
            i++;
        }
        return "Room wasn't found";
    }

    private String addBook(List<String> arguments, Library library) {
        int i = 0;
        String title = arguments.get(1);
        String author = arguments.get(2);
        String genre = arguments.get(3);
        int bookCondition = Integer.parseInt(arguments.get(4));
        int bookWidth = Integer.parseInt(arguments.get(5));
        while (i < library.getShelfList().size()) {
            if (Objects.equals(library.getShelfList().get(i).getGenre(), genre)) {
                Shelf shelf = library.getShelfList().get(i);
                new Book.Builder()
                        .library(library)
                        .bookID(UUID.randomUUID())
                        .title(title)
                        .author(author)
                        .genre(genre)
                        .shelf(shelf)
                        .bookCondition(bookCondition)
                        .bookWidth(bookWidth)
                        .build();
                return "Book added";
            }
            i++;
        }
        return "No Shelf found";
    }

    private String viewBooks(Library library) {
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
    }

    private String deleteBook(List<String> arguments, Library library) {
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
    }

    private String searchBook(List<String> arguments, Library library) {
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

    private String borrowBook(List<String> arguments, Library library) {
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
    }

    private String returnBook(List<String> arguments, Library library) {
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
    }

    private String restoreBook(List<String> arguments, Library library) {
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
    }

    private String viewBorrowedBooks(Library library) {
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
    }

    public String check(List<String> arguments) {

        String options = "createVisitor, createLibrarian, createShelf, deleteVisitor, deleteLibrarian, deleteShelf, addBook, viewBooks, deleteBook, searchBook, borrowBook, returnBook, restoreBook, viewBorrowedBooks";
        return switch (arguments.getFirst()) {
            case CREATE_VISITOR ->
                    (arguments.size() != 6) ? "Usage: [option] [Name] [Surname] [Birthday] [Email] [Folder]\n" : null;
            case CREATE_SHELF ->
                    (arguments.size() != 5) ? "Usage: [option] [genre] [shelfWidth] [boardNumber] [Folder]\n" + options : null;
            case CREATE_LIBRARIAN, DELETE_LIBRARIAN ->
                    (arguments.size() != 5) ? "Usage: [option] [Name] [Surname] [Birthday] [Folder]\n" + options : null;
            case CREATE_ROOM -> (arguments.size() != 3) ? "Usage: [option] [shelfLimit] [Folder]\n" + options : null;
            case DELETE_SHELF -> (arguments.size() != 3) ? "Usage: [option] [shelfID] [Folder]\n" + options : null;
            case DELETE_ROOM -> (arguments.size() != 3) ? "Usage: [option] [roomID] [Folder]\n" + options : null;
            case ADD_BOOK ->
                    (arguments.size() != 7) ? "Usage: [option] [Title] [Author] [Genre] [BookCondition] [BookWidth] [Folder]\n" + options : null;
            case DELETE_BOOK, RETURN_BOOK, RESTORE_BOOK ->
                    (arguments.size() != 3) ? "Usage: [option] [BookID] [Folder]\n" + options : null;
            case SEARCH_BOOK -> (arguments.size() != 3) ? "Usage: [option] [Title] [Folder]\n" + options : null;
            case DELETE_VISITOR -> (arguments.size() != 3) ? "Usage: [option] [Email] [Folder]\n" + options : null;
            case VIEW_BOOKS, VIEW_BORROWED_BOOKS ->
                    (arguments.size() != 2) ? "Usage: [option] [Folder]\n" + options : null;
            case BORROW_BOOK ->
                    (arguments.size() != 4) ? "Usage: [option] [BookID] [Email] [Folder]\n" + options : null;
            case "init" -> (arguments.size() != 2) ? "Usage: [option] [Folder]\n" + options : null;
            default -> "Invalid option";
        };
    }
}
