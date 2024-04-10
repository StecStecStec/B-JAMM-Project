package hwr.oop;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class VisitorTest{
    @Test
    void createVisitor_checkRightAssignment() {
        Visitor visitor = new Visitor("Max", "Mustermann", "01.01.1999", "max.mustermann@gmx.de");
        Assertions.assertThat(visitor.getVisitorName()).isEqualTo("Max");
        Assertions.assertThat(visitor.getVisitorSurname()).isEqualTo("Mustermann");
        Assertions.assertThat(visitor.getVisitorBirthday()).isEqualTo("01.01.1999");
        Assertions.assertThat(visitor.getVisitorEmailAddress()).isEqualTo("max.mustermann@gmx.de");
    }
}
