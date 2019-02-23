<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.article.model.*, com.member.model.*,com.article_report.model.*"%> 

<jsp:useBean id="MemService" scope="page" class="com.member.model.MemService" />
<!-- 用來判別文章被檢舉的狀態 -->
<jsp:useBean id="artiReportService" scope="page" class="com.article_report.model.ArticleReportService" />

<%
	
	ArticleService artiSvc = new ArticleService();
	List<ArticleVO> list = artiSvc.getAll();
	pageContext.setAttribute("list", list);
	List<ArticleReportVO> articleReport = (List<ArticleReportVO>)artiReportService.getAllAritcleReport();
	
	//所有未處理的檢舉文章的文章編號
	Set<String> arti_no_of_report = new HashSet<>();
	//所有檢舉審核通過的文章編號
	Set<String> arti_no_of_report_done = new HashSet<>();
	for(int i=0;i<articleReport.size();i++){
		if(articleReport.get(i).getReport_status() == 0)	//如果檢舉文章的狀態為未處理，加到集合裡
			arti_no_of_report.add(articleReport.get(i).getArti_no());
		else if(articleReport.get(i).getReport_status() == 1)
			arti_no_of_report_done.add(articleReport.get(i).getArti_no());
	}
	session.setAttribute("read_only_articles",arti_no_of_report);
	pageContext.setAttribute("arti_no_of_report_done",arti_no_of_report_done);
	System.out.println("未處理的檢舉文章編號為:"+arti_no_of_report);
	
%>


<!DOCTYPE html>
<html lang="en">
<head>
<title>InstaBuy</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=big5" />
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
		<%@ include file="/FrontEnd/headerFooter/header1.jsp" %> 
	</div>
		<!-- //header-->
	
	

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
			      <p style="color:red; font-family:微軟正黑體;">${errorMsgs}</p>
			    </div>
			</form>	
		</div>

	</div>
	<div class="container" style="margin-top:5% ;font-family:微軟正黑體; font-weight:bold">
		<div class="col-md-offset-1 col-md-10">
		
			<div class="row col">
				<h1 style="float:left; margin-left:15px">文章列表</h1>
				<a href="po_arti.jsp" style="float:right; margin-right:15px"><button class="btn btn-primary btn-lg"><span class="glyphicon glyphicon-pencil"></span>發表主題</button></a>	
			</div>
			
				<div class="col" style="margin-top:3%">
				<%@ include file="page1.file" %> 
				<c:forEach var="articleVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
						<!-- 				如果為0，表示這篇文章檢舉尚未處理 -->
				<c:if test="${!arti_no_of_report_done.contains(articleVO.arti_no)}">		
				<div class="list-group col" style="box-shadow: 1px 1px 8px black;">
				  <a href="article.jsp?arti_no=${articleVO.arti_no}" class="list-group-item ">
				    <div class="list-group-item-heading" style="margin-left:5%;">
					    <p><img src="data:image/*;base64,${MemService.findByPK(articleVO.mem_no).profilepicEncoded}" class="img-circle" style="height: 50px; width: 50px;">
					    	<span style="margin-left:10px; color:green; font-size:20px">${MemService.findByPK(articleVO.mem_no).mem_name}</span>
					    </p>
				    </div>
				    <div class="list-group-item-text" style="margin-left:15%; word-wrap:break-word; word-break: break-all; font-size:30px"><b>${articleVO.arti_topic}</b></div>
				    <br>
							<!-- EL就好好和EL使用，不要EL混百分比(JSP)的寫法 -->
							<c:if test="${read_only_articles.contains(articleVO.arti_no)}">
								<p style="margin-left:15%; font-size:18px;color:red">此文章檢舉審核中</p>
							</c:if>
							<c:if test="${!read_only_articles.contains(articleVO.arti_no)}">
								<p style="margin-left:15%; font-size:18px">查看全部...</p>
							</c:if>
					<p align="Right" style="font-size:20px"><fmt:formatDate pattern="MM月dd日  HH:mm" value="${articleVO.po_time}"/></p>
				  </a>
				</div>
				</c:if>
				</c:forEach>
				</div>
			<%@ include file="page2.file" %>		
		</div>
	</div>
	<div style="margin-top: 10%; ">
		<%@ include file="/FrontEnd/headerFooter/footer.html"%>
	</div>
	
</body>
</html>