<%-- 
    Document   : inc
    Created on : Jul 27, 2014, 6:00:56 PM
    Author     : touchnan
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div id="bar">
    <a href='<c:url value="/"/>' class="button" >首页</a>
    <a href='<c:url value="/user/user"/>' class="button" >个人信息</a>
    <!-- <a href="javascript:void()" class="button" onClick="expFile()">导出通讯录</a> -->
    <a href='<c:url value="/txl/exp"/>' class="button">导出通讯录</a>
    <a href='<c:url value="/manage/logout"/>' class="button" >退出</a>
</div>
<div style="margin-top:10px; position: fixed;z-index: 2;width:100%;">
    <h1 align="center" >School班通讯录</h1>
</div>