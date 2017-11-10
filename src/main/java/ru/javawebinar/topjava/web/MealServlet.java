package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.NoDataBaseService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Created by Максим on 03.11.2017.
 */
public class MealServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(MealServlet.class);
    private static final MealService service = new NoDataBaseService();
    private static final String MEALS_JSP = "meals.jsp";
    private static final String MEAL_JSP = "meal.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        logger.debug("redirect to Meals");
        logger.debug(req.getParameter("action"));

        String action = req.getParameter("action");
        try {
            Objects.requireNonNull(action);
        } catch (NullPointerException e) {
            action = "";
        }

        RequestDispatcher view = null;
        int id;
        switch (action) {

            case "edit": {
                logger.debug("edit");
                if ((id = Integer.parseInt(req.getParameter("id"))) != -1) {
                    req.setAttribute("id", id);
                    req.setAttribute("meal", service.get(id));
                }

                view = req.getRequestDispatcher(MEAL_JSP);
                break;
            }

            case "add" : {
                logger.debug("add");
                req.setAttribute("meal", service.getMockMeal());
                view = req.getRequestDispatcher(MEAL_JSP);
                break;
            }

            case "delete" : {
                logger.debug("delete");
                if ((id = Integer.parseInt(req.getParameter("id"))) != -1) {

                    logger.debug("list length = " + service.getMealWithExceedList().size());
                    service.delete(id);
                    logger.debug(id + " deleted");

                    logger.debug("list length = " + service.getMealWithExceedList().size());


                }
                logger.debug("before redirect");

                resp.sendRedirect("meals");
                logger.debug("after redirect");

//                req.setAttribute("mealWithExceedList", service.getMealWithExceedList());

//                view = req.getRequestDispatcher(MEALS_JSP);
                return;
            }
            default : {
                logger.debug("default");
                req.setAttribute("mealWithExceedList", service.getMealWithExceedList());
                view = req.getRequestDispatcher(MEALS_JSP);
            }
        }

        view.forward(req, resp);
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
        service.initDB();
    }
}
