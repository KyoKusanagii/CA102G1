<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.article.model.*"%> 

<%
	List<ArticleVO> list = (List<ArticleVO>)session.getAttribute("list");
	request.setAttribute("list", list);
%>
<jsp:useBean id="MemService" scope="page" class="com.member.model.MemService" />
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

<script src="js/jquery-scrolltofixed-min.js" type="text/javascript"></script>
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

<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.2.0/css/all.css" integrity="sha384-hWVjflwFxL6sNzntih27bfxkr27PmbbK/iSvJ+a4+0owXq79v+lsFkW54bOGbiDQ" crossorigin="anonymous">
</head>
<body>
	<!-- header -->
	<div class="header">
		<%@ include file="/FrontEnd/headerFooter/header1.jsp" %> 
	</div>
		<!-- //header-->
			

    <!-- Placed at the end of the document so the pages load faster --> 


	
		<div class="container col">
		<div class="header-logo col-md-12">
            <h1><a href="<%=request.getContextPath()%>/FrontEnd/index.jsp"><span>Insta</span>Buy <i style="font-family: 微軟正黑體;font-size: 14px">in直拍</i></a></h1>
        </div>
		<div class="col-md-2"></div>
		<div class="col-md-10 ">
			<form action="article.do" method="post">
			    <div class="col-12 col-md-10">
			      <div class="input-group">
			        <input type="text" class="form-control" name="arti_topic" placeholder="搜尋文章 ..." style="height:46px"/>
			        <div class="input-group-btn">
			          <button class="btn btn-danger btn-lg" type="submit">
			            <span class="glyphicon glyphicon-search"></span>
			          </button>
			          <input type="hidden" name="action" value="findByTopic">
			        </div>
			      </div>
			    </div>
			</form>	
		</div>
		
	</div>
	
	<div class="container" style="margin-top:5% ;font-family:微軟正黑體; font-weight:bold">
		<div class="col-md-offset-1 col-md-10" >
		
			<div class="row col">
				<h1 style="float:left; margin-left:15px">搜尋結果</h1>
			
				<a href="po_arti.jsp" style="float:right; margin-right:15px"><button class="btn btn-primary btn-lg"><span class="glyphicon glyphicon-pencil"></span>發表主題</button></a>	
			</div>
			
			<% 
				if(list != null && (list.size() > 0)){
			%>
			
				<div class="col" style="margin-top:3%">
				<%@ include file="page1.file" %> 
				<c:forEach var="articleVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
				<div class="list-group col" style="box-shadow: 1px 1px 8px black;">
				  <a href="article.jsp?arti_no=${articleVO.arti_no}" class="list-group-item ">
				    <div class="list-group-item-heading" style="margin-left:5%">
					    <p><img src="data:image/*;base64,${MemService.findByPK(articleVO.mem_no).profilepicEncoded}" class="img-circle" style="height: 50px; width: 50px;">
					    <span style="margin-left:10px;  color:green; font-size:20px">${MemService.findByPK(articleVO.mem_no).mem_name}</span>
					    </p>
				    </div>
				    
				    <div class="list-group-item-text" style="margin-left:15%; word-wrap:break-word; word-break: break-all; font-size:30px"><b>${articleVO.arti_topic}</b></div>
				    <br>
				    <P style="margin-left:15%; font-size:18px">查看全部...</P>
					<p Align="Right"><fmt:formatDate pattern="MM月dd日  HH:mm" value="${articleVO.po_time}" /></p>
				  </a>
				</div>
				</c:forEach>
				</div>
				<%@ include file="page2.file" %>
					
				
					<%}else{ %>
				
					<div class=" text-center" style="font-size:50px; margin-bottom:30%; margin-top: 150px">
						<p>
							<span style="font-size:80px" class="far fa-frown"></span><font style="margin-left:20px">目前暫無相關文章</font>
						</p>
					</div>
					
					<%} %>
				
				</div>
		</div>
		
			
	
	
	<div class="text-center">
		<a href="forum.jsp" ><button class="btn btn-lg btn-default">返回文章列表</button></a>	
	</div>
	





	<div style="margin-top: 10%; ">
		<%@ include file="/FrontEnd/headerFooter/footer.html" %> 
	</div>
	
</body>


</html>