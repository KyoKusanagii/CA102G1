<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*, java.lang.*"%>
<%@ page import="com.member.model.*, com.item.model.*, com.itempic.model.*, com.fborder.model.*, com.android.model.*"%> 

<% 

	MemVO memVO = (MemVO)session.getAttribute("memVO");
	
	if(memVO == null){
		session.setAttribute("location", request.getRequestURI());
		response.sendRedirect(request.getContextPath() + "/FrontEnd/member/login.jsp");
		return;
	}
	
	String mem_no = memVO.getMem_no();
	
	String fb_order_no = request.getParameter("fb_order_no");
    FbOrderService fborderSvc = new FbOrderService();
	FbOrderVO fborderVO = fborderSvc.getOneOrder(fb_order_no);
	pageContext.setAttribute("fborderVO", fborderVO);
	
	List<BuyerOrderVO> list =  fborderSvc.getAllOrderItem(fb_order_no);
	pageContext.setAttribute("list", list);
	
%>
<jsp:useBean id="MemService" scope="page" class="com.member.model.MemService" />  
<jsp:useBean id="ItemService" scope="page" class="com.item.model.ItemService" />  
<jsp:useBean id="ItempicService" scope="page" class="com.itempic.model.ItempicService" />  
<jsp:useBean id="encoder" scope="page" class="sun.misc.BASE64Encoder" />

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

	
<script src="<%=request.getContextPath()%>/FrontEnd/js/jquery.twzipcode.min.js"></script>
<style>
	
	.form-group{
		margin-top:5px
	}
	


</style>
	
</head>
<body>
	<!-- header -->
	<div class="header">
		<jsp:include page="/FrontEnd/headerFooter/header.jsp"/>
	</div>
	<!-- //header-->


		
		<div class="container"  id="check-out" style="font-family:微軟正黑體; font-weight:bold">
		
		<div class="panel panel-default col-md-10 col-md-offset-1">
			<div class="panel-heading col-md-12">
					<font size=+3>結帳</font>
			</div>
			<table class="table">
			 <tr >
			 	<th class="col-md-1">訂購商品</th>
	    		<th ></th>
	    		<th class="text-center">賣家</th>
	    		<th class="text-center">單價</th>
	    		<th class="text-center">數量</th>
	    		<th class="text-center">總價</th>
	    	</tr >
			
			 <c:forEach var="buyerorderVO" items="${list}">
				<tr>
				    <td >
				    <img src="data:image/*;base64,${encoder.encode(buyerorderVO.item_pic)}" class="img-responsive" style="width:50px; height:50px">
				    </td>
				    <td>${ItemService.getOneItem(buyerorderVO.item_no).item_name}</td>
				    <td class="text-center">${MemService.findByPK(ItemService.getOneItem(buyerorderVO.item_no).item_owner).mem_name}</td>
				    <td class="text-center">${buyerorderVO.item_prc}</td>
				    <td class="text-center">${buyerorderVO.item_cnt}</td>
				    <td class="text-center">${buyerorderVO.item_prc * buyerorderVO.item_cnt}</td>
				 </tr>
			 </c:forEach>
			  
			  <tr>
			  	<td colspan="6" style="text-align:right;"> 
			  		<font size="+2">總金額：$${fborderVO.fb_order_prc}  </font>
			  	</td>
			  </tr>
			   </table>
		</div>
			
			
			
			
		<div class="panel panel-default col-md-10 col-md-offset-1">
		
			<form method="post" id="myForm" name="myForm" class="form-horizontal form-inline"  action="<%=request.getContextPath()%>/FrontEnd/fborder/fbOrder.do">
				
				
			<div class="panel-body col-md-offset-3">
				<div class="form-group col-md-12">
					<label class="control-label col-md-2">收件人:</label>
					<div class="col-md-10">
						<input name="receiver" type="text" class="form-control" id="receiver" placeholder="請輸入收件人姓名" style="width: 200px" value="${memVO.mem_name}">
						<p style="color:red; font-family:微軟正黑體;">${errorMsgs.receiver}</p>
					</div>
				</div>
				
				<div class="form-group col-md-12">
					<label class="control-label col-md-2">收貨地址:</label>
					<div class="col-md-10">
                        <div id="twzipcode"></div>
                        <input type="text" class="form-control" name="add" style="width: 270px;" placeholder="街/路、樓層">
                    	<p style="color:red; font-family:微軟正黑體;">${errorMsgs.add}</p>
					</div>
				</div>
				
				<div class="form-group col-md-12">
					<label class="control-label col-md-2">電話:</label>
					<div class="col-md-10">
					    <input type="tel" name="phone" class="form-control" id="rec_phone" placeholder="請輸入手機" style="width: 200px" maxlength="10" value="${phone}">
						<p style="color:red; font-family:微軟正黑體;">${errorMsgs.phone}</p>
					</div>
				</div>
				
				<div class="form-group col-md-12">
                     <label class="control-label col-md-2">付款方式:</label>
					 <div class="col-sm-10">
						  <label class="radio-inline"> <input type="radio" name="fb_order_trans" id="inlineRadio1" value="信用卡" onclick="$('#creditCard').show();$('#transfer').hide()" checked>信用卡 / VISA金融卡</label> 
						  <label class="radio-inline"> <input type="radio" name="fb_order_trans" id="inlineRadio2" value="銀行轉帳" onclick="$('#creditCard').hide();$('#transfer').show()">銀行轉帳</label> 
						  <label class="radio-inline"> <input type="radio" name="fb_order_trans" id="inlineRadio3" value="貨到付款" onclick="$('#creditCard').hide();$('#transfer').hide()">貨到付款</label>
					</div>
					<p style="color:red; font-family:微軟正黑體;">${errorMsgs.fb_order_trans}</p>
				</div>
				
				<div id="creditCard" >
					
					<div class="form-group col-md-12">
	                    <label class="control-label col-md-2">持卡人姓名:</label>
						<div class="col-sm-10">
							<input type="text" name="cardowner" class="form-control" placeholder="請輸入姓名" style="width:200px">
							<p style="color:red; font-family:微軟正黑體;">${errorMsgs.cardowner}</p>
						</div>
						
					</div>
					
					<div class="form-group col-md-12">
						<label class="control-label col-md-2" style="white-space:nowrap;">信用卡卡號:</label>
						<div class="col-md-10">
							 <input type="text" class="form-control" style="width: 58px" maxlength="4" name="cdno1" size="5" onkeyup="cdno(1);" style="">
	                         ─ 
	                         <input type="text" class="form-control" style="width: 58px" maxlength="4" name="cdno2" size="5" onkeyup="cdno(2);">
	                         ─ 
	                         <input type="text" class="form-control" style="width: 58px" maxlength="4" name="cdno3" size="5" onkeyup="cdno(3);">	
	                         ─ 
	                         <input type="text" class="form-control" style="width: 58px" maxlength="4" name="cdno4" size="5">
							 <p style="color:red; font-family:微軟正黑體;">${errorMsgs.cdno}</p>
						</div>
						
					</div>
					
					
					
					<div class="form-group col-md-12">
						<label class="col-md-2" style="white-space:nowrap;">信用卡截止日:</label>
						<div class="col-md-10">
	                         <select class="form-control" name="expire_month">
	                             <option value="3"selected>請選擇</option>
	                           
	                             <option value="1">1</option>
	                           
	                             <option value="2">2</option>
	                           
	                             <option value="3">3</option>
	                           
	                             <option value="4">4</option>
	                           
	                             <option value="5">5</option>
	                           
	                             <option value="6">6</option>
	                           
	                             <option value="7">7</option>
	                           
	                             <option value="8">8</option>
	                           
	                             <option value="9">9</option>
	                           
	                             <option value="10">10</option>
	                           
	                             <option value="11">11</option>
	                           
	                             <option value="12">12</option>
	                           
	                         </select>月
	                
	                         <select class="form-control" name="expire_year">
	                             <option value="${expire_year}">請選擇</option>
	                           
	                             <option >2018</option>
	                             
	                             <option >2019</option>
	                           
	                             <option >2020</option>
	                           
	                             <option >2021</option>
	                           
	                             <option >2022</option>
	                           
	                             <option >2023</option>
	                           
	                             <option >2024</option>
	                           
	                             <option >2025</option>
	                           
	                             <option >2026</option>
	                           
	                             <option >2027</option>
	                           
	                             <option >2028</option>
	                           
	                             
	                           
	                         </select>年
	                         <p style="color:red; font-family:微軟正黑體;">${errorMsgs.expire}</p>
						</div>
					</div>
					
					<div class="form-group col-md-12">
						<label class="control-label col-md-2" style="white-space:nowrap;">信用卡安全碼:</label>
						<div class="col-sm-10"> 
						     <input type="text" name="expire_safe" class="form-control text-center" placeholder="安全碼" maxlength="3" style="width:70px">
							 <p style="color:red; font-family:微軟正黑體;">${errorMsgs.safe}</p>
						</div>
					</div>
				</div>
				
				<div id="transfer" style="display:none">
					
					<div class="form-group col-md-12">
						
						<label class="control-label col-md-2" style="white-space:nowrap;"></label>
						<div class="col-sm-10"> 
						 
							<font size=+1>
								<strong style="color:red">請於24小時內匯款至Instabuy指定帳號</strong>
									
							</font>	
						</div>
					</div>
					
				</div>
				
				<div class="form-group col-md-12">
					<label class="control-label col-md-2" style="white-space:nowrap;">備註:</label>
					<div class="col-sm-10"> 
					     <textarea name="mall_order_remark" class="form-control" cols="30" rows="5" style="resize:none;"></textarea>
					</div>
				</div>
				
				
					<div class="col-md-offset-2 col-md-10" style="margin-top:30px">
				        <input type="submit" class="order btn btn-success" value="送出" >
				    	<input type="hidden" name="action" value="checkFbOrder">
				    	<input type="hidden" name="fb_order_no" value="${fborderVO.fb_order_no}">
				    	</form>
				    	
				    	<button type="button" onclick="getMemberData()" id="mbutton" class="btn ">導入會員資料</button>
	                </div>
					 
				</div> 
					 
				 
					 
			   </div>
			 
		</div> 
		 	
			
		
	</div>
	<!-- footer -->
	<div id="footer">
	  <%@ include file="/FrontEnd/headerFooter/footer.html"%>
	 </div>
</body>
<script type="text/javascript">

function cdno(arg){
    if(eval('document.myForm.cdno'+arg+'.value.length') == 4){
		   eval('document.myForm.cdno'+(arg+1)+'.focus()');
	}
}
    

function getMemberData(){ //導入會員資料
	
	$("#twzipcode").twzipcode('set', {
	    'county': '桃園市',
	    'district': '中壢區',
	    'zipcode': 320
	}); 
	$("input[name='add']").val("中央大學資策會") //地址
	$("input[name='phone']").val("${memVO.mem_mobile}") //地址
	$("input[name='cardowner']").val("${memVO.mem_name}")//持卡人姓名
	var cdno = "4019454065577271"; //信用卡號碼
	$("input[name='cdno1']").val(cdno.substring(0,4));  //信用卡號碼
	$("input[name='cdno2']").val(cdno.substring(4,8));  //信用卡號碼
	$("input[name='cdno3']").val(cdno.substring(8,12)); //信用卡號碼
	$("input[name='cdno4']").val(cdno.substring(12,16));//信用卡號碼
	$("select[name='expire_month']").val("6");	//信用卡到期月份
	$("select[name='expire_year']").val("2020"); //信用卡到期年份
	$("input[name='expire_safe']").val("777"); //信用卡安全碼
	$("textarea[name='mall_order_remark']").val("希望盡量晚上時段再送來"); //備註

}

</script>
  <script>
   $("#twzipcode").twzipcode({
  	 css: ["city ", "town", "zip "]
   });
   
  </script>
  <style>
  		.city, .town, .zip {
	    
	      width:90px;
		  height: 34px;
		  padding: 6px 12px;
		  font-size: 14px;
		  line-height: 1.42857143;
		  color: #555;
		  background-color: #fff;
		  background-image: none;
		  border: 1px solid #ccc;
		  border-radius: 4px;
		  -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075);
		          box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075);
		  -webkit-transition: border-color ease-in-out .15s, -webkit-box-shadow ease-in-out .15s;
		       -o-transition: border-color ease-in-out .15s, box-shadow ease-in-out .15s;
		          transition: border-color ease-in-out .15s, box-shadow ease-in-out .15s;
		}	
  </style>

</html>