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
	
	String mem_no = memVO.getMem_no();
	
	FbOrderService fborderSvc = new FbOrderService();
	List<FbOrderVO> list = fborderSvc.getBuyFbOrder(mem_no);
	pageContext.setAttribute("list", list);
	
%>

<jsp:useBean id="MemService" scope="page" class="com.member.model.MemService" /> 
<jsp:useBean id="FbOrderService" scope="page" class="com.fborder.model.FbOrderService" /> 
<jsp:useBean id="ItempicService" scope="page" class="com.itempic.model.ItempicService" /> 
<jsp:useBean id="encoder" scope="page" class="sun.misc.BASE64Encoder" />


<!DOCTYPE html>
<html lang="en">
<head>
<title>InstaBuy</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="" />
<script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false);
		function hideURLbar(){ window.scrollTo(0,1); } </script>
<!-- Custom Theme files -->
<link href="<%=request.getContextPath()%>/FrontEnd/css/bootstrap.css" rel="stylesheet" type="text/css" media="all" />
<link href="<%=request.getContextPath()%>/FrontEnd/css/style.css" rel="stylesheet" type="text/css" media="all" /> 
<link href="<%=request.getContextPath()%>/FrontEnd/css/menu.css" rel="stylesheet" type="text/css" media="all" /> <!-- menu style --> 
<link href="<%=request.getContextPath()%>/FrontEnd/css/ken-burns.css" rel="stylesheet" type="text/css" media="all" /> <!-- banner slider --> 
<link href="<%=request.getContextPath()%>/FrontEnd/css/animate.min.css" rel="stylesheet" type="text/css" media="all" /> 
<link href="<%=request.getContextPath()%>/FrontEnd/css/owl.carousel.css" rel="stylesheet" type="text/css" media="all"> <!-- carousel slider -->  
<!-- //Custom Theme files -->
<!-- font-awesome icons -->
<link href="<%=request.getContextPath()%>/FrontEnd/css/font-awesome.css" rel="stylesheet">
<!-- //font-awesome icons -->
<!-- js -->
<script src="<%=request.getContextPath()%>/FrontEnd/js/jquery-2.2.3.min.js"></script> 
<!-- //js --> 
<!-- web-fonts -->
<link href='https://fonts.googleapis.com/css?family=Roboto+Condensed:400,300,300italic,400italic,700,700italic' rel='stylesheet' type='text/css'>
<link href='https://fonts.googleapis.com/css?family=Lovers+Quarrel' rel='stylesheet' type='text/css'>
<link href='https://fonts.googleapis.com/css?family=Offside' rel='stylesheet' type='text/css'>
<link href='https://fonts.googleapis.com/css?family=Tangerine:400,700' rel='stylesheet' type='text/css'>
<!-- web-fonts --> 
<script src="<%=request.getContextPath()%>/FrontEnd/js/owl.carousel.js"></script>  
<script>
$(document).ready(function() { 
	$("#owl-demo").owlCarousel({ 
	  autoPlay: 3000, //Set AutoPlay to 3 seconds 
	  items :4,
	  itemsDesktop : [640,5],
	  itemsDesktopSmall : [480,2],
	  navigation : true
 
	}); 
}); 
</script>
<script src="<%=request.getContextPath()%>/FrontEnd/js/jquery-scrolltofixed-min.js" type="text/javascript"></script>
<script>
    $(document).ready(function() {

        // Dock the header to the top of the window when scrolled past the banner. This is the default behaviour.

        $('.header-two').scrollToFixed();  
        // previous summary up the page.

        var summaries = $('.summary');
        summaries.each(function(i) {
            var summary = $(summaries[i]);
            var next = summaries[i + 1];

            summary.scrollToFixed({
                marginTop: $('.header-two').outerHeight(true) + 10, 
                zIndex: 999
            });
        });
    });
</script>
<!-- start-smooth-scrolling -->
<script type="text/javascript" src="<%=request.getContextPath()%>/FrontEnd/js/move-top.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/FrontEnd/js/easing.js"></script>	
<script type="text/javascript">
		jQuery(document).ready(function($) {
			$(".scroll").click(function(event){		
				event.preventDefault();
				$('html,body').animate({scrollTop:$(this.hash).offset().top},1000);
			});
		});
</script>
<!-- //end-smooth-scrolling -->
<!-- smooth-scrolling-of-move-up -->
	<script type="text/javascript">
		$(document).ready(function() {
		
			var defaults = {
				containerID: 'toTop', // fading element id
				containerHoverID: 'toTopHover', // fading element hover id
				scrollSpeed: 1200,
				easingType: 'linear' 
			};
			
			$().UItoTop({ easingType: 'easeOutQuart' });
			
		});
	</script>
	<!-- //smooth-scrolling-of-move-up -->
<script src="<%=request.getContextPath()%>/FrontEnd/js/bootstrap.js"></script>	



</head>
<body>
	<!-- header -->
	<div class="header">
		<jsp:include page="/FrontEnd/headerFooter/header.jsp"/>
	</div><!-- //header-->
			

	
	

	
	<!-- cart-js -->
	<script src="<%=request.getContextPath()%>/FrontEnd/js/minicart.js"></script>
	<script>
        w3ls.render();

        w3ls.cart.on('w3sb_checkout', function (evt) {
        	var items, len, i;

        	if (this.subtotal() > 0) {
        		items = this.items();

        		for (i = 0, len = items.length; i < len; i++) {
        			items[i].set('shipping', 0);
        			items[i].set('shipping2', 0);
        		}
        	}
        });
    </script>  
	<!-- //cart-js -->	
	
	<!-- menu js aim -->
	<script src="<%=request.getContextPath()%>/FrontEnd/js/jquery.menu-aim.js"> </script>
	<script src="<%=request.getContextPath()%>/FrontEnd/js/main.js"></script> <!-- Resource jQuery -->
	<!-- //menu js aim --> 
	<!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster --> 
	
	
	
		<div class="row" style="font-family:微軟正黑體; font-weight:bold">
			<div class="col-12 col-md-2" >
				<ul class="nav nav-pills nav-stacked">
				  <li role="presentation" class="text-center"><a href="profile.jsp"><h4>會員資料</h4></a></li>
				  <li role="presentation" class="text-center"><a href="followMem.jsp"><h4>我的最愛</h4></a></li>
				  <li role="presentation" class="active text-center"><a href="#"><h4>訂單查詢</h4></a></li>
				</ul>
			</div>
			
		
			
			<div class="container col-md-8">
				
				      

				<ul class="nav nav-tabs">
				  <li><a href="mem_mallOrder.jsp">商城訂單</a></li>
				  <li class="active"><a  href="#">FB訂單</a></li>	 
				</ul>
				
				<div class="tab-content" style="margin-top:20px">
				  <div  class="tab-pane fade in active">
				 	
				 	<c:forEach var="fborderVO" items="${list}">
				 	<div class="col-md-8 col-md-offset-2" style="resize:none; box-shadow: 1px 1px 8px black; margin-top:10px" >
				 		<table class="table">
					 		<tr>
					 			<td>
					 				<div class="text-align-center" style="Display:inline-block;float: left; margin-left: 20px;">
					 					<img src="data:image/*;base64,${MemService.findByPK(FbOrderService.getOrderSeller(fborderVO.fb_order_no)).profilepicEncoded}" class="img-responsive img-circle" style="height:50px; width:50px">
					 				
					 				</div>
					 				<div style="Display:inline-block;float: left; margin-left: 20px; margin-top:15px">
					 					<font>${MemService.findByPK(FbOrderService.getOrderSeller(fborderVO.fb_order_no)).mem_id} (${MemService.findByPK(FbOrderService.getOrderSeller(fborderVO.fb_order_no)).mem_name})</font>
					 				</div>
					 				<div class="text-right" style="margin-left:80%; margin-top:15px; margin-right:20px" >
					 					${fborderVO.fb_pay_status}
					 				</div>
					 			
					 			</td>
					 			
					 		</tr>
					 		<tr>
					 			<td>
					 			
					 				<c:forEach var="buyerorderVO" items="${FbOrderService.getAllOrderItem(fborderVO.fb_order_no)}">
					 				<div class="row">
						 				<div style="Display:inline-block;float: left; margin-left: 20px;">
						 					<img src="data:image/*;base64,${encoder.encode(buyerorderVO.item_pic)}" class="img-responsive " style="height:80px; width:80px">
						 					
						 				</div>
						 				<div style="Display:inline-block;float: left; margin-left: 20px; margin-top:25px">
						 					 <font>${buyerorderVO.item_name}</font><br>
						 					 <font>$${buyerorderVO.item_prc}</font>
						 				</div>
						 				<div  style="Display:inline-block;float:right; margin-top:25px;margin-right:20px">
						 					 <font>x ${buyerorderVO.item_cnt}</font>
						 				</div>
					 				</div>
					 				</c:forEach>
					 				
					 			</td>
					 		</tr>
					 		<tr>
					 			<td>
					 				<div style="float:right;">
					 					應付總額  : $ ${fborderVO.fb_order_prc}
					 				</div>
					 			</td>
					 		</tr>
					 		<tr>
					 			<td>
					 				<c:if test="${fborderVO.fb_order_status eq '進行中'}">
						 				<div style="Display:inline-block;float: left; margin-left: 20px; color:blue;" class="fb_order_status">
						 					${fborderVO.fb_order_status}
						 				</div>
					 				</c:if>
					 				<c:if test="${fborderVO.fb_order_status eq '已完成'}">
						 				<div style="Display:inline-block;float: left; margin-left: 20px; color:green;" class="fb_order_status">
						 					${fborderVO.fb_order_status}
						 				</div>
					 				</c:if>
					 				<c:if test="${fborderVO.fb_order_status eq '取消'}">
						 				<div style="Display:inline-block;float: left; margin-left: 20px; color:red;" class="fb_order_status">
						 					${fborderVO.fb_order_status}
						 				</div>
					 				</c:if>
					 				
					 				
					 				<div style="Display:inline-block; float: right;">
					 					<fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${fborderVO.fb_order_time}" />
					 				</div>
					 			</td>
					 		</tr>
					 		<tr>
					 			
						 			<td class="text-right">
						 			
						 				<c:if test="${!(fborderVO.fb_order_status eq '已完成' || fborderVO.fb_order_status eq '取消' || fborderVO.fb_pay_status eq '已付款')}"> 
							 				<div style="Display:inline-block;">
								 				<button class="btn btn-danger cancel" id="${fborderVO.fb_order_no}" >取消訂單</button>
							 				</div>
						 				</c:if>
						 			<c:choose>
						 				<c:when test="${fborderVO.fb_order_trans eq '未選擇' &&  !(fborderVO.fb_order_status eq '取消') && !(fborderVO.fb_pay_status eq '已付款')}">
						 					<div style="Display:inline-block;">
								 				<form method="post" action = "<%=request.getContextPath()%>/FrontEnd/fborder/fbOrder.do">
								 					<button type="submit" class="btn btn-success">去買單</button>
								 					<input type="hidden" name="action" value="check">
								 					<input type="hidden" name="fb_order_no" value="${fborderVO.fb_order_no}">
								 			    </form>
						 					</div>
						 				</c:when>
						 				
						 				<c:otherwise>
						 					<div style="Display:inline-block;">
								 				<form method="post" action = "<%=request.getContextPath()%>/FrontEnd/fborder/fbOrder.do">
								 					<button type="submit" class="btn btn-warning">查看詳情</button>
								 					<input type="hidden" name="action" value="showOneFbOrder">
								 					<input type="hidden" name="fb_order_no" value="${fborderVO.fb_order_no}">
								 			    </form>
						 					</div>
						 				</c:otherwise>
						 			</c:choose>
						 			
						 		</td>
					 			
					 		</tr>
				 		</table>
				 	</div>
				 	</c:forEach>
				 	
				  </div>
				</div>
          
			</div>
		</div>
		
		<!-- footer -->
		<div style="margin-top: 10%; ">
		<%@ include file="/FrontEnd/headerFooter/footer.html"%> 
		</div>
	
		
	
</body>
	<script type="text/javascript" >
	

	$(document).ready(function () {
	 
      $(".cancel").click(function(){
    	  if(window.confirm('確定要取消此筆訂單?')){
 	         var action = "buyer_cancel_order";
 	         var fb_order_no = $(this).attr("id");
 	         var fb_buyer_no = "${memVO.mem_no}";
 	         

		     $.ajax({
			     url : "<%= request.getContextPath()%>/FrontEnd/fborder/fbOrder.do",
			     method : "POST",
			     data : {
		    		action:action,
			        fb_order_no:fb_order_no,
			        fb_buyer_no:fb_buyer_no 
		     	},
		    
		    });
		    alert("已成功取消此筆，並通知賣家家");
		      $(this).hide();
		      $(this).parent().parent().parent().prev().children().children("div.fb_order_status").text("取消").css("color","red");
	       	  $(this).parent().parent().html("<div style='Display:inline-block;'><form method='post' action = '<%=request.getContextPath()%>/FrontEnd/fborder/fbOrder.do'><button type='submit' class='btn btn-warning'>查看詳情</button><input type='hidden' name='action' value='showOneFbOrder'><input type='hidden' name='fb_order_no' value='${fborderVO.fb_order_no}'></form></div>")
		  }
	   });
	  });
	</script>

</html>