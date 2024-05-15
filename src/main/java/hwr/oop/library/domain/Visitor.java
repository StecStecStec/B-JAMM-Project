package hwr.oop.library.domain;

import hwr.oop.library.persistence.CSVAdapter;

import java.util.List;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class Visitor {
    private final UUID visitorID;
    private final String visitorName;
    private final String visitorSurname;
    private final String visitorBirthday;
    private String visitorEmailAddress;
    private List<Book> borrowedBooks;
    private List<Book> booksToReturn; //warnings

    public UUID getVisitorID() {
        return visitorID;
    }

    public String getVisitorName() {
        return visitorName;
    }

    public String getVisitorSurname() {
        return visitorSurname;
    }

    public String getVisitorBirthday() {
        return visitorBirthday;
    }

    public String getVisitorEmailAddress() {
        return visitorEmailAddress;
    }

    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void addBorrowedBook(Book book) {
        borrowedBooks.add(book);
    }

    public void removeBorrowedBook(Book book) {
        borrowedBooks.remove(book);
    }

    public List<Book> getBooksToReturn() {
        return booksToReturn;
    }

    public void addBookToReturn(Book book) {
        booksToReturn.add(book);
    }

    public void removeBookToReturn(Book book) {
        booksToReturn.remove(book);
    }

    public static Visitor createNewVisitor(CSVAdapter csvAdapter, String visitorName, String visitorSurname, String visitorBirthday, String visitorEmailAddress) {
        return new Visitor(csvAdapter, visitorName, visitorSurname, visitorBirthday, visitorEmailAddress, UUID.randomUUID());
    }

    public static Visitor createCompleteVisitor(CSVAdapter csvAdapter, String visitorName, String visitorSurname, String visitorBirthday, String visitorEmailAddress, UUID uuid) {
        return new Visitor(csvAdapter, visitorName, visitorSurname, visitorBirthday, visitorEmailAddress, uuid);
    }

    private Visitor(CSVAdapter csvAdapter, String visitorName, String visitorSurname, String visitorBirthday, String visitorEmailAddress, UUID visitorID) {
        this.visitorName = visitorName;
        this.visitorSurname = visitorSurname;
        this.visitorBirthday = visitorBirthday;
        this.visitorEmailAddress = visitorEmailAddress;
        this.booksToReturn = new ArrayList<>();
        this.borrowedBooks = new ArrayList<>();
        this.visitorID = visitorID;
        csvAdapter.addVisitor(this);
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
        return Objects.hash(visitorID, visitorName, visitorSurname, visitorBirthday, visitorEmailAddress);
    }
}
