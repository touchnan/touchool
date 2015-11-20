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
        <%@include file="../inc.jsp" %>
        <div id="example">
            <form:errors></form:errors>
            <form:form action="user" commandName="user">
                <ul>
                    <li><form:hidden path="id"/></li>
                    <li>
                        <form:input path="loginName"></form:input>
                        <form:errors path="loginName" />
                    </li>
                    <li>
                        <form:input path="passwd" value=""></form:input>
                        <form:errors path="passwd" />
                    </li>
                    <c:forEach var="up" items="${user.propDtos}" varStatus="vStatus">
                        <li>
                            <form:hidden path="propDtos[${vStatus.index}].id"></form:hidden>
                            <form:input path="propDtos[${vStatus.index}].title"></form:input>
                            :<form:input path="propDtos[${vStatus.index}].value"></form:input>
                                <span>删除</span>
                            </li>
                    </c:forEach>

                </ul>
                        <form:button>保存修改</form:button>

            </form:form>            
        </div>

    </body>
</html>