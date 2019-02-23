<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*, com.member.model.*, com.fborder.model.*, com.android.model.*"%>




<% 

	MemVO memVO = (MemVO)session.getAttribute("memVO");

	if(memVO == null){
		session.setAttribute("location", request.getRequestURI());
		response.sendRedirect(request.getContextPath() + "/FrontEnd/member/login.jsp");
		return;
	}
	
// 	String mem_no = memVO.getMem_no();
	String fb_order_no = request.getParameter("fb_order_no");
	FbOrderService fborderSvc = new FbOrderService();
	FbOrderVO fborderVO = fborderSvc.getOneOrder(fb_order_no);
	pageContext.setAttribute("fborderVO", fborderVO);
	
%>

<jsp:useBean id="MemService" scope="page" class="com.member.model.MemService" /> 
<jsp:useBean id="FbOrderService" scope="page" class="com.fborder.model.FbOrderService" /> 
<jsp:useBean id="ItempicService" scope="page" class="com.itempic.model.ItempicService" /> 



<!DOCTYPE html>
<html lang="en">
<head>
<title>InstaBuy</title>
<%@ include file="/FrontEnd/headerFooter/head.jsp"%>
<script src="<%=request.getContextPath()%>/FrontEnd/js/bootstrap.js"></script>	

<style>
body {

    background: url(<%=request.getContextPath()%>/FrontEnd/images/bg2.jpg)no-repeat center 0px fixed;
    background-size: cover;
    
}

</style>


</head>
<body>
	<!-- header -->
	<div class="header">
		<jsp:include page="/FrontEnd/headerFooter/header1.jsp"/>
	</div><!-- //header-->
			
	<div class="container" style="margin-top: 10%;">
	<div class="col-md-6 col-md-offset-3" style="font-family:微軟正黑體;resize:none; box-shadow: 1px 1px 8px black; margin-top:10px;background-color:white">
		<div class="row">
			<div style="Display:inline-block; margin-left: 20px; margin-top:15px"><font size=+3>訂單詳情</font></div>
		</div>
		<div style="margin-left:20px;margin-top:15px; font-size:20px; margin-bottom:20px;font-weight: bold">
		<div>訂單編號 : ${fborderVO.fb_order_no}</div>	
		<div></div>
		<div>收件地址 : ${MemService.findByPK(fborderVO.fb_buyer_no).mem_receiveadd}</div>
		<div>購買總金額 : $${fborderVO.fb_order_prc}</div>
		<div>買家 : ${MemService.findByPK(fborderVO.fb_buyer_no).mem_name}</div>
		<div>付款方式 : ${fborderVO.fb_order_trans}</div>
		<div>寄送方式 : ${fborderVO.fb_transport}</div>
		<div id="mall_pay_status">付款狀態: ${fborderVO.fb_pay_status}</div>
		<div id="mall_order_status">訂單狀態: ${fborderVO.fb_order_status}</div>
		</div>
		<c:if test="${fborderVO.fb_order_status eq '取消'}">
		<div class="text-center "  style="margin-top:20px; margin-bottom:20px">
			<button class='btn btn-danger btn-block' disabled><span class='glyphicon glyphicon-remove'></span> 訂單已取消</button>
		</div>
		</c:if>
		<c:if test="${fborderVO.fb_order_status eq '進行中'}">
		<div class="text-center "  style="margin-top:20px; margin-bottom:20px">
			<button class='btn btn-primary btn-block' disabled><span class='glyphicon glyphicon-play'></span> 訂單進行中</button>
		</div>
		</c:if>
		<c:if test="${fborderVO.fb_order_status eq '已完成'}">
		<div class="text-center "  style="margin-top:20px; margin-bottom:20px">
			<button class='btn btn-success btn-block' disabled><span class='glyphicon glyphicon-ok'></span> 訂單已完成</button>
		</div>
		</c:if>
	</div>
	<div class="text-center col-md-12" style="margin-top:50px"><a href="<%=request.getContextPath()%>/FrontEnd/fborder/fbOrder.jsp" class="btn btn-info">返回訂單列表</a></div>
	</div>	
		<!-- footer -->
		<div style="margin-top: 10%; ">
		<%@ include file="/FrontEnd/headerFooter/footer.html"%> 
		</div>
			
		
		
	
</body>

	

</html>