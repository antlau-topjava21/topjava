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
<h1>Meals</h1>
<jsp:useBean id="mealsTo" scope="request" type="java.util.List"/>

<table border="1">
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th></th>
        <th></th>
    </tr>
        <c:forEach var="meal" items="${mealsTo}">
            <tr style="color: ${meal.excess ? 'red' : 'green'}">
                <td><javatime:format value="${meal.dateTime}" pattern="dd-MM-yyyy hh:mm:ss"/></td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?action=edit&id=<c:out value="${meal.id}"/>">Update</a></td>
                <td><a href="meals?action=delete&id=<c:out value="${meal.id}"/>">Delete</a></td>
            </tr>
        </c:forEach>
</table>
<p><a href="meals?action=insert">Add User</a></p>
</body>
</html>