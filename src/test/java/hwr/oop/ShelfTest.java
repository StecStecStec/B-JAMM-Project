package hwr.oop;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

public class ShelfTest {
    @Test
    void createShelf_checkRightAssignment(){
        Room room = Room.createNewRoom();
        Shelf shelf = Shelf.createNewShelf(room, "Action", 400,1);
        Assertions.assertThat(shelf.getRoomIn()).isEqualTo(room);
        Assertions.assertThat(shelf.getGenre()).isEqualTo("Action");
        Assertions.assertThat(shelf.getShelfWidth()).isEqualTo(400);
        Assertions.assertThat(shelf.getBoardNumber()).isEqualTo(1);

        Assertions.assertThat(shelf.getShelfID()).isNotNull();
    }

    @Test
    void addBook_checkIfBookAdded(){
        Room room = Room.createNewRoom();
        Shelf shelf = Shelf.createNewShelf(room, "Action", 400,1);
        Book book = new Book("Welt", "Peter Hans", "Natur", shelf, 100);
        shelf.addBookOnShelf(book);
        Assertions.assertThat(book).isIn(shelf.getBooksOnShelf());
    }

    @Test
    void removeShelf_checkIfBookRemoved(){
        Room room = Room.createNewRoom();
        Shelf shelf = Shelf.createNewShelf(room, "Action", 400,1);
        Book book = new Book("Welt", "Peter Hans", "Natur", shelf, 100);
        shelf.removeBookOnShelf(book);
        Assertions.assertThat(book).isNotIn(shelf.getBooksOnShelf());
    }

    @Test
    void testEqualsMethod() {
        UUID uuid = UUID.randomUUID();
        Room room = Room.createNewRoom();
        Shelf shelf1 = Shelf.createCompleteNewShelf(uuid,room, "Action", 400,1);
        Shelf shelf2 = Shelf.createCompleteNewShelf(uuid,room, "Action", 400,1);
        Shelf shelf3 = Shelf.createNewShelf(room, "Action", 400,1);

        Assertions.assertThat(shelf1)
                //Comparison with null should be return false
                .isNotNull()
                //Comparison with an object of another class should be return false
                .isNotEqualTo(room);



        Assertions.assertThat(shelf1.equals(shelf3)).isFalse();

        //UUID shelfID = shelf1.getShelfID();
        //shelf2.setShelfID(shelfID);
        Assertions.assertThat(shelf1.equals(shelf2)).isTrue();
    }

    @Test
    void testHashCodeMethod() {
        UUID uuid = UUID.randomUUID();

        Room room = Room.createNewRoom();
        Shelf shelf1 = Shelf.createCompleteNewShelf(uuid,room, "Action", 400,1);
        Shelf shelf2 = Shelf.createCompleteNewShelf(uuid,room, "Action", 400,1);
        Shelf shelf3 = Shelf.createNewShelf(room, "Action", 400,1);

        Assertions.assertThat(shelf1.hashCode()).isNotEqualTo(shelf3.hashCode());


        Assertions.assertThat(shelf1).hasSameHashCodeAs(shelf2);
    }

}
