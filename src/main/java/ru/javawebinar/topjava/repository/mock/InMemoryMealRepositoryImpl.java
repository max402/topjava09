package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * GKislin
 * 15.09.2015.
 */
@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach((meal) -> save(AuthorizedUser.id(), meal));
    }

    @Override
    public Meal save(int userId, Meal meal) {
        Map<Integer, Meal> userMeal = repository.computeIfAbsent(userId, key -> new ConcurrentHashMap<>());
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        } else {
            if(!userMeal.containsKey(meal.getId())) return null;
        }
        userMeal.put(meal.getId(), meal);
        //repository.put(userId, userMeal);
        return meal;
    }

    @Override
    public boolean delete(int userId, int id) {
        Map<Integer, Meal> userMeal = repository.getOrDefault(userId, new ConcurrentHashMap<>());
        if(!userMeal.containsKey(id)) return false;
        userMeal.remove(id);
        //repository.put(userId, userMeal); понял что эта строка лишняя
        return true;
    }

    @Override
    public Meal get(int userId, int id) {
        Map<Integer, Meal> userMeal = repository.getOrDefault(userId, new ConcurrentHashMap<>());
        return userMeal.get(id);
    }

    @Override
    public List<Meal> getAll(int userId, LocalDate startDate, LocalDate endDate) {
        return getAll(userId).stream().filter(m-> DateTimeUtil.isBetweenDate(m.getDateTime().toLocalDate(), startDate, endDate)).collect(Collectors.toList());
    }

    @Override
    public List<Meal> getAll(int userId) {
        Map<Integer, Meal> userMeal = repository.getOrDefault(userId, new ConcurrentHashMap<>());
        Comparator<Meal> mealComparator = (m1, m2) -> m1.getDateTime().compareTo(m2.getDateTime());
        return userMeal.values().stream().sorted(mealComparator).collect(Collectors.toList());
    }
}

