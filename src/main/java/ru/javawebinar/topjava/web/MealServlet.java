package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.NoDataBaseService;
import ru.javawebinar.topjava.util.MealUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Created by Максим on 03.11.2017.
 */
public class MealServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(MealServlet.class);
    private static MealService service;
    private static final String MEALS_JSP = "meals.jsp";
    private static final String MEAL_JSP = "meal.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        logger.debug("redirect to Meals");
        String action = req.getParameter("action");
        RequestDispatcher view = null;

        switch (action == null ? "" : action) {

            case "edit":
            case "add" : {
                logger.debug("add / edit");
                req.setAttribute("meal", service.getMockMeal());
                view = req.getRequestDispatcher(MEAL_JSP);
                view.forward(req, resp);
                break;
            }
            case "delete" : {
                logger.debug("delete");
                service.delete(Integer.parseInt(req.getParameter("id")));
                resp.sendRedirect("meals");
                break;
            }
            default : {
                logger.debug("default");
                req.setAttribute("mealWithExceedList",
                        MealUtil.getWithExceeded(service.getAll().values(), 2000));
                view = req.getRequestDispatcher(MEALS_JSP);
                view.forward(req, resp);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("doPost()");

        req.setCharacterEncoding("UTF-8");

        Meal meal = new Meal(
                LocalDateTime.parse(req.getParameter("dateTime")),
                req.getParameter("description"),
                Integer.parseInt(req.getParameter("calories"))
        );

        int id = 0;
        try {
            id = Integer.parseInt(req.getParameter("id"));
        } catch (NumberFormatException e) {
            id = -1;
        }

        if(id == -1)
            service.add(meal);
        else
            service.edit(id, meal);

        resp.sendRedirect("meals");


    }

    @Override
    public void init() throws ServletException {
        service = new NoDataBaseService();
        service.initDB();
    }
}
