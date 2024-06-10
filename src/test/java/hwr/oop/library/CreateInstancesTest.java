package hwr.oop.library;

import hwr.oop.library.cli.MainLibrary;
import hwr.oop.library.domain.*;
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
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CreateInstancesTest {


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
    void createBook_checkRightAssignment() {
        Room room = Room.createNewRoom(library, 5);
        Shelf shelf = Shelf.createNewShelf(library, room, "Action", 400, 1);
        Shelf shelf1 = Shelf.createNewShelf(library, room, "Action", 400, 2);
        Book book = new Book.Builder()
                .library(library)
                .bookID(UUID.randomUUID())
                .title("Welt")
                .author("Peter Hans")
                .genre("Natur")
                .shelf(shelf)
                .bookCondition(50)
                .bookWidth(3)
                .build();
        Book book1 = new Book.Builder()
                .library(library)
                .bookID(UUID.randomUUID())
                .title("Welt")
                .author("Peter Hans")
                .genre("Natur")
                .shelf(shelf1)
                .bookCondition(110)
                .bookWidth(31)
                .build();
        Book book2 = new Book.Builder()
                .library(library)
                .bookID(UUID.randomUUID())
                .title("Welt")
                .author("Peter Hans")
                .genre("Natur")
                .shelf(shelf1)
                .bookCondition(-110)
                .bookWidth(31)
                .build();
        Book book3 = new Book.Builder()
                .library(library)
                .bookID(UUID.randomUUID())
                .title("Welt")
                .author("Peter Hans")
                .genre("Natur")
                .shelf(shelf1)
                .bookCondition(0)
                .bookWidth(31)
                .build();
        Book book4 = new Book.Builder()
                .library(library)
                .bookID(UUID.randomUUID())
                .title("Welt")
                .author("Peter Hans")
                .genre("Natur")
                .shelf(shelf1)
                .bookCondition(100)
                .bookWidth(31)
                .build();
        assertThat(book.getBookID()).isNotNull();
        assertThat(book.getBookTitle()).isEqualTo("Welt");
        assertThat(book.getBookAuthor()).isEqualTo("Peter Hans");
        assertThat(book.getBookGenre()).isEqualTo("Natur");
        assertThat(book.getShelf()).isEqualTo(shelf);
        assertThat(book.getBookCondition()).isEqualTo(50);
        assertThat(book1.getBookCondition()).isEqualTo(-1);
        assertThat(book2.getBookCondition()).isEqualTo(-1);
        assertThat(book3.getBookCondition()).isZero();
        assertThat(book4.getBookCondition()).isEqualTo(100);
        assertThat(book.getBookWidth()).isEqualTo(3);
        assertThat(book).isIn(shelf.getBooksOnShelf())
                .isIn(library.getBookList());
        assertThat(shelf.getRemainingSpace()).isEqualTo(397);
        library.deleteRoom(room);
        library.deleteShelf(shelf);
        library.deleteShelf(shelf1);
        library.deleteBook(book);
        library.deleteBook(book1);
        library.deleteBook(book2);
        library.deleteBook(book3);
        library.deleteBook(book4);
    }

    @Test
    void createBookFails_checkExceptionRaise() {
        Room room = Room.createNewRoom(library, 5);
        Shelf shelf = Shelf.createNewShelf(library, room, "Action", 1, 1);
        Book book = new Book.Builder()
                .library(library)
                .bookID(UUID.randomUUID())
                .title("Welt")
                .author("Peter Hans")
                .genre("Natur")
                .shelf(shelf)
                .bookCondition(100)
                .bookWidth(1)
                .build();
        assertThatThrownBy(() -> new Book.Builder()
                .library(library)
                .bookID(UUID.randomUUID())
                .title("Welt")
                .author("Peter Hans")
                .genre("Natur")
                .shelf(shelf)
                .bookCondition(100)
                .bookWidth(1)
                .build()).hasMessage("Added book to shelf with not enough space.");
        library.deleteRoom(room);
        library.deleteShelf(shelf);
        library.deleteBook(book);
    }

    @Test
    void createLibrarian_checkRightAssignment() {
        Librarian librarian = Librarian.createNewLibrarian(library, "Max", "Mustermann", "01.01.1999");
        assertThat(librarian.getLibrarianName()).isEqualTo("Max");
        assertThat(librarian.getLibrarianSurname()).isEqualTo("Mustermann");
        assertThat(librarian.getLibrarianBirthday()).isEqualTo("01.01.1999");
        assertThat(librarian.getLibrarianID()).isNotNull();
        assertThat(librarian).isIn(library.getLibrarianList());
        library.deleteLibrarian(librarian);
    }

    @Test
    void createRoom_checkRightAssignment() {
        Room room = Room.createNewRoom(library, 5);
        assertThat(room.getRoomID()).isNotNull();
        assertThat(room.getShelfLimit()).isEqualTo(5);
        assertThat(room).isIn(library.getRoomList());
        library.deleteRoom(room);
    }

    @Test
    void createTempRoom_checkRightAssignment() {
        Room room = Room.createTempRoom(library);
        assertThat(room.getRoomID()).isNotNull();
        assertThat(room.getShelfLimit()).isEqualTo(10000);
        library.deleteRoom(room);
    }

    @Test
    void createShelf_checkRightAssignment() {
        Room room = Room.createNewRoom(library, 5);
        Shelf shelf = Shelf.createNewShelf(library, room, "Action", 400, 1);
        assertThat(shelf.getRoomIn()).isEqualTo(room);
        assertThat(shelf.getGenre()).isEqualTo("Action");
        assertThat(shelf.getShelfWidth()).isEqualTo(400);
        assertThat(shelf.getBoardNumber()).isEqualTo(1);
        assertThat(shelf.getShelfID()).isNotNull();
        assertThat(shelf).isIn(room.getShelfList())
                .isIn(library.getShelfList());
        library.deleteRoom(room);
        library.deleteShelf(shelf);
    }

    @Test
    void createShelfFails_checkExceptionRaise() {
        Room room = Room.createNewRoom(library, 1);
        Shelf.createNewShelf(library, room, "Action", 1, 1);
        assertThatThrownBy(() -> Shelf.createNewShelf(library, room, "Action", 2, 1)).hasMessage("Added shelf to room with not enough space.");
    }

    @Test
    void createTempShelf_checkRightAssignment() {
        Room room = Room.createTempRoom(library);
        Shelf shelf = Shelf.createTempShelf(library, room);
        assertThat(shelf.getRoomIn()).isEqualTo(room);
        assertThat(shelf.getGenre()).isEqualTo("temp");
        assertThat(shelf.getShelfWidth()).isEqualTo(10000);
        assertThat(shelf.getBoardNumber()).isEqualTo(10000);
        assertThat(shelf.getShelfID()).isNotNull();
        assertThat(shelf).isIn(room.getShelfList());
        library.deleteRoom(room);
        library.deleteShelf(shelf);
    }

    @Test
    void createVisitor_checkRightAssignment() {
        Visitor visitor = Visitor.createNewVisitor(library, "Max", "Mustermann", "01.01.1999", "max.mustermann@gmx.de");
        assertThat(visitor.getVisitorName()).isEqualTo("Max");
        assertThat(visitor.getVisitorSurname()).isEqualTo("Mustermann");
        assertThat(visitor.getVisitorBirthday()).isEqualTo("01.01.1999");
        assertThat(visitor.getVisitorEmailAddress()).isEqualTo("max.mustermann@gmx.de");
        assertThat(visitor.getVisitorID()).isNotNull();
        assertThat(visitor).isIn(library.getVisitorList());
        library.deleteVisitor(visitor);
    }

    @Test
    void createLibrary_checkRightAssignment() {
        UUID uuid = UUID.randomUUID();
        Library library1 = Library.createCompleteLibrary(uuid);
        assertThat(library1.getLibraryID()).isEqualTo(uuid);
    }

    @AfterEach
    void tearDown() {
        persistence.saveLibrary(library);
    }
}
