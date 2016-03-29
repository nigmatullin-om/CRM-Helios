<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Добавление сделки</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
    <script src="//code.jquery.com/jquery-1.10.2.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
    <script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
    <script>
        $(function() {
            $("#datepicker").datepicker();
        });
        $(function() {
            $("#datepicker2").datepicker();
        });
        $('.clockpicker').clockpicker();
    </script>
</head>
<body>
    <div class="container">
        <h1>Добавление сделки</h1>
        <form class="form-horizontal" role="form" action="" method="post">
            <div class="row">
                <div class="col-sm-6">
                    <div class="form-group">
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
                    <div class="form-group">
                        <label class="control-label col-sm-4">Ответственный</label>
                        <div class="col-sm-8">
                            <select class="form-control" name="dealResponsible" id="dealResponsible">
                                <option value="one">One</option>
                                <option value="two">Two</option>
                                <option value="three">Three</option>
                                <option value="four">Four</option>
                                <option value="five">Five</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-4">Бюджет</label>
                        <div class="col-sm-7">
                            <input type="text" class="form-control" name="dealBudget" id="dealBudget" >
                        </div>
                        <label class="control-label col-sm-1">грн</label>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-4">Этап</label>
                        <div class="col-sm-8">
                            <select class="form-control" name="dealStage" id="dealStage">
                                <c:forEach items="${stages}" var="stage">
                                    <option value="${stage.ordinal()}">${stage}</option>
                                </c:forEach>

                                <option value="two">Two</option>
                                <option value="three">Three</option>
                                <option value="four">Four</option>
                                <option value="five">Five</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-4">Примечание</label>
                        <div class="col-sm-8">
                            <textarea class="form-control" rows="5" name="dealNote" id="dealNote"></textarea>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-4">Дата создания</label>
                        <div class="col-sm-8">
                            <input type="text" class="form-control" id="datepicker">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-8">
                            <select class="form-control" title="Выбрать контакт" name="dealContact" id="dealContact">
                                <option value="one">One</option>
                                <option value="two">Two</option>
                                <option value="three">Three</option>
                                <option value="four">Four</option>
                                <option value="five">Five</option>
                            </select>
                        </div>
                        <button type="button" class="btn control-button col-sm-4">Добавить</button>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-8">Добавить Контакт</label>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-4">Имя Фамилия</label>
                        <div class="col-sm-8">
                            <input type="text" class="form-control" name="contactName" id="contactName">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-4">Компания</label>
                        <div class="col-sm-8">
                            <input type="text" class="form-control" name="contactCompany" id="contactCompany">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-4">Должность</label>
                        <div class="col-sm-8">
                            <input type="text" class="form-control" name="contactPosition" id="contactPosition">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-4">
                            <select class="form-control" title="Выбрать контакт" name="contactPhoneType" id="contactPhoneType">
                                <option value="one">One</option>
                                <option value="two">Two</option>
                                <option value="three">Three</option>
                                <option value="four">Four</option>
                                <option value="five">Five</option>
                            </select>
                        </div>
                        <div class="col-sm-8">
                            <input type="text" class="form-control" name="contactPhone" id="contactPhone">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-4">Email</label>
                        <div class="col-sm-8">
                            <input type="text" class="form-control" name="contactEmail" id="contactEmail">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-4">Skype</label>
                        <div class="col-sm-8">
                            <input type="text" class="form-control" name="contactSkype" id="contactSkype">
                        </div>
                    </div>
                </div>

                <div class="col-sm-6">
                    <div class="form-group">
                        <label class="control-label col-sm-4">Компания</label>
                        <div class="col-sm-4">
                            <select class="form-control" title="Выбрать компанию" name="dealCompany" id="dealCompany">
                                <option value="one">One</option>
                                <option value="two">Two</option>
                                <option value="three">Three</option>
                                <option value="four">Four</option>
                                <option value="five">Five</option>
                            </select>
                        </div>
                        <button type="button" class="btn control-button col-sm-4" name="addCompany" id="addCompany">Добавить компанию</button>
                    </div>

                    <div class="form-group">
                        <label class="control-label col-sm-8">Добавить компанию</label>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-4">Название компании</label>
                        <div class="col-sm-8">
                            <input type="text" class="form-control" name="companyName" id="companyName">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-4">Номер телефона</label>
                        <div class="col-sm-8">
                            <input type="text" class="form-control" name="companyPhone" id="companyPhone">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-4">Email</label>
                        <div class="col-sm-8">
                            <input type="text" class="form-control" name="companyEmail" id="companyEmail">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-4">Web адресс</label>
                        <div class="col-sm-8">
                            <input type="text" class="form-control" name="companyWeb" id="companyWeb">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-4">Адресс</label>
                        <div class="col-sm-8">
                            <input type="text" class="form-control" name="companyAdress" id="companyAdress">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="control-label col-sm-8">Добавить действие</label>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-4">Период</label>
                        <div class="col-sm-8">
                            <select class="form-control" title="Выбрать период" name="taskPeriod" id="taskPeriod">
                                <option value="one">One</option>
                                <option value="two">Two</option>
                                <option value="three">Three</option>
                                <option value="four">Four</option>
                                <option value="five">Five</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-8">или дату и время</label>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="datepicker2">
                        </div>
                        <div class="input-group clockpicker">
                            <input type="text" class="form-control" value="09:30">
                            <span class="input-group-addon">
                                <span class="glyphicon glyphicon-time"></span>
                            </span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-4">Ответственный</label>
                        <div class="col-sm-8">
                            <select class="form-control" name="taskResponsible" id="taskResponsible">
                                <option value="one">One</option>
                                <option value="two">Two</option>
                                <option value="three">Three</option>
                                <option value="four">Four</option>
                                <option value="five">Five</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-4">Тип задачи</label>
                        <div class="col-sm-8">
                            <select class="form-control" name="taskType" id="taskType">
                                <option value="one">One</option>
                                <option value="two">Two</option>
                                <option value="three">Three</option>
                                <option value="four">Four</option>
                                <option value="five">Five</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
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
                        <div class="col-sm-8">
                            <button type="button" class="btn control-button col-sm-8" name="cancel" id="cancel">Отмена</button>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6">
                    <div class="form-group">
                        <button type="button" class="btn control-button col-sm-8" name="save" id="save">Сохранить</button>
                    </div>
                </div>
            </div>

        </form>
    </div>

</body>
</html>
