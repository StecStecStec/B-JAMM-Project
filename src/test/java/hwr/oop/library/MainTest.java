package hwr.oop.library;

import hwr.oop.library.cli.MainLibrary;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.NoSuchElementException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MainTest {

  @Test
  void mainWithEmptyArgumentsTest() {
    new MainLibrary();

    String[] args = {};

    assertThrows(
        NoSuchElementException.class,
        () -> {
          MainLibrary.main(args);
        });
  }

  @Test
  void mainWithValidArgumentsTest() {
    String[] args = {"viewBooks", "csvFiles"};
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));

    Assertions.assertDoesNotThrow(
        () -> {
          MainLibrary.main(args);
        });

    assertThat(outputStream.toString()).contains("Books viewed");
  }

  @Test
  void mainWithInvalidArgumentsTest() {
    String[] args = {"Invalid Statement"};

    assertThrows(
        IllegalArgumentException.class,
        () -> {
          MainLibrary.main(args);
        });
  }
}
