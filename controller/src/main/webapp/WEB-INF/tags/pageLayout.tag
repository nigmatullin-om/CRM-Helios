<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@tag description="Page Template" pageEncoding="UTF-8"%>

<%@attribute name="title"%>
<%@attribute name="bodyLayout" fragment="true" %>

<html>
<head>
    <title>${title}</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css"/>
</head>
<body>
<div class="container">

    <jsp:invoke fragment="bodyLayout"/>
    <hr>

    <footer>
        <p>© 2016 CRM-HELIOS.</p>
    </footer>
</div>

</body>
</html>