package hwr.oop;

import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;

public class BookTest {
    @Test
    void createBook_checkRightAssignment(){
        Shelf shelf = new Shelf();
        Book book = new Book("Welt", "Peter Hans", "Natur", shelf, 100);
        Assertions.assertThat(book.getName()).isEqualTo("Welt");
        Assertions.assertThat(book.getAuthor()).isEqualTo("Peter Hans");
        Assertions.assertThat(book.getTopic()).isEqualTo("Natur");
        Assertions.assertThat(book.getShelf()).isEqualTo(shelf);
        Assertions.assertThat(book.getBookCondition()).isEqualTo(100);
    }
    @Test
    void borrowBook_checkIfBorrowedByIsSetToGivenVisitorAndShelfIsNull(){
        Shelf shelf = new Shelf();
        Book book = new Book("Welt", "Peter Hans", "Natur", shelf, 100);
        Visitor visitor = new Visitor();
        book.borrow(visitor);
        Assertions.assertThat(book.getBorrowedBy()).isEqualTo(Visitor);
        Assertions.assertThat(book.getShelf()).isEqualTo(null);
    }
    @Test
    void returnBook_checkIfShelfIsSetToGivenShelfAndBorrowedByNone(){
        Shelf shelf = new Shelf();
        Book book = new Book("Welt", "Peter Hans", "Natur", shelf, 100);
        Visitor visitor = new Visitor();
        book.borrow(visitor);
        book.returnBook(shelf);
        Assertions.assertThat(book.getBorrowedBy()).isEqualTo(null);
        Assertions.assertThat(book.getShelf()).isEqualTo(shelf);
    }
}
