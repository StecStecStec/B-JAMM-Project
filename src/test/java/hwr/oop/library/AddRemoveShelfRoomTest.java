package hwr.oop.library;

import hwr.oop.library.domain.Library;
import hwr.oop.library.domain.Room;
import hwr.oop.library.domain.Shelf;
import hwr.oop.library.persistence.CSVAdapter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

class AddRemoveShelfRoomTest {

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

    @Test
    void addShelfToRoom_checkThatTheRoomWasCorrectlyAddedToList() {
        Room room = Room.createNewRoom(library, 5);
        library.addRoom(room);
        Shelf shelf = Shelf.createNewShelf(library, room, "Action", 400, 1);
        library.addShelf(shelf);
        room.roomAddShelf(shelf);
        assertThat(shelf).isIn(room.getShelfList());
    }

    @Test
    void removeShelfFromRoom_checkThatTheRoomWasCorrectlyRemovedFromList() {
        Room room = Room.createNewRoom(library, 5);
        library.addRoom(room);
        Shelf shelf = Shelf.createNewShelf(library, room, "Action", 400, 1);
        library.addShelf(shelf);
        room.roomRemoveShelf(shelf);
        assertThat(shelf).isNotIn(room.getShelfList());
    }

    @AfterEach
    void tearDown() {
        csvAdapter.saveLibrary(library);
    }
}
