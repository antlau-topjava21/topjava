package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.db.InMemoryMeals;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static final String INSERT_OR_EDIT = "/meal.jsp";
    private static final String LIST_MEAL = "/meals.jsp";
    private static MealDao dao;

    public MealServlet() {
        super();
        dao = new InMemoryMeals();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String forward;
        String action = req.getParameter("action");
        boolean create = false;
        Meal newMeal = null;

        if (action != null && action.equalsIgnoreCase("insert")) {
            newMeal = new Meal(LocalDateTime.MIN, "", 0);
            dao.addMeal(newMeal);
            action = "edit";
            create = true;
        }

        if (action == null) {
            forward = LIST_MEAL;
        } else if (action.equalsIgnoreCase("delete")) {
            log.debug("redirect to meals");
            int id = Integer.parseInt(req.getParameter("id"));
            dao.deleteMeal(id);
            forward = LIST_MEAL;
        } else if (action.equalsIgnoreCase("edit")) {
            log.debug("redirect to meal");
            int id;
            if (!create) {
                id = Integer.parseInt(req.getParameter("id"));
            } else {
                id = newMeal.getId();
            }
            Meal meal = dao.getMealById(id);
            req.setAttribute("meal", meal);
            forward = INSERT_OR_EDIT;
        } else if (action.equalsIgnoreCase("listMeal")) {
            log.debug("redirect to meals");
            req.setAttribute("mealsTo", MealsUtil.filteredByStreams(dao.getAllMeals(), LocalTime.MIN, LocalTime.MAX,
                    MealsUtil.CALORIES_PER_DAY));
            forward = LIST_MEAL;
        } else {
            log.debug("redirect to meal");
            forward = LIST_MEAL;
        }

        req.setAttribute("mealsTo", MealsUtil.filteredByStreams(dao.getAllMeals(), LocalTime.MIN, LocalTime.MAX,
                MealsUtil.CALORIES_PER_DAY));
        RequestDispatcher view = req.getRequestDispatcher(forward);
        view.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LocalDateTime dateTime = LocalDateTime.parse(req.getParameter("dateTime"), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
        String description = req.getParameter("description");
        int calories = Integer.parseInt(req.getParameter("calories"));
        Meal meal = new Meal(dateTime, description, calories);
        String id = req.getParameter("id");
        if (id == null || id.isEmpty()) {
            dao.addMeal(meal);
        } else {
            meal.setId(Integer.parseInt(id));
            dao.updateMeal(meal);
        }
        req.setAttribute("mealsTo", MealsUtil.filteredByStreams(dao.getAllMeals(), LocalTime.MIN, LocalTime.MAX,
                MealsUtil.CALORIES_PER_DAY));
        RequestDispatcher view = req.getRequestDispatcher(LIST_MEAL);
        view.forward(req, resp);
    }
}
