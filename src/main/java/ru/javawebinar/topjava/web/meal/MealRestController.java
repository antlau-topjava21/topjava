package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    protected final Logger log = LoggerFactory.getLogger(getClass());

    private final MealService service;

    public MealRestController(MealService service) {
        this.service = service;
    }

    public Collection<MealTo> getAll() {
        log.info("getAll meal of user {}", authUserId());
        return MealsUtil.getTos(service.getAll(authUserId()), SecurityUtil.authUserCaloriesPerDay());
    }

    public MealTo get(int id) {
        log.info("get {} from user {}", id, authUserId());
        return getAll().stream().filter(mealTo -> mealTo.getId() == id).findAny().orElse(null);
    }

    public MealTo create(Meal meal) {
        log.info("create {} to user {}", meal, authUserId());
        checkNew(meal);
        service.create(authUserId(), meal);
        return getAll().stream().filter(mealTo -> mealTo.getId().equals(meal.getId())).findAny().orElse(null);
    }

    public void delete(int id) {
        log.info("delete {} from user {}", id, authUserId());
        service.delete(authUserId(), id);
    }

    public MealTo update(Meal meal, int id) {
        log.info("update {} to user {}", meal, authUserId());
        assureIdConsistent(meal, id);
        service.update(authUserId(), meal);
        return getAll().stream().filter(mealTo -> mealTo.getId().equals(meal.getId())).findAny().orElse(null);
    }
}