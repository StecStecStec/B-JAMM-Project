package hwr.oop.library;

import hwr.oop.library.cli.MainLibrary;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MainTest {

    @Test
    void mainWithEmptyArgumentsTest() {
        String[] args = {};

        Assertions.assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            MainLibrary.main(args);
        });
    }

    //@Test
    void mainWithValidArgumentsTest() {
        String[] args = {"createVisitor", "Hans", "Meier", "01.01.2020", "meier@sd.com"};

        Assertions.assertDoesNotThrow(() -> {
            MainLibrary.main(args);
        });

        String[] args2 = {"deleteVisitor", "meier@sd.com"};
        Assertions.assertDoesNotThrow(() -> {
            MainLibrary.main(args2);
        });
    }

    @Test
    void mainWithInvalidArgumentsTest() {
        String[] args = {"invalid_argument"};

        Assertions.assertThrows(IllegalStateException.class, () -> {
            MainLibrary.main(args);
        });
    }
}
