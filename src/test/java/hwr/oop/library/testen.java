package hwr.oop.library;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

//macht 90% linecoverage bei csvAdapter
class testen {

   @Test
    void testCSVAdapter() throws FileNotFoundException {
        final OutputStream outputStream = new ByteArrayOutputStream();
        final var consoleUI = new CLI(outputStream);


        // Testfall: Eine Methode, die eine bestimmte Ausnahme auslöst
        assertThrows(RuntimeException.class, () -> {
            // Erstellen Sie eine Instanz von CSVAdapter und übergeben Sie einen ungültigen Dateipfad
            CSVAdapter csvAdapter = new CSVAdapter("invalid_path_to_file.csv");
            // Eine Methode aufrufen, die eine FileNotFoundException auslösen könnte
            csvAdapter.loadCSV();
            csvAdapter.saveCSV();
        });


    }



}
