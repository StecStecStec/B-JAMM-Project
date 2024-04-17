package hwr.oop;

import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

class RoomTest {
  @Test
  void createRoom_checkRightAttributesUponCreation() {
    Room room = new Room();
    Assertions.assertThat(room.getRoomID()).isNotNull();
    Assertions.assertThat(room.getShelfLimit()).isEqualTo(5);
  }

  @Test
  void appointShelfToRoom_checkThatTheRoomWasCorrectlyAddedToList() {
    Room room = new Room();
    Shelf shelf = new Shelf(room, "Action", 400,1);
    room.roomAddShelf(shelf);
    Assertions.assertThat(shelf).isIn(room.getShelfList());
  }

  @Test
  void removeShelfFromRoom_checkThatTheRoomWasCorrectlyRemovedFromList() {
    Room room = new Room();
    Shelf shelf = new Shelf(room, "Action", 400,1);
    room.roomAddShelf(shelf);
    room.roomRemoveShelf(shelf);
    Assertions.assertThat(shelf).isNotIn(room.getShelfList());
  }

  @Test
  void testEqualsMethod() {
    Room room1 = new Room();
    Room room2 = new Room();
    Room room3 = new Room();

    Assertions.assertThat(room1)
            //Comparison with null should be return false
            .isNotNull()
            //Comparison with an object of another class should be return false
            .isNotEqualTo(new Shelf(room1, "Action", 400, 1));

    List<Shelf> shelfList = new ArrayList<>();
    shelfList.add(new Shelf(room3, "Fiction", 200, 1));
    shelfList.add(new Shelf(room3, "Science", 100, 2));
    room3.setShelfList(shelfList);
    Assertions.assertThat(room3.getShelfList()).isEqualTo(shelfList);

    Assertions.assertThat(room1.equals(room2)).isFalse();

    UUID roomID = room1.getRoomID();
    List<Shelf> shelfList2 = room1.getShelfList();
    room2.setRoomID(roomID);
    room2.setShelfList(shelfList2);
    Assertions.assertThat(room1.equals(room2)).isTrue();
  }

  @Test
  void testHashCodeMethod() {
    Room room1 = new Room();
    Room room2 = new Room();

    Assertions.assertThat(room1.hashCode()).isNotEqualTo(room2.hashCode());

    UUID roomID = room1.getRoomID();
    List<Shelf> shelfList2 = room1.getShelfList();
    room2.setRoomID(roomID);
    room2.setShelfList(shelfList2);
    Assertions.assertThat(room1).hasSameHashCodeAs(room2);
  }
}
