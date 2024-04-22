package hwr.oop.library;

import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

class RoomTest {
    @Test
    void createRoom_checkRightAttributesUponCreation() {
        Room room = Room.createNewRoom(5);
        Assertions.assertThat(room.getRoomID()).isNotNull();
        Assertions.assertThat(room.getShelfLimit()).isEqualTo(5);
    }

    @Test
    void appointShelfToRoom_checkThatTheRoomWasCorrectlyAddedToList() {
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

    @Test
    void testEqualsMethod() {
        UUID uuid = UUID.randomUUID();

        Room room1 = Room.createCompleteNewRoom(uuid, 5);
        Room room2 = Room.createCompleteNewRoom(uuid, 5);
        Room room3 = Room.createNewRoom(5);
        Room room4 = Room.createCompleteNewRoom(uuid, 5);

        Assertions.assertThat(room1)
                //Comparison with null should be return false
                .isNotNull()
                //Comparison with an object of another class should be return false
                .isNotEqualTo(Shelf.createNewShelf(room1, "Action", 400, 1))
                //Comparison with same room should be return true
                .isNotEqualTo(room3);
        Assertions.assertThat(room2).isEqualTo(room4);

    }

    @Test
    void testHashCodeMethod() {
        UUID uuid = UUID.randomUUID();
        Room room1 = Room.createCompleteNewRoom(uuid, 5);
        Room room2 = Room.createCompleteNewRoom(uuid, 5);
        Room room3 = Room.createNewRoom(5);

        Assertions.assertThat(room1.hashCode()).isNotEqualTo(room3.hashCode());
        Assertions.assertThat(room1).hasSameHashCodeAs(room2);
    }

    @Test
    void testShelfLimit() {
        Room room = Room.createNewRoom(5);
        int a = 0;
        while (a <= room.getShelfLimit()) {
            Shelf shelf = Shelf.createNewShelf(room, "Action", 400, 1);
            room.roomAddShelf(shelf);
            a++;
        }

        Shelf shelfTemp = Shelf.createNewShelf(room, "Action", 400, 1);

        room.roomAddShelf(shelfTemp);

        Assertions.assertThat(room.getShelfList()).hasSize(room.getShelfLimit());

    }
}
