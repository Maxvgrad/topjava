package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

/**
 * Created by Максим on 17.11.2017.
 */


public class FilterServlet extends HttpServlet {
    private MealRestController mealRestController;

    @Override
    public void init() throws ServletException {
        super.init();
        mealRestController = (MealRestController)
                this.getServletContext().getAttribute("mealRestController");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        LocalDateTime start = createStartLocalDateTime(request.getParameter("startDate"),
                request.getParameter("startTime"));

        LocalDateTime end = createEndLocalDateTime(request.getParameter("endDate"),
                request.getParameter("endTime"));
        mealRestController.setFilter(start, end);

        response.sendRedirect("meals");
    }

    private LocalDateTime createStartLocalDateTime(String startDate,
                                                   String startTime) {
        return LocalDateTime.of(getLocalDate(startDate, LocalDate.MIN),
                getLocalTime(startTime, LocalTime.MIN));
    }

    private LocalDateTime createEndLocalDateTime(String endDate,
                                                 String endTime) {
        return LocalDateTime.of(getLocalDate(endDate, LocalDate.MAX),
                getLocalTime(endTime, LocalTime.MAX));
    }



    private LocalDate getLocalDate(String date, LocalDate defLocalDate) {
        return date.equals("") ?
                Objects.requireNonNull(defLocalDate) :
                LocalDate.parse(date);
    }

    private LocalTime getLocalTime(String time, LocalTime defLocalTime) {
        return time.equals("") ?
                Objects.requireNonNull(defLocalTime) :
                LocalTime.parse(time);
    }
}
