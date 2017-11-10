package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Максим on 05.11.2017.
 */
public interface MealDAO {

    CopyOnWriteArrayList<Meal> getMealList();

    void initDB();

    boolean add(Meal meal);

    Meal edit(int id, Meal meal);

    Meal delete(int id);

    Meal get(int id);


}
