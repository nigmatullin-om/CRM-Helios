<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<t:pageLayout title="Сделки">
    <jsp:attribute name="bodyLayout">
        <div class="deal-view-links">
            <a href="${pageContext.request.contextPath}/add/deal" class="btn btn-default" role="button" >Добавить</a>
        </div>
        <br>
        <div class="deal-view-links">
            <a href="?view=funnel" class="btn btn-primary active" role="button">Воронка</a>
            <a href="?view=list" class="btn btn-primary disabled" role="button">Список</a>
        </div>
<table class="table table-striped">
    <thead>
    <tr>
        <th>Название сделки</th>
        <th>Основной контакт</th>
        <th>Комапния контакта</th>
        <th>Этап сделки</th>
        <th>Бюджет</th>
    </tr>
    </thead>
    <tbody>
    <jsp:useBean id="deals" scope="request" type="java.util.List"/>
    <c:forEach items="${deals}" var="deal">
        <tr>
            <td>${deal.name}</td>
            <td>
                <c:if test="${not empty deal.contacts}">
                    ${deal.contacts[0].name}
                </c:if>
            </td>
            <td>${deal.company.name}</td>
            <td>${deal.dealStage}</td>
            <td>${deal.budget} $</td>
        </tr>
    </c:forEach>
    </tbody>
</table>

    </jsp:attribute>
</t:pageLayout>