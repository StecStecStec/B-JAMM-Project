package hwr.oop.library;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class CreateInstancesTest {
    @Test
    void createBook_checkRightAssignment() {
        Room room = Room.createNewRoom(5);
        Room room2 = Room.createNewRoom(5);
        Shelf shelf = Shelf.createNewShelf(room, "Action", 400, 1);
        Shelf shelf2 = Shelf.createNewShelf(room2, "Action", 400, 1);
        Book book = Book.createNewBook("Welt", "Peter Hans", "Natur", shelf, 100, 3);
        Book book2 = Book.createNewBook("Welt", "Peter Hans", "Natur", shelf2, -10000, 10);
        Book book3 = Book.createNewBook("Welt", "Peter Hans", "Natur", shelf2, 0, 10);
        Assertions.assertThat(book.getBookID()).isNotNull();
        Assertions.assertThat(book.getBookTitle()).isEqualTo("Welt");
        Assertions.assertThat(book.getBookAuthor()).isEqualTo("Peter Hans");
        Assertions.assertThat(book.getBookGenre()).isEqualTo("Natur");
        Assertions.assertThat(book.getShelf()).isEqualTo(shelf);
        Assertions.assertThat(book.getBookCondition()).isEqualTo(100);
        Assertions.assertThat(book.getBookWidth()).isEqualTo(3);
        Assertions.assertThat(book).isIn(shelf.getBooksOnShelf());
        Assertions.assertThat(shelf.getRemainingSpace()).isEqualTo(397);
        Assertions.assertThat(book2.getBookCondition()).isEqualTo(-1);
        Assertions.assertThat(book3.getBookCondition()).isZero();
    }

    @Test
    void createBookFails_checkExceptionRaise() {
        Room room = Room.createNewRoom(5);
        Shelf shelf = Shelf.createNewShelf(room, "Action", 1, 1);
        Book.createNewBook("Welt", "Peter Hans", "Natur", shelf, 100, 1);
        Assertions.assertThatThrownBy(() -> Book.createNewBook("Welt2", "Peter Hans", "Natur", shelf, 100, 1)).hasMessage("Added book to shelf with not enough space.");
    }

    @Test
    void createLibrarian_checkRightAssignment() {
        Librarian librarian = Librarian.createNewLibrarian("Max", "Mustermann", "01.01.1999");
        Assertions.assertThat(librarian.getLibrarianName()).isEqualTo("Max");
        Assertions.assertThat(librarian.getLibrarianSurname()).isEqualTo("Mustermann");
        Assertions.assertThat(librarian.getLibrarianBirthday()).isEqualTo("01.01.1999");
        Assertions.assertThat(librarian.getLibrarianID()).isNotNull();
    }

    @Test
    void createRoom_checkRightAssignment() {
        Room room = Room.createNewRoom(5);
        Assertions.assertThat(room.getRoomID()).isNotNull();
        Assertions.assertThat(room.getShelfLimit()).isEqualTo(5);
    }

    @Test
    void createShelf_checkRightAssignment() {
        Room room = Room.createNewRoom(5);
        Shelf shelf = Shelf.createNewShelf(room, "Action", 400, 1);
        Assertions.assertThat(shelf.getRoomIn()).isEqualTo(room);
        Assertions.assertThat(shelf.getGenre()).isEqualTo("Action");
        Assertions.assertThat(shelf.getShelfWidth()).isEqualTo(400);
        Assertions.assertThat(shelf.getBoardNumber()).isEqualTo(1);
        Assertions.assertThat(shelf.getShelfID()).isNotNull();
        Assertions.assertThat(shelf).isIn(room.getShelfList());
    }

    @Test
    void createShelfFails_checkExceptionRaise() {
        Room room = Room.createNewRoom(1);
        Shelf.createNewShelf(room, "Action", 400, 1);
        Assertions.assertThatThrownBy(() -> Shelf.createNewShelf(room, "Action", 2, 1)).hasMessage("Added shelf to room with not enough space.");
    }

    @Test
    void createVisitor_checkRightAssignment() {
        Visitor visitor = Visitor.createNewVisitor("Max", "Mustermann", "01.01.1999", "max.mustermann@gmx.de");
        Assertions.assertThat(visitor.getVisitorName()).isEqualTo("Max");
        Assertions.assertThat(visitor.getVisitorSurname()).isEqualTo("Mustermann");
        Assertions.assertThat(visitor.getVisitorBirthday()).isEqualTo("01.01.1999");
        Assertions.assertThat(visitor.getVisitorEmailAddress()).isEqualTo("max.mustermann@gmx.de");
        Assertions.assertThat(visitor.getVisitorID()).isNotNull();
    }
}
