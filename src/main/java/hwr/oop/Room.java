package hwr.oop;

import java.util.List;
import java.util.UUID;
import java.util.ArrayList;

public class Room {

  private List<Shelf> shelfList;

  private int shelfLimit = 5;

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

  public Room(int shelfLimit) {
    this.shelfList = new ArrayList<Shelf>();
    this.shelfLimit = shelfLimit;
    this.roomID = UUID.randomUUID();
  }
}
