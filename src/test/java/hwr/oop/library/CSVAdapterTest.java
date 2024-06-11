package hwr.oop.library;

import hwr.oop.library.cli.MainLibrary;
import hwr.oop.library.domain.*;
import hwr.oop.library.persistence.CSVAdapter;
import hwr.oop.library.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CSVAdapterTest {

  private Library library = Library.createNewLibrary();
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
  void createDirectoryWithAllFilesTest() throws IOException {
    new CSVAdapter(List.of("init", "DIRECTORY"), "test");

    String directory = Paths.get("src", "test", "resources").resolve("DIRECTORY").toString();
    Path path6 = Path.of(directory);
    assertThat(Files.isDirectory(path6)).isTrue();
    Path path1 = Path.of(directory, "Room.csv");
    assertThat(Files.exists(path1)).isTrue();
    Path path2 = Path.of(directory, "Shelf.csv");
    assertThat(Files.exists(path2)).isTrue();
    Path path3 = Path.of(directory, "Visitor.csv");
    assertThat(Files.exists(path3)).isTrue();
    Path path4 = Path.of(directory, "Librarian.csv");
    assertThat(Files.exists(path4)).isTrue();
    Path path5 = Path.of(directory, "Book.csv");
    assertThat(Files.exists(path5)).isTrue();

    Files.delete(path1);
    Files.delete(path2);
    Files.delete(path3);
    Files.delete(path4);
    Files.delete(path5);
    Files.delete(path6);
  }

  @Test
  void createDirectoryWithInvalidInputTest() {
    assertThatThrownBy(() -> new CSVAdapter(List.of("createVisitor", "arguments"), "test"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage(
            "Path is null or empty\nUsage: [option] [Name] [Surname] [Birthday] [Email] [Folder]\n");
  }

  @Test
  void loadClearAndSaveCSV() {
    Room room = Room.createNewRoom(library, 5);
    Shelf shelf = Shelf.createNewShelf(library, room, "Action", 400, 1);
    Book book1 =
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
    Book book2 =
        new Book.Builder()
            .library(library)
            .bookID(UUID.randomUUID())
            .title("Welten")
            .author("Peter Hansen")
            .genre("Naturen")
            .shelf(shelf)
            .bookCondition(100)
            .bookWidth(5)
            .build();
    Visitor visitor1 =
        Visitor.createNewVisitor(
            library, "Max", "Mustermann", "01.01.1999", "max.mustermann@gmx.de");
    Visitor visitor2 =
        Visitor.createNewVisitor(
            library, "Maxia", "Mustermannia", "01.02.1999", "max.mustermannia@gmx.de");
    Librarian librarian =
        Librarian.createNewLibrarian(library, "Maxa", "Mustermanna", "01.01.2000");
    book2.borrow(visitor2);
    visitor2.addBookToReturn(book2);

    assertThat(library.getRoomList()).contains(room);
    assertThat(library.getShelfList()).contains(shelf);
    assertThat(library.getBookList()).contains(book1).contains(book2);
    assertThat(library.getVisitorList()).contains(visitor1).contains(visitor2);
    assertThat(library.getLibrarianList()).contains(librarian);

    persistence.saveLibrary(library);

    library.deleteRoom(room);
    assertThat(library.getRoomList()).doesNotContain(room).isEmpty();

    library.deleteShelf(shelf);
    assertThat(library.getShelfList()).doesNotContain(shelf).isEmpty();

    library.deleteBook(book1);
    library.deleteBook(book2);
    assertThat(library.getBookList()).doesNotContain(book1).doesNotContain(book2).isEmpty();

    library.deleteVisitor(visitor1);
    library.deleteVisitor(visitor2);
    assertThat(library.getVisitorList())
        .doesNotContain(visitor1)
        .doesNotContain(visitor2)
        .isEmpty();

    library.deleteLibrarian(librarian);
    assertThat(library.getLibrarianList()).doesNotContain(librarian).isEmpty();

    library = persistence.loadLibrary();

    assertThat(room).isEqualTo(library.getRoomList().getFirst());
    assertThat(shelf).isEqualTo(library.getShelfList().getFirst());
    assertThat(book1).isEqualTo(library.getBookList().getFirst());
    assertThat(book2).isEqualTo(library.getBookList().getLast());
    assertThat(visitor1).isEqualTo(library.getVisitorList().getFirst());
    assertThat(visitor2).isEqualTo(library.getVisitorList().getLast());
    assertThat(librarian).isEqualTo(library.getLibrarianList().getFirst());
  }

  @Test
  void testCSVAdapter() {
    Persistence persistence2 = new CSVAdapter("invalid_path_to_file.csv");

    assertThrows(RuntimeException.class, persistence2::loadLibrary);
  }

  @AfterEach
  void tearDown() {
    persistence.saveLibrary(library);
  }

  @AfterAll
  static void cleanUp() throws IOException {
    Path directory1 =
        Paths.get(System.getProperty("user.dir"))
            .resolve("src")
            .resolve("test")
            .resolve("resources")
            .resolve("DIRECTORY");
    Path directory2 =
        Paths.get(System.getProperty("user.dir"))
            .resolve("src")
            .resolve("test")
            .resolve("resources")
            .resolve("arguments");

    Files.deleteIfExists(Paths.get(directory1.toString(), "Book.csv"));
    Files.deleteIfExists(Paths.get(directory1.toString(), "Shelf.csv"));
    Files.deleteIfExists(Paths.get(directory1.toString(), "Librarian.csv"));
    Files.deleteIfExists(Paths.get(directory1.toString(), "Visitor.csv"));
    Files.deleteIfExists(Paths.get(directory1.toString(), "Room.csv"));
    Files.deleteIfExists(directory1);

    Files.deleteIfExists(Paths.get(directory2.toString(), "Book.csv"));
    Files.deleteIfExists(Paths.get(directory2.toString(), "Shelf.csv"));
    Files.deleteIfExists(Paths.get(directory2.toString(), "Librarian.csv"));
    Files.deleteIfExists(Paths.get(directory2.toString(), "Visitor.csv"));
    Files.deleteIfExists(Paths.get(directory2.toString(), "Room.csv"));
    Files.deleteIfExists(directory2);
  }
}
