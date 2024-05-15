package hwr.oop.library;

import hwr.oop.library.domain.Room;
import hwr.oop.library.domain.Shelf;
import hwr.oop.library.persistance.CSVAdapter;
import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;

import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

class AddRemoveShelfRoomTest {
    @Test
    void addShelfToRoom_checkThatTheRoomWasCorrectlyAddedToList() {
        String path = "/csvTestFiles/";
        InputStream stream = getClass().getResourceAsStream(path);
        assert stream != null;
        CSVAdapter csvAdapter = new CSVAdapter(stream.toString());

        Room room = Room.createNewRoom(csvAdapter, 5);
        Shelf shelf = Shelf.createNewShelf(csvAdapter, room, "Action", 400, 1);
        room.roomAddShelf(shelf);
        assertThat(shelf).isIn(room.getShelfList());
    }

    @Test
    void removeShelfFromRoom_checkThatTheRoomWasCorrectlyRemovedFromList() {
        String path = "/csvTestFiles/";
        InputStream stream = getClass().getResourceAsStream(path);
        assert stream != null;
        CSVAdapter csvAdapter = new CSVAdapter(stream.toString());

        Room room = Room.createNewRoom(csvAdapter, 5);
        Shelf shelf = Shelf.createNewShelf(csvAdapter, room, "Action", 400, 1);
        room.roomRemoveShelf(shelf);
        assertThat(shelf).isNotIn(room.getShelfList());
    }
}
