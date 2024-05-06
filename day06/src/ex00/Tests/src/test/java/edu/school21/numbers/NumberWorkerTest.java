package edu.school21.numbers;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

public class NumberWorkerTest {

    @ParameterizedTest
    @ValueSource(ints = {13, 17, 19, 23, 29, 31})
    void isPrimeForPrimes(int input) {
        NumberWorker numberWorker = new NumberWorker();
        assertTrue(numberWorker.isPrime(input));
    }

    @ParameterizedTest
    @ValueSource(ints = {12, 16, 18, 22, 28, 30})
    void isPrimeForNotPrimes(int input) {
        NumberWorker numberWorker = new NumberWorker();
        assertFalse(numberWorker.isPrime(input));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, -5, -555, -1000})
    void isPrimeForIncorrectNumbers(int input) {
        NumberWorker numberWorker = new NumberWorker();
        assertThrows(IllegalArgumentException.class, () -> numberWorker.isPrime(input));
    }
    @ParameterizedTest
    @CsvFileSource(resources = "/data.csv", numLinesToSkip = 1)
    void digitSum(int input, int output) {
        NumberWorker numberWorker = new NumberWorker();
        assertEquals(numberWorker.digitsSum(input), output);
    }
}
