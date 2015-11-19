<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/inc.jsp"%>
<html>
	<head>
	<%@ include file="/WEB-INF/view/inc/head.jsp" %>
	</head> 
	<body>
		
			<div class="ch-container">
			    <div class="row">
			        
					    <div class="row">
					        <div class="col-md-12 center login-header">
					            <h2>Welcome to Touch</h2>
					        </div>
					    </div>
					
					    <div class="row">
					        <div class="well col-md-5 center login-box">
					            <div class="alert alert-info">

												<c:choose>
											      <c:when test="${!empty info }"><font color="red">${info}</font></c:when>
											      <c:otherwise>请输入用户名和密码</c:otherwise>
												</c:choose>					               
													 <!-- 		             
					                Please login with your Username and Password.请输入用户名和密码.
					                 -->
					            </div>
					            <form class="form-horizontal" method="post">
					                <fieldset>
					                    <div class="input-group input-group-lg">
					                        <span class="input-group-addon"><i class="glyphicon glyphicon-user red"></i></span>
					                        <input type="text" class="form-control" name="username" value="${username}" placeholder="用户">
					                    </div>
					                    <div class="clearfix"></div><br>
					
					                    <div class="input-group input-group-lg">
					                        <span class="input-group-addon"><i class="glyphicon glyphicon-lock red"></i></span>
					                        <input type="password" class="form-control" name="password" placeholder="密码">
					                    </div>
					                    <div class="clearfix"></div>
					
					                    <div class="input-prepend">
					                        <label class="remember" for="remember"><input type="checkbox" name="rememberMe">Remember me</label>
					                    </div>
					                    <div class="clearfix"></div>
					
					                    <p class="center col-md-5">
					                        <button type="submit" class="btn btn-primary">Login</button>
					                    </p>
					                </fieldset>
					            </form>
					        </div>
					    </div>
					</div>
			</div>
	</body>
</html>


