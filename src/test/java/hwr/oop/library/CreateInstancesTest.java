package hwr.oop.library;

import hwr.oop.library.domain.*;
import hwr.oop.library.persistance.CSVAdapter;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CreateInstancesTest {
    @Test
    void createBook_checkRightAssignment() {
        CSVAdapter csvAdapter = new CSVAdapter(".\\src\\test\\resources\\csvTestFiles\\");
        Room room = Room.createNewRoom(csvAdapter, 5);
        Shelf shelf = Shelf.createNewShelf(csvAdapter, room, "Action", 400, 1);
        Shelf shelf1 = Shelf.createNewShelf(csvAdapter, room, "Action", 400, 2);
        Book book = Book.createNewBook(csvAdapter, "Welt", "Peter Hans", "Natur", shelf, 50, 3);
        Book book1 = Book.createNewBook(csvAdapter,"Welt", "Peter Hans", "Natur", shelf1, 110, 31);
        Book book2 = Book.createNewBook(csvAdapter, "Welt", "Peter Hans", "Natur", shelf1, -110, 31);
        Book book3 = Book.createNewBook(csvAdapter, "Welt", "Peter Hans", "Natur", shelf1, 0, 31);
        Book book4 = Book.createNewBook(csvAdapter, "Welt", "Peter Hans", "Natur", shelf1, 100, 31);
        assertThat(book.getBookID()).isNotNull();
        assertThat(book.getBookTitle()).isEqualTo("Welt");
        assertThat(book.getBookAuthor()).isEqualTo("Peter Hans");
        assertThat(book.getBookGenre()).isEqualTo("Natur");
        assertThat(book.getShelf()).isEqualTo(shelf);
        assertThat(book.getBookCondition()).isEqualTo(50);
        assertThat(book1.getBookCondition()).isEqualTo(-1);
        assertThat(book2.getBookCondition()).isEqualTo(-1);
        assertThat(book3.getBookCondition()).isZero();
        assertThat(book4.getBookCondition()).isEqualTo(100);
        assertThat(book.getBookWidth()).isEqualTo(3);
        assertThat(book).isIn(shelf.getBooksOnShelf())
                                   .isIn(csvAdapter.getBookList());
        assertThat(shelf.getRemainingSpace()).isEqualTo(397);
    }

    @Test
    void createBookFails_checkExceptionRaise() {
        CSVAdapter csvAdapter = new CSVAdapter(".\\src\\test\\resources\\csvTestFiles\\");
        Room room = Room.createNewRoom(csvAdapter, 5);
        Shelf shelf = Shelf.createNewShelf(csvAdapter, room, "Action", 1, 1);
        Book.createNewBook(csvAdapter, "Welt", "Peter Hans", "Natur", shelf, 100, 1);
        assertThatThrownBy(() -> Book.createNewBook(csvAdapter, "Welt2", "Peter Hans", "Natur", shelf, 100, 1)).hasMessage("Added book to shelf with not enough space.");
    }

    @Test
    void createLibrarian_checkRightAssignment() {
        CSVAdapter csvAdapter = new CSVAdapter(".\\src\\test\\resources\\csvTestFiles\\");
        Librarian librarian = Librarian.createNewLibrarian(csvAdapter, "Max", "Mustermann", "01.01.1999");
        assertThat(librarian.getLibrarianName()).isEqualTo("Max");
        assertThat(librarian.getLibrarianSurname()).isEqualTo("Mustermann");
        assertThat(librarian.getLibrarianBirthday()).isEqualTo("01.01.1999");
        assertThat(librarian.getLibrarianID()).isNotNull();
        assertThat(librarian).isIn(csvAdapter.getLibrarianList());
    }

    @Test
    void createRoom_checkRightAssignment() {
        CSVAdapter csvAdapter = new CSVAdapter(".\\src\\test\\resources\\csvTestFiles\\");
        Room room = Room.createNewRoom(csvAdapter, 5);
        assertThat(room.getRoomID()).isNotNull();
        assertThat(room.getShelfLimit()).isEqualTo(5);
        assertThat(room).isIn(csvAdapter.getRoomList());
    }

    @Test
    void createTempRoom_checkRightAssignment() {
        CSVAdapter csvAdapter = new CSVAdapter(".\\src\\test\\resources\\csvTestFiles\\");
        Room room = Room.createTempRoom(csvAdapter);
        assertThat(room.getRoomID()).isNotNull();
        assertThat(room.getShelfLimit()).isEqualTo(10000);
    }

    @Test
    void createShelf_checkRightAssignment() {
        CSVAdapter csvAdapter = new CSVAdapter(".\\src\\test\\resources\\csvTestFiles\\");
        Room room = Room.createNewRoom(csvAdapter, 5);
        Shelf shelf = Shelf.createNewShelf(csvAdapter, room, "Action", 400, 1);
        assertThat(shelf.getRoomIn()).isEqualTo(room);
        assertThat(shelf.getGenre()).isEqualTo("Action");
        assertThat(shelf.getShelfWidth()).isEqualTo(400);
        assertThat(shelf.getBoardNumber()).isEqualTo(1);
        assertThat(shelf.getShelfID()).isNotNull();
        assertThat(shelf).isIn(room.getShelfList())
                                    .isIn(csvAdapter.getShelfList());
    }

    @Test
    void createShelfFails_checkExceptionRaise() {
        CSVAdapter csvAdapter = new CSVAdapter(".\\src\\test\\resources\\csvTestFiles\\");
        Room room = Room.createNewRoom(csvAdapter, 1);
        Shelf.createNewShelf(csvAdapter, room, "Action", 400, 1);
        assertThatThrownBy(() -> Shelf.createNewShelf(csvAdapter, room, "Action", 2, 1)).hasMessage("Added shelf to room with not enough space.");
    }

    @Test
    void createTempShelf_checkRightAssignment() {
        CSVAdapter csvAdapter = new CSVAdapter(".\\src\\test\\resources\\csvTestFiles\\");
        Room room = Room.createTempRoom(csvAdapter);
        Shelf shelf = Shelf.createTempShelf(csvAdapter, room);
        assertThat(shelf.getRoomIn()).isEqualTo(room);
        assertThat(shelf.getGenre()).isEqualTo("temp");
        assertThat(shelf.getShelfWidth()).isEqualTo(10000);
        assertThat(shelf.getBoardNumber()).isEqualTo(10000);
        assertThat(shelf.getShelfID()).isNotNull();
        assertThat(shelf).isIn(room.getShelfList());
    }

    @Test
    void createVisitor_checkRightAssignment() {
        CSVAdapter csvAdapter = new CSVAdapter(".\\src\\test\\resources\\csvTestFiles\\");
        Visitor visitor = Visitor.createNewVisitor(csvAdapter, "Max", "Mustermann", "01.01.1999", "max.mustermann@gmx.de");
        assertThat(visitor.getVisitorName()).isEqualTo("Max");
        assertThat(visitor.getVisitorSurname()).isEqualTo("Mustermann");
        assertThat(visitor.getVisitorBirthday()).isEqualTo("01.01.1999");
        assertThat(visitor.getVisitorEmailAddress()).isEqualTo("max.mustermann@gmx.de");
        assertThat(visitor.getVisitorID()).isNotNull();
        assertThat(visitor).isIn(csvAdapter.getVisitorList());
    }

    @Test
    void createCSVAdapter_checkRightAssignment() {
        CSVAdapter csvAdapter = new CSVAdapter("test/path");
        assertThat(csvAdapter.getPath()).isEqualTo("test/path");
    }
}
