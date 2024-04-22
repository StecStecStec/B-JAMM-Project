package hwr.oop.library;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class LibrarianTest {

    @Test
    void createLibrarian_checkRightAssignment() {
        // Create a librarian object with specific attributes
        Librarian librarian = Librarian.createNewLibrarian("Max", "Mustermann", "01.01.1999", "Hallo1234");

        // Assert that the attributes are assigned correctly
        Assertions.assertThat(librarian.getLibrarianName()).isEqualTo("Max");
        Assertions.assertThat(librarian.getLibrarianSurname()).isEqualTo("Mustermann");
        Assertions.assertThat(librarian.getLibrarianBirthday()).isEqualTo("01.01.1999");
        Assertions.assertThat(librarian.getLibrarianID()).isNotNull();
    }

    @Test
    void testEqualsMethod() {
        UUID uuid = UUID.randomUUID();
        Librarian librarian1 = Librarian.createCompleteNewLibrarian(uuid, "John", "Doe", "1990-01-01", "Hallo1234");
        Librarian librarian2 = Librarian.createCompleteNewLibrarian(uuid, "John", "Doe", "1990-01-01", "Hallo1234");
        Librarian librarian3 = Librarian.createNewLibrarian("Jane", "Doe", "1990-01-01", "Hallo1234");

        // Ensure equals method works correctly for different librarians
        Assertions.assertThat(librarian1)
                .isEqualTo(librarian1)
                .isNotEqualTo(librarian3)
                .isEqualTo(librarian2)
                .isNotNull()
                .isNotEqualTo(Room.createNewRoom(5));
    }

    @Test
    void testHashCodeMethod() {
        UUID uuid = UUID.randomUUID();
        Librarian librarian1 = Librarian.createCompleteNewLibrarian(uuid, "John", "Doe", "1990-01-01", "Hallo1234");
        Librarian librarian2 = Librarian.createCompleteNewLibrarian(uuid, "John", "Doe", "1990-01-01", "Hallo1234");
        Librarian librarian3 = Librarian.createNewLibrarian( "Jane", "Doe", "1990-01-01", "Hallo1234");

        Assertions.assertThat(librarian1.hashCode()).isNotEqualTo(librarian3.hashCode());
        Assertions.assertThat(librarian1).hasSameHashCodeAs(librarian2);
    }

}
