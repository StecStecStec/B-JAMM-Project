package hwr.oop.library;

import hwr.oop.library.cli.CLI;
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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;


import static org.assertj.core.api.Assertions.assertThat;

class CLIToolsTest {

    private final Library library = Library.createNewLibrary();
    private Persistence persistence;
    private final OutputStream outputStream = new ByteArrayOutputStream();
    private final CLI consoleUI = new CLI(outputStream);
    private static String path = null;

    @BeforeAll
    static void init() {
        path = pathToDirectory();
    }


    private static String pathToDirectory() {
        try {
            Path currentDirectory = Paths.get(System.getProperty("user.dir"));

            try (Stream<Path> stream = Files.walk(currentDirectory)) {
                Optional<Path> directory = stream
                        .filter(Files::isDirectory)
                        .filter(path -> path.getFileName().toString().equals("csvTestFiles"))
                        .findFirst();

                return directory.map(Path::toString).orElse(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
        List<String> args = new ArrayList<>();
        args.add("createVisitor");
        args.add("Hans");
        args.add("Meier");
        args.add("01.01.2000");
        args.add("hans@meier.com");

        List<String> args2 = new ArrayList<>();
        args2.add("createVisitor");
        args2.add("Hansi");
        args2.add("Meier");
        args2.add("01.01.2000");
        args2.add("hansi@meier.com");

        List<String> delete = new ArrayList<>();
        delete.add("deleteVisitor");
        delete.add("h@meier.com");

        handleCLI(args);
        assertOutputContains("Visitor created");

        handleCLI(args2);
        assertOutputContains("Visitor created");

        handleCLI(args);
        assertOutputContains("Mail already exists");

        handleCLI(delete);
        assertOutputContains("Visitor wasn't found");

        delete.set(1, "hans@meier.com");
        handleCLI(delete);
        assertOutputContains("Visitor deleted");
        Library library = persistence.loadLibrary();
        assertThat(library.getVisitorList()).hasSize(1);

        delete.set(1, "@");
        delete.add("abc");
        handleCLI(delete);
        assertOutputContains("Invalid Input");
        assertOutputContains("Usage: [option] [Email]\n");

        args.removeLast();
        handleCLI(args);
        int invalidInputCounter = outputStream.toString().split("Invalid Input", -1).length - 1;
        assertThat(invalidInputCounter).isEqualTo(2);
    }

    @Test
    void create_and_delete_LibrariansTest() {
        List<String> args = new ArrayList<>();
        args.add("createLibrarian");
        args.add("Hans");
        args.add("Meier");
        args.add("01.01.2000");

        List<String> args2 = new ArrayList<>();
        args2.add("createLibrarian");
        args2.add("Hansi");
        args2.add("Meier");
        args2.add("01.01.2000");

        handleCLI(args);
        assertOutputContains("Librarian created");
        handleCLI(args2);

        handleCLI(args);
        assertOutputContains("Librarian already exists");

        args.set(0, "deleteLibrarian");
        args.set(3, "05.01.2000");
        handleCLI(args);

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

        args2.set(0, "deleteLibrarian");
        args2.set(3, "01.01.2000");
        handleCLI(args2);
        assertOutputContains("Librarian deleted");
        assertThat(library.getLibrarianList()).isEmpty();

        args.removeLast();
        handleCLI(args);
        assertOutputContains("Invalid Input");

        args2.removeLast();
        args2.remove(2);
        handleCLI(args2);
        int invalidInputCounter = outputStream.toString().split("Invalid Input", -1).length - 1;
        assertThat(invalidInputCounter).isEqualTo(3);
    }

    @Test
    void add_delete_and_view_BookTest() {
        int i = 0;
        String uuid = null;

        List<String> args = new ArrayList<>();
        args.add("addBook");
        args.add("Planes");
        args.add("Alf");
        args.add("Action");
        args.add("100");
        args.add("20");

        List<String> args2 = new ArrayList<>();
        args2.add("viewBooks");
        Room room = Room.createNewRoom(library, 4);
        Shelf shelf = Shelf.createNewShelf(library, room, "Action", 400, 1);

        handleCLI(args);
        assertOutputContains("Book added");

        while (i < library.getBookList().size()) {
            if (Objects.equals(library.getBookList().get(i).getBookTitle(), "Planes") && Objects.equals(library.getBookList().get(i).getBookAuthor(), "Alf")) {
                uuid = library.getBookList().get(i).getBookID().toString();
                break;
            }
            i++;
        }

        args.set(3, "Roman");
        handleCLI(args);
        assertOutputContains("No Shelf found");

        handleCLI(args2);
        assertThat(outputStream.toString()).containsOnlyOnce("BookID\t\t\t\t\tTitle\tAuthor\tGenre");
        assertThat(outputStream.toString()).contains(uuid, "Planes", "Alf", "Action");
        assertOutputContains("Books viewed");
        args2.add("abc");
        handleCLI(args2);
        assertOutputContains("Invalid Input");

        List<String> invalidInput = new ArrayList<>();
        invalidInput.add("deleteBook");
        invalidInput.add("017a50d0-f7c5-4223-8ff3-4baa0d977ddf");
        invalidInput.add("Invalid Input");

        handleCLI(invalidInput);
        assertOutputContains("Invalid Input");

        if (uuid != null) {
            List<String> args3 = new ArrayList<>();
            args3.add("deleteBook");
            args3.add("017a50d0-f7c5-4223-8ff3-4baa0d977ddf");

            handleCLI(args3);
            assertOutputContains("No Book found");

            args3.set(1, uuid);
            handleCLI(args3);
            assertThat(outputStream.toString()).contains("Book deleted");
        }

        args.removeLast();
        handleCLI(args);
        int invalidInputCounter = outputStream.toString().split("Invalid Input", -1).length - 1;
        assertThat(invalidInputCounter).isEqualTo(3);

        library.deleteShelf(shelf);
        library.deleteRoom(room);
    }

    @Test
    void searchBookTest() {
        int i = 0;
        String uuid = null;

        List<String> args = new ArrayList<>();
        args.add("addBook");
        args.add("Place");
        args.add("Meier");
        args.add("Action");
        args.add("100");
        args.add("20");

        Room room = Room.createNewRoom(library, 3);
        Shelf shelf = Shelf.createNewShelf(library, room, "Action", 400, 1);

        handleCLI(args);
        assertThat(library.getBookList()).hasSize(1);

        List<String> invalidInput = new ArrayList<>();
        invalidInput.add("searchBook");
        invalidInput.add("Place");
        invalidInput.add("Invalid Input");
        handleCLI(invalidInput);
        assertOutputContains("Invalid Input");

        List<String> args2 = new ArrayList<>();
        args2.add("searchBook");
        args2.add("Place");

        while (i < library.getBookList().size()) {
            if (Objects.equals(library.getBookList().get(i).getBookTitle(), "Place") && Objects.equals(library.getBookList().get(i).getBookAuthor(), "Meier")) {
                uuid = library.getBookList().get(i).getBookID().toString();
                break;
            }
            i++;
        }

        handleCLI(args2);
        assertThat(outputStream.toString()).containsOnlyOnce("BookID\t\t\t\t\tTitle\tAuthor\tGenre");
        assertThat(outputStream.toString()).contains(uuid, "Place", "Meier", "Action", "Books searched");

        if (uuid != null) {
            args.clear();
            args.add("deleteBook");
            args.add(uuid);
            handleCLI(args);
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


        List<String> args = new ArrayList<>();
        args.add("addBook");
        args.add("Harry");
        args.add("Idi");
        args.add("Action");
        args.add("100");
        args.add("10");

        List<String> args1 = new ArrayList<>();
        args1.add("addBook");
        args1.add("Planes");
        args1.add("Meier");
        args1.add("Action");
        args1.add("100");
        args1.add("20");


        List<String> args2 = new ArrayList<>();
        args2.add("viewBooks");

        handleCLI(args);

        handleCLI(args1);
        while (i < library.getBookList().size()) {
            if (Objects.equals(library.getBookList().get(i).getBookTitle(), "Planes") && Objects.equals(library.getBookList().get(i).getBookAuthor(), "Meier")) {
                uuid = library.getBookList().get(i).getBookID().toString();
                break;
            }
            i++;
        }
        i = 0;
        while (i < library.getBookList().size()) {
            if (Objects.equals(library.getBookList().get(i).getBookTitle(), "Harry") && Objects.equals(library.getBookList().get(i).getBookAuthor(), "Idi")) {
                uuid2 = library.getBookList().get(i).getBookID().toString();
                break;
            }
            i++;
        }
        assert uuid != null;
        assert uuid2 != null;

        handleCLI(args2);

        List<String> args2_1 = new ArrayList<>();
        args2_1.add("createVisitor");
        args2_1.add("Lach");
        args2_1.add("Mustermann");
        args2_1.add("02.01.1998");
        args2_1.add("l.de");

        handleCLI(args2_1);

        List<String> args3 = new ArrayList<>();
        args3.add("createVisitor");
        args3.add("Max");
        args3.add("Mustermann");
        args3.add("01.01.1999");
        args3.add("email.de");

        handleCLI(args3);

        handleCLI(List.of("borrowBook", "7b40bee4-e2ca-4903-996a-ea974a8cb435", "email.de"));
        handleCLI(List.of("borrowBook", "7b40bee4-e2ca-4903-996a-ea974a8cb435", "email"));
        handleCLI(List.of("borrowBook", uuid, "email"));

        List<String> invalidInput = new ArrayList<>();
        invalidInput.add("borrowBook");
        invalidInput.add("7b40bee4-e2ca-4903-996a-ea974a8cb435");
        invalidInput.add("email.de");
        invalidInput.add("Invalid Input");

        handleCLI(invalidInput);
        assertOutputContains("Invalid Input");

        List<String> args5 = new ArrayList<>();
        args5.add("borrowBook");
        args5.add(uuid);
        args5.add("email.de");
        handleCLI(args5);
        assertOutputContains("Book borrowed");


        List<String> argsViewBorrowedBooks = new ArrayList<>();
        argsViewBorrowedBooks.add("viewBorrowedBooks");
        handleCLI(argsViewBorrowedBooks);
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

        int counter = outputStream.toString().split("Meier", -1).length - 1;
        assertThat(counter).isEqualTo(2);
        assertOutputContains("Borrowed books viewed");

        List<String> args6 = new ArrayList<>();
        args6.add("returnBook");
        args6.add("833b92f9-1922-4bd9-87ba-08cf33d0b112");
        handleCLI(args6);

        library.deleteShelf(shelf1);
        library.deleteShelf(shelf2);
        handleCLI(args6);
        args6.set(1, uuid);
        handleCLI(args6);

        library.addShelf(shelf1);
        library.addShelf(shelf2);

        int noBookFoundCounter = outputStream.toString().split("Book wasn't found", -1).length - 1;
        assertThat(noBookFoundCounter).isEqualTo(6);

        args6.set(1, uuid);
        handleCLI(args6);
        assertOutputContains("Book returned");
        assertThat(shelf2.getBooksOnShelf()).hasSize(2);

        List<String> InvalidInput2 = new ArrayList<>();
        InvalidInput2.add("returnBook");
        InvalidInput2.add(uuid);
        InvalidInput2.add("Invalid Input");
        handleCLI(InvalidInput2);
        int invalidInputCounter2 = outputStream.toString().split("Invalid Input", -1).length - 1;
        assertThat(invalidInputCounter2).isEqualTo(2);

        List<String> args7 = new ArrayList<>();
        args7.add("deleteVisitor");
        args7.add("email.de");
        handleCLI(args7);

        List<String> args8 = new ArrayList<>();
        args8.add("deleteBook");
        args8.add(uuid);
        handleCLI(args8);

        handleCLI(List.of("viewBorrowedBooks"));
        assertOutputContains("No Books borrowed");

        List<String> args9 = new ArrayList<>();
        args9.add("deleteBook");
        args9.add(uuid2);
        handleCLI(args9);

        library.deleteShelf(shelf1);
        library.deleteShelf(shelf2);
        library.deleteRoom(room);
    }

    @Test
    void ViewBorrowedBooksInvalidInputTest() {

        List<String> args = new ArrayList<>();
        args.add("viewBorrowedBooks");
        args.add("invalid_email");
        handleCLI(args);
        assertOutputContains("Invalid Input");
    }

    @Test
    void RestoreBookTest() {
        int i = 0;
        String uuid = null;

        List<String> args = new ArrayList<>();
        args.add("addBook");
        args.add("Planes");
        args.add("Meier");
        args.add("Action");
        args.add("50");
        args.add("20");

        Room room = Room.createNewRoom(library, 4);
        Shelf shelf = Shelf.createNewShelf(library, room, "Action", 400, 1);

        handleCLI(args);
        while (i < library.getBookList().size()) {
            if (Objects.equals(library.getBookList().get(i).getBookTitle(), "Planes") && Objects.equals(library.getBookList().get(i).getBookAuthor(), "Meier")) {
                uuid = library.getBookList().get(i).getBookID().toString();
                break;
            }
            i++;
        }

        List<String> args3 = new ArrayList<>();
        args3.add("restoreBook");
        args3.add("acb45dff-660b-4701-9852-b89873580ec1");
        handleCLI(args3);
        assertThat(outputStream.toString()).contains("Book wasn't found");

        List<String> args4 = new ArrayList<>();
        args4.add("restoreBook");
        args4.add(uuid);
        handleCLI(args4);
        assertThat(library.getBookList().getFirst().getBookCondition()).isEqualTo(100);
        assertThat(outputStream.toString()).contains("Book restored");

        List<String> args5 = new ArrayList<>();
        args5.add("restoreBook");
        args5.add(uuid);
        args5.add("email.de");
        handleCLI(args5);
        assertThat(outputStream.toString()).contains("Invalid Input");

        List<String> args6 = new ArrayList<>();
        args6.add("deleteBook");
        args6.add(uuid);
        handleCLI(args6);
        System.out.println(outputStream);

        library.deleteShelf(shelf);
        library.deleteRoom(room);
    }

    @AfterEach
    void tearDown() {
        persistence.saveLibrary(library);
    }
}
