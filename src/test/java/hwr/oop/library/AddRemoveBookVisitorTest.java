package hwr.oop.library;

import hwr.oop.library.persistance.CSVAdapter;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


class AddRemoveBookVisitorTest {
    @Test
    void addBorrowedBook_checkIfBookAdded() {
        String path = "/csvTestFiles/";
        InputStream stream = getClass().getResourceAsStream(path);
        assert stream != null;
        CSVAdapter csvAdapter = new CSVAdapter(stream.toString());

        Visitor visitor = Visitor.createCompleteVisitor(csvAdapter, "Max", "Mustermann", "01.01.1999", "max.mustermann@gmx.de", UUID.randomUUID());
        Room room = Room.createNewRoom(csvAdapter, 5);
        Shelf shelf = Shelf.createNewShelf(csvAdapter, room, "Action", 400, 1);
        Book book = Book.createNewBook(csvAdapter, "Welt", "Peter Hans", "Natur", shelf, 100, 3);
        visitor.addBorrowedBook(book);
        assertThat(book).isIn(visitor.getBorrowedBooks());
    }

    @Test
    void removeBorrowedBook_checkIfBookRemoved() {
        String path = "/csvTestFiles/";
        InputStream stream = getClass().getResourceAsStream(path);
        assert stream != null;
        CSVAdapter csvAdapter = new CSVAdapter(stream.toString());

        Visitor visitor = Visitor.createCompleteVisitor(csvAdapter, "Max", "Mustermann", "01.01.1999", "max.mustermann@gmx.de", UUID.randomUUID());
        Room room = Room.createNewRoom(csvAdapter, 5);
        Shelf shelf = Shelf.createNewShelf(csvAdapter, room, "Action", 400, 1);
        Book book = Book.createNewBook(csvAdapter, "Welt", "Peter Hans", "Natur", shelf, 100, 3);
        visitor.addBorrowedBook(book);
        visitor.removeBorrowedBook(book);
        assertThat(book).isNotIn(visitor.getBorrowedBooks());
    }

    @Test
    void addBookToReturn_checkIfBookAdded() {
        String path = "/csvTestFiles/";
        InputStream stream = getClass().getResourceAsStream(path);
        assert stream != null;
        CSVAdapter csvAdapter = new CSVAdapter(stream.toString());

        Visitor visitor = Visitor.createCompleteVisitor(csvAdapter, "Max", "Mustermann", "01.01.1999", "max.mustermann@gmx.de", UUID.randomUUID());
        Room room = Room.createNewRoom(csvAdapter, 5);
        Shelf shelf = Shelf.createNewShelf(csvAdapter, room, "Action", 400, 1);
        Book book = Book.createNewBook(csvAdapter, "Welt", "Peter Hans", "Natur", shelf, 100, 3);
        visitor.addBookToReturn(book);
        assertThat(book).isIn(visitor.getBooksToReturn());
    }

    @Test
    void removeBookToReturn_checkIfBookRemoved() {
        String path = "/csvTestFiles/";
        InputStream stream = getClass().getResourceAsStream(path);
        assert stream != null;
        CSVAdapter csvAdapter = new CSVAdapter(stream.toString());

        Visitor visitor = Visitor.createNewVisitor(csvAdapter, "Max", "Mustermann", "01.01.1999", "max.mustermann@gmx.de");
        Room room = Room.createNewRoom(csvAdapter, 5);
        Shelf shelf = Shelf.createNewShelf(csvAdapter, room, "Action", 400, 1);
        Book book = Book.createNewBook(csvAdapter, "Welt", "Peter Hans", "Natur", shelf, 100, 3);
        visitor.addBookToReturn(book);
        visitor.removeBookToReturn(book);
        assertThat(book).isNotIn(visitor.getBooksToReturn());
    }
}
