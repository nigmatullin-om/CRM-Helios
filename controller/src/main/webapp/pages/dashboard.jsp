<%@include file="/resources/jsp/locale.jsp"%>

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
                        <p><fmt:message key="dealsAll"/>: <a href="${pageContext.request.contextPath}/view/deals">${fn:length(deals)}</a></p>
                        <c:set var="sum" value="${0}"/>
                        <c:forEach items="${deals}" var="deal">
                            <c:set var="sum" value="${sum + deal.budget}"/>
                        </c:forEach>
                        <p><fmt:message key="lblBudget"/>: <a href="${pageContext.request.contextPath}/view/deals">${sum}</a></p>
                    </div>
                </div>

                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <h3 class="panel-title"><fmt:message key="dealsSuccess"/> </h3>
                    </div>
                    <div class="panel-body">
                        <p><fmt:message key="dealsSuccess"/>: <a href="${pageContext.request.contextPath}/view/deals">${successDealsCount}</a></p>
                        <p><fmt:message key="dealsFail"/>: <a href="${pageContext.request.contextPath}/view/deals">${failedDealsCount}</a></p>
                    </div>
                </div>

                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <h3 class="panel-title"><fmt:message key="dealsWithoutTasks"/> </h3>
                    </div>
                    <div class="panel-body">
                        <p><fmt:message key="dealsWithoutTasks"/>: <a href="${pageContext.request.contextPath}/view/deals">${dealsWithoutTasksCount}</a></p>
                        <p><fmt:message key="dealsWithTasks"/>: <a href="${pageContext.request.contextPath}/view/deals" >${dealsWithTasksCount}</a></p>
                    </div>
                </div>

            </div>
            <div class="col-md-4">
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <h3 class="panel-title"><fmt:message key="tasksInWorkAndDone"/> </h3>
                    </div>
                    <div class="panel-body">
                        <p><fmt:message key="tasksInWork"/>: <a href="${pageContext.request.contextPath}/view/tasks">${runningTasksCount}</a></p>
                        <p><fmt:message key="tasksDone"/>: <a href="${pageContext.request.contextPath}/view/tasks">${doneTasksCount}</a></p>
                        <p><fmt:message key="tasksMatured"/>: <a href="${pageContext.request.contextPath}/view/tasks">${notDoneTasksCount}</a></p>
                    </div>
                </div>
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <h3 class="panel-title"><fmt:message key="allContactsAllCompanies"/> </h3>
                    </div>
                    <div class="panel-body">
                        <p><fmt:message key="contactsAll"/>: <a href="#">${contactsCount}</a></p>
                        <p><fmt:message key="companiesAll"/>: <a href="${pageContext.request.contextPath}/view/CompaniesList">${companiesCount}</a></p>
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