package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;

import java.util.Collection;

public interface MealService {
    Meal save(Meal meal);

    void delete(int id);

    Meal get(int id);

    Collection<MealWithExceed> getAll(int userId);

}