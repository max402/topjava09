package ru.javawebinar.topjava.repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static ru.javawebinar.topjava.util.MealsUtil.getWithExceeded;

/**
 * Created by Maxim Grischenko on 13.12.16.
 */
public class InMemoryMealRepository implements MealRepository {

//    private static volatile InMemoryMealRepository instance;

    private AtomicInteger COUNTER = new AtomicInteger(0);
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();

    {
        save(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        save(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        save(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        save(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        save(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        save(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
    }

//    private InMemoryMealRepository() {
//    }
//
//    public static InMemoryMealRepository getInstance(){
//        if(instance==null){
//            synchronized(InMemoryMealRepository.class) {
//                if(instance==null) instance=new InMemoryMealRepository();
//            }
//        }
//        return instance;
//    }

    @Override
    public void remove(int id) {
        repository.remove(id);
    }

    @Override
    public Collection<Meal> getAll() {
        return repository.values();
    }

    @Override
    public void save(Meal meal) {
        if(meal.isNew()){
            meal.setId(COUNTER.incrementAndGet());
        }
        repository.put(meal.getId(), meal);
    }

    @Override
    public Meal get(int id) {
        return repository.get(id);
    }
}
