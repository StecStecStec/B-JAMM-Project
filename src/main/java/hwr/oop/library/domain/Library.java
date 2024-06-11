package hwr.oop.library.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Library {
  private final UUID libraryID;
  private List<Room> roomList;
  private List<Shelf> shelfList;
  private List<Book> bookList;
  private List<Visitor> visitorList;
  private List<Librarian> librarianList;

  public static Library createNewLibrary() {
    return new Library(UUID.randomUUID());
  }

  public static Library createCompleteLibrary(UUID uuid) {
    return new Library(uuid);
  }

  private Library(UUID uuid) {
    this.libraryID = uuid;
    roomList = new ArrayList<>();
    shelfList = new ArrayList<>();
    bookList = new ArrayList<>();
    visitorList = new ArrayList<>();
    librarianList = new ArrayList<>();
  }

  public List<Room> getRoomList() {
    return roomList;
  }

  public List<Shelf> getShelfList() {
    return shelfList;
  }

  public List<Book> getBookList() {
    return bookList;
  }

  public List<Visitor> getVisitorList() {
    return visitorList;
  }

  public List<Librarian> getLibrarianList() {
    return librarianList;
  }

  public void addRoom(Room room) {
    roomList.add(room);
  }

  public void addShelf(Shelf shelf) {
    shelfList.add(shelf);
  }

  public void addBook(Book book) {
    bookList.add(book);
  }

  public void addVisitor(Visitor visitor) {
    visitorList.add(visitor);
  }

  public void addLibrarian(Librarian librarian) {
    librarianList.add(librarian);
  }

  public void deleteRoom(Room room) {
    roomList.remove(room);
  }

  public void deleteShelf(Shelf shelf) {
    shelfList.remove(shelf);
  }

  public void deleteBook(Book book) {
    bookList.remove(book);
  }

  public void deleteVisitor(Visitor visitor) {
    visitorList.remove(visitor);
  }

  public void deleteLibrarian(Librarian librarian) {
    librarianList.remove(librarian);
  }

  public UUID getLibraryID() {
    return libraryID;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Library library = (Library) o;
    return Objects.equals(libraryID, library.libraryID)
        && Objects.equals(roomList, library.roomList)
        && Objects.equals(shelfList, library.shelfList)
        && Objects.equals(bookList, library.bookList)
        && Objects.equals(visitorList, library.visitorList)
        && Objects.equals(librarianList, library.librarianList);
  }

  @Override
  public int hashCode() {
    return Objects.hash(libraryID, roomList, shelfList, bookList, visitorList, librarianList);
  }
}
