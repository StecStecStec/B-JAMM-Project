package hwr.oop.library;

import hwr.oop.library.cli.MainLibrary;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertThrows;

class MainTest {

    @Test
    void mainWithEmptyArgumentsTest() {
        String[] args = {};

        assertThrows(NoSuchElementException.class, () -> {
            MainLibrary.main(args);
        });
    }

    @Test
    void mainWithValidArgumentsTest() {
        String[] args = {"viewBooks"};

        Assertions.assertDoesNotThrow(() -> {
            MainLibrary.main(args);
        });
    }

    @Test
    void mainWithInvalidArgumentsTest() {
        String[] args = {"invalid_argument"};

        assertThrows(IllegalStateException.class, () -> {
            MainLibrary.main(args);
        });
    }
}
