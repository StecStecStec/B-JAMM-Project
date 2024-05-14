package hwr.oop.library;

import hwr.oop.library.persistance.CSVAdapter;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AddRemoveBookShelfTest {
    @Test
    void addBook_checkIfBookAdded() {
        CSVAdapter csvAdapter = new CSVAdapter(".\\src\\test\\resources\\csvTestFiles\\");
        Room room = Room.createNewRoom(csvAdapter, 5);
        Shelf shelf = Shelf.createNewShelf(csvAdapter, room, "Action", 400, 1);
        Book book = Book.createNewBook(csvAdapter, "Welt", "Peter Hans", "Natur", shelf, 100, 3);
        assertThat(book).isIn(shelf.getBooksOnShelf());
    }

    @Test
    void removeShelf_checkIfBookRemoved() {
        CSVAdapter csvAdapter = new CSVAdapter(".\\src\\test\\resources\\csvTestFiles\\");
        Room room = Room.createNewRoom(csvAdapter, 5);
        Shelf shelf = Shelf.createNewShelf(csvAdapter, room, "Action", 400, 1);
        Book book = Book.createNewBook(csvAdapter, "Welt", "Peter Hans", "Natur", shelf, 100, 3);
        shelf.removeBookOnShelf(book);
        assertThat(book).isNotIn(shelf.getBooksOnShelf());
    }
}
