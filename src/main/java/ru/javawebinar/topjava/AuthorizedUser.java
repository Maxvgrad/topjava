package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.AbstractBaseEntity;

import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;

public class AuthorizedUser {
    private static Integer id = AbstractBaseEntity.START_SEQ;

    public static Integer getId() {
        return id;
    }
    public static void setId(Integer id) {
        AuthorizedUser.id = id;
    }
    public static Integer getCaloriesPerDay() {
        return DEFAULT_CALORIES_PER_DAY;
    }
}