<%-- 
    Document   : login
    Created on : Jul 27, 2014, 1:03:10 PM
    Author     : touchnan
--%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>School班通讯录</title>
        <link href='<c:url value="/resources/css/bootstrap.min.css" />'  rel="stylesheet" type="text/css"/>
        
		<style type="text/css">
			div.center, p.center, img.center {
				  margin-left: auto !important;
				  margin-right: auto !important;
				  float: none !important;
				  display: block;
				  text-align: center;
			}
		</style>
	
    </head>
	<body>
		
			<div class="ch-container">
			    <div class="row">
			        
					    <div class="row">
					        <div class="col-md-12 center login-header">
					            <h2>Welcome to 通讯录</h2>
					        </div>
					    </div>
					
					    <div class="row">
					        <div class="well col-md-5 center login-box">
					            <div class="alert alert-info">
					            
					                          <spring:bind path="user.*">
					                          	<c:choose>
											      <c:when test="${!empty status.errorMessage }"><font color="red">${status.errorMessage}</font></c:when>
											      <c:otherwise>请输入用户名和密码</c:otherwise>
												</c:choose>	
												
                    						</spring:bind>
					            </div>
					            <form class="form-horizontal" action="<c:url value='/user/auth'/>" method="post">
					                <fieldset>
					                    <div class="input-group input-group-lg">
					                        <span class="input-group-addon"><i class="glyphicon glyphicon-user red"></i></span>
					                         <spring:bind path="user.loginName">
					                        <input type="text" class="form-control" name="${status.expression}" value="${status.value}" placeholder="用户">
					                        <font color="red">${status.errorMessage}</font>
					                        </spring:bind>
					                    </div>
					                    <div class="clearfix"></div><br>
					
					                    <div class="input-group input-group-lg">
					                        <span class="input-group-addon"><i class="glyphicon glyphicon-lock red"></i></span>
					                        <spring:bind path="user.passwd">  
					                        <input type="password" class="form-control" name="${status.expression}" placeholder="密码">
					                        <font color="red">${status.errorMessage}</font>
					                        </spring:bind>
					                    </div>
					                    <div class="clearfix"></div>
										
										<!--
					                    <div class="input-prepend">
					                        <label class="remember" for="remember"><input type="checkbox" name="rememberMe">Remember me</label>
					                    </div>
					                    -->
					                    <div class="clearfix"></div>
					
					                    <p class="center col-md-5" style="margin-top:20px">
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


