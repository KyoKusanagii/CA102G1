<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.member.model.*, com.follow_mem.model.*, com.follow_item.model.*, com.item.model.*, com.itempic.model.*"%> 

<% 

MemVO memVO = (MemVO)session.getAttribute("memVO");
String mem_no = memVO.getMem_no();



Follow_itemService folo_itemSvc = new Follow_itemService();
List<Follow_itemVO> itemList = folo_itemSvc.getAll(mem_no);
pageContext.setAttribute("itemList", itemList);



%>
<jsp:useBean id="MemService" scope="page" class="com.member.model.MemService" />  
<jsp:useBean id="ItemService" scope="page" class="com.item.model.ItemService" />  
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




<script src="<%=request.getContextPath()%>/FrontEnd/js/jquery.twzipcode.min.js"></script>
<script src="<%=request.getContextPath()%>/FrontEnd/js/birthday.js"></script>

</head>
<body>
	<!-- header -->
	<div class="header">
		<jsp:include page="/FrontEnd/headerFooter/header.jsp"/>
	</div>
	<!-- //header-->
			

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



	<div class="row" style="font-family:微軟正黑體; font-weight:bold">
		<div class="col-12 col-md-2" >
					<ul class="nav nav-pills nav-stacked">
					  <li role="presentation" class="text-center"><a href="profile.jsp"><h4>會員資料</h4></a></li>
					  <li role="presentation" class="active text-center"><a href="#"><h4>我的最愛</h4></a></li>
					  <li role="presentation" class="text-center"><a href="mem_mallOrder.jsp"><h4>訂單查詢</h4></a></li>
					</ul>
				</div>
		
		
		<div class="container" >
			
			<div class="col-12 col-md-10">
			  <ul class="nav nav-tabs" role="tablist">
			    <li role="presentation" ><a href="<%=request.getContextPath()%>/FrontEnd/member/followMem.jsp" aria-controls="home" >賣家</a></li>
			    <li role="presentation" class="active"><a href="#" aria-controls="profile" role="tab" data-toggle="tab">商品</a></li>
			  </ul>
		
			 
			  <div class="tab-content">
			 
					    <div role="tabpanel" class="tab-pane active" id="foloitem" >
					    	 <c:forEach var="folo_itemVO" items="${itemList}">	
							  <div class="col-sm-6 col-md-3" >
							    <div class="thumbnail">
							      <div class="caption text-right">
								      <button class="btn btn-default btn-xs Folo_item" id="${folo_itemVO.item_no}"><i class="glyphicon glyphicon-remove"></i></button>
								      <input type="hidden" name="action" value="deleteFolo_item">
							      </div>
							      	<img src="data:image/*;base64,${ItempicService.getThumbnail(folo_itemVO.item_no).encoded}" class="img-responsive" style="height:180px; width:150px">
							      <div class="caption text-center">
							        <h5>${ItemService.getOneItem(folo_itemVO.item_no).item_name}</h5>
							          <form method="post" action="<%=request.getContextPath()%>/FrontEnd/query.do" style="margin-top:10px;">
							      		<input type="hidden" name="itemNo" value="${folo_itemVO.item_no}">
										<input type="hidden" name="action" value="findOneItem">
							      	 	<button class="btn btn-danger">查看商品頁面</button>
							     	 </form>
							      </div>
							    </div>
							  </div>
						  </c:forEach>
		                </div>
			    </div>
			</div>
			
		</div>
		
	</div>
		
	

	<!-- footer -->
	<div style="margin-top: 10%;">
		<%@ include file="/FrontEnd/headerFooter/footer.html" %>
    </div>
</body>
<script type="text/javascript">
	$('#myTabs a').click(function (e) {
	  e.preventDefault()
	  $(this).tab('show')
	})	
</script>
<script type="text/javascript">
	 
	$(document).ready(function () {
	      $(".Folo_item").click(function(){
	       var action = "folo_item";
	       var mem_no = "${memVO.mem_no}";
	   	   var item_no = $(this).attr("id");
	       var collectMessage = document.getElementById("collectMessage");
	    $.ajax({
	     url : "<%=request.getContextPath()%>/follow.do",
	     method : "POST",
	     data : {
	      action:action,
	      mem_no:mem_no,
	      item_no:item_no 
	     },
	   
	    });
	    $(this).parent().parent().parent().css("display","none");
	   });
	  });
	 </script>
</html>