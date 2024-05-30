package hwr.oop.library;

import hwr.oop.library.domain.*;
import hwr.oop.library.persistence.CSVAdapter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URL;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class EqualsHashTest {

    private final Library library = Library.createNewLibrary();
    private CSVAdapter csvAdapter;

    @BeforeEach
    void setUp() {
        csvAdapter = new CSVAdapter(".\\src\\test\\resources\\csvTestFiles\\");
    }

    @Test
    void book_testEqualsMethod() {
        UUID uuid = UUID.randomUUID();

        Room room = Room.createNewRoom(library, 5);
        Shelf shelf = Shelf.createNewShelf(library, room, "Action", 400, 1);
        Book book1 = Book.createCompleteBook(library, uuid, "Welt", "Peter Hans", "Natur", shelf, 100, 3);
        Book book2 = Book.createCompleteBook(library, uuid, "Welt", "Peter Hans", "Natur", shelf, 100, 3);
        Book book3 = Book.createNewBook(library, "Planet", "Max Mustermann", "SciFi", shelf, 80, 3);

        assertThat(book1)
                .isEqualTo(book1)
                .isNotEqualTo(book3)
                .isNotNull();
        assertThat(book1.equals(book2)).isTrue();
        assertThat(book1.equals(room)).isFalse();

        library.deleteRoom(room);
        library.deleteShelf(shelf);
        library.deleteBook(book1);
        library.deleteBook(book2);
        library.deleteBook(book3);
    }

    @Test
    void book_testHashCodeMethod() {
        UUID uuid = UUID.randomUUID();

        Room room = Room.createNewRoom(library, 5);
        Shelf shelf = Shelf.createNewShelf(library, room, "Action", 400, 1);
        Book book1 = Book.createCompleteBook(library, uuid, "Welt", "Peter Hans", "Natur", shelf, 100, 3);
        Book book2 = Book.createCompleteBook(library, uuid, "Welt", "Peter Hans", "Natur", shelf, 100, 3);
        Book book3 = Book.createNewBook(library, "Planet", "Max Mustermann", "SciFi", shelf, 80, 3);

        assertThat(book1).hasSameHashCodeAs(book2);
        assertThat(book1.hashCode()).isNotEqualTo(book3.hashCode());

        library.deleteRoom(room);
        library.deleteShelf(shelf);
        library.deleteBook(book1);
        library.deleteBook(book2);
        library.deleteBook(book3);
    }

    @Test
    void visitor_testEqualsMethod() {
        UUID uuid1 = UUID.randomUUID();

        Room room = Room.createNewRoom(library, 5);
        Visitor visitor1 = Visitor.createCompleteVisitor(library, "John", "Doe", "1990-01-01", "john.doe@example.com", uuid1);
        Visitor visitor2 = Visitor.createCompleteVisitor(library, "John", "Doe", "1990-01-01", "john.doe@example.com", uuid1);
        Visitor visitor3 = Visitor.createNewVisitor(library, "Jane", "Doe", "1990-01-01", "jane.doe@example.com");

        assertThat(visitor1)
                .isEqualTo(visitor1)
                .isNotEqualTo(visitor3)
                .isNotNull();
        assertThat(visitor1.equals(visitor2)).isTrue();
        assertThat(visitor1.equals(room)).isFalse();

        library.deleteVisitor(visitor1);
        library.deleteVisitor(visitor2);
        library.deleteVisitor(visitor3);
    }

    @Test
    void visitor_testHashCodeMethod() {
        UUID uuid1 = UUID.randomUUID();

        Visitor visitor1 = Visitor.createCompleteVisitor(library, "John", "Doe", "1990-01-01", "john.doe@example.com", uuid1);
        Visitor visitor2 = Visitor.createCompleteVisitor(library, "John", "Doe", "1990-01-01", "john.doe@example.com", uuid1);
        Visitor visitor3 = Visitor.createNewVisitor(library, "Max", "Mustermann", "01.01.1999", "max.mustermann@gmx.de");

        assertThat(visitor1.hashCode()).isNotEqualTo(visitor3.hashCode());
        assertThat(visitor1).hasSameHashCodeAs(visitor2);

        library.deleteVisitor(visitor1);
        library.deleteVisitor(visitor2);
        library.deleteVisitor(visitor3);
    }

    @Test
    void shelf_testEqualsMethod() {
        UUID uuid = UUID.randomUUID();

        Room room = Room.createNewRoom(library, 5);
        Shelf shelf1 = Shelf.createCompleteNewShelf(library, uuid, room, "Action", 400, 1);
        Shelf shelf2 = Shelf.createCompleteNewShelf(library, uuid, room, "Action", 400, 1);
        Shelf shelf3 = Shelf.createNewShelf(library, room, "Action", 400, 1);

        assertThat(shelf1)
                .isEqualTo(shelf1)
                .isNotEqualTo(shelf3)
                .isNotNull();
        assertThat(shelf1.equals(shelf2)).isTrue();
        assertThat(shelf1.equals(room)).isFalse();

        library.deleteRoom(room);
        library.deleteShelf(shelf1);
        library.deleteShelf(shelf2);
        library.deleteShelf(shelf3);
    }

    @Test
    void shelf_testHashCodeMethod() {
        UUID uuid = UUID.randomUUID();

        Room room = Room.createNewRoom(library, 5);
        Shelf shelf1 = Shelf.createCompleteNewShelf(library, uuid, room, "Action", 400, 1);
        Shelf shelf2 = Shelf.createCompleteNewShelf(library, uuid, room, "Action", 400, 1);
        Shelf shelf3 = Shelf.createNewShelf(library, room, "Action", 400, 1);

        assertThat(shelf1.hashCode()).isNotEqualTo(shelf3.hashCode());
        assertThat(shelf1).hasSameHashCodeAs(shelf2);

        library.deleteRoom(room);
        library.deleteShelf(shelf1);
        library.deleteShelf(shelf2);
        library.deleteShelf(shelf3);
    }

    @Test
    void room_testEqualsMethod() {
        UUID uuid = UUID.randomUUID();

        Visitor visitor = Visitor.createNewVisitor(library, "John", "Doe", "1990-01-01", "john.doe@example.com");
        Room room1 = Room.createCompleteNewRoom(library, uuid, 5);
        Room room2 = Room.createCompleteNewRoom(library, uuid, 5);
        Room room3 = Room.createNewRoom(library, 5);

        assertThat(room1)
                .isEqualTo(room1)
                .isNotEqualTo(room3)
                .isNotNull();
        assertThat(room1.equals(room2)).isTrue();
        assertThat(room1.equals(visitor)).isFalse();

        library.deleteVisitor(visitor);
        library.deleteRoom(room1);
        library.deleteRoom(room2);
        library.deleteRoom(room3);
    }

    @Test
    void room_testHashCodeMethod() {
        UUID uuid = UUID.randomUUID();

        Room room1 = Room.createCompleteNewRoom(library, uuid, 5);
        Room room2 = Room.createCompleteNewRoom(library, uuid, 5);
        Room room3 = Room.createNewRoom(library, 5);

        assertThat(room1.hashCode()).isNotEqualTo(room3.hashCode());
        assertThat(room1).hasSameHashCodeAs(room2);

        library.deleteRoom(room1);
        library.deleteRoom(room2);
        library.deleteRoom(room3);
    }

    @Test
    void librarian_testEqualsMethod() {
        UUID uuid = UUID.randomUUID();

        Room room = Room.createNewRoom(library, 5);
        Librarian librarian1 = Librarian.createCompleteNewLibrarian(library, uuid, "John", "Doe", "1990-01-01");
        Librarian librarian2 = Librarian.createCompleteNewLibrarian(library, uuid, "John", "Doe", "1990-01-01");
        Librarian librarian3 = Librarian.createNewLibrarian(library, "John", "Doe", "1990-01-01");

        assertThat(librarian1)
                .isEqualTo(librarian1)
                .isNotEqualTo(librarian3)
                .isNotNull();
        assertThat(librarian1.equals(librarian2)).isTrue();
        assertThat(librarian1.equals(room)).isFalse();

        library.deleteLibrarian(librarian1);
        library.deleteLibrarian(librarian2);
        library.deleteLibrarian(librarian3);
    }

    @Test
    void librarian_testHashCodeMethod() {
        UUID uuid = UUID.randomUUID();

        Librarian librarian1 = Librarian.createCompleteNewLibrarian(library, uuid, "John", "Doe", "1990-01-01");
        Librarian librarian2 = Librarian.createCompleteNewLibrarian(library, uuid, "John", "Doe", "1990-01-01");
        Librarian librarian3 = Librarian.createNewLibrarian(library, "Jane", "Doe", "1990-01-01");

        assertThat(librarian1.hashCode()).isNotEqualTo(librarian3.hashCode());
        assertThat(librarian1).hasSameHashCodeAs(librarian2);

        library.deleteLibrarian(librarian1);
        library.deleteLibrarian(librarian2);
        library.deleteLibrarian(librarian3);
    }

    @AfterEach
    void tearDown() {
        csvAdapter.saveLibrary(library);
    }
}
