<%@include file="/resources/jsp/locale.jsp"%>

<fmt:message key= "pageTasks" var="pageTasks"/>


<t:pageLayout title="${pageTasks}">
    <jsp:attribute name="bodyLayout">
        <div class="task-view-links">
            <a href="?view=list" class="btn btn-primary active" role="button"><fmt:message key="lblList"/> </a>
            <a href="?view=todo" class="btn btn-primary disabled" role="button"><fmt:message key="lblToDoLine"/></a>
            <a href="?view=day" class="btn btn-primary active" role="button"><fmt:message key="lblDay"/></a>
            <a href="?view=week" class="btn btn-primary active" role="button"><fmt:message key="lblWeek"/></a>
            <a href="?view=month" class="btn btn-primary active" role="button"><fmt:message key="lblMonth"/></a>
        </div>
        <div class="container task-container">
            <table class="calendar table table-bordered task-todo-container">
                <caption>
                    <fmt:parseDate value="${viewDate}" pattern="yyyy-MM-dd" var="parsedDate"
                                   type="date"/>
                    <fmt:formatDate value="${parsedDate}" type="date" dateStyle="LONG"/>
                </caption>

                <thead>
                <tr>
                    <th><fmt:message key="tasksMatured"/></th>
                    <th><fmt:message key="tasksForToday"/></th>
                    <th><fmt:message key="tasksForTomorrow"/></th>

                </tr>
                </thead>
                <tbody>
                <tr>

                    <c:forEach items="${todoTasks}" var="toDotask">
                        <td class="task-card">
                            <c:forEach items="${toDotask.value}" var="task">
                                <div class="${toDotask.key}-task task-card">
                                    <ul>
                                        <li class="finish-date">
                                            <fmt:message key="lblDay"/>: <fmt:formatDate type="date"
                                                                  value="${task.getFinishDate()}"/>
                                        </li>
                                        <li class="finish-time">
                                            <fmt:message key="lblTime"/>: <fmt:formatDate type="time" timeStyle="short"
                                                                   value="${task.getFinishDate()}"/>
                                        </li>
                                        <li class="responsible-user">
                                            <fmt:message key="lblResponsible"/>: ${task.getResponsibleUser().getName()}
                                        </li>
                                        <li class="task-type">
                                            <fmt:message key="taskType"/>: ${task.getTaskType().getTypeName()}
                                        </li>
                                        <li class="description">
                                            <fmt:message key="lblDescription"/>: ${task.getDescription()}
                                        </li>
                                        <li>
                                            <c:if test="${not empty task.getContact()}">
                                                <fmt:message key="contactName"/>: <br/>${task.getContact().getName()}
                                            </c:if>

                                            <c:if test="${not empty task.getDeal()}">
                                                <fmt:message key="dealName"/>: <br/>${task.getDeal().getName()}
                                            </c:if>

                                            <c:if test="${not empty task.getCompany()}">
                                                <fmt:message key="companyName"/>: <br/>${task.getCompany().getName()}
                                            </c:if>
                                        </li>
                                    </ul>
                                    <form action="/tasks" method="post">
                                        <div class="btn-group">
                                            <input hidden="true" name="taskId" value="${task.getId()}">
                                            <button type="submit" class="btn btn-default" name="changeDate"
                                                    value="onToday">
                                                <fmt:message key="tasksForToday"/>
                                            </button>
                                            <button type="submit" class="btn btn-default" name="changeDate"
                                                    value="onTomorrow">
                                                <fmt:message key="tasksForTomorrow"/>
                                            </button>
                                        </div>
                                    </form>

                                </div>
                            </c:forEach>

                        </td>
                    </c:forEach>

                </tr>
                </tbody>
            </table>
        </div>


    </jsp:attribute>
</t:pageLayout>