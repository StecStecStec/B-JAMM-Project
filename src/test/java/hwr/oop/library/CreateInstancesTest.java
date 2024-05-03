package hwr.oop.library;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class CreateInstancesTest {
    @Test
    void createBook_checkRightAssignment() {
        CSVAdapter csvAdapter = new CSVAdapter("");
        Room room = Room.createNewRoom(csvAdapter, 5);
        Shelf shelf = Shelf.createNewShelf(csvAdapter, room, "Action", 400, 1);
        Shelf shelf1 = Shelf.createNewShelf(csvAdapter, room, "Action", 400, 2);
        Book book = Book.createNewBook(csvAdapter, "Welt", "Peter Hans", "Natur", shelf, 50, 3);
        Book book1 = Book.createNewBook(csvAdapter,"Welt", "Peter Hans", "Natur", shelf1, 110, 31);
        Book book2 = Book.createNewBook(csvAdapter, "Welt", "Peter Hans", "Natur", shelf1, -110, 31);
        Book book3 = Book.createNewBook(csvAdapter, "Welt", "Peter Hans", "Natur", shelf1, 0, 31);
        Assertions.assertThat(book.getBookID()).isNotNull();
        Assertions.assertThat(book.getBookTitle()).isEqualTo("Welt");
        Assertions.assertThat(book.getBookAuthor()).isEqualTo("Peter Hans");
        Assertions.assertThat(book.getBookGenre()).isEqualTo("Natur");
        Assertions.assertThat(book.getShelf()).isEqualTo(shelf);
        Assertions.assertThat(book.getBookCondition()).isEqualTo(50);
        Assertions.assertThat(book1.getBookCondition()).isEqualTo(-1);
        Assertions.assertThat(book2.getBookCondition()).isEqualTo(-1);
        Assertions.assertThat(book3.getBookCondition()).isZero();
        Assertions.assertThat(book.getBookWidth()).isEqualTo(3);
        Assertions.assertThat(book).isIn(shelf.getBooksOnShelf())
                                   .isIn(csvAdapter.getBookList());
        Assertions.assertThat(shelf.getRemainingSpace()).isEqualTo(397);
    }

    @Test
    void createBookFails_checkExceptionRaise() {
        CSVAdapter csvAdapter = new CSVAdapter("");
        Room room = Room.createNewRoom(csvAdapter, 5);
        Shelf shelf = Shelf.createNewShelf(csvAdapter, room, "Action", 1, 1);
        Book.createNewBook(csvAdapter, "Welt", "Peter Hans", "Natur", shelf, 100, 1);
        Assertions.assertThatThrownBy(() -> Book.createNewBook(csvAdapter, "Welt2", "Peter Hans", "Natur", shelf, 100, 1)).hasMessage("Added book to shelf with not enough space.");
    }

    @Test
    void createLibrarian_checkRightAssignment() {
        CSVAdapter csvAdapter = new CSVAdapter("");
        Librarian librarian = Librarian.createNewLibrarian(csvAdapter, "Max", "Mustermann", "01.01.1999");
        Assertions.assertThat(librarian.getLibrarianName()).isEqualTo("Max");
        Assertions.assertThat(librarian.getLibrarianSurname()).isEqualTo("Mustermann");
        Assertions.assertThat(librarian.getLibrarianBirthday()).isEqualTo("01.01.1999");
        Assertions.assertThat(librarian.getLibrarianID()).isNotNull();
        Assertions.assertThat(librarian).isIn(csvAdapter.getLibrarianList());
    }

    @Test
    void createRoom_checkRightAssignment() {
        CSVAdapter csvAdapter = new CSVAdapter("");
        Room room = Room.createNewRoom(csvAdapter, 5);
        Assertions.assertThat(room.getRoomID()).isNotNull();
        Assertions.assertThat(room.getShelfLimit()).isEqualTo(5);
        Assertions.assertThat(room).isIn(csvAdapter.getRoomList());
    }

    @Test
    void createTempRoom_checkRightAssignment() {
        CSVAdapter csvAdapter = new CSVAdapter("");
        Room room = Room.createTempRoom(csvAdapter);
        Assertions.assertThat(room.getRoomID()).isNotNull();
        Assertions.assertThat(room.getShelfLimit()).isEqualTo(10000);
    }

    @Test
    void createShelf_checkRightAssignment() {
        CSVAdapter csvAdapter = new CSVAdapter("");
        Room room = Room.createNewRoom(csvAdapter, 5);
        Shelf shelf = Shelf.createNewShelf(csvAdapter, room, "Action", 400, 1);
        Assertions.assertThat(shelf.getRoomIn()).isEqualTo(room);
        Assertions.assertThat(shelf.getGenre()).isEqualTo("Action");
        Assertions.assertThat(shelf.getShelfWidth()).isEqualTo(400);
        Assertions.assertThat(shelf.getBoardNumber()).isEqualTo(1);
        Assertions.assertThat(shelf.getShelfID()).isNotNull();
        Assertions.assertThat(shelf).isIn(room.getShelfList())
                                    .isIn(csvAdapter.getShelfList());
    }

    @Test
    void createShelfFails_checkExceptionRaise() {
        CSVAdapter csvAdapter = new CSVAdapter("");
        Room room = Room.createNewRoom(csvAdapter, 1);
        Shelf.createNewShelf(csvAdapter, room, "Action", 400, 1);
        Assertions.assertThatThrownBy(() -> Shelf.createNewShelf(csvAdapter, room, "Action", 2, 1)).hasMessage("Added shelf to room with not enough space.");
    }

    @Test
    void createTempShelf_checkRightAssignment() {
        CSVAdapter csvAdapter = new CSVAdapter("");
        Room room = Room.createTempRoom(csvAdapter);
        Shelf shelf = Shelf.createTempShelf(csvAdapter, room);
        Assertions.assertThat(shelf.getRoomIn()).isEqualTo(room);
        Assertions.assertThat(shelf.getGenre()).isEqualTo("temp");
        Assertions.assertThat(shelf.getShelfWidth()).isEqualTo(10000);
        Assertions.assertThat(shelf.getBoardNumber()).isEqualTo(10000);
        Assertions.assertThat(shelf.getShelfID()).isNotNull();
        Assertions.assertThat(shelf).isIn(room.getShelfList());
    }

    @Test
    void createVisitor_checkRightAssignment() {
        CSVAdapter csvAdapter = new CSVAdapter("");
        Visitor visitor = Visitor.createNewVisitor(csvAdapter, "Max", "Mustermann", "01.01.1999", "max.mustermann@gmx.de");
        Assertions.assertThat(visitor.getVisitorName()).isEqualTo("Max");
        Assertions.assertThat(visitor.getVisitorSurname()).isEqualTo("Mustermann");
        Assertions.assertThat(visitor.getVisitorBirthday()).isEqualTo("01.01.1999");
        Assertions.assertThat(visitor.getVisitorEmailAddress()).isEqualTo("max.mustermann@gmx.de");
        Assertions.assertThat(visitor.getVisitorID()).isNotNull();
        Assertions.assertThat(visitor).isIn(csvAdapter.getVisitorList());
    }

    @Test
    void createCSVAdapter_checkRightAssignment() {
        CSVAdapter csvAdapter = new CSVAdapter("test/path");
        Assertions.assertThat(csvAdapter.getPath()).isEqualTo("test/path");
    }
}
