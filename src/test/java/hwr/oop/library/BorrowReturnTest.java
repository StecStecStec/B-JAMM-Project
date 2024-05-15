package hwr.oop.library;

import hwr.oop.library.domain.Book;
import hwr.oop.library.domain.Room;
import hwr.oop.library.domain.Shelf;
import hwr.oop.library.domain.Visitor;
import hwr.oop.library.persistance.CSVAdapter;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

class BorrowReturnTest {
    @Test
    void borrowBook_checkIfBorrowedByIsSetToGivenVisitorAndShelfIsNull() {
        String path = "/csvTestFiles/";
        InputStream stream = getClass().getResourceAsStream(path);
        assert stream != null;
        CSVAdapter csvAdapter = new CSVAdapter(stream.toString());

        Room room = Room.createNewRoom(csvAdapter, 5);
        Shelf shelf = Shelf.createNewShelf(csvAdapter, room, "Action", 400, 1);
        Book book = Book.createNewBook(csvAdapter, "Welt", "Peter Hans", "Natur", shelf, 100, 3);
        Visitor visitor = Visitor.createNewVisitor(csvAdapter, "Max", "Mustermann", "01.01.1999", "max.mustermann@gmx.de");
        book.borrow(visitor);
        assertThat(book.getBorrowedBy()).isEqualTo(visitor);
        assertThat(book).isIn(visitor.getBorrowedBooks());
        assertThat(book.getShelf()).isNull();
        assertThat(book).isNotIn(shelf.getBooksOnShelf());
        assertThat(shelf.getRemainingSpace()).isEqualTo(400);
    }

    @Test
    void borrowBookFails_checkIfBorrowedBookIsNotBorrowable() {
        String path = "/csvTestFiles/";
        InputStream stream = getClass().getResourceAsStream(path);
        assert stream != null;
        CSVAdapter csvAdapter = new CSVAdapter(stream.toString());

        Room room = Room.createNewRoom(csvAdapter, 5);
        Shelf shelf = Shelf.createNewShelf(csvAdapter, room, "Action", 400, 1);
        Book book = Book.createNewBook(csvAdapter, "Welt", "Peter Hans", "Natur", shelf, 100, 3);
        Visitor visitor1 = Visitor.createNewVisitor(csvAdapter, "Max", "Mustermann", "01.01.1999", "max.mustermann@gmx.de");
        Visitor visitor2 = Visitor.createNewVisitor(csvAdapter, "Maxa", "Mustermanna", "02.01.1999", "maxa.mustermanna@gmx.de");
        book.borrow(visitor1);
        book.borrow(visitor2);
        assertThat(book.getBorrowedBy()).isNotEqualTo(visitor2);
        assertThat(book).isNotIn(visitor2.getBorrowedBooks());
    }

    @Test
    void returnBook_checkIfShelfIsSetToGivenShelfAndBorrowedByIsNull() {
        String path = "/csvTestFiles/";
        InputStream stream = getClass().getResourceAsStream(path);
        assert stream != null;
        CSVAdapter csvAdapter = new CSVAdapter(stream.toString());

        Room room = Room.createNewRoom(csvAdapter, 5);
        Shelf shelf = Shelf.createNewShelf(csvAdapter, room, "Action", 400, 1);
        Book book = Book.createNewBook(csvAdapter, "Welt", "Peter Hans", "Natur", shelf, 100, 3);
        Visitor visitor = Visitor.createNewVisitor(csvAdapter, "Max", "Mustermann", "01.01.1999", "max.mustermann@gmx.de");
        book.borrow(visitor);
        book.returnBook(shelf);
        assertThat(book.getBorrowedBy()).isNull();
        assertThat(book).isNotIn(visitor.getBorrowedBooks());
        assertThat(book.getShelf()).isEqualTo(shelf);
        assertThat(book).isIn(shelf.getBooksOnShelf());
        assertThat(shelf.getRemainingSpace()).isEqualTo(397);
    }

    @Test
    void returnBookFails_checkExceptionRaise() {
        String path = "/csvTestFiles/";
        InputStream stream = getClass().getResourceAsStream(path);
        assert stream != null;
        CSVAdapter csvAdapter = new CSVAdapter(stream.toString());

        Room room = Room.createNewRoom(csvAdapter, 5);
        Shelf shelf1 = Shelf.createNewShelf(csvAdapter, room, "Action", 400, 1);
        Shelf shelf2 = Shelf.createNewShelf(csvAdapter, room, "Action", 2, 1);
        Book book = Book.createNewBook(csvAdapter, "Welt", "Peter Hans", "Natur", shelf1, 100, 3);
        Visitor visitor = Visitor.createNewVisitor(csvAdapter, "Max", "Mustermann", "01.01.1999", "max.mustermann@gmx.de");
        book.borrow(visitor);
        Assertions.assertThatThrownBy(() -> book.returnBook(shelf2)).hasMessage("Added book to shelf with not enough space.");
    }
}
