package hwr.oop.library.persistence;

import hwr.oop.library.cli.CLI;
import hwr.oop.library.domain.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.ArrayList;
import java.util.stream.Stream;

public class CSVAdapter implements Persistence {
    private final String path;
    private Shelf tempShelf;
    private static final String ROOM = "Room.csv";
    private static final String SHELF = "Shelf.csv";
    private static final String VISITOR = "Visitor.csv";
    private static final String LIBRARIAN = "Librarian.csv";
    private static final String BOOK = "Book.csv";


    public CSVAdapter(String path) {
        this.path = path;
    }

    public CSVAdapter(List<String> arguments, String mainOrTest) {
        String directory = pathToDirectory(arguments, mainOrTest);
        if (directory == null) {
            throw new IllegalArgumentException("Path is null or empty");
        }
        this.path =  directory;
    }

    /*CSV format:
        1: every variable is split with an ";"
        2: the elements of every list-variable are split with an ","
        3: for each object reference the uuid is saved
        4: in case a variable is null it is converted to a string named "null" */

    public Library loadLibrary() {
        Library library = Library.createNewLibrary();
        try {
            loadRoom(library);
            loadShelf(library);
            loadBook(library);
            loadVisitor(library);
            loadLibrarian(library);
            return library;
        } catch (FileNotFoundException e) {
            throw new CSVAdapterExceptions("Error loading CSV");
        }
    }

    @Override
    public void saveLibrary(Library library) {
        saveRoom(library.getRoomList());
        saveShelf(library.getShelfList());
        saveBook(library.getBookList());
        saveVisitor(library.getVisitorList());
        saveLibrarian(library.getLibrarianList());
    }

    private void saveRoom(List<Room> roomList) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path + ROOM))) {
            for (Room room : roomList) {
                writer.write(String.format("%s;%d%n", room.getRoomID().toString(), room.getShelfLimit()));
            }
        } catch (IOException e) {
            throw new CSVAdapterExceptions("Error saving room");
        }
    }

    private void saveShelf(List<Shelf> shelfList) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path + SHELF))) {
            for (Shelf shelf : shelfList) {
                writer.write(String.format("%s;%s;%s;%d;%d%n", shelf.getShelfID().toString(), shelf.getRoomIn().getRoomID().toString(), shelf.getGenre(), shelf.getShelfWidth(), shelf.getBoardNumber()));
            }
        } catch (IOException e) {
            throw new CSVAdapterExceptions("Error saving shelf");
        }
    }

    private void saveBook(List<Book> bookList) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path + BOOK))) {
            for (Book book : bookList) {
                if (book.getShelf() == null) {
                    writer.write(String.format("%s;%d;%d;%s;%s;%s;%s;%s%n", book.getBookID().toString(), book.getBookCondition(), book.getBookWidth(), book.getBookTitle(), book.getBookAuthor(), book.getBookGenre(), "null", book.getBorrowedBy().getVisitorID().toString()));
                } else {
                    writer.write(String.format("%s;%d;%d;%s;%s;%s;%s;%s%n", book.getBookID().toString(), book.getBookCondition(), book.getBookWidth(), book.getBookTitle(), book.getBookAuthor(), book.getBookGenre(), book.getShelf().getShelfID().toString(), "null"));
                }
            }
        } catch (IOException e) {
            throw new CSVAdapterExceptions("Error saving book");
        }
    }

    private void saveVisitor(List<Visitor> visitorList) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path + VISITOR))) {
            for (Visitor visitor : visitorList) {
                List<String> borrowedBookIdList = new ArrayList<>();
                for (Book book : visitor.getBorrowedBooks()) {
                    borrowedBookIdList.add(book.getBookID().toString());
                }
                if (borrowedBookIdList.isEmpty()) {
                    borrowedBookIdList.add("null");
                }
                List<String> booksToReturnIdList = new ArrayList<>();
                for (Book book : visitor.getBooksToReturn()) {
                    booksToReturnIdList.add(book.getBookID().toString());
                }
                if (booksToReturnIdList.isEmpty()) {
                    booksToReturnIdList.add("null");
                }
                writer.write(String.format("%s;%s;%s;%s;%s;%s;%s%n", visitor.getVisitorID().toString(), visitor.getVisitorName(), visitor.getVisitorSurname(), visitor.getVisitorBirthday(), visitor.getVisitorEmailAddress(), String.join(",", borrowedBookIdList), String.join(",", booksToReturnIdList)));
            }
        } catch (IOException e) {
            throw new CSVAdapterExceptions("Error saving visitor");
        }
    }

    private void saveLibrarian(List<Librarian> librarianList) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path + LIBRARIAN))) {
            for (Librarian librarian : librarianList) {
                writer.write(String.format("%s;%s;%s;%s%n", librarian.getLibrarianID().toString(), librarian.getLibrarianName(), librarian.getLibrarianSurname(), librarian.getLibrarianBirthday()));
            }
        } catch (IOException e) {
            throw new CSVAdapterExceptions("Error saving librarian");
        }
    }

    private void loadRoom(Library library) {
        try (BufferedReader reader = new BufferedReader(new FileReader(path + ROOM))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] variables = line.split(";");
                UUID uuid = UUID.fromString(variables[0]);
                int shelfLimit = Integer.parseInt(variables[1]);
                Room.createCompleteNewRoom(library, uuid, shelfLimit);
            }
        } catch (IOException e) {
            throw new CSVAdapterExceptions("Error loading room");
        }
    }

    private void loadShelf(Library library) {
        try (BufferedReader reader = new BufferedReader(new FileReader(path + SHELF))) {
            String line;
            List<Room> roomList = library.getRoomList();
            while ((line = reader.readLine()) != null) {
                String[] variables = line.split(";");
                UUID uuid = UUID.fromString(variables[0]);
                UUID roomId = UUID.fromString(variables[1]);
                Room inRoom = null;
                for (Room room : roomList) {
                    if (room.getRoomID().equals(roomId)) {
                        inRoom = room;
                        break;
                    }
                }
                String genre = variables[2];
                int shelfWidth = Integer.parseInt(variables[3]);
                int boardNumber = Integer.parseInt(variables[4]);
                Shelf.createCompleteNewShelf(library, uuid, inRoom, genre, shelfWidth, boardNumber);
            }
        } catch (IOException e) {
            throw new CSVAdapterExceptions("Error loading shelf");
        }
    }

    private void loadBook(Library library) throws FileNotFoundException {
        try (BufferedReader reader = new BufferedReader(new FileReader(path + BOOK))) {
            String line;
            List<Shelf> shelfList = library.getShelfList();
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
                        Library tempLibrary = Library.createNewLibrary();
                        Room tempRoom = Room.createTempRoom(tempLibrary);
                        tempShelf = Shelf.createTempShelf(tempLibrary, tempRoom);
                    }
                    new Book.Builder()
                            .library(library)
                            .bookID(uuid)
                            .title(bookTitle)
                            .author(bookAuthor)
                            .genre(bookGenre)
                            .shelf(tempShelf)
                            .bookCondition(bookCondition)
                            .bookWidth(bookWidth)
                            .build();
                } else { // book on shelf
                    UUID shelfId = UUID.fromString(variables[6]);
                    Shelf inShelf = null;
                    for (Shelf shelf : shelfList) {
                        if (shelf.getShelfID().equals(shelfId)) {
                            inShelf = shelf;
                            break;
                        }
                    }
                    new Book.Builder()
                            .library(library)
                            .bookID(uuid)
                            .title(bookTitle)
                            .author(bookAuthor)
                            .genre(bookGenre)
                            .shelf(inShelf)
                            .bookCondition(bookCondition)
                            .bookWidth(bookWidth)
                            .build();
                }
            }
        } catch (IOException e) {
            throw new CSVAdapterExceptions("Error loading book");
        }
    }

    private void loadVisitor(Library library) throws FileNotFoundException {
        try (BufferedReader reader = new BufferedReader(new FileReader(path + VISITOR))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] variables = line.split(";");
                UUID uuid = UUID.fromString(variables[0]);
                String name = variables[1];
                String surname = variables[2];
                String birthday = variables[3];
                String email = variables[4];
                Visitor visitor = Visitor.createCompleteVisitor(library, name, surname, birthday, email, uuid);
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
        } catch (IOException e) {
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

    private void loadLibrarian(Library library) throws FileNotFoundException {
        try (BufferedReader reader = new BufferedReader(new FileReader(path + LIBRARIAN))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] variables = line.split(";");
                UUID uuid = UUID.fromString(variables[0]);
                String name = variables[1];
                String surname = variables[2];
                String birthday = variables[3];
                Librarian.createCompleteNewLibrarian(library, uuid, name, surname, birthday);
            }
        } catch (IOException e) {
            throw new CSVAdapterExceptions("Error loading librarian");
        }
    }

    @SuppressWarnings("java:S106")
    private static String pathToDirectory(List<String> arguments, String mainOrTest) {
        CLI cli = new CLI(System.out);
        if (cli.check(arguments) == null) {

            String directoryNameToFind = arguments.getLast();
            String directory = null;

            try (Stream<Path> paths = Files.find(
                    Paths.get(System.getProperty("user.dir")).resolve("src").resolve(mainOrTest).resolve("resources"),
                    Integer.MAX_VALUE,
                    (p, attr) -> Files.isReadable(p) && attr.isDirectory() && p.getFileName().toString().equals(directoryNameToFind)
            )) {
                Optional<Path> foundPath = paths.findFirst();
                if (foundPath.isPresent()) {
                    directory = foundPath.get().toString();
                } else {
                    Path newDir = Paths.get(System.getProperty("user.dir")).resolve("src").resolve(mainOrTest).resolve("resources").resolve(directoryNameToFind);
                    Files.createDirectories(newDir);
                    directory = newDir.toString();

                    createFilesInDirectory(Path.of(newDir + "/"));
                }
            } catch (IOException e) {
                throw new CSVAdapterExceptions("Error finding directory");
            }

            return directory + "/";
        } else {
            return null;
        }
    }

    private static void createFilesInDirectory(Path directory) {
        String[] fileNames = {BOOK, SHELF, ROOM, VISITOR, LIBRARIAN};
        for (String fileName : fileNames) {
            try {
                Files.createFile(directory.resolve(fileName));
            } catch (IOException e) {
                throw new CSVAdapterExceptions("Error creating files");
            }
        }
    }
}
