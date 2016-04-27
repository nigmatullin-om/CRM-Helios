<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    <link type="text/css" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.1/themes/smoothness/jquery-ui.css" rel="stylesheet" />
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.1/jquery-ui.min.js"></script>
    <script type="text/javascript" src="http://jquery-ui.googlecode.com/svn/tags/1.8.9/ui/i18n/jquery.ui.datepicker-ru.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
    <style> <%@include file='/resources/css/CompaniesList.css' %> </style>
    <script> <%@include file='/resources/js/companiesList.js' %> </script>
</head>
<body>
<t:pageLayout title="listCompanies">
    <jsp:attribute name="bodyLayout">
<div class="container">
    <h2 align="center">Компании</h2>
    <hr class="thickLine"/>

    <div class="row">
        <div class="col-md-4" style="border-right:darkgrey solid 1px;">

            <form id="radio-task">
                <p style="font-size: 12pt; font-weight: bold">Фильтры:</p>
                <form role="form">
                    <div class="radio">
                        <label><input type="radio" name="byTaskOption" value="all" checked="checked"><i>Полный список</i></label>
                    </div>
                    <div class="radio">
                        <label><input type="radio" name="byTaskOption" value="without"><i>Без задач</i></label>
                    </div>
                    <div class="radio">
                        <label><input type="radio" name="byTaskOption" value="outdated"><i>С просроченными задачами</i></label>
                    </div>
                    <div class="radio">
                        <label><input type="radio" name="byTaskOption" value="deleted"><i>Удаленные</i></label>
                    </div>
                </form>
            </form>

            <hr class="thinLine"/>
            <strong>Когда:</strong>
            <form id="select-period">
                <select id="period-dropdown" class="form-control">
                    <option value="allTime">за все время</option>
                    <option value="today">за сегодня</option>
                    <option value="3 days">за 3 дня</option>
                    <option value="week">за неделю</option>
                    <option value="month">за месяц</option>
                    <option value="3 months">за квартал</option>
                    <option value="calendar">календарь</option>
                </select> <br>
            </form>
            <div id="datepicker"></div>
            <form id="radio-created">
                <form role="form">
                    <div class="radio-inline">
                        <label><input type="radio" name="byPeriodOption" value="created" checked="checked"><i>созданы</i></label>
                    </div>
                    <div class="radio-inline">
                        <label><input type="radio" name="byPeriodOption" value="modified"><i>изменены</i></label>
                    </div>
                </form>
            </form>
            <hr class="thinLine"/>

            <form id="checkbox-stage">
                <strong>Этапы:</strong>
                <div class="checkbox">
                    <label><input type="checkbox" value="without deals">без сделок</label><br>
                    <label><input type="checkbox" value="without open deals">без открытых сделок</label><br>
                    <c:forEach items="${stages}" var="stages">
                        <label><input type="checkbox" value="${stages}">${stages}</label><br>
                    </c:forEach>
                </div>
            </form>

            <hr class="thinLine"/>
            <form id="select-managers">
                <strong>Менеджеры:</strong>
                <select id="managers-dropdown" class="form-control">
                    <option value="default">все менеджеры</option>
                    <c:forEach items="${users}" var="users">
                        <option value="${users.name}">${users.name}</option>
                    </c:forEach>
                </select>
            </form>

            <hr class="thinLine"/>
            <form id="select-task">
                <strong>Задачи:</strong>
                <select id="tasks-dropdown" class="form-control">
                    <option value="allTasks">не учитывать</option>
                    <option value="today">на сегодня</option>
                    <option value="tomorrow">на завтра</option>
                    <option value="week">на этой неделе</option>
                    <option value="month">в этом месяце</option>
                    <option value="3 months">в этом квартале</option>
                    <option value="noTasks">нет задач</option>
                    <option value="outdated">просрочены</option>
                </select>
            </form>

            <hr class="thinLine"/>
            <form id="select-tags">
                <strong>Теги:</strong>
                <select id="tags-dropdown" class="form-control">
                    <option value="default">все теги</option>
                    <c:forEach items="${tags}" var="tags">
                        <option value="${tags.name}">${tags.name}</option>
                    </c:forEach>
                </select><br>
            </form>

            <div class="submitButton"><input type="button" value="Применить" id="submit"></div>
            <div class="resetButton"><input type="button" value="Очистить" id="reset"></div>

        </div>

        <div class="col-md-8">
            <a href="http://localhost:8080/crm-helios/addCompany" class="addButton" role="button" >Добавить</a>
            <br> <br />
            <ul class="nav nav-tabs">
                <li class="active"><a data-toggle="tab" href="#companies">Компании</a></li>
                <li><a data-toggle="tab" href="#contacts">Контакты</a></li>
                <li><a data-toggle="tab" href="#all">Все</a></li>
            </ul>

            <div class="tab-content">
                <div id="companies" class="tab-pane fade in active">
                    <div class='table-responsive text-center'>
                        <table class='table table-bordered table-condensed' id='companiesTable'>
                            <thead>
                            <tr bgcolor="darkgrey">
                                <th width='50%'>Наименование</th>
                                <th width='25%' class='text-center'>Телефон</th>
                                <th width='25%' class='text-center'>E-mail</th>
                            </tr>
                            </thead>

                            <tbody id="tablebody">
                            <c:forEach items="${companies}" var="companies">
                                <tr>
                                    <td>${companies.name}</td>
                                    <td class='text-center'>${companies.phone}</td>
                                    <td class='text-center'>${companies.email}</td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>

            </div>
        </div>
    </div>
</div>
        </jsp:attribute>
</t:pageLayout>
</body>
</html>

