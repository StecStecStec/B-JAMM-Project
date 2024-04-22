package hwr.oop.library;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class BorrowReturnTest {
    @Test
    void borrowBook_checkIfBorrowedByIsSetToGivenVisitorAndShelfIsNull() {
        Room room = Room.createNewRoom(5);
        Shelf shelf = Shelf.createNewShelf(room, "Action", 400, 1);
        Book book = Book.createNewBook("Welt", "Peter Hans", "Natur", shelf, 100, 3);
        Visitor visitor = Visitor.createNewVisitor("Max", "Mustermann", "01.01.1999", "max.mustermann@gmx.de");
        book.borrow(visitor);
        Assertions.assertThat(book.getBorrowedBy()).isEqualTo(visitor);
        Assertions.assertThat(book).isIn(visitor.getBorrowedBooks());
        Assertions.assertThat(book.getShelf()).isNull();
        Assertions.assertThat(book).isNotIn(shelf.getBooksOnShelf());
        Assertions.assertThat(shelf.getRemainingSpace()).isEqualTo(400);
    }

    @Test
    void borrowBookFails_checkIfBorrowedBookIsNotBorrowable() {
        Room room = Room.createNewRoom(5);
        Shelf shelf = Shelf.createNewShelf(room, "Action", 400, 1);
        Book book = Book.createNewBook("Welt", "Peter Hans", "Natur", shelf, 100, 3);
        Visitor visitor1 = Visitor.createNewVisitor("Max", "Mustermann", "01.01.1999", "max.mustermann@gmx.de");
        Visitor visitor2 = Visitor.createNewVisitor("Maxa", "Mustermanna", "02.01.1999", "maxa.mustermanna@gmx.de");
        book.borrow(visitor1);
        book.borrow(visitor2);
        Assertions.assertThat(book.getBorrowedBy()).isNotEqualTo(visitor2);
        Assertions.assertThat(book).isNotIn(visitor2.getBorrowedBooks());
    }

    @Test
    void returnBook_checkIfShelfIsSetToGivenShelfAndBorrowedByIsNull() {
        Room room = Room.createNewRoom(5);
        Shelf shelf = Shelf.createNewShelf(room, "Action", 400, 1);
        Book book = Book.createNewBook("Welt", "Peter Hans", "Natur", shelf, 100, 3);
        Visitor visitor = Visitor.createNewVisitor("Max", "Mustermann", "01.01.1999", "max.mustermann@gmx.de");
        book.borrow(visitor);
        book.returnBook(shelf);
        Assertions.assertThat(book.getBorrowedBy()).isNull();
        Assertions.assertThat(book).isNotIn(visitor.getBorrowedBooks());
        Assertions.assertThat(book.getShelf()).isEqualTo(shelf);
        Assertions.assertThat(book).isIn(shelf.getBooksOnShelf());
        Assertions.assertThat(shelf.getRemainingSpace()).isEqualTo(397);
    }

    @Test
    void returnBookFails_checkExceptionRaise() {
        Room room = Room.createNewRoom(5);
        Shelf shelf1 = Shelf.createNewShelf(room, "Action", 400, 1);
        Shelf shelf2 = Shelf.createNewShelf(room, "Action", 2, 1);
        Book book = Book.createNewBook("Welt", "Peter Hans", "Natur", shelf1, 100, 3);
        Visitor visitor = Visitor.createNewVisitor("Max", "Mustermann", "01.01.1999", "max.mustermann@gmx.de");
        book.borrow(visitor);
        Assertions.assertThatThrownBy(() -> book.returnBook(shelf2)).hasMessage("Added book to shelf with not enough space.");
    }
}
