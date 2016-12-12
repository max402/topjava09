<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    </tr>

<c:forEach var="m" items="${meals}">
    <tr style="color: ${m.isExceed()?"red":"green"};">
        <td><c:out value="${m.getDateTime().toLocalDate()}:${m.getDateTime().toLocalTime()}"/></td>
        <td><c:out value="${m.getDescription()}"/></td>
        <td><c:out value="${m.getCalories()}"/></td>
    </tr>
</c:forEach>
</table>
</body>
</html>
