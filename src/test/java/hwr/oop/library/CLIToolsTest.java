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

    @Test
    void CreateVisitor_and_deleteVisitorTest() throws FileNotFoundException {
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
        consoleUI.handle(args2, csvAdapter);
        Assertions.assertThat(outputStream.toString()).contains("Visitor wasn't found");

        csvAdapter.clear();
        args2.set(1, "hans@meier.com");
        consoleUI.handle(args2, csvAdapter);

        csvAdapter.clear();
        args.removeLast();
        consoleUI.handle(args, csvAdapter);
        Assertions.assertThat(outputStream.toString()).contains("Invalid Input");

        csvAdapter.clear();
        args2.removeLast();
        consoleUI.handle(args2, csvAdapter);
        Assertions.assertThat(outputStream.toString()).contains("Invalid Input");
    }

    @Test
    void create_and_delete_more_VisitorsTest() throws FileNotFoundException {
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
        delete.add("hans@meier.com");

        consoleUI.handle(args, csvAdapter);
        Assertions.assertThat(outputStream.toString()).contains("Visitor created");

        csvAdapter.clear();
        consoleUI.handle(args2, csvAdapter);
        Assertions.assertThat(outputStream.toString()).contains("Visitor created");

        csvAdapter.clear();
        consoleUI.handle(args2, csvAdapter);
        Assertions.assertThat(outputStream.toString()).contains("Mail already exists");

        csvAdapter.clear();
        consoleUI.handle(delete, csvAdapter);
        Assertions.assertThat(outputStream.toString()).contains("Visitor deleted");

        csvAdapter.clear();
        delete.set(1, "hansi@meier.com");
        consoleUI.handle(delete, csvAdapter);
        Assertions.assertThat(outputStream.toString()).contains("Visitor deleted");


    }

    @Test
    void CreateVisitor_and_deleteLibrarianTest() throws FileNotFoundException {
        final OutputStream outputStream = new ByteArrayOutputStream();
        final var consoleUI = new CLI(outputStream);
        CSVAdapter csvAdapter = new CSVAdapter(".\\src\\test\\resources\\csvTestFiles\\");

        List<String> args = new ArrayList<>();
        args.add("createLibrarian");
        args.add("Hans");
        args.add("Meier");
        args.add("01.01.2000");

        List<String> args2 = new ArrayList<>();
        args2.add("deleteLibrarian");
        args2.add("Hans");
        args2.add("Meier");
        args2.add("02.01.2000");


        consoleUI.handle(args, csvAdapter);

        Assertions.assertThat(outputStream.toString()).contains("Librarian created");

        csvAdapter.clear();
        consoleUI.handle(args, csvAdapter);
        Assertions.assertThat(outputStream.toString()).contains("Librarian already exists");

        csvAdapter.clear();
        consoleUI.handle(args2, csvAdapter);
        Assertions.assertThat(outputStream.toString()).contains("Librarian wasn't found");

        csvAdapter.clear();
        args2.set(3, "01.01.2000");
        consoleUI.handle(args2, csvAdapter);

        csvAdapter.clear();
        args.removeLast();
        consoleUI.handle(args, csvAdapter);
        Assertions.assertThat(outputStream.toString()).contains("Invalid Input");

        csvAdapter.clear();
        args2.removeLast();
        consoleUI.handle(args2, csvAdapter);
        Assertions.assertThat(outputStream.toString()).contains("Invalid Input");
    }

    @Test
    void add_and_delete_Book() throws FileNotFoundException {
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

        consoleUI.handle(args, csvAdapter);
        Assertions.assertThat(outputStream.toString()).contains("Book added");
        while (i < csvAdapter.getBookList().size()) {
            if (Objects.equals(csvAdapter.getBookList().get(i).getBookTitle(), "Planes") && Objects.equals(csvAdapter.getBookList().get(i).getBookAuthor(), "Meier")) {
                uuid = csvAdapter.getBookList().getLast().getBookID().toString();
                System.out.println(uuid);
                break;
            }
            i++;
        }

        csvAdapter.clear();
        args.set(3, "Roman");
        consoleUI.handle(args, csvAdapter);
        Assertions.assertThat(outputStream.toString()).contains("No Shelf found");


        if (uuid != null) {
            List<String> args2 = new ArrayList<>();
            args2.add("deleteBook");
            args2.add(uuid);


            csvAdapter.clear();
            consoleUI.handle(args2, csvAdapter);
            Assertions.assertThat(outputStream.toString()).contains("Book deleted");
        }


        csvAdapter.clear();

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
