<%@ page contentType="text/html; charset=BIG5" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.article.model.*"%> 
    
    
    
    
    
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
		<div class="w3ls-header"><!--header-one--> 
			
			<div class="w3ls-header-right">
				<ul>
					<li class="dropdown head-dpdn">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-user" aria-hidden="true"></i>會員專區<span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="login.html">登入 </a></li> 
							<li><a href="signup.html">註冊</a></li> 
							<li><a href="login.html">訂單查詢</a></li>  
							<li><a href="login.html">會員資料修改</a></li> 
						</ul> 
					</li> 
					<li class="dropdown head-dpdn">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-laptop" aria-hidden="true"></i>賣家中心<span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="offers.html">賣場</a></li> 
							<li><a href="offers.html">商品管理</a></li>
							<li><a href="offers.html">訂單查詢</a></li>
							<li><a href="offers.html">直播快手</a></li>
							<li><a href="offers.html">直播預告</a></li> 
						</ul> 
					</li> 
					<li class="dropdown head-dpdn">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-bullhorn" aria-hidden="true"></i>直播預告 <span class="badge" id="onair-count" style="background: #FF0000">N</a>
						<ul class="dropdown-menu">
							<li><a href="offers.html">預告一</a></li> 
							<li><a href="offers.html">預告二</a></li>
							<li><a href="offers.html">預告三</a></li> 
						</ul> 
					</li> 
					<li class="dropdown head-dpdn">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-bell" aria-hidden="true"></i>正在直播 <span class="badge" id="onair-count" style="background: #FF0000">N</span></a>
						<ul class="dropdown-menu">
							<li><a href="offers.html">直播一</a></li> 
							<li><a href="offers.html">直播二</a></li>
							<li><a href="offers.html">直播三</a></li> 
						</ul>
					</li> 
					<li class="dropdown head-dpdn">
						<a href="#" class="dropdown-toggle"><i class="fa fa-columns" aria-hidden="true"></i>討論區</a>
					</li> 
					<li class="dropdown head-dpdn">
						<a href="#" class="dropdown-toggle"><i class="fa fa-wechat" aria-hidden="true"></i> 聊天</a>
					</li>
					<li class="dropdown head-dpdn">
						<a href="#" class="dropdown-toggle"><i class="fa fa-newspaper-o" aria-hidden="true"></i> 最新消息</a>
					</li>
				</ul>
			</div>
			<div class="clearfix"> </div> 
		</div>
		<!-- //header-->
			

	
    <!-- Placed at the end of the document so the pages load faster --> 
	<c:if test="${not empty errorMsgs}">
	 <font style="color:red">請修正以下錯誤:</font>
	 <ul>
	  <c:forEach var="message" items="${errorMsgs}">
	   <li style="color:red">${message.value}</li>
	  </c:forEach>
	 </ul>
	</c:if>

	<div class="container" style="margin-top: 5%">
		
		
		
		<form method="post" action="article.do">
			<div class="form-group">
			    <label for="Textarea1" style="font-size: 30px">主題</label>
			    <i class="fa fa-pencil prefix"></i>
			    <textarea name="arti_topic" class="form-control rounded-0" value="${param.arti_topic}" rows="3"></textarea>
			</div>	
			
			<div class="form-group">
			
			    <label for="Textarea2" style="font-size: 30px">內容</label>
			    <i class="fa fa-pencil prefix"></i>
			    
			    
			    <textarea name="arti_content" class="form-control " value="${param.arti_content}" style="font-size:20px;" rows="20"></textarea>
			    
			</div>

			<div class="text-center">
			<input type="hidden" name="action" value="insert">
			<input type="submit" class="btn btn-primary"  value="發布文章">
			
			<input type="button" class="btn btn_info" value="取消" onclick="location.href = 'forum.jsp'">
			</div>
		</form>
		
	</div>
	
	
	<script src="<%=request.getContextPath()%>/FrontEnd/forum/ckeditor/ckeditor.js"></script>


		
		<script>
		        CKEDITOR.replace('arti_content', {
		            extraPlugins: 'base64image',
		            removePlugins: 'image',
		            removePlugins: 'resize',
		            removeDialogTabs: 'image:advanced;link:advanced',
		            height: 500,
		        });
		</script>


	<div style="margin-top: 10%; ">
		<jsp:include page="/FrontEnd/headerFooter/footer.html"/> 
	</div>
</body>

</html>