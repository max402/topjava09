<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://example.com/functions" %>
<html>
<head>
    <title>Meal list</title>
</head>
<body>
<h2><a href="index.html">Home</a></h2>
<h2>Meal list</h2>
<table border="1">
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th>Edit</th>
        <th>Delete</th>
    </tr>

<%--<jsp:useBean id="meals" scope="request" type="java.util.List"/>--%>
<c:forEach var="m" items="${meals}">
    <tr style="color: ${m.exceed?"red":"green"};">
        <%--<td><c:out value="${m.getDateTime().toLocalDate()} ${m.getDateTime().toLocalTime()}"/></td>--%>
        <td>${f:formatLocalDateTime(m.dateTime, 'dd.MM.yyyy HH:mm:SS')}</td>
        <td><c:out value="${m.description}"/></td>
        <td><c:out value="${m.calories}"/></td>
        <td><a href="?action=edit&id=${m.id}">Edit</a></td>
        <td><a href="?action=delete&id=${m.id}">Delete</a></td>
    </tr>
</c:forEach>
</table>

<br><br><br>
<h2>Add meal form</h2>

<%--<jsp:useBean id="meal" scope="request" type="ru.javawebinar.topjava.model.Meal"/>--%>
<form method="post">
    Date:<br>
    <input type="datetime-local" name="dateTime" value="${meal.dateTime}"><br>
    Description:<br>
    <input type="text" name="description" value="${meal.description}"><br>
    Calories:<br>
    <input type="number" name="calories" value="${meal.calories}"><br>
    <br>
    <input type="hidden" name="id" value="${meal.id}">
    <input type="submit" value="${param.action=="edit"?"Update":"Add"}"><br>
</form>


</body>
</html>
