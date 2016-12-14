package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Maxim Grischenko on 13.12.16.
 */
public interface MealRepository {

    public void add(LocalDateTime dateTime, String description, int calories);

    public void update(int id, LocalDateTime dateTime, String description, int calories);

    public void remove(int id);

    public List<MealWithExceed> getAll();

    public Meal findById(int id);
}
