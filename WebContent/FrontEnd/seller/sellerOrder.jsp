<%@ page contentType="text/html; charset=UTF-8" pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<html>

<head>
<title>賣家中心</title>
<%@ include file="/FrontEnd/headerFooter/head.jsp"%>
<!-- 思源黑體設定-->
<style type="text/css">
.btn {
	height: 100px;
	padding-top: 40px;
	font-family: 'Noto Sans TC';
	font-style: normal;
	font-weight: 500;
}
</style>
</head>

<body>
	<!-- header -->
	<div id="header">
		<jsp:include page="/FrontEnd/headerFooter/header.jsp" flush="true" />
	</div>
	<!--========================================================賣家中心============================================================= -->
	<div class="container">
		<!-- ************************************************整個右下角*********************** -->
		<div class="col-md-9 product-w3ls-right">
			<!-- breadcrumbs -->
			<ol class="breadcrumb breadcrumb1">
				<li><a href="index.html"><i class="fa fa-home"></i> 首頁</a></li>
				<li><a href="sellerCenter.html"><i class="glyphicon glyphicon-piggy-bank"></i> 賣家中心</a></li>
				<li class="active"><i class="fa fa-file-text"></i> 賣家訂單查詢</li>
			</ol>
			<div class="clearfix"></div>
			<!-- //breadcrumbs -->
			<div class="product-top">
				<h4>PageDavidSeafood</h4>
				<ul>
					<li class="dropdown head-dpdn"><a href="#" class="dropdown-toggle" data-toggle="dropdown">Filter By<span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="#">Low price</a></li>
							<li><a href="#">High price</a></li>
							<li><a href="#">Latest</a></li>
							<li><a href="#">Popular</a></li>
						</ul></li>
					<li class="dropdown head-dpdn"><a href="#" class="dropdown-toggle" data-toggle="dropdown">Brands<span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="#">Samsung</a></li>
							<li><a href="#">LG</a></li>
							<li><a href="#">Sony</a></li>
							<li><a href="#">Other</a></li>
						</ul></li>
				</ul>
				<div class="clearfix"></div>
			</div>
			<!-- right side col右邊資料要放這邊唷 -->
			<div class="products-row">
				<div class="clearfix"></div>
			</div>
		</div>
		<!-- ************************************************整個右下角結束*********************** -->
		<!-- left side col -->
		<div class="col-md-3 rsidebar">
			<div class="slider-left btn-group-vertical" role="group">
				<h3 class="btn btn-warning btn-lg ">
					<i class="fa fa-money"></i> 賣家訂單查詢
				</h3>
				<h3 class="btn btn-primary btn-lg ">
					<i class="fa fa-video-camera "></i> 發送直播預告
				</h3>
				<h3 class="btn btn-info btn-lg ">
					<i class="fa fa-clock-o"></i> 限時特賣管理
				</h3>
				<h3 class="btn btn-danger btn-lg ">
					<i class="fa fa-gift"></i> 商品上架管理
				</h3>
				<h3 class="btn btn-success btn-lg ">
					<i class="fa fa-file-code-o"></i> 賣場頁面管理
				</h3>
			</div>
		</div>
	</div>
	<div id="footer">
		<%@ include file="/FrontEnd/headerFooter/footer.html"%>
	</div>
</body>

</html>

