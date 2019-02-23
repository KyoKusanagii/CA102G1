<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.live.model.*"%>
<%
    LiveService liveSvc = new LiveService();
	List<LiveVO> list = liveSvc.getLiveNow();
    pageContext.setAttribute("list",list);
%>

<html lang="en">
<html>
<head>
<%@ include file="/FrontEnd/headerFooter/head.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=BIG5">
<title>Live Broadcast</title>


    
    <script type="text/javascript">
        (function(d, s, id) {
            var js, fjs = d.getElementsByTagName(s)[0];
            if (d.getElementById(id)) return;
            js = d.createElement(s);
            js.id = id;
            js.src = 'https://connect.facebook.net/zh_TW/sdk.js#xfbml=1&version=v3.0&appId=246507402569803&autoLogAppEvents=1';
            fjs.parentNode.insertBefore(js, fjs);
        }(document, 'script', 'facebook-jssdk'));
    </script>
</head>
<body>

	<!-- header -->
	<div class="header">
		<%@ include file="/FrontEnd/headerFooter/header.jsp"%>
	</div>
	<!-- //header-->
		 
	<div class="container">
		<div style="text-align:center">
		<img alt="" src="<%=request.getContextPath()%>/FrontEnd/live/live_banner.png" >
		</div>
	</div>

	
	<div class="products" style="margin-top:0; margin-bottom:0">	 
		<div class="container">
			<button class="btn btn-primary" style="border-radius:0">直播中</button>
			<button class="btn btn-default" style="border-radius:0">直播預告</button>
			<table class="table table-hover" style="font-family: 微軟正黑體">
				<thead >
					<tr class="w3ls-header table-dark" style="color:white">
						<th class="text-left" style="padding-left:5%"><h3>直播中</h3></th>
					</tr>
				</thead>
			</table>
			
			<c:forEach var="liveVO" items="${list}">
			<div class="fb-video" 
				data-href="${liveVO.live_address}" 
				data-width="240" data-height="240" data-show-text="false"
				style="padding-left:20px; padding-right:20px; padding-bottom:25px"
			></div>
			</c:forEach>
			
			
		</div>
	</div>
	    <script src="<%=request.getContextPath()%>/FrontEnd/js/bootstrap.js "></script>
</body>
</html>