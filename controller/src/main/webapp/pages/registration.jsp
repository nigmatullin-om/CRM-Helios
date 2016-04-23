<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<t:pageLayout title="Создать контакт">
    <jsp:attribute name="bodyLayout">
              <div class="container">

                  <div class="row">
                      <div class="col-xs-12 col-sm-8 col-md-6 col-sm-offset-2 col-md-offset-3">
                          <form role="form" action="/registration" method="post">
                              <h2>CRM HELIOS</h2>
                              <hr class="colorgraph">
                              <div class="form-group">
                                  <input type="text" name="user_name" id="user_name" class="form-control input-lg" placeholder="Name" tabindex="1" required>
                              </div>
                              <div class="form-group">
                                  <input type="email" name="email" id="email" class="form-control input-lg" placeholder="email" tabindex="3" required>
                              </div>
                              <div class="form-group">
                                  <input type="text" name="phone" id="phone" class="form-control input-lg" placeholder="Phone" tabindex="4"  required>
                              </div>
                              <div class="form-group">
                                  <input type="text" name="role" id="role" class="form-control input-lg" placeholder="Role" tabindex="6">
                              </div>
                              <div class="form-group">
                                  <textarea class="form-control input-lg" name="note" rows="4" cols="25" id="note" placeholder="Note" ></textarea>
                              </div>
                              <div class="row">
                                  <div class="col-xs-6 col-sm-6 col-md-6">
                                      <div class="form-group">
                                          <input type="password" name="password" id="password" class="form-control input-lg" placeholder="Password" tabindex="5" required>
                                      </div>
                                  </div>
                                  <div class="col-xs-6 col-sm-6 col-md-6">
                                      <div class="form-group">
                                          <input type="password" name="password_confirmation" id="password_confirmation" class="form-control input-lg" placeholder="Confirm Password" tabindex="6" required>
                                      </div>
                                  </div>
                              </div>
                              <span class="label label-primary" name = "prompt">${prompt}</span>
                              <hr class="colorgraph">
                              <div class="row">
                                  <div class="col-xs-6 col-md-6"><input type="submit" value="Register" class="btn btn-primary btn-block btn-lg" tabindex="7"></div>
                                  <div class="col-xs-6 col-md-6"><a href="/login" class="btn btn-success btn-block btn-lg">Sign In</a></div>
                              </div>
                          </form>
                      </div>
                  </div>
              </div>
    </jsp:attribute>
</t:pageLayout>