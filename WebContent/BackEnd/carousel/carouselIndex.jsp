<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 <meta charset="UTF-8">
    <title>設置幻燈片</title>
   <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css">
    <!-- Optional theme -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.2/css/bootstrap.min.css" integrity="sha384-Smlep5jCw/wG7hdkwQ/Z5nLIefveQRIY9nfy6xoR1uRYBtpZgI6339F5dgvm/e9B" crossorigin="anonymous">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/BackEnd/css/BackEnd.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <link href="<%=request.getContextPath()%>/BackEnd/css/carouselIndexStyle.css" rel="stylesheet" type="text/css">
    <!-- Latest compiled and minified JavaScript -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
    <script src="http://code.jquery.com/jquery-1.11.3.min.js"></script>
    <script src="<%=request.getContextPath()%>/BackEnd/js/Logout.js"></script>

<!-- //         $(document).ready(function() { -->
<!-- //             var select = document.getElementById("carousel"); -->
<!-- //             for (let i = 0; i < 5; i++) { -->
<!-- //                 let option = new Option(i + 1, i + 1); -->
<!-- //                 select.add(option); -->
<!-- //             } -->
<!-- //             $("#picDisplay").hide(); -->
<!-- //             $("#carousel").on("change", function() { -->
<!-- //                 $("#picUploadform > div > form").html(null); -->
<!-- //                 let index = select.selectedIndex; -->
<!-- //                 let picNum = parseInt(select.options[index].value); -->
<!-- //                 for (var i = 0; i < picNum; i++) { -->
<!-- //                     $("#picUploadform > div > form").append("<input type='file' name='pic" + (i + 1) + "'>" + -->
<!-- //                         "<img>"); -->
<!-- //                     $("#picUploadform > div > form > input").on("change", function() { -->
<!-- //                         $(this).next().css({ -->
<!-- //                             "width": "400px", -->
<!-- //                             "height": "250px" -->
<!-- //                         }); -->
<!-- //                         InputLoadImageToBindImageElement(this, $(this).next()); //傳入改變的input和要改變的img標籤 -->
<!-- //                     }); -->
<!-- //                 } -->
<!-- //             }); -->


<!-- //             $("#uploadbtn").click(function() { -->
<!-- //                 $("#picDisplay").hide(); -->
<!-- //                 $("#picUpload").show(); -->
<!-- //             }); -->

<!-- //             $("#displaybtn").click(function() { -->
<!-- //                 $("#picUpload").hide(); -->
<!-- //                 $("#picDisplay").show(); -->
<!-- //             }); -->

<!-- //         }); -->

<!-- //         function InputLoadImageToBindImageElement(inputEl, imgEl) { //顯示預覽圖的方法 -->
<!-- //             if (inputEl.files && inputEl.files[0]) { -->
<!-- //                 var reader = new FileReader(); -->
<!-- //                 reader.onload = function(e) { -->
<!-- //                     $(imgEl).attr('src', e.target.result); -->
<!-- //                 } -->
<!-- //                 reader.readAsDataURL(inputEl.files[0]); -->
<!-- //             } -->
<!-- //         } -->

</head>
<body>
  <%@ include file="/BackEnd/header/header.jsp" %>
   
     <div class="list-group">
        <div class="col-2">
         <div class="card border-primary mb-3" style="max-width: 18rem;margin-top:30px;">
                            <div class="card-header">員工資訊</div>
                            <div class="card-body">
                                <h5 class="card-title">員工編號:${emp.emp_no}</h5>
                                <h5 class="card-title">員工姓名:${emp.emp_name}</h5>
                            </div>
                        </div>
            <span class="list-group-item list-group-item-action active">設置幻燈片</span>
            <a href="<%=request.getContextPath()%>/BackEnd/BackEnd.jsp" class="list-group-item list-group-item-action">回首頁</a>
        </div>
    </div>
    <div class="operationArea" align="center" style="padding-top: 40px;overflow:auto;">
        <section id="service">
		    <div class="container">
		      <div class="row row-1">
		        <div class="col-md-4 ser-col-4">
		          <div class="ser-col ser-1">
		            <div class="icon-col">
		              <i class="fa fa-snowflake-o" aria-hidden="true"></i>
		            </div>
		            <h2>Add a Slide</h2>
		            <div>新增單張幻燈片</div>
		            <a href="<%=request.getContextPath()%>/BackEnd/carousel/addCarousel.jsp" class="btn"> Action!</a>
		            <span class="circle hidden-xs">
		            <i class="fa fa-circle-thin" aria-hidden="true"></i>
		            </span>
		          </div>
		        </div>
		
				<div class="col-md-4 ser-col-4">
				 <div class="ser-col ser-2">
				   <div class="icon-col">
				     <i class="fa fa-th-list" aria-hidden="true"></i>
				   </div>
				   <h2>Get All Slides</h2>
				   <div>檢視所有幻燈片</div>
				   <a href="<%=request.getContextPath()%>/BackEnd/carousel/getAllCarousel.jsp" class="btn"> Action!</a>
				    <span class="circle hidden-xs">
				    <i class="fa fa-circle-thin" aria-hidden="true"></i>
				    </span>
				  </div>
				</div>
		        
		<jsp:useBean id="carouselSvc" scope="page" class="com.carousel.model.CarouselService" />
		
		        <div class="col-md-4 ser-col-4-l">
		          <div class="ser-col ser-3">
		            <div class="icon-col">
		              <i class="fa fa-search-plus" aria-hidden="true"></i>
		            </div>
		            <h2>Query a Slide</h2>
		            <div>查詢與搜尋幻燈片</div>
		            <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/BackEnd/carousel/carousel.do" >
				       <b>Slide No.</b>
				       <select size="1" name="Carousel_no">
				         <c:forEach var="CarouselVO" items="${carouselSvc.all}" > 
				          <option value="${CarouselVO.carousel_no}">${CarouselVO.carousel_no}
				         </c:forEach>   
				       </select>
				       <input type="hidden" name="action" value="getOne">
				       <input class="btn" type="submit" value="Action!">
				    </FORM>
				    
				    <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/BackEnd/carousel/carousel.do" >
				        <b>Title Keyword:</b>
				        <input type="text" name="Carousel_title" size=5>
				        <input type="hidden" name="action" value="search">
				        <input class="btn" type="submit" value="Action!">
		    		</FORM>
		          </div>
		        </div>
		      </div>
		
		    </div>
  	 </section>
        
        
    </div>
</body>
</html>