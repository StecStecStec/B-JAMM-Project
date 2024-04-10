package hwr.oop;

import java.util.Objects;
import java.util.UUID;

public class Book {
    private UUID ID;
    private int bookCondition; //in percentage
    private String name;
    private String author;
    private String topic;
    private Shelf shelf;
    private Visitor borrowedBy = null;

    public int getBookCondition() {return bookCondition;}

    public int getID() {return ID;}

    public Shelf getShelf() {return shelf;}

    public String getName() {return name;}

    public String getAuthor() {return author;}

    public String getTopic() {return topic;}

    public Visitor getBorrowedBy() {return borrowedBy;}

    public Book(String name, String author, String topic, Shelf shelf, int bookCondition){
        this.name = name;
        this.author = author;
        this.topic = topic;
        this.shelf = shelf;
        this.bookCondition = bookCondition;
        this.ID = UUID.randomUUID();
    }

    public int borrow(Visitor visitor) {
        if(borrowedBy != null) {
            return -1;
        }
        borrowedBy = visitor;
        shelf = null;
        return 0;
    }
    public int returnBook(Shelf returnShelf) {
        if(borrowedBy == null) {
            return -1;
        }
        borrowedBy = null;
        shelf = returnShelf;
        return 0;
    }
    public void setBookCondition(int bookCondition) {
        this.bookCondition = bookCondition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return ID == book.ID && bookCondition == book.bookCondition && Objects.equals(name, book.name) && Objects.equals(author, book.author) && Objects.equals(topic, book.topic) && Objects.equals(shelf, book.shelf) && Objects.equals(borrowedBy, book.borrowedBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, bookCondition, name, author, topic, shelf, borrowedBy);
    }
}
