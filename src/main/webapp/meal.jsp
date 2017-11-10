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
        <input type="text" name="id" value="${id}">
        <div>
            Date and Time:
            <javatime:format value="${meal.dateTime}" pattern="yyyy-MM-dd" var="parsedDate"/>
            <javatime:format value="${meal.dateTime}" pattern="hh:mm" var="parsedTime"/>
            <input type="datetime-local" name="dateTime" value="${parsedDate}T${parsedTime}"/>
        </div>
        <div>
            Description:
            <input type="text" name="description" value="${meal.description}"/>
        </div>
        <div>
            Calories:
            <input type="number" min="0" name="calories" value="${meal.calories}" />
        </div>
        <input type="submit" value="<c:out value="${not empty id ? 'Edit' : 'Add'}"/>"/>
    </form>
</body>
</html>