<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*, com.member.model.*, com.mallorder.model.*, com.android.model.*"%>




<% 

	MemVO memVO = (MemVO)session.getAttribute("memVO");

	if(memVO == null){
		session.setAttribute("location", request.getRequestURI());
		response.sendRedirect(request.getContextPath() + "/FrontEnd/member/login.jsp");
		return;
	}
	
	String mem_no = memVO.getMem_no();
	String mall_order_no = request.getParameter("mall_order_no");
	MallOrderService mallorderSvc = new MallOrderService();
	MallOrderVO mallorderVO = mallorderSvc.getOneOrder(mall_order_no);
	pageContext.setAttribute("mallorderVO", mallorderVO);
	
%>

<jsp:useBean id="MemService" scope="page" class="com.member.model.MemService" /> 
<jsp:useBean id="MallOrderService" scope="page" class="com.mallorder.model.MallOrderService" /> 
<jsp:useBean id="ItempicService" scope="page" class="com.itempic.model.ItempicService" /> 



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
	
	<div class="container">
	<div class="col-md-6 col-md-offset-3" style="font-family:微軟正黑體;resize:none; box-shadow: 1px 1px 8px black; margin-top:10px">
		<div class="row">
			<div style="Display:inline-block; margin-left: 20px; margin-top:15px"><font size=+3>訂單詳情</font></div>
		</div>
		<div style="margin-left:20px;margin-top:15px; font-size:20px; margin-bottom:20px">
		<div>訂單編號 : ${mallorderVO.mall_order_no}</div>	
		<div></div>
		<div>收件地址 : ${MemService.findByPK(mallorderVO.mall_buyer_no).mem_receiveadd}</div>
		<div>購買總金額 : $${mallorderVO.mall_order_prc}</div>
		<div>賣家 : ${MemService.findByPK(MallOrderService.getOrderSeller(mallorderVO.mall_order_no)).mem_name}</div>
		<div>付款方式 : ${mallorderVO.mall_order_trans}</div>
		<div>寄送方式 : ${mallorderVO.mall_transport}</div>
		<div id="mall_pay_status">付款狀態: ${mallorderVO.mall_pay_status}</div>
		<div id="mall_order_status">訂單狀態: ${mallorderVO.mall_order_status}</div>
		</div>
	
		<c:if test="${mallorderVO.mall_order_status eq '取消'}">
		<div class="text-center "  style="margin-top:20px; margin-bottom:20px">
			<button class='btn btn-danger btn-block' disabled><span class='glyphicon glyphicon-remove'></span> 訂單已取消</button>
		</div>
		</c:if>
		<c:if test="${mallorderVO.mall_order_status eq '進行中'}">
		<div class="text-center "  style="margin-top:20px; margin-bottom:20px">
			<button class='btn btn-primary btn-block' disabled><span class='glyphicon glyphicon-play'></span> 訂單進行中</button>
		</div>
		</c:if>
		<c:if test="${mallorderVO.mall_order_status eq '已完成'}">
		<div class="text-center "  style="margin-top:20px; margin-bottom:20px">
			<button class='btn btn-success btn-block' disabled><span class='glyphicon glyphicon-ok'></span> 訂單已完成</button>
		</div>
		</c:if>
	
	</div>
	<div class="text-center col-md-12" style="margin-top:50px"><a href="<%=request.getContextPath()%>/FrontEnd/mallorder/mallOrder.jsp" class="btn btn-default">返回訂單列表</a></div>
	</div>	
		<!-- footer -->
		<div style="margin-top: 10%; ">
		<%@ include file="/FrontEnd/headerFooter/footer.html"%> 
		</div>
			
		
		
	
</body>


</html>