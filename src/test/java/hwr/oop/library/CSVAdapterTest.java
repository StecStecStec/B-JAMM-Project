package hwr.oop.library;

import hwr.oop.library.cli.CLI;
import hwr.oop.library.domain.*;
import hwr.oop.library.persistance.CSVAdapter;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.OutputStream;

import static org.junit.jupiter.api.Assertions.assertThrows;

class CSVAdapterTest {
    @Test
    void loadClearAndSaveCSV(){
        CSVAdapter csvAdapter = new CSVAdapter(".\\src\\test\\resources\\csvTestFiles\\");
        Room room = Room.createNewRoom(csvAdapter, 5);
        Shelf shelf = Shelf.createNewShelf(csvAdapter, room, "Action", 400, 1);
        Book book1 = Book.createNewBook(csvAdapter, "Welt", "Peter Hans", "Natur", shelf, 100, 3);
        Book book2 = Book.createNewBook(csvAdapter, "Welten", "Peter Hansen", "Naturen", shelf, 100, 5);
        Visitor visitor1 = Visitor.createNewVisitor(csvAdapter, "Max", "Mustermann", "01.01.1999", "max.mustermann@gmx.de");
        Visitor visitor2 = Visitor.createNewVisitor(csvAdapter, "Maxia", "Mustermannia", "01.02.1999", "max.mustermannia@gmx.de");
        Librarian librarian = Librarian.createNewLibrarian(csvAdapter, "Maxa", "Mustermanna", "01.01.2000");
        book2.borrow(visitor2);
        visitor2.addBookToReturn(book2);

        Assertions.assertThat(csvAdapter.getRoomList()).contains(room);
        Assertions.assertThat(csvAdapter.getShelfList()).contains(shelf);
        Assertions.assertThat(csvAdapter.getBookList()).contains(book1)
                                                       .contains(book2);
        Assertions.assertThat(csvAdapter.getVisitorList()).contains(visitor1)
                                                          .contains(visitor2);
        Assertions.assertThat(csvAdapter.getLibrarianList()).contains(librarian);

        csvAdapter.saveCSV();

        csvAdapter.clear();
        Assertions.assertThat(csvAdapter.getRoomList()).isEmpty();
        Assertions.assertThat(csvAdapter.getShelfList()).isEmpty();
        Assertions.assertThat(csvAdapter.getBookList()).isEmpty();
        Assertions.assertThat(csvAdapter.getVisitorList()).isEmpty();
        Assertions.assertThat(csvAdapter.getLibrarianList()).isEmpty();

        csvAdapter.loadCSV();

        Assertions.assertThat(room).isEqualTo(csvAdapter.getRoomList().getFirst());
        Assertions.assertThat(shelf).isEqualTo(csvAdapter.getShelfList().getFirst());
        Assertions.assertThat(book1).isEqualTo(csvAdapter.getBookList().getFirst());
        Assertions.assertThat(book2).isEqualTo(csvAdapter.getBookList().getLast());
        Assertions.assertThat(visitor1).isEqualTo(csvAdapter.getVisitorList().getFirst());
        Assertions.assertThat(visitor2).isEqualTo(csvAdapter.getVisitorList().getLast());
        Assertions.assertThat(librarian).isEqualTo(csvAdapter.getLibrarianList().getFirst());

        csvAdapter.deleteVisitor(visitor1);
        csvAdapter.deleteVisitor(visitor2);
        csvAdapter.deleteLibrarian(librarian);
        csvAdapter.deleteBook(book1);
        csvAdapter.deleteBook(book2);
        csvAdapter.saveCSV();

    }
    @Test
    void testCSVAdapter() throws FileNotFoundException {
        final OutputStream outputStream = new ByteArrayOutputStream();
        final var consoleUI = new CLI(outputStream);

        assertThrows(RuntimeException.class, () -> {

            CSVAdapter csvAdapter = new CSVAdapter("invalid_path_to_file.csv");
            csvAdapter.loadCSV();
            csvAdapter.saveCSV();
        });


    }
}

