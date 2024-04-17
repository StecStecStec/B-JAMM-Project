package hwr.oop;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.ArrayList;

public class Room {

    private final List<Shelf> shelfList;
    private final int shelfLimit;
    private final UUID roomID;

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
        if(this.shelfList.size() < this.shelfLimit){
            shelfList.add(shelf);
        }
        //once interface added, add error message
    }

    public void roomRemoveShelf(Shelf shelf) {
        shelfList.remove(shelf);
    }

    public static Room createNewRoom(){
        return new Room(UUID.randomUUID());
    }

    public static Room createCompleteNewRoom(UUID uuid){
        return new Room(uuid);
    }

    private Room(UUID uuid) {
        this.shelfList = new ArrayList<>();
        this.shelfLimit = 5;
        this.roomID = uuid;
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
