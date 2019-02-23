<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.news.model.*"%>

<!doctype html>
<html>

<head>
    <meta charset="UTF-8">
    <title>設置最新消息</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css">
    <!-- Optional theme -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.2/css/bootstrap.min.css" integrity="sha384-Smlep5jCw/wG7hdkwQ/Z5nLIefveQRIY9nfy6xoR1uRYBtpZgI6339F5dgvm/e9B" crossorigin="anonymous">
    <link rel="stylesheet" href="css/BackEnd.css">
    <!-- Latest compiled and minified JavaScript -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/BackEnd/css/wickedpicker.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/BackEnd/css/wickedpicker.scss">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/BackEnd/css/BackEnd.css">
    <!-- Latest compiled and minified JavaScript -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="http://code.jquery.com/jquery-1.11.3.min.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <script src="<%=request.getContextPath()%>/BackEnd/js/wickedpicker.min.js"></script>
    <script src="<%=request.getContextPath()%>/BackEnd/js/Logout.js"></script>
    <script>
        $(document).ready(function() {
            var today = new Date();
            var options = {
                //now: "12:35", //hh:mm 24 hour format only, defaults to current time 
                twentyFour: true, //Display 24 hour format, defaults to false 
                upArrow: 'wickedpicker__controls__control-up', //The up arrow class selector to use, for custom CSS 
                downArrow: 'wickedpicker__controls__control-down', //The down arrow class selector to use, for custom CSS 
                close: 'wickedpicker__close', //The close class selector to use, for custom CSS 
                hoverState: 'hover-state', //The hover state class to use, for custom CSS 
                title: 'Timepicker', //The Wickedpicker's title, 
                showSeconds: true, //Whether or not to show seconds, 
                secondsInterval: 1, //Change interval for seconds, defaults to 1  , 
                minutesInterval: 1, //Change interval for minutes, defaults to 1 
                beforeShow: null, //A function to be called before the Wickedpicker is shown 
                show: null, //A function to be called when the Wickedpicker is shown 
                clearable: true //Make the picker's input clearable (has clickable "x")  }; 
            };

            $("#StartTime").wickedpicker(options);
            $("#StartDate").datepicker({
                showOn: "both",
                //beforeShow: customRange,
                dateFormat: "yy-mm-dd",
                minDate: today,
                maxDate: new Date(today.getFullYear(), today.getMonth(), today.getDate() + 28)
            });

            //                    function customRange(input) { //選擇結束日期的範圍
            //                        if (input.id == 'EndDate') {
            //                            var minDate = new Date($('#StartDate').val());
            //                            var maxDate = new Date($('#StartDate').val());
            //                            minDate.setDate(minDate.getDate());
            //                            maxDate.setDate(minDate.getDate() + 2);
            //                            return {
            //                                minDate: minDate,
            //                                maxDate: maxDate
            //                            };
            //                        }
            //                    }

        });

    </script>
</head>
<body>
	<!-- header -->
	<%@ include file="/BackEnd/header/header.jsp" %>
    <!-- Placed at the end of the document so the pages load faster --> 
	<c:if test="${not empty errorMsgs}">
	 <font style="color:red">請修正以下錯誤:</font>
	 <ul>
	  <c:forEach var="message" items="${errorMsgs}">
	   <li style="color:red">${message.value}</li>
	  </c:forEach>
	 </ul>
	</c:if>

	<div class="container">
		
		<form method="post" action="<%=request.getContextPath()%>/BackEnd/news.do">
			<div class="form-group">
			    <label for="Textarea1" style="font-size: 30px">最新消息主題</label>
			    <i class="fa fa-pencil prefix"></i>
			    <textarea name="news_title" class="form-control rounded-0" value="${param.news_title}"></textarea>
			    <label for="StartDate">預計發布日期</label><br>
                <input type="text" id="StartDate" name="news_date_day"><br>
                <label for="StartTime">預計發布時間</label><br>
                <input type="text" id="StartTime" name="news_date_time" class="timepicker">
			</div>	
			
			<div class="form-group">
			
			    <label for="Textarea2" style="font-size: 30px">內容</label>
			    <i class="fa fa-pencil prefix"></i>
			    
			    
			    <textarea name="news_content" class="form-control " value="${param.news_content}" style="font-size:20px;" rows="20"></textarea>
			    
			</div>

			<div class="text-center">
			<input type="hidden" name="action" value="insert">
			<input type="submit" class="btn btn-primary"  value="發布消息">
			
			<input type="button" class="btn btn_info" value="取消" onclick="location.href = 'News.jsp'">
			</div>
		</form>
		
	</div>
	
	
	<script src="<%=request.getContextPath()%>/FrontEnd/forum/ckeditor/ckeditor.js"></script>


		
		<script>
		        CKEDITOR.replace('news_content', {
		            extraPlugins: 'base64image',
		            removePlugins: 'image',
		            removePlugins: 'resize',
		            removeDialogTabs: 'image:advanced;link:advanced',
		            height: 500,
		        });
		</script>

</body>

</html>