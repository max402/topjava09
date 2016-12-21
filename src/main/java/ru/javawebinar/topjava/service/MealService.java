package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

/**
 * GKislin
 * 15.06.2015.
 */
public interface MealService {
    Meal save(int userId, Meal meal) throws NotFoundException;

    void delete(int userId, int id) throws NotFoundException;

    Meal get(int userId, int id) throws NotFoundException;

    List<Meal> getAll(int userId, LocalDate startDate, LocalDate endDate);

    List<Meal> getAll(int userId);

    void update(int userId, Meal meal) throws NotFoundException;
}
