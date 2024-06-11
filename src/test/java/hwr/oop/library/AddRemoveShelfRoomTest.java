package hwr.oop.library;

import hwr.oop.library.cli.MainLibrary;
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

import static org.assertj.core.api.Assertions.assertThat;

class AddRemoveShelfRoomTest {

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
  void addShelfToRoom_checkThatTheRoomWasCorrectlyAddedToList() {
    Room room = Room.createNewRoom(library, 5);
    Shelf shelf = Shelf.createNewShelf(library, room, "Action", 400, 1);
    room.roomAddShelf(shelf);
    assertThat(shelf).isIn(room.getShelfList());
    library.deleteRoom(room);
    library.deleteShelf(shelf);
  }

  @Test
  void removeShelfFromRoom_checkThatTheRoomWasCorrectlyRemovedFromList() {
    Room room = Room.createNewRoom(library, 5);
    Shelf shelf = Shelf.createNewShelf(library, room, "Action", 400, 1);
    room.roomRemoveShelf(shelf);
    assertThat(shelf).isNotIn(room.getShelfList());
    library.deleteRoom(room);
    library.deleteShelf(shelf);
  }

  @AfterEach
  void tearDown() {
    persistence.saveLibrary(library);
  }
}
