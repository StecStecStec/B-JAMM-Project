package hwr.oop.library;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CSVAdapter {
    private final String path;
    private List<Room> roomList = new ArrayList<>();
    private List<Shelf> shelfList = new ArrayList<>();
    private List<Book> bookList = new ArrayList<>();
    private List<Visitor> visitorList = new ArrayList<>();
    private List<Librarian> librarianList = new ArrayList<>();

    public CSVAdapter(String path) {
        this.path = path;
    }

    public void addRoom(Room room){roomList.add(room);}
    public void addShelf(Shelf shelf){shelfList.add(shelf);}
    public void addBook(Book book){bookList.add(book);}
    public void addVisitor(Visitor visitor){visitorList.add(visitor);}
    public void addLibrarian(Librarian librarian){librarianList.add(librarian);}
    /*CSV format:
    1: every variable is split with an ";"
    2: the elements of every list-variable are split with an ","
    3: for each object reference the uuid is saved
    4: in case a variable is null it is converted to a string named "null"
    Important for loading the csv:
    1. make sure to assign the right variables
    2. make sure that the lists are read correctly
    3. when converting the ids back to object references make sure to not create the same object twice
    4. pay attention on the null strings */
    public void loadCSV() {
        
    }
    public void saveCSV() throws IOException {
        //Room
        BufferedWriter writer = new BufferedWriter(new FileWriter(path+"Room.csv"));
        try {
            for (Room room : roomList) {
                List<String> shelfIdList = new ArrayList<>();
                for (Shelf shelf : room.getShelfList()) {
                    shelfIdList.add(shelf.getShelfID().toString());
                }
                if (shelfIdList.isEmpty()){shelfIdList.add("null");}
                writer.write(String.format("%s;%d;%s%n", room.getRoomID().toString(), room.getShelfLimit(), String.join(",", shelfIdList)));
            }
        }
        finally {
            writer.close();
        }

        //Shelf
        writer = new BufferedWriter(new FileWriter(path+"Shelf.csv"));
        try {
            for (Shelf shelf : shelfList) {
                List<String> bookIdList = new ArrayList<>();
                for (Book book : shelf.getBooksOnShelf()) {
                    bookIdList.add(book.getBookID().toString());
                }
                if (bookIdList.isEmpty()){bookIdList.add("null");}
                writer.write(String.format("%s;%s;%s;%d;%d;%s%n", shelf.getShelfID().toString(), shelf.getRoomIn().getRoomID().toString(), shelf.getGenre(), shelf.getShelfWidth(), shelf.getBoardNumber(), String.join(",", bookIdList)));
            }
        }
        finally {
            writer.close();
        }
        //Book
        writer = new BufferedWriter(new FileWriter(path+"Book.csv"));
        try {
            for (Book book : bookList) {
                if (book.getShelf() == null){
                    writer.write(String.format("%s;%d;%d;%s;%s;%s;%s;%s%n", book.getBookID().toString(), book.getBookCondition(), book.getBookWidth(), book.getBookTitle(), book.getBookAuthor(), book.getBookGenre(), "null", book.getBorrowedBy().getVisitorID().toString()));
                }
                else {
                    writer.write(String.format("%s;%d;%d;%s;%s;%s;%s;%s%n", book.getBookID().toString(), book.getBookCondition(), book.getBookWidth(), book.getBookTitle(), book.getBookAuthor(), book.getBookGenre(), book.getShelf().getShelfID().toString(), "null"));
                }
            }
        }
        finally {
            writer.close();
        }

        //Visitor
        writer = new BufferedWriter(new FileWriter(path+"Visitor.csv"));
        try {
            for (Visitor visitor : visitorList) {
                List<String> borrowedBookIdList = new ArrayList<>();
                for (Book book : visitor.getBorrowedBooks()) {
                    borrowedBookIdList.add(book.getBookID().toString());
                }
                if (borrowedBookIdList.isEmpty()){borrowedBookIdList.add("null");}
                List<String> booksToReturnIdList = new ArrayList<>();
                for (Book book : visitor.getBooksToReturn()) {
                    booksToReturnIdList.add(book.getBookID().toString());
                }
                if (booksToReturnIdList.isEmpty()){booksToReturnIdList.add("null");}
                writer.write(String.format("%s;%s;%s;%s;%s;%s;%s%n", visitor.getVisitorID().toString(), visitor.getVisitorName(), visitor.getVisitorSurname(), visitor.getVisitorBirthday(), visitor.getVisitorEmailAddress(), String.join(",", borrowedBookIdList), String.join(",", booksToReturnIdList)));
            }
        }
        finally {
            writer.close();
        }

        //Librarian
        writer = new BufferedWriter(new FileWriter(path+"Librarian.csv"));
        try{
            for (Librarian librarian : librarianList) {
                writer.write(String.format("%s;%s;%s;%s%n", librarian.getLibrarianID().toString(), librarian.getLibrarianName(), librarian.getLibrarianSurname(), librarian.getLibrarianBirthday()));
            }
        }
        finally {
            writer.close();
        }
    }
    //for testing
    public static void main(String[] args) throws IOException {
        CSVAdapter csvAdapter = new CSVAdapter(".\\csvFiles\\");
        Visitor visitor = Visitor.createCompleteVisitor(csvAdapter, "Max", "Mustermann", "01.01.1999", "max.mustermann@gmx.de", UUID.randomUUID());
        Room room = Room.createNewRoom(csvAdapter, 5);
        Shelf shelf = Shelf.createNewShelf(csvAdapter, room, "Action", 400, 1);
        Book book1 = Book.createNewBook(csvAdapter, "Welt", "Peter Hans", "Natur", shelf, 100, 3);
        Book book2 = Book.createNewBook(csvAdapter, "TestWelt", "Petest", "Testing", shelf, 90, 5);
        book1.borrow(visitor);
        book2.borrow(visitor);
        csvAdapter.saveCSV();
    }
}
