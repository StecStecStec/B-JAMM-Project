package hwr.oop;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class VisitorTest{
    @Test
    void createVisitor_checkRightAssignment() {
        Visitor visitor = new Visitor("Max", "Mustermann", "01.01.1999", "max.mustermann@gmx.de");
        Assertions.assertThat(visitor.getVisitorName()).isEqualTo("Max");
        Assertions.assertThat(visitor.getVisitorSurname()).isEqualTo("Mustermann");
        Assertions.assertThat(visitor.getVisitorBirthday()).isEqualTo("01.01.1999");
        Assertions.assertThat(visitor.getVisitorEmailAddress()).isEqualTo("max.mustermann@gmx.de");
    }

    @Test
    void addBorrowedBook_checkIfBookAdded() {
        Visitor visitor = new Visitor("Max", "Mustermann", "01.01.1999", "max.mustermann@gmx.de");
        Room room = new Room();
        Shelf shelf = new Shelf(room, "Action", 400,1);
        Book book = new Book("Welt", "Peter Hans", "Natur", shelf, 100);
        visitor.addBorrowedBook(book);
        Assertions.assertThat(book).isIn(visitor.getBorrowedBooks());
    }

    @Test
    void removeBorrowedBook_checkIfBookRemoved() {
        Visitor visitor = new Visitor("Max", "Mustermann", "01.01.1999", "max.mustermann@gmx.de");
        Room room = new Room();
        Shelf shelf = new Shelf(room, "Action", 400,1);
        Book book = new Book("Welt", "Peter Hans", "Natur", shelf, 100);
        visitor.addBorrowedBook(book);
        visitor.removeBorrowedBook(book);
        Assertions.assertThat(book).isNotIn(visitor.getBorrowedBooks());
    }

    @Test
    void addBookToReturn_checkIfBookAdded() {
        Visitor visitor = new Visitor("Max", "Mustermann", "01.01.1999", "max.mustermann@gmx.de");
        Room room = new Room();
        Shelf shelf = new Shelf(room, "Action", 400,1);
        Book book = new Book("Welt", "Peter Hans", "Natur", shelf, 100);
        visitor.addBookToReturn(book);
        Assertions.assertThat(book).isIn(visitor.getBooksToReturn());
    }

    @Test
    void removeBookToReturn_checkIfBookRemoved() {
        Visitor visitor = new Visitor("Max", "Mustermann", "01.01.1999", "max.mustermann@gmx.de");
        Room room = new Room();
        Shelf shelf = new Shelf(room, "Action", 400,1);
        Book book = new Book("Welt", "Peter Hans", "Natur", shelf, 100);
        visitor.addBookToReturn(book);
        visitor.removeBookToReturn(book);
        Assertions.assertThat(book).isNotIn(visitor.getBooksToReturn());
    }
}
