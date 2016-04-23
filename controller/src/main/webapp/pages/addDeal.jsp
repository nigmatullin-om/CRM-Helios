<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Добавление сделки</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
    <link rel="stylesheet" href="../resources/css/clockpicker.css">
    <script src="//code.jquery.com/jquery-1.10.2.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
    <script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
    <script src="../resources/js/clockpicker.js"></script>
    <script src="../resources/js/addDealForm.js"></script>
    <script src="../resources/js/addContactsToDeal.js"></script>
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
    </script>
</head>
<body>
    <div class="container">
        <h1 style="text-align:  center">Добавление сделки</h1>
        <form class="form-horizontal" id="addDealForm" role="form" action="/saveDeal" method="post" enctype="multipart/form-data">
            <div class="row">
                <div class="col-sm-6">

                    <div class="form-group" id="dealNameBlock">
                        <label class="control-label col-sm-4">Название сделки</label>
                        <div class="col-sm-8">
                            <input type="text" class="form-control" name="dealName" id="dealName">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="control-label col-sm-4">Теги</label>
                        <div class="col-sm-8">
                            <input type="text" class="form-control" name="dealTags" id="dealTags" >
                        </div>
                    </div>

                    <div class="form-group" id="dealResponsibleBlock">
                        <label class="control-label col-sm-4">Ответственный</label>
                        <div class="col-sm-8">
                            <select class="form-control" value="-1" name="dealResponsible" id="dealResponsible" placeholder="SELECT">
                                <option value="-1" selected disabled>Please select</option>
                                <c:forEach items="${users}" var="user">
                                    <option value="${user.id}">${user.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>

                    <div class="form-group" id="dealBudgetBlock">
                        <label class="control-label col-sm-4">Бюджет</label>
                        <div class="col-sm-7">
                            <input type="number" step="0.01" class="form-control" name="dealBudget" id="dealBudget" >
                        </div>
                        <label class="control-label col-sm-1">грн</label>
                    </div>

                    <div class="form-group" id="dealStageBlock">
                        <label class="control-label col-sm-4">Этап</label>
                        <div class="col-sm-8">
                            <select class="form-control" name="dealStage" id="dealStage">
                                <option value="-1" selected disabled>Please select</option>
                                <c:forEach items="${dealStages}" var="stage">
                                    <option value="${stage.id}">${stage.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>

                    <div class="form-group" id="dealNoteBlock">
                        <label class="control-label col-sm-4">Примечание</label>
                        <div class="col-sm-8">
                            <textarea class="form-control" rows="5" name="dealNote" id="dealNote"></textarea>
                        </div>
                    </div>

                    <div class="form-group" id="dealDateBlock">
                        <label class="control-label col-sm-4">Дата создания</label>
                        <div class="col-sm-8">
                            <input type="text" class="form-control" id="dealDate" name="dealDate">
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-sm-12">
                            <label class="control-label col-sm-4">Добавить файл</label>
                            <div class="col-sm-8">
                                <input type="file" class="file" data-show-preview="false" name="fileName" id="fileName">
                            </div>
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-sm-8">
                            <select class="form-control"  name="contact" id="contact">
                                <option value="-1" selected disabled>Please select</option>
                                <c:forEach items="${contacts}" var="contact">
                                    <option value="${contact.id}">${contact.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <button type="button" onclick="return addDealButton()" class="btn control-button col-sm-4">Добавить</button>
                    </div>

                    <div class="form-group">
                        <label class="control-label col-sm-8">Добавить Контакт</label>
                    </div>

                    <div class="form-group" id="contactNameBlock">
                        <label class="control-label col-sm-4">Имя Фамилия</label>
                        <div class="col-sm-8">
                            <input type="text" class="form-control" name="contactName" id="contactName">
                        </div>
                    </div>

                    <div class="form-group" id="contactCompanyBlock">
                        <label class="control-label col-sm-4">Компания</label>
                        <div class="col-sm-8">
                            <input type="text" class="form-control" name="contactCompany" id="contactCompany">
                        </div>
                    </div>

                    <div class="form-group" id="contactPositionBlock">
                        <label class="control-label col-sm-4">Должность</label>
                        <div class="col-sm-8">
                            <input type="text" class="form-control" name="contactPosition" id="contactPosition">
                        </div>
                    </div>

                    <div class="form-group" id="contactPhoneTypeBlock">
                        <div class="col-sm-4">
                            <select class="form-control"  name="contactPhoneType" id="contactPhoneType">
                                <option value="-1" selected disabled>Please select</option>
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
                        <label class="control-label col-sm-4">Email</label>
                        <div class="col-sm-8">
                            <input type="text" class="form-control" name="contactEmail" id="contactEmail">
                        </div>
                    </div>

                    <div class="form-group" id="contactSkypeBlock">
                        <label class="control-label col-sm-4">Skype</label>
                        <div class="col-sm-8">
                            <input type="text" class="form-control" name="contactSkype" id="contactSkype">
                        </div>
                    </div>
                </div>

                <div class="col-sm-6">
                    <div class="form-group" id="companyBlock">
                        <label class="control-label col-sm-4">Компания</label>
                        <div class="col-sm-4">
                            <select class="form-control"  name="company" id="company">
                                <option value="-1" selected disabled>Please select</option>
                                <c:forEach items="${companies}" var="companyItem">
                                    <option value="${companyItem.id}">${companyItem.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <button type="button" class="btn control-button col-sm-4" onclick="return addCompanyButtonFunction()" name="addCompany" id="addCompany">Добавить компанию</button>
                    </div>

                    <div class="form-group">
                        <label class="control-label col-sm-8">Добавить компанию</label>
                    </div>

                    <div class="form-group" id="companyNameBlock">
                        <label class="control-label col-sm-4">Название компании</label>
                        <div class="col-sm-8">
                            <input type="text" class="form-control" name="companyName" id="companyName">
                        </div>
                    </div>

                    <div class="form-group" id="companyPhoneBlock">
                        <label class="control-label col-sm-4">Номер телефона</label>
                        <div class="col-sm-8">
                            <input type="text" class="form-control" name="companyPhone" id="companyPhone">
                        </div>
                    </div>

                    <div class="form-group" id="companyEmailBlock">
                        <label class="control-label col-sm-4">Email</label>
                        <div class="col-sm-8">
                            <input type="text" class="form-control" name="companyEmail" id="companyEmail">
                        </div>
                    </div>

                    <div class="form-group" id="companyWebBlock">
                        <label class="control-label col-sm-4">Web адресс</label>
                        <div class="col-sm-8">
                            <input type="text" class="form-control" name="companyWeb" id="companyWeb">
                        </div>
                    </div>

                    <div class="form-group" id="companyAdressBlock">
                        <label class="control-label col-sm-4">Адресс</label>
                        <div class="col-sm-8">
                            <input type="text" class="form-control" name="companyAdress" id="companyAdress">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="control-label col-sm-8">Добавить действие</label>
                    </div>

                    <div class="form-group" id="taskPeriodBlock">
                        <label class="control-label col-sm-4">Период</label>
                        <div class="col-sm-8">
                            <select class="form-control"  name="taskPeriod" id="taskPeriod">
                                <option value="-1" selected disabled>Please select</option>
                                <c:forEach items="${periods}" var="period">
                                    <option value="${period.id}">${period.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="control-label col-sm-8">или дату и время</label>
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
                        <label class="control-label col-sm-4">Имя задачи</label>
                        <div class="col-sm-8">
                            <input type="text" class="form-control" name="taskName" id="taskName">
                        </div>
                    </div>

                    <div class="form-group" id="taskResponsibleBlock">
                        <label class="control-label col-sm-4">Ответственный</label>
                        <div class="col-sm-8">
                            <select class="form-control" name="taskResponsible" id="taskResponsible">
                                <option value="-1" selected disabled>Please select</option>
                                <c:forEach items="${users}" var="user">
                                    <option value="${user.id}">${user.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>

                    <div class="form-group" id="taskTypeBlock">
                        <label class="control-label col-sm-4">Тип задачи</label>
                        <div class="col-sm-8">
                            <select class="form-control" name="taskType" id="taskType">
                                <option value="-1" selected disabled>Please select</option>
                                <c:forEach items="${taskTypes}" var="taskType">
                                    <option value="${taskType.id}">${taskType.typeName}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>

                    <div class="form-group" id="taskTextBlock">
                        <label class="control-label col-sm-4">Текст задачи</label>
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
                        <button type="submit" class="btn control-button col-sm-4" onclick="return checkDealForm()" name="save" id="save">Сохранить</button>
                    </div>
                </div>
                <div class="col-sm-6">
                    <div class="form-group">
                        <label class="control-label col-sm-4"></label>
                        <a href="/dashboard" class="button"> <button type="button" class="btn control-button col-sm-4" name="cancel" id="cancel">Отмена</button> </a>

                    </div>
                </div>
            </div>

        </form>
    </div>
</body>
</html>
