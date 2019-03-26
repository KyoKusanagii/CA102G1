<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.carousel.model.*"%>
<%@ page import="com.randomNewItem.model.*"%>
<%@ page import="com.item.model.*"%>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.limitSaleSub.model.*"%>

<%
    CarouselService carouselSvc = new CarouselService();
    List<CarouselVO> carouselList = carouselSvc.getAll();
    pageContext.setAttribute("carouselList",carouselList);
%>
	<%application.getAttribute("randomItemList");%>
	
<%
	Set<LimitSaleSubVO> lsList = (Set<LimitSaleSubVO>) application.getAttribute("lsList");
	Iterator<LimitSaleSubVO> it = lsList.iterator();
	while(it.hasNext()){
		LimitSaleSubVO vo = it.next();
		if(vo.getSale_end().before(new Date())){
			it.remove();
		}
	}
	if(lsList.isEmpty()){
		LimitSaleSubService lmSvc = new LimitSaleSubService();
		lsList = lmSvc.getRandomFive();
	}
	application.setAttribute("lsList", lsList);

	Date date = new Date();
	pageContext.setAttribute("date", date);
%>	

<% 
 	RandomItemService riSvc = new RandomItemService();
 	if(session.getAttribute("memVO") == null || session.getAttribute("CLOTH_CNTS") == null){
 		session.setAttribute("preference", riSvc.getRandomFiveItemsByCategory(1));
 	}else{
 		Integer cloth = (Integer) session.getAttribute("CLOTH_CNTS");
     	Integer food = (Integer) session.getAttribute("FOOD_CNTS");
    	Integer game = (Integer) session.getAttribute("GAME_CNTS");
     	Integer outdoor = (Integer) session.getAttribute("OUTDOOR_CNTS");
     	Integer homeelec = (Integer) session.getAttribute("HOMEELEC_CNTS");
     	Integer threec = (Integer) session.getAttribute("THREEC_CNTS");
 		if((cloth + food + game + outdoor + homeelec + threec) > 0 ){
 			int a[] = {cloth, food, game, outdoor, homeelec, threec};
	    	
 	    	int theMost = a[0];
	    	
 	    	for(int i = 0; i < a.length; i++){
 	    		if(a[i] > theMost){
 	    			theMost = a[i];
 	    		}
 	    	}
	    	
 	    	if(theMost == cloth){
 	    		session.setAttribute("preference", riSvc.getRandomFiveItemsByCategory(1));
 	    	}else if(theMost == food){
	    		session.setAttribute("preference", riSvc.getRandomFiveItemsByCategory(2));
 	    	}else if(theMost == game){
	    		session.setAttribute("preference", riSvc.getRandomFiveItemsByCategory(3));
 	    	}else if(theMost == outdoor){
 	    		session.setAttribute("preference", riSvc.getRandomFiveItemsByCategory(4));
	    	}else if(theMost == homeelec){
 	    		session.setAttribute("preference", riSvc.getRandomFiveItemsByCategory(5));
 	    	}else if(theMost == threec){
 	    		session.setAttribute("preference", riSvc.getRandomFiveItemsByCategory(6));
    	}
		}
    	
 	}

 %>
    
<html>
<head>
<title>Home</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="" />
<script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false);
		function hideURLbar(){ window.scrollTo(0,1); } </script>
<!-- Custom Theme files -->
<link href="css/bootstrap.css" rel="stylesheet" type="text/css" media="all" />
<link href="css/style.css" rel="stylesheet" type="text/css" media="all" /> 
<link href="css/menu.css" rel="stylesheet" type="text/css" media="all" /> <!-- menu style --> 
<link href="css/ken-burns.css" rel="stylesheet" type="text/css" media="all" /> <!-- banner slider --> 
<link href="css/animate.min.css" rel="stylesheet" type="text/css" media="all" /> 
<link href="css/owl.carousel.css" rel="stylesheet" type="text/css" media="all"> <!-- carousel slider -->  
<!-- //Custom Theme files -->
<!-- font-awesome icons -->
<link href="css/font-awesome.css" rel="stylesheet">
<!-- //font-awesome icons -->
<!-- js -->
<script src="js/jquery-2.2.3.min.js"></script> 
<!-- //js --> 
<!-- web-fonts -->
<link href='https://fonts.googleapis.com/css?family=Roboto+Condensed:400,300,300italic,400italic,700,700italic' rel='stylesheet' type='text/css'>
<link href='https://fonts.googleapis.com/css?family=Lovers+Quarrel' rel='stylesheet' type='text/css'>
<link href='https://fonts.googleapis.com/css?family=Offside' rel='stylesheet' type='text/css'>
<link href='https://fonts.googleapis.com/css?family=Tangerine:400,700' rel='stylesheet' type='text/css'>
<!-- web-fonts --> 

<!-- 幻燈片JS -->
<script src="js/owl.carousel.js"></script>  
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

 
$(document).ready(function () {
      $('.banner').find('.item').first().addClass('active');
      $('.banner').find('.kb_caption_right').last().addClass('kb_caption_center').removeClass('kb_caption_right');
      $('.banner').css('font-family',"Microsoft JhengHei");
    });
</script>
<!-- 幻燈片JS結束 -->
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
<script type="text/javascript" src="js/move-top.js"></script>
<script type="text/javascript" src="js/easing.js"></script>	
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
<script src="js/bootstrap.js"></script>	
<!-- 	讓模板的商品幻燈片字好看一些 -->
<style>
	.glry-w3agile-grids.agileits h4, .glry-w3agile-grids.agileits p {
		color:white;
		text-overflow: ellipsis;
		white-space: nowrap;
		overflow:hidden;
		font-family:"微軟正黑體";
	}
</style>
<!-- 	讓模板的字好看一些 -->
<!-- 限時特賣用JS -->
<script>
     function cw(e){
        var countDownDate = new Date(e).getTime();
        var x = setInterval(function() {
            var now = new Date().getTime();
            var distance = countDownDate - now;
            var days = Math.floor(distance / (1000 * 60 * 60 * 24));
            var hours = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
            var minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
            var seconds = Math.floor((distance % (1000 * 60)) / 1000);

            document.getElementById(e).innerHTML = days + "d " + hours + "h "
            + minutes + "m " + seconds + "s ";

            if (distance < 0) {
                clearInterval(x);
                document.getElementById(e).innerHTML = "EXPIRED";
            }
        }, 1000);
     }
</script>

<div id="fb-root"></div>
<script>(function(d, s, id) {
  var js, fjs = d.getElementsByTagName(s)[0];
  if (d.getElementById(id)) return;
  js = d.createElement(s); js.id = id;
  js.src = 'https://connect.facebook.net/zh_TW/sdk.js#xfbml=1&autoLogAppEvents=1&version=v3.1&appId=221160448483712';
  fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));</script>
</head>
<body>
	<!-- header -->
	<div class="header">
		<%@ include file="/FrontEnd/headerFooter/header.jsp"%>
	</div>
	<!-- banner -->
	<div class="banner">
		<div id="kb" class="carousel kb_elastic animate_text kb_wrapper" data-ride="carousel" data-interval="6000" data-pause="hover">
			<!-- Wrapper-for-Slides -->
            <div class="carousel-inner" role="listbox" id="noactive">  
            	<c:forEach var="carouselVO" items="${carouselList}">
                <div class="item"><!-- First-Slide -->
                    <img src="data:image/png;base64,${carouselVO.byteString}" alt="" class="img-responsive" />
                    <div class="carousel-caption kb_caption kb_caption_right">
                        <h3 data-animation="animated flipInX">${carouselVO.carousel_title}</h3>
                        <h4 data-animation="animated flipInX">${carouselVO.carousel_subTitle}</h4>
                    </div>
                </div>  
                </c:forEach>
            </div> 
            <!-- Left-Button -->
            <a class="left carousel-control kb_control_left" href="#kb" role="button" data-slide="prev">
				<span class="fa fa-angle-left kb_icons" aria-hidden="true"></span>
                <span class="sr-only">Previous</span>
            </a> 
            <!-- Right-Button -->
            <a class="right carousel-control kb_control_right" href="#kb" role="button" data-slide="next">
                <span class="fa fa-angle-right kb_icons" aria-hidden="true"></span>
                <span class="sr-only">Next</span>
            </a> 
        </div>
		<script src="js/custom.js"></script>
	</div>
	<!-- //banner -->  
	<!-- deals -->
	<div class="deals"> 
		<div class="container"> 
			<h3 class="w3ls-title" style="font-family: 微軟正黑體;">分類項目</h3>
			<div class="deals-row">
				<div class="col-md-2 focus-grid w3focus-grid-mdl">
					<FORM name='cat1' METHOD="post" ACTION="query.do">
					<a href='javascript:document.cat1.submit();' class=" wthree-btn wthree4">
						<div class="focus-image"><i class="fa fa-shirtsinbulk"></i></div>
						<h4 class="clrchg">
							<input type="text" name="item_primary_class" value="1" style="display: none;">
							<input type="hidden" name="action" value="findByCat">
							服飾衣著
						</h4>
					</a>
					</FORM>
					</div>
				</div>
				<div class="col-md-2 focus-grid"> 
					<FORM name='cat2' METHOD="post" ACTION="query.do">
					<a href='javascript:document.cat2.submit();' class="wthree-btn wthree2"> 
						<div class="focus-image"><i class="fa fa-cutlery"></i></div>
						<h4 class="clrchg" type="submit">
							<input type="text" name="item_primary_class" value="2" style="display: none;">
							<input type="hidden" name="action" value="findByCat">
							舌尖美食
						</h4> 
					</a>
					</FORM>
				</div>
				<div class="col-md-2 focus-grid"> 
					<FORM name='cat3' METHOD="post" ACTION="query.do">
					<a href='javascript:document.cat3.submit();' class="wthree-btn wthree3"> 
						<div class="focus-image"><i class="fa fa-gamepad"></i></div>
						<h4 class="clrchg">
							<input type="text" name="item_primary_class" value="3" style="display: none;">
							<input type="hidden" name="action" value="findByCat">
							遊戲王
						</h4> 
					</a>
					</FORM>
				</div>
				<div class="col-md-2 focus-grid"> 
					<FORM name='cat4' METHOD="post" ACTION="query.do">
					<a href='javascript:document.cat4.submit();' class="wthree-btn wthree5"> 
						<div class="focus-image"><i class="fa fa-futbol-o"></i></div>
						<h4 class="clrchg">
							<input type="text" name="item_primary_class" value="4" style="display: none;">
							<input type="hidden" name="action" value="findByCat">
							戶外休閒
						</h4> 
					</a>
					</FORM>
				</div>
				<div class="col-md-2 focus-grid">
					<FORM name='cat5' METHOD="post" ACTION="query.do"> 
					<a href='javascript:document.cat5.submit();' class="wthree-btn wthree1"> 
						<div class="focus-image"><i class="fa fa-desktop"></i></div>
						<h4 class="clrchg">
							<input type="text" name="item_primary_class" value="5" style="display: none;">
							<input type="hidden" name="action" value="findByCat">
							家電用品
						</h4> 
					</a>
					</FORM>
				</div>
				<div class="col-md-2 focus-grid w3focus-grid-mdl">
					<FORM name='cat6' METHOD="post" ACTION="query.do"> 
					<a href='javascript:document.cat6.submit();' class="wthree-btn wthree3"> 
						<div class="focus-image"><i class="fa fa-laptop"></i></div>
						<h4 class="clrchg">
							<input type="text" name="item_primary_class" value="6" style="display: none;">
							<input type="hidden" name="action" value="findByCat">
							電競3C
						</h4>
					</a>
					</FORM>
				</div>
				<div class="clearfix"> </div>
			</div>  	
		</div>  	

	<!-- //deals -->

	<!-- coming soon -->
	<jsp:useBean id="limitSaleSvc" scope="page" class="com.limitSale.model.LimitSaleService" />
	<jsp:useBean id="itempicSvc" scope="page" class="com.itempic.model.ItempicService" />
	<jsp:useBean id="itemSvc" scope="page" class="com.item.model.ItemService" />
	<c:forEach var="limitSaleVO" items="${limitSaleSvc.findHomePage()}">
		<div class="soon">
			<div class="container">
			
			<FORM name='item${limitSaleVO.item_no}' METHOD="post" ACTION="query.do" target="_blank">
				<input type="hidden" name="itemNo" value="${limitSaleVO.item_no}">
				<input type="hidden" name="action" value="findOneItem">
				<a href="<%=request.getContextPath()%>/FrontEnd/query.do?action=findOneItem&itemNo=${limitSaleVO.item_no}" target="_blank"><h3>${itemSvc.getOneItem(limitSaleVO.item_no).item_name}</h3></a>
				<h2 style="color:white;font-weight:bold;margin-top:10px;text-decoration:line-through">原價:${itemSvc.getOneItem(limitSaleVO.item_no).item_price} 元</h2>
				<h1 style="color:red;font-weight:bold">限時優惠價: ${limitSaleVO.sale_price} 元<i class="fa fa-exclamation"></i><i class="fa fa-exclamation"></i></h1> 
				<h4>${limitSaleVO.sale_remark}</h4>
				<input type="image" name="submit" src="data:image/png;base64,${itempicSvc.getThumbnail(limitSaleVO.item_no).encoded}" alt="Submit" class="img-responsive" style="display:block; margin:auto;" />
				<div id="${limitSaleVO.sale_no}" class="ClassyCountdownDemo"  style="margin-top:20px"></div>
			</FORM>
			</div> 
		</div>	
	</c:forEach>
	<!-- //coming soon -->


	<!-- welcome -->
	<div class="welcome"> 
		<div class="container"> 
			<div class="welcome-info">
				<div class="bs-example bs-example-tabs" role="tabpanel" data-example-id="togglable-tabs">
					<div class="clearfix"> </div>
					<h3 class="w3ls-title flash" style="font-family: 微軟正黑體;">限時特賣</h3>
					<div id="myTabContent" class="tab-content">
						<div role="tabpanel" class="tab-pane fade in active" id="home" aria-labelledby="home-tab">
							<div class="tabcontent-grids">  
								<div id="owl-demo" class="owl-carousel"> 
									
<c:forEach var="Item" items="${lsList}">								
									<div class="item">
										<div class="glry-w3agile-grids agileits" style="position:relative;"> 
											<img src="data:image;base64,${Item.byteString}" width="175" height="210" alt="img">
											
											<div class="view-caption agileits-w3layouts">           
												<h4>${Item.item_name}</h4>
												<p>${Item.sale_remark}</p>
												<h5 style="background-color:red;">$${Item.sale_price}</h5> 
												<form action="<%=request.getContextPath()%>/FrontEnd/query.do" method="post">
													<input type="hidden" name="itemNo" value="${Item.item_no}" />
													<input type="hidden" name="action" value="findOneItem">
													<button type="submit" class="w3ls-cart" ><i class="fa fa-eye" aria-hidden="true"></i>查看商品</button>
												</form>
											</div>   
										</div>   
										<p id="${Item.sale_end}" style="color:red;font-size:20px;"></p>
										<script type="text/javascript">cw('${Item.sale_end}');</script>
										<p style="color:red;font-size:20px;"><strike>不用${Item.item_price}元!!</strike></p>
									</div>
</c:forEach>									
																									
									</div>
								</div> 
							</div>
						</div>
					</div>  
				</div>  
			</div>  	
		</div>  	
	</div> 
	<!-- //welcome -->
	<div class="welcome"> 
		<div class="container"> 
			<div class="welcome-info">
				<div class="bs-example bs-example-tabs" role="tabpanel" data-example-id="togglable-tabs">
					<div class="clearfix"> </div>
					<h3 class="w3ls-title" style="font-family: 微軟正黑體;">會員專屬推薦</h3>
					<div id="myTabContent" class="tab-content">
						<div role="tabpanel" class="tab-pane fade in active" id="home" aria-labelledby="home-tab">
							<div class="tabcontent-grids">  
								<div id="owl-demo1" class="owl-carousel"> 
								
<c:forEach var="preferItem" items="${preference}">								
								
									<div class="item">
										<div class="glry-w3agile-grids agileits"> 
											<img src="data:image;base64,${preferItem.encoded}" width="100" height="200" alt="img">
											<div class="view-caption agileits-w3layouts">           
												<h4>${preferItem.item_name}</h4>
												<p>${preferItem.item_description}</p>
												<h5>$${preferItem.item_price}</h5>
												<form action="<%=request.getContextPath()%>/FrontEnd/query.do" method="post">
													<input type="hidden" name="itemNo" value="${preferItem.item_no}" />
													<input type="hidden" name="action" value="findOneItem">
													<button type="submit" class="w3ls-cart" ><i class="fa fa-eye" aria-hidden="true"></i>查看商品</button>
												</form>
											</div>        
										</div>    
									</div>
									
</c:forEach>																		
																
									
								</div>   
							</div>
						</div> 
						<div role="tabpanel" class="tab-pane fade" id="carl" aria-labelledby="carl-tab">
							<div class="tabcontent-grids">
								<script>
									$(document).ready(function() { 
										$("#owl-demo1").owlCarousel({
									 
										  autoPlay: 3000, //Set AutoPlay to 3 seconds
									 
										  items :4,
										  itemsDesktop : [640,5],
										  itemsDesktopSmall : [414,4],
										  navigation : true
									 
										});
										
									}); 
								</script>   
							</div>
						</div> 
					</div>   
				</div>  
			</div>  	
		</div>  	
	</div> 
	<!-- //welcome -->

	<div class="welcome"> 
		<div class="container"> 
			<div class="welcome-info">
				<div class="bs-example bs-example-tabs" role="tabpanel" data-example-id="togglable-tabs">
					<div class="clearfix"> </div>
					<h3 class="w3ls-title" style="font-family: 微軟正黑體;">每日新發現</h3>
					<div id="myTabContent" class="tab-content">
						<div role="tabpanel" class="tab-pane fade in active" id="home" aria-labelledby="home-tab">
							<div class="tabcontent-grids">  
								<div id="owl-demo2" class="owl-carousel"> 
								
<c:forEach var="Item" items="${randomItemList}">
									<div class="item">
										<div class="glry-w3agile-grids agileits"> 
											<img src="data:image;base64,${Item.encoded}" width="100" height="200" alt="img">
											<div class="view-caption agileits-w3layouts">           
												<h4>${Item.item_name}</h4>
												<p>${Item.item_description}</p>
												<h5>$${Item.item_price}</h5>
												<form action="<%=request.getContextPath()%>/FrontEnd/query.do" method="post">
													<input type="hidden" name="itemNo" value="${Item.item_no}" />
													<input type="hidden" name="action" value="findOneItem">
													<button type="submit" class="w3ls-cart" ><i class="fa fa-eye" aria-hidden="true"></i>查看商品</button>
												</form>
											</div>        
										</div>    
									</div>
</c:forEach>									
								</div>   
							</div>
						</div> 
						<div role="tabpanel" class="tab-pane fade" id="carl" aria-labelledby="carl-tab">
							<div class="tabcontent-grids">
								<script>
									$(document).ready(function() { 
										$("#owl-demo2").owlCarousel({
									 
										  autoPlay: 3000, //Set AutoPlay to 3 seconds
									 
										  items :4,
										  itemsDesktop : [640,5],
										  itemsDesktopSmall : [414,4],
										  navigation : true
									 
										});
										
									}); 
								</script>   
							</div>
						</div> 
					</div>   
				</div>  
			</div>  	
		</div>  	
	</div> 
	<!-- //welcome -->
	
	
	<!-- cart-js -->
	<script src="js/minicart.js"></script>
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
	<!-- countdown.js -->	
	<script src="js/jquery.knob.js"></script>
	<script src="js/jquery.throttle.js"></script>
	<script src="js/jquery.classycountdown.js"></script>
	
	<% SimpleDateFormat  sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	pageContext.setAttribute("sdf",sdf);
%>
	<c:forEach var="limitSaleVO" items="${limitSaleSvc.findHomePage()}">
		<script>
			$(document).ready(function() {
				$('#${limitSaleVO.sale_no}').ClassyCountdown({
					end: '${sdf.parse(limitSaleVO.sale_end).getTime()/1000}',
					now: '${sdf.parse(sdf.format(date)).getTime()/1000}',
					labels: true,
					style: {
						element: "",
						textResponsive: .5,
						days: {
							gauge: {
								thickness: .10,
								bgColor: "rgba(0,0,0,0)",
								fgColor: "#1abc9c",
								lineCap: 'round'
							},
							textCSS: 'font-weight:300; color:#fff;'
						},
						hours: {
							gauge: {
								thickness: .10,
								bgColor: "rgba(0,0,0,0)",
								fgColor: "#05BEF6",
								lineCap: 'round'
							},
							textCSS: ' font-weight:300; color:#fff;'
						},
						minutes: {
							gauge: {
								thickness: .10,
								bgColor: "rgba(0,0,0,0)",
								fgColor: "#8e44ad",
								lineCap: 'round'
							},
							textCSS: ' font-weight:300; color:#fff;'
						},
						seconds: {
							gauge: {
								thickness: .10,
								bgColor: "rgba(0,0,0,0)",
								fgColor: "#f39c12",
								lineCap: 'round'
							},
							textCSS: ' font-weight:300; color:#fff;'
						}

					},
					onEndCallback: function() {
						window.alert("限時特賣已結束!");
					}
				});
			});
		</script>
		</c:forEach>
	<!-- //countdown.js -->
	<!-- menu js aim -->
	<script src="js/jquery.menu-aim.js"> </script>
	<script src="js/main.js"></script> <!-- Resource jQuery -->
	<!-- //menu js aim --> 
	<!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster --> 
    	<div id="footer">
		<%@ include file="/FrontEnd/headerFooter/footer.html"%>
	</div>
</body>
</html>