package hwr.oop;

import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

class RoomTest {
    @Test
    void createRoom_checkRightAttributesUponCreation() {
        Room room = Room.createNewRoom();
        Assertions.assertThat(room.getRoomID()).isNotNull();
        Assertions.assertThat(room.getShelfLimit()).isEqualTo(5);
    }

    @Test
    void appointShelfToRoom_checkThatTheRoomWasCorrectlyAddedToList() {
        Room room = Room.createNewRoom();
        Shelf shelf = Shelf.createNewShelf(room, "Action", 400, 1);
        room.roomAddShelf(shelf);
        Assertions.assertThat(shelf).isIn(room.getShelfList());
    }

    @Test
    void removeShelfFromRoom_checkThatTheRoomWasCorrectlyRemovedFromList() {
        Room room = Room.createNewRoom();
        Shelf shelf = Shelf.createNewShelf(room, "Action", 400, 1);
        room.roomRemoveShelf(shelf);
        Assertions.assertThat(shelf).isNotIn(room.getShelfList());
    }

    @Test
    void testEqualsMethod() {
        UUID uuid = UUID.randomUUID();

        Room room1 = Room.createCompleteNewRoom(uuid);
        Room room2 = Room.createCompleteNewRoom(uuid);
        Room room3 = Room.createNewRoom();

        Assertions.assertThat(room1)
                //Comparison with null should be return false
                .isNotNull()
                //Comparison with an object of another class should be return false
                .isNotEqualTo(Shelf.createNewShelf(room1, "Action", 400, 1));

        Assertions.assertThat(room1.equals(room3)).isFalse();
        Assertions.assertThat(room1.equals(room1)).isTrue();

    }

    @Test
    void testShelfLimit() {
        Room room = Room.createNewRoom();
        //room.roomAddShelf(Shelf.createNewShelf());


    }

    @Test
    void testHashCodeMethod() {
        UUID uuid = UUID.randomUUID();
        Room room1 = Room.createCompleteNewRoom(uuid);
        Room room2 = Room.createCompleteNewRoom(uuid);
        Room room3 = Room.createNewRoom();

        Assertions.assertThat(room1.hashCode()).isNotEqualTo(room3.hashCode());
        Assertions.assertThat(room1).hasSameHashCodeAs(room2);
    }
}
