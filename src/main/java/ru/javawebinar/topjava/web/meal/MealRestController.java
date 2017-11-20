package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

@Controller
public class MealRestController {
    private static final Logger LOG = LoggerFactory.getLogger(MealRestController.class);

    @Autowired
    private MealService service;

    public Meal save(Meal meal) {
        LOG.info("save{}", meal);
        return service.save(meal);
    }

    public void delete(int id) {
        LOG.info("delete{}", id);
        service.delete(id);
    }

    public Meal get(int id) {
        return service.get(id);
    }

    public Collection<MealWithExceed> getBetween(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        return service.getBetween(startDate, endDate, startTime, endTime);
    }

    public Collection<MealWithExceed> getAll() {
        return service.getAll();
    }

}