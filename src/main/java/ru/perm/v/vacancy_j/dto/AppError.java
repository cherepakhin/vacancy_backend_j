package ru.perm.v.vacancy_j.dto;

import java.util.Objects;

public class AppError {
    private int statusCode;
    private String message;

    public AppError() {
    }

    public AppError(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AppError appError)) return false;
        return statusCode == appError.statusCode && Objects.equals(message, appError.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(statusCode, message);
    }

    @Override
    public String toString() {
        return "AppError{" +
                "statusCode=" + statusCode +
                ", message='" + message + "'" +
                '}';
    }
}
