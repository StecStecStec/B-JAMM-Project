package hwr.oop.library;

import java.util.Objects;
import java.util.UUID;

public class Book {
    private final UUID bookID;
    private int bookCondition; //in percentage (0-100)
    private final int bookWidth;
    private final String title;
    private final String author;
    private String genre;
    private Shelf shelf;
    private Visitor borrowedBy = null;

    public int getBookWidth() {
        return bookWidth;
    }

    public int getBookCondition() {
        return bookCondition;
    }

    public UUID getBookID() {
        return bookID;
    }

    public Shelf getShelf() {
        return shelf;
    }

    public String getBookTitle() {
        return title;
    }

    public String getBookAuthor() {
        return author;
    }

    public String getBookGenre() {
        return genre;
    }

    public Visitor getBorrowedBy() {
        return borrowedBy;
    }

    public static Book createNewBook(String title, String author, String genre, Shelf shelf, int bookCondition, int bookWidth) {
        return new Book(UUID.randomUUID(), title, author, genre, shelf, bookCondition, bookWidth);
    }

    public static Book createCompleteBook(UUID uuid, String title, String author, String genre, Shelf shelf, int bookCondition, int bookWidth) {
        return new Book(uuid, title, author, genre, shelf, bookCondition, bookWidth);
    }

    private Book(UUID uuid, String title, String author, String genre, Shelf shelf, int bookCondition, int bookWidth) {
        this.bookID = uuid;
        this.bookWidth = bookWidth;
        this.shelf = shelf;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.bookCondition = bookCondition;
        shelf.addBookOnShelf(this);
    }


    public void borrow(Visitor visitor) {
        if (borrowedBy == null) {
            borrowedBy = visitor;
            borrowedBy.addBorrowedBook(this);
            shelf.removeBookOnShelf(this);
            shelf = null;
        }
    }

    public void returnBook(Shelf returnShelf) {
        if (borrowedBy != null) {
            borrowedBy.removeBorrowedBook(this);
            borrowedBy = null;
            shelf = returnShelf;
            shelf.addBookOnShelf(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return bookCondition == book.bookCondition && bookWidth == book.bookWidth && Objects.equals(bookID, book.bookID) && Objects.equals(title, book.title) && Objects.equals(author, book.author) && Objects.equals(genre, book.genre) && Objects.equals(shelf, book.shelf) && Objects.equals(borrowedBy, book.borrowedBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookID, bookCondition, bookWidth, title, author, genre, borrowedBy);
    }
}
