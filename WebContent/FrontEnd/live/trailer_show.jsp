<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.item.model.*"%>
<%@ page import="com.trailer.model.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.SimpleDateFormat, java.sql.*" %>


<%
	MemVO memvo = (MemVO) session.getAttribute("memVO");
		if(memvo == null){
			session.setAttribute("location",request.getRequestURI());
			response.sendRedirect(request.getContextPath()+"/FrontEnd/member/login.jsp");
			return;
			
		}

		TrailerVO trailerVO = (TrailerVO) request.getAttribute("trailerVO");
		
		TrailerService trailerSvc = new TrailerService();
		pageContext.setAttribute("trailerSvc",trailerSvc);
		
		List<TrailerVO> trailerList = trailerSvc.getAll();
		List<String> trailerExist = new LinkedList<String>();
		for(TrailerVO t : trailerList){
			trailerExist.add(t.getTrailer_seller_no());
		}
		pageContext.setAttribute("trailerExist",trailerExist);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		pageContext.setAttribute("sdf",sdf);
		
		java.util.Date date = new java.util.Date();
		pageContext.setAttribute("date",date);
	
		
%>

<html>
<head>
<title>直播預告管理</title>
<%@ include file="/FrontEnd/headerFooter/head.jsp" %>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/FrontEnd/css/wickedpicker.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/FrontEnd/css/wickedpicker.scss">
<link rel="stylesheet" href="https://cdn.bootcss.com/titatoggle/2.1.2/titatoggle-dist-min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://code.jquery.com/jquery-1.11.3.min.js"></script>
<%@ include file="/FrontEnd/headerFooter/head.jsp" %>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="<%=request.getContextPath()%>/FrontEnd/js/wickedpicker.min.js"></script>

<!-- 思源黑體設定-->

<script>
	$(document).ready(
			function() {
				var today = new Date();
				var options = {
					//now: "12:35", //hh:mm 24 hour format only, defaults to current time 
					twentyFour : true, //Display 24 hour format, defaults to false 
					upArrow : 'wickedpicker__controls__control-up', //The up arrow class selector to use, for custom CSS 
					downArrow : 'wickedpicker__controls__control-down', //The down arrow class selector to use, for custom CSS 
					close : 'wickedpicker__close', //The close class selector to use, for custom CSS 
					hoverState : 'hover-state', //The hover state class to use, for custom CSS 
					title : '請選擇時間', //The Wickedpicker's title, 
					showSeconds : true, //Whether or not to show seconds, 
					secondsInterval : 1, //Change interval for seconds, defaults to 1  , 
					minutesInterval : 1, //Change interval for minutes, defaults to 1 
					beforeShow : null, //A function to be called before the Wickedpicker is shown 
					show : null, //A function to be called when the Wickedpicker is shown 
					clearable : true
				//Make the picker's input clearable (has clickable "x")  }; 
				};

				$("#StartTime").wickedpicker(options);
				$("#StartDate").datepicker(
						{
							showOn : "both",
							//beforeShow: customRange,
							dateFormat : "yy-mm-dd",
							minDate : today,
							maxDate : new Date(today.getFullYear(), today
									.getMonth(), today.getDate() + 365),

						});
				$(function() {
					$('#StartDate').datepicker('setDate', new Date())
				});
				$("#EndDate").datepicker(
						{
							showOn : "both",
							//beforeShow: customRange,
							dateFormat : "yy-mm-dd",
							minDate : today,
							maxDate : new Date(today.getFullYear(), today
									.getMonth(), today.getDate() + 365),

						});
				$(function() {
					var date = new Date((new Date()).valueOf() + 1000 * 3600
							* 24 * 7);
					$('#EndDate').datepicker('setDate', date);
				});

			});
</script>
<style type="text/css">
.login-body {
	width: 75%;
	font-family: 'Noto Sans TC';
	font-style: normal;
	font-weight: 500;
	box-shadow: 1px 1px 8px green;
}

h3, .w3ls-title1 {
	font-family: 'Noto Sans TC';
	font-style: normal;
	font-weight: 500;
	margin-bottom: 10px;
}


body {

    background: url(<%=request.getContextPath()%>/FrontEnd/images/bg1.jpg) no-repeat center 0px fixed;
    background-size: cover;
}

 li, .nav-tabs > li.active > a, .nav-tabs > li.active > a:hover, .nav-tabs > li.active > a:focus {
	color:white;
	font-family: 'Noto Sans TC';
	font-style: normal;
	font-weight: 500;
	font-size: 16px ;

	
}

li a {

	color:#ffaa00;

}

.wickedpicker__title{

	font-family: 'Noto Sans TC';
	font-style: normal;
	font-weight: 500;
	font-size: 16px ;
	}


td {

	font-family: 'Noto Sans TC';
	font-style: normal;
	font-weight: 500;
	font-size: 25px ;
	}

.wickedpicker__controls__control--hours, .wickedpicker__controls__control--seconds, .wickedpicker__controls__control--minutes{
	color:black;
}

</style>

</head>
<body>
	<!-- header -->
	<div id="header">
		<%@ include file="/FrontEnd/headerFooter/header1.jsp"%>
	</div>
	
		<!-- tab -->
	<ul class="nav nav-tabs">
	  <li role="presentation"><a href="<%=request.getContextPath()%>/FrontEnd/item/ItemAll.jsp"> <i class="glyphicon glyphicon-shopping-cart"></i>商品管理</a></li>
	  <li role="presentation"><a href="<%=request.getContextPath()%>/FrontEnd/fborder/fbOrder.jsp"><i class="glyphicon glyphicon-thumbs-up"></i>fb訂單管理</a></li>
	  <li role="presentation"><a href="<%=request.getContextPath()%>/FrontEnd/mallorder/mallOrder.jsp"><i class="glyphicon glyphicon-usd"></i>商城訂單管理</a></li>
	  <li role="presentation" class="active"><a href="<%=request.getContextPath()%>/FrontEnd/live/trailer_show.jsp"><i class="glyphicon glyphicon-phone"></i>直播預告管理</a></li>
	</ul>


	<div class="container" style="min-height: 500px">
		<h3 class="w3ls-title1" align="center" style="color:white;font-weight:bold;margin:15px 15px 15px 15px">
			<i class="fa fa-clock-o"></i>直播預告時間
		</h3>
			

		<%-- 錯誤表列 --%>
		<c:if test="${not empty errorMsgs}">
			<font style="color: red">請修正以下錯誤:</font>
			<ul>
				<c:forEach var="message" items="${errorMsgs}">
					<li style="color: red">${message}</li>
				</c:forEach>
			</ul>
		</c:if>
		<div class="login-body">
	<c:choose>
	<c:when test="${!empty trailerSvc.findByPK(memVO.mem_no)}">
			<FORM class="form-horizontal" METHOD="post" ACTION="<%=request.getContextPath() %>/FrontEnd/live/trailer_update.jsp" name="form1">
				<div class="form-group">
					<table class="table">
						<jsp:useBean id="itemSvc" scope="page" class="com.item.model.ItemService" />
						<tr>
							<td>賣場名稱:</td>
							<td>
								 <div style="color: #FF1C1C">${memVO.mem_martname}</div>
							</td>
						</tr>
						
						
						<tr>
							<td>預計直播時間: </td>
							<td style="color: blue">
								
								<c:choose>
								<c:when test="${trailerExist.contains(memVO.mem_no)}"> 
									${sdf.format(trailerSvc.findByPK(memVO.mem_no).getTrailer_time())}
								</c:when>

								<c:otherwise>
									暫無設定直播預告<br>
								</c:otherwise>
								</c:choose>

							</td>
						
						</tr>
						<tr>
							<td>直播預告資訊:</td>
							<td align="left">
								<%=(trailerSvc.findByPK(memVO.getMem_no()) == null) ? "" : trailerSvc.findByPK(memVO.getMem_no()).getTrailer_topic()%>
							</td>
						</tr>
						
						
						<tr><td></td>
							<td>
								<div style="text-align: right;font-size: 12px;color: #db3552">直播前30分鐘發送通知訊息</div>
							</td>
							
						</tr>



					</table>
					<br>
					<input type="hidden" name="action" value="${empty trailerSvc.findByPK(memVO.mem_no)? 'insert' : 'getOneUpdate'}">
					<div align="center">
						<input class="btn btn-success btn-lg " type="submit" value="修改">
						<input type="hidden" name="trailer_no" value="${trailerSvc.findByPK(memVO.mem_no).trailer_no }">
					</div>
				</div>
			</FORM>
			</c:when>
			
			
			
			<c:otherwise>
			<div style="text-align:center;font-size:18px ">您尚無直播預告資訊<br>
			<a href="<%=request.getContextPath()%>/FrontEnd/live/trailer_add.jsp" title="新增直播預告"><i class="glyphicon glyphicon-plus-sign" style="font-size:50px;text-align:cener;color:black"></i></a></div>
			
			</c:otherwise>
			</c:choose>
		</div>
	</div>

	<div id="footer">
		<%@ include file="/FrontEnd/headerFooter/footer.html"%>
	</div>


	
	
	<script src="<%=request.getContextPath()%>/FrontEnd/js/bootstrap.js "></script>
</body>

</html>