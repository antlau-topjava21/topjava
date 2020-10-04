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
    </tr>
        <c:forEach var="meal" items="${mealsTo}">
            <c:choose>
                <c:when test="${meal.excess == true}">
                    <tr style="color: red">
                        <td><javatime:format value="${meal.dateTime}" pattern="dd-MM-yyyy hh:mm:ss"/></td>
                        <td>${meal.description}</td>
                        <td>${meal.calories}</td>
                    </tr>
                </c:when>
                <c:otherwise>
                    <tr style="color: green">
                        <td><javatime:format value="${meal.dateTime}" pattern="dd-MM-yyyy hh:mm:ss"/></td>
                        <td>${meal.description}</td>
                        <td>${meal.calories}</td>
                    </tr>
                </c:otherwise>
            </c:choose>
        </c:forEach>
</table>
</body>
</html>