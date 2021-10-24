package template;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CalcSumTest {

    Calculator sut;
    String numbersFilePath;

    @BeforeEach
    void setUp() {
        this.sut = new Calculator();
        this.numbersFilePath = getClass().getResource("numbers.txt").getPath();
    }

    @Test
    void sumOfNumbers() throws IOException {
        int sum = sut.calcSum(numbersFilePath);
        assertThat(sum).isEqualTo(10);
    }

    @Test
    void multiplyOfNumbers() throws IOException {
        assertThat(sut.calcMultiply(numbersFilePath)).isEqualTo(24);
    }
}
