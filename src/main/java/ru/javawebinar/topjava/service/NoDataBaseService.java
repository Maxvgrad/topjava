package ru.javawebinar.topjava.service;


import ru.javawebinar.topjava.dao.MealDAO;
import ru.javawebinar.topjava.dao.NoDataBaseDAO;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Максим on 05.11.2017.
 */
public class NoDataBaseService implements MealService {
    private MealDAO mealDAO = new NoDataBaseDAO();

    @Override
    public void initDB() {
        mealDAO.initDB();
    }

    @Override
    public CopyOnWriteArrayList<MealWithExceed> getMealWithExceedList() {
        return new CopyOnWriteArrayList<>(
                MealsUtil.getWithExceeded(mealDAO.getMealList(), 2000));
    }

    @Override
    public boolean add(Meal meal) {
        return mealDAO.add(meal);
    }

    @Override
    public Meal edit(int id, Meal meal) {
        return mealDAO.edit(id, meal);
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
    public Meal getMockMeal() {
        return new Meal(LocalDateTime.now(), "description", 100);
    }
}
