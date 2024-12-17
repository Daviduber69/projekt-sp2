<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>User Details</title>
</head>
<body>
<h1>User Details</h1>

<p>Name: ${user.name}</p>
<p>Email: ${user.email}</p>
<p>Address:</p>
<ul>
    <li>Street: ${user.address.street}</li>
    <li>City: ${user.address.city}</li>
    <li>State: ${user.address.state}</li>
    <li>Postal Code: ${user.address.postalCode}</li>
    <li>Country: ${user.address.country}</li>
</ul>

</body>
</html>
