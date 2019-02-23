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
    <title>�|���v���޲z</title>
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
                            <div class="card-header">���u��T</div>
                            <div class="card-body">
                                <h5 class="card-title">���u�s��:${emp.emp_no}</h5>
                                <h5 class="card-title">���u�m�W:${emp.emp_name}</h5>
                            </div>
                        </div>
            <span class="list-group-item list-group-item-action active">�|���b���޲z</span>
            <a href="<%=request.getContextPath()%>/BackEnd/BackEnd.jsp" class="list-group-item list-group-item-action">�^����</a>
        </div>

    </div>
    <div class="operationArea" style="border:0px; width:1100px; left:350px" >
        <p><b>�|���b���C��</b></p>
        <FORM METHOD="post" ACTION="member.do">
	        <b>��J�|���s�� (�pM00001):</b>
	        <input type="text" name="mem_no">
	        <input type="hidden" name="action" value="findByMem_no">
	        <input class="btn btn-success" type="submit" value="�e�X" style="margin-bottom: 7px;">
	    </FORM>
        <table class="table table-hover" style="font-size:20px;">
            <thead class="table-success">
                <tr>
                    <th width=150px>�|���s��</th>
                    <th width=200px>�|���b��</th>
                    <th width=300px>�|���H�c</th>
                    <th width=150px>�{�Ҫ��A</th>
                    <th width=150px>�o���v��</th>
                    <th width=150px>�]�w�|���v��</th>
                </tr>
            </thead>
            <tbody class="table" >
            	<tr>
            		<td>${memVO.mem_no}</td>
            		<td>${memVO.mem_id}</td>
            		<td>${memVO.mem_email}</td>
            		<td style="color:${(memVO.mem_condition==1) ? 'green' : 'red' }">${(memVO.mem_condition==1) ? "�{�ҷ|��" : "�|���{��" }</td>
            		<td style="color:${(memVO.mem_artauth==1) ? 'green' : 'red' }">${(memVO.mem_artauth==1) ? "�}�q" : "���v" }</td>
            		<td>
						<FORM METHOD="post" ACTION="member.do" style="margin-bottom: 0px;">
							<input class="btn btn-danger" type="submit" value="�]�w�|���v��">
							<input type="hidden" name="mem_no" value="${memVO.mem_no}">
							<input type="hidden" name="action" value="findByPK">
						</FORM>
					</td>	
            	</tr>
                
            </tbody>
        </table>
		<h4><a href="<%=request.getContextPath()%>/BackEnd/member/Member.jsp">��^�Ҧ��|���C��</a></h4>
    </div>
</body>

</html>