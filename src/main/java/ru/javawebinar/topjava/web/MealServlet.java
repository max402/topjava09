package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.repository.InMemoryMealRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.repository.InMemoryMealRepository.MEALS;
import static ru.javawebinar.topjava.repository.InMemoryMealRepository.getInstance;
import static ru.javawebinar.topjava.util.MealsUtil.getMealsWithExceeded;

public class MealServlet extends HttpServlet {

    private static final Logger LOG = getLogger(UserServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        LOG.debug("redirect to meals");
        InMemoryMealRepository repository = getInstance();

        String action = request.getParameter("action");
        String id = request.getParameter("id");


        if("delete".equals(action)&&(id!=null)){
            repository.remove(Integer.parseInt(id));
            response.sendRedirect("meals");
            return;
        }

        if("edit".equals(action)&&(id!=null)){
            Meal meal = repository.findById(Integer.parseInt(id));
            request.setAttribute("meal", meal);
        }

        List<MealWithExceed> filteredWithExceeded = getMealsWithExceeded(MEALS, 2000);

        request.setAttribute("meals", filteredWithExceeded);

        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("post to meals");
        InMemoryMealRepository repository = getInstance();
        request.setCharacterEncoding("UTF-8");

        String dt = request.getParameter("dateTime");//2016-12-02T10:00
        String descr = request.getParameter("description");
        String calor = request.getParameter("calories");
        String id = request.getParameter("id");
        if("".equals(id))
            repository.add(LocalDateTime.parse(dt), descr, Integer.parseInt(calor));
        else {
            repository.update(Integer.parseInt(id), LocalDateTime.parse(dt), descr, Integer.parseInt(calor));
            request.removeAttribute("meal");
        }

        //doGet(request, response);
        response.sendRedirect("meals");

    }

}
