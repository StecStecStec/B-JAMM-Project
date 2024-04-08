package hwr.oop;

import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;

public class BookIdTest {
    @Test
    void getBookId_returnsCorrectBookId(){
        Book book = new Book();
        book.bookId = 1;
        Assertions.assertThat(book.getBookId()).isEqualTo(1);
    }
}
