package hwr.oop.library;

import hwr.oop.library.domain.*;
import hwr.oop.library.persistence.CSVAdapter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BorrowReturnTest {

    private Library library;
    private CSVAdapter csvAdapter;
    @BeforeEach
    void setUp() {
        URL resourceUrl = getClass().getClassLoader().getResource("csvTestFiles");
        assert resourceUrl != null;
        File directory = new File(resourceUrl.getFile());
        String path = directory.getAbsolutePath() + "/";
        csvAdapter = new CSVAdapter(path);
        library = csvAdapter.loadLibrary();
    }

    @Test
    void borrowBook_checkIfBorrowedByIsSetToGivenVisitorAndShelfIsNull() {
        Room room = Room.createNewRoom(library, 5);
        library.addRoom(room);
        Shelf shelf = Shelf.createNewShelf(library, room, "Action", 400, 1);
        library.addShelf(shelf);
        Book book = Book.createNewBook(library, "Welt", "Peter Hans", "Natur", shelf, 100, 3);
        library.addBook(book);
        Visitor visitor = Visitor.createNewVisitor(library, "Max", "Mustermann", "01.01.1999", "max.mustermann@gmx.de");
        library.addVisitor(visitor);
        book.borrow(visitor);
        assertThat(book.getBorrowedBy()).isEqualTo(visitor);
        assertThat(book).isIn(visitor.getBorrowedBooks());
        assertThat(book.getShelf()).isNull();
        assertThat(book).isNotIn(shelf.getBooksOnShelf());
        assertThat(shelf.getRemainingSpace()).isEqualTo(400);
    }

    @Test
    void borrowBookFails_checkIfBorrowedBookIsNotBorrowable() {
        Room room = Room.createNewRoom(library, 5);
        library.addRoom(room);
        Shelf shelf = Shelf.createNewShelf(library, room, "Action", 400, 1);
        library.addShelf(shelf);
        Book book = Book.createNewBook(library, "Welt", "Peter Hans", "Natur", shelf, 100, 3);
        library.addBook(book);
        Visitor visitor1 = Visitor.createNewVisitor(library, "Max", "Mustermann", "01.01.1999", "max.mustermann@gmx.de");
        library.addVisitor(visitor1);
        Visitor visitor2 = Visitor.createNewVisitor(library, "Maxa", "Mustermanna", "02.01.1999", "maxa.mustermanna@gmx.de");
        library.addVisitor(visitor2);
        book.borrow(visitor1);
        book.borrow(visitor2);
        assertThat(book.getBorrowedBy()).isNotEqualTo(visitor2);
        assertThat(book).isNotIn(visitor2.getBorrowedBooks());
    }

    @Test
    void returnBook_checkIfShelfIsSetToGivenShelfAndBorrowedByIsNull() {
        Room room = Room.createNewRoom(library, 5);
        library.addRoom(room);
        Shelf shelf = Shelf.createNewShelf(library, room, "Action", 400, 1);
        library.addShelf(shelf);
        Book book = Book.createNewBook(library, "Welt", "Peter Hans", "Natur", shelf, 100, 3);
        library.addBook(book);
        Visitor visitor = Visitor.createNewVisitor(library, "Max", "Mustermann", "01.01.1999", "max.mustermann@gmx.de");
        library.addVisitor(visitor);
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
        Room room = Room.createNewRoom(library, 5);
        library.addRoom(room);
        Shelf shelf1 = Shelf.createNewShelf(library, room, "Action", 400, 1);
        library.addShelf(shelf1);
        Shelf shelf2 = Shelf.createNewShelf(library, room, "Action", 2, 1);
        library.addShelf(shelf2);
        Book book = Book.createNewBook(library, "Welt", "Peter Hans", "Natur", shelf1, 100, 3);
        library.addBook(book);
        Visitor visitor = Visitor.createNewVisitor(library, "Max", "Mustermann", "01.01.1999", "max.mustermann@gmx.de");
        library.addVisitor(visitor);
        book.borrow(visitor);
        assertThatThrownBy(() -> book.returnBook(shelf2)).hasMessage("Added book to shelf with not enough space.");
    }

    @AfterEach
    void tearDown() {
        csvAdapter.saveLibrary(library);
    }
}
