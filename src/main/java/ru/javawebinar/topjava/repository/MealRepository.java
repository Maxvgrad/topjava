package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

public interface MealRepository {
    Boolean save(Meal meal);
    Boolean remove(Integer mealId);
    Meal getById(Integer id);
    List<Meal> getAllByUserId(Integer userId);
    List<Meal> getBetween(LocalDateTime startDate,
                          LocalDateTime endDate,
                          Integer userId);
}
