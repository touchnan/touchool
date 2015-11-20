<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/jsp/inc/inc.jsp"%>
<html>
	<head>
		<meta charset="utf-8">
		<title>欢迎页面</title>
	</head> 
	<body>
		<c:url value="/site" var="siteUrl" />
		<a href="${siteUrl}">Click to enter site</a>
		
		<hr>
		<a href="<c:url value="/scroll"  />">Scroll</a>
		
		
		<hr>
		<a href="<c:url value='/manage'/>">Click to 后台管理页面</a>
		
		<hr>
		<a href="<c:url value='/txl'/>">Click to 通讯录</a>		
	</body>
</html>
