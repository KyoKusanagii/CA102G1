<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
    var today = new Date();
	var showToday = (today.getFullYear()+"-"+(today.getMonth()+1)+"-"+today.getDate()).toString();
	var showNow;
</script>
<!-- 使用現在時間的時鐘 -->
<nav class="navbar navbar-light bg-primary justify-content-bewteen" style="vertical-alignment:middle;">
       <a class="navbar-brand" href="<%=request.getContextPath()%>/FrontEnd/index.jsp" style="font-size: 30px;color: white;"><i>InstaBuy</i></a>
       <span style="margin-left:320px;margin-top:-7px;font-size:20px;color:white;font-weight:bold;font-family:Arial;" id="showToday"></span>
        <div id='currentTime'style="margin-left:-300px;">
        	<span><iframe src="https://freesecure.timeanddate.com/clock/i6db2524/n241/tltw8/fs20/fcfff/tct/pct/ftb/th2" frameborder="0" width="118" height="25" allowTransparency="true"></iframe></span>
		</div>
		<script>$("#showToday").text(showToday)</script>
		<br>
       <form class="form-inline" id="formLogout" style="margin-right: 50px;" action="<%=request.getContextPath()%>/EmpLogout">
         <img src="data:image/jpg;base64,${emp_icon}" style=" border-radius: 500px;width: 60px;height: 60px;">
         <label style="font-size: 20px;color: white;margin-left: 10px;">${emp.emp_name} 您好,</label><a class="navbar-brand" id="logout" href="#" style="color:white;">登出</a>
      </form>
</nav>

