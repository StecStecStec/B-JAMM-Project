package hwr.oop.library;

import hwr.oop.library.domain.Room;
import hwr.oop.library.domain.Shelf;
import hwr.oop.library.persistence.CSVAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

class AddRemoveShelfRoomTest {

    private String path;
    @BeforeEach
    void setUp() {
        URL resourceUrl = getClass().getClassLoader().getResource("csvTestFiles");
        assert resourceUrl != null;
        File directory = new File(resourceUrl.getFile());
        path = directory.getAbsolutePath() +"/";
    }
    @Test
    void addShelfToRoom_checkThatTheRoomWasCorrectlyAddedToList() {
        CSVAdapter csvAdapter = new CSVAdapter(path);

        Room room = Room.createNewRoom(csvAdapter, 5);
        Shelf shelf = Shelf.createNewShelf(csvAdapter, room, "Action", 400, 1);
        room.roomAddShelf(shelf);
        assertThat(shelf).isIn(room.getShelfList());
    }

    @Test
    void removeShelfFromRoom_checkThatTheRoomWasCorrectlyRemovedFromList() {
        CSVAdapter csvAdapter = new CSVAdapter(path);

        Room room = Room.createNewRoom(csvAdapter, 5);
        Shelf shelf = Shelf.createNewShelf(csvAdapter, room, "Action", 400, 1);
        room.roomRemoveShelf(shelf);
        assertThat(shelf).isNotIn(room.getShelfList());
    }
}
