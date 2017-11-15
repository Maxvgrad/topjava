package ru.javawebinar.topjava.repository.mock;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        //TODO initialize repository
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (repository.containsKey(userId))
            return null;

        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        }
        repository.get(userId).put(meal.getId(), meal);
        return meal;
    }

    @Override
    public void delete(int id, int userId) {
        if (repository.containsKey(userId))
            repository.get(userId).remove(id);
    }

    @Override
    public Meal get(int id, int userId) {
        if (repository.containsKey(userId))
            return null;

        return repository.get(userId).get(id);
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        if (repository.containsKey(userId))
            return null;
        //TODO sort result collection by date
        return repository.get(userId).values();
    }
}

