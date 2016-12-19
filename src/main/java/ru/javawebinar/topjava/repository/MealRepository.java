package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * Created by Maxim Grischenko on 13.12.16.
 */
public interface MealRepository {

    public void save(Meal meal);

    public void remove(int id);

    public Collection<Meal> getAll();

    public Meal get(int id);
}
