<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="p" tagdir="/WEB-INF/tags" %>
<p:page title="Client list">
    <style>
        td {
            border: 1px solid black;
        }

        thead > tr > td {
            font-weight: bold;
        }
    </style>
    <h1>List of clients</h1>
    <table>
        <thead>
        <tr>
            <td>ID</td>
            <td>Type</td>
            <td>Name</td>
            <td>Address</td>
            <td>Action</td>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${clients}" var="client">
            <tr>
                <td>
                    <c:out value="${client.id}"/>
                </td>
                <td>
                    <c:out value="${client.getClass().getSimpleName()}"/>
                </td>
                <td>
                    <c:out value="${client.name}"/>
                </td>
                <td>
                    <c:out value="${client.address}"/>
                </td>
                <td>
                    <form method="post"
                          action="${client.getClass().getSimpleName() eq 'Person' ? 'personPayment' : 'companyPayment'}">
                        <input type="hidden" name="name" value="${client.name}">
                        <input type="hidden" name="address" value="${client.address}">
                        <c:choose>
                            <c:when test="${client.getClass().getSimpleName() eq 'Company'}">
                                <input type="hidden" name="taxNumber" value="${client.taxNumber}">
                            </c:when>
                            <c:otherwise>
                                <input type="hidden" name="idNumber" value="${client.idNumber}">
                            </c:otherwise>
                        </c:choose>
                        <input type="submit" value="Pay!">
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</p:page>
