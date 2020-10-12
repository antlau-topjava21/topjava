package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.MealsUtil.meals;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Map<Integer, Meal>> generalRepository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);
    private final int demoUserId = 1;

    {
        meals.forEach(meal -> save(demoUserId, meal));
        log.info("initiate demoUser with meals {}", meals);
    }

    @Override
    public Meal save(int userId, Meal meal) {
        log.info("save {}", meal);
        Map<Integer, Meal> repository = generalRepository.getOrDefault(userId, new HashMap<>());
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            generalRepository.put(userId, repository);
            return meal;
        }
        Meal mealResult = repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
        generalRepository.put(userId, repository);
        return mealResult;
    }

    @Override
    public boolean delete(int userId, int id) {
        log.info("delete {}", id);
        Map<Integer, Meal> repository = generalRepository.getOrDefault(userId, null);
        if (repository == null) {
            return false;
        }
        boolean result = repository.remove(id) != null;
        generalRepository.put(userId, repository);
        return result;
    }

    @Override
    public Meal get(int userId, int id) {
        log.info("get {}", id);
        Map<Integer, Meal> repository = generalRepository.getOrDefault(userId, null);
        if (repository == null) {
            return null;
        }
        return repository.getOrDefault(id, null);
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        log.info("getAll meal from user {}", userId);
        Map<Integer, Meal> repository = generalRepository.getOrDefault(userId, null);
        if (repository == null) {
            return null;
        }
        return repository.values().stream()
                .sorted(Comparator.comparing(Meal::getDateTime).reversed()).collect(Collectors.toList());
    }
}

