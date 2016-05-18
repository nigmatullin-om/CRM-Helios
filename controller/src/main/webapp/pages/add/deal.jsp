<%@include file="/resources/jsp/locale.jsp"%>

<fmt:message key= "pageDealAdd" var="pageDealAdd"/>

<t:pageLayout title="${pageDealAdd}">
   <jsp:attribute name="bodyLayout">
            <div class="container">
                <h1 style="text-align:  center"><fmt:message key="dealAdd"/></h1>
                <form class="form-horizontal" id="addDealForm" role="form" action="${pageContext.request.contextPath}/add/deal" method="post">
                    <div class="row">
                        <div class="col-sm-6">

                            <fieldset class="form-group">
                                <label for="dealName" class="control-label col-sm-4"><fmt:message key="dealName"/></label>
                                <div class="col-sm-8">
                                    <input type="text" class="form-control" name="dealName" id="dealName">
                                </div>
                            </fieldset>

                            <div class="form-group">
                                <label class="control-label col-sm-4"><fmt:message key="lblTags"/></label>
                                <div class="col-sm-8">
                                    <input type="text" class="form-control" name="dealTags" id="dealTags">
                                </div>
                            </div>

                            <div class="form-group" id="dealResponsibleBlock">
                                <label class="control-label col-sm-4"><fmt:message key="lblResponsible"/></label>
                                <div class="col-sm-8">
                                    <select class="form-control" name="dealResponsible" id="dealResponsible">
                                        <option value="-1" selected disabled><fmt:message key="pleaseSelect"/></option>
                                        <c:forEach items="${usersList}" var="user">
                                            <option value=${user.id}>${user.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>

                            <div class="form-group" id="dealBudgetBlock">
                                <label class="control-label col-sm-4"><fmt:message key="lblBudget"/></label>
                                <div class="col-sm-7">
                                    <input type="number" step="0.01" class="form-control" name="dealBudget" id="dealBudget" >
                                </div>
                                <label class="control-label col-sm-1">$</label>
                            </div>

                            <div class="form-group" id="dealStageBlock">
                                <label class="control-label col-sm-4"><fmt:message key="dealStage"/></label>
                                <div class="col-sm-8">
                                    <select class="form-control" name="dealStage" id="dealStage">
                                        <option value="-1" selected disabled><fmt:message key="pleaseSelect"/></option>
                                        <c:forEach items="${dealStages}" var="stage">
                                            <option value=${stage}><fmt:message key= "${stage}"/></option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>

                            <div class="form-group" id="dealNoteBlock">
                                <label class="control-label col-sm-4"><fmt:message key="lblNote"/></label>
                                <div class="col-sm-8">
                                    <textarea class="form-control" rows="5" name="dealNote" id="dealNote"></textarea>
                                </div>
                            </div>

                            <div class="form-group" id="dealDateBlock">
                                <label class="control-label col-sm-4"><fmt:message key="lblCreationDate"/></label>
                                <div class="col-sm-8">
                                    <input type="text" class="form-control" id="dealDate" name="dealDate">
                                </div>
                            </div>

                            <div class="form-group">
                                <div class="col-sm-12">
                                    <label class="control-label col-sm-4"><fmt:message key="lblFileAdd"/></label>
                                    <div class="col-sm-8">
                                        <input type="file" class="file" data-show-preview="false" name="fileName" id="fileName">
                                    </div>
                                </div>
                            </div>

                            <div class="form-group">
                                <div class="col-sm-8">
                                    <select class="form-control"  name="contact" id="contact">
                                        <option value="-1" selected disabled><fmt:message key="pleaseSelect"/></option>
                                        <c:forEach items="${contacts}" var="contact">
                                            <option value="${contact.id}">${contact.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <button type="button" onclick="return addDealButton()" class="btn control-button col-sm-4"><fmt:message key="lblSubmit"/></button>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-sm-8"><fmt:message key="contactAdd"/></label>
                            </div>

                            <div class="form-group" id="contactNameBlock">
                                <label class="control-label col-sm-4"><fmt:message key="contactName"/></label>
                                <div class="col-sm-8">
                                    <input type="text" class="form-control" name="contactName" id="contactName">
                                </div>
                            </div>

                            <div class="form-group" id="contactCompanyBlock">
                                <label class="control-label col-sm-4"><fmt:message key="companyName"/></label>
                                <div class="col-sm-8">
                                    <input type="text" class="form-control" name="contactCompany" id="contactCompany">
                                </div>
                            </div>

                            <div class="form-group" id="contactPositionBlock">
                                <label class="control-label col-sm-4"><fmt:message key="lblResponsible"/></label>
                                <div class="col-sm-8">
                                    <input type="text" class="form-control" name="contactPosition" id="contactPosition">
                                </div>
                            </div>

                            <div class="form-group" id="contactPhoneTypeBlock">
                                <div class="col-sm-4">
                                    <select class="form-control"  name="contactPhoneType" id="contactPhoneType">
                                        <option value="-1" selected disabled><fmt:message key="pleaseSelect"/></option>
                                        <c:forEach items="${phoneTypes}" var="phoneType">
                                            <option value=${phoneType}><fmt:message key = "${phoneType}"/></option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="col-sm-8">
                                    <input type="text" class="form-control" name="contactPhone" id="contactPhone">
                                </div>
                            </div>

                            <div class="form-group" id="contactEmailBlock">
                                <label class="control-label col-sm-4"><fmt:message key="lblEmail"/></label>
                                <div class="col-sm-8">
                                    <input type="text" class="form-control" name="contactEmail" id="contactEmail">
                                </div>
                            </div>

                            <div class="form-group" id="contactSkypeBlock">
                                <label class="control-label col-sm-4"><fmt:message key="lblSkype"/></label>
                                <div class="col-sm-8">
                                    <input type="text" class="form-control" name="contactSkype" id="contactSkype">
                                </div>
                            </div>
                        </div>

                        <div class="col-sm-6">
                            <div class="form-group" id="companyBlock">
                                <label class="control-label col-sm-4"><fmt:message key="companyName"/></label>
                                <div class="col-sm-4">
                                    <select class="form-control"  name="company" id="company">
                                        <option value="-1" selected disabled><fmt:message key="pleaseSelect"/></option>
                                        <c:forEach items="${companies}" var="companyItem">
                                            <option value="${companyItem.id}">${companyItem.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <button type="button" class="btn control-button col-sm-4" onclick="return addCompanyButtonFunction()" name="addCompany" id="addCompany"><fmt:message key="lblSubmit"/></button>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-sm-8"><fmt:message key="pageCompanyAdd"/></label>
                            </div>

                            <div class="form-group" id="companyNameBlock">
                                <label class="control-label col-sm-4"><fmt:message key="companyName"/></label>
                                <div class="col-sm-8">
                                    <input type="text" class="form-control" name="companyName" id="companyName">
                                </div>
                            </div>

                            <div class="form-group" id="companyPhoneBlock">
                                <label class="control-label col-sm-4"><fmt:message key="lblPhone"/></label>
                                <div class="col-sm-8">
                                    <input type="text" class="form-control" name="companyPhone" id="companyPhone">
                                </div>
                            </div>

                            <div class="form-group" id="companyEmailBlock">
                                <label class="control-label col-sm-4"><fmt:message key="lblEmail"/></label>
                                <div class="col-sm-8">
                                    <input type="text" class="form-control" name="companyEmail" id="companyEmail">
                                </div>
                            </div>

                            <div class="form-group" id="companyWebBlock">
                                <label class="control-label col-sm-4"><fmt:message key="lblWeb"/></label>
                                <div class="col-sm-8">
                                    <input type="text" class="form-control" name="companyWeb" id="companyWeb">
                                </div>
                            </div>

                            <div class="form-group" id="companyAdressBlock">
                                <label class="control-label col-sm-4"><fmt:message key="lblAddress"/></label>
                                <div class="col-sm-8">
                                    <input type="text" class="form-control" name="companyAdress" id="companyAdress">
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-sm-8"><fmt:message key="taskAdd"/></label>
                            </div>

                            <div class="form-group" id="taskPeriodBlock">
                                <label class="control-label col-sm-4"><fmt:message key="lblPeriod"/></label>
                                <div class="col-sm-8">
                                    <select class="form-control"  name="taskPeriod" id="taskPeriod">
                                        <option value="-1" selected disabled><fmt:message key="pleaseSelect"/></option>
                                        <c:forEach items="${taskPeriods}" var="period">
                                            <option value=${period}><fmt:message key= "${period}"/></option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-sm-8"><fmt:message key="lblDateAndTime"/></label>
                            </div>
                            <div class="form-group" id="taskDateTimeBlock">
                                <div class="col-sm-4">
                                    <input type="text" class="form-control" id="taskDate" name="taskDate">
                                </div>
                                <div class="input-group clockpicker">
                                    <input type="text" class="form-control" value="" id="taskTime" name="taskTime">
                                    <span class="input-group-addon">
                                        <span class="glyphicon glyphicon-time"></span>
                                    </span>
                                </div>
                            </div>

                            <div class="form-group" id="taskNameBlock">
                                <label class="control-label col-sm-4"><fmt:message key="taskName"/></label>
                                <div class="col-sm-8">
                                    <input type="text" class="form-control" name="taskName" id="taskName">
                                </div>
                            </div>

                            <div class="form-group" id="taskResponsibleBlock">
                                <label class="control-label col-sm-4"><fmt:message key="lblResponsible"/></label>
                                <div class="col-sm-8">
                                    <select class="form-control" name="taskResponsible" id="taskResponsible">
                                        <option value="-1" selected disabled><fmt:message key="pleaseSelect"/></option>
                                        <c:forEach items="${usersList}" var="user">
                                            <option value=${user.id}>${user.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>

                            <div class="form-group" id="taskTypeBlock">
                                <label class="control-label col-sm-4"><fmt:message key="taskType"/></label>
                                <div class="col-sm-8">
                                    <select class="form-control" name="taskType" id="taskType">
                                        <option value="-1" selected disabled><fmt:message key="pleaseSelect"/></option>
                                        <c:forEach items="${taskTypes}" var="taskType">
                                            <option value="${taskType.id}">${taskType.typeName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>

                            <div class="form-group" id="taskTextBlock">
                                <label class="control-label col-sm-4"><fmt:message key="taskDescription"/></label>
                                <div class="col-sm-8">
                                    <textarea class="form-control" rows="5" name="taskText" id="taskText"></textarea>
                                </div>
                            </div>

                        </div>
                    </div>

                    <div class="row" style="text-align:center;">
                        <div class="col-md-12 col-sm-12">
                            <button type="submit" class="btn btn-success btn-large" onclick="return checkDealForm()">
                                <fmt:message key= "lblSubmit"/>
                            </button>
                            <button type="reset" class="btn btn-danger btn-large" style="margin-left: 40px">
                                <fmt:message key= "lblReset"/>
                            </button>
                        </div>
                    </div>

                </form>
            </div>
    </jsp:attribute>
</t:pageLayout>
</html>