package hwr.oop;

import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;

public class BookTest {
    @Test
    void createBook_checkRightAttributes(){
        Room room = new Room();
        Shelf shelf = new Shelf(room, "Action", 400,1);
        Book book = new Book("Welt", "Peter Hans", "Natur", shelf, 100);
        Assertions.assertThat(book.getBookID()).isNotNull();
        Assertions.assertThat(book.getBookTitle()).isEqualTo("Welt");
        Assertions.assertThat(book.getBookAuthor()).isEqualTo("Peter Hans");
        Assertions.assertThat(book.getBookGenre()).isEqualTo("Natur");
        Assertions.assertThat(book.getShelf()).isEqualTo(shelf);
        Assertions.assertThat(book.getBookCondition()).isEqualTo(100);
    }
    @Test
    void borrowBook_checkIfBorrowedByIsSetToGivenVisitorAndShelfIsNull(){
        Room room = new Room();
        Shelf shelf = new Shelf(room, "Action", 400,1);
        Book book = new Book("Welt", "Peter Hans", "Natur", shelf, 100);
        Visitor visitor = new Visitor();
        book.borrow(visitor);
        Assertions.assertThat(book.getBorrowedBy()).isEqualTo(Visitor);
        Assertions.assertThat(book.getShelf()).isEqualTo(null);
    }
    @Test
    void returnBook_checkIfShelfIsSetToGivenShelfAndBorrowedByNone(){
        Room room = new Room();
        Shelf shelf = new Shelf(room, "Action", 400,1);
        Book book = new Book("Welt", "Peter Hans", "Natur", shelf, 100);
        Visitor visitor = new Visitor();
        book.borrow(visitor);
        book.returnBook(shelf);
        Assertions.assertThat(book.getBorrowedBy()).isEqualTo(null);
        Assertions.assertThat(book.getShelf()).isEqualTo(shelf);
    }
}
