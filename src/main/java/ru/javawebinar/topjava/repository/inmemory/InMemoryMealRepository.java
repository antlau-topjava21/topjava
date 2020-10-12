package ru.javawebinar.topjava.repository.inmemory;

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

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Map<Integer, Meal>> generalRepository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(1, meal));
    }

    @Override
    public Meal save(int userId, Meal meal) {
        Map<Integer, Meal> repository = generalRepository.getOrDefault(userId, new HashMap<>());
//        if (repository == null) {
//            return null;
//        }
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            generalRepository.put(userId, repository);
            return meal;
        }
        // handle case: update, but not present in storage
        Meal mealResult = repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
        generalRepository.put(userId, repository);
        return mealResult;
    }

    @Override
    public boolean delete(int userId, int id) {
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
        Map<Integer, Meal> repository = generalRepository.getOrDefault(userId, null);
        if (repository == null) {
            return null;
        }
        return repository.getOrDefault(id, null);
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        Map<Integer, Meal> repository = generalRepository.getOrDefault(userId, null);
        if (repository == null) {
            return null;
        }
//        return repository.values().stream()
//                .sorted((meal1, meal2) -> meal2.getDateTime().compareTo(meal1.getDateTime())).collect(Collectors.toList());
        return repository.values().stream()
                .sorted(Comparator.comparing(Meal::getDateTime).reversed()).collect(Collectors.toList());
    }
}

