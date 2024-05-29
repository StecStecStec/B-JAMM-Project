package hwr.oop.library.domain;

import hwr.oop.library.persistence.CSVAdapter;

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

<<<<<<< HEAD
    public static Room createNewRoom(CSVAdapter csvAdapter, int shelfLimit) {
        return new Room(csvAdapter, UUID.randomUUID(), shelfLimit);
    }

    public static Room createCompleteNewRoom(CSVAdapter csvAdapter, UUID uuid, int shelfLimit) {
        return new Room(csvAdapter, uuid, shelfLimit);
    }

    //for CSVAdapter
    public static Room createTempRoom(CSVAdapter tempCsvAdapter) {
        return new Room(tempCsvAdapter, UUID.randomUUID(), 10000);
    }

    private Room(CSVAdapter csvAdapter, UUID uuid, int shelfLimit) {
        this.shelfLimit = shelfLimit;
        this.shelfList = new ArrayList<>(this.shelfLimit);
        this.roomID = uuid;
        csvAdapter.addRoom(this);
=======
    public static Room createNewRoom(Library library, int shelfLimit) {
        return new Room(library, UUID.randomUUID(), shelfLimit);
    }

    public static Room createCompleteNewRoom(Library library, UUID uuid, int shelfLimit) {
        return new Room(library, uuid, shelfLimit);
    }

    //for CSVAdapter
    public static Room createTempRoom(Library tempLibrary) {
        return new Room(tempLibrary, UUID.randomUUID(), 10000);
    }

    private Room(Library library, UUID uuid, int shelfLimit) {
        this.shelfLimit = shelfLimit;
        this.shelfList = new ArrayList<>(this.shelfLimit);
        this.roomID = uuid;
        library.addRoom(this);
>>>>>>> 0e4c06e (refactored persistence by adding library class)
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

