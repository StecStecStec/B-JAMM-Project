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

    public Shelf getTempShelf() {return tempShelf;}
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
        //Room
        BufferedReader reader = new BufferedReader(new FileReader(path+"Room.csv"));
        String line;
        try{
            while ((line = reader.readLine()) != null){
                String[] variables = line.split(";");
                UUID uuid = UUID.fromString(variables[0]);
                int shelfLimit = Integer.parseInt(variables[1]);
                Room.createCompleteNewRoom(this, uuid, shelfLimit);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        //Shelf
        reader = new BufferedReader(new FileReader(path+"Shelf.csv"));
        try{
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
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        //Book
        reader = new BufferedReader(new FileReader(path+"Book.csv"));
        try{
            while ((line = reader.readLine()) != null){
                String[] variables = line.split(";");
                UUID uuid = UUID.fromString(variables[0]);
                int bookCondition = Integer.parseInt(variables[1]);
                int bookWidth = Integer.parseInt(variables[2]);
                String bookTitle = variables[3];
                String bookAuthor = variables[4];
                String bookGenre = variables[5];
                if(Objects.equals(variables[6], "null")){ // borrowed book
                    /* In this case the book is borrowed but we cant create a book without assigning it to a shelf.
                    Therefore, temp objects are created that are completely separate and are not saved.
                    To reaccess the book we only need to save the temp shelf.
                    The book gets borrowed when loading the visitors. */
                    if(tempShelf == null){
                        CSVAdapter tempCsvAdapter = new CSVAdapter("");
                        Room tempRoom = Room.createTempRoom(tempCsvAdapter);
                        tempShelf = Shelf.createTempShelf(tempCsvAdapter, tempRoom);
                    }
                    Book.createCompleteBook(this, uuid, bookTitle, bookAuthor, bookGenre, tempShelf, bookCondition, bookWidth);
                }
                else{ // book on shelf
                    UUID shelfId = UUID.fromString(variables[6]);
                    Shelf inShelf = null;
                    for (Shelf shelf : shelfList){
                        if (shelf.getShelfID().equals(shelfId)){
                            inShelf = shelf;
                            break;
                        }
                    }
                    Book.createCompleteBook(this, uuid, bookTitle, bookAuthor, bookGenre, inShelf, bookCondition, bookWidth);
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        //Visitor
        reader = new BufferedReader(new FileReader(path+"Visitor.csv"));
        try{
            while ((line = reader.readLine()) != null){
                String[] variables = line.split(";");
                UUID uuid = UUID.fromString(variables[0]);
                String name = variables[1];
                String surname = variables[2];
                String birthday = variables[3];
                String email = variables[4];
                Visitor visitor = Visitor.createCompleteVisitor(this, name, surname, birthday, email, uuid);
                List<String> borrowedBooksStringIds = new ArrayList<>();
                List<Book> borrowedBooks = new ArrayList<>();
                //borrowed books
                if(!Objects.equals(variables[5], "null")) {
                    borrowedBooksStringIds = Arrays.asList(variables[5].split(","));
                    for (Book bookOnTempShelf : tempShelf.getBooksOnShelf()) {
                        for (String borrowedBookStringId : borrowedBooksStringIds) {
                            if (bookOnTempShelf.getBookID().equals(UUID.fromString(borrowedBookStringId))) {
                                borrowedBooks.add(bookOnTempShelf);
                            }
                        }
                    }
                    for (Book book : borrowedBooks) {
                        book.borrow(visitor);
                    }
                }
                //books to return
                if (!Objects.equals(variables[6], "null")) {
                    List<String> booksToReturnStringIds = new ArrayList<>();
                    booksToReturnStringIds = Arrays.asList(variables[6].split(","));
                    for (Book borrowedBook : visitor.getBorrowedBooks()) {
                        for (String bookToReturnStringId : booksToReturnStringIds) {
                            if (borrowedBook.getBookID().equals(UUID.fromString(bookToReturnStringId))) {
                                visitor.addBookToReturn(borrowedBook);
                            }
                        }
                    }
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        //Librarian
        reader = new BufferedReader(new FileReader(path+"Librarian.csv"));
        try{
            while ((line = reader.readLine()) != null){
                String[] variables = line.split(";");
                UUID uuid = UUID.fromString(variables[0]);
                String name = variables[1];
                String surname = variables[2];
                String birthday = variables[3];
                Librarian.createCompleteNewLibrarian(this, uuid, name, surname, birthday);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveCSV() throws IOException {
        //Room
        BufferedWriter writer = new BufferedWriter(new FileWriter(path+"Room.csv"));
        try {
            for (Room room : roomList) {
                writer.write(String.format("%s;%d%n", room.getRoomID().toString(), room.getShelfLimit()));
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        writer.close();

        //Shelf
        writer = new BufferedWriter(new FileWriter(path+"Shelf.csv"));
        try {
            for (Shelf shelf : shelfList) {
                writer.write(String.format("%s;%s;%s;%d;%d%n", shelf.getShelfID().toString(), shelf.getRoomIn().getRoomID().toString(), shelf.getGenre(), shelf.getShelfWidth(), shelf.getBoardNumber()));
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        writer.close();

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
        catch (IOException e) {
            e.printStackTrace();
        }
        writer.close();

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
        catch (IOException e) {
            e.printStackTrace();
        }
        writer.close();

        //Librarian
        writer = new BufferedWriter(new FileWriter(path+"Librarian.csv"));
        try{
            for (Librarian librarian : librarianList) {
                writer.write(String.format("%s;%s;%s;%s%n", librarian.getLibrarianID().toString(), librarian.getLibrarianName(), librarian.getLibrarianSurname(), librarian.getLibrarianBirthday()));
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        writer.close();
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
