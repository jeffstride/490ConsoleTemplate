package com.mrstride;

import org.junit.Test;

import com.mrstride.console.SortingWork;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SampleTest {
    @Test
    public void verifySampleMethod() {
        // Arrange
        SortingWork sw = new SortingWork();
        
        // Act
        int actual = sw.sampleMethod(4);

        // Assert
        int expected = 4 * 2;
        assertEquals(actual, expected, "Failed to do simple math.");
    }
}
