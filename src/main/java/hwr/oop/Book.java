package hwr.oop;

import java.util.Objects;
import java.util.UUID;

public class Book {
    private UUID bookID;
    private int bookCondition; //in percentage (0-100)
    private String title;
    private String author;
    private String genre;
    private Shelf shelf;
    private Visitor borrowedBy = null;

    public int getBookCondition() {return bookCondition;}
    public UUID getBookID() {return bookID;}
    public Shelf getShelf() {return shelf;}
    public String getBookTitle() {return title;}
    public String getBookAuthor() {return author;}
    public String getBookGenre() {return genre;}
    public Visitor getBorrowedBy() {return borrowedBy;}

    public Book(String title, String author, String genre, Shelf shelf, int bookCondition){
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.shelf = shelf;
        this.bookCondition = bookCondition;
        this.bookID = UUID.randomUUID();
        shelf.addBookOnShelf(this);
    }

    void setBookID(UUID bookID) {
        this.bookID = bookID;
    }

    public void borrow(Visitor visitor) {
        if(borrowedBy != null) {
            return;
        }
        borrowedBy = visitor;
        borrowedBy.addBorrowedBook(this);
        shelf.removeBookOnShelf(this);
        shelf = null;
    }
    public void returnBook(Shelf returnShelf) {
        if(borrowedBy == null) {
            return;
        }
        borrowedBy.removeBorrowedBook(this);
        borrowedBy = null;
        shelf = returnShelf;
        shelf.addBookOnShelf(this);
    }
    public void setBookCondition(int bookCondition) {
        this.bookCondition = bookCondition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return bookID == book.bookID && bookCondition == book.bookCondition && Objects.equals(title, book.title) && Objects.equals(author, book.author) && Objects.equals(genre, book.genre) && Objects.equals(shelf, book.shelf) && Objects.equals(borrowedBy, book.borrowedBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookID, bookCondition, title, author, genre, borrowedBy);
    }
}
