package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.mock.InMemoryMealRepositoryImpl;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * User: gkislin
 * Date: 19.08.2014
 */
public class MealServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);

    private ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
    private MealRestController mealRestController = appCtx.getBean(MealRestController.class);

    private LocalDate startDate = LocalDate.MIN;
    private LocalDate endDate = LocalDate.MAX;
    private LocalTime startTime = LocalTime.MIN;
    private LocalTime endTime = LocalTime.MAX;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        if(request.getParameter("filter")!=null) {
            String strStartDate = request.getParameter("startDate");
            startDate = strStartDate == "" ? LocalDate.MIN : LocalDate.parse(strStartDate);

            String strEndDate = request.getParameter("endDate");
            endDate = strEndDate == "" ? LocalDate.MAX : LocalDate.parse(strEndDate);

            String strStartTime = request.getParameter("startTime");
            startTime = strStartTime == "" ? LocalTime.MIN : LocalTime.parse(strStartTime);

            String strEndTime = request.getParameter("endTime");
            endTime = strEndDate == "" ? LocalTime.MAX : LocalTime.parse(strEndTime);
        } else {
            String id = request.getParameter("id");
            Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                    LocalDateTime.parse(request.getParameter("dateTime")),
                    request.getParameter("description"),
                    Integer.valueOf(request.getParameter("calories")));

            LOG.info(meal.isNew() ? "Create {}" : "Update {}", meal);
            //repository.save(AuthorizedUser.id(), meal);
            if (meal.isNew()) mealRestController.create(meal);
            else mealRestController.update(meal);
        }

        response.sendRedirect("meals");

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        request.setAttribute("startDate", startDate);
        request.setAttribute("startTime", startTime);
        request.setAttribute("endDate", endDate);
        request.setAttribute("endTime", endTime);

        if (action == null) {
            LOG.info("getAll");
            request.setAttribute("meals",
                    //MealsUtil.getWithExceeded(repository.getAll(AuthorizedUser.id(), LocalDate.MIN, LocalDate.MAX), MealsUtil.DEFAULT_CALORIES_PER_DAY));
                    mealRestController.getFilteredWithExceded(startDate, startTime, endDate, endTime));
            request.getRequestDispatcher("/meals.jsp").forward(request, response);

        } else if ("delete".equals(action)) {
            int id = getId(request);
            LOG.info("Delete {}", id);
            //repository.delete(AuthorizedUser.id(), id);
            mealRestController.delete(id);
            response.sendRedirect("meals");

        } else if ("create".equals(action) || "update".equals(action)) {
            final Meal meal = action.equals("create") ?
                    new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                    //repository.get(AuthorizedUser.id(), getId(request));
                    mealRestController.get(getId(request));
            request.setAttribute("meal", meal);
            request.getRequestDispatcher("meal.jsp").forward(request, response);
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }
}
