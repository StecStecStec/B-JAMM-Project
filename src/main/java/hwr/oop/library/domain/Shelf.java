package hwr.oop.library.domain;

import hwr.oop.library.persistence.CSVAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Shelf {
    private final UUID shelfID;
    private Room roomIn;
    private List<Book> booksOnShelf;
    private final String genre;
    private final int shelfWidth;
    private int remainingSpace;
    private final int boardNumber;

    public UUID getShelfID() {
        return shelfID;
    }

    public int getRemainingSpace() {
        return remainingSpace;
    }

    public List<Book> getBooksOnShelf() {
        return booksOnShelf;
    }

    public String getGenre() {
        return genre;
    }

    public int getShelfWidth() {
        return shelfWidth;
    }

    public int getBoardNumber() {
        return boardNumber;
    }

    public Room getRoomIn() {
        return roomIn;
    }

    public void addBookOnShelf(Book book) {
        if (remainingSpace < book.getBookWidth()) {
            throw new IllegalArgumentException("Added book to shelf with not enough space.");
        }
        remainingSpace -= book.getBookWidth();
        booksOnShelf.add(book);
    }

    public void removeBookOnShelf(Book book) {
        remainingSpace += book.getBookWidth();
        booksOnShelf.remove(book);
    }


<<<<<<< HEAD
    private Shelf(CSVAdapter csvAdapter, UUID shelfID, Room roomIn, String genre, int shelfWidth, int boardNumber) {
=======
    private Shelf(Library library, UUID shelfID, Room roomIn, String genre, int shelfWidth, int boardNumber) {
>>>>>>> 0e4c06e (refactored persistence by adding library class)
        this.shelfID = shelfID;
        this.roomIn = roomIn;
        this.booksOnShelf = new ArrayList<>();
        this.genre = genre;
        this.shelfWidth = shelfWidth;
        this.remainingSpace = shelfWidth;
        this.boardNumber = boardNumber;
        roomIn.roomAddShelf(this);
<<<<<<< HEAD
        csvAdapter.addShelf(this);
    }

    public static Shelf createNewShelf(CSVAdapter csvAdapter, Room roomIn, String genre, int shelfWidth, int boardNumber) {
        return new Shelf(csvAdapter, UUID.randomUUID(), roomIn, genre, shelfWidth, boardNumber);
    }

    public static Shelf createCompleteNewShelf(CSVAdapter csvAdapter, UUID roomID, Room roomIn, String genre, int shelfWidth, int boardNumber) {
        return new Shelf(csvAdapter, roomID, roomIn, genre, shelfWidth, boardNumber);
    }

    //for CSVAdapter
    public static Shelf createTempShelf(CSVAdapter tempCsvAdapter, Room tempRoom) {
        return new Shelf(tempCsvAdapter, UUID.randomUUID(), tempRoom, "temp", 10000, 10000);
=======
        library.addShelf(this);
    }

    public static Shelf createNewShelf(Library library, Room roomIn, String genre, int shelfWidth, int boardNumber) {
        return new Shelf(library, UUID.randomUUID(), roomIn, genre, shelfWidth, boardNumber);
    }

    public static Shelf createCompleteNewShelf(Library library, UUID roomID, Room roomIn, String genre, int shelfWidth, int boardNumber) {
        return new Shelf(library, roomID, roomIn, genre, shelfWidth, boardNumber);
    }

    //for CSVAdapter
    public static Shelf createTempShelf(Library tempLibrary, Room tempRoom) {
        return new Shelf(tempLibrary, UUID.randomUUID(), tempRoom, "temp", 10000, 10000);
>>>>>>> 0e4c06e (refactored persistence by adding library class)
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shelf shelf = (Shelf) o;
        return shelfWidth == shelf.shelfWidth && remainingSpace == shelf.remainingSpace && boardNumber == shelf.boardNumber && Objects.equals(shelfID, shelf.shelfID) && Objects.equals(booksOnShelf, shelf.booksOnShelf) && Objects.equals(genre, shelf.genre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shelfID, booksOnShelf, genre, shelfWidth, remainingSpace, boardNumber);
    }
}
