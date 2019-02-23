<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.member.model.*"%>
<%@ page import="com.live.model.*"%>
<%
    LiveService liveListSvc = new LiveService();
	List<LiveVO> liveList = liveListSvc.getLiveNow();
    pageContext.setAttribute("liveList",liveList);
%>
<%
	MemVO memVO = (MemVO) request.getSession().getAttribute("memVO");
	String login, logout;
	if (memVO != null) {
		login = "display:none;";
		logout = "display:'';";
	} else {
		login = "display:'';";
		logout = "display:none;";
	}

	boolean login_state = false;
	Object login_state_temp = request.getSession().getAttribute("login_state");
	if (login_state_temp != null) {
		login_state = (boolean) login_state_temp;
	}
%>

<% if(memVO != null) {%>
<script type="text/javascript">
		var MyPoint = "/NotifySocket/<%=memVO.getMem_no()%>";
		var host = window.location.host;
		var path = window.location.pathname;
		var webCtx = path.substring(0, path.indexOf('/', 1));
		var endPointURL = "wss://" + window.location.host + webCtx + MyPoint;
		
		var statusOutput = document.getElementById("statusOutPut");
		var webSocket;

		
		//���o�e�����q����
		if(localStorage.getItem("countNotify", countNotify) ===null){
			var countNotify = 0;
		}else{
			var countNotify = localStorage.getItem("countNotify", countNotify);

		}

	
		
<%-- 		<%request.getSession().setAttribute("countNotify",countNotify);%> --%>
		
		
		function connect(){
			
			//���o�e�������q��
			if(countNotify != 0){
				$("#onair-count").html(countNotify);
			}else{
				$("#onair-count").html("");
			}
	
			
			console.log(endPointURL);
			webSocket = new WebSocket(endPointURL);
			
			
			webSocket.onopen = function(event) {
				console.log(endPointURL);
				getHistory();
				
			};

			webSocket.onmessage = function(event) {
				let jsonObj = JSON.parse(event.data);
				
				
				
				if(jsonObj.type == "history"){
					//list�C�Ӹ̭��]�@��josn�r��
					let historyMsg = JSON.parse(jsonObj.notifyMessage);
					$.each(historyMsg, function(index, eachHistory){
						let hiscontent = JSON.parse(eachHistory);
						$("#notify").prepend("<li><a href="+ hiscontent.liveAddress +" >"+ hiscontent.sender+ hiscontent.notifyMessage+"<br>" + hiscontent.notifyTime +  "</a><br></li>");
					})
				}else{
					countNotify ++;
					localStorage.setItem("countNotify", countNotify);
	
		
					$("#notify").prepend("<li><a href="+ jsonObj.liveAddress +" >"+ jsonObj.sender+ jsonObj.notifyMessage+"<br>" + jsonObj.notifyTime +  "</a><br></li>");
					console.log(countNotify);
					
					$("#onair-count").html(countNotify);
				}				
	
			};

			webSocket.onclose = function(event) {
				
			};
		}		

		function disconnect() {
			webSocket.close();
		}
		

		function notifytozero(){
			countNotify = 0;
			console.log(countNotify);
			
			//�N�q�����x�s
			localStorage.setItem("countNotify", countNotify);
			
			$("#notifycount").val(countNotify);
			$("#onair-count").html("");
		}
		
		
		function getHistory() {
			
			var jsonObj = {
				"notifyList"	: "",
			  	"sender"		: "<%=memVO.getMem_no()%>",
			 	"notifyTitle"	: "",
			 	"notifyMessage"	: "�����}�l�y!!!",
			 	"notifyTime"    : "",
			 	"liveAddress"	: "",
			 	"type"			: "history"
			};
			webSocket.send(JSON.stringify(jsonObj));
		}
		
		
// 		<c:if test="${TrailNotify} != null">
// 			webSocket.send(${TrailNotify});
// 		</c:if>
		
		window.onbeforeunload = function(){
			   disconnect();
		}
		
		
	</script>
<%}%>



<style onload="connect()"></style>


<div class="w3ls-header" style="font-family:Microsoft JhengHei">
	<!--header-one-->
	<div class="w3ls-header-left">
		<ul>
			<li class="dropdown head-dpdn">
			<a href="<%=request.getContextPath()%>/FrontEnd/index.jsp"><img src="<%=request.getContextPath()%>/FrontEnd/images/logo.png" style="width:30px; height:30px" title="����"></a>
			<a href="<%=request.getContextPath()%>/BackEnd/BackEnd.jsp" class="dropdown-toggle" style="color:white"><i class="fa fa-desktop"></i> ��x�޲z</a></li>
		</ul>
	</div>
	<div class="w3ls-header-right">
		<ul>
			<li class="dropdown head-dpdn"><a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-laptop" aria-hidden="true"></i>��a����<span
					class="caret"></span></a>
				<ul class="dropdown-menu">
					<li><a href="<%=request.getContextPath()%>/FrontEnd/seller/SellerPage.jsp">���</a></li>
					<li><a href="<%=request.getContextPath()%>/FrontEnd/item/ItemAll.jsp">�ӫ~�޲z</a></li>
					<li><a href="<%=request.getContextPath()%>/FrontEnd/fborder/fbOrder.jsp">�q��޲z</a></li>
					<li><a href="<%=request.getContextPath()%>/FrontEnd/live/trailer_show.jsp">�����w�i�޲z</a></li>
					<li><a href="<%=request.getContextPath()%>/FrontEnd/live/livestartnotice.jsp">�����֤�</a></li>
				</ul></li>
			<li onclick="notifytozero()" class="dropdown head-dpdn"><a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-bullhorn" aria-hidden="true"></i>�����w�i
					<span class="badge" id="onair-count" style="background: #FF0000">N</span></a>
				<ul class="dropdown-menu" id="notify">
<!-- 					<li><a href="offers.html">�w�i�@</a></li> -->
<!-- 					<li><a href="offers.html">�w�i�G</a></li> -->
<!-- 					<li><a href="offers.html">�w�i�T</a></li> -->
				</ul></li>
			<li class="dropdown head-dpdn"><a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-bell" aria-hidden="true"></i>���b����
					<span class="badge" id="onair-count" style="background: #FF0000">${liveList.size()}</span></a>
				<ul class="dropdown-menu">
					<c:forEach var="liveVO" items="${liveList}" begin="<%= 0%>" end="<%= 2%>">
					<li><a href="${liveVO.live_address}">${liveVO.live_seller_no}������</a></li>
					</c:forEach>
					<li><a href="<%=request.getContextPath()%>/FrontEnd/live/fblive.jsp">��h...</a></li>
				</ul></li>
			<li class="dropdown head-dpdn"><a href="<%=request.getContextPath()%>/FrontEnd/forum/forum.jsp" class="dropdown-toggle"><i
					class="fa fa-columns" aria-hidden="true"></i>�Q�װ�</a></li>
			<li class="dropdown head-dpdn"><a href="<%=request.getContextPath()%>/FrontEnd/chat/Chat.jsp" class="dropdown-toggle"><i
					class="fa fa-wechat" aria-hidden="true"></i> ���</a></li>
			<li class="dropdown head-dpdn"><a href="<%=request.getContextPath()%>/FrontEnd/news/newsBrowse.jsp" class="dropdown-toggle"><i class="fa fa-newspaper-o" aria-hidden="true"></i> �̷s����</a></li>

			<li class="dropdown head-dpdn"><a href="<%=request.getContextPath()%>/FrontEnd/member/shoppingCart.jsp" class="dropdown-toggle"><i class="glyphicon glyphicon-shopping-cart" aria-hidden="true"></i> �ʪ���</a></li>		

			<li class="dropdown head-dpdn" style="<%=login%>"><a href="<%=request.getContextPath()%>/FrontEnd/member/login.jsp" class="dropdown-toggle"><i
					class="glyphicon glyphicon-log-in" aria-hidden="true"></i> �n�J</a></li>

			<li class="dropdown head-dpdn" style="<%=logout%>"><a href="#" class="dropdown-toggle" data-toggle="dropdown">
			<% 
				if(memVO != null){
					String picture = memVO.getProfilepicEncoded();	
					if(picture != null){ 
				%>
				<img src="data:image/*;base64,${memVO.profilepicEncoded}" class="img-circle" style="height: 30px; width: 30px; margin:auto">
				<%}else{ %>
					<img src="<%=request.getContextPath()%>/FrontEnd/images/visitor.jpg" class="img-circle" style="height: 30px; width: 30px; margin:auto">
					<%}%>
				<%}%>
				${memVO.mem_name}
			</a>		
				
				<ul class="dropdown-menu">
					<li><a href="<%=request.getContextPath()%>/FrontEnd/member/profile.jsp">�ӤH���</a></li>
					<li><a href="<%=request.getContextPath()%>/FrontEnd/member/member.do?action=logout">�n�X</a></li>
				</ul>
			</li>
			


		</ul>
	</div>
	<div class="clearfix"></div>
</div>




