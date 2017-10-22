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
    private static Map<LocalDate, Map<Integer, Object>> caloriesPerDayMap = new HashMap<>();
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
        //Keys
        int CAL_KEY = 1;
        int EXC_KEY = 2;

        //Boolean ref
        Boolean isExceededRef;

        List<UserMealWithExceed> result = new ArrayList<>();

        for (UserMeal userMeal : mealList) {

            LocalDate mealDate = userMeal.getDateTime().toLocalDate();

            //Adding new date into the caloriesPerDayMap or checking is calories exceeded for date
            if(caloriesPerDayMap.get(mealDate) == null) {
                Map<Integer, Object> dataMap = new HashMap<>();
                dataMap.put(CAL_KEY, userMeal.getCalories());
                dataMap.put(EXC_KEY, userMeal.getCalories() > caloriesPerDay);
                caloriesPerDayMap.put(mealDate, dataMap);

                isExceededRef = (Boolean) dataMap.get(EXC_KEY);
            } else {

                Map<Integer, Object> dataMap = caloriesPerDayMap.get(mealDate);
                int caloriesSum = ((Integer) dataMap.get(CAL_KEY)) + userMeal.getCalories();
                dataMap.put(CAL_KEY, caloriesSum);
                isExceededRef = (Boolean) dataMap.get(EXC_KEY);
            }

            if(TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(), startTime, endTime)) {
                UserMealWithExceed userMealWithExceed =
                        new UserMealWithExceed(userMeal.getDateTime(),
                                userMeal.getDescription(),
                                userMeal.getCalories(),
                                (Boolean) caloriesPerDayMap.get(mealDate).get(EXC_KEY));
                result.add(userMealWithExceed);
            }
        }
        for(Map.Entry<LocalDate, Map<Integer, Object>> entry : caloriesPerDayMap.entrySet()) {
            System.out.println("--------------------");
            System.out.println(entry.getKey());
            System.out.println(entry.getValue().get(EXC_KEY));
            System.out.println(entry.getValue().get(CAL_KEY));
        }


        for(UserMealWithExceed d : result) {
            System.out.println(d);
        }

        return result;
    }
}
