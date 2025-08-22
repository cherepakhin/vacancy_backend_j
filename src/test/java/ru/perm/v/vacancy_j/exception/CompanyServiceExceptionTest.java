package ru.perm.v.vacancy_j.exception;

import org.junit.jupiter.api.Test;
import ru.perm.v.vacancy_j.exceptions.CompanyServiceException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CompanyServiceExceptionTest {

    @Test
    void testMessageException() {
        CompanyServiceException exception = new CompanyServiceException("Test message");

        assertEquals("Test message", exception.getMessage());
    }
}
