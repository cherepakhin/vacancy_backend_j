package ru.perm.v.vacancy_j.example;

import java.util.Objects;

public class Car {
    public String color ="";
    public String type ="";

    public Car() {
    }

    public Car(String color, String type) {
        this.color = color;
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Car car)) return false;
        return Objects.equals(color, car.color) && Objects.equals(type, car.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, type);
    }

    @Override
    public String toString() {
        return "Car{" +
                "color='" + color + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
