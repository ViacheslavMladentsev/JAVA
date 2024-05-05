package edu.lieineyes.numbers;

import edu.lieineyes.exception.IllegalNumberException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class NumberWorkerTest {

    private static final NumberWorker test = new NumberWorker();

    @ParameterizedTest
    @ValueSource (ints = {13, 7, 19})
    void isPrimeForPrimes(Integer val) {
        assertTrue(test.isPrime(val));
    }

    @ParameterizedTest
    @ValueSource (ints = {9, 10, 12})
    public void isPrimeForNotPrimes(Integer val) {
        assertFalse(test.isPrime(val));
    }

    @ParameterizedTest
    @ValueSource (ints = {-5, 0, 1})
    void isPrimeForIncorrectNumbers(Integer val) {
        assertThrows(IllegalNumberException.class, () -> test.isPrime(val));
    }

    @ParameterizedTest
    @CsvFileSource (resources = "/data.csv")
    void digitSum(Integer val, Integer result) {
        assertEquals(result, test.digitsSum(val));
    }

}