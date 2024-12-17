<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Address List</title>
</head>
<body>
<h1>Address List</h1>

<ul>
    <c:forEach items="${addresses}" var="address">
        <li>
            <a href="<c:url value='/website/addresses/address/${address.id}'/>">
                    ${address.street}, ${address.city}, ${address.state}
            </a> - ${address.postalCode}, ${address.country}
        </li>
    </c:forEach>
</ul>
</body>
</html>
