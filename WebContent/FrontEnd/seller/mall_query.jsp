<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!--  -->

<html>
<head>
<%@ include file="/FrontEnd/headerFooter/head.jsp"%>
<title>賣場搜尋</title>
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


<style>

  .item-table {
  	font-family:微軟正黑體;
	width: 855px;
	margin-top: 0px;
	margin-bottom: 20px;
  }
  
  .item-table th {
    background: #f44336;
    color: #FFFFFF;
  }
  
  .item-table th, .item-table td {
    padding: 5px;
    text-align: center;
  }

</style>



</head>
<body>
	<!-- header -->
	<div id="header">
		<jsp:include page="/FrontEnd/headerFooter/header.jsp" flush="true" />
	</div>
	<!-- //header-->
	<div class="products">	 
		<div class="container">
			<div class="col-md-9 product-w3ls-right">
				<!-- breadcrumbs --> 
				<ol class="breadcrumb breadcrumb1">
					<li><a href="index.jsp">Home</a></li>
					<li class="active">Market</li>
				</ol> 
				<div class="clearfix"> </div>
				<!-- //breadcrumbs -->
				<div class="product-top">
					<h4 style="font-family:微軟正黑體">賣場列表</h4>
					<div class="clearfix"> </div>
				</div>
				<div style="margin-top:0"> <%-- 賣場列表 --%>
					<c:forEach var="memVO" items="${list}" begin="<%= 0%>" end="${list.size()}">
					<FORM name='mem${memVO.mem_no}' METHOD="post" ACTION="query.do" target="_blank"> 
<!-- 					連結方法 ：將整個table用form包住用超連結送出 -->
						<input type="hidden" name="memNo" value="${memVO.mem_no}">
						<input type="hidden" name="action" value="findOneMall">
						<a href="javascript:document.getElementsByName('mem${memVO.mem_no}')[0].submit();">
							<table class="item-table" id="item-table">
								<tr class="table-dark">  <%-- 賣場名稱 --%>
									<th colspan=3>
										<h2>${memVO.mem_martname}</h2>
									</th>
								</tr>
		
								<tr height=150px>
									<td height=150px width=400px>
											<img src="data:image/*;base64,${memVO.martcoverEncoded}" 
											style="max-width: 100%; max-height: 100%;">	
									</td>
									<td width=300px style="text-align:left">
										<h3>${memVO.mem_martinfo}</h3>
									</td>
									<td width=150px>
										<h3>${memVO.mem_id}</h3>
									</td>
								</tr>
							</table>
						</a>
					</FORM>
					</c:forEach>
				</div>
			</div>
			<div class="col-md-3 rsidebar">
				<div class="rsidebar-top">
					<div class="sidebar-row">
						<h4 style="font-family:微軟正黑體;">逛逛賣場</h4>
						<h4 style="font-family:微軟正黑體;">商品分類</h4>
						<ul class="faq" style="font-family:微軟正黑體; font-size:16px">
							<li class="item1"><a href="#">服飾衣著<span class="glyphicon glyphicon-menu-down"></span></a>
								<ul>
									<li class="subitem1">
										<table style="table-layout:fixed;">
											<tr>
												<td>
													<FORM METHOD="post" ACTION="query.do">
														<input type="text" name="item_secondary_class" value="1" style="display: none;">
														<input type="hidden" name="action" value="findByPart">
														<input type="submit" value="長袖上衣" style="border:0px;background-color:white">
													</FORM>
												</td>
												<td>
													<FORM METHOD="post" ACTION="query.do">
														<input type="text" name="item_secondary_class" value="2" style="display: none;">
														<input type="hidden" name="action" value="findByPart">
														<input type="submit" value="短袖上衣" style="border:0px;background-color:white">
													</FORM>
												</td>
											</tr>
											<tr>
												<td>
													<FORM METHOD="post" ACTION="query.do">
														<input type="text" name="item_secondary_class" value="3" style="display: none;">
														<input type="hidden" name="action" value="findByPart">
														<input type="submit" value="背心" style="border:0px;background-color:white">
													</FORM>
												</td>
												<td>
													<FORM METHOD="post" ACTION="query.do">
														<input type="text" name="item_secondary_class" value="4" style="display: none;">
														<input type="hidden" name="action" value="findByPart">
														<input type="submit" value="長褲" style="border:0px;background-color:white">
													</FORM>
												</td>
											</tr>
											<tr>
												<td>
													<FORM METHOD="post" ACTION="query.do">
														<input type="text" name="item_secondary_class" value="5" style="display: none;">
														<input type="hidden" name="action" value="findByPart">
														<input type="submit" value="短褲" style="border:0px;background-color:white">
													</FORM>
												</td>
												<td>
													<FORM METHOD="post" ACTION="query.do">
														<input type="text" name="item_secondary_class" value="6" style="display: none;">
														<input type="hidden" name="action" value="findByPart">
														<input type="submit" value="裙子" style="border:0px;background-color:white">
													</FORM>
												</td>
											</tr>
											<tr>
												<td>
													<FORM METHOD="post" ACTION="query.do">
														<input type="text" name="item_secondary_class" value="7" style="display: none;">
														<input type="hidden" name="action" value="findByPart">
														<input type="submit" value="外套大衣" style="border:0px;background-color:white">
													</FORM>
												</td>
												<td>
													<FORM METHOD="post" ACTION="query.do">
														<input type="text" name="item_secondary_class" value="8" style="display: none;">
														<input type="hidden" name="action" value="findByPart">
														<input type="submit" value="帽子" style="border:0px;background-color:white">
													</FORM>
												</td>
											</tr>
											<tr>
												<td>
													<FORM METHOD="post" ACTION="query.do">
														<input type="text" name="item_secondary_class" value="9" style="display: none;">
														<input type="hidden" name="action" value="findByPart">
														<input type="submit" value="圍巾" style="border:0px;background-color:white">
													</FORM>
												</td>
												<td>
													<FORM METHOD="post" ACTION="query.do">
														<input type="text" name="item_secondary_class" value="10" style="display: none;">
														<input type="hidden" name="action" value="findByPart">
														<input type="submit" value="其他" style="border:0px;background-color:white">
													</FORM>
												</td>
											</tr>
										</table>
								    </li>								
								</ul>
							</li>
							<li class="item2"><a href="#">舌尖美食<span class="glyphicon glyphicon-menu-down"></span></a>
								<ul>
									<li class="subitem1">
										<table style="table-layout:fixed;">
											<tr>
												<td>
													<FORM METHOD="post" ACTION="query.do">
														<input type="text" name="item_secondary_class" value="11" style="display: none;">
														<input type="hidden" name="action" value="findByPart">
														<input type="submit" value="休閒零食" style="border:0px;background-color:white">
													</FORM>
												</td>
												<td>
													<FORM METHOD="post" ACTION="query.do">
														<input type="text" name="item_secondary_class" value="12" style="display: none;">
														<input type="hidden" name="action" value="findByPart">
														<input type="submit" value="進口零食" style="border:0px;background-color:white">
													</FORM>
												</td>
											</tr>
											<tr>
												<td>
													<FORM METHOD="post" ACTION="query.do">
														<input type="text" name="item_secondary_class" value="13" style="display: none;">
														<input type="hidden" name="action" value="findByPart">
														<input type="submit" value="生鮮食品" style="border:0px;background-color:white">
													</FORM>
												</td>
												<td>
													<FORM METHOD="post" ACTION="query.do">
														<input type="text" name="item_secondary_class" value="14" style="display: none;">
														<input type="hidden" name="action" value="findByPart">
														<input type="submit" value="米食/麵食" style="border:0px;background-color:white">
													</FORM>
												</td>
											</tr>
											<tr>
												<td>
													<FORM METHOD="post" ACTION="query.do">
														<input type="text" name="item_secondary_class" value="15" style="display: none;">
														<input type="hidden" name="action" value="findByPart">
														<input type="submit" value="飲料/沖泡品" style="border:0px;background-color:white">
													</FORM>
												</td>
												<td>
													<FORM METHOD="post" ACTION="query.do">
														<input type="text" name="item_secondary_class" value="16" style="display: none;">
														<input type="hidden" name="action" value="findByPart">
														<input type="submit" value="蛋糕/甜點" style="border:0px;background-color:white">
													</FORM>
												</td>
											</tr>
											<tr>
												<td>
													<FORM METHOD="post" ACTION="query.do">
														<input type="text" name="item_secondary_class" value="17" style="display: none;">
														<input type="hidden" name="action" value="findByPart">
														<input type="submit" value="南北雜貨" style="border:0px;background-color:white">
													</FORM>
												</td>
												<td>
													<FORM METHOD="post" ACTION="query.do">
														<input type="text" name="item_secondary_class" value="18" style="display: none;">
														<input type="hidden" name="action" value="findByPart">
														<input type="submit" value="伴手禮" style="border:0px;background-color:white">
													</FORM>
												</td>
											</tr>
											<tr>
												<td>
													<FORM METHOD="post" ACTION="query.do">
														<input type="text" name="item_secondary_class" value="19" style="display: none;">
														<input type="hidden" name="action" value="findByPart">
														<input type="submit" value="其他" style="border:0px;background-color:white">
													</FORM>
												</td>
											</tr>
										</table>
								    </li>								
								</ul>
							</li>
							<li class="item3"><a href="#">遊戲王<span class="glyphicon glyphicon-menu-down"></span></a>
								<ul>
									<li class="subitem1">
										<table style="table-layout:fixed;">
											<tr>
												<td>
													<FORM METHOD="post" ACTION="query.do">
														<input type="text" name="item_secondary_class" value="20" style="display: none;">
														<input type="hidden" name="action" value="findByPart">
														<input type="submit" value="主機遊戲" style="border:0px;background-color:white">
													</FORM>
												</td>
												<td>
													<FORM METHOD="post" ACTION="query.do">
														<input type="text" name="item_secondary_class" value="21" style="display: none;">
														<input type="hidden" name="action" value="findByPart">
														<input type="submit" value="掌上型電玩" style="border:0px;background-color:white">
													</FORM>
												</td>
											</tr>
											<tr>
												<td>
													<FORM METHOD="post" ACTION="query.do">
														<input type="text" name="item_secondary_class" value="22" style="display: none;">
														<input type="hidden" name="action" value="findByPart">
														<input type="submit" value="益智遊戲" style="border:0px;background-color:white">
													</FORM>
												</td>
												<td>
													<FORM METHOD="post" ACTION="query.do">
														<input type="text" name="item_secondary_class" value="23" style="display: none;">
														<input type="hidden" name="action" value="findByPart">
														<input type="submit" value="手機遊戲" style="border:0px;background-color:white">
													</FORM>
												</td>
											</tr>
											<tr>
												<td>
													<FORM METHOD="post" ACTION="query.do">
														<input type="text" name="item_secondary_class" value="24" style="display: none;">
														<input type="hidden" name="action" value="findByPart">
														<input type="submit" value="電腦遊戲" style="border:0px;background-color:white">
													</FORM>
												</td>
												<td>
													<FORM METHOD="post" ACTION="query.do">
														<input type="text" name="item_secondary_class" value="25" style="display: none;">
														<input type="hidden" name="action" value="findByPart">
														<input type="submit" value="遊戲周邊" style="border:0px;background-color:white">
													</FORM>
												</td>
											</tr>
											<tr>
												<td>
													<FORM METHOD="post" ACTION="query.do">
														<input type="text" name="item_secondary_class" value="26" style="display: none;">
														<input type="hidden" name="action" value="findByPart">
														<input type="submit" value="其他" style="border:0px;background-color:white">
													</FORM>
												</td>
											</tr>
										</table>
								    </li>									
								</ul>
							</li>
							<li class="item3"><a href="#">戶外休閒<span class="glyphicon glyphicon-menu-down"></span></a>
								<ul>
									<li class="subitem1">
										<table style="table-layout:fixed;">
											<tr>
												<td>
													<FORM METHOD="post" ACTION="query.do">
														<input type="text" name="item_secondary_class" value="27" style="display: none;">
														<input type="hidden" name="action" value="findByPart">
														<input type="submit" value="登山/露營" style="border:0px;background-color:white">
													</FORM>
												</td>
												<td>
													<FORM METHOD="post" ACTION="query.do">
														<input type="text" name="item_secondary_class" value="28" style="display: none;">
														<input type="hidden" name="action" value="findByPart">
														<input type="submit" value="戶外用品" style="border:0px;background-color:white">
													</FORM>
												</td>
											</tr>
											<tr>
												<td>
													<FORM METHOD="post" ACTION="query.do">
														<input type="text" name="item_secondary_class" value="29" style="display: none;">
														<input type="hidden" name="action" value="findByPart">
														<input type="submit" value="運動用品" style="border:0px;background-color:white">
													</FORM>
												</td>
												<td>
													<FORM METHOD="post" ACTION="query.do">
														<input type="text" name="item_secondary_class" value="30" style="display: none;">
														<input type="hidden" name="action" value="findByPart">
														<input type="submit" value="運動健身" style="border:0px;background-color:white">
													</FORM>
												</td>
											</tr>
											<tr>
												<td>
													<FORM METHOD="post" ACTION="query.do">
														<input type="text" name="item_secondary_class" value="31" style="display: none;">
														<input type="hidden" name="action" value="findByPart">
														<input type="submit" value="腳踏車" style="border:0px;background-color:white">
													</FORM>
												</td>
												<td>
													<FORM METHOD="post" ACTION="query.do">
														<input type="text" name="item_secondary_class" value="32" style="display: none;">
														<input type="hidden" name="action" value="findByPart">
														<input type="submit" value="運動服飾" style="border:0px;background-color:white">
													</FORM>
												</td>
											</tr>
											<tr>
												<td>
													<FORM METHOD="post" ACTION="query.do">
														<input type="text" name="item_secondary_class" value="33" style="display: none;">
														<input type="hidden" name="action" value="findByPart">
														<input type="submit" value="雨衣/雨具" style="border:0px;background-color:white">
													</FORM>
												</td>
												<td>
													<FORM METHOD="post" ACTION="query.do">
														<input type="text" name="item_secondary_class" value="34" style="display: none;">
														<input type="hidden" name="action" value="findByPart">
														<input type="submit" value="鞋子" style="border:0px;background-color:white">
													</FORM>
												</td>
											</tr>
											<tr>
												<td>
													<FORM METHOD="post" ACTION="query.do">
														<input type="text" name="item_secondary_class" value="35" style="display: none;">
														<input type="hidden" name="action" value="findByPart">
														<input type="submit" value="旅行相關" style="border:0px;background-color:white">
													</FORM>
												</td>
												<td>
													<FORM METHOD="post" ACTION="query.do">
														<input type="text" name="item_secondary_class" value="36" style="display: none;">
														<input type="hidden" name="action" value="findByPart">
														<input type="submit" value="其他" style="border:0px;background-color:white">
													</FORM>
												</td>
											</tr>
										</table>
								    </li>									
								</ul>
							</li>
							<li class="item3"><a href="#">家電用品<span class="glyphicon glyphicon-menu-down"></span></a>
								<ul>
									<li class="subitem1"><a href="#"> Tv & Accessories</a></li>										
									<li class="subitem1"><a href="#">Digital Camera </a></li>										
									<li class="subitem1"><a href="#">Computer</a></li>										
								</ul>
							</li>
							<li class="item3"><a href="#">電競3C<span class="glyphicon glyphicon-menu-down"></span></a>
								<ul>
									<li class="subitem1"><a href="#"> Tv & Accessories</a></li>										
									<li class="subitem1"><a href="#">Digital Camera </a></li>										
									<li class="subitem1"><a href="#">Computer</a></li>										
								</ul>
							</li>
						</ul>
						<!-- script for tabs -->
						<script type="text/javascript">
							$(function() {
							
								var menu_ul = $('.faq > li > ul'),
									   menu_a  = $('.faq > li > a');
								
								menu_ul.hide();
							
								menu_a.click(function(e) {
									e.preventDefault();
									if(!$(this).hasClass('active')) {
										menu_a.removeClass('active');
										menu_ul.filter(':visible').slideUp('normal');
										$(this).addClass('active').next().stop(true,true).slideDown('normal');
									} else {
										$(this).removeClass('active');
										$(this).next().stop(true,true).slideUp('normal');
									}
								});
							
							});
						</script>
						<!-- script for tabs -->
					</div>
				</div>
			</div>
					
			
		</div>
	</div>
	<!--//products-->