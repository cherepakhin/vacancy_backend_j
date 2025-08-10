package ru.perm.v.vacancy_j.example;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ArgumentCaptureTest {
    @Test
    void testArgumentCaptor() {
        // Mock a List object
        List<String> mockList = mock(List.class);
        // Use the mock object
        mockList.add("Mockito");
        // Capture the argument
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

        verify(mockList).add(captor.capture());
        // Assert the captured value
        assertEquals("Mockito", captor.getValue());

    }
}
