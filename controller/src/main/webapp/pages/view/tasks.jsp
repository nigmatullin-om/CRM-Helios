<%@include file="/resources/jsp/locale.jsp"%>

<fmt:message key= "pageTasks" var="pageTasks"/>

<t:pageLayout title="${pageTasks}">
    <jsp:attribute name="bodyLayout">
        <p class="task-view-links">
            <a href="#" class="btn btn-primary disabled" role="button"><fmt:message key="lblToDoLine"/> </a>
            <a href="#" class="btn btn-primary disabled" role="button"><fmt:message key="lblDay"/> </a>
            <a href="#" class="btn btn-primary disabled" role="button"><fmt:message key="lblWeek"/> </a>
            <a href="#" class="btn btn-primary disabled" role="button"><fmt:message key="lblMonth"/></a>
            <a href="#" class="btn btn-primary active" role="button"><fmt:message key="lblList"/> </a>
        </p>
<table class="table table-striped">
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

            <c:if test="${not empty task.getContact()}">
                <td class="contact-company-deal"><fmt:message key="contactName"/> <br/>${task.getContact().getName()}</td>
            </c:if>

            <c:if test="${not empty task.getDeal()}">
                <td class="contact-company-deal"><fmt:message key="dealName"/> <br/>${task.getDeal().getName()}</td>
            </c:if>

            <c:if test="${not empty task.getCompany()}">
                <td class="contact-company-deal"><fmt:message key="companyName"/> <br/>${task.getCompany().getName()}</td>
            </c:if>

            <td class="task-type">${task.getTaskType().getTypeName()}</td>
            <td class="description">${task.getDescription()}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>

    </jsp:attribute>
</t:pageLayout>