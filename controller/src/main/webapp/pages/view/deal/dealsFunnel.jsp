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
            <a href="?view=funnel" class="btn btn-primary disabled" role="button">Воронка</a>
            <a href="?view=list" class="btn btn-primary active" role="button">Список</a>
        </div>
<table class="table">
    <thead>
    <tr>
        <th>Первичный контакт</th>
        <th>Переговоры</th>
        <th>Принимаю решение</th>
        <th>Согласование договора</th>
    </tr>
    </thead>
    <tbody>
    <tr>
    <t:dealCard deals="${dealsByStage['PRIMARY_CONTACT']}" />
    <t:dealCard deals="${dealsByStage['NEGOTIATION']}" />
    <t:dealCard deals="${dealsByStage['DECIDING']}" />
    <t:dealCard deals="${dealsByStage['AGREEMENT']}" />
    </tr>
    </tbody>
</table>

    </jsp:attribute>
</t:pageLayout>