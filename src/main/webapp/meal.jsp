<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="ru">
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h1>Edit meal</h1>
<jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
<form method="post" action="meals">
    <input type="hidden" name="id" value="${meal.id}">
    <dl>
        <dt>DateTime:</dt>
        <dt><input type="datetime-local" value="${meal.dateTime}" name="dateTime"></dt>
    </dl>
    <dl>
        <dt>Description:</dt>
        <dt><input type="text" value="${meal.description}" name="description"></dt>
    </dl>
    <dl>
        <dt>Calories</dt>
        <dt><input type="number" value="${meal.calories}" name="calories"></dt>
    </dl>
    <button type="submit">Save</button>
    <button onclick="window.history.back()">Cancel</button>
</form>
</body>
</html>
