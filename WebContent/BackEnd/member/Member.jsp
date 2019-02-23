<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.member.model.*"%>


<%
    MemService memSvc = new MemService();
    List<MemVO> list = memSvc.getAllDesc();
    pageContext.setAttribute("list",list);
%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>會員權限管理</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css">
    <!-- Optional theme -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.2/css/bootstrap.min.css" integrity="sha384-Smlep5jCw/wG7hdkwQ/Z5nLIefveQRIY9nfy6xoR1uRYBtpZgI6339F5dgvm/e9B" crossorigin="anonymous">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/BackEnd/css/BackEnd.css">
    <!-- Latest compiled and minified JavaScript -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
    <script src="http://code.jquery.com/jquery-1.11.3.min.js"></script>
    <script src="<%=request.getContextPath()%>/BackEnd/js/Logout.js"></script>
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
      <nav class="navbar navbar-light bg-primary justify-content-bewteen">
                    <a class="navbar-brand" style="font-size: 30px;color: white;"><i>InstaBuy</i></a>
                    <form class="form-inline" id="formLogout" style="margin-right: 50px;" action="<%=request.getContextPath()%>/EmpLogout">
                        <img src="data:image/jpg;base64,${emp_icon}" style=" border-radius: 500px;width: 60px;height: 60px;">
                        <label style="font-size: 20px;color: white;margin-left: 10px;">${emp.emp_name} 您好,</label><a class="navbar-brand" id="logout" href="#" style="color:white;">登出</a>
                    </form>
                </nav>
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
        <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/BackEnd/member.do">
	        <b>輸入會員編號 (如M00001):</b>
	        <input type="text" name="mem_no">
	        <input type="hidden" name="action" value="findByMem_no">
	        <input class="btn btn-success" type="submit" value="送出" style="margin-bottom: 7px;">
	    </FORM>
        <table class="table table-hover" style="font-size:20px;">
            <thead class="table-primary">
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
            <c:forEach var="memVO" items="${list}" begin="<%= 0%>" end="${list.size()}">
            	<tr>
            		<td>${memVO.mem_no}</td>
            		<td>${memVO.mem_id}</td>
            		<td>${memVO.mem_email}</td>
            		<td style="color:${(memVO.mem_condition==1) ? 'green' : 'red' }">${(memVO.mem_condition==1) ? "認證會員" : "尚未認證" }</td>
            		<td style="color:${(memVO.mem_artauth==1) ? 'green' : 'red' }">${(memVO.mem_artauth==1) ? "開通" : "停權" }</td>
            		<td>
						<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/BackEnd/member.do" style="margin-bottom: 0px;">
							<input class="btn btn-danger" type="submit" value="設定會員權限">
							<input type="hidden" name="mem_no" value="${memVO.mem_no}">
							<input type="hidden" name="action" value="findByPK">
						</FORM>
					</td>
            	</tr>
            </c:forEach>
                <!-- 
                <tr>
                    <td>001</td>
                    <td>李毓芬</td>
                    <td>Apple12345</td>
                    <td><button type="button" class="btn btn-danger memViewBtn" data-toggle="modal" data-target="#myModal">設定權限</button></td>
                </tr>
				 -->
            </tbody>
        </table>
    </div>
    
<c:if test="${openModal!=null}">

<div class="modal fade" id="basicModal" tabindex="-1" role="dialog" aria-labelledby="basicModal" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
				
			<div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h3 class="modal-title" id="myModalLabel">The Bootstrap modal-header</h3>
            </div>
			
			<div class="modal-body">
<!-- =========================================以下為原listOneEmp.jsp的內容========================================== -->
               <jsp:include page="MemberUpdateAuth.jsp" />
<!-- =========================================以上為原listOneEmp.jsp的內容========================================== -->
			</div>
			
			<div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary">Save changes</button>
            </div>
		
		</div>
	</div>
</div>

<script>
$("#basicModal").modal({show: true});
</script>
</c:if>
    
    <!--
    <div id="myModal" class="modal fade" role="dialog">
        <div class="modal-dialog">
            
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">帳號詳情</h4>
                </div>
                <div class="modal-body">
                    <span>會員編號:</span><span id="memId"></span><br>
                    <span>會員姓名:</span><span id="memName"></span><br>
                    <span>會員帳號:</span><span id="memAccountId"></span><br>
                    <span>操作權限:</span>
                    <span>
                        <ul>
                            <li>已認證會員<label class="yes">是<input name="isMem" type="radio"></label><label class="no">否<input name="isMem" type="radio"></label></li>
                            <li>發文權限<label class="yes">是<input name="isPost" type="radio"></label><label class="no">否<input name="isPost" type="radio"></label></li>
                            <li>留言權限<label class="yes">是<input name="isReply" type="radio"></label><label class="no">否<input name="isReply" type="radio"></label></li>
                        </ul>
                    </span>
                </div>
                <div class="modal-footer">
                    <span style="color: red;font-weight: bold;">確定調整權限?</span>
                    <button type="button" class="btn btn-default" data-dismiss="modal">是</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">否</button>
                </div>
            </div>
        </div>
    </div>
    -->
</body>

</html>