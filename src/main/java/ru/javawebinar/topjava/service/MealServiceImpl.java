package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.util.Collection;

@Service
public class MealServiceImpl implements MealService {

    private LocalDateTime startDateTime = LocalDateTime.MIN;
    private LocalDateTime endDateTime = LocalDateTime.MAX;

    @Autowired
    private MealRepository repository;

    @Override
    public Meal save(Meal meal, int userId) {
        return repository.save(meal, userId);
    }

    @Override
    public void delete(int id, int userId) {
        repository.delete(id, userId);
    }

    @Override
    public Meal get(int id, int userId) {
        return repository.get(id, userId);
    }

    @Override
    public Collection<MealWithExceed> getAll(int userId) {
        return MealsUtil.getFilteredWithExceeded(repository.getAll(userId), startDateTime, endDateTime, MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

    public void setRepository(MealRepository repository) {
        this.repository = repository;
    }

    @Override
    public void setFilter(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    @Override
    public LocalDateTime getFilter() {
        return startDateTime;
    }
}