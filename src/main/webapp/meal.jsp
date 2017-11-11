<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
    <form action="meals" method="post">
        <jsp:useBean id="meal" scope="request" type="ru.javawebinar.topjava.model.Meal"/>
        <input type="hidden" name="id" value="${meal.id}">

        <dl>
            <dt>Date and Time:</dt>
            <dd>
                <input type="datetime-local" name="dateTime" value="${meal.dateTime}"/>
            </dd>

            <dt>Description:</dt>
            <dd>
                <input type="text" name="description" value="${meal.description}"/>
            </dd>

            <dt>Calories:</dt>
            <dd>
                <input type="number" min="0" name="calories" value="${meal.calories}"/>
            </dd>

            <input type="submit" value="<c:out value="${not empty meal.id ? 'Edit' : 'Add'}"/>"/>
        </dl>
    </form>
</body>
</html>