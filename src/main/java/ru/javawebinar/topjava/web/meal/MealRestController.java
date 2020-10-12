package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.Collection;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
public class MealRestController {
    private MealService service;

    public MealRestController(MealService service) {
        this.service = service;
    }

    public Collection<MealTo> getAll() {
        return MealsUtil.getTos(service.getAll(authUserId()), SecurityUtil.authUserCaloriesPerDay());
    }

    public MealTo get(int id) {
        return getAll().stream().filter(mealTo -> mealTo.getId() == id).findAny().orElse(null);
    }

    public MealTo create(Meal meal) {
        checkNew(meal);
        service.create(authUserId(), meal);
        return getAll().stream().filter(mealTo -> mealTo.getId().equals(meal.getId())).findAny().orElse(null);
    }

    public void delete(int id) {
        service.delete(authUserId(), id);
    }

    public MealTo update(Meal meal, int id) {
        assureIdConsistent(meal, id);
        service.update(authUserId(), meal);
        return getAll().stream().filter(mealTo -> mealTo.getId().equals(meal.getId())).findAny().orElse(null);
    }
}