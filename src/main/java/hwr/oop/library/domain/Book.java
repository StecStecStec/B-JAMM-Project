package hwr.oop.library.domain;

import hwr.oop.library.persistance.CSVAdapter;

import java.util.Objects;
import java.util.UUID;

public class Book {
    private final UUID bookID;
    private int bookCondition; //in percentage (0-100)
    private final int bookWidth;
    private final String title;
    private final String author;
    private final String genre;
    private Shelf shelf;
    private Visitor borrowedBy = null;

    private static final int UPPER_BOUNDARY = 100;
    private static final int LOWER_BOUNDARY = 0;

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

    public static Book createNewBook(CSVAdapter csvAdapter, String title, String author, String genre, Shelf shelf, int bookCondition, int bookWidth) {
        return new Book(csvAdapter, UUID.randomUUID(), title, author, genre, shelf, bookCondition, bookWidth);
    }

    public static Book createCompleteBook(CSVAdapter csvAdapter, UUID uuid, String title, String author, String genre, Shelf shelf, int bookCondition, int bookWidth) {
        return new Book(csvAdapter, uuid, title, author, genre, shelf, bookCondition, bookWidth);
    }

    private Book(CSVAdapter csvAdapter, UUID uuid, String title, String author, String genre, Shelf shelf, int bookCondition, int bookWidth) {
        this.bookID = uuid;
        this.bookWidth = Math.max(bookWidth, 0);
        this.shelf = shelf;
        this.title = title;
        this.author = author;
        this.genre = genre;
        if (bookCondition >= LOWER_BOUNDARY && bookCondition <= UPPER_BOUNDARY) {
            this.bookCondition = bookCondition;
        } else {
            this.bookCondition = -1;
        }
        shelf.addBookOnShelf(this);
        csvAdapter.addBook(this);
    }

    public void borrow(Visitor visitor) {
        if (borrowedBy == null) {
            borrowedBy = visitor;
            borrowedBy.addBorrowedBook(this);
            shelf.removeBookOnShelf(this);
            shelf = null;
        }
    }

    public void restoreBook () {
        this.bookCondition = 100;
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
        return bookCondition == book.bookCondition && bookWidth == book.bookWidth && Objects.equals(bookID, book.bookID) && Objects.equals(title, book.title) && Objects.equals(author, book.author) && Objects.equals(genre, book.genre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookID, bookCondition, bookWidth, title, author, genre, borrowedBy);
    }
}
