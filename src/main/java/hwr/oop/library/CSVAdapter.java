package hwr.oop.library;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVAdapter {
    private final String path;
    private List<Room> roomList;
    private List<Shelf> shelfList;
    private List<Book> bookList;
    private List<Visitor> visitorList;
    private List<Librarian> librarianList;

    public CSVAdapter(String path) {
        this.path = path;
    }

    /*CSV format:
    1: every variable is split with an ";"
    2: the elements of every list-variable are split with an ","
    3: for each object reference the uuid is saved
    Important for loading the csv:
    1. make sure to assign the right variables
    2. make sure that the lists are read correctly
    3. when converting the ids back to object references make sure to not create the same object twice*/
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
                writer.write(String.format("%s;%s;%s;%d;%d;%d;%s%n", shelf.getShelfID().toString(), shelf.getRoomIn().getRoomID().toString(), shelf.getGenre(), shelf.getShelfWidth(), shelf.getRemainingSpace(), shelf.getBoardNumber(), String.join(",", bookIdList)));
            }
        }
        finally {
            writer.close();
        }
        //Book
        writer = new BufferedWriter(new FileWriter(path+"Book.csv"));
        try {
            for (Book book : bookList) {
                writer.write(String.format("%s;%d;%d;%s;%s;%s;%s;%s%n", book.getBookID().toString(), book.getBookCondition(), book.getBookWidth(), book.getBookTitle(), book.getBookAuthor(), book.getBookGenre(), book.getShelf().getShelfID().toString(), book.getBorrowedBy().getVisitorID().toString()));
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
                List<String> booksToReturnIdList = new ArrayList<>();
                for (Book book : visitor.getBooksToReturn()) {
                    booksToReturnIdList.add(book.getBookID().toString());
                }
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
}
