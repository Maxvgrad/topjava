package ru.javawebinar.topjava.service;


import ru.javawebinar.topjava.dao.MealDAO;
import ru.javawebinar.topjava.dao.NoDataBaseDAO;
import ru.javawebinar.topjava.model.Meal;

import java.util.concurrent.ConcurrentMap;

/**
 * Created by Максим on 05.11.2017.
 */
public class NoDataBaseService implements MealService {
    private MealDAO mealDAO;

    @Override
    public void initDB() {
        mealDAO = new NoDataBaseDAO();
        mealDAO.initDB();
    }

    @Override
    public Meal add(Meal meal) {
        return mealDAO.add(meal);
    }

    @Override
    public Meal delete(int id) {
        return mealDAO.delete(id);
    }

    @Override
    public Meal get(int id) {
        return mealDAO.get(id);
    }

    @Override
    public ConcurrentMap<Integer, Meal> getAll() {
        return mealDAO.getAll();
    }
}
