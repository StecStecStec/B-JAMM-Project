package hwr.oop;

import org.assertj.core.api.ObjectAssert;
import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;
import java.util.UUID;

class BookTest {
    @Test
    void createBook_checkRightAttributes() {
        Room room = Room.createNewRoom(5);
        Shelf shelf = Shelf.createNewShelf(room, "Action", 400, 1);
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
        Room room = Room.createNewRoom(5);
        Shelf shelf = Shelf.createNewShelf(room, "Action", 2, 1);
        Assertions.assertThatThrownBy(() -> Book.createNewBook("Welt", "Peter Hans", "Natur", shelf, 100, 3)).hasMessage("Added book to shelf with not enough space.");
    }

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

    @Test
    void testEqualsMethod() {

        UUID uuid = UUID.randomUUID();

        Room room = Room.createNewRoom(5);
        Shelf shelf = Shelf.createNewShelf(room, "Action", 400,1);
        Book book1 = Book.createCompleteBook(uuid,"Welt", "Peter Hans", "Natur", shelf, 100, 3);
        Book book2 = Book.createCompleteBook(uuid,"Welt", "Peter Hans", "Natur", shelf, 100, 3);
        Book book3 = Book.createNewBook("Planet", "Max Mustermann", "SciFi", shelf, 80, 3);

        // Ensure equals method works correctly for different visitors
        Assertions.assertThat(book1).isNotEqualTo(book3);

        // Ensure equals method works correctly after setting same attributes
        Assertions.assertThat(book1.equals(book2)).isTrue();

        // Ensure equals method returns false when comparing with null
        Assertions.assertThat(book1).isNotNull();
    }

    @Test
    void testHashCodeMethod()   {
        Room room = Room.createNewRoom(5);
        Shelf shelf = Shelf.createNewShelf(room, "Action", 400,1);

        UUID uuid = UUID.randomUUID();
        Book book1 = Book.createCompleteBook(uuid,"Welt", "Peter Hans", "Natur", shelf, 100, 3);
        Book book2 = Book.createCompleteBook(uuid,"Welt", "Peter Hans", "Natur", shelf, 100, 3);
        Book book3 = Book.createNewBook("Planet", "Max Mustermann", "SciFi", shelf, 80, 3);


        Assertions.assertThat(book1).hasSameHashCodeAs(book2);

        Assertions.assertThat(book1.hashCode()).isNotEqualTo(book3.hashCode());

    }

    @Test
    void setBookCondition_checkThatTheBookConditionIsSetCorrectly() {
        Room room = Room.createNewRoom(5);
        Shelf shelf = Shelf.createNewShelf(room, "Action", 400,1);
        Book book1 = Book.createNewBook("Welt", "Peter Hans", "Natur", shelf, 100, 3);

        Assertions.assertThat(book1.getBookCondition()).isEqualTo(100);


    }

    @Test
    void testBook_testEqualsMethod() {
        UUID uuid = UUID.randomUUID();
        Room room = Room.createNewRoom(5);
        Shelf shelf = Shelf.createNewShelf(room, "Action", 400,1);

        Book book1 = Book.createCompleteBook(uuid, "Welt", "Peter Hans", "Natur", shelf, 100, 3);
        Book book3 = Book.createNewBook("Welt", "Peter Hans", "Natur", shelf, 100, 3);

        Assertions.assertThat(book1)
                //Comparison with null should be return false
                .isNotNull()
                //Comparison with an object of another class should be return false
                .isNotEqualTo(Shelf.createNewShelf(room, "Action", 400, 1));


        Assertions.assertThat(book1).isNotEqualTo(book3);
    }

}
