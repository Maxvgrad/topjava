package ru.javawebinar.topjava.util;

import com.sun.org.apache.xpath.internal.operations.Bool;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.IntStream;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        List<UserMealWithExceed> list = getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);
//        .toLocalDate();
//        .toLocalTime();
        for(UserMealWithExceed user : list) {
            System.out.println(user);
        }

    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList,
                                                                    LocalTime startTime,
                                                                    LocalTime endTime,
                                                                    int caloriesPerDay) {
        List<UserMealWithExceed> result = new ArrayList<>();

        Map<LocalDate, Integer> caloriesPerDayMap = new HashMap<>();

        for (UserMeal userMeal : mealList) {

            LocalDate mealDate = userMeal.getDateTime().toLocalDate();

            //Adding new date into the caloriesPerDayMap or checking is calories exceeded for date
            if(caloriesPerDayMap.get(mealDate) == null) {
                caloriesPerDayMap.put(mealDate, userMeal.getCalories());
            } else {
                caloriesPerDayMap.put(mealDate, caloriesPerDayMap.get(mealDate) + userMeal.getCalories());
            }
        }
        for(UserMeal userMeal : mealList) {
            if(TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(), startTime, endTime)) {
                UserMealWithExceed userMealWithExceed =
                        new UserMealWithExceed(userMeal.getDateTime(),
                                userMeal.getDescription(),
                                userMeal.getCalories(),
                                caloriesPerDayMap.get(userMeal.getDateTime().toLocalDate()) > caloriesPerDay);
                result.add(userMealWithExceed);
            }
        }
        return result;
    }
}
