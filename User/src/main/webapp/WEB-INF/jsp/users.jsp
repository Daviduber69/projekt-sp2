<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>User List</title>
</head>
<body>
<h1>User List</h1>

<ul>
    <c:forEach items="${users}" var="user">
        <li>
            <a href="<c:url value='/website/users/user/${user.id}'/>">${user.name}</a> - ${user.email}
        </li>
    </c:forEach>
</ul>
</body>
</html>
