package com.fibonacci;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FibonacciServiceTest {
    private final FibonacciRepository fibonacciRepository =
            Mockito.mock(FibonacciRepository.class);
    private final FibonacciCalculator fibonacciCalculator =
            Mockito.mock(FibonacciCalculator.class);
    private final FibonacciService fibonacciService =
            new FibonacciService(fibonacciRepository, fibonacciCalculator);

    @Test
    @DisplayName("Test index from DB")
    public void getFibonacciNumberFromDB() {
        int index = 3;
        int value = 2;

        FibonacciNumber fibonacciNumber = new FibonacciNumber();
        fibonacciNumber.setIndex(index);
        fibonacciNumber.setValue(value);

        when(fibonacciRepository.findByIndex(index)).thenReturn(Optional.of(fibonacciNumber));

        assertEquals(value, fibonacciService.fibonacciNumber(index).getValue());

        verify(fibonacciRepository, times(1)).findByIndex(index);
        verify(fibonacciCalculator, times(0)).getFibonacciNumber(index);
    }

    @Test
    @DisplayName("Test index from calculator")
    public void getFibonacciNumberFromCalculator() {
        int index = 3;
        int value = 2;

        fibonacciService.fibonacciNumber(index);

        FibonacciNumber fibonacciNumber = new FibonacciNumber();
        fibonacciNumber.setValue(value);
        fibonacciNumber.setIndex(index);

        when(fibonacciCalculator.getFibonacciNumber(index)).thenReturn(value);

        assertEquals(value, fibonacciNumber.getValue());

        verify(fibonacciCalculator, times(1)).getFibonacciNumber(index);
        verify(fibonacciRepository, times(1)).findByIndex(index);
    }

    @Test
    @DisplayName("Test index less 1")
    public void getFibonacciNumberIfLessThanOne() {
        int index = 1;

        when(fibonacciService.fibonacciNumber(index)).
                thenThrow(new IllegalArgumentException());

        verify(fibonacciCalculator, times(1)).getFibonacciNumber(index);
        verify(fibonacciRepository, times(1)).findByIndex(index);
    }
}
