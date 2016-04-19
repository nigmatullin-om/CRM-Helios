<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<c:set var ="userLocale" value="${cookie['localeCookie'].value}"/>
<c:if test="${userLocale == ''}">
    <c:set var ="userLocale" value="en_US"/>
    ${cookie['localeCookie'].value} = "en_US"
    ${cookie['localeCookie'].path} = "/"
</c:if>
<fmt:setLocale value="${userLocale}"/>
<fmt:setBundle basename="LocaleText"/>
<fmt:message key= "pageDashboard" var="pageDashboard"/>

<t:pageLayout title="${pageDashboard}">
    <jsp:attribute name="bodyLayout">
        <!-- Example row of columns -->
        <div class="row">
            <div class="col-md-4">
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <h3 class="panel-title"><fmt:message key= "deals"/></h3>
                    </div>
                    <div class="panel-body">
                        <p><fmt:message key="dealsAll"/>: <a href="#" onclick="testAlert(); return false">${fn:length(deals)}</a></p>
                        <c:set var="sum" value="${0}"/>
                        <c:forEach items="${deals}" var="deal">
                            <c:set var="sum" value="${sum + deal.budget}"/>
                        </c:forEach>
                        <p><fmt:message key="lblBudget"/>: <a href="#" onclick="testAlert(); return false">${sum}</a></p>
                    </div>
                </div>

                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <h3 class="panel-title"><fmt:message key="dealsSuccess"/> </h3>
                    </div>
                    <div class="panel-body">
                        <p><fmt:message key="dealsSuccess"/>: <a href="#" onclick="testAlert(); return false">${successDealsCount}</a></p>
                        <p><fmt:message key="dealsFail"/>: <a href="#" onclick="testAlert(); return false">${failedDealsCount}</a></p>
                    </div>
                </div>

                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <h3 class="panel-title"><fmt:message key="dealsWithoutTasks"/> </h3>
                    </div>
                    <div class="panel-body">
                        <p><fmt:message key="dealsWithoutTasks"/>: <a href="#" onclick="testAlert(); return false">${dealsWithoutTasksCount}</a></p>
                        <p><fmt:message key="dealsWithTasks"/>: <a href="#" onclick="testAlert(); return false">${dealsWithTasksCount}</a></p>
                    </div>
                </div>

            </div>
            <div class="col-md-4">
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <h3 class="panel-title"><fmt:message key="tasksInWorkAndDone"/> </h3>
                    </div>
                    <div class="panel-body">
                        <p><fmt:message key="tasksInWork"/>: <a href="#" onclick="testAlert(); return false">${runningTasksCount}</a></p>
                        <p><fmt:message key="tasksDone"/>: <a href="#" onclick="testAlert(); return false">${doneTasksCount}</a></p>
                        <p><fmt:message key="tasksMatured"/>: <a href="#" onclick="testAlert(); return false">${notDoneTasksCount}</a></p>
                    </div>
                </div>
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <h3 class="panel-title"><fmt:message key="allContactsAllCompanies"/> </h3>
                    </div>
                    <div class="panel-body">
                        <p><fmt:message key="contactsAll"/>: <a href="#" onclick="testAlert(); return false">${contactsCount}</a></p>
                        <p><fmt:message key="companiesAll"/>: <a href="#" onclick="testAlert(); return false">${companiesCount}</a></p>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <h3 class="panel-title"><fmt:message key="lastEvents"/> </h3>
                    </div>
                    <div class="panel-body">

                    </div>
                </div>
            </div>
        </div>
    </jsp:attribute>
</t:pageLayout>