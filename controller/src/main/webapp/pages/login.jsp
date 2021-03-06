<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@include file="/resources/jsp/locale.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<fmt:message key= "pageLogin" var="pageLogin"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<t:pageLayout title="${pageLogin}">
    <jsp:attribute name="bodyLayout">
        <div class="container col-md-offset-3">
            <div class="row">
                <div class="col-xs-12 col-sm-12 col-lg-6">
                    <div class="panel panel-primary">
                        <div class="panel-heading">
                            <h3 class="panel-title">
                                crm-helios</h3>
                        </div>
                        <div class="panel-body">
                            <div class="row">
                                <div class="col-xs-12 col-sm-12 col-md-12 ">
                                    <form role="form" action="/login" method="post" id="login">
                                        <div class="col-xs-12 col-sm-12 col-md-12 " >
                                            <br>
                                            <div class="input-group">
                                                <fmt:message key= "Username" var="Username"/>
                                                <span class="input-group-addon"><span class="glyphicon glyphicon-user"></span></span>
                                                <input type="text" name="userName" class="form-control input-lg" placeholder="${Username}" required autofocus/>
                                            </div>

                                            <div class="input-group">
                                                <fmt:message key= "Password" var="Password"/>
                                                <span class="input-group-addon"><span class="glyphicon glyphicon-lock"></span></span>
                                                <input type="password" name="userPassword" class="form-control input-lg" placeholder="${Password}" required />
                                            </div>
                                        </div>
                                       <div class="col-xs-12 col-sm-12 col-md-12">
                                           <fmt:message key= "hintLogin"/> <a href="/registration"><fmt:message key= "registrationRef"/></a>
                                            <span class="wrong-pas-text"> ${wrong_pass_or_email}</span>
                                           <hr class="colorgraph">
                                        </div>
                                                <div class="row">
                                                    <fmt:message key= "inputButton" var = "inputButton"/>
                                                    <div class="col-xs-offset-3 col-sm-offset-3  col-md-offset-3 col-xs-6 col-sm-6 col-md-6">
                                                            <input type="submit" value="${inputButton}" class="btn btn-primary btn-block btn-lg btn-success">
                                                            </input>
                                                        </div>

                                                </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </jsp:attribute>
</t:pageLayout>