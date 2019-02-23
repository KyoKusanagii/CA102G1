<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*, com.member.model.*"%>




<% 

	MemVO memVO = (MemVO)session.getAttribute("memVO");

	if(memVO == null){
		session.setAttribute("location", request.getRequestURI());
		response.sendRedirect(request.getContextPath() + "/FrontEnd/member/login.jsp");
		return;
	}
	
	String mem_no = memVO.getMem_no();
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




<script src="<%=request.getContextPath()%>/FrontEnd/js/jquery.twzipcode.min.js"></script>
<script src="<%=request.getContextPath()%>/FrontEnd/js/birthday.js"></script>
  <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <link rel="stylesheet" href="/resources/demos/style.css">
  <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<style>


	img{
		
		height:200px;
		width: 200px;
	
	}

</style>

</head>
<body>
	<!-- header -->
	<div class="header">
		<jsp:include page="/FrontEnd/headerFooter/header.jsp"/>
	</div><!-- //header-->
			

	
	

	
	<!-- cart-js -->
	<script src="<%=request.getContextPath()%>/FrontEnd/js/minicart.js"></script>
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
	
	<!-- menu js aim -->
	<script src="<%=request.getContextPath()%>/FrontEnd/js/jquery.menu-aim.js"> </script>
	<script src="<%=request.getContextPath()%>/FrontEnd/js/main.js"></script> <!-- Resource jQuery -->
	<!-- //menu js aim --> 
	<!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster --> 
	
	
	
		<div class="row" style="font-family:微軟正黑體;font-weight:bold">
			<div class="col-12 col-md-2" >
				<ul class="nav nav-pills nav-stacked">
				  <li role="presentation" class="active text-center"><a href="#"><h4>會員資料</h4></a></li>
				  <li role="presentation" class="text-center"><a href="followMem.jsp"><h4>我的最愛</h4></a></li>
				  <li role="presentation" class="text-center"><a href="mem_mallOrder.jsp"><h4>訂單查詢</h4></a></li>
				</ul>
			</div>
			
			
			
			<div class="container col-md-8" >
				
				      

				<ul class="nav nav-tabs">
				  <li class="active"><a data-toggle="tab" href="#">個人資料</a></li>
				  <li><a  href="<%=request.getContextPath()%>/FrontEnd/member/storeInfo.jsp">賣場資訊</a></li>	 
				</ul>
				
				<div class="tab-content">
				  <div  class="tab-pane fade in active">
				    <br>
				    
				    <div class="text-right" style=" margin-bottom: 20px;">
				    	<a href="<%=request.getContextPath()%>/FrontEnd/member/updatePsw.jsp" style="color:red">變更密碼</a>
				    </div>
				    
				            <form action="member.do" method="post" enctype="multipart/form-data">
				            <div class="row">
				             
				                <div class="col-12 col-md-3" align="center">
				                  <div id=showpic>
					                  <c:choose>  
										<c:when test="${memVO.profilepicEncoded eq null}"> 
										    <img src="<%=request.getContextPath()%>/FrontEnd/images/visitor.jpg" class="img-circle img-responsive" style="width:200px; height:200px" id="0">
										</c:when>   
										<c:otherwise>
											<img src="data:image/*;base64,${memVO.profilepicEncoded}" class="img-circle img-responsive" style="width:200px; height:200px" id="0"> 									
										</c:otherwise>
									  </c:choose>
				                  </div>
				                  <input type="file" name="mem_profilepic" accept="image/*">
				                </div>
				                <div class="col-12 col-md-9">
				                  <table class="table ">
				                      	
				                        <tr>
				                          <td>姓名:</td>
				                            <td>
				                            	<input type="text" value="${memVO.mem_name}" class="form-control" name="mem_name" placeholder="請輸入姓名" style="width: 250px;">
				                            	<p style="color:red; font-family:微軟正黑體;">${errorMsgs.name}</p>
				                            </td>
				                        </tr>
				                        
				                        <tr>
				                          <td>E-mail:</td>
				                            <td>
				                            	<input type="email" value="${memVO.mem_email}" class="form-control" name="mem_email" placeholder="請輸入e-mail" style="width: 250px;">
				                           		<p style="color:red; font-family:微軟正黑體;">${errorMsgs.email}</p>
				                            </td>
				                        </tr>
				                        
				                   
				                        <tr>
				                          <td>手機號碼:</td>
				                          <td>
				                          	<input type="text" value="${memVO.mem_mobile}" class="form-control" name="mem_mobile" placeholder="請輸入手機" style="width: 250px;">
				                          	<p style="color:red; font-family:微軟正黑體;">${errorMsgs.mobile}</p>
				                          </td>
				                        </tr>
				                       
				                        <tr>
				                          <td>性別:</td>
				                          <td>
				                            <div>
				                              <label class="radio-inline">
				                                <input type="radio" value="1" class="form-check-input" name="mem_sex">男
				                              </label>
				                              <label class="radio-inline">
				                                <input type="radio" value="2" class="form-check-input" name="mem_sex">女
				                              </label>
				                            </div>  
				                          </td>
				                       </tr>
				                       

				                        <tr>
				                            <td>地址:</td>
				                            <td style="padding-bottom: 0px;
				                            ">
				                            
				                              <div class="form-row">
											    <div class="form-group col-md-2 row">
					                              	<input type="text"  class="form-control" name="mem_post" placeholder="郵遞區號" maxlength="3" value="${memVO.mem_post}" style="width:80px; font-size:13px" >  
												    
											    </div>
											    
											    <div class="form-group col-md-6" style="margin-">
					                              	<input type="text"  class="form-control" name="mem_address" value="${memVO.mem_address}" placeholder="請輸入地址">
											    	<p style="color:red; font-family:微軟正黑體;">${errorMsgs.post}</p>
											    </div>
											    
											  </div>
				                            	
				                            </td>
				                        </tr>
				                         
				                        <tr>
				                          <td>生日:</td>
				                          <td>
 				                        
				                			<input type="text"  style="width: 120px;" id="datepicker" class="form-control" name="mem_birth" value="${memVO.mem_birth}">
				                			<p style="color:red; font-family:微軟正黑體;">${errorMsgs.birth}</p>
				                			<script>
											  $(function() {
											   $( "#datepicker" ).datepicker({
											      showAnim: "slideDown",
											      maxDate: "0d",
											      dateFormat : "yy-mm-dd"
											    });
											  });
											 </script>
				                			
				                			
				                          </td>
				                        </tr>
				                    </table>
				                    <div align="center">
				                      <button type="submit" href="#" class="btn btn-primary">儲存</button>
				                      <input type="hidden" name="action" value="updateMem">
				                      <a href="<%=request.getContextPath()%>/FrontEnd/member/profile.jsp" class="btn btn-default">取消</a>
				                    </div>
				                </div>
				              
				              </div>
				            </form>
				  </div>
				</div>
          
			</div>
		</div>
		
		
		
			
		<!-- footer -->
		<div style="margin-top: 10%; ">
			<%@ include file="/FrontEnd/headerFooter/footer.html"%> 
		</div>
			
		
		
	
</body>
	<script type="text/javascript">
		$('#myTabs a').click(function (e) {
		  e.preventDefault()
		  $(this).tab('show')
		})	
	</script>
	
	<script type="text/javascript">
		if(	'1' ==  '${memVO.mem_sex}' ){
			$('input[name="mem_sex"][value="1"]').prop("checked","true");
		}else if('2' ==  '${memVO.mem_sex}' ){
			$('input[name="mem_sex"][value="2"]').prop("checked","true");
		}
	</script>
	
	<script type="text/javascript">
		function $id(id){
			return document.getElementById(id);
		}
	 	document.getElementsByName("mem_profilepic")[0].onchange=function(){readURL(this,0)};
	 	
		   function readURL(input,id){
		        var parent = $id("showpic");
		        var child  = $id(id);
		        
		        if(input.files && input.files[0]){ //確認是否有檔案
		            var reader = new FileReader();
		            reader.onload=function(e){
		            	if(!parent.contains(child)){
		                	$id("showpic").innerHTML+= "<img src='"+e.target.result+"' id="+id+" class='img-circle'>";
		            	}else{
		            		parent.removeChild(child);
		            		$id("showpic").innerHTML+= "<img src='"+e.target.result+"' id="+id+" class='img-circle'>";
		            	}
		            }
		            reader.readAsDataURL(input.files[0]);
		        }else{
		            parent.removeChild(child); //必須藉由父節點才能刪除底下的子節點
		        }
		    }
	 	
	 </script>

</html>