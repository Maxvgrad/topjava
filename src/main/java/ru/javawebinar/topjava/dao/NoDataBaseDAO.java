package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealUtil;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Максим on 05.11.2017.
 */
public class NoDataBaseDAO implements MealDAO {
    private static ConcurrentMap<Integer, Meal> repository;
    private static final AtomicInteger ID_COUNTER = new AtomicInteger(0);

    @Override
    public void initDB() {
        repository = new ConcurrentHashMap<>();
        MealUtil.MEALS.forEach(this::add);
    }

    @Override
    public Meal add(Meal meal) {
        if(meal.getId() == null)
            meal.setId(ID_COUNTER.getAndAdd(1));

        return repository.put(meal.getId(), meal);
    }

    @Override
    public Meal delete(int id) {
        return repository.remove(id);
    }

    @Override
    public Meal get(int id) {
        return repository.get(id);
    }

    @Override
    public ConcurrentMap<Integer, Meal> getAll() {
        return repository;
    }
}
