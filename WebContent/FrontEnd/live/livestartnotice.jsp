<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.item.model.*"%>
<%@ page import="com.particulars.model.*"%>
<%@ page import="com.category.model.*"%>
<%@ page import="com.follow_mem.model.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="com.google.gson.Gson"%>

<%

	MemVO memvo = (MemVO) session.getAttribute("memVO");
	if(memvo == null){
		session.setAttribute("location",request.getRequestURI());
		response.sendRedirect(request.getContextPath()+"/FrontEnd/member/login.jsp");
		return;
		
	}
	
	ItemVO itemVO = (ItemVO) request.getAttribute("itemVO");
	
	MemVO LiveingMemVO = (MemVO)session.getAttribute("memVO");
	
	Follow_memService service = new Follow_memService();
	
	List<MemVO> followList = service.getAllFans(LiveingMemVO.getMem_no());
	
	Gson gson = new Gson();
	String jsonFollowList = gson.toJson(followList);
	
	Timestamp nowTimestamp = new Timestamp(System.currentTimeMillis());  

%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<title>�����]�w</title>

<%@ include file="/FrontEnd/headerFooter/head.jsp"%>
<!-- �䷽����]�w-->
<style type="text/css">
.login-body {
	width: 75%;
	font-family: 'Noto Sans TC';
	font-style: normal;
	font-weight: 500;
  	box-shadow: 1px 1px 8px black; 
}

h3 {
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
	box-shadow: 1px 1px 5px red;
}
</style>

</head>
<body onload="connect()" onunload="disconnect()">
	<!-- header -->
	<div id="header">
		<%@ include file="/FrontEnd/headerFooter/header.jsp"%>
	</div>

	<div class="container">


		<h3 class="w3ls-title1" align="center">�����]�w</h3>

		<%-- ���~��C --%>
		<c:if test="${not empty errorMsgs}">
			<font style="color: red">�Эץ��H�U���~:</font>
			<ul>
				<c:forEach var="message" items="${errorMsgs}">
					<li style="color: red">${message}</li>
				</c:forEach>
			</ul>
		</c:if>
		<div class="login-body">
			<FORM class="form-horizontal" METHOD="post" ACTION="LiveStartNotify" name="form1">
				<div class="form-group">
					<table class="table">
						<tr>
							<h3>${memVO.mem_name}�A�n!!</h3>
						
						</tr>
						<tr>
							<td>�������}:</td>
							<td>
								<input type="TEXT" id = "liveAddress" name="liveAddress" size="45" placeholder="�п�JFacebook�������}" required="required"/>
							</td>
						</tr>
						
					</table>
					<br>
					<input type="hidden" name="nowTimestamp" value="<%= nowTimestamp %>">
					<div align="center">
						<input class="btn btn-success btn-lg " type="submit" value="�}�l����" onclick ="sendMessage()">
						<input class="btn btn-danger btn-lg " type="button" value="��������" onclick="javascript:history.back(1)">
						<input type="hidden" name="requestURL" value="<%=request.getContextPath()%>">
				
		
					</div>
				</div>
			</FORM>
		</div>
	</div>
	<div id="footer">
		<%@ include file="/FrontEnd/headerFooter/footer.html"%>
	</div>

	<script type="text/javascript">
<%-- 		var MyPoint = "/NotifySocket/<%=memVO.getMem_no()%>"; --%>
// 		var host = window.location.host;
// 		var path = window.location.pathname;
// 		var webCtx = path.substring(0, path.indexOf('/', 1));
// 		var endPointURL = "ws://" + window.location.host + webCtx + MyPoint;
		
// 		var statusOutput = document.getElementById("statusOutPut");
// 		var webSocket;
		
// 		function connect(){
// 			console.log(endPointURL);
// 			webSocket = new WebSocket(endPointURL);
			
			
// 			webSocket.onopen = function(event) {
				
// 			};

// 			webSocket.onmessage = function(event) {
				
// 			};

// 			webSocket.onclose = function(event) {
				
// 			};
// 		}
		

		
		function sendMessage() {
			
				var jsonObj = {
					"notifyList"	: <%= jsonFollowList %>,
				  	"sender"		: "<%=memVO.getMem_name()%>",
				 	"notifyTitle"	: "<%=memVO.getMem_name()%>�}�l����",
				 	"notifyMessage"	: "�����}�l�y!!!",
				 	"notifyTime"    : "<%=nowTimestamp%>",
				 	"liveAddress"	: $("#liveAddress").val(),
				 	"type"			: "notify"
				};
				webSocket.send(JSON.stringify(jsonObj));
		}

// 		function disconnect() {
// 			webSocket.close();
// 		}
		
		
		
		
		
		
	</script>
	<script src="<%=request.getContextPath()%>/FrontEnd/js/select.js "></script>
	<script src="<%=request.getContextPath()%>/FrontEnd/js/bootstrap.js "></script>
</body>

</html>