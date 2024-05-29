package hwr.oop.library;

import hwr.oop.library.cli.CLI;
import hwr.oop.library.domain.Library;
import hwr.oop.library.persistence.CSVAdapter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


import static org.assertj.core.api.Assertions.assertThat;

class CLIToolsTest {

    private Library library;
    private CSVAdapter csvAdapter;

    @BeforeEach
    void setUp() {
        URL resourceUrl = getClass().getClassLoader().getResource("csvTestFiles");
        assert resourceUrl != null;
        File directory = new File(resourceUrl.getFile());
        String path = directory.getAbsolutePath() + "/";
        csvAdapter = new CSVAdapter(path);
        library = Library.createNewLibrary();
    }

    @Test
    void createVisitorTest() throws FileNotFoundException {
        final OutputStream outputStream = new ByteArrayOutputStream();
        final var consoleUI = new CLI(outputStream);

        List<String> args = new ArrayList<>();
        args.add("createVisitor");
        args.add("Hans");
        args.add("Meier");
        args.add("01.01.2000");
        args.add("hans@meier.com");

        List<String> args2 = new ArrayList<>();
        args2.add("deleteVisitor");
        args2.add("ha@meier.com");

        consoleUI.handle(args, csvAdapter);

        assertThat(outputStream.toString()).contains("Visitor created");

        consoleUI.handle(args, csvAdapter);
        assertThat(outputStream.toString()).contains("Mail already exists");

        args2.set(1, "hans@meier.com");
        consoleUI.handle(args2, csvAdapter);
    }

    @Test
    void create_and_delete_VisitorsTest() throws FileNotFoundException {
        final OutputStream outputStream = new ByteArrayOutputStream();
        final var consoleUI = new CLI(outputStream);

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

        consoleUI.handle(args, csvAdapter);
        assertThat(outputStream.toString()).contains("Visitor created");

        consoleUI.handle(args2, csvAdapter);
        assertThat(outputStream.toString()).contains("Visitor created");

        consoleUI.handle(args, csvAdapter);
        assertThat(outputStream.toString()).contains("Mail already exists");

        consoleUI.handle(args2, csvAdapter);
        assertThat(outputStream.toString()).contains("Mail already exists");

        consoleUI.handle(delete, csvAdapter);
        assertThat(outputStream.toString()).contains("Visitor wasn't found");

        delete.set(1, "ha@meier.com");
        consoleUI.handle(delete, csvAdapter);
        assertThat(outputStream.toString()).contains("Visitor wasn't found");

        delete.set(1, "hans@meier.com");
        consoleUI.handle(delete, csvAdapter);
        assertThat(outputStream.toString()).contains("Visitor deleted");

        delete.set(1, "hansi@meier.com");
        consoleUI.handle(delete, csvAdapter);
        assertThat(outputStream.toString()).contains("Visitor deleted");

        args.removeLast();
        consoleUI.handle(args, csvAdapter);
        assertThat(outputStream.toString()).contains("Invalid Input");

        args2.removeLast();
        consoleUI.handle(args2, csvAdapter);
        assertThat(outputStream.toString()).contains("Invalid Input");

        delete.removeLast();
        consoleUI.handle(delete, csvAdapter);
        assertThat(outputStream.toString()).contains("Invalid Input");
    }

    @Test
    void create_LibrarianTest() throws FileNotFoundException {
        final OutputStream outputStream = new ByteArrayOutputStream();
        final var consoleUI = new CLI(outputStream);

        List<String> args = new ArrayList<>();
        args.add("createLibrarian");
        args.add("Bib");
        args.add("Meier");
        args.add("01.01.2000");

        consoleUI.handle(args, csvAdapter);
        assertThat(outputStream.toString()).contains("Librarian created");

        consoleUI.handle(args, csvAdapter);
        assertThat(outputStream.toString()).contains("Librarian already exists");


        args.set(0, "deleteLibrarian");
        consoleUI.handle(args, csvAdapter);
    }

    @Test
    void create_and_delete_LibrariansTest() throws FileNotFoundException {
        final OutputStream outputStream = new ByteArrayOutputStream();
        final var consoleUI = new CLI(outputStream);

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

        consoleUI.handle(args, csvAdapter);
        assertThat(outputStream.toString()).contains("Librarian created");

        consoleUI.handle(args2, csvAdapter);
        assertThat(outputStream.toString()).contains("Librarian created");
        System.out.println(library.getLibrarianList());

        /*
        csvAdapter.loadLibrary();
        consoleUI.handle(args, csvAdapter);
        assertThat(outputStream.toString()).contains("Librarian already exists");
        csvAdapter.saveLibrary(library);

           /*
        csvAdapter.loadLibrary();
        consoleUI.handle(args2, csvAdapter);
        assertThat(outputStream.toString()).contains("Librarian already exists");
        csvAdapter.saveLibrary(library);

        csvAdapter.loadLibrary();
        args.set(0, "deleteLibrarian");
        args.set(3, "05.01.2000");
        consoleUI.handle(args, csvAdapter);
        assertThat(outputStream.toString()).contains("Librarian wasn't found");
        csvAdapter.saveLibrary(library);

        csvAdapter.loadLibrary();
        args2.set(0, "deleteLibrarian");
        args2.set(3, "02.01.2000");
        consoleUI.handle(args2, csvAdapter);
        assertThat(outputStream.toString()).contains("Librarian wasn't found");
        assertThat(library.getLibrarianList()).hasSize(2);
        csvAdapter.saveLibrary(library);

        csvAdapter.loadLibrary();
        consoleUI.handle(List.of("createLibrarian"), csvAdapter);
        assertThat(outputStream.toString()).contains("Invalid Input");
        csvAdapter.saveLibrary(library);

        csvAdapter.loadLibrary();
        args.set(0, "deleteLibrarian");
        args.set(3, "01.01.2000");
        consoleUI.handle(args, csvAdapter);
        assertThat(outputStream.toString()).contains("Librarian deleted");
        assertThat(library.getLibrarianList()).hasSize(1);
        csvAdapter.saveLibrary(library);

        csvAdapter.loadLibrary();
        args2.set(3, "01.01.2000");
        consoleUI.handle(args2, csvAdapter);
        assertThat(outputStream.toString()).contains("Librarian deleted");
        assertThat(library.getLibrarianList()).isEmpty();
        csvAdapter.saveLibrary(library);

        csvAdapter.loadLibrary();
        args.removeLast();
        consoleUI.handle(args, csvAdapter);
        assertThat(outputStream.toString()).contains("Invalid Input");
        csvAdapter.saveLibrary(library);

        csvAdapter.loadLibrary();
        args2.removeLast();
        args2.remove(2);
        consoleUI.handle(args2, csvAdapter);
        assertThat(outputStream.toString()).contains("Invalid Input");
        csvAdapter.saveLibrary(library);

         */
    }

    @Test
    void add_delete_and_view_BookTest() throws FileNotFoundException {
        final OutputStream outputStream = new ByteArrayOutputStream();
        final var consoleUI = new CLI(outputStream);
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


        consoleUI.handle(args, csvAdapter);
        assertThat(outputStream.toString()).contains("Book added");
        while (i < library.getBookList().size()) {
            if (Objects.equals(library.getBookList().get(i).getBookTitle(), "Planes") && Objects.equals(library.getBookList().get(i).getBookAuthor(), "Alf")) {
                uuid = library.getBookList().get(i).getBookID().toString();
                break;
            }
            i++;
        }

        args.set(3, "Roman");
        consoleUI.handle(args, csvAdapter);
        assertThat(outputStream.toString()).contains("No Shelf found");

        consoleUI.handle(args2, csvAdapter);
        assertThat(outputStream.toString()).contains(uuid, "Planes", "Alf", "Action");

        List<String> invalidInput = new ArrayList<>();
        invalidInput.add("deleteBook");
        invalidInput.add("017a50d0-f7c5-4223-8ff3-4baa0d977ddf");
        invalidInput.add("invalid Input");

        consoleUI.handle(invalidInput, csvAdapter);
        assertThat(outputStream.toString()).contains("Invalid Input");

        if (uuid != null) {
            List<String> args3 = new ArrayList<>();
            args3.add("deleteBook");
            args3.add("017a50d0-f7c5-4223-8ff3-4baa0d977ddf");


            consoleUI.handle(args3, csvAdapter);
            assertThat(outputStream.toString()).contains("No Book found");

            args3.set(1, uuid);
            consoleUI.handle(args3, csvAdapter);
            assertThat(outputStream.toString()).contains("Book deleted");
        }

        args.removeLast();
        consoleUI.handle(args, csvAdapter);
        assertThat(outputStream.toString()).contains("Invalid Input");
    }

    @Test
    void searchBookTest() throws FileNotFoundException {
        final OutputStream outputStream = new ByteArrayOutputStream();
        final var consoleUI = new CLI(outputStream);
        int i = 0;
        String uuid = null;

        List<String> args = new ArrayList<>();
        args.add("addBook");
        args.add("Plas");
        args.add("Meier");
        args.add("Action");
        args.add("100");
        args.add("20");

        consoleUI.handle(args, csvAdapter); //add book
        assertThat(library.getBookList()).hasSize(1);


        List<String> invalidInput = new ArrayList<>();
        invalidInput.add("searchBook");
        invalidInput.add("Plas");
        invalidInput.add("Invalid Input");
        consoleUI.handle(invalidInput, csvAdapter);
        assertThat(outputStream.toString()).contains("Invalid Input");

        List<String> args2 = new ArrayList<>();
        args2.add("searchBook");
        args2.add("Plas");
        consoleUI.handle(args2, csvAdapter);
        assertThat(outputStream.toString()).containsOnlyOnce("BookID\t\t\t\t\tTitle\tAuthor\tGenre");
        assertThat(outputStream.toString()).contains("Plas", "Meier", "Action");

        while (i < library.getBookList().size()) {
            if (Objects.equals(library.getBookList().get(i).getBookTitle(), "Plas") && Objects.equals(library.getBookList().get(i).getBookAuthor(), "Meier")) {
                uuid = library.getBookList().get(i).getBookID().toString();
                break;
            }
            i++;
        }

        if (uuid != null) {
            args.clear();
            args.add("deleteBook");
            args.add(uuid);
            consoleUI.handle(args, csvAdapter);
        }

    }

    @Test
    void BorrowAndReturnBookTest() throws FileNotFoundException {
        final OutputStream outputStream = new ByteArrayOutputStream();
        final var consoleUI = new CLI(outputStream);
        int i = 0;
        String uuid = null;
        String uuid2 = null;

        List<String> args = new ArrayList<>();
        args.add("addBook");
        args.add("Harry");
        args.add("Idi");
        args.add("Action");
        args.add("100");
        args.add("10");

        consoleUI.handle(args, csvAdapter);

        List<String> args1 = new ArrayList<>();
        args1.add("addBook");
        args1.add("Planes");
        args1.add("Meier");
        args1.add("Action");
        args1.add("100");
        args1.add("20");

        List<String> args2 = new ArrayList<>();
        args2.add("viewBooks");

        consoleUI.handle(args1, csvAdapter);
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

        consoleUI.handle(args2, csvAdapter);

        List<String> args2_1 = new ArrayList<>();
        args2_1.add("createVisitor");
        args2_1.add("Lach");
        args2_1.add("Mustermann");
        args2_1.add("02.01.1998");
        args2_1.add("l.de");

        consoleUI.handle(args2_1, csvAdapter);

        List<String> args3 = new ArrayList<>();
        args3.add("createVisitor");
        args3.add("Max");
        args3.add("Mustermann");
        args3.add("01.01.1999");
        args3.add("email.de");
        consoleUI.handle(args3, csvAdapter);

        List<String> args4 = new ArrayList<>();
        args4.add("borrowBook");
        args4.add("7b40bee4-e2ca-4903-996a-ea974a8cb435");
        args4.add("email.de");

        consoleUI.handle(args4, csvAdapter);
        assertThat(outputStream.toString()).contains("Book wasn't found");

        List<String> InvalidInput = new ArrayList<>();
        InvalidInput.add("borrowBook");
        InvalidInput.add("7b40bee4-e2ca-4903-996a-ea974a8cb435");
        InvalidInput.add("email.de");
        InvalidInput.add("Invalid Input");

        consoleUI.handle(InvalidInput, csvAdapter);
        assertThat(outputStream.toString()).contains("Invalid Input");

        List<String> args5 = new ArrayList<>();
        args5.add("borrowBook");
        args5.add(uuid);
        args5.add("email.de");
        consoleUI.handle(args5, csvAdapter);
        assertThat(outputStream.toString()).contains("Book borrowed");


        List<String> argsViewBorrowedBooks = new ArrayList<>();
        argsViewBorrowedBooks.add("viewBorrowedBooks");
        consoleUI.handle(argsViewBorrowedBooks, csvAdapter);

        String output = outputStream.toString();
        int count = 0;
        int index = output.indexOf("Meier");

        while (index != -1) {
            count++;
            index = output.indexOf("Meier", index + 1);
        }
        System.out.println(output);
        assertThat(count).isEqualTo(2);
        assertThat(outputStream.toString()).contains("Borrowed books viewed");


        List<String> args6 = new ArrayList<>();
        args6.add("returnBook");
        args6.add("833b92f9-1922-4bd9-87ba-08cf33d0b112");
        consoleUI.handle(args6, csvAdapter);
        assertThat(outputStream.toString()).contains("Book wasn't found");
        args6.set(1, uuid);
        consoleUI.handle(args6, csvAdapter);
        assertThat(outputStream.toString()).contains("Book returned");

        List<String> InvalidInput2 = new ArrayList<>();
        InvalidInput2.add("returnBook");
        InvalidInput2.add(uuid);
        InvalidInput2.add("Invalid Input");
        consoleUI.handle(InvalidInput2, csvAdapter);
        assertThat(outputStream.toString()).contains("Invalid Input");

        List<String> args7 = new ArrayList<>();
        args7.add("deleteVisitor");
        args7.add("email.de");
        consoleUI.handle(args7, csvAdapter);

        if (uuid != null) {
            List<String> args8 = new ArrayList<>();
            args8.add("deleteBook");
            args8.add(uuid);
            consoleUI.handle(args8, csvAdapter);
        }

        consoleUI.handle(List.of("viewBorrowedBooks"), csvAdapter);
        assertThat(outputStream.toString()).contains("No Books borrowed");

        if (uuid2 != null) {
            List<String> args9 = new ArrayList<>();
            args9.add("deleteBook");
            args9.add(uuid2);
            consoleUI.handle(args9, csvAdapter);
        }
    }

    @Test
    void ViewBorrowedBooksInvalidInputTest() throws FileNotFoundException {
        final OutputStream outputStream = new ByteArrayOutputStream();
        final var consoleUI = new CLI(outputStream);

        List<String> args = new ArrayList<>();
        args.add("viewBorrowedBooks"); // gültiger Befehl
        args.add("invalid_email"); // ungültige E-Mail
        consoleUI.handle(args, csvAdapter);
        assertThat(outputStream.toString()).contains("Invalid Input");
    }

    @Test
    void RestoreBookTest() throws FileNotFoundException {
        final OutputStream outputStream = new ByteArrayOutputStream();
        final var consoleUI = new CLI(outputStream);
        int i = 0;
        String uuid = null;

        List<String> args = new ArrayList<>();
        args.add("addBook");
        args.add("Planes");
        args.add("Meier");
        args.add("Action");
        args.add("50");
        args.add("20");

        List<String> args2 = new ArrayList<>();
        args2.add("viewBooks");

        consoleUI.handle(args, csvAdapter);
        while (i < library.getBookList().size()) {
            if (Objects.equals(library.getBookList().get(i).getBookTitle(), "Planes") && Objects.equals(library.getBookList().get(i).getBookAuthor(), "Meier")) {
                uuid = library.getBookList().get(i).getBookID().toString();
                break;
            }
            i++;
        }

        consoleUI.handle(args2, csvAdapter);

        args2.add("abcdef");
        consoleUI.handle(args2, csvAdapter);
        assertThat(outputStream.toString()).contains("Invalid Input");

        List<String> args3 = new ArrayList<>();
        args3.add("restoreBook");
        args3.add("acb45dff-660b-4701-9852-b89873580ec1");
        consoleUI.handle(args3, csvAdapter);
        assertThat(outputStream.toString()).contains("Book wasn't found");

        List<String> args4 = new ArrayList<>();
        args4.add("restoreBook");
        args4.add(uuid);
        consoleUI.handle(args4, csvAdapter);
        assertThat(library.getBookList().getFirst().getBookCondition()).isEqualTo(100);
        assertThat(outputStream.toString()).contains("Book restored");

        List<String> args5 = new ArrayList<>();
        args5.add("restoreBook");
        args5.add(uuid);
        args5.add("email.de");
        consoleUI.handle(args5, csvAdapter);
        assertThat(outputStream.toString()).contains("Invalid Input");

        List<String> args6 = new ArrayList<>();
        args6.add("deleteBook");
        args6.add(uuid);
        consoleUI.handle(args6, csvAdapter);
    }

    @AfterEach
    void tearDown() {
        csvAdapter.saveLibrary(library);
    }
}
