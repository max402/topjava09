package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.InMemoryMealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import static org.slf4j.LoggerFactory.getLogger;


public class MealServlet extends HttpServlet {

    private static final Logger LOG = getLogger(UserServlet.class);

    private InMemoryMealRepository repository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        repository = new InMemoryMealRepository();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        LOG.debug("redirect to meals");
        //InMemoryMealRepository repository = getInstance();

        String action = request.getParameter("action");
        String id = request.getParameter("id");


        if("delete".equals(action)&&(id!=null)){
            repository.remove(Integer.parseInt(id));
            response.sendRedirect("meals");
            return;
        }

        if("edit".equals(action)&&(id!=null)){
            Meal meal = repository.get(Integer.parseInt(id));
            request.setAttribute("meal", meal);
        }


        List withExceeded = MealsUtil.getWithExceeded(repository.getAll(), 2000);

        request.setAttribute("meals", withExceeded);

        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("post to meals");
        //InMemoryMealRepository repository = getInstance();
        request.setCharacterEncoding("UTF-8");

        String dt = request.getParameter("dateTime");//2016-12-02T10:00
        String descr = request.getParameter("description");
        String calor = request.getParameter("calories");
        String id = request.getParameter("id");

        Meal meal = new Meal((id.isEmpty() ? null:Integer.valueOf(id)), LocalDateTime.parse(dt), descr, Integer.valueOf(calor));
        repository.save(meal);

//        if("".equals(id))
//            repository.add(LocalDateTime.parse(dt), descr, Integer.parseInt(calor));
//        else {
//            repository.update(Integer.parseInt(id), LocalDateTime.parse(dt), descr, Integer.parseInt(calor));
//            request.removeAttribute("meal");
//        }


        //doGet(request, response);
        response.sendRedirect("meals");

    }

}
