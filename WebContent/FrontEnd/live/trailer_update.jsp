<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.item.model.*"%>
<%@ page import="com.trailer.model.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.SimpleDateFormat, java.sql.*" %>
<%@ page import="com.google.gson.Gson"%>
<%@ page import="com.follow_mem.model.*"%>



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
		
		
		
		//直播預告
			ItemVO itemVO = (ItemVO) request.getAttribute("itemVO");
			
			MemVO LiveingMemVO = (MemVO)session.getAttribute("memVO");

			
			Follow_memService service = new Follow_memService();
			
			List<MemVO> followList = service.getAllFans(LiveingMemVO.getMem_no());
			

			
			Gson gson = new Gson();
			String jsonFollowList = gson.toJson(followList);
	
		
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
	
	
	function sendTrail() {
		
		var jsonObj = {
			"notifyList"	: <%= jsonFollowList %>,
		  	"sender"		: "<%=memvo.getMem_name()%>",
		 	"notifyTitle"	: "<%=memvo.getMem_name()%>要開始直播摟",
		 	"notifyMessage"	: "預計將在30鐘後開始直播!!!",
		 	"notifyTime"    : $("#StartDate").val() +"$" + $("#StartTime").val(),    
		 	"liveAddress"	: $("#liveAddress").val(),
		 	"type"			: "trail"
		};
		webSocket.send(JSON.stringify(jsonObj));

}
	
	
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

td img {
	border: 1px solid #ddd;
	border-radius: 4px;
	padding: 5px;
	width: 150px;
	height: 150px;
	box-shadow: 1px 1px 5px black;
	margin-bottom: 10px;
}

body {

    background: url(<%=request.getContextPath()%>/FrontEnd/images/bg1.jpg)no-repeat center 0px fixed;
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


	<div class="container">
		<h3 class="w3ls-title1" align="center" style="color:white;font-weight:bold;margin:15px 15px 15px 15px">
			<i class="fa fa-clock-o"></i>修改直播預告時間
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

			<FORM class="form-horizontal" METHOD="post" ACTION="<%=request.getContextPath() %>/FrontEnd/Trailer.do" name="form1">
				<div class="form-group">
					<table class="table">
						<jsp:useBean id="itemSvc" scope="page" class="com.item.model.ItemService" />
						<tr>
							<td>賣場名稱:</td>
							<td>
								 <div style="font-weight:bold; font-size:20px;color: #FF1C1C">${memVO.mem_martname}</div>
								 <input type="hidden" name="trailer_seller_no" value="${memVO.mem_no}">
								 
							</td>
						</tr>
						

<%-- 						<c:if test="${trailerExist.contains(memVO.mem_no)}"> --%>
						
						<tr>
							<td>之前預計直播時間: </td>
							<td style="font-weight:bold; font-size:18px;color: blue">
								
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
								<label for="StartDate" style="margin-top: 5px">預計直播開始日期</label>
								<br>
								<input type="text" id="StartDate" name="trailer_start_date" style="margin-bottom: 10px"  />
								<br>
								<label for="StartTime" style="margin-top: 5px">預計直播開始時間</label>
								<br>
								<input type="text" id="StartTime" name="trailer_start_time" class="timepicker" style="margin-bottom: 5px;">
								<br>
								<label for="Textarea1">直播預告內容</label>
								<i class="fa fa-pencil prefix"></i>
								<textarea id="Textarea1"  style="font-weight:bold; font-size:18px; resize: none; width: 600px; 
								height: 200px; overflow-x: visible; overflow-y: visible;" name="trailer_topic" class="form-control rounded-0"><%=(trailerSvc.findByPK(memVO.getMem_no()) == null) ? "" : trailerSvc.findByPK(memVO.getMem_no()).getTrailer_topic()%>
								</textarea>
							</td>
						</tr>

						


					</table>
					<br>
					<input type="hidden" name="action" value="update">
					<div align="center">
						<input onclick="sendTrail()" class="btn btn-success btn-lg " type="submit" value="送出">
						<input class="btn btn-danger btn-lg " type="button" value="取消" onclick="location.href='<%=request.getContextPath()%>/FrontEnd/live/trailer_show.jsp'">
						<input type="hidden" name="requestURL" value="<%=request.getParameter("requestURL")%>">
						<!--原送出修改的來源網頁路徑,從request取出後,再送給Controller準備轉交之用-->
						<input type="hidden" name="whichPage" value="<%=request.getParameter("whichPage")%>">
						<!--只用於:istAllEmp.jsp-->
						<input type="hidden" name="trailer_no" value="${trailerSvc.findByPK(memVO.mem_no).trailer_no }">
					</div>
				</div>
			</FORM>
		</div>
	</div>

	<div id="footer">
		<%@ include file="/FrontEnd/headerFooter/footer.html"%>
	</div>
	
	<script src="<%=request.getContextPath()%>/FrontEnd/js/bootstrap.js "></script>
</body>

</html>