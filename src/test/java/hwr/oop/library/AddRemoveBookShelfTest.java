package hwr.oop.library;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class AddRemoveBookShelfTest {
    @Test
    void addBook_checkIfBookAdded() {
        Room room = Room.createNewRoom(5);
        Shelf shelf = Shelf.createNewShelf(room, "Action", 400, 1);
        Book book = Book.createNewBook("Welt", "Peter Hans", "Natur", shelf, 100, 3);
        Assertions.assertThat(book).isIn(shelf.getBooksOnShelf());
    }

    @Test
    void removeShelf_checkIfBookRemoved() {
        Room room = Room.createNewRoom(5);
        Shelf shelf = Shelf.createNewShelf(room, "Action", 400, 1);
        Book book = Book.createNewBook("Welt", "Peter Hans", "Natur", shelf, 100, 3);
        shelf.removeBookOnShelf(book);
        Assertions.assertThat(book).isNotIn(shelf.getBooksOnShelf());
    }
}
