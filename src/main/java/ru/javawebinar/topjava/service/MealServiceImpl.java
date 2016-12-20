package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
@Service
public class MealServiceImpl implements MealService {

    @Autowired
    private MealRepository repository;

    @Override
    public Meal save(int userId, Meal meal) throws NotFoundException {
        Meal savedMeal = repository.save(userId, meal);
        if(savedMeal==null) throw new NotFoundException("meal not found with id:"+meal.getId());
        return savedMeal;
    }

    @Override
    public void delete(int userId, int id) throws NotFoundException {
        if(!repository.delete(userId, id)) throw new NotFoundException("meal not found with id:"+id);
    }

    @Override
    public Meal get(int userId, int id) throws NotFoundException {
        Meal meal = repository.get(userId, id);
        if(meal==null) throw new NotFoundException("meal not found with id:"+id);
        return meal;
    }

    @Override
    public List<Meal> getAll(int userId, LocalDate startDate, LocalDate endDate) {
        return repository.getAll(userId, startDate, endDate);
    }

    @Override
    public void update(int userId, Meal meal) throws NotFoundException {
        Meal savedMeal = repository.save(userId, meal);
        if(savedMeal==null) throw new NotFoundException("meal not found with id:"+meal.getId());
    }
}
