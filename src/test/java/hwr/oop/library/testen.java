package hwr.oop.library;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class testen {

    @Test
    void testMyException() {
        // Testfall: Eine Methode, die eine bestimmte Ausnahme auslöst
        Assertions.assertThrows(testen.class, () -> {
            // Aufruf einer Methode, die MyException auslöst
            throw new testen("This is an example exception");
        });
    }
}
