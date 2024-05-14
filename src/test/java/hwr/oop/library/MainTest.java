package hwr.oop.library;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class MainTest {

    @Test
    void mainWithEmptyArgumentsTest() {
        String[] args = {};

        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            MainLibrary.main(args);
        });
    }

    @Test
    void mainWithValidArgumentsTest() {
        String[] args = {"viewBooks"};

        MainLibrary mainLibrary = new MainLibrary();
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
