package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.web.user.AbstractUserController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import static org.slf4j.LoggerFactory.getLogger;

public class UserServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);
    private static AbstractUserController controller;
    private static

    @Override
    public void init() throws ServletException {
        try (ConfigurableApplicationContext ctx =
                     new ClassPathXmlApplicationContext("/spring/spring-app.xml")) {
            controller = (AbstractUserController) ctx.getBean("adminRestController");
        }
    }

    @Override
    public void destroy() {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("forward to users");
        request.getRequestDispatcher("/users.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int id = Integer.parseInt(Objects.requireNonNull(req.getParameter("userID")));
        if ()
        AuthorizedUser.setId(id);
        resp.sendRedirect("meals");
    }

}
