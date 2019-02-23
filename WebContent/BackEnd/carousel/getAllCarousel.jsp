<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*,com.emp.model.EmpVO"%>
<%@ page import="com.carousel.model.*"%>

<%
    CarouselService carouselSvc = new CarouselService();
    List<CarouselVO> list = carouselSvc.getAll();
    pageContext.setAttribute("list",list);
    
%>
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
<!--     <script> -->
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
<!--     </script> -->
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
            <a href="carouselIndex.jsp" class="list-group-item list-group-item-action active">設置幻燈片</a>
            <a href="<%=request.getContextPath()%>/BackEnd/BackEnd.jsp" class="list-group-item list-group-item-action">回首頁</a>
        </div>
    </div>
    <div class="operationArea" align="center" style="padding-top: 40px;overflow:auto;">
        

	    <c:forEach var="carouselVO" items="${list}">
		    <div class="container">    
		        <div class="card col-6">
		            <img id="picPreview" class="card-img-top" src="data:image/png;base64,${carouselVO.byteString}" onError="this.src='<%=request.getContextPath() %>/FrontEnd/images/nopic.jpg';" alt="Card image cap"/>
		            <div class="card-body">
		                <h5 class="card-title">${carouselVO.carousel_title}</h5>
		                <h3 class="card-text">${carouselVO.carousel_subTitle}</h3>
		                <div class="row">
			                <form method=post ACTION="<%=request.getContextPath()%>/BackEnd/carousel/carousel.do">
				                <input type="hidden" name="Carousel_no" value="${carouselVO.carousel_no}"> 
				                <input type="hidden" name="action" value="update">
				                <button type="submit" class="btn btn-info">Edit</button>
			                </form>
			                
			                <form method=post ACTION="<%=request.getContextPath()%>/BackEnd/carousel/carousel.do">
				                <input type="hidden" name="Carousel_no" value="${carouselVO.carousel_no}"> 
				                <input type="hidden" name="action" value="delete">
				                <button type="submit" class="btn btn-danger">Delete</button>
			                </form>
		                </div>
		            </div>
		        </div>
		    </div>
	    </c:forEach>
	    
		
     </div>  
    
</body>
</html>