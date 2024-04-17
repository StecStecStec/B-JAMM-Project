package hwr.oop;

import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;
import java.util.UUID;

public class BookTest {
    @Test
    void createBook_checkRightAttributes() {
        Room room = Room.createNewRoom();
        Shelf shelf = new Shelf(room, "Action", 400, 1);
        Book book = Book.createNewBook("Welt", "Peter Hans", "Natur", shelf, 100, 3);
        Assertions.assertThat(book.getBookID()).isNotNull();
        Assertions.assertThat(book.getBookTitle()).isEqualTo("Welt");
        Assertions.assertThat(book.getBookAuthor()).isEqualTo("Peter Hans");
        Assertions.assertThat(book.getBookGenre()).isEqualTo("Natur");
        Assertions.assertThat(book.getShelf()).isEqualTo(shelf);
        Assertions.assertThat(book.getBookCondition()).isEqualTo(100);
        Assertions.assertThat(book.getBookWidth()).isEqualTo(3);
        Assertions.assertThat(book).isIn(shelf.getBooksOnShelf());
        Assertions.assertThat(shelf.getRemainingSpace()).isEqualTo(397);
    }

    @Test
    void createBookFails_checkExceptionRaise() {
        Room room = new Room();
        Shelf shelf = new Shelf(room, "Action", 2, 1);
        Assertions.assertThatThrownBy(() -> new Book("Welt", "Peter Hans", "Natur", shelf, 100, 3)).hasMessage("Added book to shelf with not enough space.");
    }

    @Test
    void borrowBook_checkIfBorrowedByIsSetToGivenVisitorAndShelfIsNull() {
        Room room = new Room();
        Shelf shelf = new Shelf(room, "Action", 400, 1);
        Book book = new Book("Welt", "Peter Hans", "Natur", shelf, 100, 3);
        Visitor visitor = new Visitor("Max", "Mustermann", "01.01.1999", "max.mustermann@gmx.de");
        book.borrow(visitor);
        Assertions.assertThat(book.getBorrowedBy()).isEqualTo(visitor);
        Assertions.assertThat(book).isIn(visitor.getBorrowedBooks());
        Assertions.assertThat(book.getShelf()).isNull();
        Assertions.assertThat(book).isNotIn(shelf.getBooksOnShelf());
        Assertions.assertThat(shelf.getRemainingSpace()).isEqualTo(400);
    }

    @Test
    void returnBook_checkIfShelfIsSetToGivenShelfAndBorrowedByIsNull() {
        Room room = new Room();
        Shelf shelf = new Shelf(room, "Action", 400, 1);
        Book book = new Book("Welt", "Peter Hans", "Natur", shelf, 100, 3);
        Visitor visitor = new Visitor("Max", "Mustermann", "01.01.1999", "max.mustermann@gmx.de");
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
        Room room = new Room();
        Shelf shelf1 = new Shelf(room, "Action", 400, 1);
        Shelf shelf2 = new Shelf(room, "Action", 2, 1);
        Book book = new Book("Welt", "Peter Hans", "Natur", shelf1, 100, 3);
        Visitor visitor = new Visitor("Max", "Mustermann", "01.01.1999", "max.mustermann@gmx.de");
        book.borrow(visitor);
        Assertions.assertThatThrownBy(() -> book.returnBook(shelf2)).hasMessage("Added book to shelf with not enough space.");
    }

    @Test
    void testEqualsMethod() {
        Room room = new Room();
        Shelf shelf = new Shelf(room, "Action", 400,1);
        Book book1 = new Book("Welt", "Peter Hans", "Natur", shelf, 100, 3);
        Book book2 = new Book("Welt", "Peter Hans", "Natur", shelf, 95, 3);
        Book book3 = new Book("Welt", "Peter Hans", "Natur", shelf, 100, 3);

        //Comparison with null should return false
        Assertions.assertThat(book1).isNotNull();

        //Comparison with an object of another class should return false
        Assertions.assertThat(book1).isNotEqualTo(shelf);

        Assertions.assertThat(book1.equals(book2)).isFalse();

        UUID bookID = book1.getBookID();
        book3.setBookID(bookID);
        Assertions.assertThat(book1.equals(book3)).isTrue();
    }

    @Test
    void testHashCodeMethod()   {
        Room room = new Room();
        Shelf shelf = new Shelf(room, "Action", 400,1);
        Book book1 = new Book("Welt", "Peter Hans", "Natur", shelf, 100, 3);
        Book book2 = new Book("Welt", "Peter Hans", "Natur", shelf, 100, 3);

        Assertions.assertThat(book1.hashCode()).isNotEqualTo(book2.hashCode());

        UUID bookID = book1.getBookID();
        book2.setBookID(bookID);

        Assertions.assertThat(book1.getShelf()).hasSameHashCodeAs(book2.getShelf());
    }

    @Test
    void setBookCondition_checkThatTheBookConditionIsSetCorrectly() {
        Room room = new Room();
        Shelf shelf = new Shelf(room, "Action", 400,1);
        Book book1 = new Book("Welt", "Peter Hans", "Natur", shelf, 100, 3);

        Assertions.assertThat(book1.getBookCondition()).isEqualTo(100);

        book1.setBookCondition(95);

        Assertions.assertThat(book1.getBookCondition()).isEqualTo(95);
    }
}
