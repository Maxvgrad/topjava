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
import java.util.Objects;


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
                Meal meal = req.getParameter("id") == null ?
                        new Meal(LocalDateTime.now(), "", 100) :
                        service.get(getId(req));
                req.setAttribute("meal", meal);
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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        logger.debug("doPost()");
        req.setCharacterEncoding("UTF-8");

        Meal meal = new Meal(
                LocalDateTime.parse(req.getParameter("dateTime")),
                req.getParameter("description"),
                Integer.parseInt(req.getParameter("calories")));

        if(req.getParameter("id") != null)
            meal.setId(getId(req));

        service.add(meal);

        resp.sendRedirect("meals");


    }

    @Override
    public void init() throws ServletException {
        service = new NoDataBaseService();
        service.initDB();
    }

    private Integer getId(HttpServletRequest req) {
        return Integer.parseInt(Objects.requireNonNull(req.getParameter("id")));
    }
}
