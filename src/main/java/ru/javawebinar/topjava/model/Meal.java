package ru.javawebinar.topjava.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Meal extends AbstractBaseEntity {

    private LocalDateTime mealDate;
    private String description;
    private int calories;

    public Meal() {}

    public Meal(LocalDateTime mealDate, String description, int calories) {
        this(null, mealDate, description, calories);
    }

    public Meal(Integer id, LocalDateTime mealDate, String description, int calories) {
        super(id);
        this.mealDate = mealDate;
        this.description = description;
        this.calories = calories;
    }

    public LocalDateTime getMealDateTime() {
        return mealDate;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public LocalDate getMealDate() {
        return mealDate.toLocalDate();
    }

    public LocalTime getMealTime() {
        return mealDate.toLocalTime();
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", mealDate=" + mealDate +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
}
