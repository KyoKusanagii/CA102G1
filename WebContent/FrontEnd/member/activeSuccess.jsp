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
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Refresh" content="5;url=<%=request.getContextPath()%>/FrontEnd/member/login.jsp"/>
<title>驗證成功</title>
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
	
	<!-- sign up-page -->
	<div class="login-page">
		<div class="container"> 
			<h3 class="w3ls-title w3ls-title1" style="font-family:微軟正黑體; color:white">信箱驗證成功</h3>  
			<div class="login-body" style="font-family:微軟正黑體; text-align:left">
				<h4><b>親愛的會員你好！</b></h4><br>
				<h4><b>信箱已驗證成功！</b></h4><br>
				<h4><b>請盡情享受InstaBuy所有服務</b></h4><br>
				<h4><b>此通知網頁將在數秒後自動導入登入頁面</b></h4><br>
				<h6 style="font-family:微軟正黑體;"><b>網頁還沒跳轉？ </b><a href="login.jsp"><b> 前往登入頁面 </b></a></h6> 
			</div>  
			 
		</div>
	</div>
	<!-- //sign up-page -->
</body>
</html>