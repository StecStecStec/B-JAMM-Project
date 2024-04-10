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
    void createShelf_checkIfBookAdded(){
        Book book = new Book();
        Shelf shelf = new Shelf(room, "Action", 400,1);
        shelf.addBookOnShelf(book);
        Assertions.assertThat(shelf.getBooksOnShelf()).isNotNull();
        }
}
