<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.carousel.model.*"%>

<%
    CarouselService carouselSVC = new CarouselService();
	String keyword = request.getParameter("Carousel_title");
    List<CarouselVO> list = carouselSVC.getSomeByKeyword(keyword);
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
    <link rel="stylesheet" href="css/BackEnd.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <link href="<%=request.getContextPath() %>/BackEnd/css/carouselIndexStyle.css" rel="stylesheet" type="text/css">
    <!-- Latest compiled and minified JavaScript -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
    <script src="http://code.jquery.com/jquery-1.11.3.min.js"></script>
    <script src="<%=request.getContextPath()%>/BackEnd/js/Logout.js"></script>
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
	            <img class="card-img-top" src="data:image/png;base64,${carouselVO.byteString}" onError="this.src='images/nopic.jpg';" alt="Card image cap">
	            <div class="card-body">
	                <h5 class="card-title">${carouselVO.carousel_title}</h5>
	                <p class="card-text">${carouselVO.carousel_subTitle}</p>
	            </div>
	        </div>
	    </div>
	    
	    </c:forEach>
    </div>
    
</body>
</html>