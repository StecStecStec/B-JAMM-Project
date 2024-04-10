package hwr.oop;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class Shelf {
  private UUID shelfID;
  private Room roomIn = null;
  private ArrayList<Book> booksOnShelf = new ArrayList<>();
  private String genre;
  private int shelfWidth;
  private int boardNumber;

  public UUID getShelfID() {
    return shelfID;
  }

  public ArrayList<Book> getBooksOnShelf() {
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

  public Room getRoomIn() {return roomIn;}

  public Shelf(Room roomIn, String genre, int shelfWidth, int boardNumber) {
    this.shelfID = UUID.randomUUID();
    this.roomIn = roomIn;
    this.booksOnShelf = getBooksOnShelf();
    this.genre = genre;
    this.shelfWidth = shelfWidth;
    this.boardNumber = boardNumber;
  }

  public int addBookOnShelf(Book book) {
    booksOnShelf.add(book);
      return 0;
  }
}
