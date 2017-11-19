package ru.javawebinar.topjava.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collection;

@Service
public class MealServiceImpl implements MealService {
    private static final Logger LOG = LoggerFactory.getLogger(MealServiceImpl.class);

    @Autowired
    private MealRepository repository;

    @Override
    public Meal save(Meal meal) {
        int userId = AuthorizedUser.id();
        LOG.info("Class{} save{}", this.getClass().getSimpleName(), meal.getId());
        return repository.save(meal, userId);
    }

    @Override
    public void delete(int id) {
        int userId = AuthorizedUser.id();
        LOG.info("Class{} delete{}", getClass().getSimpleName(), id);
        repository.delete(id, userId);
    }

    @Override
    public Meal get(int id) {
        int userId = AuthorizedUser.id();
        LOG.info("Class{} get{}", getClass().getSimpleName(), id);
        return repository.get(id, userId);
    }

    @Override
    public Collection<MealWithExceed> getAll(int userId) {
        return MealsUtil.getFilteredWithExceeded(repository.getAll(userId), startDateTime, endDateTime, MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

    public void setRepository(MealRepository repository) {
        this.repository = repository;
    }

}