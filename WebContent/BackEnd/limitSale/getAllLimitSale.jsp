<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*,com.emp.model.EmpVO"%>
<%@ page import="com.limitSaleSub.model.*"%>

<%
	LimitSaleSubService lsSvc = new LimitSaleSubService();
    Set<LimitSaleSubVO> lsList = lsSvc.getAll();
    pageContext.setAttribute("lsList",lsList);
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 <meta charset="UTF-8">
    <title>設置限時特賣</title>
   <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css">
    <!-- Optional theme -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.2/css/bootstrap.min.css" integrity="sha384-Smlep5jCw/wG7hdkwQ/Z5nLIefveQRIY9nfy6xoR1uRYBtpZgI6339F5dgvm/e9B" crossorigin="anonymous">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/BackEnd/css/BackEnd.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <link href="<%=request.getContextPath()%>/BackEnd/css/carouselIndexStyle.css" rel="stylesheet" type="text/css">
    <script src="<%=request.getContextPath()%>/BackEnd/js/Logout.js"></script>
    <!-- Latest compiled and minified JavaScript -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
    <script src="http://code.jquery.com/jquery-1.11.3.min.js"></script>
    <!-- checkbox CSS -->
     <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/pretty-checkbox@3.0/dist/pretty-checkbox.min.css">
     
<!-- 搜尋用 -->
<script>    
  function myFunction() {
  var input = document.getElementById("Search");
  var filter = input.value.toLowerCase();
  var nodes = document.getElementsByClassName('target');

  for (i = 0; i < nodes.length; i++) {
    if (nodes[i].innerText.toLowerCase().includes(filter)) {
      nodes[i].style.display = "";
    } else {
      nodes[i].style.display = "none";
    }
  }
} 
</script>       
    
    
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
            <a href="<%=request.getContextPath()%>/BackEnd/limitSale/getAllLimitSale.jsp" class="list-group-item list-group-item-action active">限時特賣一覽表</a>
            <a href="<%=request.getContextPath()%>/BackEnd/limitSale/limitSaleOnIndex.jsp" class="list-group-item list-group-item-action">首頁搶先曝光中</a>
            <a href="<%=request.getContextPath()%>/BackEnd/BackEnd.jsp" class="list-group-item list-group-item-action">回首頁</a>
        </div>
    </div>
    <div class="operationArea" align="center" style="padding-top: 40px;overflow:auto;">
    
    
    
    
    	<form action="<%=request.getContextPath()%>/limitSale.do" method="post" id="submitMain">
		 <div class='container'>
			<table align="center" width="20%">
			  <tr>
			    <td style="padding-right: 10px">
			      <input type="text" id="Search" class="form-control" onkeyup="myFunction()" placeholder="關鍵字找商品" title="Type in a name">
			    </td>
			  </tr>
			</table>
			<br>		 
<c:forEach var="lmVO" items="${lsList}">
		        <div class="row target" style="border-width:3px;border-style:dashed;border-color:#1963F9;padding:5px;margin:3px;">            
		            <div class="col-3">
		                <img src="data:image/png;base64,${lmVO.byteString}" width="150px" height="150px">
		            </div>
		            <div class="col-9">
		                <div class="row">
		                    <div class="col-6 text-left">賣家編號: ${lmVO.item_owner}</div>
		                    <div class="col-3 text-left">原價: ${lmVO.item_price}</div>
		                    <div class="col-3 text-left">特價: ${lmVO.sale_price}</div>
		                </div>    
		                <div class="row">
		                	<div class="col-12 text-left">${lmVO.item_name}</div>
		                </div>
		                <div class="row">
		                    <div class="col-6 text-left">特賣開始: ${lmVO.sale_start}</div>
		                    <div class="col-6 text-left">特賣結束: ${lmVO.sale_end}</div>
		                </div>
		                <div class="row">
		                    <div class="col-8 text-left">備註: ${lmVO.sale_remark}</div>
		                    <div class="col-4 text-left">
								<div class="pretty p-default p-round">
									<input type="checkbox" name="saleItem" value="${lmVO.item_no}"/>
								    <div class="state p-danger">
								    	<label>上首頁?</label>
								    </div>
								</div>                      
		                    </div>
		                </div>
		            </div>                        
		        </div>  
</c:forEach>
		<br>
		</div>
		<input type="hidden" name="action" value="placeOnIndex">          
	<input type="submit" value="首頁重新佈署" class="btn btn-danger btn-lg" id="button">
</form>
	

<script>
$("#button").click(function(){
	var check=$("input[name='saleItem']:checked").length;
	if(check==0){
		alert("明明就一個都沒選!!!");
		return false;
	}else{
		if(check > 0 && check < 4 ){
			if(confirm("不滿4個會自動幫你湊滿喔!")){
				alert("幫你湊好了~");
				return true;
			}else{
				return false;
			}
			
		}
		alert("現在已經上去首頁啦~");
		return true;
	}
})
</script>
	

    
</body>
</html>