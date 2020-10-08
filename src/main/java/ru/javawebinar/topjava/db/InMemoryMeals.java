package ru.javawebinar.topjava.db;

import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryMeals implements MealDao {
    private static final AtomicInteger id = new AtomicInteger(0);
    public static Map<Integer ,Meal> meals = new ConcurrentHashMap<>();

    static
    {
        for (Meal meal : MealsUtil.MEALS) {
            if (meal.isIdNull()) {
                meal.setId(id.incrementAndGet());
                meals.put(meal.getId(), meal);
            }
        }
    }

    @Override
    public void addMeal(Meal meal) {
        meal.setId(id.incrementAndGet());
        meals.put(meal.getId(), meal);
    }

    @Override
    public void updateMeal(Meal meal) {
        meals.put(meal.getId(), meal);
    }

    @Override
    public void deleteMeal(int id) {
        meals.remove(id);
    }

    @Override
    public Meal getMealById(int id) {
        return meals.getOrDefault(id, null);
    }

    @Override
    public List<Meal> getAllMeals() {
        return new ArrayList<>(meals.values());
    }
}
