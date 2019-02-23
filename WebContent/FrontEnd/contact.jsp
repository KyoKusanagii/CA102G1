<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>



<!DOCTYPE html>
<html lang="en">
<head>
<title>關於InstaBuy</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=Big5" />
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


<!-- 會員專區用的 -->
<script src="scripts/jquery.twzipcode.min.js"></script>
<script src="scripts/birthday.js"></script>
<!-- //會員專區用的 -->

<!-- FB登入 -->
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/FrontEnd/css/fbLoginButton.css">
<script type="text/javascript">
	
        //初始化設定載入Facebook SDK
        (function(d, s, id) {
            var js, fjs = d.getElementsByTagName(s)[0];
            if (d.getElementById(id)) return;
            js = d.createElement(s);
            js.id = id;
            js.src = 'https://connect.facebook.net/zh_TW/sdk.js#xfbml=1&version=v3.0&appId=599955533703878&autoLogAppEvents=1';
            fjs.parentNode.insertBefore(js, fjs);
        }(document, 'script', 'facebook-jssdk'));

        //登入按鈕 //取得授權並登入應用程式
        function fbLogin() {
            FB.login(function(response) {
                statusChangeCallback(response);
            }, {scope: 'public_profile,email'});
        }
        //登出按鈕 //把FB真的登出
        function fbLogout() {
            FB.logout(function(response) {
                statusChangeCallback(response);
            });
        }
        //檢查登入狀態並取得資料
        function statusChangeCallback(response) {
            if (response.status === 'connected') {
                console.log('Facebook已登入')
                FB.api('/me?fields=id,name,picture,email', function(response) {
                    document.getElementById('com').innerText='FACEBOOK'
                    document.getElementById('uid').innerText=response.id
                    document.getElementById('name').innerText=response.name
                    document.getElementById('email').innerText=response.email
                    document.getElementById('picture').src=response.picture.data.url
                    document.getElementById('mem_id').value = document.getElementById('uid').innerHTML;
                	document.getElementById('mem_name').value = document.getElementById('name').innerHTML;
                	document.getElementById('mem_email').value = document.getElementById('email').innerHTML;
                	document.getElementById('mem_picture').value = document.getElementById('picture').src;
                	document.forms["fbForm"].submit();
                });
            } else {
                console.log('Facebook未登入')
            }
        }
        
        function putValue(){
        	
        	var a = document.getElementById('name').innerHTML;
        	alert(a)
        	
        }
        
</script>
<!-- //FB登入 -->
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

		<div class="login-page">
		<div class="container" style="font-weight:bolt"> 
			<h3 class="w3ls-title w3ls-title1" style="font-family:微軟正黑體; color:white"><b>關於InstaBuy</b></h3>  
			<div class="login-body" style="width: 100%; font-family:微軟正黑體; text-align:left">
				<h2><b>緣由</b></h2><br>
				<h3><b style="color:#f44336">InstaBuy</b><b>讓網路商城不再只是以往的購物網站模式</b></h3><br>
				<br>
				<h3><b style="color:#f44336">InstaBuy</b><b>商家可以隨時隨地上架商品，透過直播與消費者即時互動，將商品在短時間內銷售一空</b></h3><br>
				<br>
				<h3><b>藉由直播，收看者可以360 度的觀看商品樣貌，增加了商品的可信度和參考性，讓觀眾及消費者更有買單的慾望</b></h3><br>
				<br>
				<h3><b>請盡情享受<b style="color:#f44336">InstaBuy</b>所提供的多贏服務</b></h3><br>
				<br>
				<br>
				<br>
				<br>
				<br>
				<br>
				
			</div>  
			 
			
		</div>
	</div>
	

</body>


</html>