<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
<html>
<head>
    <title>Meals</title>
</head>
<p>
<h3><a href="index.html">Home</a></h3>
<h2>Meals</h2>

<table>
    <tr>
        <th>Date:</th>
        <th>Time:</th>
        <th>Description:</th>
        <th>Calories:</th>
        <th colspan="2">Action:</th>
    </tr>
    <c:forEach items="${mealWithExceedList}" var="meal" varStatus="loop">
        <tr>
            <td><c:out value="${meal.getDateTime().toLocalDate()}"/></td>
            <td><c:out value="${meal.getDateTime().toLocalTime()}"/></td>
            <td><c:out value="${meal.getDescription()}"/></td>
            <td><c:out value="${meal.getCalories()}"/></td>
            <td><a href="meals?action=edit&id=<c:out value="${loop.index}"/>">edit</a></td>
            <td><a href="meals?action=delete&id=<c:out value="${loop.index}"/>">delete</a></td>
        </tr>
    </c:forEach>
</table>
<p><a href="meals?action=add">Add meal</a></p>
</body>
</html>