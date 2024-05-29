package hwr.oop.library.persistence;

import hwr.oop.library.domain.*;

import java.io.*;
import java.util.*;
import java.util.ArrayList;

public class CSVAdapter implements Persistence {
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

    @Override
    public void addRoom(Room room){roomList.add(room);}
    @Override
    public void addShelf(Shelf shelf){shelfList.add(shelf);}
    @Override
    public void addBook(Book book){bookList.add(book);}
    @Override
    public void addVisitor(Visitor visitor){visitorList.add(visitor);}
    @Override
    public void addLibrarian(Librarian librarian){librarianList.add(librarian);}
    @Override
    public void deleteVisitor(Visitor visitor){visitorList.remove(visitor);}
    @Override
    public void deleteLibrarian(Librarian librarian){librarianList.remove(librarian);}
    @Override
    public void deleteBook(Book book){bookList.remove(book);}

    @Override
    public List<Librarian> getLibrarianList() {return librarianList;}
    @Override
    public List<Visitor> getVisitorList() {return visitorList;}
    @Override
    public List<Book> getBookList() {return bookList;}
    @Override
    public List<Shelf> getShelfList() {return shelfList;}
    @Override
    public List<Room> getRoomList() {return roomList;}
    @Override
    public String getPath() {return path;}

    @Override
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

    @Override
    public void loadCSV() {
        try{
            loadRoom();
            loadShelf();
            loadBook();
            loadVisitor();
            loadLibrarian();
        }
        catch (FileNotFoundException e) {
            throw new CSVAdapterExceptions("Error loading CSV");
        }
    }

    @Override
    public void saveCSV() {
        saveRoom();
        saveShelf();
        saveBook();
        saveVisitor();
        saveLibrarian();
    }
    private void saveRoom() {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(path+"Room.csv"))) {
            for (Room room : roomList) {
                writer.write(String.format("%s;%d%n", room.getRoomID().toString(), room.getShelfLimit()));
            }
        }
        catch (IOException e) {
            throw new CSVAdapterExceptions("Error saving room");
        }
    }
    private void saveShelf() {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(path+"Shelf.csv"))){
            for (Shelf shelf : shelfList) {
                writer.write(String.format("%s;%s;%s;%d;%d%n", shelf.getShelfID().toString(), shelf.getRoomIn().getRoomID().toString(), shelf.getGenre(), shelf.getShelfWidth(), shelf.getBoardNumber()));
            }
        }
        catch (IOException e) {
            throw new CSVAdapterExceptions("Error saving shelf");
        }
    }
    private void saveBook() {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(path+"Book.csv"))){
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
            throw new CSVAdapterExceptions("Error saving book");
        }
    }
    private void saveVisitor() {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(path+"Visitor.csv"))){
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
            throw new CSVAdapterExceptions("Error saving visitor");
        }
    }
    private void saveLibrarian() {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(path+"Librarian.csv"))){
            for (Librarian librarian : librarianList) {
                writer.write(String.format("%s;%s;%s;%s%n", librarian.getLibrarianID().toString(), librarian.getLibrarianName(), librarian.getLibrarianSurname(), librarian.getLibrarianBirthday()));
            }
        }
        catch (IOException e) {
            throw new CSVAdapterExceptions("Error saving librarian");
        }
    }

    private void loadRoom() {
        try(BufferedReader reader = new BufferedReader(new FileReader(path+"Room.csv"))){
            String line;
            while ((line = reader.readLine()) != null){
                String[] variables = line.split(";");
                UUID uuid = UUID.fromString(variables[0]);
                int shelfLimit = Integer.parseInt(variables[1]);
                Room.createCompleteNewRoom(this, uuid, shelfLimit);
            }
        }
        catch (IOException e) {
            throw new CSVAdapterExceptions("Error loading room");
        }
    }
    private void loadShelf() {
        try(BufferedReader reader = new BufferedReader(new FileReader(path+"Shelf.csv"))){
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
        }
        catch (IOException e) {
            throw new CSVAdapterExceptions("Error loading shelf");
        }
    }
    private void loadBook() throws FileNotFoundException {
        try(BufferedReader reader = new BufferedReader(new FileReader(path+"Book.csv"))){
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
                    Book.createCompleteBook(this, uuid, bookTitle, bookAuthor, bookGenre, tempShelf, new int[]{bookCondition, bookWidth});
                } else { // book on shelf
                    UUID shelfId = UUID.fromString(variables[6]);
                    Shelf inShelf = null;
                    for (Shelf shelf : shelfList) {
                        if (shelf.getShelfID().equals(shelfId)) {
                            inShelf = shelf;
                            break;
                        }
                    }
                    Book.createCompleteBook(this, uuid, bookTitle, bookAuthor, bookGenre, inShelf, new int[]{bookCondition, bookWidth});
                }
            }
        } catch (IOException e) {
            throw new CSVAdapterExceptions("Error loading book");
        }
    }
    private void loadVisitor() throws FileNotFoundException {
        try(BufferedReader reader = new BufferedReader(new FileReader(path+"Visitor.csv"))){
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
        }
        catch (IOException e) {
            throw new CSVAdapterExceptions("Error loading visitor");
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
        try(BufferedReader reader = new BufferedReader(new FileReader(path+"Librarian.csv"))){
            String line;
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
            throw new CSVAdapterExceptions("Error loading librarian");
        }
    }
}
