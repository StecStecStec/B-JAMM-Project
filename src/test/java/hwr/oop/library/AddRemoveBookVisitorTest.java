package hwr.oop.library;

import hwr.oop.library.cli.MainLibrary;
import hwr.oop.library.domain.Book;
import hwr.oop.library.domain.Room;
import hwr.oop.library.domain.Shelf;
import hwr.oop.library.domain.Visitor;
import hwr.oop.library.domain.Library;
import hwr.oop.library.persistence.CSVAdapter;
import hwr.oop.library.persistence.Persistence;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;


class AddRemoveBookVisitorTest {

    private final Library library = Library.createNewLibrary();
    private Persistence persistence;
    private static String path = null ;

    @BeforeAll
    static void init() throws URISyntaxException {
        path = pathToDirectory();
    }

    private static String pathToDirectory() throws URISyntaxException {
        return Objects.requireNonNull(MainLibrary.class.getClassLoader().getResource("csvTestFiles")).toURI().getPath();

    }

    @BeforeEach
    void setUp() {
        persistence = new CSVAdapter(path + "/");
    }

    @Test
    void addBorrowedBook_checkIfBookAdded() {
        Visitor visitor = Visitor.createCompleteVisitor(library, "Max", "Mustermann", "01.01.1999", "max.mustermann@gmx.de", UUID.randomUUID());
        Room room = Room.createNewRoom(library, 5);
        Shelf shelf = Shelf.createNewShelf(library, room, "Action", 400, 1);
        Book book = new Book.Builder()
                .library(library)
                .bookID(UUID.randomUUID())
                .title("Welt")
                .author("Peter Hans")
                .genre("Natur")
                .shelf(shelf)
                .bookCondition(100)
                .bookWidth(3)
                .build();
        visitor.addBorrowedBook(book);
        assertThat(book).isIn(visitor.getBorrowedBooks());
        library.deleteVisitor(visitor);
        library.deleteRoom(room);
        library.deleteShelf(shelf);
        library.deleteBook(book);
    }

    @Test
    void removeBorrowedBook_checkIfBookRemoved() {
        Visitor visitor = Visitor.createCompleteVisitor(library, "Max", "Mustermann", "01.01.1999", "max.mustermann@gmx.de", UUID.randomUUID());
        Room room = Room.createNewRoom(library, 5);
        Shelf shelf = Shelf.createNewShelf(library, room, "Action", 400, 1);
        Book book = new Book.Builder()
                .library(library)
                .bookID(UUID.randomUUID())
                .title("Welt")
                .author("Peter Hans")
                .genre("Natur")
                .shelf(shelf)
                .bookCondition(100)
                .bookWidth(3)
                .build();
        visitor.addBorrowedBook(book);
        visitor.removeBorrowedBook(book);
        assertThat(book).isNotIn(visitor.getBorrowedBooks());
        library.deleteVisitor(visitor);
        library.deleteRoom(room);
        library.deleteShelf(shelf);
        library.deleteBook(book);
    }

    @Test
    void addBookToReturn_checkIfBookAdded() {
        Visitor visitor = Visitor.createCompleteVisitor(library, "Max", "Mustermann", "01.01.1999", "max.mustermann@gmx.de", UUID.randomUUID());
        Room room = Room.createNewRoom(library, 5);
        Shelf shelf = Shelf.createNewShelf(library, room, "Action", 400, 1);
        Book book = new Book.Builder()
                .library(library)
                .bookID(UUID.randomUUID())
                .title("Welt")
                .author("Peter Hans")
                .genre("Natur")
                .shelf(shelf)
                .bookCondition(100)
                .bookWidth(3)
                .build();
        visitor.addBookToReturn(book);
        assertThat(book).isIn(visitor.getBooksToReturn());
        library.deleteVisitor(visitor);
        library.deleteRoom(room);
        library.deleteShelf(shelf);
        library.deleteBook(book);
    }

    @Test
    void removeBookToReturn_checkIfBookRemoved() {
        Visitor visitor = Visitor.createNewVisitor(library, "Max", "Mustermann", "01.01.1999", "max.mustermann@gmx.de");
        Room room = Room.createNewRoom(library, 5);
        Shelf shelf = Shelf.createNewShelf(library, room, "Action", 400, 1);
        Book book = new Book.Builder()
                .library(library)
                .bookID(UUID.randomUUID())
                .title("Welt")
                .author("Peter Hans")
                .genre("Natur")
                .shelf(shelf)
                .bookCondition(100)
                .bookWidth(3)
                .build();        visitor.addBookToReturn(book);
        visitor.removeBookToReturn(book);
        assertThat(book).isNotIn(visitor.getBooksToReturn());
        library.deleteVisitor(visitor);
        library.deleteRoom(room);
        library.deleteShelf(shelf);
        library.deleteBook(book);
    }

    @AfterEach
    void tearDown() {
        persistence.saveLibrary(library);
    }
}
