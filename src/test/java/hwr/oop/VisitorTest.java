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
        Visitor visitor = Visitor.createCompleteVisitor("Max", "Mustermann", "01.01.1999", "max.mustermann@gmx.de", UUID.randomUUID());

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
<<<<<<< HEAD
        Visitor visitor = new Visitor("Max", "Mustermann", "01.01.1999", "max.mustermann@gmx.de");
        Room room = new Room();
        Shelf shelf = new Shelf(room, "Action", 400,1);
        Book book = new Book("Welt", "Peter Hans", "Natur", shelf, 100, 3);
=======
        Visitor visitor = Visitor.createCompleteVisitor("Max", "Mustermann", "01.01.1999", "max.mustermann@gmx.de", UUID.randomUUID());
        Room room = Room.createNewRoom();
        Shelf shelf = Shelf.createNewShelf(room, "Action", 400,1);
        Book book = new Book("Welt", "Peter Hans", "Natur", shelf, 100);
>>>>>>> origin/NewTestMutationShelf

        // Add the Book to the Visitor's borrowed books list and assert its presence
        visitor.addBorrowedBook(book);
        Assertions.assertThat(book).isIn(visitor.getBorrowedBooks());
    }

    @Test
    void visitorIDCheck(){
        Visitor visitor = Visitor.createCompleteVisitor("Max", "Mustermann", "01.01.1999", "max.mustermann@gmx.de", UUID.randomUUID());

        Assertions.assertThat(visitor.getVisitorID()).isNotNull();
    }
    // Test case for removing a borrowed book from a Visitor's list of borrowed books
    @Test
    void removeBorrowedBook_checkIfBookRemoved() {
        // Create a Visitor and a Book
<<<<<<< HEAD
        Visitor visitor = new Visitor("Max", "Mustermann", "01.01.1999", "max.mustermann@gmx.de");
        Room room = new Room();
        Shelf shelf = new Shelf(room, "Action", 400,1);
        Book book = new Book("Welt", "Peter Hans", "Natur", shelf, 100, 3);
=======
        Visitor visitor = Visitor.createCompleteVisitor("Max", "Mustermann", "01.01.1999", "max.mustermann@gmx.de", UUID.randomUUID());
        Room room = Room.createNewRoom();
        Shelf shelf = Shelf.createNewShelf(room, "Action", 400,1);
        Book book = new Book("Welt", "Peter Hans", "Natur", shelf, 100);
>>>>>>> origin/NewTestMutationShelf

        // Add the Book to the Visitor's borrowed books list, then remove it and assert its absence
        visitor.addBorrowedBook(book);
        visitor.removeBorrowedBook(book);
        Assertions.assertThat(book).isNotIn(visitor.getBorrowedBooks());
    }

    // Test case for adding a book to return to a Visitor's list of books to return
    @Test
    void addBookToReturn_checkIfBookAdded() {
        // Create a Visitor and a Book
<<<<<<< HEAD
        Visitor visitor = new Visitor("Max", "Mustermann", "01.01.1999", "max.mustermann@gmx.de");
        Room room = new Room();
        Shelf shelf = new Shelf(room, "Action", 400,1);
        Book book = new Book("Welt", "Peter Hans", "Natur", shelf, 100, 3);
=======
        UUID uuid = UUID.randomUUID();

        Visitor visitor = Visitor.createCompleteVisitor("Max","Mustermann","01.01.1999", "max.mustermann@gmx.de",uuid);
        Room room = Room.createNewRoom();
        Shelf shelf = Shelf.createNewShelf(room, "Action", 400,1);
        Book book = new Book("Welt", "Peter Hans", "Natur", shelf, 100);
>>>>>>> origin/NewTestMutationShelf

        // Add the Book to the Visitor's books to return list and assert its presence
        visitor.addBookToReturn(book);
        Assertions.assertThat(book).isIn(visitor.getBooksToReturn());
    }

    // Test case for removing a book to return from a Visitor's list of books to return
    @Test
    void removeBookToReturn_checkIfBookRemoved() {
        // Create a Visitor and a Book
<<<<<<< HEAD
        Visitor visitor = new Visitor("Max", "Mustermann", "01.01.1999", "max.mustermann@gmx.de");
        Room room = new Room();
        Shelf shelf = new Shelf(room, "Action", 400,1);
        Book book = new Book("Welt", "Peter Hans", "Natur", shelf, 100, 3);
=======
        Visitor visitor = Visitor.createNewVisitor("Max", "Mustermann", "01.01.1999", "max.mustermann@gmx.de");
        Room room = Room.createNewRoom();
        Shelf shelf = Shelf.createNewShelf(room, "Action", 400,1);
        Book book = new Book("Welt", "Peter Hans", "Natur", shelf, 100);
>>>>>>> origin/NewTestMutationShelf

        // Add the Book to the Visitor's books to return list, then remove it and assert its absence
        visitor.addBookToReturn(book);
        visitor.removeBookToReturn(book);
        Assertions.assertThat(book).isNotIn(visitor.getBooksToReturn());
    }

    // Test case for the equals method in Visitor class
    @Test
    void testEqualsMethod() {
        // Original Visitor object
        UUID uuid1 = UUID.randomUUID();

        Visitor visitor1 = Visitor.createCompleteVisitor("John", "Doe", "1990-01-01", "john.doe@example.com", uuid1);

        // Create Visitor object with same attributes as visitor1
        Visitor visitor2 = Visitor.createCompleteVisitor("John", "Doe", "1990-01-01", "john.doe@example.com", uuid1);

        // Create Visitor object with different attributes
        Visitor visitor3 = Visitor.createNewVisitor("Jane", "Doe", "1990-01-01", "jane.doe@example.com");

        // Ensure equals method works correctly for different visitors
        Assertions.assertThat(visitor1).isNotEqualTo(visitor3);

        // Ensure that specific attributes are equal
<<<<<<< HEAD
        List<Book> books1 = new ArrayList<>();
        books1.add(new Book("Welt", "Peter Hans", "Natur",new Shelf(new Room(), "Acrion", 400, 1),100, 3));
        visitor3.setBooksToReturn(books1);
        Assertions.assertThat(visitor3.getBooksToReturn()).isEqualTo(books1);
=======
        //List<Book> books1 = new ArrayList<>();
        //books1.add(new Book("Welt", "Peter Hans", "Natur",new Shelf(new Room(), "Acrion", 400, 1),100));
        //visitor3.addBorrowedBook(new Book("Welt", "Peter Hans", "Natur",new Shelf(new Room(), "Acrion", 400, 1),100));
        //Assertions.assertThat(visitor3.getBooksToReturn()).isEqualTo(books1);
>>>>>>> origin/NewTestMutationShelf

        // Ensure equals method works correctly after setting same attributes
        Assertions.assertThat(visitor1.equals(visitor2)).isTrue();

        // Ensure equals method returns false when comparing with null
        Assertions.assertThat(visitor1.equals(null)).isFalse();
    }

    // Test case for the hashCode method in Visitor class
    @Test
    void testHashCodeMethod() {
        // Original Visitor object
        UUID uuid1 = UUID.randomUUID();
        Visitor visitor1 = Visitor.createCompleteVisitor("John", "Doe", "1990-01-01", "john.doe@example.com", uuid1);

        // Create Visitor object with same attributes as visitor1
        Visitor visitor2 = Visitor.createCompleteVisitor("John", "Doe", "1990-01-01", "john.doe@example.com", uuid1);

        Visitor visitor3 = Visitor.createNewVisitor("Max", "Mustermann", "01.01.1999", "max.mustermann@gmx.de") ;

        // Ensure hashCode method returns different values for different visitors | visitor1 and visitor2 have different UUID´s
        Assertions.assertThat(visitor1.hashCode()).isNotEqualTo(visitor3.hashCode());

        // Ensure hashCode method returns the same value for identical visitors | setting UUID from visitor1 to visitor2
        Assertions.assertThat(visitor1).hasSameHashCodeAs(visitor2);


    }


}
