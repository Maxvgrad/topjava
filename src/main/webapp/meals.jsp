<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="ru.javawebinar.topjava.util.DateTimeUtil" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="resources/stylesheets/meals.css"/>
    <title>Meals</title>
</head>
<p>
<h3><a href="index.html">Home</a></h3>
<h2>Meals</h2>

<table>
    <thead>
        <tr>
            <th>Time stamp:</th>
            <th>Description:</th>
            <th>Calories:</th>
            <th colspan="2">Action:</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${mealWithExceedList}" var="meal" varStatus="loop">
            <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.model.MealWithExceed"/>
            <tr class="${meal.exceed ? 'exceed' : 'normal'}">
                <td><%=DateTimeUtil.toString(meal.getDateTime())%></td>
                <td><c:out value="${meal.description}"/></td>
                <td><c:out value="${meal.calories}"/></td>
                <td><a href="meals?action=edit&id=${meal.id}">edit</a></td>
                <td><a href="meals?action=delete&id=${meal.id}">delete</a></td>
            </tr>
        </c:forEach>
    </tbody>
</table>
<p><a href="meals?action=add">Add meal</a></p>
</body>
</html>