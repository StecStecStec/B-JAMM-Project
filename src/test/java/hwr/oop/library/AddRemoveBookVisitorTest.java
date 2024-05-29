package hwr.oop.library;

import hwr.oop.library.domain.Book;
import hwr.oop.library.domain.Room;
import hwr.oop.library.domain.Shelf;
import hwr.oop.library.domain.Visitor;
import hwr.oop.library.persistence.CSVAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URL;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


class AddRemoveBookVisitorTest {

    private String path;
    @BeforeEach
    void setUp() {
        URL resourceUrl = getClass().getClassLoader().getResource("csvTestFiles");
        assert resourceUrl != null;
        File directory = new File(resourceUrl.getFile());
        path = directory.getAbsolutePath() +"/";
    }
    @Test
    void addBorrowedBook_checkIfBookAdded() {
        CSVAdapter csvAdapter = new CSVAdapter(path);

        Visitor visitor = Visitor.createCompleteVisitor(csvAdapter, "Max", "Mustermann", "01.01.1999", "max.mustermann@gmx.de", UUID.randomUUID());
        Room room = Room.createNewRoom(csvAdapter, 5);
        Shelf shelf = Shelf.createNewShelf(csvAdapter, room, "Action", 400, 1);
        Book book = Book.createNewBook(csvAdapter, "Welt", "Peter Hans", "Natur", shelf, 100, 3);
        visitor.addBorrowedBook(book);
        assertThat(book).isIn(visitor.getBorrowedBooks());
    }

    @Test
    void removeBorrowedBook_checkIfBookRemoved() {
        CSVAdapter csvAdapter = new CSVAdapter(path);

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
        CSVAdapter csvAdapter = new CSVAdapter(path);

        Visitor visitor = Visitor.createCompleteVisitor(csvAdapter, "Max", "Mustermann", "01.01.1999", "max.mustermann@gmx.de", UUID.randomUUID());
        Room room = Room.createNewRoom(csvAdapter, 5);
        Shelf shelf = Shelf.createNewShelf(csvAdapter, room, "Action", 400, 1);
        Book book = Book.createNewBook(csvAdapter, "Welt", "Peter Hans", "Natur", shelf, 100, 3);
        visitor.addBookToReturn(book);
        assertThat(book).isIn(visitor.getBooksToReturn());
    }

    @Test
    void removeBookToReturn_checkIfBookRemoved() {
        CSVAdapter csvAdapter = new CSVAdapter(path);

        Visitor visitor = Visitor.createNewVisitor(csvAdapter, "Max", "Mustermann", "01.01.1999", "max.mustermann@gmx.de");
        Room room = Room.createNewRoom(csvAdapter, 5);
        Shelf shelf = Shelf.createNewShelf(csvAdapter, room, "Action", 400, 1);
        Book book = Book.createNewBook(csvAdapter, "Welt", "Peter Hans", "Natur", shelf, 100, 3);
        visitor.addBookToReturn(book);
        visitor.removeBookToReturn(book);
        assertThat(book).isNotIn(visitor.getBooksToReturn());
    }
}
