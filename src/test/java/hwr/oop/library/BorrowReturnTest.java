package hwr.oop.library;

import hwr.oop.library.domain.*;
import hwr.oop.library.persistence.CSVAdapter;
import hwr.oop.library.persistence.Persistence;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BorrowReturnTest {

    private final Library library = Library.createNewLibrary();
    private Persistence persistence;
    private static String path = null ;

    @BeforeAll
    static void init() {
        path = pathToDirectory();
    }

    private static String pathToDirectory() {
        try {
            Path currentDirectory = Paths.get(System.getProperty("user.dir"));

            try (Stream<Path> stream = Files.walk(currentDirectory)) {
                Optional<Path> directory = stream
                        .filter(Files::isDirectory)
                        .filter(path -> path.getFileName().toString().equals("csvTestFiles"))
                        .findFirst();

                return directory.map(Path::toString).orElse(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @BeforeEach
    void setUp() {
        persistence = new CSVAdapter(path + "/");
    }

    @Test
    void borrowBook_checkIfBorrowedByIsSetToGivenVisitorAndShelfIsNull() {
        Room room = Room.createNewRoom(library, 5);
        Shelf shelf = Shelf.createNewShelf(library, room, "Action", 400, 1);
        Book book = new Book.Builder()
                .library(library)
                .bookID(UUID.randomUUID())
                .title("Welt")
                .author("Peter Hans")
                .genre("Natur")
                .shelf(shelf)
                .bookCondition(100)
                .bookWidth(3)
                .build();
        Visitor visitor = Visitor.createNewVisitor(library, "Max", "Mustermann", "01.01.1999", "max.mustermann@gmx.de");
        book.borrow(visitor);
        assertThat(book.getBorrowedBy()).isEqualTo(visitor);
        assertThat(book).isIn(visitor.getBorrowedBooks());
        assertThat(book.getShelf()).isNull();
        assertThat(book).isNotIn(shelf.getBooksOnShelf());
        assertThat(shelf.getRemainingSpace()).isEqualTo(400);
        library.deleteRoom(room);
        library.deleteShelf(shelf);
        library.deleteBook(book);
        library.deleteVisitor(visitor);
    }

    @Test
    void borrowBookFails_checkIfBorrowedBookIsNotBorrowable() {
        Room room = Room.createNewRoom(library, 5);
        Shelf shelf = Shelf.createNewShelf(library, room, "Action", 400, 1);
        Book book = new Book.Builder()
                .library(library)
                .bookID(UUID.randomUUID())
                .title("Welt")
                .author("Peter Hans")
                .genre("Natur")
                .shelf(shelf)
                .bookCondition(100)
                .bookWidth(3)
                .build();
        Visitor visitor1 = Visitor.createNewVisitor(library, "Max", "Mustermann", "01.01.1999", "max.mustermann@gmx.de");
        Visitor visitor2 = Visitor.createNewVisitor(library, "Maxa", "Mustermanna", "02.01.1999", "maxa.mustermanna@gmx.de");
        book.borrow(visitor1);
        book.borrow(visitor2);
        assertThat(book.getBorrowedBy()).isNotEqualTo(visitor2);
        assertThat(book).isNotIn(visitor2.getBorrowedBooks());
        library.deleteRoom(room);
        library.deleteShelf(shelf);
        library.deleteBook(book);
        library.deleteVisitor(visitor1);
        library.deleteVisitor(visitor2);
    }

    @Test
    void returnBook_checkIfShelfIsSetToGivenShelfAndBorrowedByIsNull() {
        Room room = Room.createNewRoom(library, 5);
        Shelf shelf = Shelf.createNewShelf(library, room, "Action", 400, 1);
        Book book = new Book.Builder()
                .library(library)
                .bookID(UUID.randomUUID())
                .title("Welt")
                .author("Peter Hans")
                .genre("Natur")
                .shelf(shelf)
                .bookCondition(100)
                .bookWidth(3)
                .build();
        Visitor visitor = Visitor.createNewVisitor(library, "Max", "Mustermann", "01.01.1999", "max.mustermann@gmx.de");
        book.borrow(visitor);
        book.returnBook(shelf);
        assertThat(book.getBorrowedBy()).isNull();
        assertThat(book).isNotIn(visitor.getBorrowedBooks());
        assertThat(book.getShelf()).isEqualTo(shelf);
        assertThat(book).isIn(shelf.getBooksOnShelf());
        assertThat(shelf.getRemainingSpace()).isEqualTo(397);
        library.deleteRoom(room);
        library.deleteShelf(shelf);
        library.deleteBook(book);
        library.deleteVisitor(visitor);
    }

    @Test
    void returnBookFails_checkExceptionRaise() {
        Room room = Room.createNewRoom(library, 5);
        Shelf shelf1 = Shelf.createNewShelf(library, room, "Action", 400, 1);
        Shelf shelf2 = Shelf.createNewShelf(library, room, "Action", 2, 1);
        Book book = new Book.Builder()
                .library(library)
                .bookID(UUID.randomUUID())
                .title("Welt")
                .author("Peter Hans")
                .genre("Natur")
                .shelf(shelf1)
                .bookCondition(100)
                .bookWidth(3)
                .build();
        Visitor visitor = Visitor.createNewVisitor(library, "Max", "Mustermann", "01.01.1999", "max.mustermann@gmx.de");
        book.borrow(visitor);
        assertThatThrownBy(() -> book.returnBook(shelf2)).hasMessage("Added book to shelf with not enough space.");
        library.deleteRoom(room);
        library.deleteShelf(shelf1);
        library.deleteShelf(shelf2);
        library.deleteBook(book);
        library.deleteVisitor(visitor);
    }

    @AfterEach
    void tearDown() {
        persistence.saveLibrary(library);
    }
}
