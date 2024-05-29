package hwr.oop.library;

import hwr.oop.library.cli.CLI;
import hwr.oop.library.domain.*;
import hwr.oop.library.persistence.CSVAdapter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CSVAdapterTest {

    private Library library;
    private CSVAdapter csvAdapter;
    @BeforeEach
    void setUp() {
        URL resourceUrl = getClass().getClassLoader().getResource("csvTestFiles");
        assert resourceUrl != null;
        File directory = new File(resourceUrl.getFile());
        String path = directory.getAbsolutePath() + "/";
        csvAdapter = new CSVAdapter(path);
        library = Library.createNewLibrary();
    }
    //@Test
    void loadClearAndSaveCSV() {
        Room room = Room.createNewRoom(library, 5);
        library.addRoom(room);
        Shelf shelf = Shelf.createNewShelf(library, room, "Action", 400, 1);
        library.addShelf(shelf);
        Book book1 = Book.createNewBook(library, "Welt", "Peter Hans", "Natur", shelf, 100, 3);
        library.addBook(book1);
        Book book2 = Book.createNewBook(library, "Welten", "Peter Hansen", "Naturen", shelf, 100, 5);
        library.addBook(book2);
        Visitor visitor1 = Visitor.createNewVisitor(library, "Max", "Mustermann", "01.01.1999", "max.mustermann@gmx.de");
        library.addVisitor(visitor1);
        Visitor visitor2 = Visitor.createNewVisitor(library, "Maxia", "Mustermannia", "01.02.1999", "max.mustermannia@gmx.de");
        library.addVisitor(visitor2);
        Librarian librarian = Librarian.createNewLibrarian(library, "Maxa", "Mustermanna", "01.01.2000");
        library.addLibrarian(librarian);
        book2.borrow(visitor2);
        visitor2.addBookToReturn(book2);

        assertThat(library.getRoomList()).contains(room);
        assertThat(library.getShelfList()).contains(shelf);
        assertThat(library.getBookList()).contains(book1)
                .contains(book2);
        assertThat(library.getVisitorList()).contains(visitor1)
                .contains(visitor2);
        assertThat(library.getLibrarianList()).contains(librarian);

        assertThat(library.getRoomList()).isEmpty();
        assertThat(library.getShelfList()).isEmpty();
        assertThat(library.getBookList()).isEmpty();
        assertThat(library.getVisitorList()).isEmpty();
        assertThat(library.getLibrarianList()).isEmpty();

        assertThat(room).isEqualTo(library.getRoomList().getFirst());
        assertThat(shelf).isEqualTo(library.getShelfList().getFirst());
        assertThat(book1).isEqualTo(library.getBookList().getFirst());
        assertThat(book2).isEqualTo(library.getBookList().getLast());
        assertThat(visitor1).isEqualTo(library.getVisitorList().getFirst());
        assertThat(visitor2).isEqualTo(library.getVisitorList().getLast());
        assertThat(librarian).isEqualTo(library.getLibrarianList().getFirst());

        library.deleteVisitor(visitor1);
        library.deleteVisitor(visitor2);
        library.deleteLibrarian(librarian);
        library.deleteBook(book1);
        library.deleteBook(book2);
    }

    @Test
    void testCSVAdapter() throws FileNotFoundException {
        final OutputStream outputStream = new ByteArrayOutputStream();
        final var consoleUI = new CLI(outputStream);
        CSVAdapter csvAdapter = new CSVAdapter("invalid_path_to_file.csv");

        assertThrows(RuntimeException.class, csvAdapter::loadLibrary);
    }

    @AfterEach
    void tearDown() {
        csvAdapter.saveLibrary(library);
    }
}

