package hwr.oop.library;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.ArrayList;

public class Room {

    private List<Shelf> shelfList;
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
        if (this.shelfList.size() >= this.shelfLimit) {
            throw new IllegalArgumentException("Added shelf to room with not enough space.");
        }
        shelfList.add(shelf);
    }

    public void roomRemoveShelf(Shelf shelf) {
        shelfList.remove(shelf);
    }

    public static Room createNewRoom(CSVAdapter csvAdapter, int shelfLimit) {
        return new Room(csvAdapter, UUID.randomUUID(), shelfLimit);
    }

    public static Room createCompleteNewRoom(CSVAdapter csvAdapter, UUID uuid, int shelfLimit) {
        return new Room(csvAdapter, uuid, shelfLimit);
    }

    private Room(CSVAdapter csvAdapter, UUID uuid, int shelfLimit) {
        this.shelfLimit = shelfLimit;
        this.shelfList = new ArrayList<>(this.shelfLimit);
        this.roomID = uuid;
        csvAdapter.addRoom(this);
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

