package hwr.oop.library;

import hwr.oop.library.domain.Library;
import hwr.oop.library.domain.Room;
import hwr.oop.library.domain.Shelf;
import hwr.oop.library.persistence.CSVAdapter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class AddRemoveShelfRoomTest {

    private final Library library = Library.createNewLibrary();
    private CSVAdapter csvAdapter;

    private String pathToDirectory () {
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
        csvAdapter = new CSVAdapter(pathToDirectory());
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
        csvAdapter.saveLibrary(library);
    }
}
