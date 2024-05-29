package hwr.oop.library;

import hwr.oop.library.domain.Book;
import hwr.oop.library.domain.Library;
import hwr.oop.library.domain.Room;
import hwr.oop.library.domain.Shelf;
import hwr.oop.library.persistence.CSVAdapter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

class AddRemoveBookShelfTest {

    private Library library;
    private CSVAdapter csvAdapter;

    @BeforeEach
    void setUp() {
        URL resourceUrl = getClass().getClassLoader().getResource("csvTestFiles");
        assert resourceUrl != null;
        File directory = new File(resourceUrl.getFile());
        String path = directory.getAbsolutePath() + "/";
        csvAdapter = new CSVAdapter(path);
        library = csvAdapter.loadLibrary();
    }

    @Test
    void addBook_checkIfBookAdded() {
        Room room = Room.createNewRoom(library, 5);
        library.addRoom(room);
        Shelf shelf = Shelf.createNewShelf(library, room, "Action", 400, 1);
        library.addShelf(shelf);
        Book book = Book.createNewBook(library, "Welt", "Peter Hans", "Natur", shelf, 100, 3);
        library.addBook(book);
        assertThat(book).isIn(shelf.getBooksOnShelf());
    }

    @Test
    void removeShelf_checkIfBookRemoved() {
        Room room = Room.createNewRoom(library, 5);
        library.addRoom(room);
        Shelf shelf = Shelf.createNewShelf(library, room, "Action", 400, 1);
        library.addShelf(shelf);
        Book book = Book.createNewBook(library, "Welt", "Peter Hans", "Natur", shelf, 100, 3);
        library.addBook(book);
        shelf.removeBookOnShelf(book);
        assertThat(book).isNotIn(shelf.getBooksOnShelf());
    }

    @AfterEach
    void tearDown() {
        csvAdapter.saveLibrary(library);
    }
}
