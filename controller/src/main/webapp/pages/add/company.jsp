<%@include file="/resources/jsp/locale.jsp"%>

<fmt:message key= "pageCompanyAdd" var="pageCompanyAdd"/>

<t:pageLayout title="${pageCompanyAdd}">
    <jsp:attribute name="bodyLayout">

<div class="container">
    <h1 class="page-header text-center">${pageCompanyAdd}</h1>
    <form class="form-horizontal" action="/add/company" method="post">
        <div class="row">

            <div class="col-md-3 col-sm-3">
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <h2 class="panel-title"><fmt:message key= "companyPanelTitle"/></h2>
                    </div>
                    <div class="row" style="margin-top: 30px">
                        <fieldset class="form-group">
                            <label for="companyName"><fmt:message key= "companyName"/></label>
                            <input type="text" class="form-control" id="companyName" name="companyName">
                        </fieldset>
                        <fieldset class="form-group">
                            <label for="companyTags"><fmt:message key= "lblTags"/></label>
                            <input type="text" class="form-control" id="companyTags" name="companyTags">
                        </fieldset>
                        <fieldset class="form-group">
                            <label for="companyResponsible"><fmt:message key= "lblResponsible"/></label>
                            <select class="form-control" id="companyResponsible" name="companyResponsible">
                                <option value="-1" selected disabled><fmt:message key= "pleaseSelect"/></option>
                                <c:forEach items="${usersList}" var="user">
                                    <option value=${user.id}>${user.name}</option>
                                </c:forEach>
                            </select>
                        </fieldset>
                        <fieldset class="form-group">
                            <label for="companyPhone"><fmt:message key= "lblPhone"/></label>
                            <select class="form-control" id="companyPhoneType" name="companyPhoneType">
                                <option value="-1" selected disabled><fmt:message key= "pleaseSelect"/></option>
                                <c:forEach items="${phoneTypes}" var="phoneType">
                                    <option value=${phoneType}><fmt:message key = "${phoneType}"/></option>
                                </c:forEach>
                            </select>
                            <input type="text" class="form-control" id="companyPhone" name="companyPhone"
                                   style="margin-top: 10px">
                        </fieldset>

                        <fieldset class="form-group">
                            <label for="companyEmail"><fmt:message key= "lblEmail"/></label>
                            <input type="email" class="form-control" id="companyEmail" name="companyEmail">
                        </fieldset>
                        <fieldset class="form-group">
                            <label for="companyWeb"><fmt:message key= "lblWeb"/></label>
                            <input type="text" class="form-control" id="companyWeb" name="companyWeb">
                        </fieldset>
                        <fieldset class="form-group">
                            <label for="companyAddress"><fmt:message key= "lblAddress"/></label>
                            <input type="text" class="form-control" id="companyAddress" name="companyAddress">
                        </fieldset>
                        <fieldset class="form-group">
                            <label for="companyNote"><fmt:message key= "lblNote"/></label>
                            <textarea class="form-control" id="companyNote" name="companyNote" rows="3"></textarea>
                        </fieldset>
                    </div>
                </div>
            </div>

            <div class="col-md-3 col-sm-3">
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <h4 class="panel-title"><fmt:message key= "contactAdd"/></h4>
                    </div>
                    <div class="row" style="margin-top: 30px">
                        <fieldset class="form-group">
                            <label for="contactName"><fmt:message key= "contactName"/></label>
                            <input type="text" class="form-control" id="contactName" name="contactName">
                        </fieldset>
                        <fieldset class="form-group">
                            <label for="contactPosition"><fmt:message key= "lblPosition"/></label>
                            <input type="text" class="form-control" id="contactPosition" name="contactPosition">
                        </fieldset>
                        <fieldset class="form-group">
                            <label for="contactPhone"><fmt:message key= "lblPhone"/></label>
                            <select class="form-control" id="contactPhoneType" name="contactPhoneType">
                                <option value="-1" selected disabled><fmt:message key= "pleaseSelect"/></option>
                                <c:forEach items="${phoneTypes}" var="phoneType">
                                    <option value=${phoneType}><fmt:message key= "${phoneType}"/></option>
                                </c:forEach>
                            </select>
                            <input type="text" class="form-control" id="contactPhone" name="contactPhone"
                                   style="margin-top: 10px">
                        </fieldset>
                        <fieldset class="form-group">
                            <label for="contactEmail"><fmt:message key= "lblEmail"/></label>
                            <input type="email" class="form-control" id="contactEmail" name="contactEmail">
                        </fieldset>
                        <fieldset class="form-group">
                            <label for="contactSkype"><fmt:message key= "lblSkype"/></label>
                            <input type="text" class="form-control" id="contactSkype" name="contactSkype">
                        </fieldset>
                    </div>
                </div>
            </div>

            <div class="col-md-3 col-sm-3">
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <h4 class="panel-title"><fmt:message key= "dealAdd"/></h4>
                    </div>
                    <div class="row" style="margin-top: 30px">
                        <fieldset class="form-group">
                            <label for="dealName"><fmt:message key= "dealName"/></label>
                            <input type="text" class="form-control" id="dealName" name="dealName">
                        </fieldset>
                        <fieldset class="form-group">
                            <label for="dealStage"><fmt:message key= "dealStage"/></label>
                            <select class="form-control" id="dealStage" name="dealStage">
                                <option value="-1" selected disabled><fmt:message key= "pleaseSelect"/></option>
                                <c:forEach items="${dealStages}" var="stage">
                                    <option value=${stage}><fmt:message key= "${stage}"/></option>
                                </c:forEach>
                            </select>
                        </fieldset>
                        <fieldset class="form-group">
                            <label for="dealBudget"><fmt:message key= "lblBudget"/></label>
                            <input type="text" class="form-control" id="dealBudget" name="dealBudget">
                        </fieldset>
                    </div>
                </div>
            </div>
            <div class="col-md-3 col-sm-3">
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <h4 class="panel-title"><fmt:message key= "taskAdd"/></h4>
                    </div>
                    <div class="row" style="margin-top: 30px">
                        <fieldset class="form-group">
                            <label for="taskPeriod"><fmt:message key= "lblPeriod"/></label>
                            <select class="form-control" id="taskPeriod" name="taskPeriod">
                                <option value="-1" selected disabled><fmt:message key= "pleaseSelect"/></option>
                                <c:forEach items="${taskPeriods}" var="period">
                                    <option value=${period}><fmt:message key= "${period}"/></option>
                                </c:forEach>
                            </select>
                        </fieldset>
                        <fieldset class="form-group">
                            <div class="input-group date">
                                <input type="text" class="form-control"  value="dd.mm.yyyy" id="taskDate" name="taskDate">
                            <span class="input-group-addon">
                                <span class="glyphicon glyphicon-calendar"></span>
                            </span>
                            </div>
                            <div class="input-group clockpicker" style="margin-top: 10px">
                                <input type="text" class="form-control" value="hh:mm" id="taskTime" name="taskTime">
                            <span class="input-group-addon">
                                <span class="glyphicon glyphicon-time"></span>
                            </span>
                            </div>
                            <script type="text/javascript">
                                $(function () {
                                    $('#taskDate').data("DateTimePicker").FUNCTION();
                                });
                            </script>
                        </fieldset>

                        <fieldset class="form-group">
                            <label for="taskResponsible"><fmt:message key= "lblResponsible"/></label>
                            <select class="form-control" id="taskResponsible" name="taskResponsible">
                                <option value="-1" selected disabled><fmt:message key= "pleaseSelect"/></option>
                                <c:forEach items="${usersList}" var="user">
                                    <option value=${user.id}>${user.name}</option>
                                </c:forEach>
                            </select>
                        </fieldset>
                        <fieldset class="form-group">
                            <label for="taskDescription"><fmt:message key= "lblDescription"/></label>
                            <textarea class="form-control" id="taskDescription" name="taskDescription"
                                      rows="3"></textarea>
                        </fieldset>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <fieldset class="form-group" style="margin-left: 50px; margin-top: 30px">
                <label id="CompanyErrorMessage"class="label-danger"></label>
            </fieldset>
        </div>
        <div class="row" style="text-align:center;">
            <div class="col-md-12 col-sm-12">
                <button type="submit" class="btn btn-success btn-large" onclick="return checkAddCompanyForm()">
                    <fmt:message key= "lblSubmit"/>
                </button>
                <button type="reset" class="btn btn-danger btn-large" style="margin-left: 40px">
                    <fmt:message key= "lblReset"/>
                </button>
            </div>
        </div>
    </form>
    </jsp:attribute>
</t:pageLayout>