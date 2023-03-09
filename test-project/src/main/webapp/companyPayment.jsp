<%@page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="UTF-8">
    <title>Company payment</title>
</head>
<body>
<c:if test="${name != null}">
    <h1>
        <c:out value="Hello ${name}!"/>
    </h1>
</c:if>
<%--<%--%>
<%--    if (request.getAttribute("name") != null) {--%>
<%--%>--%>
<%--<h1>Hello ${name}!</h1>--%>
<%--<%--%>
<%--    }--%>
<%--%>--%>
<form method="post">
    <table>
        <tr>
            <td>Name:</td>
            <td><input type="text" name="name"/></td>
        </tr>
        <tr>
            <td>Address:</td>
            <td><input type="text" name="address"/></td>
        </tr>
        <tr>
            <td>Tax number:</td>
            <td><input type="text" name="taxNumber"/></td>
        </tr>
        <tr>
            <td><input type="submit"/></td>
        </tr>
    </table>
</form>
</body>
</html>
