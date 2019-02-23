<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*, java.lang.*"%>
<%@ page import="com.member.model.*, com.item.model.*, com.itempic.model.*"%> 

<% 

// 	MemVO memVO = (MemVO)session.getAttribute("memVO");
	
// 	if(memVO == null){
// 		session.setAttribute("location", request.getRequestURI());
// 		response.sendRedirect(request.getContextPath() + "/FrontEnd/member/login.jsp");
// 		return;
// 	}
	
// 	String mem_no = memVO.getMem_no();
	
	
%>
<jsp:useBean id="MemService" scope="page" class="com.member.model.MemService" />  
<jsp:useBean id="ItemService" scope="page" class="com.item.model.ItemService" />  
<jsp:useBean id="ItempicService" scope="page" class="com.itempic.model.ItempicService" />  


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

  <script type="text/javascript">
	 //從購物車刪除
	$(document).ready(function () {
	 
	      $(".delete").on("click",function(){
	       var action = "DELETE";
	   	   var del = $(this).attr("id");
	   	   
	 
	    $.ajax({
	     url : "<%=request.getContextPath()%>/ShoppingCartServlet",
	     method : "POST",
	     data : {
	      action:action,
	      del:del
	     },
	     success: function(response) {
		       
	         $("#total").html('$' + response);
	  
	     },
	     error: function(xhr) {
	         alert('Ajax request 發生錯誤');
	       }
	    });
	    $(this).parent().parent().css("display","none");
	   });
	  });
	 </script>
	
	<script type="text/javascript">
	
	$(document).ready(function () {
	 
	      $(".quantity").click(function (){
	    	  
	    	  var price = +($(this).parent().next().children("strong").text());
	    	  var $subtotalstrong = $(this).parent().next().next().children("strong");
	    	  var subtotal = (+$(this).val()) * price;
	    	  
	    	  $subtotalstrong.text(subtotal);
	    	  $.ajax({
    		     url : "<%=request.getContextPath()%>/ShoppingCartServlet",
    		     method : "POST",
    		     data : {
    		      action:"updateQuantity",
    		      quantity:$(this).val(),
    		      item_no:$(this).attr("id"),
    		      item_price:$(this).parent().next().children("strong").text()
    		     },
    		     success: function(response) {
    		       
    		         $("#total").html('$' + response);
    		  
    		     },
    		     error: function(xhr) {
    		         alert('Ajax request 發生錯誤');
    		       }
    		    });
	    	  
	    	  
	    	  
	    	  
	      })
	  });
	
		
	 </script>
	
</head>
<body>
	<!-- header -->
	<div class="header">
		<jsp:include page="/FrontEnd/headerFooter/header.jsp"/>
	</div>
	<!-- //header-->



<% 
	@SuppressWarnings("unchecked")
	Vector<ItemVO> buylist = (Vector<ItemVO>)session.getAttribute("shoppingCart");
	
	if(buylist != null && (buylist.size() > 0)){
%>


	

	<div class="container" style="font-family:微軟正黑體 ;font-weight:bold">
		
		
         <div class="row">
       
                <table class="table table-hover">
                  <tr>
                      <th class="text-center">商品</th>
                      <th class="text-center">賣家</th>
                      <th class="text-center">數量</th>
                      <th class="text-center">單價</th>
                      <th class="text-center">總價</th>
                      <th> </th>
                  </tr>



	<% int total = 0; %>
	<%
	 
	 for (int index = 0; index < buylist.size(); index++) {
		ItemVO order = buylist.get(index);
		String item_no = order.getItem_no();
		request.setAttribute("item_no", item_no);
	%>
                  <tr>
                      <td class="col-12 col-md-6">
                      
                      <div class="media">
                          <div class="col-12 col-md-3 ">
                          	<a href="<%=request.getContextPath()%>/FrontEnd/query.do?action=findOneItem&itemNo=${ItemService.getOneItem(item_no).item_no}" target="_blank" style="text-decoration:none;font-size:20px">
               				<img src="data:image/*;base64,${ItempicService.getThumbnail(item_no).encoded}" class="img-responsive">
               				</a>
                          </div>
                          <div>
                              <a href="<%=request.getContextPath()%>/FrontEnd/query.do?action=findOneItem&itemNo=${ItemService.getOneItem(item_no).item_no}" target="_blank" style="text-decoration:none;font-size:20px">
                              ${ItemService.getOneItem(item_no).item_name}
                              </a>
                          </div>

                      </div>
                      </td>
                      <td class="col-sm-1 col-md-1 text-center" style="font-size:20px">
                      	${MemService.findByPK(ItemService.getOneItem(item_no).item_owner).mem_name}
                      </td>
                      <td class="col-sm-1 col-md-1 text-center">
                      	<input type="number" id="${item_no}" class="quantity" style="width: 45px; padding: 1px" value="<%= order.getQuantity() %>" min="1">
                      </td>
                      <td class="col-sm-1 col-md-1 text-center"><strong style="font-size:20px"><%= order.getPrice() %></strong></td>
                      <td class="col-sm-1 col-md-1 text-center"><strong style="font-size:20px; color:blue"><%= order.getQuantity()*(order.getPrice()) %></strong></td>
                      <td class="col-sm-1 col-md-1">
                      <button type="button" class="btn btn-danger delete" id="<%= item_no %>">
                         	 刪除<span class="glyphicon glyphicon-remove"></span>
                      </button></td>
                  </tr>
        <% 
	        Integer price = new Integer(order.getQuantity()*(order.getPrice()));
        	total += price;
        %>
        <%} %>                 
                  <tr>
                      <td>   </td>
                      <td>   </td>
                      <td>   </td>
                      <td><h3>總金額:</h3></td>
                      <td><h3><strong id="total">$<%= total %></strong></h3></td>
                      <td></td>
                  </tr>
                  <tr>
                      <td>
                      	<form method="post" action="<%=request.getContextPath()%>/ShoppingCartServlet">  
                      	  <button type="submit" class="btn btn-default">
                      	  	<span class="glyphicon glyphicon-trash"></span> 清空購物車
                      	  </button>
                      	  <input type="hidden" name="action" value="cleanCart">
                      	</form> 
                      </td>
                      <td>   </td>
                      <td>   </td>
                      <td>	 </td> 
                      <td>
                      	<form method="post" action="<%=request.getContextPath()%>/FrontEnd/query.do">
	                      <button type="submit" class="btn btn-primary">
	                          <span class="glyphicon glyphicon-shopping-cart"></span> 繼續購物
	                      </button>
	                      <input type="hidden" name="action" value="findByKeyWord">
	                      <input type="hidden" name="item_name">
                      	</form>
                      </td>
                      <td>
	                     <form method="post" action="<%=request.getContextPath()%>/ShoppingCartServlet">
	                      <button type="submit" class="btn btn-success">
	                          	結帳 <span class="glyphicon glyphicon-play"></span>
	                      </button>
	                      <input type="hidden" name="action" value="CHECKOUT">
                      	</form>
                      </td>
                     
                  </tr>
                  
              </table>
              	    <% }else{ %>	
				<div class="container text-center">
					<div>
						<img src="<%=request.getContextPath()%>/FrontEnd/images/cartNo.png">
						<h3 class="text-center" style="font-family: 微軟正黑體">你的購物車內尚無商品</h3>
					</div>
					<div style="margin-top:50px">
						<form method="post" action="<%=request.getContextPath()%>/FrontEnd/query.do">
							<button class="btn btn-primary"><span class="glyphicon glyphicon-shopping-cart"></span> 前往購物</button>
						<input type="hidden" name="action" value="findByKeyWord">
						<input type="hidden" name="item_name">
                      	</form>
					</div>
				</div>
				
				<%} %>
            </div>  
        
        </div>
		
	    

	<!-- footer -->
	<div id="footer">
	  <%@ include file="/FrontEnd/headerFooter/footer.html"%>
	 </div>
</body>



</html>