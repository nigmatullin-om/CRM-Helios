<%@include file="/resources/jsp/locale.jsp"%>

<fmt:message key= "pageMain" var="pageMain"/>

<t:pageLayout title="${pageMain}">
    <jsp:attribute name="bodyLayout">
        <p>CRM-HELIOS</p>

    </jsp:attribute>
</t:pageLayout>