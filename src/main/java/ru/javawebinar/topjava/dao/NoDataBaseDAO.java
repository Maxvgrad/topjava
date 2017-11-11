package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealUtil;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Максим on 05.11.2017.
 */
public class NoDataBaseDAO implements MealDAO {
    private CopyOnWriteArrayList<Meal> mealDB;
    private static ConcurrentMap<Integer, Meal> repository;

    @Override
    public CopyOnWriteArrayList<Meal> getMealList() {
        return mealDB;
    }

    @Override
    public void initDB() {
        repository = MealUtil.
        mealDB = new CopyOnWriteArrayList<>();
        Arrays.asList(
                new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        ).forEach(mealDB::add);
    }

    @Override
    public boolean add(Meal meal) {
        return mealDB.add(meal);
    }

    @Override
    public Meal edit(int id, Meal meal) {
        return mealDB.set(id, meal);
    }

    @Override
    public Meal delete(int id) {
        return mealDB.remove(id);
    }

    @Override
    public Meal get(int id) {
        return mealDB.get(id);
    }
}
