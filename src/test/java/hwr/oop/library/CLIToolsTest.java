package hwr.oop.library;

import hwr.oop.library.cli.CLI;
import hwr.oop.library.domain.Library;
import hwr.oop.library.domain.Room;
import hwr.oop.library.domain.Shelf;
import hwr.oop.library.persistence.CSVAdapter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


import static org.assertj.core.api.Assertions.assertThat;

class CLIToolsTest {

    private final Library library = Library.createNewLibrary();
    private CSVAdapter csvAdapter;
    private final OutputStream outputStream = new ByteArrayOutputStream();
    private final CLI consoleUI = new CLI(outputStream);

    @BeforeEach
    void setUp() {
        csvAdapter = new CSVAdapter(".\\src\\test\\resources\\csvTestFiles\\");
    }

    @AfterEach
    void tearDown() {
        csvAdapter.saveLibrary(library);
    }

    private void handleCLI(List<String> args) {
        consoleUI.handle(args, library, csvAdapter);
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
        assertThat(library.getVisitorList()).hasSize(1);

        delete.set(1, "@");
        delete.add("abc");
        handleCLI(delete);
        assertOutputContains("Invalid Input");

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
        assertOutputContains("Librarian wasn't found");

        handleCLI(List.of("createLibrarian"));
        assertOutputContains("Invalid Input");

        args.set(0, "deleteLibrarian");
        args.set(3, "01.01.2000");
        handleCLI(args);

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

        List<String> invalidInput = new ArrayList<>();
        invalidInput.add("deleteBook");
        invalidInput.add("017a50d0-f7c5-4223-8ff3-4baa0d977ddf");
        invalidInput.add("invalid Input");

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
        assertThat(invalidInputCounter).isEqualTo(2);

        library.deleteShelf(shelf);
        library.deleteRoom(room);
    }

    @Test
    void searchBookTest() {
        int i = 0;
        String uuid = null;

        List<String> args = new ArrayList<>();
        args.add("addBook");
        args.add("Plas");
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
        invalidInput.add("Plas");
        invalidInput.add("Invalid Input");
        handleCLI(invalidInput);
        assertOutputContains("Invalid Input");

        List<String> args2 = new ArrayList<>();
        args2.add("searchBook");
        args2.add("Plas");

        while (i < library.getBookList().size()) {
            if (Objects.equals(library.getBookList().get(i).getBookTitle(), "Plas") && Objects.equals(library.getBookList().get(i).getBookAuthor(), "Meier")) {
                uuid = library.getBookList().get(i).getBookID().toString();
                break;
            }
            i++;
        }

        handleCLI(args2);
        assertThat(outputStream.toString()).containsOnlyOnce("BookID\t\t\t\t\tTitle\tAuthor\tGenre");
        assertThat(outputStream.toString()).contains(uuid, "Plas", "Meier", "Action", "Books searched");

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
    void BorrowAndReturnBookTest(){
        int i = 0;
        String uuid = null;
        String uuid2 = null;
        Room room = Room.createNewRoom(library, 4);
        Shelf shelf1 = Shelf.createNewShelf(library, room, "Action", 400, 1);
        Shelf shelf2 = Shelf.createNewShelf(library, room, "Fiction", 400, 1);


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

        List<String> args4 = new ArrayList<>();
        args4.add("borrowBook");
        args4.add("7b40bee4-e2ca-4903-996a-ea974a8cb435");
        args4.add("email.de");

        handleCLI(args4);
        assertOutputContains("Book wasn't found");

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

        //muss 2 mal vorkommen
        assertThat(outputStream.toString()).contains(uuid, "Harry", "Idi", "Action", "Max", "Mustermann", "email.de");

        String output = outputStream.toString();
        System.out.println(outputStream);
        int count = 0;
        int index = output.indexOf("Meier");

        while (index != -1) {
            count++;
            index = output.indexOf("Meier", index + 1);
        }
        assertThat(count).isEqualTo(2);
        assertOutputContains("Borrowed books viewed");


        List<String> args6 = new ArrayList<>();
        args6.add("returnBook");
        args6.add("833b92f9-1922-4bd9-87ba-08cf33d0b112");
        handleCLI(args6);
        int invalidInputCounter = outputStream.toString().split("Book wasn't found", -1).length - 1;
        assertThat(invalidInputCounter).isEqualTo(2);
        args6.set(1, uuid);
        handleCLI(args6);
        assertOutputContains("Book returned");
        assertThat(shelf1.getBooksOnShelf()).hasSize(2);

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

        if (uuid != null) {
            List<String> args8 = new ArrayList<>();
            args8.add("deleteBook");
            args8.add(uuid);
            handleCLI(args8);
        }

        handleCLI(List.of("viewBorrowedBooks"));
        assertOutputContains("No Books borrowed");

        if (uuid2 != null) {
            List<String> args9 = new ArrayList<>();
            args9.add("deleteBook");
            args9.add(uuid2);
            handleCLI(args9);
        }

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

        consoleUI.handle(args, library, csvAdapter);
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
        consoleUI.handle(args3, library, csvAdapter);
        assertThat(outputStream.toString()).contains("Book wasn't found");

        List<String> args4 = new ArrayList<>();
        args4.add("restoreBook");
        args4.add(uuid);
        consoleUI.handle(args4, library, csvAdapter);
        assertThat(library.getBookList().getFirst().getBookCondition()).isEqualTo(100);
        assertThat(outputStream.toString()).contains("Book restored");

        List<String> args5 = new ArrayList<>();
        args5.add("restoreBook");
        args5.add(uuid);
        args5.add("email.de");
        consoleUI.handle(args5, library, csvAdapter);
        assertThat(outputStream.toString()).contains("Invalid Input");

        List<String> args6 = new ArrayList<>();
        args6.add("deleteBook");
        args6.add(uuid);
        consoleUI.handle(args6, library, csvAdapter);
        System.out.println(outputStream);

        library.deleteShelf(shelf);
        library.deleteRoom(room);
    }
}
