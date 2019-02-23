<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.item.model.*"%>
<%@ page import="com.particulars.model.*"%>
<%@ page import="com.category.model.*"%>
<%@ page import="java.util.*"%>

<%
	ItemVO itemVO = (ItemVO) request.getAttribute("itemVO");
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>新增商品</title>

<%@ include file="/FrontEnd/headerFooter/head.jsp"%>
<!-- 思源黑體設定-->
<style type="text/css">
.login-body {
	width: 75%;
	font-family: 'Noto Sans TC';
	font-style: normal;
	font-weight: 500;
  	box-shadow: 1px 1px 8px black; 
}

h3 {
	font-family: 'Noto Sans TC';
	font-style: normal;
	font-weight: 500;
	margin-bottom: 10px;
}

td img {
	border: 1px solid #ddd;
	border-radius: 4px;
	padding: 5px;
	width: 150px;
	height: 150px;
	box-shadow: 1px 1px 5px red;
}
</style>
<script type="text/javascript">
$(function () {
	$("#primary").change(function (event) {
		 var cat_no = $(this).find("option:selected").val();
		 
		 $.ajax({
             url: "<%=request.getContextPath()%>/FrontEnd/item/ItemLaunch.do",   //存取Json的網址             
             type: "POST",
             cache:false,
             dataType: 'json',
             data:{
            	 action:"FB",
            	 cat_no:cat_no
             },
             //contentType: "application/json",
             success: function (data) {
            	 $("#secondary").html("");
                 //方法一 (回傳多筆資料)                
                 for (var i = 0; i < data.length; i++) {
                     var $opt = $('<option value=""></option>')
                     $opt.val(data[i].part_no);
                     $opt.text(data[i].part_name);
                     $("#secondary").append($opt);
                     
                 }

                 //方法二 (回傳多筆資料)
//                  var i = 0;                    
//                  $.each(data, function () {
//                      alert(data[i].欄位名稱);    
//                      i++;
//                  });

                 //方法三 (回傳單筆資料)
//                  $.each(data, function (index, element) {
//                      alert(element);                      
//                  });
             },

             error: function (xhr) {
                 alert(primary);
                 
             }
         });
		
		
	})
	
})


</script>

</head>
<body>
	<!-- header -->
	<div id="header">
		<%@ include file="/FrontEnd/headerFooter/header.jsp"%>
	</div>

	<div class="container">


		<h3 class="w3ls-title1" align="center">新增商品</h3>

		<%-- 錯誤表列 --%>
		<c:if test="${not empty errorMsgs}">
			<font style="color: red">請修正以下錯誤:</font>
			<ul>
				<c:forEach var="message" items="${errorMsgs}">
					<li style="color: red">${message}</li>
				</c:forEach>
			</ul>
		</c:if>
		<div class="login-body">
			<FORM class="form-horizontal" METHOD="post" ACTION="ItemLaunch.do" name="form1" enctype="multipart/form-data">
				<div class="form-group">
					<table class="table">
						<tr>
							<td>商品名稱:</td>
							<td>
								<input type="TEXT" name="item_name" size="45" value="<%=(itemVO == null) ? "" : itemVO.getItem_name()%>" />
							</td>
						</tr>

						<tr>
							<td>新增商品圖片:</td>
							<td align="left">
								<label class="btn btn-info">
									<input type="file" style="display: none;" id="upfile" name="upfile" accept="image/*" multiple="multiple">
									<i class="fa fa-photo"></i>
									上傳圖片
								</label>
								<div id="pic"></div>
								<label class="btn btn-danger rollback" style="display: none;">
									<input type="button" style="display: none;" value="取消上傳" hidden="hidden">
									<i class="fa fa-trash-o"></i>
									取消上傳
								</label>

							</td>
						</tr>
						<tr>
							<td>商品價格:</td>
							<td>
								<input type="number" name="item_price" size="45" value="<%=(itemVO == null) ? "" : itemVO.getItem_price()%>" />
							</td>
						</tr>

						<!--請將DAO換成service-->
						<jsp:useBean id="categorySvc" scope="page" class="com.category.model.CategoryService" />
						<tr>
							<td>
								商品主類別:
								<font color=red>
									<b>*</b>
								</font>
							</td>
							<td>
								<select id="primary" size="1" name="item_primary_class">
									<!--自行請換成service-->
									<c:forEach var="categoryVO" items="${categorySvc.all}">
										<option value="${categoryVO.cat_no}" ${categoryVO.cat_no == itemVO.getItem_primary_class()?'selected':'' }>${categoryVO.cat_name}
									</c:forEach>
								</select>
							</td>
						</tr>


						<!--請將DAO換成service-->
						<jsp:useBean id="partSvc" scope="page" class="com.particulars.model.ParticularsService" />
						<tr>
							<td>
								商品副類別:
								<font color=red>
									<b>*</b>
								</font>
							</td>
							<td>
								<select id="secondary" size="1" name="item_secondary_class">

									<c:forEach var="partVO" items="${partSvc.all}">
										<option value="${partVO.part_no}" ${partVO.part_no == itemVO.getItem_secondary_class()? 'selected':'' }>${partVO.part_name}
									</c:forEach>
								</select>
							</td>
						</tr>

						<tr>
							<td>商品擁有者:</td>
							<td>
								<input type="hidden" name="item_owner" size="45" style="background-color: #eeeeee" value="${memVO.mem_no}" />
								<input readonly="readonly" type="TEXT" name="mem_name" size="45" style="background-color: #eeeeee" value="${memVO.mem_name}" />
							</td>
						</tr>

						<tr>
							<td>商品庫存:</td>
							<td>
								<input type="TEXT" name="item_inventory" size="45" value="<%=(itemVO == null) ? "" : itemVO.getItem_inventory()%>" />
							</td>
						</tr>
						<tr>
							<td>商品描述:</td>
							<td>
								<input type="TEXT" name="item_description" size="45" value="<%=(itemVO == null) ? "" : itemVO.getItem_description()%>" />
							</td>
						</tr>
					</table>
					<br>
					<input type="hidden" name="action" value="insert">
					<div align="center">
						<input class="btn btn-success btn-lg " type="submit" value="送出新增">
						<input class="btn btn-danger btn-lg " type="button" value="取消新增" onclick="location.href='<%=request.getContextPath()%><%=request.getParameter("requestURL")%>?whichPage=<%=request.getParameter("whichPage")%>'">
						<input type="hidden" name="requestURL" value="<%=request.getParameter("requestURL")%>">
						<!--原送出修改的來源網頁路徑,從request取出後,再送給Controller準備轉交之用-->
						<input type="hidden" name="whichPage" value="<%=request.getParameter("whichPage")%>">
						<!--只用於:istAllEmp.jsp-->
						<input type="hidden" class="scrl" name="scrl" value="${param.scrl}">
					</div>
				</div>
			</FORM>
		</div>
	</div>
	<div id="footer">
		<%@ include file="/FrontEnd/headerFooter/footer.html"%>
	</div>

	<script type="text/javascript">
		$("#upfile").change(
				function() {
					$(".rollback").show();
					$("#pic").empty();
					if (this.files && this.files[0]) {
						for (var i = 0; i < this.files.length; i++) {
							var reader = new FileReader();
							reader.onload = function(e) {
								var img = $("<img width='100' height='100'>")
										.attr("src", e.target.result);
								$("#pic").append(img);
							}
							reader.readAsDataURL(this.files[i]);
						}
					}
				});

		$(".rollback").click(function() {
			$(".rollback").hide();
			$("#upfile").val(null); //砍上傳檔案
			$("#pic").empty(); //砍預覽圖
		});
	</script>
	<script src="<%=request.getContextPath()%>/FrontEnd/js/bootstrap.js "></script>
</body>

</html>