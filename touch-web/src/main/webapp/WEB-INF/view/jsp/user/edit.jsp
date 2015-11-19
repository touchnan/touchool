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
        <link href='<c:url value="/resources/css/bootstrap.min.css" />'  rel="stylesheet" type="text/css"/>
        <link href='<c:url value="/resources/css/main.css" />'  rel="stylesheet" type="text/css"/>
        <script src="<c:url value="/resources/js/jquery-1.9.1.min.js" />"></script>
        
        <style type="text/css">
          li {
          	color:rgb(85, 74, 74);
          	margin:5px 5px;
          }
        </style>
    </head>
    <body>
        <%@include file="../inc.jsp" %>
        <div id="example" style="text-align: center;position: relative;">
            <section>
                <spring:bind path="user.*">
                    <font color="red"><b>${status.errorMessage}</b> </font>
                    </spring:bind>
                <form name="loginform" action="<c:url value='/user/user'/>" method="POST">
                    <spring:bind path="user.id">   
                        <input type="hidden" name="${status.expression}" value="${status.value}" />
                    </spring:bind>                    
                    <ul>
                        <li>
                            <spring:bind path="user.loginName">   
                                登录名： <input type="text" name="${status.expression}"
                                            value="${status.value}" maxlength="20"/>
                                <font color="red">${status.errorMessage}</font>
                            </spring:bind>                    
                        </li>
                        <li>
                            <spring:bind path="user.passwd">
                                密&nbsp;&nbsp;&nbsp;码： <input type="text" name="${status.expression}"
                                                             value="" maxlength="20"/>
                                <font color="red">${status.errorMessage}</font>
                            </spring:bind>

                        </li>
                        <c:forEach var="up" items="${user.props}" varStatus="vStatus">
                            <li>
                                <input type="hidden" name="props[${vStatus.index}].id" value="${up.id}" />
                                <c:choose>
                                    <c:when test="${up.type==1}">
                                        姓&nbsp;&nbsp;&nbsp;名： 
                                    </c:when>
                                    <c:otherwise>
                                        选项名：<input type="text" name="props[${vStatus.index}].title" value="${up.title}" maxlength="10"/>
                                        &nbsp;&nbsp;&nbsp;&nbsp;选项值：
                                    </c:otherwise>

                                </c:choose>
                                <input type="text" name="props[${vStatus.index}].value"
                                       value="${up.value}" maxlength="50"/>
                            </li>
                        </c:forEach>
                    </ul>
                    <input type="submit" class="btn btn-primary" value="修改保存" />
                </form> 
            </section>
        </div>

    </body>
</html>