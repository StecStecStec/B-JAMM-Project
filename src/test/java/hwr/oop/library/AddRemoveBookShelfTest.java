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

    private final Library library = Library.createNewLibrary();
    private CSVAdapter csvAdapter;

    @BeforeEach
    void setUp() {
        csvAdapter = new CSVAdapter(".\\src\\test\\resources\\csvTestFiles\\");
    }


    @Test
    void addBook_checkIfBookAdded() {
        Room room = Room.createNewRoom(library, 5);
        Shelf shelf = Shelf.createNewShelf(library, room, "Action", 400, 1);
        Book book = Book.createNewBook(library, "Welt", "Peter Hans", "Natur", shelf, 100, 3);
        assertThat(book).isIn(shelf.getBooksOnShelf());
        library.deleteRoom(room);
        library.deleteShelf(shelf);
        library.deleteBook(book);
    }

    @Test
    void removeShelf_checkIfBookRemoved() {
        Room room = Room.createNewRoom(library, 5);
        Shelf shelf = Shelf.createNewShelf(library, room, "Action", 400, 1);
        Book book = Book.createNewBook(library, "Welt", "Peter Hans", "Natur", shelf, 100, 3);
        shelf.removeBookOnShelf(book);
        assertThat(book).isNotIn(shelf.getBooksOnShelf());
        library.deleteRoom(room);
        library.deleteShelf(shelf);
        library.deleteBook(book);
    }

    @AfterEach
    void tearDown() {
        csvAdapter.saveLibrary(library);
    }
}
