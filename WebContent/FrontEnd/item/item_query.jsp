<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*,com.member.model.MemVO,com.follow_item.model.*,com.item_report.model.*,com.item.model.*,com.limitSale.model.*"%>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="com.live.model.*"%>
<%@ page import="java.util.*,java.sql.*,java.text.*"%>
<jsp:useBean id="itemReportService" scope="page" class="com.item_report.model.ItemReportService"/>
<jsp:useBean id="limitSaleService" scope="page" class="com.limitSale.model.LimitSaleService"/>


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

	
	
	List<ItemReportVO> itemReportList = (List<ItemReportVO>)itemReportService.getAllItemReport();
	Set<String> item_no_of_report = new HashSet<>();
	for(int i=0;i<itemReportList.size();i++){
		if(itemReportList.get(i).getReport_Status() == 0)
			item_no_of_report.add(itemReportList.get(i).getItem_NO());	//收集所有被檢舉但未處理的商品編號
	}
	
	//抓出所有特賣的商品
	List<LimitSaleVO> itemSaleList = (List<LimitSaleVO>)limitSaleService.getAll();
	pageContext.setAttribute("itemSaleList",itemSaleList);
	
	//抓出現在所有正在特價的商品物件編號
	Set<String> limitSaleItem = new HashSet<String>(); 
	for(int i=0;i<itemSaleList.size();i++){
		if(itemSaleList.get(i).getSale_status() == 1)	//如果這個東西正在特賣中
			limitSaleItem.add(itemSaleList.get(i).getItem_no());
	}
	pageContext.setAttribute("limitSaleItem",limitSaleItem);
	Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
	pageContext.setAttribute("now",timeStamp);
%>

<html lang="en">
<head>
<title>Item-Search</title>
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

<script type="text/javascript">
	function showItemTable(){
		document.getElementById("item-table").style.display = '';
		document.getElementById("item-table2").style.display = 'none';
	}
	function showItemTable1(){
		document.getElementById("item-table").style.display = 'none';
		document.getElementById("item-table2").style.display = '';
	}
	
	function addCart(){
		alert("已加入至購物車");
	}
</script>




<style>
  .item-table {
  	font-family:微軟正黑體;
	width: 855px;
  }
  .item-table th, .item-table td {
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

	<div class="header">
		<jsp:include page="/FrontEnd/headerFooter/header.jsp"/>
	</div>
	
	
	<div class="products">	 
		<div class="container">
			<div class="col-md-9 product-w3ls-right">
				<!-- breadcrumbs --> 
				<ol class="breadcrumb breadcrumb1">
					<li><a href="index.jsp">Home</a></li>
					<li class="active">Products</li>
				</ol> 
				<div class="clearfix"> </div>
				<!-- //breadcrumbs -->
				<div class="product-top">
					<h4 style="font-family:微軟正黑體">商品列表</h4>
					<ul> 
						<li class="dropdown head-dpdn">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown"style="font-family:微軟正黑體; text-align:center">排序<span class="caret"></span></a>
							<ul class="dropdown-menu" style="font-family:微軟正黑體; text-align:center">
								<li onclick = 'showItemTable1();'><a>網格狀</a></li> 
								<li onclick = 'showItemTable();'><a>橫條狀</a></li>
							</ul>
						</li>
					</ul> 
					<div class="clearfix"> </div>
				</div>
				<div> <%-- 商品列表 --%>
					<div id="item-table">
					<table class="item-table" id="item-table">
						<tr>
							<td width=300px><h4>商品圖片</h4></td>
							<td width=250px><h4>商品名稱</h4></td>
							<td width=150px><h4>商品價格</h4></td>
							<td width=150px><h4></h4></td>
						</tr>
					</table>
					<c:if test="${list.size() == 0}">
						<br>
						<h2 style="text-align: center; color:#f44336; font-family:微軟正黑體;">查無相關商品</h2> 
					</c:if>
					<c:forEach var="itemVO" items="${list}" begin="<%= 0%>" end="${list.size()}">
					<FORM name='item${itemVO.item_no}' METHOD="post" ACTION="<%=request.getContextPath()%>/FrontEnd/query.do" TARGET="_blank"> <%-- 連結方法 ：將整個table用form包住用超連結送出--%>
						<input type="hidden" name="itemNo" value="${itemVO.item_no}">
						<input type="hidden" name="action" value="findOneItem">
					</FORM>
					<form>
					<table style="text-align: center; border-bottom:solid #FF6558;">
						<tr>
							<td height=150px width=305px><a href="javascript:document.getElementsByName('item${itemVO.item_no}')[0].submit();">
							<img src="data:image/*;base64,${itemVO.encoded}" 
							style="max-width: 100%; max-height: 100%;"></a>
							</td>
							<td width=250px>
								<a href="javascript:document.getElementsByName('item${itemVO.item_no}')[0].submit();">
								${itemVO.item_name}</a>
							</td>
							<td width=150px>
								<c:if test="${limitSaleItem.contains(itemVO.item_no) && (limitSaleService.findOneSale(itemVO.item_no).getSale_end() > now)}">
								<del><span style="font-size: 25px;color:black;font-family:arial, helvetica, clean, sans-serif;">$<span class="itemPrice">${itemVO.item_price}</span></span></del><br>
								<span style="font-size: 25px;color:rgb(226, 28, 0);font-family:arial, helvetica, clean, sans-serif;">$<span class="${itemVO.item_no}salePrice">
								${limitSaleService.findOneSale(itemVO.item_no).getSale_price()}</span></span><br>
								
							 	<div style="font-size: 14px;color:rgb(226, 28, 0);font-family:Microsoft JhengHei;"><span class="glyphicon glyphicon-hourglass"></span>
									<div id="${itemVO.item_no}countdown" class="timeTo timeTo-white" style="display:inline;font-size: 14px;color:rgb(226, 28, 0);font-family:Microsoft JhengHei;"></div>
								</div>          	
	                        	</c:if>
	                        	<c:if test="${!limitSaleItem.contains(itemVO.item_no) || (limitSaleService.findOneSale(itemVO.item_no).getSale_end() < now)}"> 
	                        		 <span style="font-size: 25px;color:rgb(226, 28, 0);font-family:arial, helvetica, clean, sans-serif;">$<span id="itemPrice">${itemVO.item_price}</span></span><br>
	                        	</c:if>
							</td>
							<td width=150px>
								<input type="hidden" id="buyQuantity" value="1" />
								<input type="hidden" name="cmd" value="_cart" />
	                            <input type="hidden" name="add" value="1" />
	                            <input type="hidden" name="w3ls_item" value="Snow Blower" />
	                            <input type="hidden" name="amount" value="540.00" />
								<button type="button" class="w3ls-cart" id="Item${itemVO.item_no}AddToCart" style="font-family:微軟正黑體;" onclick="addCart()"><span class="glyphicon glyphicon-shopping-cart"></span> 加入購物車</button>
							
							</td>
						</tr>
					</table>
					</form>
					
	<%--有限時特賣才顯示倒數計時 --%>	
	<c:if test="${limitSaleItem.contains(itemVO.item_no) && (limitSaleService.findOneSale(itemVO.item_no).getSale_end() > now)}">				
	<script>
			// Set the date we're counting down to
			var ${itemVO.item_no}countDownDate = new Date("${limitSaleService.findOneSale(itemVO.item_no).getSale_end()}").getTime();

			// Update the count down every 1 second
			var ${itemVO.item_no}x = setInterval(function() {

  			// Get todays date and time
  			var ${itemVO.item_no}now = new Date().getTime();

  			// Find the distance between now and the count down date
  			var ${itemVO.item_no}distance = ${itemVO.item_no}countDownDate - ${itemVO.item_no}now;
 			 // Time calculations for days, hours, minutes and seconds
 			var ${itemVO.item_no}days = Math.floor(${itemVO.item_no}distance / (1000 * 60 * 60 * 24));
 			var ${itemVO.item_no}hours = Math.floor((${itemVO.item_no}distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
  			var ${itemVO.item_no}minutes = Math.floor((${itemVO.item_no}distance % (1000 * 60 * 60)) / (1000 * 60));
  			var ${itemVO.item_no}seconds = Math.floor((${itemVO.item_no}distance % (1000 * 60)) / 1000);

  			// Display the result in the element with id="demo"
  			document.getElementById("${itemVO.item_no}countdown").innerHTML = ${itemVO.item_no}days + "天 " + ${itemVO.item_no}hours + "時 "+ ${itemVO.item_no}minutes + "分 " + ${itemVO.item_no}seconds + "秒 ";

  			// If the count down is finished, write some text 
  		
  			if (${itemVO.item_no}distance < 0) {
    		clearInterval(x);
    			document.getElementById("${itemVO.item_no}countdown").innerHTML = "";
    			alert("優惠已結束");
    			location.reload();
  			}
		}, 1000);
	</script>	
	</c:if>
	

	<script type="text/javascript">
	 //加入購物車
	$(document).ready(function () {
		$("#Item${itemVO.item_no}AddToCart").on("click",function(){

			var action = "ADD";
			var item_no = "${itemVO.item_no}";
			var item_price = $(".${itemVO.item_no}salePrice").text();
			if(item_price == ""){
				item_price = "${itemVO.item_price}";
			}
			var quantity = "1";
			$.ajax({
				url : "<%=request.getContextPath()%>/ShoppingCartServlet",
				method : "POST",
				data : {
					action:action,
					item_no:item_no,
					quantity:quantity,
					item_price:item_price
				}
			});
		});
	});
	</script>
						
					
					</c:forEach>
					</div>
				</div>
				
				<div class="item-table2" id="item-table2" style="display:none">
				<c:forEach var="itemVO" items="${list}" begin="<%= 0%>" end="${list.size()}">
				<FORM name="item${itemVO.item_no}" METHOD="post" ACTION="<%=request.getContextPath()%>/FrontEnd/query.do" TARGET="_blank"> <%-- 連結方法 ：將整個table用form包住用超連結送出--%>
						<input type="hidden" name="itemNo" value="${itemVO.item_no}">
						<input type="hidden" name="action" value="findOneItem">
				</FORM>
				<div class="col-md-3 rsidebar" style="width:285;text-align:center;">
					<div class="rsidebar-top" style="border: 0px; font-family:微軟正黑體;">
					
					<table>
						<tr>
						<td style="text-align:center;">
							<a href="javascript:document.getElementsByName('item${itemVO.item_no}')[0].submit();">
							<img src="data:image/*;base64,${itemVO.encoded}"  height="250" style="width: 100%;">
							</a>
						</td>
						</tr>
						<tr>
						<td style="height:75; text-align:left">
							${itemVO.item_name}
						</td>
						</tr>
						<tr>
						<td style="text-align:center">
							<h4>
							<c:if test="${limitSaleItem.contains(itemVO.item_no) && (limitSaleService.findOneSale(itemVO.item_no).getSale_end() > now)}">
							<del><span style="font-size: 25px;color:black;font-family:arial, helvetica, clean, sans-serif;">$<span class="itemPrice">${itemVO.item_price}</span></span></del>
							<span style="font-size: 25px;color:rgb(226, 28, 0);font-family:arial, helvetica, clean, sans-serif;">$<span class="${itemVO.item_no}salePrice2">${limitSaleService.findOneSale(itemVO.item_no).getSale_price()}</span></span>        	
                        	</c:if>
                        	<c:if test="${!limitSaleItem.contains(itemVO.item_no) || (limitSaleService.findOneSale(itemVO.item_no).getSale_end() < now)}"> 
                        		 <span style="font-size: 25px;color:rgb(226, 28, 0);font-family:arial, helvetica, clean, sans-serif;">$<span id="itemPrice">${itemVO.item_price}</span></span><br>
                        	</c:if>
							<input type="hidden" id="buyQuantity" value="1" />
							<input type="hidden" name="cmd" value="_cart" />
                            <input type="hidden" name="add" value="1" />
                            <input type="hidden" name="w3ls_item" value="Snow Blower" />
                            <input type="hidden" name="amount" value="540.00" />
							<button type="button" class="w3ls-cart" id="Item${itemVO.item_no}ToCart" style="font-family:微軟正黑體;" onclick="addCart()"><span class="glyphicon glyphicon-shopping-cart"></span> 加入購物車</button></h4>
						</td>
						</tr>
					</table>		
					</div>
				</div>
				
				
	<script type="text/javascript">
	 //加入購物車
	$(document).ready(function () {
		$("#Item${itemVO.item_no}ToCart").on("click",function(){

			var action = "ADD";
			var item_no = "${itemVO.item_no}";
			var item_price = $(".${itemVO.item_no}salePrice2").text();
			if(item_price == ""){
				item_price = "${itemVO.item_price}";
			}
			var quantity = "1";
			$.ajax({
				url : "<%=request.getContextPath()%>/ShoppingCartServlet",
				method : "POST",
				data : {
					action:action,
					item_no:item_no,
					quantity:quantity,
					item_price:item_price
				}
			});
		});
	});
	</script>
				</c:forEach>
				</div>
				
				
				
			</div>
			<div class="col-md-3 rsidebar">
				<div class="rsidebar-top">
					<div class="sidebar-row">
						<form name="form1" METHOD="post" ACTION="<%=request.getContextPath()%>/FrontEnd/query.do" >
							<input type="hidden" name="item_name" value="">
							<input type="hidden" name="action" value="searchMall">
				　　　　　　<!-- 用下列語法就運用超連結進行POST動作 -->
							<h4 style="font-family:微軟正黑體;"><a href="javascript:document.form1.submit();" style="color: #f44336; font-weight:bold">逛逛賣場</a></h4>
						</form>
						
						<h4 style="font-family:微軟正黑體;"><b>商品分類</b></h4>
						<ul class="faq" style="font-family:微軟正黑體; font-size:16px; font-weight:bold">
							<li class="item1"><a href="#"><b>服飾衣著</b><span class="glyphicon glyphicon-menu-down"></span></a>
								<ul>
									<li class="subitem1">
										<table style="table-layout:fixed; font-weight:bold">
											<tr>
												<td>
													<FORM METHOD="post" ACTION="query.do" TARGET="_blank">
														<input type="text" name="item_secondary_class" value="1" style="display: none;">
														<input type="hidden" name="action" value="findByPart">
														<input type="submit" value="長袖上衣" style="border:0px;background-color:white">
													</FORM>
												</td>
												<td>
													<FORM METHOD="post" ACTION="query.do" TARGET="_blank">
														<input type="text" name="item_secondary_class" value="2" style="display: none;">
														<input type="hidden" name="action" value="findByPart">
														<input type="submit" value="短袖上衣" style="border:0px;background-color:white">
													</FORM>
												</td>
											</tr>
											<tr>
												<td>
													<FORM METHOD="post" ACTION="query.do" TARGET="_blank">
														<input type="text" name="item_secondary_class" value="3" style="display: none;">
														<input type="hidden" name="action" value="findByPart">
														<input type="submit" value="背心" style="border:0px;background-color:white">
													</FORM>
												</td>
												<td>
													<FORM METHOD="post" ACTION="query.do" TARGET="_blank">
														<input type="text" name="item_secondary_class" value="4" style="display: none;">
														<input type="hidden" name="action" value="findByPart">
														<input type="submit" value="長褲" style="border:0px; background-color:white">
													</FORM>
												</td>
											</tr>
											<tr>
												<td>
													<FORM METHOD="post" ACTION="query.do" TARGET="_blank">
														<input type="text" name="item_secondary_class" value="5" style="display: none;">
														<input type="hidden" name="action" value="findByPart">
														<input type="submit" value="短褲" style="border:0px;background-color:white">
													</FORM>
												</td>
												<td>
													<FORM METHOD="post" ACTION="query.do" TARGET="_blank">
														<input type="text" name="item_secondary_class" value="6" style="display: none;">
														<input type="hidden" name="action" value="findByPart">
														<input type="submit" value="裙子" style="border:0px;background-color:white">
													</FORM>
												</td>
											</tr>
											<tr>
												<td>
													<FORM METHOD="post" ACTION="query.do" TARGET="_blank">
														<input type="text" name="item_secondary_class" value="7" style="display: none;">
														<input type="hidden" name="action" value="findByPart">
														<input type="submit" value="外套大衣" style="border:0px;background-color:white">
													</FORM>
												</td>
												<td>
													<FORM METHOD="post" ACTION="query.do" TARGET="_blank">
														<input type="text" name="item_secondary_class" value="8" style="display: none;">
														<input type="hidden" name="action" value="findByPart">
														<input type="submit" value="帽子" style="border:0px;background-color:white">
													</FORM>
												</td>
											</tr>
											<tr>
												<td>
													<FORM METHOD="post" ACTION="query.do" TARGET="_blank">
														<input type="text" name="item_secondary_class" value="9" style="display: none;">
														<input type="hidden" name="action" value="findByPart">
														<input type="submit" value="圍巾" style="border:0px;background-color:white">
													</FORM>
												</td>
												<td>
													<FORM METHOD="post" ACTION="query.do" TARGET="_blank">
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
							<li class="item2"><a href="#"><b>舌尖美食</b><span class="glyphicon glyphicon-menu-down"></span></a>
								<ul>
									<li class="subitem1">
										<table style="table-layout:fixed; font-weight:bold">
											<tr>
												<td>
													<FORM METHOD="post" ACTION="query.do" TARGET="_blank">
														<input type="text" name="item_secondary_class" value="11" style="display: none;">
														<input type="hidden" name="action" value="findByPart">
														<input type="submit" value="休閒零食" style="border:0px;background-color:white">
													</FORM>
												</td>
												<td>
													<FORM METHOD="post" ACTION="query.do" TARGET="_blank">
														<input type="text" name="item_secondary_class" value="12" style="display: none;">
														<input type="hidden" name="action" value="findByPart">
														<input type="submit" value="進口零食" style="border:0px;background-color:white">
													</FORM>
												</td>
											</tr>
											<tr>
												<td>
													<FORM METHOD="post" ACTION="query.do" TARGET="_blank">
														<input type="text" name="item_secondary_class" value="13" style="display: none;">
														<input type="hidden" name="action" value="findByPart">
														<input type="submit" value="生鮮食品" style="border:0px;background-color:white">
													</FORM>
												</td>
												<td>
													<FORM METHOD="post" ACTION="query.do" TARGET="_blank">
														<input type="text" name="item_secondary_class" value="14" style="display: none;">
														<input type="hidden" name="action" value="findByPart">
														<input type="submit" value="米食/麵食" style="border:0px;background-color:white">
													</FORM>
												</td>
											</tr>
											<tr>
												<td>
													<FORM METHOD="post" ACTION="query.do" TARGET="_blank">
														<input type="text" name="item_secondary_class" value="15" style="display: none;">
														<input type="hidden" name="action" value="findByPart">
														<input type="submit" value="飲料/沖泡品" style="border:0px;background-color:white">
													</FORM>
												</td>
												<td>
													<FORM METHOD="post" ACTION="query.do" TARGET="_blank">
														<input type="text" name="item_secondary_class" value="16" style="display: none;">
														<input type="hidden" name="action" value="findByPart">
														<input type="submit" value="蛋糕/甜點" style="border:0px;background-color:white">
													</FORM>
												</td>
											</tr>
											<tr>
												<td>
													<FORM METHOD="post" ACTION="query.do" TARGET="_blank">
														<input type="text" name="item_secondary_class" value="17" style="display: none;">
														<input type="hidden" name="action" value="findByPart">
														<input type="submit" value="南北雜貨" style="border:0px;background-color:white">
													</FORM>
												</td>
												<td>
													<FORM METHOD="post" ACTION="query.do" TARGET="_blank">
														<input type="text" name="item_secondary_class" value="18" style="display: none;">
														<input type="hidden" name="action" value="findByPart">
														<input type="submit" value="伴手禮" style="border:0px;background-color:white">
													</FORM>
												</td>
											</tr>
											<tr>
												<td>
													<FORM METHOD="post" ACTION="query.do" TARGET="_blank">
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
							<li class="item3"><a href="#"><b>遊戲王</b><span class="glyphicon glyphicon-menu-down"></span></a>
								<ul>
									<li class="subitem1">
										<table style="table-layout:fixed; font-weight:bold">
											<tr>
												<td>
													<FORM METHOD="post" ACTION="query.do" TARGET="_blank">
														<input type="text" name="item_secondary_class" value="20" style="display: none;">
														<input type="hidden" name="action" value="findByPart">
														<input type="submit" value="主機遊戲" style="border:0px;background-color:white">
													</FORM>
												</td>
												<td>
													<FORM METHOD="post" ACTION="query.do" TARGET="_blank">
														<input type="text" name="item_secondary_class" value="21" style="display: none;">
														<input type="hidden" name="action" value="findByPart">
														<input type="submit" value="掌上型電玩" style="border:0px;background-color:white">
													</FORM>
												</td>
											</tr>
											<tr>
												<td>
													<FORM METHOD="post" ACTION="query.do" TARGET="_blank">
														<input type="text" name="item_secondary_class" value="22" style="display: none;">
														<input type="hidden" name="action" value="findByPart">
														<input type="submit" value="益智遊戲" style="border:0px;background-color:white">
													</FORM>
												</td>
												<td>
													<FORM METHOD="post" ACTION="query.do" TARGET="_blank">
														<input type="text" name="item_secondary_class" value="23" style="display: none;">
														<input type="hidden" name="action" value="findByPart">
														<input type="submit" value="手機遊戲" style="border:0px;background-color:white">
													</FORM>
												</td>
											</tr>
											<tr>
												<td>
													<FORM METHOD="post" ACTION="query.do" TARGET="_blank">
														<input type="text" name="item_secondary_class" value="24" style="display: none;">
														<input type="hidden" name="action" value="findByPart">
														<input type="submit" value="電腦遊戲" style="border:0px;background-color:white">
													</FORM>
												</td>
												<td>
													<FORM METHOD="post" ACTION="query.do" TARGET="_blank">
														<input type="text" name="item_secondary_class" value="25" style="display: none;">
														<input type="hidden" name="action" value="findByPart">
														<input type="submit" value="遊戲周邊" style="border:0px;background-color:white">
													</FORM>
												</td>
											</tr>
											<tr>
												<td>
													<FORM METHOD="post" ACTION="query.do" TARGET="_blank">
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
							<li class="item3"><a href="#"><b>戶外休閒</b><span class="glyphicon glyphicon-menu-down"></span></a>
								<ul>
									<li class="subitem1">
										<table style="table-layout:fixed; font-weight:bold">
											<tr>
												<td>
													<FORM METHOD="post" ACTION="query.do" TARGET="_blank">
														<input type="text" name="item_secondary_class" value="27" style="display: none;">
														<input type="hidden" name="action" value="findByPart">
														<input type="submit" value="登山/露營" style="border:0px;background-color:white">
													</FORM>
												</td>
												<td>
													<FORM METHOD="post" ACTION="query.do" TARGET="_blank">
														<input type="text" name="item_secondary_class" value="28" style="display: none;">
														<input type="hidden" name="action" value="findByPart">
														<input type="submit" value="戶外用品" style="border:0px;background-color:white">
													</FORM>
												</td>
											</tr>
											<tr>
												<td>
													<FORM METHOD="post" ACTION="query.do" TARGET="_blank">
														<input type="text" name="item_secondary_class" value="29" style="display: none;">
														<input type="hidden" name="action" value="findByPart">
														<input type="submit" value="運動用品" style="border:0px;background-color:white">
													</FORM>
												</td>
												<td>
													<FORM METHOD="post" ACTION="query.do" TARGET="_blank">
														<input type="text" name="item_secondary_class" value="30" style="display: none;">
														<input type="hidden" name="action" value="findByPart">
														<input type="submit" value="運動健身" style="border:0px;background-color:white">
													</FORM>
												</td>
											</tr>
											<tr>
												<td>
													<FORM METHOD="post" ACTION="query.do" TARGET="_blank">
														<input type="text" name="item_secondary_class" value="31" style="display: none;">
														<input type="hidden" name="action" value="findByPart">
														<input type="submit" value="腳踏車" style="border:0px;background-color:white">
													</FORM>
												</td>
												<td>
													<FORM METHOD="post" ACTION="query.do" TARGET="_blank">
														<input type="text" name="item_secondary_class" value="32" style="display: none;">
														<input type="hidden" name="action" value="findByPart">
														<input type="submit" value="運動服飾" style="border:0px;background-color:white">
													</FORM>
												</td>
											</tr>
											<tr>
												<td>
													<FORM METHOD="post" ACTION="query.do" TARGET="_blank">
														<input type="text" name="item_secondary_class" value="33" style="display: none;">
														<input type="hidden" name="action" value="findByPart">
														<input type="submit" value="雨衣/雨具" style="border:0px;background-color:white">
													</FORM>
												</td>
												<td>
													<FORM METHOD="post" ACTION="query.do" TARGET="_blank">
														<input type="text" name="item_secondary_class" value="34" style="display: none;">
														<input type="hidden" name="action" value="findByPart">
														<input type="submit" value="鞋子" style="border:0px;background-color:white">
													</FORM>
												</td>
											</tr>
											<tr>
												<td>
													<FORM METHOD="post" ACTION="query.do" TARGET="_blank">
														<input type="text" name="item_secondary_class" value="35" style="display: none;">
														<input type="hidden" name="action" value="findByPart">
														<input type="submit" value="旅行相關" style="border:0px;background-color:white">
													</FORM>
												</td>
												<td>
													<FORM METHOD="post" ACTION="query.do" TARGET="_blank">
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
							<li class="item3"><a href="#"><b>家電用品</b><span class="glyphicon glyphicon-menu-down"></span></a>
								<ul>
									<li class="subitem1">
										<table style="table-layout:fixed; font-weight:bold">
											<tr>
												<td>
													<FORM METHOD="post" ACTION="query.do" TARGET="_blank">
														<input type="text" name="item_secondary_class" value="37" style="display: none;">
														<input type="hidden" name="action" value="findByPart">
														<input type="submit" value="生活家電" style="border:0px;background-color:white">
													</FORM>
												</td>
												<td>
													<FORM METHOD="post" ACTION="query.do" TARGET="_blank">
														<input type="text" name="item_secondary_class" value="38" style="display: none;">
														<input type="hidden" name="action" value="findByPart">
														<input type="submit" value="電視機" style="border:0px;background-color:white">
													</FORM>
												</td>
											</tr>
											<tr>
												<td>
													<FORM METHOD="post" ACTION="query.do" TARGET="_blank">
														<input type="text" name="item_secondary_class" value="39" style="display: none;">
														<input type="hidden" name="action" value="findByPart">
														<input type="submit" value="美容家電" style="border:0px;background-color:white">
													</FORM>
												</td>
												<td>
													<FORM METHOD="post" ACTION="query.do" TARGET="_blank">
														<input type="text" name="item_secondary_class" value="40" style="display: none;">
														<input type="hidden" name="action" value="findByPart">
														<input type="submit" value="影音設備" style="border:0px;background-color:white">
													</FORM>
												</td>
											</tr>
											<tr>
												<td>
													<FORM METHOD="post" ACTION="query.do" TARGET="_blank">
														<input type="text" name="item_secondary_class" value="41" style="display: none;">
														<input type="hidden" name="action" value="findByPart">
														<input type="submit" value="廚房家電" style="border:0px;background-color:white">
													</FORM>
												</td>
												<td>
													<FORM METHOD="post" ACTION="query.do" TARGET="_blank">
														<input type="text" name="item_secondary_class" value="42" style="display: none;">
														<input type="hidden" name="action" value="findByPart">
														<input type="submit" value="音響/喇叭" style="border:0px;background-color:white">
													</FORM>
												</td>
											</tr>
											<tr>
												<td>
													<FORM METHOD="post" ACTION="query.do" TARGET="_blank">
														<input type="text" name="item_secondary_class" value="43" style="display: none;">
														<input type="hidden" name="action" value="findByPart">
														<input type="submit" value="大型家電" style="border:0px;background-color:white">
													</FORM>
												</td>
												<td>
													<FORM METHOD="post" ACTION="query.do" TARGET="_blank">
														<input type="text" name="item_secondary_class" value="44" style="display: none;">
														<input type="hidden" name="action" value="findByPart">
														<input type="submit" value="其他" style="border:0px;background-color:white">
													</FORM>
												</td>
											</tr>
										</table>
								    </li>									
								</ul>
							</li>
							<li class="item3"><a href="#"><b>電競3C</b><span class="glyphicon glyphicon-menu-down"></span></a>
								<ul>
									<li class="subitem1">
										<table style="table-layout:fixed; font-weight:bold">
											<tr>
												<td>
													<FORM METHOD="post" ACTION="query.do" TARGET="_blank">
														<input type="text" name="item_secondary_class" value="45" style="display: none;">
														<input type="hidden" name="action" value="findByPart">
														<input type="submit" value="筆記型電腦" style="border:0px;background-color:white">
													</FORM>
												</td>
												<td>
													<FORM METHOD="post" ACTION="query.do" TARGET="_blank">
														<input type="text" name="item_secondary_class" value="46" style="display: none;">
														<input type="hidden" name="action" value="findByPart">
														<input type="submit" value="電腦零組件" style="border:0px;background-color:white">
													</FORM>
												</td>
											</tr>
											<tr>
												<td>
													<FORM METHOD="post" ACTION="query.do" TARGET="_blank">
														<input type="text" name="item_secondary_class" value="47" style="display: none;">
														<input type="hidden" name="action" value="findByPart">
														<input type="submit" value="鍵盤滑鼠" style="border:0px;background-color:white">
													</FORM>
												</td>
												<td>
													<FORM METHOD="post" ACTION="query.do" TARGET="_blank">
														<input type="text" name="item_secondary_class" value="48" style="display: none;">
														<input type="hidden" name="action" value="findByPart">
														<input type="submit" value="電腦周邊配件" style="border:0px;background-color:white">
													</FORM>
												</td>
											</tr>
											<tr>
												<td>
													<FORM METHOD="post" ACTION="query.do" TARGET="_blank">
														<input type="text" name="item_secondary_class" value="49" style="display: none;">
														<input type="hidden" name="action" value="findByPart">
														<input type="submit" value="儲存裝置" style="border:0px;background-color:white">
													</FORM>
												</td>
												<td>
													<FORM METHOD="post" ACTION="query.do" TARGET="_blank">
														<input type="text" name="item_secondary_class" value="50" style="display: none;">
														<input type="hidden" name="action" value="findByPart">
														<input type="submit" value="單眼/相機" style="border:0px;background-color:white">
													</FORM>
												</td>
											</tr>
											<tr>
												<td>
													<FORM METHOD="post" ACTION="query.do" TARGET="_blank">
														<input type="text" name="item_secondary_class" value="51" style="display: none;">
														<input type="hidden" name="action" value="findByPart">
														<input type="submit" value="鏡頭" style="border:0px;background-color:white">
													</FORM>
												</td>
												<td>
													<FORM METHOD="post" ACTION="query.do" TARGET="_blank">
														<input type="text" name="item_secondary_class" value="52" style="display: none;">
														<input type="hidden" name="action" value="findByPart">
														<input type="submit" value="相機周邊配件" style="border:0px;background-color:white">
													</FORM>
												</td>
											</tr>
											<tr>
												<td>
													<FORM METHOD="post" ACTION="query.do" TARGET="_blank">
														<input type="text" name="item_secondary_class" value="53" style="display: none;">
														<input type="hidden" name="action" value="findByPart">
														<input type="submit" value="穿戴智能裝置" style="border:0px;background-color:white">
													</FORM>
												</td>
												<td>
													<FORM METHOD="post" ACTION="query.do" TARGET="_blank">
														<input type="text" name="item_secondary_class" value="54" style="display: none;">
														<input type="hidden" name="action" value="findByPart">
														<input type="submit" value="耳機/麥克風" style="border:0px;background-color:white">
													</FORM>
												</td>
											</tr>
											<tr>
												<td>
													<FORM METHOD="post" ACTION="query.do" TARGET="_blank">
														<input type="text" name="item_secondary_class" value="55" style="display: none;">
														<input type="hidden" name="action" value="findByPart">
														<input type="submit" value="其他" style="border:0px;background-color:white">
													</FORM>
												</td>
											</tr>
										</table>
								    </li>									
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