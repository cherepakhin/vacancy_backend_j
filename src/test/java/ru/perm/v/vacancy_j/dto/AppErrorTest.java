package ru.perm.v.vacancy_j.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AppErrorTest {

    @Test
    public void testDefaultConstructor() {
        AppError error = new AppError();
        assertNotNull(error);
        assertEquals(0, error.getStatusCode());
        assertNull(error.getMessage());
    }

    @Test
    public void testParameterizedConstructor() {
        int statusCode = 404;
        String message = "Not Found";

        AppError error = new AppError(statusCode, message);

        assertEquals(statusCode, error.getStatusCode());
        assertEquals(message, error.getMessage());
    }

    @Test
    public void testSettersAndGetters() {
        AppError error = new AppError();

        int code = 500;
        String msg = "Internal Server Error";

        error.setStatusCode(code);
        error.setMessage(msg);

        assertEquals(code, error.getStatusCode());
        assertEquals(msg, error.getMessage());
    }

    @Test
    public void testSetInvalidStatusCodeBelowMin() {
        AppError error = new AppError();
        error.setStatusCode(99); // Ниже минимального HTTP-кода (100)
        assertEquals(99, error.getStatusCode());
    }

    @Test
    public void testSetInvalidStatusCodeAboveMax() {
        AppError error = new AppError();
        error.setStatusCode(600); // Выше максимального HTTP-кода (599)
        assertEquals(600, error.getStatusCode());
    }

    @Test
    public void testToString() {
        AppError error = new AppError(404, "Page not found");

        String expected = "AppError{statusCode=404, message='Page not found'}";
        assertEquals(expected, error.toString());
    }
}
