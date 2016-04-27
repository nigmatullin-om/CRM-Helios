<%@include file="/resources/jsp/locale.jsp"%>

<fmt:message key= "pageTasks" var="pageTasks"/>


<t:pageLayout title="${pageTasks}">
    <jsp:attribute name="bodyLayout">
        <div class="task-view-links">
            <a href="?view=list" class="btn btn-primary disabled" role="button"><fmt:message key="lblList"/> </a>
            <a href="?view=todo" class="btn btn-primary active" role="button"><fmt:message key="lblToDoLine"/></a>
            <a href="?view=day" class="btn btn-primary active" role="button"><fmt:message key="lblDay"/></a>
            <a href="?view=week" class="btn btn-primary active" role="button"><fmt:message key="lblWeek"/></a>
            <a href="?view=month" class="btn btn-primary active" role="button"><fmt:message key="lblMonth"/></a>
        </div>
<table class="table table-bordered table-hover">
    <thead>
    <tr>
        <th><fmt:message key="lblExecutionDate"/> </th>
        <th><fmt:message key="lblTime"/> </th>
        <th><fmt:message key="lblResponsible"/> </th>
        <th><fmt:message key="lblContactOrDeal"/></th>
        <th><fmt:message key="taskType"/> </th>
        <th><fmt:message key="taskDescription"/> </th>

    </tr>
    </thead>
    <tbody>
    <c:forEach items="${tasks}" var="task">
        <tr>
            <td class="finish-date"><fmt:formatDate type="date"
                                                    value="${task.getFinishDate()}"/></td>
            <td class="finish-time"><fmt:formatDate type="time" timeStyle="short"
                                                    value="${task.getFinishDate()}"/></td>
            <td class="responsible-user">${task.getResponsibleUser().getName()}</td>

            <c:choose>
                <c:when test="${not empty task.getContact()}">
                    <td class="contact-company-deal"><fmt:message key="contactName"/> <br/>${task.getContact().getName()}</td>
                </c:when>

                <c:when test="${not empty task.getDeal()}">
                    <td class="contact-company-deal"><fmt:message key="dealName"/>  <br/>${task.getDeal().getName()}</td>
                </c:when>

                <c:when test="${not empty task.getCompany()}">
                    <td class="contact-company-deal">
                        <fmt:message key="companyName"/>  <br/>${task.getCompany().getName()}
                    </td>
                </c:when>
                <c:otherwise>
                    <td class="contact-company-deal"></td>
                </c:otherwise>
            </c:choose>

            <td class="task-type">${task.getTaskType().getTypeName()}</td>
            <td class="description">${task.getDescription()}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>

    </jsp:attribute>
</t:pageLayout>