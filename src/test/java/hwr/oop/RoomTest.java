package hwr.oop;

import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;

public class RoomTest {
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
    Assertions.assertThat(room.getShelfList()).isNotNull();
  }
}
