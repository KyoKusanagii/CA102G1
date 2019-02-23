<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,com.member.model.MemVO,com.follow_item.model.*,com.item_report.model.*,com.item.model.*,com.limitSale.model.*"%>
<%@ page import="java.sql.Timestamp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="itemReportService" scope="page" class="com.item_report.model.ItemReportService"/>
<jsp:useBean id="limitSaleService" scope="page" class="com.limitSale.model.LimitSaleService"/>
<%
	List<ItemReportVO> itemReportList = (List<ItemReportVO>)itemReportService.getAllItemReport();
	Set<String> item_no_of_report = new HashSet<>();
	for(int i=0;i<itemReportList.size();i++){
		if(itemReportList.get(i).getReport_Status() == 0)
			item_no_of_report.add(itemReportList.get(i).getItem_NO());	//收集所有被檢舉但未處理的商品編號
	}
	String thisItemNo = ((ItemVO)request.getAttribute("itemInfo")).getItem_no();
	boolean item_is_report = item_no_of_report.contains(thisItemNo);
	pageContext.setAttribute("item_no_of_report",item_no_of_report);
	
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
<html>
<head>
<%@ include file="/FrontEnd/headerFooter/head.jsp"%>

	<!-- 倒數樣式套件 -->
<link rel="stylesheet" href="<%=request.getContextPath()%>/FrontEnd/css/social.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/FrontEnd/css/timeTo.css">
<script src="<%=request.getContextPath()%>/FrontEnd/js/jquery.time-to.js"></script>
<script src="<%=request.getContextPath()%>/FrontEnd/js/jquery.time-to.min.js"></script>
<script src="<%=request.getContextPath()%>/FrontEnd/js/jQuery-BEShare.min.js"></script>
<script src="<%=request.getContextPath()%>/FrontEnd/js/script.js"></script>
    <title>商品頁面</title>
    <!-- the mousewheel plugin -->
    <style type="text/css">
        .btn-default {
            height: 40px;
            padding-top: 8px;
            padding-bottom: 8px;
            padding-left: 16px;
            padding-right: 16px;
        }

        #buyQuantity {
            width: 50px;
            margin-left: 10px;
            margin-right: 10px;
            border: solid 1px;
            font-size: 20px;
            height: 40px;
            text-align: center;
        }

        .dropdown-menu-buyWhat {
            margin-left: -80px;
        }

        .dropdown-menu-buyWhat>li>a:hover {
            color: #fff;
            background-color: white;
        }

        .carousel-indicators li {
            display: inline-block;
            border-color: black;
        }

        .carousel-indicators .active {
            border-color: black;
            background-color: black;
        }
    </style>
    <!-- load header footer script -->
    
    <script>
    $(document).ready(function () {
        var state = true;
        var addToCartTimes = 0; //加入購物車的次數
        $("#follow").click(function () {
            $(this).attr("class", state ? "glyphicon glyphicon-heart-empty" : "glyphicon glyphicon-heart");
            state = !state;
        });
        $("#addItem").click(function () {
            if (parseInt($("#itemInventory").text()) == 0) {
                alert("該項商品目前缺貨中");
                return;
            }

            if (parseInt($("#buyQuantity").text()) == parseInt($("#itemInventory").text())) {
                alert("購買數量大於庫存數量，請重新選擇數量");
                return;
            }
            $("#buyQuantity").text(parseInt($("#buyQuantity").text()) + 1);
        });
        $("#deleteItem").click(function () {
            if ($("#buyQuantity").text() == "1") {
                return;
            }
            $("#buyQuantity").text(parseInt($("#buyQuantity").text()) - 1);
        });
        $("#resetQuantity").click(function () {
            $("#buyQuantity").text("1");
        });

        $("#thisItemAddToCart").click(function () {
            if ($("#itemInventory").text() == 0) {
                alert("該項商品目前缺貨中");
                return;
            }
           alert("已加入至購物車");
            addToCartTimes++;
            $("#cartNotify").text(addToCartTimes);
            let thisItemInventory = $("#itemInventory").text();
            $("#itemInventory").text(parseInt(thisItemInventory) - parseInt($("#buyQuantity").text()));
            $("#pay").before("<li class='list-group-item item-canclear'><a href='#' style='color: black;" +
                "font-size: 15px;'>商品名稱" + $("#itemName").text() + "<br>商品價格:" +
                $("#itemPrice").text() + "<br>購買數量:" + $("#buyQuantity").text() + "</a></li>");
        });

        $("#shoppingCart").click(function () {
            if ($("#cartNotify").text() != "")
                $("#nothingInCart").hide();
            else if ($("#cartNotify").text() == "")
                $("#nothingInCart").show();
        });
        $("#cleanCart").click(function () { //清空購物車
            $(".item-canclear").remove();
            //$("#itemInventory").text(parseInt($("#buyQuantity").text()+)
            addToCartTimes = 0;
            $("#cartNotify").text("");
        });
    });
    </script>
    
    
    
    <script>
        //自己寫的script區塊
        $(document).ready(function() {  //document.ready記得一定要寫
        	

            $("#reportOpinion").focus(function() {
                $(this).attr("placeholder", "");
            });
            $("#reportOpinion").blur(function() {
                $(this).attr("placeholder", "請輸入其他意見");
            });
            $("#reportItem").click(function(){	//按下檢舉商品後，會顯示商品的名稱
            	$("#itemNameOfReport").text($("#itemName").text());
            });
            $(".modal-footer button").click(function() { //按下確定檢舉後
            	if($(this).attr("id")=="sendReport"){
                    let reasons = document.getElementsByName("itemReportReason");	//取的所有的checkbox
                    let itemReasons="";	//把所有的檢舉原因變成一個字串
                    for(let i=0;i<reasons.length;i++){		//如果checkbox被勾取
                        if(reasons[i].checked){
                            itemReasons+=$("#reason"+(i+1)).text();	//將原因加入至字串                                       
                            itemReasons+=",";                        
                        }
                    }
                    if(itemReasons.charAt(itemReasons.length-1)==","){  //如果原因的最後一個字是逗號
                       itemReasons = itemReasons.slice(0,itemReasons.length-1); //原因的字串擷取逗號至前一個字元
                    }
                    let itemNo = "${itemInfo.item_no}";
                    let itemPicStr = $(".carousel-inner .active img").attr("src"); //拿actvie的商品圖片的src
                    $.ajax({
                        url: "<%=request.getContextPath()%>/addItemReport",
                        type: "post",      
                        data:{
                        	itemReportDescription:$("#reportOpinion").val(),	//textarea打的字一樣要用val()非text()
                        	itemNo:itemNo,
                        	itemReporter:"${memVO.mem_no}",
                        	itemReasons:itemReasons,
                        	itemPic:itemPicStr.substring(itemPicStr.indexOf(",") + 1)	//再把商品圖片的src取base64那段
                        },
                        success: function() {
                        	location.reload();
                            alert("檢舉成功");
                        }
                    });
            	}else{
            		$("input:checkbox[name='itemReportReason']").attr("checked",false);
            		$("#reportOpinion").val("");        		
            	}          
            });

        });
    </script>
    
		<!--     修澤 -->
    <script type="text/javascript">
	 //關注商品
	$(document).ready(function () {
	 
	      $("#addFolo_item").on("click",function(){
	       var action = "folo_item";
	       var mem_no = "${memVO.mem_no}";
	   	   var item_no = "${itemInfo.item_no}";
	       var collectMessage = document.getElementById("collectMessage");
	    $.ajax({
	     url : "<%=request.getContextPath()%>/follow.do",
	     method : "POST",
	     data : {
	      action:action,
	      mem_no:mem_no,
	      item_no:item_no 
	     }
	    });
	   });
	  });
	 </script>
	 
    <script type="text/javascript">
	 //加入購物車
	$(document).ready(function () {
	      $("#thisItemAddToCart").on("click",function(){
	       var action = "ADD";
	   	   var item_no = "${itemInfo.item_no}";
	 	   var item_price = $(".itemPrice:eq(1)").text();
	 	   if(item_price == ""){
	 		  item_price = $("#itemPrice").text();
	 	   }
	    $.ajax({
	     url : "<%=request.getContextPath()%>/ShoppingCartServlet",
	     method : "POST",
	     data : {
	      action:action,
	      item_no:item_no,
	      quantity:$("#buyQuantity").text(),
	      item_price:item_price
	     }
	    });
	 	  $("#buyQuantity").text("1");
	   });
	  });
	 </script>
	<script>
			// Set the date we're counting down to
			var countDownDate = new Date("${limitSaleService.findOneSale(itemInfo.item_no).getSale_end()}").getTime();

			// Update the count down every 1 second
			var x = setInterval(function() {

  			// Get todays date and time
  			var now = new Date().getTime();

  			// Find the distance between now and the count down date
  			var distance = countDownDate - now;
 			 // Time calculations for days, hours, minutes and seconds
 			var days = Math.floor(distance / (1000 * 60 * 60 * 24));
 			var hours = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
  			var minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
  			var seconds = Math.floor((distance % (1000 * 60)) / 1000);

  			// Display the result in the element with id="demo"
  			document.getElementById("countdown").innerHTML = days + "天 " + hours + "時 "+ minutes + "分 " + seconds + "秒 ";

  			// If the count down is finished, write some text 
  		
  			if (distance < 0) {
    		clearInterval(x);
    			document.getElementById("countdown").innerHTML = "";
    			alert("優惠已結束");
    			location.reload();
  			}
		}, 1000);
	</script>
	 
    
			<!--     為了讓字更好看 -->
    <style>
		.glry-w3agile-grids.agileits h4, .glry-w3agile-grids.agileits p {
			color:white;
			text-overflow: ellipsis;
			white-space: nowrap;
			overflow:hidden;
			font-family:"微軟正黑體";
		}
	</style>
</head>

<body>

	<!-- Load Facebook SDK for JavaScript -->
<!--   	<div id="fb-root"></div> -->
<!--   	<script>(function(d, s, id) { -->
<!--      	var js, fjs = d.getElementsByTagName(s)[0]; -->
<!--      	if (d.getElementById(id)) return; -->
<!--      	js = d.createElement(s); js.id = id; -->
<!--      	js.src = "https://connect.facebook.net/en_US/sdk.js#xfbml=1&version=v3.0"; -->
<!--      	fjs.parentNode.insertBefore(js, fjs); -->
<!--    	}(document, 'script', 'facebook-jssdk')); -->
<!--   	</script> -->

<!--   <!-- Your share button code --> 
<!--   <div class="fb-share-button"  -->
<!--     data-href="https://www.your-domain.com/your-page.html"  -->
<!--     data-layout="button_count"> -->
<!--   </div> -->
  
    <!-- header -->
    <div id="header">
        <%@ include file="/FrontEnd/headerFooter/header1.jsp" %>
        <div class="header-two">
            <!-- header-two -->
            <div class="container">
                <div class="header-logo">
                    <h1><a href="<%=request.getContextPath()%>/FrontEnd/index.jsp"><span>Insta</span>Buy <i style="font-family: 微軟正黑體;font-size: 14px">in直拍</i></a></h1>
                </div>
               <div class="header-search">
				<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/FrontEnd/query.do" style="text-align: left">
					<input type="search" name="item_name" placeholder="Search by keyword" style="width:500px;">
					<select class="select" name="action" style="height:50px; width:70px; border:0; background: #f44336; outline: none; color:#FFFFFF; font-family: 微軟正黑體;">
						<option value="findByKeyWord">找商品</option>
						<option value="searchMall">找賣場</option>
					</select>
					<button type="submit" class="btn btn-default" style="height:50px;"><i class="fa fa-search" aria-hidden="true"> </i></button>
            	</FORM>
            	<div style="font-family: 微軟正黑體; text-align: left">熱門搜尋 | JAVA 海鮮 大衛 新垣結衣</div>
			</div>
                <div class="header-cart" style="margin-left:-5px;">
                    <div class="my-account">
                        <a href="contact.html" style="font-family: 微軟正黑體;"><i class="fa fa-map-marker" aria-hidden="true"></i> 關於我們</a>
                    </div>
                    <div class="cart" style="margin-top: 5px;">
                        <form action="#" method="post" class="last">
                            <input type="hidden" name="cmd" value="_cart" />
                            <input type="hidden" name="display" value="1" />
                            <!--      右上角的藍色購物車              -->
                            <a href="<%=request.getContextPath()%>/FrontEnd/member/shoppingCart.jsp">
                            <div  style="font-family : 微軟正黑體">
                                <button class="w3view-cart " id="shoppingCart" type="button"  style="margin-left: -10px"><i class="fa fa-cart-arrow-down" aria-hidden="true"></i></button><span class="badge" style="background-color: red;" id="cartNotify"></span>
                                
                            </div>
                            </a>
                        </form>
                    </div>
                    <div class="clearfix"> </div>
                </div>
                <div class="clearfix"> </div>
            </div>
        </div>
    </div>
	<%! List<String> itemPics;%>
	<% itemPics = (List<String>)request.getAttribute("itemPicsList");%>
    <div class="products" style="font-family : 微軟正黑體">
        <div class="container">
            <div class="single-page">
                <div class="single-page-row" id="detail-21">
                    <div class="col-md-6 single-top-left">
                        <div id="myCarousel" class="carousel slide" data-ride="carousel">
                            <!-- Indicators -->
                            <ol class="carousel-indicators">
                            	<% if(itemPics != null && itemPics.size() > 1){
                            		for(int i=0;i<itemPics.size();i++){
                            			if(itemPics.size() == 1){%>
                            				<li data-target="#myCarousel"></li>
                            			<%}%>	
                            			<%if(i==0 && itemPics.size()>1){%>
                            				 <li data-target="#myCarousel" data-slide-to="${i}" class="active"></li>
                            			<%}else if(i!=0){%>
                            				 <li data-target="#myCarousel" data-slide-to="${i}"></li>
                                       <%}%>
                            		<%}%>
                            	<%}%>
                            </ol>
                            <!-- Wrapper for slides -->
                            <div class="carousel-inner">
                                <% if(itemPics != null){
                            		for(int i=0;i<itemPics.size();i++){
                            		if(i==0){%>
                            		  <div class="item active">
                            			 <img src="data:image/jpg;base64,<%= itemPics.get(i)%>" style="width:357px;height:357px;">
                            		  </div>
                            		<%}else if(i!=0){%>
                            		<div class="item">
                            			 <img src="data:image/jpg;base64,<%= itemPics.get(i)%>" style="width:357px;height:357px;">
                            		</div>
                            			<%}%>
                              		<%}%> 
                             	 <%}%> 
                                </div>
                            </div>

                            <!-- Left and right controls 如果商品有多張圖片，再給箭頭滑動 -->
                            	 <a class="left carousel-control" href="#myCarousel" data-slide="prev">
                            	  <% if(itemPics.size() > 1){%>		
      								<span class="glyphicon glyphicon-chevron-left"></span>
     								<span class="sr-only">Previous</span>
     							  <%}%>
   							 	 </a>
                                <a class="right carousel-control" href="#myCarousel" data-slide="next">
                             	 <% if(itemPics.size() > 1){%>
     								 <span class="glyphicon glyphicon-chevron-right"></span>
      								 <span class="sr-only">Next</span>
      								<%}%>
   							     </a>                                                   
                        </div>
                    </div>
                    <div class="col-md-6 single-top-right">
                        <h3 class="item_name" style="font-family: 微軟正黑體" id="itemName">${itemInfo.item_name}
						<!--                         	如果這個商品是被檢舉的 -->
                        </h3>
                      	<hr>
																																							<!--價格顯示區 -->
                        <form>
							<!--如果這個東西有特賣中 -->
							
							<!-- 							如果商品特賣時間還沒過而且是在特賣中就顯示 -->
                        	<c:if test="${limitSaleItem.contains(itemInfo.item_no) && (limitSaleService.findOneSale(itemInfo.item_no).getSale_end() > now)}">
								 <del><span style="font-size: 25px;color:black;font-family:arial, helvetica, clean, sans-serif;">$<span class="itemPrice">${itemInfo.item_price}</span></span></del>
								 <span style="font-size: 25px;color:rgb(226, 28, 0);font-family:arial, helvetica, clean, sans-serif;">$<span class="itemPrice">
								 ${limitSaleService.findOneSale(itemInfo.item_no).getSale_price()}</span></span><br>
								
								 	<div style="font-size: 25px;color:rgb(226, 28, 0);font-family:Microsoft JhengHei;"><span class="glyphicon glyphicon-hourglass"></span>
                                		<div id="countdown" class="timeTo timeTo-white" style="display:inline;font-size: 25px;color:rgb(226, 28, 0);font-family:Microsoft JhengHei;"></div>
                                    </div>          	
                        	</c:if>
                        	<c:if test="${!limitSaleItem.contains(itemInfo.item_no) || (limitSaleService.findOneSale(itemInfo.item_no).getSale_end() < now)}"> 
                        		 <span style="font-size: 25px;color:rgb(226, 28, 0);font-family:arial, helvetica, clean, sans-serif;">$<span id="itemPrice">${itemInfo.item_price}</span></span><br>
                        	</c:if>
                         
		
                            <span class="single-price-text" style="font-size: 20px;font-family: 微軟正黑體;margin-top: 10px;">庫存數量:<span id="itemInventory">${itemInfo.item_inventory}</span></span><br>
                           
                            	<span class="single-price-text" style="font-size: 20px;font-family: 微軟正黑體;margin-top: 10px;">選擇購買數量:
                            		<span><button type="button" class="btn btn-default" id="deleteItem"><span class="glyphicon glyphicon-minus" style="display: inline-block;"></span></button><label id="buyQuantity" align="center">1</label><button type="button" class="btn btn-default" id="addItem"><span class="glyphicon glyphicon-plus" style="display: inline-block;"></span></button> <button type="button" class="btn btn-default" id="resetQuantity">數量重置</button></span>
                            	</span>
                            <input type="hidden" name="cmd" value="_cart" />
                            <input type="hidden" name="add" value="1" />
                            <input type="hidden" name="w3ls_item" value="Snow Blower" />
                            <input type="hidden" name="amount" value="540.00" />
     						<button type="button" class="w3ls-cart" id="thisItemAddToCart"><span class="glyphicon glyphicon-shopping-cart"></span> 加入購物車</button>
                        
                        </form>
                        
                       
                       <!-- 有登入才能關注商品,並判斷是否加入過以顯示不同的按鈕 -->
                        <% 
                        	if(memVO != null){
                        	Follow_itemService folo_itemSvc = new Follow_itemService();
                        	String mem_no = memVO.getMem_no();
                        	String item_no = request.getParameter("itemNo");
		    				Follow_itemVO cnt = folo_itemSvc.findByPK(mem_no, item_no);
                        
                        %>
                        <!-- //關注商品 -->
                        <button class="w3ls-cart w3ls-cart-like aaa" id="addFolo_item" style="margin-top: 10px; font-weight:<%=(cnt == null) ? "1" : "2"%>"><%=(cnt == null) ? "關注商品" : "取消關注"%></button>
                        
                        	<script type="text/javascript">
	                       	
	                       	$("#addFolo_item").click(function () {
	                            if ($(".aaa").css('font-weight') == 1) {
	                            	$(".aaa").css('font-weight', '2')
	                            	$(".aaa").text('取消商品');
	                                
	                            } else {
	                            	$(".aaa").css('font-weight', '1')
	                            	$(".aaa").text('關注商品');
	                            }
	                        });
	                       	
                       		</script>
                        
                        <%}else{ %>
                        <button class="w3ls-cart w3ls-cart-like aaa" id="addFolo_item" style="margin-top: 10px;">關注商品</button>
                        
                        <script type="text/javascript">
	                       
                       	</script>
                        
                        <%}%>
                        <button class="w3ls-cart w3ls-cart-like" data-toggle="modal" data-target="#myModal" id="reportItem">檢舉商品</button>
              
                        <hr>
                        <div>
                            <span class="single-price-text" style="font-size: 20px;font-family: 微軟正黑體;margin-top: 5px;">${itemInfo.item_description}</span> 
                        </div>
                    </div>
                    <div class="clearfix"> </div>
                </div>
                <div class="single-page-icons social-icons">
                    <ul>
                        <li>
                            <h4>Share on</h4>
                        </li>
                        <li><a rel="nofollow" target="_blank" href="https://www.facebook.com/share.php?u=https://ca102g1.tk/CA102G1/FrontEnd/query.do?action=share${itemInfo.item_no}" onclick="shareButtonClick('facebook','https://ca102g1.tk/CA102G1/FrontEnd/query.do?action=share${itemInfo.item_no}');"><img title="分享到臉書！" src="https://lh3.ggpht.com/_SAlWJ_xow1Y/TEwRvVzhdiI/AAAAAAAABV8/RRa3HuGBLeA/facebook.png" border="0" style="height:32px" /></a></li>
                        <li><a href="#" class="fa fa-twitter icon twitter"> </a></li>
                        <li><a href="#" class="fa fa-google-plus icon googleplus"> </a></li>
                    </ul>
                </div>
            </div>
            <!-- recommendations -->
            <div class="container recommend">
                <h3 class="w3ls-title" style="font-family: 微軟正黑體;">其他推薦商品</h3>
                <script>
                    $(document).ready(function() {
                        $("#owl-demo5").owlCarousel({
                            autoPlay: 3000, //Set AutoPlay to 3 seconds
                            items: 4,
                            itemsDesktop: [640, 5],
                            itemsDesktopSmall: [414, 4],
                            navigation: true
                        });

                    });
                </script>
                <div id="owl-demo5" class="owl-carousel">
                    <c:forEach var="Item" items="${randomItemList}">
									<div class="item">
										<div class="glry-w3agile-grids agileits"> 
											<img src="data:image;base64,${Item.encoded}" width="100" height="200" alt="img">
											<div class="view-caption agileits-w3layouts">           
												<h4>${Item.item_name}</h4>
												<p>${Item.item_description}</p>
												<h5>$${Item.item_price}</h5>
												<form action="<%=request.getContextPath()%>/FrontEnd/query.do" method="post" target="_blank">
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
   
    <!--    檢舉商品跳窗-->
    <div id="myModal" class="modal fade" role="dialog" style="font-size: 20px;font-family: 微軟正黑體;">
        <div class="modal-dialog">
            <!-- Modal content-->
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">輸入檢舉資訊</h4>
                </div>
                <div class="modal-body">
                    <!--  $("#reason1").val() = $(this).next().text();   -->
                    檢舉的商品:<span id="itemNameOfReport"></span><br>
                    <span>請選擇檢舉原因:</span><br>
                    <input type="checkbox" name="itemReportReason"><span id="reason1">這含有不當內容，如：色情、粗俗字眼、暴力等訊息...</span><br>
                    <input type="checkbox" name="itemReportReason"><span id="reason2">這是廣告訊息</span><br>
                    <input type="checkbox" name="itemReportReason"><span id="reason3">內文與實際不符</span><br>
                    <input type="checkbox" name="itemReportReason"><span id="reason4">我覺得這不應該出現</span><br>
                    <textarea id="reportOpinion" cols="54" rows="10" style="resize: none;" placeholder="請輸入其他意見"></textarea><br>

                </div>
                <div class="modal-footer">
                    <span style="color: red;font-weight: bold;">確定檢舉?</span>
                    <button type="button" class="btn btn-default" id="sendReport" data-dismiss="modal">是</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">否</button>
                </div>
            </div>
        </div>
    </div>
    <!--    檢舉商品跳窗-->

    <!-- footer -->
    <div id="footer">
    	<%@ include file="/FrontEnd/headerFooter/footer.html"%>
    </div>
    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="<%=request.getContextPath()%>/FrontEnd/js/bootstrap.js"></script>
</body>

</html>
