<%@include file="/resources/jsp/locale.jsp"%>

<fmt:message key= "pageTasks" var="pageTasks"/>


<t:pageLayout title="${pageTasks}">
    <jsp:attribute name="bodyLayout">
        <div class="task-view-links">
            <a href="?view=list" class="btn btn-primary active" role="button"><fmt:message key="lblList"/> </a>
            <a href="?view=todo" class="btn btn-primary active" role="button"><fmt:message key="lblToDoLine"/></a>
            <a href="?view=day" class="btn btn-primary active" role="button"><fmt:message key="lblDay"/></a>
            <a href="?view=week" class="btn btn-primary disabled" role="button"><fmt:message key="lblWeek"/></a>
            <a href="?view=month" class="btn btn-primary active" role="button"><fmt:message key="lblMonth"/></a>
        </div>
        <div class="container">
            <table class="calendar table table-bordered table-hover">
                <caption>
                    <a href="?view=week&date=${prevWeek}" class="btn btn-info btn-xs active" role="button"><<</a>

                    <fmt:parseDate value="${viewDate}" pattern="yyyy-MM-dd" var="parsedDate"
                                   type="date"/>
                    <fmt:formatDate value="${parsedDate}" type="date"/>
                    <a href="?view=week&date=${nextWeek}" class="btn btn-info btn-xs active" role="button">>></a>
                </caption>
                <thead>
                <tr>
                    <th><fmt:message key="lblTime"/> </th>
                    <c:forEach var="dayOfWeek" items="${dayNames}">
                        <th>${dayOfWeek}</th>
                    </c:forEach>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="tasksForDay" items="${tasksForWeek}">
                    <tr>
                        <td class="time-in-table">
                                ${tasksForDay.key}
                        </td>
                        <c:forEach var="timeAndTask" items="${tasksForDay.value}">

                            <c:if test="${empty timeAndTask.value}">
                                <td class=" no-events" rowspan="1"></c:if>

                            <c:if test="${not empty timeAndTask.value}">
                                <td class=" has-events" rowspan="1">
                                <c:forEach items="${timeAndTask.value}" var="task" varStatus="loop">
                                    <div class="event">
                                            ${task.getTaskType().getTypeName()},
                                            ${task.getResponsibleUser().getName()},
                                            ${task.getDescription()}
                                    </div>
                                </c:forEach>
                            </c:if>
                            </td>
                        </c:forEach>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </jsp:attribute>
</t:pageLayout>