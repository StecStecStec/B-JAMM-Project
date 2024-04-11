package hwr.oop;

import java.util.List;
import java.util.Objects;
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

    public void roomRemoveShelf(Shelf shelf) {
        shelfList.remove(shelf);
    }

    //needed for tests
    public void setRoomID(UUID roomID) {
        this.roomID = roomID;
    }

    public void setShelfList(List<Shelf> shelfList) {
        this.shelfList = shelfList;
    }

    public Room() {
        this.shelfList = new ArrayList<Shelf>();
        this.shelfLimit = 5;
        this.roomID = UUID.randomUUID();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return shelfLimit == room.shelfLimit && Objects.equals(shelfList, room.shelfList) && Objects.equals(roomID, room.roomID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shelfList, shelfLimit, roomID);
    }
}
