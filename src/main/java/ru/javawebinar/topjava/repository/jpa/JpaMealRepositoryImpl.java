package ru.javawebinar.topjava.repository.jpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class JpaMealRepositoryImpl implements MealRepository {

    private static final Logger log = LoggerFactory.getLogger(JpaMealRepositoryImpl.class);

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        User user = em.getReference(User.class, userId);
//        user.getMeals().forEach(m -> log.info("{} ID {}", m.getDate(), m.getId()));
//        log.info("existing meal id {}", meal.getId());

        if (meal.isNew()) {
            meal.setUser(user);
            em.persist(meal);
        } else {
            if (!user.getMeals()
                    .stream()
                    .anyMatch(m -> {
                        log.info("m.id({}) == meal.id({}) -> {}",
                                m.getId(), meal.getId(),
                                m.getId().equals(meal.getId()));
                        return m.getId().equals(meal.getId());})) {
                log.debug("Meal !contains {}", meal);
                throw new NotFoundException("NOT FOUND!");
            }
            meal.setUser(user);
            em.merge(meal);
        }
        return meal;
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        return em.createQuery("DELETE FROM Meal m WHERE m.id=:id AND m.user.id=:userId")
                .setParameter("id", id)
                .setParameter("userId", userId)
                .executeUpdate() != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal = em.find(Meal.class, id);
        if (!(meal.getUser().getId() == userId)) {
            throw new NotFoundException(
                    String.format("User's (id = %d) meal %d is not found", userId, id));
        }
        return meal;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return em.createQuery("SELECT m FROM Meal m WHERE m.user.id= :userId " +
                "ORDER BY m.dateTime DESC", Meal.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return em.createQuery("SELECT m FROM Meal m " +
                "WHERE m.user.id = :userId AND dateTime " +
                "BETWEEN :startDate AND :endDate " +
                "ORDER BY m.dateTime DESC", Meal.class)
                .setParameter("userId", userId)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();
    }
}