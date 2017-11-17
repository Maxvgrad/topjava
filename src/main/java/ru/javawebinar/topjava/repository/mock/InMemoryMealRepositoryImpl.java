package ru.javawebinar.topjava.repository.mock;

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
    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);
    private static final int USER = 0;
    private static final int ADMIN = 1;

    {
        MealsUtil.MEALS.forEach(meal -> {
            meal.setId(counter.getAndAdd(1));
            addToRepository(meal, (counter.get() % 2) == 0 ? USER : ADMIN);
//            System.out.println("repository.size() " + repository.size());
        });
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (!repository.containsKey(userId))
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
        if (!repository.containsKey(userId))
            return null;

        return repository.get(userId).get(id);
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        if (!repository.containsKey(userId))
            return null;
        return repository.get(userId).values();
    }

    private void addToRepository(Meal meal, int id) {
        Map<Integer, Meal> map = repository.containsKey(id) ?
                repository.get(id) :
                new ConcurrentHashMap<>();
        map.put(meal.getId(), meal);
        repository.put(id, map);
    }
}

