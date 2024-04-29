package hwr.oop.library;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;


class AddRemoveBookVisitorTest {
    @Test
    void addBorrowedBook_checkIfBookAdded() {
        Visitor visitor = Visitor.createCompleteVisitor("Max","Max", "Mustermann", "01.01.1999", "max.mustermann@gmx.de", UUID.randomUUID());
        Room room = Room.createNewRoom(5);
        Shelf shelf = Shelf.createNewShelf(room, "Action", 400, 1);
        Book book = Book.createNewBook("Welt", "Peter Hans", "Natur", shelf, 100, 3);
        visitor.addBorrowedBook(book);
        Assertions.assertThat(book).isIn(visitor.getBorrowedBooks());
    }

    @Test
    void removeBorrowedBook_checkIfBookRemoved() {
        Visitor visitor = Visitor.createCompleteVisitor("Max","Max", "Mustermann", "01.01.1999", "max.mustermann@gmx.de", UUID.randomUUID());
        Room room = Room.createNewRoom(5);
        Shelf shelf = Shelf.createNewShelf(room, "Action", 400, 1);
        Book book = Book.createNewBook("Welt", "Peter Hans", "Natur", shelf, 100, 3);
        visitor.addBorrowedBook(book);
        visitor.removeBorrowedBook(book);
        Assertions.assertThat(book).isNotIn(visitor.getBorrowedBooks());
    }

    @Test
    void addBookToReturn_checkIfBookAdded() {
        UUID uuid = UUID.randomUUID();
        Visitor visitor = Visitor.createCompleteVisitor("Max","Max", "Mustermann", "01.01.1999", "max.mustermann@gmx.de", uuid);
        Room room = Room.createNewRoom(5);
        Shelf shelf = Shelf.createNewShelf(room, "Action", 400, 1);
        Book book = Book.createNewBook("Welt", "Peter Hans", "Natur", shelf, 100, 3);
        visitor.addBookToReturn(book);
        Assertions.assertThat(book).isIn(visitor.getBooksToReturn());
    }

    @Test
    void removeBookToReturn_checkIfBookRemoved() {
        Visitor visitor = Visitor.createNewVisitor("Max","Max", "Mustermann", "01.01.1999", "max.mustermann@gmx.de");
        Room room = Room.createNewRoom(5);
        Shelf shelf = Shelf.createNewShelf(room, "Action", 400, 1);
        Book book = Book.createNewBook("Welt", "Peter Hans", "Natur", shelf, 100, 3);
        visitor.addBookToReturn(book);
        visitor.removeBookToReturn(book);
        Assertions.assertThat(book).isNotIn(visitor.getBooksToReturn());
    }
}
