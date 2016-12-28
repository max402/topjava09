package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.model.BaseEntity;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static ru.javawebinar.topjava.MealTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class MealServiceTest {

    @Autowired
    private MealService service;

    @Autowired
    private DbPopulator dbPopulator;

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
    }

    @Test
    public void testSave() throws Exception {
        Meal newMeal = new Meal(null, LocalDateTime.of(2015,5,1,10,0), "Завтрак", 500);
        Meal created = service.save(newMeal, USER_ID);
        newMeal.setId(created.getId());
        List<Meal> m = new ArrayList<>(mealsMap.get(USER_ID));
        m.add(newMeal);
        MATCHER.assertCollectionEquals(m, service.getAll(USER_ID));
    }

    @Test
    public void testGet() throws Exception {
        Meal meal = service.get(BaseEntity.START_SEQ+6, USER_ID);
        MATCHER.assertEquals(mealsMap.get(USER_ID).get(1), meal);
    }

    @Test(expected = NotFoundException.class)
    public void testDelete() throws Exception {
        service.delete(BaseEntity.START_SEQ+2, USER_ID);
        service.get(BaseEntity.START_SEQ+2, USER_ID);
    }

    @Test
    public void getBetweenDateTimes() throws Exception {
        LocalDateTime start = LocalDateTime.of(2015, Month.MAY,1,0,0);
        LocalDateTime end = LocalDateTime.of(2015, Month.MAY, 31,0,0);
        List<Meal> expected = mealsMap.get(USER_ID).stream().filter(meal -> DateTimeUtil.isBetween(meal.getDateTime(), start, end)).collect(Collectors.toList());
        MATCHER.assertCollectionEquals(expected, service.getBetweenDateTimes(start, end, USER_ID));
    }

    @Test
    public void testGetAll() throws Exception {
        Collection<Meal> all = service.getAll(USER_ID);
        MATCHER.assertCollectionEquals(mealsMap.get(USER_ID), all);

    }

    @Test
    public void testUpdate() throws Exception {
        Meal updated = new Meal(mealsMap.get(USER_ID).get(1));
        updated.setDescription("UpdatedDescription");
        updated.setCalories(330);
        service.update(updated, USER_ID);
        MATCHER.assertEquals(updated, service.get(BaseEntity.START_SEQ+6, USER_ID));
    }

    @Test(expected = NotFoundException.class)
    public void testDeleteWrongUser() throws Exception {
        service.delete(BaseEntity.START_SEQ+2, ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void testGetWrongUser() throws Exception {
        service.get(BaseEntity.START_SEQ+3, ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void testUpdateWrongUser() throws Exception {
        Meal updated = new Meal(mealsMap.get(USER_ID).get(1));
        service.update(updated, ADMIN_ID);
    }

}