<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.article.model.*,com.member.model.*"%> 
  
  
<%    
	MemVO memVO = (MemVO)session.getAttribute("memVO");
	
	if(memVO == null){
		session.setAttribute("location", request.getRequestURI());
		response.sendRedirect(request.getContextPath() + "/FrontEnd/member/login.jsp");
		return;
	}
    
	String arti_topic = request.getParameter("arti_topic");
	String arti_content = request.getParameter("arti_content");
	pageContext.setAttribute("arti_topic", arti_topic);
	pageContext.setAttribute("arti_content", arti_content);
	
%>    
    
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
		<jsp:include page="/FrontEnd/headerFooter/header1.jsp"/>
	</div>
		<!-- //header-->
			

	
    <!-- Placed at the end of the document so the pages load faster --> 
	

	<div class="container" style="margin-top: 5%; font-family:微軟正黑體; font-weight:bold">
		
		
		
		<form method="post" action="article.do">
			<div class="form-group">
			    <label for="Textarea1" style="font-size: 30px">主題</label>
			    <i class="fa fa-pencil prefix"></i>
			    <p style="color:red; font-family:微軟正黑體;">${errorMsgs.arti_topic}</p>
			    <textarea name="arti_topic" class="form-control" rows="3" style="resize:none;box-shadow: 1px 1px 8px black;">${arti_topic}</textarea>
			</div>	
			
			<div class="form-group">
			
			    <label for="Textarea2" style="font-size: 30px">內容</label>
			    <i class="fa fa-pencil prefix"></i>
			    <p style="color:red; font-family:微軟正黑體;">${errorMsgs.arti_content}</p>
			    
			    <div style="box-shadow: 1px 1px 8px black;">
			      <textarea name="arti_content" class="form-control "style="font-size:20px;" rows="20" >${arti_content}</textarea>
			    </div>
			</div>

			<div class="text-center">
			<input type="hidden" name="action" value="insert">
			<input type="hidden" name="mem_no" value="${memVO.mem_no}">
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
		<%@include file="/FrontEnd/headerFooter/footer.html" %> 
	</div>
</body>

</html>