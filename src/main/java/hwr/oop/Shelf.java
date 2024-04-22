package hwr.oop;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Shelf {
    private final UUID shelfID;
    private Room roomIn;
    private List<Book> booksOnShelf;
    private String genre;
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


    private Shelf(UUID shelfID, Room roomIn, String genre, int shelfWidth, int boardNumber) {
        this.shelfID = shelfID;
        this.roomIn = roomIn;
        this.booksOnShelf = new ArrayList<>();
        this.genre = genre;
        this.shelfWidth = shelfWidth;
        this.remainingSpace = shelfWidth;
        this.boardNumber = boardNumber;
        roomIn.roomAddShelf(this);
    }


    public static Shelf createNewShelf(Room roomIn, String genre, int shelfWidth, int boardNumber) {
        return new Shelf(UUID.randomUUID(), roomIn, genre, shelfWidth, boardNumber);
    }

    public static Shelf createCompleteNewShelf(UUID roomID, Room roomIn, String genre, int shelfWidth, int boardNumber) {
        return new Shelf(roomID, roomIn, genre, shelfWidth, boardNumber);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shelf shelf = (Shelf) o;
        return shelfWidth == shelf.shelfWidth && remainingSpace == shelf.remainingSpace && boardNumber == shelf.boardNumber && Objects.equals(shelfID, shelf.shelfID) && Objects.equals(roomIn, shelf.roomIn) && Objects.equals(booksOnShelf, shelf.booksOnShelf) && Objects.equals(genre, shelf.genre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shelfID, booksOnShelf, genre, shelfWidth, remainingSpace, boardNumber);
    }
}
