<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<t:pageLayout title="Dashboard">
    <jsp:attribute name="bodyLayout">
        <!-- Example row of columns -->
        <div class="row">
            <div class="col-md-4">
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <h3 class="panel-title">Сделки</h3>
                    </div>
                    <div class="panel-body">
                        <p>Всего сделок: ${fn:length(deals)}</p>
                        <c:set var="sum" value="${0}"/>
                        <c:forEach items="${deals}" var="deal">
                            <c:set var="sum" value="${sum + deal.budget}"/>
                        </c:forEach>
                        <p>Бюджет: $${sum}</p>
                    </div>
                </div>

                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <h3 class="panel-title">Успешные сделки</h3>
                    </div>
                    <div class="panel-body">
                        <p>Успешных сделок: ${successDealsCount}</p>
                        <p>Нереализовано: ${failedDealsCount}</p>
                    </div>
                </div>

                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <h3 class="panel-title">Сделки без задач</h3>
                    </div>
                    <div class="panel-body">
                        <p>Сделок без задач: 0</p>
                        <p>Сделок с задачами: 0</p>
                    </div>
                </div>

            </div>
            <div class="col-md-4">
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <h3 class="panel-title">Задачи в работе и Выполненные</h3>
                    </div>
                    <div class="panel-body">
                        <p>Задачи в работе: 0</p>
                        <p>Выполненные: 0</p>
                        <p>Просроченные: 0</p>
                    </div>
                </div>
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <h3 class="panel-title">Всего контактов и Всего компаний</h3>
                    </div>
                    <div class="panel-body">
                        <p>Контактов: ${contactCount}</p>
                        <p>Компаний: ${companyCount}</p>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <h3 class="panel-title">Последние события</h3>
                    </div>
                    <div class="panel-body">

                    </div>
                </div>
            </div>
        </div>
    </jsp:attribute>
</t:pageLayout>