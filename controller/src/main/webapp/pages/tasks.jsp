<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<t:pageLayout title="Задачи">
    <jsp:attribute name="bodyLayout">
<table class="table table-striped">
    <thead>
    <tr>
        <th>Дата исполнения</th>
        <th>Время</th>
        <th>Ответственный</th>
        <th>Контакт\сделка</th>
        <th>Тип зададачи</th>
        <th>Текст зададачи</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${tasks}" var="task">
        <tr>
            <td>${task.getFinishDate()}</td>
            <td>Doe</td>
            <td>${task.getResponsibleUser().getName()}</td>
            <td>John</td>
            <td>Doe</td>
            <td>${task.getDescription()}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>

    </jsp:attribute>
</t:pageLayout>