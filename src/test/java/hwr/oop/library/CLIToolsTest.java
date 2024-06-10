package hwr.oop.library;

import hwr.oop.library.cli.CLI;
import hwr.oop.library.cli.MainLibrary;
import hwr.oop.library.domain.Library;
import hwr.oop.library.domain.Room;
import hwr.oop.library.domain.Shelf;
import hwr.oop.library.persistence.CSVAdapter;
import hwr.oop.library.persistence.Persistence;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;


import static org.assertj.core.api.Assertions.assertThat;

class CLIToolsTest {

    private final Library library = Library.createNewLibrary();
    private Persistence persistence;
    private final OutputStream outputStream = new ByteArrayOutputStream();
    private final CLI consoleUI = new CLI(outputStream);
    private static String path = null;

    @BeforeAll
    static void init() throws URISyntaxException {
        path = pathToDirectory();
    }

    private static String pathToDirectory() throws URISyntaxException {
        return Objects.requireNonNull(MainLibrary.class.getClassLoader().getResource("csvTestFiles")).toURI().getPath();

    }

    @BeforeEach
    void setUp() {
        persistence = new CSVAdapter(path + "/");
    }

    private void handleCLI(List<String> args) {
        consoleUI.handle(args, library, persistence);
    }

    private void assertOutputContains(String expected) {
        assertThat(outputStream.toString()).contains(expected);
    }

    @Test
    void create_and_delete_VisitorsTest() {
        handleCLI(List.of("createVisitor", "Hans", "Meier", "01.01.2000", "hans@meier.com"));
        assertOutputContains("Visitor created");

        handleCLI(List.of("createVisitor", "Hansi", "Meier", "01.01.2000", "hansi@meier.com"));
        assertOutputContains("Visitor created");

        handleCLI(List.of("createVisitor", "Hans", "Meier", "01.01.2000", "hans@meier.com"));
        assertOutputContains("Mail already exists");

        handleCLI(List.of("deleteVisitor", "h@meier.com"));
        assertOutputContains("Visitor wasn't found");

        handleCLI(List.of("deleteVisitor", "hans@meier.com"));
        assertOutputContains("Visitor deleted");
        Library library2 = persistence.loadLibrary();
        assertThat(library2.getVisitorList()).hasSize(1);

        handleCLI(List.of("deleteVisitor", "@", "abc"));
        assertOutputContains("Invalid Input");
        assertOutputContains("Usage: [option] [Email]\n");

        handleCLI(List.of("createVisitor", "Hans", "Meier", "01.01.2000"));
        int invalidInputCounter = outputStream.toString().split("Invalid Input", -1).length - 1;
        assertThat(invalidInputCounter).isEqualTo(2);
    }

    @Test
    void create_and_delete_LibrariansTest() {
        handleCLI(List.of("createLibrarian", "Hans", "Meier", "01.01.2000"));
        assertOutputContains("Librarian created");
        handleCLI(List.of("createLibrarian", "Hansi", "Meier", "01.01.2000"));

        handleCLI(List.of("createLibrarian", "Hans", "Meier", "01.01.2000"));
        assertOutputContains("Librarian already exists");

        handleCLI(List.of("deleteLibrarian", "Hans", "Meier", "05.01.2000"));

        handleCLI(List.of("createLibrarian"));
        assertOutputContains("Invalid Input");

        handleCLI(List.of("deleteLibrarian", "Hans", "M", "02.01.2000"));
        handleCLI(List.of("deleteLibrarian", "Hans", "M", "01.01.2000"));
        handleCLI(List.of("deleteLibrarian", "Hans", "Meier", "02.01.2000"));
        handleCLI(List.of("deleteLibrarian", "H", "Meier", "02.01.2000"));
        handleCLI(List.of("deleteLibrarian", "H", "Meier", "01.01.2000"));
        handleCLI(List.of("deleteLibrarian", "H", "M", "02.01.2000"));
        handleCLI(List.of("deleteLibrarian", "Hans", "Meier", "01.01.2000"));

        int noLibrarianFoundCounter = outputStream.toString().split("Librarian wasn't found", -1).length - 1;
        assertThat(noLibrarianFoundCounter).isEqualTo(7);

        handleCLI(List.of("deleteLibrarian", "Hansi", "Meier", "01.01.2000"));
        assertOutputContains("Librarian deleted");
        assertThat(library.getLibrarianList()).isEmpty();

        handleCLI(List.of("deleteLibrarian", "Hans", "Meier"));
        assertOutputContains("Invalid Input");

        handleCLI(List.of("createLibrarian", "Hansi"));
        int invalidInputCounter = outputStream.toString().split("Invalid Input", -1).length - 1;
        assertThat(invalidInputCounter).isEqualTo(3);
    }

    @Test
    void add_delete_and_view_BookTest() {
        int i = 0;
        String uuid = null;

        Room room = Room.createNewRoom(library, 4);
        Shelf shelf = Shelf.createNewShelf(library, room, "Action", 400, 1);

        handleCLI(List.of("addBook", "Planes", "Alf", "Action", "100", "20"));
        assertOutputContains("Book added");

        while (i < library.getBookList().size()) {
            if (Objects.equals(library.getBookList().get(i).getBookTitle(), "Planes") && Objects.equals(library.getBookList().get(i).getBookAuthor(), "Alf")) {
                uuid = library.getBookList().get(i).getBookID().toString();
                break;
            }
            i++;
        }

        handleCLI(List.of("addBook", "Planes", "Alf", "Roman", "100", "20"));
        assertOutputContains("No Shelf found");

        handleCLI(List.of("viewBooks"));
        assertThat(outputStream.toString()).containsOnlyOnce("BookID\t\t\t\t\tTitle\tAuthor\tGenre");
        assertThat(outputStream.toString()).contains(uuid, "Planes", "Alf", "Action");
        assertOutputContains("Books viewed");
        handleCLI(List.of("viewBooks", "abc"));
        assertOutputContains("Invalid Input");

        handleCLI(List.of("deleteBook", "017a50d0-f7c5-4223-8ff3-4baa0d977ddf", "Invalid Input"));
        assertOutputContains("Invalid Input");

        if (uuid != null) {
            handleCLI(List.of("deleteBook", "017a50d0-f7c5-4223-8ff3-4baa0d977ddf"));
            assertOutputContains("No Book found");

            handleCLI(List.of("deleteBook", uuid));
            assertThat(outputStream.toString()).contains("Book deleted");
        }

        handleCLI(List.of("addBook", "Planes", "Alf", "Action", "100"));
        int invalidInputCounter = outputStream.toString().split("Invalid Input", -1).length - 1;
        assertThat(invalidInputCounter).isEqualTo(3);

        library.deleteShelf(shelf);
        library.deleteRoom(room);
    }

    @Test
    void searchBookTest() {

        int i = 0;
        String uuid = null;

        Room room = Room.createNewRoom(library, 3);
        Shelf shelf = Shelf.createNewShelf(library, room, "Action", 400, 1);

        handleCLI(List.of("addBook", "Place", "Meier", "Action", "100", "20"));
        assertThat(library.getBookList()).hasSize(1);

        handleCLI(List.of("searchBook", "Place", "Invalid Input"));
        assertOutputContains("Invalid Input");

        while (i < library.getBookList().size()) {
            if (Objects.equals(library.getBookList().get(i).getBookTitle(), "Place") && Objects.equals(library.getBookList().get(i).getBookAuthor(), "Meier")) {
                uuid = library.getBookList().get(i).getBookID().toString();
                break;
            }
            i++;
        }

        handleCLI(List.of("searchBook", "Place"));
        assertThat(outputStream.toString()).containsOnlyOnce("BookID\t\t\t\t\tTitle\tAuthor\tGenre");
        assertThat(outputStream.toString()).contains(uuid, "Place", "Meier", "Action", "Books searched");

        if (uuid != null) {
            handleCLI(List.of("deleteBook", uuid));
            assertThat(library.getBookList()).isEmpty();
        }

        library.deleteShelf(shelf);
        library.deleteRoom(room);
    }

    @Test
    void BorrowAndReturnBookTest() {
        int i = 0;
        String uuid = null;
        String uuid2 = null;
        Room room = Room.createNewRoom(library, 4);
        Shelf shelf1 = Shelf.createNewShelf(library, room, "Fiction", 400, 1);
        Shelf shelf2 = Shelf.createNewShelf(library, room, "Action", 400, 1);

        handleCLI(List.of("addBook", "Harry", "Idi", "Action", "100", "10"));
        handleCLI(List.of("addBook", "Planes", "Meier", "Action", "100", "20"));

        while (i < library.getBookList().size()) {
            if (Objects.equals(library.getBookList().get(i).getBookTitle(), "Planes") &&
                    Objects.equals(library.getBookList().get(i).getBookAuthor(), "Meier")) {
                uuid = library.getBookList().get(i).getBookID().toString();
                break;
            }
            i++;
        }
        i = 0;
        while (i < library.getBookList().size()) {
            if (Objects.equals(library.getBookList().get(i).getBookTitle(), "Harry") &&
                    Objects.equals(library.getBookList().get(i).getBookAuthor(), "Idi")) {
                uuid2 = library.getBookList().get(i).getBookID().toString();
                break;
            }
            i++;
        }
        assert uuid != null;
        assert uuid2 != null;

        handleCLI(List.of("viewBooks"));

        handleCLI(List.of("createVisitor", "Lach", "Mustermann", "02.01.1998", "l.de"));
        handleCLI(List.of("createVisitor", "Max", "Mustermann", "01.01.1999", "email.de"));

        handleCLI(List.of("borrowBook", "7b40bee4-e2ca-4903-996a-ea974a8cb435", "email.de"));
        handleCLI(List.of("borrowBook", "7b40bee4-e2ca-4903-996a-ea974a8cb435", "email"));
        handleCLI(List.of("borrowBook", uuid, "email"));

        handleCLI(List.of("borrowBook", "7b40bee4-e2ca-4903-996a-ea974a8cb435", "email.de", "Invalid Input"));
        assertOutputContains("Invalid Input");

        handleCLI(List.of("borrowBook", uuid, "email.de"));
        assertOutputContains("Book borrowed");

        handleCLI(List.of("viewBorrowedBooks"));
        assertThat(outputStream.toString()).containsOnlyOnce("BookID\t\t\t\t\tTitle\tAuthor\tGenre\tBorrowed by\tEmail");

        assertThat(outputStream.toString()).contains("Max", "Mustermann", "email.de");
        int uuidCounter = outputStream.toString().split(uuid, -1).length - 1;
        assertThat(uuidCounter).isEqualTo(2);
        int titleCounter = outputStream.toString().split("Planes", -1).length - 1;
        assertThat(titleCounter).isEqualTo(2);
        int authorCounter = outputStream.toString().split("Meier", -1).length - 1;
        assertThat(authorCounter).isEqualTo(2);
        int genreCounter = outputStream.toString().split("Action", -1).length - 1;
        assertThat(genreCounter).isEqualTo(3);

        assertOutputContains("Borrowed books viewed");

        handleCLI(List.of("returnBook", "833b92f9-1922-4bd9-87ba-08cf33d0b112"));

        library.deleteShelf(shelf1);
        library.deleteShelf(shelf2);
        handleCLI(List.of("returnBook", "833b92f9-1922-4bd9-87ba-08cf33d0b112"));
        handleCLI(List.of("returnBook", uuid));

        library.addShelf(shelf1);
        library.addShelf(shelf2);

        int noBookFoundCounter = outputStream.toString().split("Book wasn't found", -1).length - 1;
        assertThat(noBookFoundCounter).isEqualTo(6);

        handleCLI(List.of("returnBook", uuid));
        assertOutputContains("Book returned");
        assertThat(shelf2.getBooksOnShelf()).hasSize(2);

        handleCLI(List.of("returnBook", uuid, "Invalid Input"));
        int invalidInputCounter2 = outputStream.toString().split("Invalid Input", -1).length - 1;
        assertThat(invalidInputCounter2).isEqualTo(2);

        handleCLI(List.of("deleteVisitor", "email.de"));
        handleCLI(List.of("deleteBook", uuid));

        handleCLI(List.of("viewBorrowedBooks"));
        assertOutputContains("No Books borrowed");

        handleCLI(List.of("deleteBook", uuid2));

        library.deleteShelf(shelf1);
        library.deleteShelf(shelf2);
        library.deleteRoom(room);
    }

    @Test
    void ViewBorrowedBooksInvalidInputTest() {
        handleCLI(List.of("viewBorrowedBooks", "invalid_email"));
        assertOutputContains("Invalid Input");
    }

    @Test
    void RestoreBookTest() {
        int i = 0;
        String uuid = null;

        Room room = Room.createNewRoom(library, 4);
        Shelf shelf = Shelf.createNewShelf(library, room, "Action", 400, 1);

        handleCLI(List.of("addBook", "Planes", "Meier", "Action", "50", "20"));
        while (i < library.getBookList().size()) {
            if (Objects.equals(library.getBookList().get(i).getBookTitle(), "Planes") && Objects.equals(library.getBookList().get(i).getBookAuthor(), "Meier")) {
                uuid = library.getBookList().get(i).getBookID().toString();
                break;
            }
            i++;
        }

        handleCLI(List.of("restoreBook", "acb45dff-660b-4701-9852-b89873580ec1"));
        assertThat(outputStream.toString()).contains("Book wasn't found");

        assert uuid != null;
        handleCLI(List.of("restoreBook", uuid));
        assertThat(library.getBookList().getFirst().getBookCondition()).isEqualTo(100);
        assertThat(outputStream.toString()).contains("Book restored");

        handleCLI(List.of("restoreBook", uuid, "email.de"));
        assertThat(outputStream.toString()).contains("Invalid Input");

        handleCLI(List.of("deleteBook", uuid));

        library.deleteShelf(shelf);
        library.deleteRoom(room);
    }

    @AfterEach
    void tearDown() {
        persistence.saveLibrary(library);
    }
}
