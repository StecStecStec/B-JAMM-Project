package hwr.oop;

import java.util.List;
import java.util.UUID;
import java.util.ArrayList;

public class Room {

  private List<Shelf> shelfList;
  private int shelfLimit;
  private UUID roomID;

  public UUID getRoomID() {
    return roomID;
  }
  public int getShelfLimit() {
    return shelfLimit;
  }
  public List<Shelf> getShelfList() {
    return shelfList;
  }
  public void roomAddShelf(Shelf shelf) {
    shelfList.add(shelf);
  }

  public Room() {
    this.shelfList = new ArrayList<Shelf>();
    this.shelfLimit = 5;
    this.roomID = UUID.randomUUID();
  }
}
