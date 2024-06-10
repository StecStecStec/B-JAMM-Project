package hwr.oop.library.domain;

import java.util.Objects;
import java.util.UUID;

public class Book {
    Library library;
    private final UUID bookID;
    private int bookCondition; //in percentage (0-100)
    private final int bookWidth;
    private final String title;
    private final String author;
    private final String genre;
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

    public static class Builder {
        private Library library;
        private UUID bookID;
        private int bookCondition; //in percentage (0-100)
        private int bookWidth;
        private String title;
        private String author;
        private String genre;
        private Shelf shelf;

        private static final int UPPER_BOUNDARY = 100;
        private static final int LOWER_BOUNDARY = 0;


        public Builder library(Library library) {
            this.library = library;
            return this;
        }

        public Builder() {
            // nothing to do here
        }

        public Builder bookID(UUID uuid) {
            this.bookID = uuid;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder author(String author) {
            this.author = author;
            return this;
        }

        public Builder genre(String genre) {
            this.genre = genre;
            return this;
        }

        public Builder shelf(Shelf shelf) {
            this.shelf = shelf;
            return this;
        }

        public Builder bookCondition(int bookCondition) {
            if (bookCondition >= LOWER_BOUNDARY && bookCondition <= UPPER_BOUNDARY) {
                this.bookCondition = bookCondition;
            } else {
                this.bookCondition = -1;
            }
            return this;
        }

        public Builder bookWidth(int bookWidth) {
            this.bookWidth = Math.max(bookWidth, 0);
            return this;
        }

        public Book build() {
            Book book = new Book(this);
            if (shelf != null) {
                shelf.addBookOnShelf(book);
            }
            if (library != null) {
                library.addBook(book);
            }
            return book;
        }
    }

    private Book(Builder builder) {
        this.library = builder.library;
        this.bookID = builder.bookID;
        this.bookWidth = builder.bookWidth;
        this.shelf = builder.shelf;
        this.title = builder.title;
        this.author = builder.author;
        this.genre = builder.genre;
        this.bookCondition = builder.bookCondition;
    }

    /*public static Book createNewBook(Library library, String title, String author, String genre, Shelf shelf, int bookCondition, int bookWidth) {
        return new Book(library, UUID.randomUUID(), title, author, genre, shelf, bookCondition, bookWidth);
    }

    public static Book createCompleteBook(Library library, UUID uuid, String title, String author, String genre, Shelf shelf, int bookCondition, int bookWidth) {
        return new Book(library, uuid, title, author, genre, shelf, bookCondition, bookWidth);
    }

    private Book(Library library, UUID uuid, String title, String author, String genre, Shelf shelf, int bookCondition, int bookWidth) {
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
        library.addBook(this);
    }

     */

    public void borrow(Visitor visitor) {
        if (borrowedBy == null) {
            borrowedBy = visitor;
            borrowedBy.addBorrowedBook(this);
            shelf.removeBookOnShelf(this);
            shelf = null;
        }
    }

    public void restoreBook() {
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
