package hwr.oop.library;

import hwr.oop.library.domain.*;
import hwr.oop.library.persistence.CSVAdapter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CSVAdapterTest {

    private Library library = Library.createNewLibrary();
    private CSVAdapter csvAdapter;

    private String pathToDirectory() {
        try {
            Path currentDirectory = Paths.get(System.getProperty("user.dir"));

            try (Stream<Path> stream = Files.walk(currentDirectory)) {
                Optional<Path> directory = stream
                        .filter(Files::isDirectory)
                        .filter(path -> path.getFileName().toString().equals("csvTestFiles"))
                        .findFirst();

                return directory.map(Path::toString).orElse(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @BeforeEach
    void setUp() {
        csvAdapter = new CSVAdapter(pathToDirectory());
    }

    @Test
    void loadClearAndSaveCSV() {
        Room room = Room.createNewRoom(library, 5);
        Shelf shelf = Shelf.createNewShelf(library, room, "Action", 400, 1);
        Book book1 = Book.createNewBook(library, "Welt", "Peter Hans", "Natur", shelf, 100, 3);
        Book book2 = Book.createNewBook(library, "Welten", "Peter Hansen", "Naturen", shelf, 100, 5);
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

        csvAdapter.saveLibrary(library);

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

        library = csvAdapter.loadLibrary();

        assertThat(room).isEqualTo(library.getRoomList().getFirst());
        assertThat(shelf).isEqualTo(library.getShelfList().getFirst());
        assertThat(book1).isEqualTo(library.getBookList().getFirst());
        assertThat(book2).isEqualTo(library.getBookList().getLast());
        assertThat(visitor1).isEqualTo(library.getVisitorList().getFirst());
        assertThat(visitor2).isEqualTo(library.getVisitorList().getLast());
        assertThat(librarian).isEqualTo(library.getLibrarianList().getFirst());
    }

    //@Test
    void testCSVAdapter() {
        csvAdapter = new CSVAdapter("invalid_path_to_file.csv");

        assertThrows(RuntimeException.class, csvAdapter::loadLibrary);
    }

    @AfterEach
    void tearDown() {
        csvAdapter.saveLibrary(library);
    }
}

