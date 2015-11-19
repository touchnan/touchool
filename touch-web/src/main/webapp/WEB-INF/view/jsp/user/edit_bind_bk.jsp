<%-- 
    Document   : login
    Created on : Jul 27, 2014, 1:03:10 PM
    Author     : touchnan
--%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>School班通讯录</title>
        <link href='<c:url value="/resources/css/main.css" />'  rel="stylesheet" type="text/css"/>
        <script src="<c:url value="/resources/js/jquery-1.9.1.min.js" />"></script>
    </head>
    <body>
       <spring:bind path="user.*">
            <font color="red"><b>${status.errorMessage}</b> </font>
            <br>
        </spring:bind>
        请输入用户名和密码：
        <p> 
        <form name="loginform" action="<c:url value='/user/user'/>" method="POST">
            <ul>
                <li>
                    <spring:bind path="user.loginName">   
                        登录名： <input type="text" name="${status.expression}"
                                    value="${status.value}"  style="width: 100px"/>
                        <font color="red">${status.errorMessage}</font>
                    </spring:bind>                    
                </li>
                <li>
                    <spring:bind path="user.passwd">  
                        密码： <input type="text" name="${status.expression}"
                                   value="${status.value}"  style="width: 100px"/>
                        <font color="red">${status.errorMessage}</font>
                    </spring:bind>

                </li>
                <c:forEach var="up" items="${user.propDtos}" varStatus="vStatus">
                    <li>
                        ${up.title}${vStatus.index}： <input type="text" name="propDtos[${vStatus.index}].value"
                                 value="${up.value}"  style="width: 100px"/>
                    </li>
                </c:forEach>
            </ul>
            <input type="submit" value="修改保存" />
        </form>
    </body>
</html>