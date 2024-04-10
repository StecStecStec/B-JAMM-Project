package hwr.oop;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class ShelfTest {
    @Test
    void createShelf_checkRightAssignment(){
        Room room = new Room();
        Shelf shelf = new Shelf(room, "Action", 400,1);
        Assertions.assertThat(shelf.getRoomIn()).isEqualTo(room);
        Assertions.assertThat(shelf.getGenre()).isEqualTo("Action");
        Assertions.assertThat(shelf.getShelfWidth()).isEqualTo(400);
        Assertions.assertThat(shelf.getBoardNumber()).isEqualTo(1);

    }

    @Test
    void addBook_checkIfBookAdded(){
        Room room = new Room();
        Shelf shelf = new Shelf(room, "Action", 400,1);
        Book book = new Book("Welt", "Peter Hans", "Natur", shelf, 100);
        shelf.addBookOnShelf(book);
        Assertions.assertThat(book).isIn(shelf.getBooksOnShelf());
    }

    @Test
    void removeShelf_checkIfBookRemoved(){
        Room room = new Room();
        Shelf shelf = new Shelf(room, "Action", 400,1);
        Book book = new Book("Welt", "Peter Hans", "Natur", shelf, 100);
        shelf.addBookOnShelf(book);
        shelf.removeBookOnShelf(book);
        Assertions.assertThat(book).isNotIn(shelf.getBooksOnShelf());
    }
}
