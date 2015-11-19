<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/inc.jsp"%>

<html>
	<head>
	    <meta charset="utf-8">
	    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	    <meta name="description" content="">
	    <meta name="author" content="">
	    <title>scroll</title>
	</head>
  
<body>

	<!-- 容器 -->
	<div id="container" style="border:1px solid red;heigth:40px;">
		  
        <div class="scroll" style="border:1px solid blue">         
        	<!-- 每次要加载数据的数据块-->
		    载数据的数据块每次要加载数据的数据块每次要加载数据的数据块每次要加载数据的数据块每次要加载数据的数据块
		    载数据的数据块每次要加载数据的数据块每次要加载数据的数据块每次要加载数据的数据块每次要加载数据的数据块
		    载数据的数据块每次要加载数据的数据块每次要加载数据的数据块每次要加载数据的数
		    载数据的数据块每次要加载数据的数据块每次要加载数据的数据块每次要加载数据的数据块每次要加载数据的数据块
		    载数据的数据块每次要加载数据的数据块每次要加载数据的数据块每次要加载数据的数据块每次要加载数据的数据块
		    载数据的数据块每次要加载数据的数据块每次要加载数据的数据块每次要加载数据的数据块每次要加载数据的数据块
		    载数据的数据块每次要加载数据的数据块每次要加载数据的数据块每次要加载数据的数据块每次要加载数据的数据块
		 </div>
        <div class="scroll" style="border:1px solid blue">         
        	<!-- 每次要加载数据的数据块-->
		    载数据的数据块每次要加载数据的数据块每次要加载数据的数据块每次要加载数据的数据块每次要加载数据的数据块
		    载数据的数据块每次要加载数据的数据块每次要加载数据的数据块每次要加载数据的数据块每次要加载数据的数据块
		    载数据的数据块每次要加载数据的数据块每次要加载数据的数据块每次要加载数据的数
		    载数据的数据块每次要加载数据的数据块每次要加载数据的数据块每次要加载数据的数据块每次要加载数据的数据块
		    载数据的数据块每次要加载数据的数据块每次要加载数据的数据块每次要加载数据的数据块每次要加载数据的数据块
		    载数据的数据块每次要加载数据的数据块每次要加载数据的数据块每次要加载数据的数据块每次要加载数据的数据块
		    载数据的数据块每次要加载数据的数据块每次要加载数据的数据块每次要加载数据的数据块每次要加载数据的数据块
		 </div>
		    
        <div class="scroll" style="border:1px solid blue">         
        	<!-- 每次要加载数据的数据块-->
		    载数据的数据块每次要加载数据的数据块每次要加载数据的数据块每次要加载数据的数据块每次要加载数据的数据块
		    载数据的数据块每次要加载数据的数据块每次要加载数据的数据块每次要加载数据的数据块每次要加载数据的数据块
		    载数据的数据块每次要加载数据的数据块每次要加载数据的数据块每次要加载数据的数
		    载数据的数据块每次要加载数据的数据块每次要加载数据的数据块每次要加载数据的数据块每次要加载数据的数据块
		    载数据的数据块每次要加载数据的数据块每次要加载数据的数据块每次要加载数据的数据块每次要加载数据的数据块
		    载数据的数据块每次要加载数据的数据块每次要加载数据的数据块每次要加载数据的数据块每次要加载数据的数据块
		    载数据的数据块每次要加载数据的数据块每次要加载数据的数据块每次要加载数据的数据块每次要加载数据的数据块
		 </div>
        <div class="scroll" style="border:1px solid blue">         
        	<!-- 每次要加载数据的数据块-->
		    载数据的数据块每次要加载数据的数据块每次要加载数据的数据块每次要加载数据的数据块每次要加载数据的数据块
		    载数据的数据块每次要加载数据的数据块每次要加载数据的数据块每次要加载数据的数据块每次要加载数据的数据块
		    载数据的数据块每次要加载数据的数据块每次要加载数据的数据块每次要加载数据的数
		    载数据的数据块每次要加载数据的数据块每次要加载数据的数据块每次要加载数据的数据块每次要加载数据的数据块
		    载数据的数据块每次要加载数据的数据块每次要加载数据的数据块每次要加载数据的数据块每次要加载数据的数据块
		    载数据的数据块每次要加载数据的数据块每次要加载数据的数据块每次要加载数据的数据块每次要加载数据的数据块
		    载数据的数据块每次要加载数据的数据块每次要加载数据的数据块每次要加载数据的数据块每次要加载数据的数据块
		 </div>		    		    		    
        
        
        </div>  
    
        <div id="navigation" align="center">         <!-- 页面导航-->  
        <a href="<c:url value='/page?page=1'/>" ></a>        	
		</div>
		
		
		<div style="both:clear;heigth:150px;">底部留空白</div>
		
	    <script src="<c:url value='/plugins/jquery-1.11.2.js' />"></script>
	    <script src="<c:url value='/plugins/infinite-scroll/jquery.infinitescroll.js' />"></script>
	
	<script type="text/javascript">
	
		$(document).ready(function (){
		      $("#container").infinitescroll({  
		            navSelector: "#navigation",     //页面分页元素--成功后自动隐藏  
		            nextSelector: "#navigation a",  
		            itemSelector: ".scroll" ,             
		            animate: true,
		            extraScrollPx: 80,
		            //bufferPx: 40,
		            appendCallback: true,
		            binder: $(window)
		            //maxPage:5,
		            //dataType:"html",
		            //dataType:"json",
		            //appendCallback: true
		        },function(json,opts){
		        	var page = opts.state.currPage;
		        	console.log(page)
		        });  
		 });

	</script>
</body>
</html>
