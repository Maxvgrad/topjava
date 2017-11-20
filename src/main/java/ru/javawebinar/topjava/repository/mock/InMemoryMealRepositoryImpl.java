package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private static final Logger LOG = LoggerFactory.getLogger(InMemoryMealRepositoryImpl.class);

    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);
    private static final int USER = 0;
    private static final int ADMIN = 1;

    {
        MealsUtil.MEALS.forEach(meal -> save(meal, USER));
    }

    @Override
    public Meal save(Meal meal, int userId) {

        LOG.info("Class {} save {} {}", getClass().getSimpleName(), meal.getId(), userId);
        Map<Integer, Meal> mealMap = repository.computeIfAbsent(userId, ConcurrentHashMap::new);

        if (meal.isNew()) {
            meal.setId(counter.getAndAdd(1));
            mealMap.put(meal.getId(), meal);
            return meal;
        }

        LOG.info("repository.size() {}", repository.size());
        return mealMap.computeIfPresent(meal.getId(), (id, odMeal)-> meal);
    }

    @Override
    public void delete(int id, int userId) {
        LOG.info("Class{} delete {} {}", getClass().getSimpleName(), id, userId);
        if (repository.containsKey(userId))
            repository.get(userId).remove(id);
    }

    @Override
    public Meal get(int id, int userId) {
        LOG.info("Class{} get {} {}", getClass().getSimpleName(), id, userId);
        if (!repository.containsKey(userId))
            return null;

        return repository.get(userId).get(id);
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        LOG.info("Class {} getAll {}", getClass().getSimpleName(), userId);

        if (!repository.containsKey(userId)) {
            LOG.debug("userID {} ", userId);
            LOG.debug("repository.size {} ", repository.size());
            LOG.info("Class{} getAll {} if-statement {}",
                    getClass().getSimpleName(),
                    userId,
                    !repository.containsKey(userId));
            return null;
        }
        return repository.get(userId).values();
    }

}

