package ru.javawebinar.topjava.repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.MealsUtil.getMealsWithExceeded;

/**
 * Created by Maxim Grischenko on 13.12.16.
 */
public class InMemoryMealRepository implements MealRepository {

    private static volatile InMemoryMealRepository instance;

    public static int COUNTER = 0;

    public static List<Meal> MEALS = new ArrayList<>(Arrays.asList(
            new Meal(++COUNTER, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
            new Meal(++COUNTER, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
            new Meal(++COUNTER, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
            new Meal(++COUNTER, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
            new Meal(++COUNTER, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
            new Meal(++COUNTER, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
    ));

    private InMemoryMealRepository() {
    }

    public static InMemoryMealRepository getInstance(){
        if(instance==null){
            synchronized(InMemoryMealRepository.class) {
                if(instance==null) instance=new InMemoryMealRepository();
            }
        }
        return instance;
    }

    @Override
    public void add(LocalDateTime dateTime, String description, int calories) {
        MEALS.add(new Meal(++COUNTER, dateTime, description, calories));
    }

    @Override
    public void remove(int id) {
        MEALS.remove(findById(id));
    }

    @Override
    public List<MealWithExceed> getAll() {
        return getMealsWithExceeded(MEALS, 2000);
    }

    @Override
    public void update(int id, LocalDateTime dateTime, String description, int calories) {
        Meal meal = findById(id);
        meal.setDateTime(dateTime);
        meal.setDescription(description);
        meal.setCalories(calories);
    }

    @Override
    public Meal findById(int id) {
        for(Meal m:MEALS) if(m.getId()==id) return m;
        return null;
    }
}
