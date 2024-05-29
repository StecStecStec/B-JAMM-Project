package hwr.oop.library.persistence;

import hwr.oop.library.domain.*;

import java.util.List;

public interface Persistence {

    void addRoom(Room room);

    void addShelf(Shelf shelf);

    void addBook(Book book);

    void addVisitor(Visitor visitor);

    void addLibrarian(Librarian librarian);

    void deleteVisitor(Visitor visitor);

    void deleteLibrarian(Librarian librarian);

    void deleteBook(Book book);

    List<Librarian> getLibrarianList();

    List<Visitor> getVisitorList();

    List<Book> getBookList();

    List<Shelf> getShelfList();

    List<Room> getRoomList();

    String getPath();

    void clear();

    void loadCSV();

    void saveCSV();
}
