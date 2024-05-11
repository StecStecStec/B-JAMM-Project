package hwr.oop.library;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.shadow.com.univocity.parsers.csv.Csv;

import java.util.UUID;

class EqualsHashTest {
    @Test
    void book_testEqualsMethod() {
        UUID uuid = UUID.randomUUID();

        CSVAdapter csvAdapter = new CSVAdapter(".\\src\\test\\resources\\csvTestFiles\\");
        Room room = Room.createNewRoom(csvAdapter, 5);
        Shelf shelf = Shelf.createNewShelf(csvAdapter, room, "Action", 400,1);

        Book book1 = Book.createCompleteBook(csvAdapter, uuid,"Welt", "Peter Hans", "Natur", shelf, 100, 3);
        Book book2 = Book.createCompleteBook(csvAdapter, uuid,"Welt", "Peter Hans", "Natur", shelf, 100, 3);
        Book book3 = Book.createNewBook(csvAdapter, "Planet", "Max Mustermann", "SciFi", shelf, 80, 3);

        Assertions.assertThat(book1)
                .isEqualTo(book1)
                .isNotEqualTo(book3)
                .isNotNull();
        Assertions.assertThat(book1.equals(book2)).isTrue();
        Assertions.assertThat(book1.equals(room)).isFalse();
    }

    @Test
    void book_testHashCodeMethod()   {
        UUID uuid = UUID.randomUUID();

        CSVAdapter csvAdapter = new CSVAdapter(".\\src\\test\\resources\\csvTestFiles\\");
        Room room = Room.createNewRoom(csvAdapter, 5);
        Shelf shelf = Shelf.createNewShelf(csvAdapter, room, "Action", 400,1);

        Book book1 = Book.createCompleteBook(csvAdapter, uuid,"Welt", "Peter Hans", "Natur", shelf, 100, 3);
        Book book2 = Book.createCompleteBook(csvAdapter, uuid,"Welt", "Peter Hans", "Natur", shelf, 100, 3);
        Book book3 = Book.createNewBook(csvAdapter, "Planet", "Max Mustermann", "SciFi", shelf, 80, 3);

        Assertions.assertThat(book1).hasSameHashCodeAs(book2);
        Assertions.assertThat(book1.hashCode()).isNotEqualTo(book3.hashCode());
    }

    @Test
    void visitor_testEqualsMethod() {
        UUID uuid1 = UUID.randomUUID();

        CSVAdapter csvAdapter = new CSVAdapter(".\\src\\test\\resources\\csvTestFiles\\");
        Room room = Room.createNewRoom(csvAdapter, 5);

        Visitor visitor1 = Visitor.createCompleteVisitor(csvAdapter, "John", "Doe", "1990-01-01", "john.doe@example.com", uuid1);
        Visitor visitor2 = Visitor.createCompleteVisitor(csvAdapter, "John", "Doe", "1990-01-01", "john.doe@example.com", uuid1);
        Visitor visitor3 = Visitor.createNewVisitor(csvAdapter, "Jane", "Doe", "1990-01-01", "jane.doe@example.com");

        Assertions.assertThat(visitor1)
                .isEqualTo(visitor1)
                .isNotEqualTo(visitor3)
                .isNotNull();
        Assertions.assertThat(visitor1.equals(visitor2)).isTrue();
        Assertions.assertThat(visitor1.equals(room)).isFalse();
    }

    @Test
    void visitor_testHashCodeMethod() {
        UUID uuid1 = UUID.randomUUID();

        CSVAdapter csvAdapter = new CSVAdapter(".\\src\\test\\resources\\csvTestFiles\\");

        Visitor visitor1 = Visitor.createCompleteVisitor(csvAdapter, "John", "Doe", "1990-01-01", "john.doe@example.com", uuid1);
        Visitor visitor2 = Visitor.createCompleteVisitor(csvAdapter, "John", "Doe", "1990-01-01", "john.doe@example.com", uuid1);
        Visitor visitor3 = Visitor.createNewVisitor(csvAdapter, "Max", "Mustermann", "01.01.1999", "max.mustermann@gmx.de");

        Assertions.assertThat(visitor1.hashCode()).isNotEqualTo(visitor3.hashCode());
        Assertions.assertThat(visitor1).hasSameHashCodeAs(visitor2);
    }

    @Test
    void shelf_testEqualsMethod() {
        UUID uuid = UUID.randomUUID();

        CSVAdapter csvAdapter = new CSVAdapter(".\\src\\test\\resources\\csvTestFiles\\");
        Room room = Room.createNewRoom(csvAdapter, 5);

        Shelf shelf1 = Shelf.createCompleteNewShelf(csvAdapter, uuid, room, "Action", 400, 1);
        Shelf shelf2 = Shelf.createCompleteNewShelf(csvAdapter, uuid, room, "Action", 400, 1);
        Shelf shelf3 = Shelf.createNewShelf(csvAdapter, room, "Action", 400, 1);

        Assertions.assertThat(shelf1)
                .isEqualTo(shelf1)
                .isNotEqualTo(shelf3)
                .isNotNull();
        Assertions.assertThat(shelf1.equals(shelf2)).isTrue();
        Assertions.assertThat(shelf1.equals(room)).isFalse();
    }

    @Test
    void shelf_testHashCodeMethod() {
        UUID uuid = UUID.randomUUID();

        CSVAdapter csvAdapter = new CSVAdapter(".\\src\\test\\resources\\csvTestFiles\\");
        Room room = Room.createNewRoom(csvAdapter, 5);

        Shelf shelf1 = Shelf.createCompleteNewShelf(csvAdapter, uuid, room, "Action", 400, 1);
        Shelf shelf2 = Shelf.createCompleteNewShelf(csvAdapter, uuid, room, "Action", 400, 1);
        Shelf shelf3 = Shelf.createNewShelf(csvAdapter, room, "Action", 400, 1);

        Assertions.assertThat(shelf1.hashCode()).isNotEqualTo(shelf3.hashCode());
        Assertions.assertThat(shelf1).hasSameHashCodeAs(shelf2);
    }

    @Test
    void room_testEqualsMethod() {
        UUID uuid = UUID.randomUUID();

        CSVAdapter csvAdapter = new CSVAdapter(".\\src\\test\\resources\\csvTestFiles\\");
        Visitor visitor = Visitor.createNewVisitor(csvAdapter, "John", "Doe", "1990-01-01", "john.doe@example.com");

        Room room1 = Room.createCompleteNewRoom(csvAdapter, uuid, 5);
        Room room2 = Room.createCompleteNewRoom(csvAdapter, uuid, 5);
        Room room3 = Room.createNewRoom(csvAdapter, 5);

        Assertions.assertThat(room1)
                .isEqualTo(room1)
                .isNotEqualTo(room3)
                .isNotNull();
        Assertions.assertThat(room1.equals(room2)).isTrue();
        Assertions.assertThat(room1.equals(visitor)).isFalse();
    }

    @Test
    void room_testHashCodeMethod() {
        UUID uuid = UUID.randomUUID();

        CSVAdapter csvAdapter = new CSVAdapter(".\\src\\test\\resources\\csvTestFiles\\");

        Room room1 = Room.createCompleteNewRoom(csvAdapter, uuid, 5);
        Room room2 = Room.createCompleteNewRoom(csvAdapter, uuid, 5);
        Room room3 = Room.createNewRoom(csvAdapter, 5);

        Assertions.assertThat(room1.hashCode()).isNotEqualTo(room3.hashCode());
        Assertions.assertThat(room1).hasSameHashCodeAs(room2);
    }

    @Test
    void librarian_testEqualsMethod() {
        UUID uuid = UUID.randomUUID();

        CSVAdapter csvAdapter = new CSVAdapter(".\\src\\test\\resources\\csvTestFiles\\");
        Room room = Room.createNewRoom(csvAdapter, 5);

        Librarian librarian1 = Librarian.createCompleteNewLibrarian(csvAdapter, uuid, "John", "Doe", "1990-01-01");
        Librarian librarian2 = Librarian.createCompleteNewLibrarian(csvAdapter, uuid, "John", "Doe", "1990-01-01");
        Librarian librarian3 = Librarian.createNewLibrarian(csvAdapter, "John", "Doe", "1990-01-01");

        Assertions.assertThat(librarian1)
                .isEqualTo(librarian1)
                .isNotEqualTo(librarian3)
                .isNotNull();
        Assertions.assertThat(librarian1.equals(librarian2)).isTrue();
        Assertions.assertThat(librarian1.equals(room)).isFalse();
    }

    @Test
    void librarian_testHashCodeMethod() {
        UUID uuid = UUID.randomUUID();

        CSVAdapter csvAdapter = new CSVAdapter(".\\src\\test\\resources\\csvTestFiles\\");

        Librarian librarian1 = Librarian.createCompleteNewLibrarian(csvAdapter, uuid, "John", "Doe", "1990-01-01");
        Librarian librarian2 = Librarian.createCompleteNewLibrarian(csvAdapter, uuid, "John", "Doe", "1990-01-01");
        Librarian librarian3 = Librarian.createNewLibrarian(csvAdapter, "Jane", "Doe", "1990-01-01");

        Assertions.assertThat(librarian1.hashCode()).isNotEqualTo(librarian3.hashCode());
        Assertions.assertThat(librarian1).hasSameHashCodeAs(librarian2);
    }
}
