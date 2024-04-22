package hwr.oop.library;

import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;

class AddRemoveShelfRoomTest {
    @Test
    void addShelfToRoom_checkThatTheRoomWasCorrectlyAddedToList() {
        Room room = Room.createNewRoom(5);
        Shelf shelf = Shelf.createNewShelf(room, "Action", 400, 1);
        room.roomAddShelf(shelf);
        Assertions.assertThat(shelf).isIn(room.getShelfList());
    }

    @Test
    void removeShelfFromRoom_checkThatTheRoomWasCorrectlyRemovedFromList() {
        Room room = Room.createNewRoom(5);
        Shelf shelf = Shelf.createNewShelf(room, "Action", 400, 1);
        room.roomRemoveShelf(shelf);
        Assertions.assertThat(shelf).isNotIn(room.getShelfList());
    }
}
