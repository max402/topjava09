package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static ru.javawebinar.topjava.util.ValidationUtil.checkIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

/**
 * User: gkislin
 * Date: 22.08.2014
 */
@Controller
public class RootController {
    @Autowired
    private UserService service;

    @Autowired
    private MealService mealService;

    private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String root() {
        return "index";
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String users(Model model) {
        model.addAttribute("users", service.getAll());
        return "users";
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public String setUser(HttpServletRequest request) {
        int userId = Integer.valueOf(request.getParameter("userId"));
        AuthorizedUser.setId(userId);
        return "redirect:meals";
    }

    @RequestMapping(value = "/meals", method = RequestMethod.GET)
    public String meals(Model model) {
        int userId = AuthorizedUser.id();
        LOG.info("getAll for User {}", userId);
        List<MealWithExceed> meals = MealsUtil.getWithExceeded(mealService.getAll(userId), AuthorizedUser.getCaloriesPerDay());
        model.addAttribute("meals", meals);
        return "meals";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String deleteMeal(@RequestParam(value = "id") int id, Model model) {

        int userId = AuthorizedUser.id();
        LOG.info("delete meal {} for User {}", id, userId);
        mealService.delete(id, userId);
        return "redirect:meals";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String createMeal(HttpServletRequest request) {
        int userId = AuthorizedUser.id();
        final Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
        request.setAttribute("meal", meal);
        return "meal";
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String updateMeal(
            @RequestParam(value = "id", required=false) int id, HttpServletRequest request) {
        int userId = AuthorizedUser.id();
        Meal meal = mealService.get(getId(request), userId);
        checkIdConsistent(meal, id);
        request.setAttribute("meal", meal);
        return "meal";
    }

    @RequestMapping(value = "/saveMeal", method = RequestMethod.POST)
    public String saveMeal(
            @RequestParam(value = "id", required=false) Integer id, HttpServletRequest request) {
        final Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.valueOf(request.getParameter("calories")));

        int userId = AuthorizedUser.id();

        if(id==null){
            LOG.info("Create {} for User {}", meal, userId);
            mealService.save(meal, userId);
        } else {
            meal.setId(id);
            LOG.info("update {} for User {}", meal, userId);
            mealService.update(meal, userId);
        }

        return "redirect:meals";
    }



    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }


    @RequestMapping(value = "/filter", method = RequestMethod.POST)
    public String filterMeal(HttpServletRequest request) {

        LocalDate startDate = DateTimeUtil.parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = DateTimeUtil.parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = DateTimeUtil.parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = DateTimeUtil.parseLocalTime(request.getParameter("endTime"));

        int userId = AuthorizedUser.id();
        LOG.info("getBetween dates {} - {} for time {} - {} for User {}", startDate, endDate, startTime, endTime, userId);

        List<MealWithExceed> meals = MealsUtil.getFilteredWithExceeded(
                mealService.getBetweenDates(
                    startDate != null ? startDate : DateTimeUtil.MIN_DATE,
                    endDate != null ? endDate : DateTimeUtil.MAX_DATE, userId),
                    startTime != null ? startTime : LocalTime.MIN,
                    endTime != null ? endTime : LocalTime.MAX,
                AuthorizedUser.getCaloriesPerDay()
        );

        request.setAttribute("meals", meals);
        return "meals";
    }
}
