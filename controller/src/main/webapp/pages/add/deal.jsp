<%@include file="/resources/jsp/locale.jsp"%>

<t:pageLayout title="${pageDealAdd}">
   <jsp:attribute name="bodyLayout">

        <html>
        <head>
            <title><fmt:message key="dealAdd"/></title>
            <meta name="viewport" content="width=device-width, initial-scale=1">
            <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
            <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
            <link rel="stylesheet" href="../../resources/css/clockpicker.css">
            <script src="//code.jquery.com/jquery-1.10.2.js"></script>
            <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
            <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
            <script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
            <script src="/resources/js/clockpicker.js"></script>
            <script src="../../resources/js/addDealForm.js"></script>
            <script src="../../resources/js/addContactsToDeal.js"></script>
            <script src="../../resources/js/messageResource.min.js"></script>
            <script>
                $(function() {
                    $("#dealDate").datepicker();
                });
                $(function() {
                    $("#taskDate").datepicker();
                });
                $(function(){
                    $("#taskTime").clockpicker();
                });
                $(document).ready(initWarnings());
            </script>
        </head>
        <body>

            <div class="container">
                <h1 style="text-align:  center"><fmt:message key="dealAdd"/></h1>
                <form class="form-horizontal" id="addDealForm" role="form" action="/add/deal" method="post" enctype="multipart/form-data">
                    <div class="row">
                        <div class="col-sm-6">

                            <div class="form-group" id="dealNameBlock">
                                <label class="control-label col-sm-4"><fmt:message key="dealName"/></label>
                                <div class="col-sm-8">
                                    <input type="text" class="form-control" name="dealName" id="dealName">
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-sm-4"><fmt:message key="lblTags"/></label>
                                <div class="col-sm-8">
                                    <input type="text" class="form-control" name="dealTags" id="dealTags" >
                                </div>
                            </div>

                            <div class="form-group" id="dealResponsibleBlock">
                                <label class="control-label col-sm-4"><fmt:message key="lblResponsible"/></label>
                                <div class="col-sm-8">
                                    <select class="form-control" value="-1" name="dealResponsible" id="dealResponsible" placeholder="SELECT">
                                        <option value="-1" selected disabled><fmt:message key="pleaseSelect"/></option>
                                        <c:forEach items="${users}" var="user">
                                            <option value="${user.id}">${user.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>

                            <div class="form-group" id="dealBudgetBlock">
                                <label class="control-label col-sm-4"><fmt:message key="lblBudget"/></label>
                                <div class="col-sm-7">
                                    <input type="number" step="0.01" class="form-control" name="dealBudget" id="dealBudget" >
                                </div>
                                <label class="control-label col-sm-1">грн</label>
                            </div>

                            <div class="form-group" id="dealStageBlock">
                                <label class="control-label col-sm-4"><fmt:message key="dealStage"/></label>
                                <div class="col-sm-8">
                                    <select class="form-control" name="dealStage" id="dealStage">
                                        <option value="-1" selected disabled><fmt:message key="pleaseSelect"/>t</option>
                                        <c:forEach items="${dealStages}" var="stage">
                                            <option value="${stage.id}">${stage.name}</option>
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
                                            <option value="${phoneType.id}">${phoneType.name}</option>
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
                                        <c:forEach items="${periods}" var="period">
                                            <option value="${period.id}">${period.name}</option>
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
                                        <c:forEach items="${users}" var="user">
                                            <option value="${user.id}">${user.name}</option>
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

                    <div class="row">
                        <div class="col-sm-6">
                            <div class="form-group">
                                <label class="control-label col-sm-4"></label>
                                <button type="submit" class="btn control-button col-sm-4" onclick="return checkDealForm()" name="save" id="save"><fmt:message key="lblSubmit"/></button>
                            </div>
                        </div>
                        <div class="col-sm-6">
                            <div class="form-group">
                                <label class="control-label col-sm-4"></label>
                                <a href="/dashboard" class="button"> <button type="button" class="btn control-button col-sm-4" name="cancel" id="cancel"><fmt:message key="lblReset"/></button> </a>

                            </div>
                        </div>
                    </div>

                </form>
            </div>
        </body>
        </html>
    </jsp:attribute>
</t:pageLayout>