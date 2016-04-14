<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<t:pageLayout title="Задачи">
    <jsp:attribute name="bodyLayout">
        <div class="task-view-links">
            <a href="?view=list" class="btn btn-primary active" role="button">Список</a>
            <a href="?view=todo" class="btn btn-primary disabled" role="button">To-do line</a>
            <a href="?view=day" class="btn btn-primary active" role="button">День</a>
            <a href="?view=week" class="btn btn-primary active" role="button">Неделя</a>
            <a href="?view=month" class="btn btn-primary active" role="button">Месяц</a>
        </div>
        <div class="container task-todo-container task-holder">
        <div class="row">
            <div class="col-sm-4">
                <div class="task-card overdue-task text-center">Просроченные задачи</div>
            </div>
            <div class="col-sm-4">
                <div class="task-card today-task text-center">Задачи на сегодня
                </div>
            </div>
            <div class="col-sm-4">
                <div class="task-card tomorrow-task text-center">
                Задачи на завтра
                </div>
            </div>

        </div>
    <div class="row">
        <div class="col-sm-4">

            <c:forEach items="${overdueTasks}" var="task">
                <div class="task-card overdue-task">
                    <ul>
                        <li class="finish-date">
                            Дата: <fmt:formatDate type="date"
                                                  value="${task.getFinishDate()}"/>
                        </li>
                        <li class="finish-time">
                            Время: <fmt:formatDate type="time" timeStyle="short"
                                                   value="${task.getFinishDate()}"/>
                        </li>
                        <li class="responsible-user">
                            Ответсвенный: ${task.getResponsibleUser().getName()}
                        </li>
                        <li class="task-type">
                            Тип: ${task.getTaskType().getTypeName()}
                        </li>
                        <li class="description">
                            Текс: ${task.getDescription()}
                        </li>
                        <li>
                            <c:if test="${not empty task.getContact()}">
                                Контакт: <br/>${task.getContact().getName()}
                            </c:if>

                            <c:if test="${not empty task.getDeal()}">
                                Сделка <br/>${task.getDeal().getName()}
                            </c:if>

                            <c:if test="${not empty task.getCompany()}">
                                Компания <br/>${task.getCompany().getName()}
                            </c:if>
                        </li>
                    </ul>
                    <button type="button" class="btn btn-default">
                        На завтра
                    </button>
                </div>
            </c:forEach>

        </div>
        <div class="col-sm-4">

            <c:forEach items="${todayTasks}" var="task">
                <div class="task-card today-task">
                    <ul>
                        <li class="finish-date">
                            Дата: <fmt:formatDate type="date"
                                                  value="${task.getFinishDate()}"/>
                        </li>
                        <li class="finish-time">
                            Время: <fmt:formatDate type="time" timeStyle="short"
                                                   value="${task.getFinishDate()}"/>
                        </li>
                        <li class="responsible-user">
                            Ответсвенный: ${task.getResponsibleUser().getName()}
                        </li>
                        <li class="task-type">
                            Тип: ${task.getTaskType().getTypeName()}
                        </li>
                        <li class="description">
                            Текс: ${task.getDescription()}
                        </li>
                        <li>
                            <c:if test="${not empty task.getContact()}">
                                Контакт: <br/>${task.getContact().getName()}
                            </c:if>

                            <c:if test="${not empty task.getDeal()}">
                                Сделка <br/>${task.getDeal().getName()}
                            </c:if>

                            <c:if test="${not empty task.getCompany()}">
                                Компания <br/>${task.getCompany().getName()}
                            </c:if>
                        </li>
                    </ul>
                    <button type="button" class="btn btn-default">
                        На завтра
                    </button>
                </div>
            </c:forEach>

        </div>
        <div class="col-sm-4">

            <c:forEach items="${tomorrowTasks}" var="task">
                <div class="task-card tomorrow-task">
                    <ul>
                        <li class="finish-date">
                            Дата: <fmt:formatDate type="date"
                                                  value="${task.getFinishDate()}"/>
                        </li>
                        <li class="finish-time">
                            Время: <fmt:formatDate type="time" timeStyle="short"
                                                   value="${task.getFinishDate()}"/>
                        </li>
                        <li class="responsible-user">
                            Ответсвенный: ${task.getResponsibleUser().getName()}
                        </li>
                        <li class="task-type">
                            Тип: ${task.getTaskType().getTypeName()}
                        </li>
                        <li class="description">
                            Текс: ${task.getDescription()}
                        </li>
                        <li>
                            <c:if test="${not empty task.getContact()}">
                                Контакт: <br/>${task.getContact().getName()}
                            </c:if>

                            <c:if test="${not empty task.getDeal()}">
                                Сделка <br/>${task.getDeal().getName()}
                            </c:if>

                            <c:if test="${not empty task.getCompany()}">
                                Компания <br/>${task.getCompany().getName()}
                            </c:if>
                        </li>
                    </ul>
                    <button type="button" class="btn btn-default">
                        На завтра
                    </button>
                </div>
            </c:forEach>
        </div>


    </div>
    </div>
    </jsp:attribute>
</t:pageLayout>