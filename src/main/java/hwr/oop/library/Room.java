package hwr.oop.library;

import java.io.FileOutputStream;
import java.io.IOException;
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
        if (this.shelfList.size() < this.shelfLimit) {
            shelfList.add(shelf);
        }
        //once interface added, add error message
    }

    public void roomRemoveShelf(Shelf shelf) {
        shelfList.remove(shelf);
    }

    public static Room createNewRoom(int shelfLimit) {
        UUID uuid = UUID.randomUUID();
        return createCompleteNewRoom(uuid, shelfLimit);
    }

    public static Room createCompleteNewRoom(UUID uuid, int shelfLimit) {
        saveCsvFile("csvFiles\\csvRoom.csv", uuid, shelfLimit);
        return new Room(uuid, shelfLimit);
    }

    private Room(UUID uuid, int shelfLimit) {
        this.shelfLimit = shelfLimit;
        this.shelfList = new ArrayList<>(this.shelfLimit);
        this.roomID = uuid;
    }

    public static void loadCsvFile(String fileName) {

    }

    public static void saveCsvFile(String fileName, UUID roomID, int shelfLimit) {
        try (FileOutputStream stream = new FileOutputStream(fileName, true)) {
            String csvData = roomID.toString() +  ";" + shelfLimit +  ";\n";
            stream.write(csvData.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public static void main(String[] args) {
        int shelfLimit = 7;
        Room.createNewRoom(shelfLimit);
    }
}



