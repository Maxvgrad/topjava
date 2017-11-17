package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;

import java.time.LocalDateTime;
import java.util.Collection;

@Controller
public class MealRestController {
    private static final Logger LOG = LoggerFactory.getLogger(MealRestController.class);

    @Autowired
    private MealService service;

    public Meal save(Meal meal) {
        LOG.info("save{}", meal);
        return service.save(meal, AuthorizedUser.id());
    }

    public void delete(int id) {
        LOG.info("delete{}", id);
        service.delete(id, AuthorizedUser.id());
    }

    public Meal get(int id) {
        return service.get(id, AuthorizedUser.id());
    }

    public Collection<MealWithExceed> getAll() {
        return service.getAll(AuthorizedUser.id());
    }

    public void setService(MealService service) {
        this.service = service;
    }

    public void setFilter(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        service.setFilter(startDateTime, endDateTime);
    }
    public LocalDateTime getFilter() {
        return service.getFilter();
    }


}