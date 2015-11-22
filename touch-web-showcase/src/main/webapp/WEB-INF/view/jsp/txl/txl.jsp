<%-- 
    Document   : txl
    Created on : Jul 26, 2014, 2:51:17 PM
    Author     : touchnan
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="cn.touch.showcase.entity.UserProperty"%>
<%@page import="cn.touch.showcase.entity.User"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>School班通讯录</title>
        <link href='<c:url value="/resources/css/main.css" />'  rel="stylesheet" type="text/css"/>
        <style type="text/css">
            .item{
                position:absolute;	
            }
            .element {
                background: none repeat scroll 0 0 #888888;
                border-top-right-radius: 1.2em;
                color: #222222;
                float: left;
                height: 98%;	
                width: 98%;
            }	
            .element * {
                margin: auto auto;
                padding: 0em 0em 0em 2em;
                /*    position: absolute;*/
            }
            .element .number {
                color: rgba(0, 0, 0, 0.5);
                font-size: 2.25em;
                font-weight: bold;
                right: 0.5em;
                top: 0.5em;
            }	
            .element .symbol {
                color: #FFFFFF;
                font-size: 7.8em;
                left: 0.2em;
                line-height: 1em;
                top: 0.4em;
            }
            .element .name {
                /* bottom: 1.7em; */
                font-size: 1.7em;
                left: 0.5em;
                font-weight: bold;
                margin: 0.5em 0em 0.5em -2em;
                text-align: center;
            }
            .element .weight {
                bottom: 1em;
                font-size: 0.9em;
/*                /left: 0.5em;*/
                position: absolute;
            }
            .element:hover {
                cursor: pointer;
            }
            .element:hover h3{
                text-shadow: 0 0 10px white, 0 0 10px white;
            }
            .element:hover h2 {
                color: white;
            }
            .element.nonmetal {
                background: none repeat scroll 0 0 #62C2CC;
            }
            .element.noble-gas {
                background: none repeat scroll 0 0 #FF717E;
            }
            .element.alkali {
                background: none repeat scroll 0 0 #E86850;
            }
            .element.alkaline-earth {
                background: none repeat scroll 0 0 #F68B1F;
            }
            .element.metalloid {
                background: none repeat scroll 0 0 #1F7872;
            }
            .element.metal {
                background: none repeat scroll 0 0 #666666;
            }            
        </style>
        <script src='<c:url value="/resources/js/jquery-1.9.1.min.js" />'></script>
        <script src='<c:url value="/resources/js/jresponsive.js" />'></script>
    </head>
    <body>
        <!--        <div id="bar">
                    <a href='<c:url value=""/>' class="button" >首页</a>
                    <a href="<c:url value="/user/user"/>" class="button" >个人信息</a>
                    <a href="javascript:void()" class="button" onClick="expFile()">导出通讯录</a>
                    <a href="<c:url value=""/>" class="button" >密码</a>
                </div>
                <div style="margin-top:10px; position: fixed;z-index: 2;width:100%;">
                    <h1 align="center" >School班通讯录</h1>
                </div>-->
        <%@include file="../inc.jsp" %>
        <div id="example"></div>

        <script type="text/javascript">
            var content = [];

            <c:forEach var="u" items="${users}" varStatus="vStatus">
                content[${vStatus.index}] = '<div class="element <c:choose><c:when test="${u.type==0}">nonmetal</c:when><c:otherwise>noble-gas</c:otherwise></c:choose>"><c:forEach var="up" items="${u.props}"><c:if test="${up.value!='' && up.value!=null}"><div <c:if test="${up.type==1}">class="name"</c:if>><c:if test="${up.type!=1}"><c:out value="${up.title}："></c:out></c:if><c:out value="${up.value}">:</c:out></div></c:if></c:forEach><div class="weight">最近更新：${u.updateDate}</div></div>';
            </c:forEach>

            $(function() {
                $('#example').jresponsive({
                    min_size: 200,
                    max_size: 200,
                    hspace: 50,
                    vspace: 10,
                    height: 280,
                    class_name: 'item',
                    content_array: content,
                    transformation: 'animation'
                });
            });

        </script>         
    </body>


</html>
