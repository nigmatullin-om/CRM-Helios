<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@tag description="Page Template" pageEncoding="UTF-8"%>

<c:set var ="userLocale" value="${cookie['localeCookie'].value}"/>
<c:if test="${userLocale == ''}">
    <c:set var ="userLocale" value="en_US"/>
    ${cookie['localeCookie'].value} = "en_US"
    ${cookie['localeCookie'].path} = "/"
</c:if>
<fmt:setLocale value="${userLocale}"/>
<fmt:setBundle basename="LocaleText"/>
<fmt:message key= "pageMain" var="pageMain"/>

<%@attribute name="title"%>
<%@attribute name="bodyLayout" fragment="true" %>

<html>
    <title>${title}</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/style.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-1.12.1.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/app.js"></script>
    <script>
        $(document).ready(function() {
            var pathname = window.location.pathname;
            $(".nav li").on("click", function () {
                $(".nav li").removeClass("active");
                $('.nav > li > a[href="'+pathname+'"]').parent().addClass('active');
            });
            $('.nav > li > a[href="'+pathname+'"]').parent().addClass('active');
        });
    </script>
</head>
<body>
<nav class="navbar navbar-default">
    <div class="container">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="${pageContext.request.contextPath}/"><fmt:message key= "pageMain"/></a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li><a href="${pageContext.request.contextPath}/dashboard"><fmt:message key= "pageDashboard"/></a></li>
            </ul>
            <ul class="nav navbar-nav">
                <li><a href="${pageContext.request.contextPath}/view/tasks"><fmt:message key= "pageTasks"/></a></li>
            </ul>
            <ul class="nav navbar-nav">
                <li><a href="${pageContext.request.contextPath}/add/company"><fmt:message key= "pageCompanyAdd"/></a></li>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>

<div class="container">

    <header>
    </header>

    <jsp:invoke fragment="bodyLayout"/>
    <hr>

    <footer>
        <div class="row">
            <div class="col-md-3 col-sm-3">
                <p>© 2016 CRM-HELIOS.</p>
            </div>
            <div class="col-md-9 col-sm-9" align="right">
                <input type="button" value="Рус" class="btn btn-default flag flag-ru" onclick="SetCookie('localeCookie','ru_RU')"/>
                <input type="button" value="Eng" class="btn btn-default flag flag-us" onclick="SetCookie('localeCookie','en_US')"/>
            </div>
        </div>
    </footer>
</div>

</body>
</html>