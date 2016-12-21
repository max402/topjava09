package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

/**
 * GKislin
 * 06.03.2015.
 */
@Service
public class MealServiceImpl implements MealService {

    @Autowired
    private MealRepository repository;

    @Override
    public Meal save(int userId, Meal meal) {
//        Meal savedMeal = repository.save(userId, meal);
//        if(savedMeal==null) throw new NotFoundException("meal not found with id:"+meal.getId());
//        return savedMeal;
        return checkNotFoundWithId(repository.save(userId, meal), meal.getId());
    }

    @Override
    public void delete(int userId, int id) {
        //if(!repository.delete(userId, id)) throw new NotFoundException("meal not found with id:"+id);
        checkNotFoundWithId(repository.delete(userId, id), id);
    }

    @Override
    public Meal get(int userId, int id) {
//        Meal meal = repository.get(userId, id);
//        if(meal==null) throw new NotFoundException("meal not found with id:"+id);
//        return meal;
        return checkNotFoundWithId(repository.get(userId, id), id);
    }

    @Override
    public List<Meal> getAll(int userId, LocalDate startDate, LocalDate endDate) {
        return repository.getAll(userId, startDate, endDate);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return repository.getAll(userId);
    }

    @Override
    public void update(int userId, Meal meal) {
//        Meal savedMeal = repository.save(userId, meal);
//        if(savedMeal==null) throw new NotFoundException("meal not found with id:"+meal.getId());
        checkNotFoundWithId(repository.save(userId, meal), meal.getId());
    }
}
