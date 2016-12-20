package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
@Controller
public class MealRestController {
    protected final Logger LOG = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;

    public List<Meal> getAll() {
        LOG.info("getAll");
        return service.getAll(AuthorizedUser.id(), LocalDate.MIN, LocalDate.MAX);
    }

    public List<MealWithExceed> getFilteredWithExceded(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        LOG.info("getFilteredWithExceed");
        return MealsUtil.getFilteredWithExceeded(service.getAll(AuthorizedUser.id(), startDate, endDate), startTime, endTime, MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

    public Meal get(int id) {
        LOG.info("get " + id);
        return service.get(AuthorizedUser.id(), id);
    }

    public Meal create(Meal meal) {
        meal.setId(null);
        LOG.info("create " + meal);
        return service.save(AuthorizedUser.id(), meal);
    }

    public void delete(int id) {
        LOG.info("delete " + id);
        service.delete(AuthorizedUser.id(), id);
    }

    public void update(Meal meal) {
        LOG.info("update " + meal);
        service.update(AuthorizedUser.id(), meal);
    }
}
