package com.fibonacci;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class FibonacciCalculatorTest {
    private FibonacciCalculator calculator;

    @BeforeEach
    public void setUp() {
        calculator = new FibonacciCalculator();
    }

    @ParameterizedTest
    @CsvSource(value = {"3:2", "8:21", "10:55"}, delimiter = ':')
    @DisplayName("Test index number 3, 8, 10")
    public void testGetFibonacciNumberWhenIndexThreeEightTen(int index,
                                                             int expectedFibonacciNumber) {
        int result = calculator.getFibonacciNumber(index);

        assertEquals(expectedFibonacciNumber, result);
    }

    @Test
    @DisplayName("Test index less 1")
    public void testGetFibonacciNumberIfLessOne() {
        int index = 0;
        String expectedMessage = "Index should be greater or equal to 1";

        IllegalArgumentException thrown = Assertions.assertThrows(
                IllegalArgumentException.class, () ->
                        calculator.getFibonacciNumber(index));

        Assertions.assertEquals(expectedMessage, thrown.getMessage());
    }

    @ParameterizedTest
    @CsvSource(value = {"1:1", "2:1"}, delimiter = ':')
    @DisplayName("Test index 1 or 2")
    public void testGetFibonacciNumberIfOneOrTwo(int index,
                                                 int expectedFibonacciNumber) {
        int result = calculator.getFibonacciNumber(index);

        assertEquals(expectedFibonacciNumber, result);
    }
}
