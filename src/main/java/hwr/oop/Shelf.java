package hwr.oop;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Shelf {
    private UUID shelfID;
    private Room roomIn;
    private List<Book> booksOnShelf;
    private String genre;
    private int shelfWidth;
    private int boardNumber;

    public UUID getShelfID() {
        return shelfID;
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
        booksOnShelf.add(book);
    }

    public void removeBookOnShelf(Book book) {
        booksOnShelf.remove(book);
    }

    //needed for tests
    public void setShelfID(UUID shelfID) {
        this.shelfID = shelfID;
    }

    public Shelf(Room roomIn, String genre, int shelfWidth, int boardNumber) {
        this.shelfID = UUID.randomUUID();
        this.roomIn = roomIn;
        this.booksOnShelf = new ArrayList<Book>();
        this.genre = genre;
        this.shelfWidth = shelfWidth;
        this.boardNumber = boardNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shelf shelf = (Shelf) o;
        return shelfWidth == shelf.shelfWidth && boardNumber == shelf.boardNumber && Objects.equals(shelfID, shelf.shelfID) && Objects.equals(roomIn, shelf.roomIn) && Objects.equals(booksOnShelf, shelf.booksOnShelf) && Objects.equals(genre, shelf.genre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shelfID, roomIn, booksOnShelf, genre, shelfWidth, boardNumber);
    }
}
