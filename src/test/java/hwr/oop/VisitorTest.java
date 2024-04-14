package hwr.oop;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


class VisitorTest{
    // Test case for ensuring correct initialization of Visitor attributes
    @Test
    void createVisitor_checkRightAssignment() {
        // Create a Visitor object with specific attributes
        Visitor visitor = new Visitor("Max", "Mustermann", "01.01.1999", "max.mustermann@gmx.de");

        // Assert that the attributes are assigned correctly
        Assertions.assertThat(visitor.getVisitorName()).isEqualTo("Max");
        Assertions.assertThat(visitor.getVisitorSurname()).isEqualTo("Mustermann");
        Assertions.assertThat(visitor.getVisitorBirthday()).isEqualTo("01.01.1999");
        Assertions.assertThat(visitor.getVisitorEmailAddress()).isEqualTo("max.mustermann@gmx.de");
    }

    // Test case for adding a borrowed book to a Visitor's list of borrowed books
    @Test
    void addBorrowedBook_checkIfBookAdded() {
        // Create a Visitor and a Book
        Visitor visitor = new Visitor("Max", "Mustermann", "01.01.1999", "max.mustermann@gmx.de");
        Room room = new Room();
        Shelf shelf = new Shelf(room, "Action", 400,1);
        Book book = new Book("Welt", "Peter Hans", "Natur", shelf, 100);

        // Add the Book to the Visitor's borrowed books list and assert its presence
        visitor.addBorrowedBook(book);
        Assertions.assertThat(book).isIn(visitor.getBorrowedBooks());
    }

    // Test case for removing a borrowed book from a Visitor's list of borrowed books
    @Test
    void removeBorrowedBook_checkIfBookRemoved() {
        // Create a Visitor and a Book
        Visitor visitor = new Visitor("Max", "Mustermann", "01.01.1999", "max.mustermann@gmx.de");
        Room room = new Room();
        Shelf shelf = new Shelf(room, "Action", 400,1);
        Book book = new Book("Welt", "Peter Hans", "Natur", shelf, 100);

        // Add the Book to the Visitor's borrowed books list, then remove it and assert its absence
        visitor.addBorrowedBook(book);
        visitor.removeBorrowedBook(book);
        Assertions.assertThat(book).isNotIn(visitor.getBorrowedBooks());
    }

    // Test case for adding a book to return to a Visitor's list of books to return
    @Test
    void addBookToReturn_checkIfBookAdded() {
        // Create a Visitor and a Book
        Visitor visitor = new Visitor("Max", "Mustermann", "01.01.1999", "max.mustermann@gmx.de");
        Room room = new Room();
        Shelf shelf = new Shelf(room, "Action", 400,1);
        Book book = new Book("Welt", "Peter Hans", "Natur", shelf, 100);

        // Add the Book to the Visitor's books to return list and assert its presence
        visitor.addBookToReturn(book);
        Assertions.assertThat(book).isIn(visitor.getBooksToReturn());
    }

    // Test case for removing a book to return from a Visitor's list of books to return
    @Test
    void removeBookToReturn_checkIfBookRemoved() {
        // Create a Visitor and a Book
        Visitor visitor = new Visitor("Max", "Mustermann", "01.01.1999", "max.mustermann@gmx.de");
        Room room = new Room();
        Shelf shelf = new Shelf(room, "Action", 400,1);
        Book book = new Book("Welt", "Peter Hans", "Natur", shelf, 100);

        // Add the Book to the Visitor's books to return list, then remove it and assert its absence
        visitor.addBookToReturn(book);
        visitor.removeBookToReturn(book);
        Assertions.assertThat(book).isNotIn(visitor.getBooksToReturn());
    }

    // Test case for the equals method in Visitor class
    @Test
    void testEqualsMethod() {
        // Original Visitor object
        Visitor visitor1 = new Visitor("John", "Doe", "1990-01-01", "john.doe@example.com");

        // Create Visitor object with same attributes as visitor1
        Visitor visitor2 = new Visitor("John", "Doe", "1990-01-01", "john.doe@example.com");

        // Create Visitor object with different attributes
        Visitor visitor3 = new Visitor("Jane", "Doe", "1990-01-01", "jane.doe@example.com");

        // Ensure equals method works correctly for identical visitors
        Assertions.assertThat(visitor1).isEqualTo(visitor1);

        // Ensure equals method works correctly for different visitors
        Assertions.assertThat(visitor1).isNotEqualTo(visitor3);

        // Ensure that specific attributes are equal
        List<Book> books1 = new ArrayList<>();
        books1.add(new Book("Welt", "Peter Hans", "Natur",new Shelf(new Room(), "Acrion", 400, 1),100));
        visitor3.setBooksToReturn(books1);
        Assertions.assertThat(visitor3.getBooksToReturn()).isEqualTo(books1);

        // Ensure equals method works correctly after modifying attributes
        Assertions.assertThat(visitor1.equals(visitor2)).isFalse();

        // Ensure equals method works correctly after setting same attributes
        UUID setvisiorID = visitor1.getVisitorID();
        visitor2.setVisitorID(setvisiorID);
        visitor2.setVisitorID(visitor1.getVisitorID());
        visitor2.setBooksToReturn(visitor1.getBooksToReturn());
        Assertions.assertThat(visitor1.equals(visitor2)).isTrue();

        // Ensure equals method returns false when comparing with null
        Assertions.assertThat(visitor1.equals(null)).isFalse();
    }

    // Test case for the hashCode method in Visitor class
    @Test
    void testHashCodeMethod() {
        // Original Visitor object
        Visitor visitor1 = new Visitor("John", "Doe", "1990-01-01", "john.doe@example.com");

        // Create Visitor object with same attributes as visitor1
        Visitor visitor2 = new Visitor("John", "Doe", "1990-01-01", "john.doe@example.com");

        // Create Visitor object with different attributes
        Visitor visitor3 = new Visitor("Jane", "Doe", "1990-01-01", "jane.doe@example.com");

        // Ensure hashCode method returns the same value for identical visitors
        UUID setvisitorID = visitor1.getVisitorID();
        visitor2.setVisitorID(setvisitorID);
        visitor2.setBorrowedBooks(visitor1.getBorrowedBooks());
        visitor2.setBooksToReturn(visitor1.getBooksToReturn());
        Assertions.assertThat(visitor1).hasSameHashCodeAs(visitor2);

        // Ensure hashCode method returns different values for different visitors
        Assertions.assertThat(visitor1.hashCode()).isNotEqualTo(visitor3.hashCode());
    }


}
