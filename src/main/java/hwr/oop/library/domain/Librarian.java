package hwr.oop.library.domain;

import java.util.Objects;
import java.util.UUID;

public class Librarian {

    private final UUID librarianID;
    private final String librarianName;
    private final String librarianSurname;
    private final String librarianBirthday;

    public UUID getLibrarianID() {
        return librarianID;
    }

    public String getLibrarianBirthday() {
        return librarianBirthday;
    }

    public String getLibrarianSurname() {
        return librarianSurname;
    }

    public String getLibrarianName() {
        return librarianName;
    }

    public static Librarian createNewLibrarian(Library library, String librarianName, String librarianSurname, String librarianBirthday) {
        return new Librarian(library, UUID.randomUUID(), librarianName, librarianSurname, librarianBirthday);
    }

    public static Librarian createCompleteNewLibrarian(Library library, UUID uuid, String librarianName, String librarianSurname, String librarianBirthday) {
        return new Librarian(library, uuid, librarianName, librarianSurname, librarianBirthday);
    }

    private Librarian(Library library, UUID uuid, String librarianName, String librarianSurname, String librarianBirthday) {
        this.librarianID = uuid;
        this.librarianName = librarianName;
        this.librarianSurname = librarianSurname;
        this.librarianBirthday = librarianBirthday;
        library.addLibrarian(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Librarian librarian = (Librarian) o;
        return Objects.equals(librarianID, librarian.librarianID) && Objects.equals(librarianName, librarian.librarianName) && Objects.equals(librarianSurname, librarian.librarianSurname) && Objects.equals(librarianBirthday, librarian.librarianBirthday);
    }

    @Override
    public int hashCode() {
        return Objects.hash(librarianID, librarianName, librarianSurname, librarianBirthday);
    }
}
