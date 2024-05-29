package hwr.oop.library;

import hwr.oop.library.domain.*;
import hwr.oop.library.persistence.CSVAdapter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CreateInstancesTest {


    private Library library;
    private CSVAdapter csvAdapter;

    private String path;

    @BeforeEach
    void setUp() {
        URL resourceUrl = getClass().getClassLoader().getResource("csvTestFiles");
        assert resourceUrl != null;
        File directory = new File(resourceUrl.getFile());
        String path = directory.getAbsolutePath() + "/";
        csvAdapter = new CSVAdapter(path);
        library = Library.createNewLibrary();
    }

    @Test
    void createBook_checkRightAssignment() {
        Room room = Room.createNewRoom(library, 5);
        library.addRoom(room);
        Shelf shelf = Shelf.createNewShelf(library, room, "Action", 400, 1);
        library.addShelf(shelf);
        Shelf shelf1 = Shelf.createNewShelf(library, room, "Action", 400, 2);
        library.addShelf(shelf1);
        Book book = Book.createNewBook(library, "Welt", "Peter Hans", "Natur", shelf, 50, 3);
        library.addBook(book);
        Book book1 = Book.createNewBook(library, "Welt", "Peter Hans", "Natur", shelf1, 110, 31);
        library.addBook(book1);
        Book book2 = Book.createNewBook(library, "Welt", "Peter Hans", "Natur", shelf1, -110, 31);
        library.addBook(book2);
        Book book3 = Book.createNewBook(library, "Welt", "Peter Hans", "Natur", shelf1, 0, 31);
        library.addBook(book3);
        Book book4 = Book.createNewBook(library, "Welt", "Peter Hans", "Natur", shelf1, 100, 31);
        library.addBook(book4);
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
                .isIn(library.getBookList());
        assertThat(shelf.getRemainingSpace()).isEqualTo(397);
    }

    @Test
    void createBookFails_checkExceptionRaise() {
        Room room = Room.createNewRoom(library, 5);
        library.addRoom(room);
        Shelf shelf = Shelf.createNewShelf(library, room, "Action", 1, 1);
        library.addShelf(shelf);
        Book book = Book.createNewBook(library, "Welt", "Peter Hans", "Natur", shelf, 100, 1);
        library.addBook(book);
        assertThatThrownBy(() -> Book.createNewBook(library, "Welt2", "Peter Hans", "Natur", shelf, 100, 1)).hasMessage("Added book to shelf with not enough space.");
    }

    @Test
    void createLibrarian_checkRightAssignment() {
        Librarian librarian = Librarian.createNewLibrarian(library, "Max", "Mustermann", "01.01.1999");
        library.addLibrarian(librarian);
        assertThat(librarian.getLibrarianName()).isEqualTo("Max");
        assertThat(librarian.getLibrarianSurname()).isEqualTo("Mustermann");
        assertThat(librarian.getLibrarianBirthday()).isEqualTo("01.01.1999");
        assertThat(librarian.getLibrarianID()).isNotNull();
        assertThat(librarian).isIn(library.getLibrarianList());
    }

    @Test
    void createRoom_checkRightAssignment() {
        Room room = Room.createNewRoom(library, 5);
        library.addRoom(room);
        assertThat(room.getRoomID()).isNotNull();
        assertThat(room.getShelfLimit()).isEqualTo(5);
        assertThat(room).isIn(library.getRoomList());
    }

    @Test
    void createTempRoom_checkRightAssignment() {
        Room room = Room.createTempRoom(library);
        library.addRoom(room);
        assertThat(room.getRoomID()).isNotNull();
        assertThat(room.getShelfLimit()).isEqualTo(10000);
    }

    @Test
    void createShelf_checkRightAssignment() {
        Room room = Room.createNewRoom(library, 5);
        library.addRoom(room);
        Shelf shelf = Shelf.createNewShelf(library, room, "Action", 400, 1);
        library.addShelf(shelf);
        assertThat(shelf.getRoomIn()).isEqualTo(room);
        assertThat(shelf.getGenre()).isEqualTo("Action");
        assertThat(shelf.getShelfWidth()).isEqualTo(400);
        assertThat(shelf.getBoardNumber()).isEqualTo(1);
        assertThat(shelf.getShelfID()).isNotNull();
        assertThat(shelf).isIn(room.getShelfList())
                .isIn(library.getShelfList());
    }

    @Test
    void createShelfFails_checkExceptionRaise() {
        Room room = Room.createNewRoom(library, 1);
        library.addRoom(room);
        assertThatThrownBy(() -> Shelf.createNewShelf(library, room, "Action", 2, 1)).hasMessage("Added shelf to room with not enough space.");
    }

    @Test
    void createTempShelf_checkRightAssignment() {
        Room room = Room.createTempRoom(library);
        library.addRoom(room);
        Shelf shelf = Shelf.createTempShelf(library, room);
        library.addShelf(shelf);
        assertThat(shelf.getRoomIn()).isEqualTo(room);
        assertThat(shelf.getGenre()).isEqualTo("temp");
        assertThat(shelf.getShelfWidth()).isEqualTo(10000);
        assertThat(shelf.getBoardNumber()).isEqualTo(10000);
        assertThat(shelf.getShelfID()).isNotNull();
        assertThat(shelf).isIn(room.getShelfList());
    }

    @Test
    void createVisitor_checkRightAssignment() {
        Visitor visitor = Visitor.createNewVisitor(library, "Max", "Mustermann", "01.01.1999", "max.mustermann@gmx.de");
        library.addVisitor(visitor);
        assertThat(visitor.getVisitorName()).isEqualTo("Max");
        assertThat(visitor.getVisitorSurname()).isEqualTo("Mustermann");
        assertThat(visitor.getVisitorBirthday()).isEqualTo("01.01.1999");
        assertThat(visitor.getVisitorEmailAddress()).isEqualTo("max.mustermann@gmx.de");
        assertThat(visitor.getVisitorID()).isNotNull();
        assertThat(visitor).isIn(library.getVisitorList());
    }

   /* @Test
    void createCSVAdapter_checkRightAssignment() {
        CSVAdapter csvAdapter = new CSVAdapter("test/path");
        assertThat(csvAdapter.getPath()).isEqualTo("test/path");
    }

    */

    @AfterEach
    void tearDown() {
        csvAdapter.saveLibrary(library);
    }
}
