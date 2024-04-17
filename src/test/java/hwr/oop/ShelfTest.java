package hwr.oop;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

public class ShelfTest {
    @Test
    void createShelf_checkRightAssignment(){
        Room room = Room.createNewRoom(5);
        Shelf shelf = Shelf.createNewShelf(room, "Action", 400,1);
        Assertions.assertThat(shelf.getRoomIn()).isEqualTo(room);
        Assertions.assertThat(shelf.getGenre()).isEqualTo("Action");
        Assertions.assertThat(shelf.getShelfWidth()).isEqualTo(400);
        Assertions.assertThat(shelf.getBoardNumber()).isEqualTo(1);

        Assertions.assertThat(shelf.getShelfID()).isNotNull();
        Assertions.assertThat(shelf).isIn(room.getShelfList());
    }

    @Test
    void addBook_checkIfBookAdded(){
        Room room = Room.createNewRoom(5);
        Shelf shelf = Shelf.createNewShelf(room, "Action", 400,1);
        Book book = Book.createNewBook("Welt", "Peter Hans", "Natur", shelf, 100, 3);
        Assertions.assertThat(book).isIn(shelf.getBooksOnShelf());
    }

    @Test
    void addBookFails_checkExceptionRaise(){
        Room room = Room.createNewRoom(5);
        Shelf shelf1 = Shelf.createNewShelf(room, "Action", 3,1);
        Shelf shelf2 = Shelf.createNewShelf(room, "Action", 2,1);
        Book book = Book.createNewBook("Welt", "Peter Hans", "Natur", shelf1, 100, 3);
        Visitor visitor = Visitor.createNewVisitor("Max", "Mustermann", "01.01.1999", "max.mustermann@gmx.de");
        book.borrow(visitor);
        Assertions.assertThatThrownBy(() -> book.returnBook(shelf2)).hasMessage("Added book to shelf with not enough space.");
    }

    @Test
    void removeShelf_checkIfBookRemoved(){
        Room room = Room.createNewRoom(5);
        Shelf shelf = Shelf.createNewShelf(room, "Action", 400,1);
        Book book = Book.createNewBook("Welt", "Peter Hans", "Natur", shelf, 100, 3);
        shelf.removeBookOnShelf(book);
        Assertions.assertThat(book).isNotIn(shelf.getBooksOnShelf());
    }

    @Test
    void testEqualsMethod() {
        UUID uuid = UUID.randomUUID();
        Room room = Room.createNewRoom(5);
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

        Room room = Room.createNewRoom(5);
        Shelf shelf1 = Shelf.createCompleteNewShelf(uuid,room, "Action", 400,1);
        Shelf shelf2 = Shelf.createCompleteNewShelf(uuid,room, "Action", 400,1);
        Shelf shelf3 = Shelf.createNewShelf(room, "Action", 400,1);

        Assertions.assertThat(shelf1.hashCode()).isNotEqualTo(shelf3.hashCode());


        Assertions.assertThat(shelf1).hasSameHashCodeAs(shelf2);
    }

}
