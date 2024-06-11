package hwr.oop.library;

import hwr.oop.library.cli.MainLibrary;
import hwr.oop.library.domain.Book;
import hwr.oop.library.domain.Library;
import hwr.oop.library.domain.Room;
import hwr.oop.library.domain.Shelf;
import hwr.oop.library.persistence.CSVAdapter;
import hwr.oop.library.persistence.Persistence;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.util.Objects;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class AddRemoveBookShelfTest {

  private final Library library = Library.createNewLibrary();
  private Persistence persistence;
  private static String path = null;

  @BeforeAll
  static void init() throws URISyntaxException {
    path = pathToDirectory();
  }

  private static String pathToDirectory() throws URISyntaxException {
    return Objects.requireNonNull(MainLibrary.class.getClassLoader().getResource("csvTestFiles"))
        .toURI()
        .getPath();
  }

  @BeforeEach
  void setUp() {
    persistence = new CSVAdapter(path + "/");
  }

  @Test
  void addBook_checkIfBookAdded() {
    Room room = Room.createNewRoom(library, 5);
    Shelf shelf = Shelf.createNewShelf(library, room, "Action", 400, 1);
    Book book =
        new Book.Builder()
            .library(library)
            .bookID(UUID.randomUUID())
            .title("Welt")
            .author("Peter Hans")
            .genre("Natur")
            .shelf(shelf)
            .bookCondition(100)
            .bookWidth(3)
            .build();
    assertThat(book).isIn(shelf.getBooksOnShelf());
    library.deleteRoom(room);
    library.deleteShelf(shelf);
    library.deleteBook(book);
  }

  @Test
  void removeShelf_checkIfBookRemoved() {
    Room room = Room.createNewRoom(library, 5);
    Shelf shelf = Shelf.createNewShelf(library, room, "Action", 400, 1);
    Book book =
        new Book.Builder()
            .library(library)
            .bookID(UUID.randomUUID())
            .title("Welt")
            .author("Peter Hans")
            .genre("Natur")
            .shelf(shelf)
            .bookCondition(100)
            .bookWidth(3)
            .build();
    shelf.removeBookOnShelf(book);
    assertThat(book).isNotIn(shelf.getBooksOnShelf());
    library.deleteRoom(room);
    library.deleteShelf(shelf);
    library.deleteBook(book);
  }

  @AfterEach
  void tearDown() {
    persistence.saveLibrary(library);
  }
}
