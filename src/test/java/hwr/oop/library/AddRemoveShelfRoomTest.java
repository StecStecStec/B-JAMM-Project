package hwr.oop.library;

import hwr.oop.library.domain.Room;
import hwr.oop.library.domain.Shelf;
import hwr.oop.library.persistance.CSVAdapter;
import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;

class AddRemoveShelfRoomTest {
    @Test
    void addShelfToRoom_checkThatTheRoomWasCorrectlyAddedToList() {
        CSVAdapter csvAdapter = new CSVAdapter(".\\src\\test\\resources\\csvTestFiles\\");
        Room room = Room.createNewRoom(csvAdapter, 5);
        Shelf shelf = Shelf.createNewShelf(csvAdapter, room, "Action", 400, 1);
        room.roomAddShelf(shelf);
        Assertions.assertThat(shelf).isIn(room.getShelfList());
    }

    @Test
    void removeShelfFromRoom_checkThatTheRoomWasCorrectlyRemovedFromList() {
        CSVAdapter csvAdapter = new CSVAdapter(".\\src\\test\\resources\\csvTestFiles\\");
        Room room = Room.createNewRoom(csvAdapter, 5);
        Shelf shelf = Shelf.createNewShelf(csvAdapter, room, "Action", 400, 1);
        room.roomRemoveShelf(shelf);
        Assertions.assertThat(shelf).isNotIn(room.getShelfList());
    }
}
