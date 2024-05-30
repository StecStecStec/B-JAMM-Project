package hwr.oop.library;

import hwr.oop.library.domain.Book;
import hwr.oop.library.domain.Room;
import hwr.oop.library.domain.Shelf;
import hwr.oop.library.domain.Visitor;
import hwr.oop.library.domain.Library;
import hwr.oop.library.persistence.CSVAdapter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URL;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


class AddRemoveBookVisitorTest {

    private Library library = Library.createNewLibrary();
    private CSVAdapter csvAdapter;
    @BeforeEach
    void setUp() {
        csvAdapter = new CSVAdapter(".\\src\\test\\resources\\csvTestFiles\\");
    }

    @Test
    void addBorrowedBook_checkIfBookAdded() {
        Visitor visitor = Visitor.createCompleteVisitor(library, "Max", "Mustermann", "01.01.1999", "max.mustermann@gmx.de", UUID.randomUUID());
        Room room = Room.createNewRoom(library, 5);
        Shelf shelf = Shelf.createNewShelf(library, room, "Action", 400, 1);
        Book book = Book.createNewBook(library, "Welt", "Peter Hans", "Natur", shelf, 100, 3);
        visitor.addBorrowedBook(book);
        assertThat(book).isIn(visitor.getBorrowedBooks());
        library.deleteVisitor(visitor);
        library.deleteRoom(room);
        library.deleteShelf(shelf);
        library.deleteBook(book);
    }

    @Test
    void removeBorrowedBook_checkIfBookRemoved() {
        Visitor visitor = Visitor.createCompleteVisitor(library, "Max", "Mustermann", "01.01.1999", "max.mustermann@gmx.de", UUID.randomUUID());
        Room room = Room.createNewRoom(library, 5);
        Shelf shelf = Shelf.createNewShelf(library, room, "Action", 400, 1);
        Book book = Book.createNewBook(library, "Welt", "Peter Hans", "Natur", shelf, 100, 3);
        visitor.addBorrowedBook(book);
        visitor.removeBorrowedBook(book);
        assertThat(book).isNotIn(visitor.getBorrowedBooks());
        library.deleteVisitor(visitor);
        library.deleteRoom(room);
        library.deleteShelf(shelf);
        library.deleteBook(book);
    }

    @Test
    void addBookToReturn_checkIfBookAdded() {
        Visitor visitor = Visitor.createCompleteVisitor(library, "Max", "Mustermann", "01.01.1999", "max.mustermann@gmx.de", UUID.randomUUID());
        Room room = Room.createNewRoom(library, 5);
        Shelf shelf = Shelf.createNewShelf(library, room, "Action", 400, 1);
        Book book = Book.createNewBook(library, "Welt", "Peter Hans", "Natur", shelf, 100, 3);
        visitor.addBookToReturn(book);
        assertThat(book).isIn(visitor.getBooksToReturn());
        library.deleteVisitor(visitor);
        library.deleteRoom(room);
        library.deleteShelf(shelf);
        library.deleteBook(book);
    }

    @Test
    void removeBookToReturn_checkIfBookRemoved() {
        Visitor visitor = Visitor.createNewVisitor(library, "Max", "Mustermann", "01.01.1999", "max.mustermann@gmx.de");
        Room room = Room.createNewRoom(library, 5);
        Shelf shelf = Shelf.createNewShelf(library, room, "Action", 400, 1);
        Book book = Book.createNewBook(library, "Welt", "Peter Hans", "Natur", shelf, 100, 3);
        visitor.addBookToReturn(book);
        visitor.removeBookToReturn(book);
        assertThat(book).isNotIn(visitor.getBooksToReturn());
        library.deleteVisitor(visitor);
        library.deleteRoom(room);
        library.deleteShelf(shelf);
        library.deleteBook(book);
    }

    @AfterEach
    void tearDown() {
        csvAdapter.saveLibrary(library);
    }
}
