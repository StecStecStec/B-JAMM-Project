package hwr.oop.library;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

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

        List<String> invalidInput = new ArrayList<>();
        invalidInput.add("createLibrarian");
        invalidInput.add("Bib");
        invalidInput.add("Meier");
        invalidInput.add("01.01.2000");
        invalidInput.add("hans@meier.com");

        consoleUI.handle(invalidInput, csvAdapter);

        Assertions.assertThat(outputStream.toString()).contains("Invalid Input");

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
                uuid = csvAdapter.getBookList().get(i).getBookID().toString();
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

        csvAdapter.clear();
        List<String> invalidInput = new ArrayList<>();
        invalidInput.add("deleteBook");
        invalidInput.add("017a50d0-f7c5-4223-8ff3-4baa0d977ddf");
        invalidInput.add("invalid Input");

        consoleUI.handle(invalidInput, csvAdapter);
        Assertions.assertThat(outputStream.toString()).contains("Invalid Input");

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
        List<String> invalidInput = new ArrayList<>();
        invalidInput.add("searchBook");
        invalidInput.add("Plas");
        invalidInput.add("Invalid Input");
        consoleUI.handle(invalidInput, csvAdapter);
        Assertions.assertThat(outputStream.toString()).contains("Invalid Input");

        csvAdapter.clear();
        List<String> args2 = new ArrayList<>();
        args2.add("searchBook");
        args2.add("Plas");
        consoleUI.handle(args2, csvAdapter);
        Assertions.assertThat(outputStream.toString()).contains("Plas", "Meier", "Action");

        while (i < csvAdapter.getBookList().size()) {
            if (Objects.equals(csvAdapter.getBookList().get(i).getBookTitle(), "Plas") && Objects.equals(csvAdapter.getBookList().get(i).getBookAuthor(), "Meier")) {
                uuid = csvAdapter.getBookList().get(i).getBookID().toString();
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

    @Test
    void BorrowAndReturnBookTest() throws FileNotFoundException {
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
        while (i < csvAdapter.getBookList().size()) {
            if (Objects.equals(csvAdapter.getBookList().get(i).getBookTitle(), "Planes") && Objects.equals(csvAdapter.getBookList().get(i).getBookAuthor(), "Meier")) {
                uuid = csvAdapter.getBookList().get(i).getBookID().toString();
                break;
            }
            i++;
        }

        csvAdapter.clear();
        consoleUI.handle(args2, csvAdapter);

        List<String> args2_1 = new ArrayList<>();
        args2_1.add("createVisitor");
        args2_1.add("Lach");
        args2_1.add("Mustermann");
        args2_1.add("02.01.1998");
        args2_1.add("l.de");

        csvAdapter.clear();
        consoleUI.handle(args2_1,csvAdapter);

        List<String> args3 = new ArrayList<>();
        args3.add("createVisitor");
        args3.add("Max");
        args3.add("Mustermann");
        args3.add("01.01.1999");
        args3.add("email.de");
        csvAdapter.clear();
        consoleUI.handle(args3, csvAdapter);

        csvAdapter.clear();
        List<String> args4 = new ArrayList<>();
        args4.add("borrowBook");
        args4.add("7b40bee4-e2ca-4903-996a-ea974a8cb435");
        args4.add("email.de");

        consoleUI.handle(args4, csvAdapter);
        Assertions.assertThat(outputStream.toString()).contains("Book wasn't found");

        csvAdapter.clear();
        List<String> InvalidInput = new ArrayList<>();
        InvalidInput.add("borrowBook");
        InvalidInput.add("7b40bee4-e2ca-4903-996a-ea974a8cb435");
        InvalidInput.add("email.de");
        InvalidInput.add("Invalid Input");

        consoleUI.handle(InvalidInput, csvAdapter);
        Assertions.assertThat(outputStream.toString()).contains("Invalid Input");

        csvAdapter.clear();
        List<String> args5 = new ArrayList<>();
        args5.add("borrowBook");
        args5.add(uuid);
        args5.add("email.de");
        consoleUI.handle(args5, csvAdapter);
        Assertions.assertThat(outputStream.toString()).contains("Book borrowed");


        csvAdapter.clear();
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

        Assertions.assertThat(count).isEqualTo(2);
        Assertions.assertThat(outputStream.toString()).contains("Borrowed books viewed");

        csvAdapter.clear();
        List<String> args6 = new ArrayList<>();
        args6.add("returnBook");
        args6.add(uuid);
        consoleUI.handle(args6, csvAdapter);
        Assertions.assertThat(outputStream.toString()).contains("Book returned");

        csvAdapter.clear();
        List<String> InvalidInput2 = new ArrayList<>();
        InvalidInput2.add("returnBook");
        InvalidInput2.add(uuid);
        InvalidInput2.add("Invalid Input");
        consoleUI.handle(InvalidInput2, csvAdapter);
        Assertions.assertThat(outputStream.toString()).contains("Invalid Input");

        csvAdapter.clear();
        List<String> args7 = new ArrayList<>();
        args7.add("deleteVisitor");
        args7.add("email.de");

        consoleUI.handle(args7, csvAdapter);

        csvAdapter.clear();
        if (uuid != null) {
            List<String> args8 = new ArrayList<>();
            args8.add("deleteBook");
            args8.add(uuid);
            consoleUI.handle(args8, csvAdapter);
        }


    }

    @Test
    void ViewBorrowedBooksInvalidInputTest() throws FileNotFoundException {
        final OutputStream outputStream = new ByteArrayOutputStream();
        final var consoleUI = new CLI(outputStream);

        CSVAdapter csvAdapter = new CSVAdapter(".\\src\\test\\resources\\csvTestFiles\\");

        List<String> args = new ArrayList<>();
        args.add("viewBorrowedBooks"); // gültiger Befehl
        args.add("invalid_email"); // ungültige E-Mail
        consoleUI.handle(args, csvAdapter);
        Assertions.assertThat(outputStream.toString()).contains("Invalid Input");

    }

    @Test
    void RestoreBookTest() throws FileNotFoundException {
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
        args.add("50");
        args.add("20");

        List<String> args2 = new ArrayList<>();
        args2.add("viewBooks");

        consoleUI.handle(args, csvAdapter);
        while (i < csvAdapter.getBookList().size()) {
            if (Objects.equals(csvAdapter.getBookList().get(i).getBookTitle(), "Planes") && Objects.equals(csvAdapter.getBookList().get(i).getBookAuthor(), "Meier")) {
                uuid = csvAdapter.getBookList().get(i).getBookID().toString();
                break;
            }
            i++;
        }

        csvAdapter.clear();
        consoleUI.handle(args2, csvAdapter);

        csvAdapter.clear();
        args2.add("abcdef");
        consoleUI.handle(args2, csvAdapter);
        Assertions.assertThat(outputStream.toString()).contains("Invalid Input");

        csvAdapter.clear();
        List<String> args3 = new ArrayList<>();
        args3.add("restoreBook");
        args3.add("acb45dff-660b-4701-9852-b89873580ec1"); // Falscher UUID-Wert
        consoleUI.handle(args3, csvAdapter);
        Assertions.assertThat(outputStream.toString()).contains("Book wasn't found");

        csvAdapter.clear();
        List<String> args4 = new ArrayList<>();
        args4.add("restoreBook");
        args4.add(uuid);
        consoleUI.handle(args4, csvAdapter);
        Assertions.assertThat(csvAdapter.getBookList().getFirst().getBookCondition()).isEqualTo(100);
        Assertions.assertThat(outputStream.toString()).contains("Book restored");

        csvAdapter.clear();
        List<String> args5 = new ArrayList<>();
        args5.add("restoreBook");
        args5.add(uuid);
        args5.add("email.de");
        consoleUI.handle(args5, csvAdapter);
        Assertions.assertThat(outputStream.toString()).contains("Invalid Input");

        csvAdapter.clear();
        List<String> args6 = new ArrayList<>();
        args6.add("deleteBook");
        args6.add(uuid);
        consoleUI.handle(args6, csvAdapter);
    }

}
