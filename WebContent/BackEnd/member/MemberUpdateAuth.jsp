<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.member.model.*"%>

<%
  MemVO memVO = (MemVO) request.getAttribute("memVO"); //EmpServlet.java (Concroller) 存入req的empVO物件 (包括幫忙取出的empVO, 也包括輸入資料錯誤時的empVO物件)
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>會員權限修改</title>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css">
    <!-- Optional theme -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.2/css/bootstrap.min.css" integrity="sha384-Smlep5jCw/wG7hdkwQ/Z5nLIefveQRIY9nfy6xoR1uRYBtpZgI6339F5dgvm/e9B" crossorigin="anonymous">
    <link rel="stylesheet" href="css/BackEnd.css">
    <!-- Latest compiled and minified JavaScript -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
    <script src="http://code.jquery.com/jquery-1.11.3.min.js"></script>
    <script src="js/Logout.js"></script>

<style>
  h4 {
    color: blue;
    display: inline;
  }
</style>

<style>
  table {
  	font-family:微軟正黑體;
  	font-size:20px;
	width: 450px;
	background-color: white;
	margin-top: 1px;
	margin-bottom: 1px;
  }
  table, th, td {
    border: 0px solid #CCCCFF;
  }
  th, td {
    padding: 1px;
  }
</style>

</head>
<body bgcolor='white'>
<%@ include file="/BackEnd/header/header.jsp" %>
<table id="table-1" align="center">
	<tr class="table-primary"><td align="center">
		 <h3>會員權限修改</h3>
	</td></tr>
</table>
<FORM METHOD="post" ACTION="member.do">
<table align="center">
	<tr>
		<td>會員編號:</td>
		<td><%=memVO.getMem_no()%></td>
	</tr>
	<tr>
		<td>會員帳號:</td>
		<td><%=memVO.getMem_id()%></td>
	</tr>
	<tr>
		<td>認證狀態:</td>
		<td style="color:${(memVO.mem_condition==1) ? 'green' : 'red' }">
		<b><input type="hidden" name="mem_condition" value="<%=memVO.getMem_condition()%>"> ${(memVO.mem_condition==1) ? "認證會員" : "尚未認證" }</b>
		</td>
	</tr>
	<tr>
		<td>發文權限:</td>
		<td style="color:${(memVO.mem_artauth==1) ? 'green' : 'red' }">
		<input type="radio" name="mem_artauth" value="1" ${(memVO.mem_artauth==1) ? 'checked' : '' }><b style="color:green;"> 開通</b>
		<input type="radio" name="mem_artauth" value="0" ${(memVO.mem_artauth==1) ? '' : 'checked' }><b style="color:red;"> 停權</b>
		</td>
	</tr>
	<tr>
		<td colspan="2" align="center">
			<input type="hidden" name="action" value="setAuth">
			<input type="hidden" name="mem_no" value="<%=memVO.getMem_no()%>">
			<input class="btn btn-warning" type="submit" value="送出修改">
		</td>
	</tr>
</table>
</FORM>
</body>
</html>