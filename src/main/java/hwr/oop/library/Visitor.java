package hwr.oop.library;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
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

    public static Visitor createNewVisitor(String visitorName, String visitorSurname, String visitorBirthday, String visitorEmailAddress) {
        UUID visitorID = UUID.randomUUID();
        return createCompleteVisitor(visitorName, visitorSurname, visitorBirthday, visitorEmailAddress, visitorID);
    }

    public static Visitor createCompleteVisitor(String visitorName, String visitorSurname, String visitorBirthday, String visitorEmailAddress, UUID uuid) {
        saveCsvFile("csvFiles\\csvVisitor.csv", uuid, visitorName, visitorSurname, visitorBirthday ,visitorEmailAddress);
        return new Visitor(visitorName, visitorSurname, visitorBirthday, visitorEmailAddress, uuid);
    }

    private Visitor(String visitorName, String visitorSurname, String visitorBirthday, String visitorEmailAddress, UUID visitorID) {
        this.visitorName = visitorName;
        this.visitorSurname = visitorSurname;
        this.visitorBirthday = visitorBirthday;
        this.visitorEmailAddress = visitorEmailAddress;
        this.booksToReturn = new ArrayList<>();
        this.borrowedBooks = new ArrayList<>();
        this.visitorID = visitorID;
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

    public static void saveCsvFile(String fileName, UUID visitorID, String visitorName, String visitorSurname,  String visitorBirthday, String visitorEmailAddress) {
        try (FileOutputStream stream = new FileOutputStream(fileName, true)) {
            String csvData = visitorID.toString() + ";" + visitorName + ";" + visitorSurname + ";"  + visitorBirthday + ";" + visitorEmailAddress + ";\n";
            stream.write(csvData.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Hier den vollständigen Dateipfad inklusive Dateinamen angeben
        UUID uuid = UUID.randomUUID();
        createCompleteVisitor("Hands", "Mayer", "2323.232.32.3", "ebenfnef@gmeojöae.ei", uuid);
    }
}

