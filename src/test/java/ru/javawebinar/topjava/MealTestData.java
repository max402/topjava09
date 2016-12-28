package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.ModelMatcher;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;

import static java.time.LocalDateTime.of;
import static ru.javawebinar.topjava.model.BaseEntity.START_SEQ;

/**
 * GKislin
 * 13.03.2015.
 */
public class MealTestData {

    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;

    public static final Map<Integer, List<Meal>> mealsMap = new HashMap<>();
    static {
        mealsMap.put(USER_ID, new ArrayList<>(Arrays.asList(
                new Meal(START_SEQ+7, of(2015, Month.MAY, 31, 20, 0), "Ужин", 511),
                new Meal(START_SEQ+6, of(2015, Month.MAY, 31, 13, 0), "Обед", 1000),
                new Meal(START_SEQ+5, of(2015, Month.MAY, 31, 10, 0), "Завтрак", 500),
                new Meal(START_SEQ+4, of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new Meal(START_SEQ+3, of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new Meal(START_SEQ+2, of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500))));

        mealsMap.put(ADMIN_ID, new ArrayList<>(Arrays.asList(
                new Meal(START_SEQ+9, of(2015, Month.JUNE, 1, 21, 0), "Админ ужин", 1500),
                new Meal(START_SEQ+8, of(2015, Month.JUNE, 1, 14, 0), "Админ ланч", 510))));

    }

    public static final ModelMatcher<Meal> MATCHER = new ModelMatcher<>(
            (expected, actual) -> expected == actual ||
                              (Objects.equals(expected.getId(), actual.getId())
                            && Objects.equals(expected.getDescription(), actual.getDescription())
                            && Objects.equals(expected.getCalories(), actual.getCalories())
                            && Objects.equals(expected.getDateTime(), actual.getDateTime())
                    )
    );

}
