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
        Visitor visitor = new Visitor("Max", "Mustermann", "01.01.1999", "max.mustermann@gmx.de");
        book.borrow(visitor);
        Assertions.assertThat(book.getBorrowedBy()).isEqualTo(visitor);
        Assertions.assertThat(book).isIn(book.getBorrowedBy().getBorrowedBooks());
        Assertions.assertThat(book.getShelf()).isEqualTo(null);
        Assertions.assertThat(book).isNotIn(book.getShelf().getBooksOnShelf());
    }
    @Test
    void returnBook_checkIfShelfIsSetToGivenShelfAndBorrowedByIsNull(){
        Room room = new Room();
        Shelf shelf = new Shelf(room, "Action", 400,1);
        Book book = new Book("Welt", "Peter Hans", "Natur", shelf, 100);
        Visitor visitor = new Visitor("Max", "Mustermann", "01.01.1999", "max.mustermann@gmx.de");
        book.borrow(visitor);
        book.returnBook(shelf);
        Assertions.assertThat(book.getBorrowedBy()).isEqualTo(null);
        Assertions.assertThat(book).isNotIn(book.getBorrowedBy().getBorrowedBooks());
        Assertions.assertThat(book.getShelf()).isEqualTo(shelf);
        Assertions.assertThat(book).isIn(book.getShelf().getBooksOnShelf());
    }
}
