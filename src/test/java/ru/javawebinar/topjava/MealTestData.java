package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {

    public static final int MEAL_1_ID_FOR_USER = START_SEQ + 2;
    public static final Meal MEAL_1_FOR_USER = new Meal(START_SEQ + 2, LocalDateTime.of(2020, 1, 30, 10, 0),
            "Завтрак", 500);
    public static final Meal MEAL_2_FOR_USER = new Meal(START_SEQ + 3, LocalDateTime.of(2020, 1, 30, 13, 0),
            "Обед", 1000);
    public static final Meal MEAL_3_FOR_USER = new Meal(START_SEQ + 4, LocalDateTime.of(2020, 1, 30, 20, 0),
            "Ужин", 500);
    public static final Meal MEAL_4_FOR_USER = new Meal(START_SEQ + 5, LocalDateTime.of(2020, 1, 31, 0, 0),
            "Еда на граничное значение", 100);
    public static final Meal MEAL_5_FOR_USER = new Meal(START_SEQ + 6, LocalDateTime.of(2020, 1, 31, 10, 0),
            "Завтрак", 1000);
    public static final Meal MEAL_6_FOR_USER = new Meal(START_SEQ + 7, LocalDateTime.of(2020, 1, 31, 13, 0),
            "Обед", 500);
    public static final Meal MEAL_7_FOR_USER = new Meal(START_SEQ + 8, LocalDateTime.of(2020, 1, 31, 20, 0),
            "Ужин", 410);
    public static final Meal MEAL_1_FOR_ADMIN = new Meal(START_SEQ + 9, LocalDateTime.of(2015, 6, 1, 14, 0),
            "Админ ланч", 510);
    public static final Meal MEAL_2_FOR_ADMIN = new Meal(START_SEQ + 10, LocalDateTime.of(2015, 6, 1, 21, 0),
            "Админ ужин", 1500);

    public static Meal getUpdated() {
        return new Meal(START_SEQ + 2, LocalDateTime.of(2020, 1, 30, 10, 0),
                "UpdatedDescription", 330);
    }

    public static final List<Meal> MEALS = Arrays.asList(MEAL_1_FOR_USER, MEAL_2_FOR_USER, MEAL_3_FOR_USER, MEAL_4_FOR_USER,
            MEAL_5_FOR_USER, MEAL_6_FOR_USER, MEAL_7_FOR_USER, MEAL_1_FOR_ADMIN, MEAL_2_FOR_ADMIN);

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.of(2020, 10, 21, 21, 55),
                "dinner", 500);
    }

    public static List<Meal> getMealsForUser() {
        return Arrays.asList(
                MEAL_7_FOR_USER,
                MEAL_6_FOR_USER,
                MEAL_5_FOR_USER,
                MEAL_4_FOR_USER,
                MEAL_3_FOR_USER,
                MEAL_2_FOR_USER,
                MEAL_1_FOR_USER
        );
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("registered", "roles").isEqualTo(expected);
    }
}
