package hwr.oop.library;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssertAlternative;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.shadow.com.univocity.parsers.csv.Csv;
import org.junit.platform.engine.support.descriptor.FileSystemSource;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


class CLIToolsTest {

    //TEST macht mehr coverage als der folgende ************************************
    @Test
    void createVisitorTest() throws FileNotFoundException {
        final OutputStream outputStream = new ByteArrayOutputStream();
        final var consoleUI = new CLI(outputStream);
        CSVAdapter csvAdapter = new CSVAdapter(".\\src\\test\\resources\\csvTestFiles\\");

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

        Assertions.assertThat(outputStream.toString()).contains("Visitor created");

        csvAdapter.clear();
        consoleUI.handle(args, csvAdapter);
        Assertions.assertThat(outputStream.toString()).contains("Mail already exists");

        csvAdapter.clear();
        args2.set(1, "hans@meier.com");
        consoleUI.handle(args2, csvAdapter);
    }

    @Test
    void create_and_delete_VisitorsTest() throws FileNotFoundException {
        final OutputStream outputStream = new ByteArrayOutputStream();
        final var consoleUI = new CLI(outputStream);
        CSVAdapter csvAdapter = new CSVAdapter(".\\src\\test\\resources\\csvTestFiles\\");

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
        Assertions.assertThat(outputStream.toString()).contains("Visitor created");

        csvAdapter.clear();
        consoleUI.handle(args2, csvAdapter);
        Assertions.assertThat(outputStream.toString()).contains("Visitor created");

        csvAdapter.clear();
        consoleUI.handle(args, csvAdapter);
        Assertions.assertThat(outputStream.toString()).contains("Mail already exists");

        csvAdapter.clear();
        consoleUI.handle(args2, csvAdapter);
        Assertions.assertThat(outputStream.toString()).contains("Mail already exists");

        csvAdapter.clear();
        consoleUI.handle(delete, csvAdapter);
        Assertions.assertThat(outputStream.toString()).contains("Visitor wasn't found");

        csvAdapter.clear();
        delete.set(1, "ha@meier.com");
        consoleUI.handle(delete, csvAdapter);
        Assertions.assertThat(outputStream.toString()).contains("Visitor wasn't found");

        csvAdapter.clear();
        delete.set(1, "hans@meier.com");
        consoleUI.handle(delete, csvAdapter);
        Assertions.assertThat(outputStream.toString()).contains("Visitor deleted");

        csvAdapter.clear();
        delete.set(1, "hansi@meier.com");
        consoleUI.handle(delete, csvAdapter);
        Assertions.assertThat(outputStream.toString()).contains("Visitor deleted");

        csvAdapter.clear();
        args.removeLast();
        consoleUI.handle(args, csvAdapter);
        Assertions.assertThat(outputStream.toString()).contains("Invalid Input");

        csvAdapter.clear();
        args2.removeLast();
        consoleUI.handle(args2, csvAdapter);
        Assertions.assertThat(outputStream.toString()).contains("Invalid Input");

        csvAdapter.clear();
        delete.removeLast();
        consoleUI.handle(delete, csvAdapter);
        Assertions.assertThat(outputStream.toString()).contains("Invalid Input");
    }

    @Test
    void create_LibrarianTest() throws FileNotFoundException {
        final OutputStream outputStream = new ByteArrayOutputStream();
        final var consoleUI = new CLI(outputStream);
        CSVAdapter csvAdapter = new CSVAdapter(".\\src\\test\\resources\\csvTestFiles\\");

        List<String> args = new ArrayList<>();
        args.add("createLibrarian");
        args.add("Bib");
        args.add("Meier");
        args.add("01.01.2000");

        consoleUI.handle(args, csvAdapter);

        Assertions.assertThat(outputStream.toString()).contains("Librarian created");

        csvAdapter.clear();
        consoleUI.handle(args, csvAdapter);
        Assertions.assertThat(outputStream.toString()).contains("Librarian already exists");
        Assertions.assertThat(csvAdapter.getLibrarianList()).hasSize(1);

        csvAdapter.clear();
        args.set(0, "deleteLibrarian");
        consoleUI.handle(args, csvAdapter);
        Assertions.assertThat(outputStream.toString()).contains("Librarian deleted");

    }

    @Test
    void create_and_delete_LibrariansTest() throws FileNotFoundException {
        final OutputStream outputStream = new ByteArrayOutputStream();
        final var consoleUI = new CLI(outputStream);
        CSVAdapter csvAdapter = new CSVAdapter(".\\src\\test\\resources\\csvTestFiles\\");

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

        csvAdapter.clear();
        consoleUI.handle(args, csvAdapter);
        Assertions.assertThat(outputStream.toString()).contains("Librarian created");

        csvAdapter.clear();
        consoleUI.handle(args2, csvAdapter);
        Assertions.assertThat(outputStream.toString()).contains("Librarian created");

        csvAdapter.clear();
        consoleUI.handle(args, csvAdapter);
        Assertions.assertThat(outputStream.toString()).contains("Librarian already exists");

        csvAdapter.clear();
        consoleUI.handle(args2, csvAdapter);
        Assertions.assertThat(outputStream.toString()).contains("Librarian already exists");

        csvAdapter.clear();
        args.set(0, "deleteLibrarian");
        args.set(3, "05.01.2000");
        consoleUI.handle(args, csvAdapter);
        Assertions.assertThat(outputStream.toString()).contains("Librarian wasn't found");

        csvAdapter.clear();
        args2.set(0, "deleteLibrarian");
        args2.set(3, "02.01.2000");
        consoleUI.handle(args2, csvAdapter);
        Assertions.assertThat(outputStream.toString()).contains("Librarian wasn't found");
        Assertions.assertThat(csvAdapter.getLibrarianList()).hasSize(2);

        csvAdapter.clear();
        args.set(0, "deleteLibrarian");
        args.set(3, "01.01.2000");
        consoleUI.handle(args, csvAdapter);
        Assertions.assertThat(outputStream.toString()).contains("Librarian deleted");
        Assertions.assertThat(csvAdapter.getLibrarianList()).hasSize(1);


        csvAdapter.clear();
        args2.set(3, "01.01.2000");
        consoleUI.handle(args2, csvAdapter);
        Assertions.assertThat(outputStream.toString()).contains("Librarian deleted");
        Assertions.assertThat(csvAdapter.getLibrarianList()).isEmpty();

        csvAdapter.clear();
        args.removeLast();
        consoleUI.handle(args, csvAdapter);
        Assertions.assertThat(outputStream.toString()).contains("Invalid Input");

        csvAdapter.clear();
        args2.removeLast();
        args2.remove(2);
        consoleUI.handle(args2, csvAdapter);
        Assertions.assertThat(outputStream.toString()).contains("Invalid Input");
    }

    @Test
    void add_delete_and_view_BookTest() throws FileNotFoundException {
        final OutputStream outputStream = new ByteArrayOutputStream();
        final var consoleUI = new CLI(outputStream);
        CSVAdapter csvAdapter = new CSVAdapter(".\\src\\test\\resources\\csvTestFiles\\");
        int i = 0;
        String uuid = null;

        List<String> args = new ArrayList<>();
        args.add("addBook");
        args.add("Planes");
        args.add("Meier");
        args.add("Action");
        args.add("100");
        args.add("20");

        List<String> args2 = new ArrayList<>();
        args2.add("viewBooks");


        consoleUI.handle(args, csvAdapter);
        Assertions.assertThat(outputStream.toString()).contains("Book added");
        while (i < csvAdapter.getBookList().size()) {
            if (Objects.equals(csvAdapter.getBookList().get(i).getBookTitle(), "Planes") && Objects.equals(csvAdapter.getBookList().get(i).getBookAuthor(), "Meier")) {
                uuid = csvAdapter.getBookList().getLast().getBookID().toString();
                break;
            }
            i++;
        }

        csvAdapter.clear();
        args.set(3, "Roman");
        consoleUI.handle(args, csvAdapter);
        Assertions.assertThat(outputStream.toString()).contains("No Shelf found");

        csvAdapter.clear();
        consoleUI.handle(args2, csvAdapter);
        Assertions.assertThat(outputStream.toString()).contains(uuid, "Planes", "Meier", "Action");



        if (uuid != null) {
            List<String> args3 = new ArrayList<>();
            args3.add("deleteBook");
            args3.add("017a50d0-f7c5-4223-8ff3-4baa0d977ddf");


            csvAdapter.clear();
            consoleUI.handle(args3, csvAdapter);
            Assertions.assertThat(outputStream.toString()).contains("No Book found");

            csvAdapter.clear();
            args3.set(1, uuid);
            System.out.println(args3);
            consoleUI.handle(args3, csvAdapter);
            Assertions.assertThat(outputStream.toString()).contains("Book deleted");
        }

        csvAdapter.clear();
        args.removeLast();
        consoleUI.handle(args, csvAdapter);
        Assertions.assertThat(outputStream.toString()).contains("Invalid Input");
    }

    @Test
    void searchBookTest() throws FileNotFoundException {
        final OutputStream outputStream = new ByteArrayOutputStream();
        final var consoleUI = new CLI(outputStream);
        CSVAdapter csvAdapter = new CSVAdapter(".\\src\\test\\resources\\csvTestFiles\\");
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
        Assertions.assertThat(csvAdapter.getBookList()).hasSize(1);
        csvAdapter.clear();

        List<String> args2 = new ArrayList<>();
        args2.add("searchBook");
        args2.add("Plas");
        consoleUI.handle(args2, csvAdapter);
        Assertions.assertThat(outputStream.toString()).contains("Plas", "Meier", "Action");

        while (i < csvAdapter.getBookList().size()) {
            if (Objects.equals(csvAdapter.getBookList().get(i).getBookTitle(), "Plas") && Objects.equals(csvAdapter.getBookList().get(i).getBookAuthor(), "Meier")) {
                uuid = csvAdapter.getBookList().getLast().getBookID().toString();
                break;
            }
            i++;
        }

        if (uuid != null) {
            csvAdapter.clear();
            args.clear();
            args.add("deleteBook");
            args.add(uuid);
            consoleUI.handle(args, csvAdapter);
        }

    }


    //@Test
    /*void ViewBorrowedBooksTest() throws FileNotFoundException {
        final OutputStream outputStream = new ByteArrayOutputStream();
        final var consoleUI = new CLI(outputStream);

        //doesn't find files
        CSVAdapter csvAdapter = new CSVAdapter("\\csvFiles");
        //Visitor visitor = Visitor.createNewVisitor(csvAdapter, "Hans", "Meier", "01.01.2000", "hans@meier.com");

        List<String> args = new ArrayList<>();
        args.add("viewBorrowedBooks");
        args.add("hans@meier.com");

        consoleUI.handle(args, csvAdapter);

        boolean result = consoleUI.check(args,2,"viewBorrowedBooks");
        Assertions.assertThat(result).isTrue();
        Assertions.assertThat(outputStream.toString()).contains(csvAdapter.getVisitorList().toString());

        args.removeLast();
        consoleUI.handle(args, csvAdapter);
        consoleUI.check(args, 2, "viewBorrowedBooks");
        Assertions.assertThat(outputStream.toString()).contains("Usage: [option] [Email]\ncreateVisitor, createLibrarian, deleteVisitor, deleteLibrarian, addBook, deleteBook, searchBook, returnBook, restoreBook, viewBorrowedBooks, viewOpenPayments, viewOpenPaymentsLibrarian");

    }*/
}
