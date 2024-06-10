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
import java.util.Objects;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CSVAdapterTest {

    private Library library = Library.createNewLibrary();
    private Persistence persistence;
    private static String path = null;

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
    void loadClearAndSaveCSV() {
        Room room = Room.createNewRoom(library, 5);
        Shelf shelf = Shelf.createNewShelf(library, room, "Action", 400, 1);
        Book book1 = new Book.Builder()
                .library(library)
                .bookID(UUID.randomUUID())
                .title("Welt")
                .author("Peter Hans")
                .genre("Natur")
                .shelf(shelf)
                .bookCondition(100)
                .bookWidth(3)
                .build();
        Book book2 = new Book.Builder()
                .library(library)
                .bookID(UUID.randomUUID())
                .title("Welten")
                .author("Peter Hansen")
                .genre("Naturen")
                .shelf(shelf)
                .bookCondition(100)
                .bookWidth(5)
                .build();
        Visitor visitor1 = Visitor.createNewVisitor(library, "Max", "Mustermann", "01.01.1999", "max.mustermann@gmx.de");
        Visitor visitor2 = Visitor.createNewVisitor(library, "Maxia", "Mustermannia", "01.02.1999", "max.mustermannia@gmx.de");
        Librarian librarian = Librarian.createNewLibrarian(library, "Maxa", "Mustermanna", "01.01.2000");
        book2.borrow(visitor2);
        visitor2.addBookToReturn(book2);

        assertThat(library.getRoomList()).contains(room);
        assertThat(library.getShelfList()).contains(shelf);
        assertThat(library.getBookList()).contains(book1)
                .contains(book2);
        assertThat(library.getVisitorList()).contains(visitor1)
                .contains(visitor2);
        assertThat(library.getLibrarianList()).contains(librarian);

        persistence.saveLibrary(library);

        library.deleteRoom(room);
        assertThat(library.getRoomList())
                .doesNotContain(room)
                .isEmpty();

        library.deleteShelf(shelf);
        assertThat(library.getShelfList())
                .doesNotContain(shelf)
                .isEmpty();

        library.deleteBook(book1);
        library.deleteBook(book2);
        assertThat(library.getBookList())
                .doesNotContain(book1)
                .doesNotContain(book2)
                .isEmpty();

        library.deleteVisitor(visitor1);
        library.deleteVisitor(visitor2);
        assertThat(library.getVisitorList())
                .doesNotContain(visitor1)
                .doesNotContain(visitor2)
                .isEmpty();

        library.deleteLibrarian(librarian);
        assertThat(library.getLibrarianList())
                .doesNotContain(librarian)
                .isEmpty();

        library = persistence.loadLibrary();

        assertThat(room).isEqualTo(library.getRoomList().getFirst());
        assertThat(shelf).isEqualTo(library.getShelfList().getFirst());
        assertThat(book1).isEqualTo(library.getBookList().getFirst());
        assertThat(book2).isEqualTo(library.getBookList().getLast());
        assertThat(visitor1).isEqualTo(library.getVisitorList().getFirst());
        assertThat(visitor2).isEqualTo(library.getVisitorList().getLast());
        assertThat(librarian).isEqualTo(library.getLibrarianList().getFirst());
    }

    @Test
    void testCSVAdapter() {
        Persistence persistence2 = new CSVAdapter("invalid_path_to_file.csv");

        assertThrows(RuntimeException.class, persistence2::loadLibrary);
    }

    @AfterEach
    void tearDown() {
        persistence.saveLibrary(library);
    }
}

