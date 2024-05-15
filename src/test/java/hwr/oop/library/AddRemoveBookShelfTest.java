package hwr.oop.library;

import hwr.oop.library.domain.Book;
import hwr.oop.library.domain.Room;
import hwr.oop.library.domain.Shelf;
import hwr.oop.library.persistence.CSVAdapter;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

class AddRemoveBookShelfTest {
    @Test
    void addBook_checkIfBookAdded() {
        String path = "/csvTestFiles/";
        InputStream stream = getClass().getResourceAsStream(path);
        assert stream != null;
        CSVAdapter csvAdapter = new CSVAdapter(stream.toString());

        Room room = Room.createNewRoom(csvAdapter, 5);
        Shelf shelf = Shelf.createNewShelf(csvAdapter, room, "Action", 400, 1);
        Book book = Book.createNewBook(csvAdapter, "Welt", "Peter Hans", "Natur", shelf, 100, 3);
        assertThat(book).isIn(shelf.getBooksOnShelf());
    }

    @Test
    void removeShelf_checkIfBookRemoved() {
        String path = "/csvTestFiles/";
        InputStream stream = getClass().getResourceAsStream(path);
        assert stream != null;
        CSVAdapter csvAdapter = new CSVAdapter(stream.toString());

        Room room = Room.createNewRoom(csvAdapter, 5);
        Shelf shelf = Shelf.createNewShelf(csvAdapter, room, "Action", 400, 1);
        Book book = Book.createNewBook(csvAdapter, "Welt", "Peter Hans", "Natur", shelf, 100, 3);
        shelf.removeBookOnShelf(book);
        assertThat(book).isNotIn(shelf.getBooksOnShelf());
    }
}
