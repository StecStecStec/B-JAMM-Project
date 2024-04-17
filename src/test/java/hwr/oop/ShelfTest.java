package hwr.oop;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

public class ShelfTest {
    @Test
    void createShelf_checkRightAssignment(){
        Room room = new Room();
        Shelf shelf = new Shelf(room, "Action", 400,1);
        Assertions.assertThat(shelf.getRoomIn()).isEqualTo(room);
        Assertions.assertThat(shelf.getGenre()).isEqualTo("Action");
        Assertions.assertThat(shelf.getShelfWidth()).isEqualTo(400);
        Assertions.assertThat(shelf.getBoardNumber()).isEqualTo(1);
        Assertions.assertThat(shelf.getRemainingSpace()).isEqualTo(400);

        Assertions.assertThat(shelf.getShelfID()).isNotNull();
    }

    @Test
    void addBook_checkIfBookAdded(){
        Room room = new Room();
        Shelf shelf = new Shelf(room, "Action", 400,1);
        Book book = new Book("Welt", "Peter Hans", "Natur", shelf, 100, 3);
        Assertions.assertThat(book).isIn(shelf.getBooksOnShelf());
        Assertions.assertThat(shelf.getRemainingSpace()).isEqualTo(397);
    }

    @Test
    void addBookFails_checkExceptionRaise(){
        Room room = new Room();
        Shelf shelf1 = new Shelf(room, "Action", 3,1);
        Shelf shelf2 = new Shelf(room, "Action", 2,1);
        Book book = new Book("Welt", "Peter Hans", "Natur", shelf1, 100, 3);
        Assertions.assertThatThrownBy(() -> shelf2.addBookOnShelf(book)).hasMessage("Added book to shelf with not enough space.");
    }

    @Test
    void removeShelf_checkIfBookRemoved(){
        Room room = new Room();
        Shelf shelf = new Shelf(room, "Action", 400,1);
        Book book = new Book("Welt", "Peter Hans", "Natur", shelf, 100, 3);
        shelf.removeBookOnShelf(book);
        Assertions.assertThat(book).isNotIn(shelf.getBooksOnShelf());
        Assertions.assertThat(shelf.getRemainingSpace()).isEqualTo(400);
    }

    @Test
    void testEqualsMethod() {
        Room room = new Room();
        Shelf shelf1 = new Shelf(room, "Action", 400,1);
        Shelf shelf2 = new Shelf(room, "Action", 400,1);
        Shelf shelf3 = new Shelf(room, "Action", 400,1);

        Assertions.assertThat(shelf1)
                //Comparison with null should be return false
                .isNotNull()
                //Comparison with an object of another class should be return false
                .isNotEqualTo(room);



        Assertions.assertThat(shelf1.equals(shelf2)).isFalse();

        UUID shelfID = shelf1.getShelfID();
        shelf2.setShelfID(shelfID);
        Assertions.assertThat(shelf1.equals(shelf2)).isTrue();
    }

    @Test
    void testHashCodeMethod() {
        Room room = new Room();
        Shelf shelf1 = new Shelf(room, "Action", 400,1);
        Shelf shelf2 = new Shelf(room, "Action", 400,1);

        Assertions.assertThat(shelf1.hashCode()).isNotEqualTo(shelf2.hashCode());

        UUID shelfID = shelf1.getShelfID();
        shelf2.setShelfID(shelfID);
        Assertions.assertThat(shelf1).hasSameHashCodeAs(shelf2);
    }

}
