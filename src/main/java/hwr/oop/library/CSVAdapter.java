package hwr.oop.library;

import java.io.*;
import java.util.*;
import java.util.ArrayList;

public class CSVAdapter {
    private final String path;
    private List<Room> roomList = new ArrayList<>();
    private List<Shelf> shelfList = new ArrayList<>();
    private List<Book> bookList = new ArrayList<>();
    private List<Visitor> visitorList = new ArrayList<>();
    private List<Librarian> librarianList = new ArrayList<>();
    private Shelf tempShelf;

    public CSVAdapter(String path) {
        this.path = path;
    }

    public void addRoom(Room room){roomList.add(room);}
    public void addShelf(Shelf shelf){shelfList.add(shelf);}
    public void addBook(Book book){bookList.add(book);}
    public void addVisitor(Visitor visitor){visitorList.add(visitor);}
    public void addLibrarian(Librarian librarian){librarianList.add(librarian);}
    public void deleteVisitor(Visitor visitor){visitorList.remove(visitor);}
    public void deleteLibrarian(Librarian librarian){librarianList.remove(librarian);}

    public List<Librarian> getLibrarianList() {return librarianList;}
    public List<Visitor> getVisitorList() {return visitorList;}
    public List<Book> getBookList() {return bookList;}
    public List<Shelf> getShelfList() {return shelfList;}
    public List<Room> getRoomList() {return roomList;}
    public String getPath() {return path;}

    public void clear(){
        roomList.clear();
        shelfList.clear();
        bookList.clear();
        visitorList.clear();
        librarianList.clear();
        tempShelf = null;
    }

    /*CSV format:
        1: every variable is split with an ";"
        2: the elements of every list-variable are split with an ","
        3: for each object reference the uuid is saved
        4: in case a variable is null it is converted to a string named "null" */

    public void loadCSV() throws FileNotFoundException {
        loadRoom();
        loadShelf();
        loadBook();
        loadVisitor();
        loadLibrarian();
    }

    public void saveCSV() {
        saveRoom();
        saveShelf();
        saveBook();
        saveVisitor();
        saveLibrarian();
    }
    private void saveRoom() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path+"Room.csv"));
            for (Room room : roomList) {
                writer.write(String.format("%s;%d%n", room.getRoomID().toString(), room.getShelfLimit()));
            }
            writer.close();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void saveShelf() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path+"Shelf.csv"));
            for (Shelf shelf : shelfList) {
                writer.write(String.format("%s;%s;%s;%d;%d%n", shelf.getShelfID().toString(), shelf.getRoomIn().getRoomID().toString(), shelf.getGenre(), shelf.getShelfWidth(), shelf.getBoardNumber()));
            }
            writer.close();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void saveBook() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path+"Book.csv"));
            for (Book book : bookList) {
                if (book.getShelf() == null){
                    writer.write(String.format("%s;%d;%d;%s;%s;%s;%s;%s%n", book.getBookID().toString(), book.getBookCondition(), book.getBookWidth(), book.getBookTitle(), book.getBookAuthor(), book.getBookGenre(), "null", book.getBorrowedBy().getVisitorID().toString()));
                }
                else {
                    writer.write(String.format("%s;%d;%d;%s;%s;%s;%s;%s%n", book.getBookID().toString(), book.getBookCondition(), book.getBookWidth(), book.getBookTitle(), book.getBookAuthor(), book.getBookGenre(), book.getShelf().getShelfID().toString(), "null"));
                }
            }
            writer.close();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void saveVisitor() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path+"Visitor.csv"));
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
            writer.close();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void saveLibrarian() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path+"Librarian.csv"));
            for (Librarian librarian : librarianList) {
                writer.write(String.format("%s;%s;%s;%s%n", librarian.getLibrarianID().toString(), librarian.getLibrarianName(), librarian.getLibrarianSurname(), librarian.getLibrarianBirthday()));
            }
            writer.close();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadRoom() {
        try{
            BufferedReader reader = new BufferedReader(new FileReader(path+"Room.csv"));
            String line;
            while ((line = reader.readLine()) != null){
                String[] variables = line.split(";");
                UUID uuid = UUID.fromString(variables[0]);
                int shelfLimit = Integer.parseInt(variables[1]);
                Room.createCompleteNewRoom(this, uuid, shelfLimit);
            }
            reader.close();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void loadShelf() {
        try{
            BufferedReader reader = new BufferedReader(new FileReader(path+"Shelf.csv"));
            String line;
            while ((line = reader.readLine()) != null){
                String[] variables = line.split(";");
                UUID uuid = UUID.fromString(variables[0]);
                UUID roomId = UUID.fromString(variables[1]);
                Room inRoom = null;
                for (Room room : roomList){
                    if (room.getRoomID().equals(roomId)){
                        inRoom = room;
                        break;
                    }
                }
                String genre = variables[2];
                int shelfWidth = Integer.parseInt(variables[3]);
                int boardNumber = Integer.parseInt(variables[4]);
                Shelf.createCompleteNewShelf(this, uuid, inRoom, genre, shelfWidth, boardNumber);
            }
            reader.close();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void loadBook() throws FileNotFoundException {
        try{
            BufferedReader reader = new BufferedReader(new FileReader(path+"Book.csv"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] variables = line.split(";");
                UUID uuid = UUID.fromString(variables[0]);
                int bookCondition = Integer.parseInt(variables[1]);
                int bookWidth = Integer.parseInt(variables[2]);
                String bookTitle = variables[3];
                String bookAuthor = variables[4];
                String bookGenre = variables[5];
                if (Objects.equals(variables[6], "null")) { // borrowed book
                    /* In this case the book is borrowed but we cant create a book without assigning it to a shelf.
                    Therefore, temp objects are created that are completely separate and are not saved.
                    To reaccess the book we only need to save the temp shelf.
                    The book gets borrowed when loading the visitors. */
                    if (tempShelf == null) {
                        CSVAdapter tempCsvAdapter = new CSVAdapter("");
                        Room tempRoom = Room.createTempRoom(tempCsvAdapter);
                        tempShelf = Shelf.createTempShelf(tempCsvAdapter, tempRoom);
                    }
                    Book.createCompleteBook(this, uuid, bookTitle, bookAuthor, bookGenre, tempShelf, bookCondition, bookWidth);
                } else { // book on shelf
                    UUID shelfId = UUID.fromString(variables[6]);
                    Shelf inShelf = null;
                    for (Shelf shelf : shelfList) {
                        if (shelf.getShelfID().equals(shelfId)) {
                            inShelf = shelf;
                            break;
                        }
                    }
                    Book.createCompleteBook(this, uuid, bookTitle, bookAuthor, bookGenre, inShelf, bookCondition, bookWidth);
                }
            }
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void loadVisitor() throws FileNotFoundException {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path+"Visitor.csv"));
            String line;
            while ((line = reader.readLine()) != null){
                String[] variables = line.split(";");
                UUID uuid = UUID.fromString(variables[0]);
                String name = variables[1];
                String surname = variables[2];
                String birthday = variables[3];
                String email = variables[4];
                Visitor visitor = Visitor.createCompleteVisitor(this, name, surname, birthday, email, uuid);
                //borrowed books
                if (!Objects.equals(variables[5], "null")) {
                    String[] borrowedBooksStringIds = variables[5].split(",");
                    Map<UUID, Book> bookMap = buildBookMap();
                    for (String borrowedBookStringId : borrowedBooksStringIds) {
                        UUID bookId = UUID.fromString(borrowedBookStringId);
                        Book borrowedBook = bookMap.get(bookId);
                        borrowedBook.borrow(visitor);
                    }
                    //books to return
                    if (!Objects.equals(variables[6], "null")) {
                        String[] booksToReturnStringIds = variables[6].split(",");
                        for (String bookToReturnStringId : booksToReturnStringIds) {
                            UUID bookId = UUID.fromString(bookToReturnStringId);
                            Book bookToReturn = bookMap.get(bookId);
                            visitor.addBookToReturn(bookToReturn);
                        }
                    }
                }
            }
            reader.close();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Map<UUID, Book> buildBookMap() {
        Map<UUID, Book> bookMap = new HashMap<>();
        for (Book book : tempShelf.getBooksOnShelf()) {
            bookMap.put(book.getBookID(), book);
        }
        return bookMap;
    }

    private void loadLibrarian() throws FileNotFoundException {
        try{
            BufferedReader reader = new BufferedReader(new FileReader(path+"Librarian.csv"));
            String line;
            while ((line = reader.readLine()) != null){
                String[] variables = line.split(";");
                UUID uuid = UUID.fromString(variables[0]);
                String name = variables[1];
                String surname = variables[2];
                String birthday = variables[3];
                Librarian.createCompleteNewLibrarian(this, uuid, name, surname, birthday);
            }
            reader.close();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //for testing
    public static void main(String[] args) throws IOException {
        CSVAdapter csvAdapter = new CSVAdapter(".\\csvFiles\\");
        csvAdapter.loadCSV();
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
