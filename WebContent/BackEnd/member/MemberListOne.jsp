<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.member.model.*"%>

<%
  MemVO memVO = (MemVO) request.getAttribute("memVO");
%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>會員權限管理</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css">
    <!-- Optional theme -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.2/css/bootstrap.min.css" integrity="sha384-Smlep5jCw/wG7hdkwQ/Z5nLIefveQRIY9nfy6xoR1uRYBtpZgI6339F5dgvm/e9B" crossorigin="anonymous">
    <link rel="stylesheet" href="css/BackEnd.css">
    <!-- Latest compiled and minified JavaScript -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
    <script src="http://code.jquery.com/jquery-1.11.3.min.js"></script>
    <script src="js/Logout.js"></script>
    <script>
        $(document).ready(function() {
            $(".memViewBtn").click(function() {
                $(".modal-body > #memId").text($(this).parent().parent().children("td:eq(0)").text());
                $(".modal-body > #memName").text($(this).parent().parent().children("td:eq(1)").text());
                $(".modal-body > #memAccountId").text($(this).parent().parent().children("td:eq(2)").text());
            });
        });

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
            <span class="list-group-item list-group-item-action active">會員帳號管理</span>
            <a href="<%=request.getContextPath()%>/BackEnd/BackEnd.jsp" class="list-group-item list-group-item-action">回首頁</a>
        </div>

    </div>
    <div class="operationArea" style="border:0px; width:1100px; left:350px" >
        <p><b>會員帳號列表</b></p>
        <FORM METHOD="post" ACTION="member.do">
	        <b>輸入會員編號 (如M00001):</b>
	        <input type="text" name="mem_no">
	        <input type="hidden" name="action" value="findByMem_no">
	        <input class="btn btn-success" type="submit" value="送出" style="margin-bottom: 7px;">
	    </FORM>
        <table class="table table-hover" style="font-size:20px;">
            <thead class="table-success">
                <tr>
                    <th width=150px>會員編號</th>
                    <th width=200px>會員帳號</th>
                    <th width=300px>會員信箱</th>
                    <th width=150px>認證狀態</th>
                    <th width=150px>發文權限</th>
                    <th width=150px>設定會員權限</th>
                </tr>
            </thead>
            <tbody class="table" >
            	<tr>
            		<td>${memVO.mem_no}</td>
            		<td>${memVO.mem_id}</td>
            		<td>${memVO.mem_email}</td>
            		<td style="color:${(memVO.mem_condition==1) ? 'green' : 'red' }">${(memVO.mem_condition==1) ? "認證會員" : "尚未認證" }</td>
            		<td style="color:${(memVO.mem_artauth==1) ? 'green' : 'red' }">${(memVO.mem_artauth==1) ? "開通" : "停權" }</td>
            		<td>
						<FORM METHOD="post" ACTION="member.do" style="margin-bottom: 0px;">
							<input class="btn btn-danger" type="submit" value="設定會員權限">
							<input type="hidden" name="mem_no" value="${memVO.mem_no}">
							<input type="hidden" name="action" value="findByPK">
						</FORM>
					</td>	
            	</tr>
                
            </tbody>
        </table>
		<h4><a href="<%=request.getContextPath()%>/BackEnd/member/Member.jsp">返回所有會員列表</a></h4>
    </div>
</body>

</html>