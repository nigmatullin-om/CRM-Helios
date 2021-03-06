<%@include file="/resources/jsp/locale.jsp"%>

<fmt:message key= "pageTasks" var="pageTasks"/>


<t:pageLayout title="${pageTasks}">
    <jsp:attribute name="bodyLayout">
        <div class="task-view-links">
            <a href="?view=list" class="btn btn-primary active" role="button"><fmt:message key="lblList"/> </a>
            <a href="?view=todo" class="btn btn-primary active" role="button"><fmt:message key="lblToDoLine"/></a>
            <a href="?view=day" class="btn btn-primary disabled" role="button"><fmt:message key="lblDay"/></a>
            <a href="?view=week" class="btn btn-primary active" role="button"><fmt:message key="lblWeek"/></a>
            <a href="?view=month" class="btn btn-primary active" role="button"><fmt:message key="lblMonth"/></a>
        </div>
        <div class="container task-container">
            <table class="calendar table table-bordered table-hover">
                <caption>
                    <a href="?view=day&date=${prevDay}" class="btn btn-info btn-xs active" role="button"><<</a>

                    <fmt:parseDate value="${viewDate}" pattern="yyyy-MM-dd" var="parsedDate"
                                   type="date"/>
                    <fmt:formatDate value="${parsedDate}" type="date"/>
                    <a href="?view=day&date=${nextDay}" class="btn btn-info btn-xs active" role="button">>></a>
                </caption>
                <thead>
                <tr>
                    <th><fmt:message key="lblTime"/></th>
                    <th>
                            ${dayName}</th>
                </tr>
                </thead>
                <tbody>

                <c:forEach var="timeAndTask" items="${tasksForDay}">
                    <tr>
                        <td class="time-in-table">${timeAndTask.key} </td>


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
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </jsp:attribute>
</t:pageLayout>