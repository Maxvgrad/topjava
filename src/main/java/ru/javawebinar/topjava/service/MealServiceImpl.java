package ru.javawebinar.topjava.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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
    public Collection<MealWithExceed> getAll() {
        int userId = AuthorizedUser.id();
        LOG.info("Class{} getAll", getClass().getSimpleName());
        return MealsUtil.getWithExceeded(repository.getAll(userId), AuthorizedUser.getCaloriesPerDay());
    }

    @Override
    public Collection<MealWithExceed> getBetween(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        int userId = AuthorizedUser.id();
        LOG.info("Class{} getBetween{}{}{}{}", getClass().getSimpleName(), startDate, endDate, startTime, endTime);

        List<Meal> list = repository.getAll(userId).stream()
                .filter(meal -> {
                    Boolean bol = DateTimeUtil.isBetween(meal.getDateTime().toLocalDate(),
                        startDate == null ? LocalDate.MIN : startDate,
                        endDate == null ? LocalDate.MAX : endDate);
                    LOG.info("Meal DATE {} bol = {}", meal.getDate(), bol);
                    return bol;
                })
                .collect(Collectors.toList());

        LOG.info("Class{} list.size() {}", getClass().getSimpleName(), list.size());
        list.forEach(m -> LOG.info(m.getDescription()));

        Collection<MealWithExceed> collection = MealsUtil.getFilteredWithExceeded(list,
                startTime == null ? LocalTime.MIN : startTime,
                endTime == null ? LocalTime.MAX : endTime,
                AuthorizedUser.getCaloriesPerDay());

        LOG.info("\n Collection<MealWithExceed> size {}", collection.size());

        return collection;
    }
}