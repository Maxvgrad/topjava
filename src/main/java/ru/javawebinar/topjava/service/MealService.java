package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;

import java.util.concurrent.ConcurrentMap;

/**
 * Created by Максим on 05.11.2017.
 */
public interface MealService {

    void initDB();

    Meal add(Meal meal);

    Meal delete(int id);

    Meal get(int id);

    ConcurrentMap<Integer, Meal> getAll();

}
