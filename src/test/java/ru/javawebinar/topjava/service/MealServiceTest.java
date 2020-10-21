package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.Util;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal actual = service.get(MEAL_1_ID_FOR_USER, USER_ID);
        assertMatch(actual, MEAL_1_FOR_USER);
    }

    @Test
    public void delete() {
        service.delete(MEAL_1_ID_FOR_USER, USER_ID);
        assertThrows(NotFoundException.class, () -> service.delete(MEAL_1_ID_FOR_USER, USER_ID));
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> expected = getMealsForUser()
                .stream()
                .filter(meal -> Util.isBetweenHalfOpen(meal.getDateTime(),
                        LocalDateTime.of(2020, 1, 30, 0, 0),
                        LocalDateTime.of(2020, 1, 31, 0, 0)))
                .collect(Collectors.toList());
        Collections.reverse(expected);
        List<Meal> actual = service.getBetweenInclusive(
                LocalDate.of(2020, 1, 30),
                LocalDate.of(2020, 1, 30),
                USER_ID);
        assertMatch(actual, expected);
    }

    @Test
    public void getAll() {
        List<Meal> expectedMeals = getMealsForUser();
        List<Meal> actualMeals = service.getAll(USER_ID);
        assertMatch(actualMeals, expectedMeals);
    }

    @Test
    public void update() {
        Meal updated = getUpdated();
        service.update(updated, USER_ID);
        assertMatch(service.get(updated.getId(), USER_ID), getUpdated());
    }

    @Test
    public void create() {
        Meal newMeal = getNew();
        Meal created = service.create(newMeal, USER_ID);
        Integer newId = created.getId();
        newMeal = getNew();
        newMeal.setId(newId);
        assertMatch(created, newMeal);
        assertMatch(service.get(newId, USER_ID), newMeal);
    }
}