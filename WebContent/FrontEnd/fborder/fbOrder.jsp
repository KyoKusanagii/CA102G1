<%@ page contentType="text/html; charset=UTF-8" pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.item.model.*"%>
<%@ page import="com.particulars.model.*, com.limitSale.model.*"%>
<%@ page import="com.category.model.*, com.member.model.*,java.sql.*,java.text.*"%>

<%-- �����m�߱ĥ� EL ���g�k���� --%>

<%
	MemVO memvo = (MemVO) session.getAttribute("memVO");
	if (memvo == null) {
		session.setAttribute("location", request.getRequestURI());
		response.sendRedirect(request.getContextPath() + "/FrontEnd/member/login.jsp");
		return;

	}

	String mem_no = memvo.getMem_no();
	// 	session.setAttribute("login_mem", "M00001");

	ItemService itemService = new com.item.model.ItemService();
	List<ItemVO> list = itemService.getAll(mem_no);
	pageContext.setAttribute("list", list);

	//���o���O�j���B�z �A�д���service
	CategoryDAO categoryDAO = new CategoryDAO();
	List<CategoryVO> categorys = categoryDAO.getAll();
	HashMap<Integer, String> categoryClass = new HashMap<Integer, String>();

	for (CategoryVO category : categorys) {
		categoryClass.put(category.getCat_no(), category.getCat_name());
	}
	session.setAttribute("categoryClass", categoryClass);

	//���o���O�Ӷ��B�z �A�д���service
	ParticularsDAO partDAO = new ParticularsDAO();
	List<ParticularsVO> parts = partDAO.getAll();
	HashMap<Integer, String> particularClass = new HashMap<Integer, String>();

	for (ParticularsVO part : parts) {
		particularClass.put(part.getPart_no(), part.getPart_name());
	}
	session.setAttribute("particularClass", particularClass);

	//�W�[�B�z
	HashMap<Integer, String> upStatus = new HashMap<Integer, String>();
	upStatus.put(0, "�U�[");
	upStatus.put(1, "�W�[");
	session.setAttribute("upStatus", upStatus);

	LimitSaleService limitSaleSvc = new LimitSaleService();
	pageContext.setAttribute("limitSaleSvc", limitSaleSvc);

	//�o��Ҧ����S��ӫ~
	List<LimitSaleVO> limitList = limitSaleSvc.getAll();
	List<String> limitExist = new LinkedList<String>();
	for (LimitSaleVO l : limitList) {
		limitExist.add(l.getItem_no());
	}
	pageContext.setAttribute("limitExist", limitExist);

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	pageContext.setAttribute("sdf", sdf);
%>

<html>
<head>
<title>FB���q��޲z</title>
<%@ include file="/FrontEnd/headerFooter/head.jsp"%>
<!-- �䷽����]�w-->
<style type="text/css">
h3, .container {
	font-family: 'Noto Sans TC';
	font-style: normal;
	font-weight: 500;
}

img {
	border-radius: 4px;
	padding: 1px;
	width: 100px;
	height: 100px;
	box-shadow: 1px 1px 5px gray;
	ext-align: center;
	vertical-align: middle;
	margin: 10px 0 10px 0
}

img:hover {
	box-shadow: 0 0 5px 1px rgba(250, 0, 0, 0.5);
}

.row {
	margin-top: 30px;
	background-color: white;
	box-shadow: 1px 1px 5px gray
}

font {
	color: #F08080;
}
body {

    background: url(<%=request.getContextPath()%>/FrontEnd/images/bg2.jpg)no-repeat center 0px fixed;
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
</style>


<style type="text/css">
.order-detail {
	display: inline-block;
	letter-spacing: normal;
	word-spacing: normal;
	vertical-align: top;
	text-rendering: auto;
	margin-top: 10px;
}
</style>

</head>


</head>
<body>
	<!-- header -->
	<div id="header">
		<%@ include file="/FrontEnd/headerFooter/header1.jsp"%>
	</div>


	<!-- tab -->
	<ul class="nav nav-tabs">
	  <li role="presentation"><a href="<%=request.getContextPath()%>/FrontEnd/item/ItemAll.jsp"> <i class="glyphicon glyphicon-shopping-cart"></i>�ӫ~�޲z</a></li>
	  <li role="presentation" class="active"><a href="<%=request.getContextPath()%>/FrontEnd/fborder/fbOrder.jsp"><i class="glyphicon glyphicon-thumbs-up"></i>fb�q��޲z</a></li>
	  <li role="presentation"><a href="<%=request.getContextPath()%>/FrontEnd/mallorder/mallOrder.jsp"><i class="glyphicon glyphicon-usd"></i>�ӫ��q��޲z</a></li>
	  <li role="presentation"><a href="<%=request.getContextPath()%>/FrontEnd/live/trailer_show.jsp"><i class="glyphicon glyphicon-phone"></i>�����w�i�޲z</a></li>
	</ul>
	<h3 class="w3ls-title1" align="center" style="font-weight: bold;color: white;margin-top: 15px">
		<i class="fa fa-facebook-square"></i>
		Facebook�������q��޲z
	</h3>
	<!-- 	�_�l�I		 -->
	<jsp:useBean id="fbOrderSvc" scope="page" class="com.fborder.model.FbOrderService" />
	<jsp:useBean id="encoder" scope="page" class="sun.misc.BASE64Encoder" />
	<jsp:useBean id="memSvc" scope="page" class="com.member.model.MemService" />

	<div class="container" style="min-height: 500px">
	<c:choose>
		<c:when test="${!empty fbOrderSvc.getMyFBOrder(memVO.mem_no)}">
		<c:forEach var="fbOrderVO" items="${fbOrderSvc.getMyFBOrder(memVO.mem_no)}">
			<div class="row" style="border-width: 1px; border-color: #dcdcdc; border-style: solid;">
<!-- ===================���b�� ====================== -->
				<div class="col-md-3">
					<div class="oreder-info">
						<div style="text-align: right;">
							<button class="btn btn-danger" id="${fbOrderVO.fb_order_no}" style="margin-right:-15px; ${fbOrderVO.fb_order_status=='�i�椤'? ' ' : 'display:none'}">
								�����q��
								<i class="fa fa-times-circle-o"></i>
							</button>
						</div>
						<div style="color: #6495ED;">�q�榨�߮ɶ�</div>
						<div style="font-size: 22px;font-weight: bold">&emsp; ${sdf.format(fbOrderVO.fb_order_time)}</div>
						<font>�q��s��</font>
						<h4><a href="<%=request.getContextPath()%>/FrontEnd/fborder/fbOrder.do?action=showSellerFbOrder&fb_order_no=${fbOrderVO.fb_order_no}">&emsp; ${fbOrderVO.fb_order_no}</a></h4>
						<font>�R�a�b��</font>
						<h4><a href="<%=request.getContextPath()%>/FrontEnd/query.do?action=findOneMall&memNo=${fbOrderVO.fb_buyer_no}" target="_blank">&emsp; ${memSvc.findByPK(fbOrderVO.fb_buyer_no).mem_id}
						(${memSvc.findByPK(fbOrderVO.fb_buyer_no).mem_name})
						</a></h4>
						<font>�B�e�覡</font>
						<h4>&emsp; ${fbOrderVO.fb_transport}</h4>
						<font>�I�ڤ覡</font>
						<h4>&emsp; ${fbOrderVO.fb_order_trans}</h4>
						<font>�q����B</font>
						<h4 style="margin-bottom: 10px">&emsp; $${fbOrderVO.fb_order_prc}</h4>
					</div>
				</div>
				
<!-- ===================�k�b�� ====================== -->

				<div class="col-md-9">
					<div class="order-detail">
						<div style="background-color: #FFEFD5; font-size: 20px; font-weight: bold">
							�q�檬�A:
							<span id="${fbOrderVO.fb_order_no}-order-status" style="color:${fbOrderVO.fb_order_status=='����'? 'red': (fbOrderVO.fb_order_status=='�i�椤'? 'blue' : 'green')};">${fbOrderVO.fb_order_status}</span>
							&emsp;�I�ڪ��A:
							<span id="pay-status" style="color:${fbOrderVO.fb_pay_status=='���I��'? 'red': (fbOrderVO.fb_pay_status=='�i�椤'? '#7B3B8C' : 'green')};">${fbOrderVO.fb_pay_status}</span>


						</div>
						<div style="background-color: #DCDCDC">
							<div style="width: 70%; text-align: center; display: inline-block;">�ӫ~</div>
							<div style="width: 12%; text-align: center; display: inline-block; margin-left: 3px">�ƶq</div>
							<div style="width: 8%; text-align: center; display: inline-block; margin-left: 2px">���</div>
							<div style="width: 7%; text-align: center; display: inline-block; margin-left: 2px">fb�d��</div>
						</div>
						<c:forEach var="buyerOrderVO" items="${fbOrderSvc.getAllOrderItem(fbOrderVO.fb_order_no)}">
							<div>
								<a href="<%=request.getContextPath()%>/FrontEnd/query.do?action=findOneItem&itemNo=${buyerOrderVO.item_no}" target="_blank">
									<div style="display: inline-block;">
										<c:choose>
											<c:when test="${empty buyerOrderVO.item_pic}">
												<img src="<%=request.getContextPath()%>/FrontEnd/images/no_image.jpg">
											</c:when>
											<c:otherwise>
												<img src="data:image/*;base64,${encoder.encode(buyerOrderVO.item_pic)}">
											</c:otherwise>
										</c:choose>
									</div>
									<div style="display: inline-block; vertical-align: text-top;">
										<div style="float: left; margin-left: 20px; width: 220px; height: 60px;">${buyerOrderVO.item_name}</div>
										<div style="float: left; margin-left: 20px; width: 220px; height: 60px;">${buyerOrderVO.item_description}</div>
								</a>
										<div style="float: left; margin-left: 20px; width: 50px; height: 60px;">${buyerOrderVO.item_cnt}</div>
										<div style="float: left; margin-left: 20px; width: 50px; height: 60px;">${buyerOrderVO.item_prc}</div>
										<div style="float: left; margin-left: 20px; width: 50px; height: 60px;">${buyerOrderVO.item_comment}</div>
									</div>
							</div>
						</c:forEach>
	
					</div>
				</div>
			</div>
			
			
	<script type="text/javascript" >
	$(function () {
	    $('#${fbOrderVO.fb_order_no}').on('click',function(){
	     if(window.confirm('�T�w�n���������q��?')){
	         var action = "cancel_order";
	         var fb_order_no = "${fbOrderVO.fb_order_no}";
	         var fb_seller = "${memVO.mem_no}";
	      $.ajax({
	       url : "<%=request.getContextPath()%>/FrontEnd/fborder/fbOrder.do",
	       method : "POST",
	       data : {
	        action:action,
	        fb_order_no:fb_order_no,
	        fb_seller:fb_seller,
	       }
	       });
	      alert("�w���\���������q��A�óq���R�a�T�{");
	      $('#${fbOrderVO.fb_order_no}').hide();
	      $("#${fbOrderVO.fb_order_no}-order-status").text("����").css("color","red");
	      return true;    
	      }else{
	    	 
	    	  return false;
	    	  }
	     });
	 });
	</script>	
	</c:forEach>
</div>
</c:when>
<c:otherwise>
<div style="color:white;text-align: center;">�d�L����q����</div>
</c:otherwise>
</c:choose>
</div>
								
								



	<div id="footer">
		<%@ include file="/FrontEnd/headerFooter/footer.html"%>
	</div>
	<script src="<%=request.getContextPath()%>/FrontEnd/js/bootstrap.js "></script>
</body>
</html>