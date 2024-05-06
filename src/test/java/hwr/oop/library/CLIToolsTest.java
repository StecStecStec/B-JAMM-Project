package hwr.oop.library;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.shadow.com.univocity.parsers.csv.Csv;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;




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
        args2.add("hans@meier.com");

        consoleUI.handle(args, csvAdapter);

        Assertions.assertThat(outputStream.toString()).contains("Visitor created");

        csvAdapter.clear();
        consoleUI.handle(args, csvAdapter);
        Assertions.assertThat(outputStream.toString()).contains("Mail already exists");


        csvAdapter.clear();
        consoleUI.handle(args2, csvAdapter);
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
