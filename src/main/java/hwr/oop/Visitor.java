package hwr.oop;

import java.util.List;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class Visitor {
    private UUID visitorID;
    private String visitorName;
    private String visitorSurname;
    private String visitorBirthday;
    private String visitorEmailAddress;
    private List<Book> borrowedBooks;
    private List<Book> booksToReturn; //warnings

    public UUID getVisitorID() {return visitorID;}
    public String getVisitorName() {return visitorName;}
    public String getVisitorSurname() {return visitorSurname;}
    public String getVisitorBirthday() {return visitorBirthday;}
    public String getVisitorEmailAddress() {return visitorEmailAddress;}
    public void addBorrowedBook(Book book) {borrowedBooks.add(book);}
    public void removeBorrowedBook(Book book) {borrowedBooks.remove(book);}
    public void addBookToReturn(Book book) {booksToReturn.add(book);}
    public void removeBookToReturn(Book book) {booksToReturn.remove(book);}

    public Visitor(String visitorName, String visitorSurname, String visitorBirthday, String visitorEmailAddress) {
        this.visitorName = visitorName;
        this.visitorSurname = visitorSurname;
        this.visitorBirthday = visitorBirthday;
        this.visitorEmailAddress = visitorEmailAddress;
        this.booksToReturn = new ArrayList<Book>();
        this.borrowedBooks = new ArrayList<Book>();
        this.visitorID = UUID.randomUUID();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Visitor visitor = (Visitor) o;
        return Objects.equals(visitorID, visitor.visitorID) && Objects.equals(visitorName, visitor.visitorName) && Objects.equals(visitorSurname, visitor.visitorSurname) && Objects.equals(visitorBirthday, visitor.visitorBirthday) && Objects.equals(visitorEmailAddress, visitor.visitorEmailAddress) && Objects.equals(borrowedBooks, visitor.borrowedBooks) && Objects.equals(booksToReturn, visitor.booksToReturn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(visitorID, visitorName, visitorSurname, visitorBirthday, visitorEmailAddress, borrowedBooks, booksToReturn);
    }
}
