package hwr.oop;

import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;

import java.util.UUID;
import java.util.ArrayList;
import java.util.List;

public class BookTest {
    @Test
    void createBook_checkRightAttributes() {
        Room room = new Room();
        Shelf shelf = new Shelf(room, "Action", 400, 1);
        Book book = new Book("Welt", "Peter Hans", "Natur", shelf, 100);
        Assertions.assertThat(book.getBookID()).isNotNull();
        Assertions.assertThat(book.getBookTitle()).isEqualTo("Welt");
        Assertions.assertThat(book.getBookAuthor()).isEqualTo("Peter Hans");
        Assertions.assertThat(book.getBookGenre()).isEqualTo("Natur");
        Assertions.assertThat(book.getShelf()).isEqualTo(shelf);
        Assertions.assertThat(book.getBookCondition()).isEqualTo(100);
    }

    @Test
    void borrowBook_checkIfBorrowedByIsSetToGivenVisitorAndShelfIsNull() {
        Room room = new Room();
        Shelf shelf = new Shelf(room, "Action", 400, 1);
        Book book = new Book("Welt", "Peter Hans", "Natur", shelf, 100);
        Visitor visitor = new Visitor("Max", "Mustermann", "01.01.1999", "max.mustermann@gmx.de");
        book.borrow(visitor);
        Assertions.assertThat(book.getBorrowedBy()).isEqualTo(visitor);
        Assertions.assertThat(book).isIn(visitor.getBorrowedBooks());
        Assertions.assertThat(book.getShelf()).isNull();
        Assertions.assertThat(book).isNotIn(shelf.getBooksOnShelf());
    }

    @Test
    void returnBook_checkIfShelfIsSetToGivenShelfAndBorrowedByIsNull() {
        Room room = new Room();
        Shelf shelf = new Shelf(room, "Action", 400, 1);
        Book book = new Book("Welt", "Peter Hans", "Natur", shelf, 100);
        Visitor visitor = new Visitor("Max", "Mustermann", "01.01.1999", "max.mustermann@gmx.de");
        book.borrow(visitor);
        book.returnBook(shelf);
        Assertions.assertThat(book.getBorrowedBy()).isNull();
        Assertions.assertThat(book).isNotIn(visitor.getBorrowedBooks());
        Assertions.assertThat(book.getShelf()).isEqualTo(shelf);
        Assertions.assertThat(book).isIn(shelf.getBooksOnShelf());
    }

    @Test
    void testEqualsMethod() {
        Room room1 = new Room();
        Room room2 = new Room();
        Room room3 = new Room();

        //Comparison with null should be return false
        Assertions.assertThat(room1).isNotEqualTo(null);

        //Comparison with an object of another class should be return false
        Assertions.assertThat(room1).isNotEqualTo(new Shelf(room1, "Action", 400, 1));

        List<Shelf> shelfList = new ArrayList<>();
        shelfList.add(new Shelf(room3, "Fiction", 200, 1));
        shelfList.add(new Shelf(room3, "Science", 100, 2));
        room3.setShelfList(shelfList);
        Assertions.assertThat(room3.getShelfList()).isEqualTo(shelfList);

        Assertions.assertThat(room1.equals(room2)).isFalse();

        UUID roomID = room1.getRoomID();
        List<Shelf> shelfList2 = room1.getShelfList();
        room2.setRoomID(roomID);
        room2.setShelfList(shelfList2);
        Assertions.assertThat(room1.equals(room2)).isTrue();
    }

    @Test
    void testHashCodeMethod() {
        Room room1 = new Room();
        Room room2 = new Room();

        Assertions.assertThat(room1.hashCode()).isNotEqualTo(room2.hashCode());

        UUID roomID = room1.getRoomID();
        List<Shelf> shelfList2 = room1.getShelfList();
        room2.setRoomID(roomID);
        room2.setShelfList(shelfList2);
        Assertions.assertThat(room1).hasSameHashCodeAs(room2);
    }

}
