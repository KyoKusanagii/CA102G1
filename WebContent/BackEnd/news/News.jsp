<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.news.model.*"%>
<%
    NewsService newsSvc = new NewsService();
    List<NewsVO> list = newsSvc.getAll();
    pageContext.setAttribute("list",list);
%>

<!doctype html>
<html>

<head>
    <meta charset="UTF-8">
    <title>設置最新消息</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css">
    <!-- Optional theme -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.2/css/bootstrap.min.css" integrity="sha384-Smlep5jCw/wG7hdkwQ/Z5nLIefveQRIY9nfy6xoR1uRYBtpZgI6339F5dgvm/e9B" crossorigin="anonymous">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/BackEnd/css/BackEnd.css">
    <!-- Latest compiled and minified JavaScript -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/BackEnd/css/wickedpicker.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/BackEnd/css/wickedpicker.scss">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/BackEnd/css/BackEnd.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/BackEnd/css/EasySideBar.css">
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
                twentyFour: false, //Display 24 hour format, defaults to false 
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
                dateFormat: "yy/mm/dd",
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
            <span class="list-group-item list-group-item-action active">設置最新消息</span>
            <a href="<%=request.getContextPath()%>/BackEnd/BackEnd.jsp" class="list-group-item list-group-item-action">回首頁</a>
        </div>

    </div>
    <div class="operationArea" align="center" style="border:0px; width:1000px; left:400px; font-family:微軟正黑體">
    	
    	<div class="row col-md-12" style="margin-top:5%">
			<div class="col-4">
				<i style="color:blue"><h2>最新消息列表</h2></i>
			</div>
			<div class="col-7"></div>
			<div class="col-1">
				<a href="<%=request.getContextPath()%>/BackEnd/news/NewsPost.jsp"><button class="btn btn-primary">發表主題</button></a>
			</div>
		</div>
		
        <!-- Table -->
		<table class="table table-hover " style="font-family: 微軟正黑體">
			<thead >
				<tr class="w3ls-header table-dark" style="color:white">
					<th class="text-center" width="250"><h3>預計發表時間</h3></th>
					<th class="text-left"><h3  style="margin-left:15%;">主題</h3></th>
					<th class="text-right" width="50"></th>
					<th class="text-right" width="50"></th>
				</tr>
			</thead>
			
			<c:forEach var="newsVO" items="${list}">
			<tbody>
				<tr>
					<td class="text-left"><fmt:formatDate pattern="YYYY年MM月dd日  HH:mm" value="${newsVO.news_date}" /></td>
					<td class="text-left" style="font-size:24px"><a href="NewsArticle.jsp?news_no=${newsVO.news_no}">${newsVO.news_title}</a></td>
					<td class="text-right">
						<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/BackEnd/news.do" style="margin-bottom: 0px;">
							<input class="btn btn-primary" type="submit" value="修改">
							<input type="hidden" name="news_no" value="${newsVO.news_no}">
							<input type="hidden" name="action" value="findByPK">
						</FORM>
					</td>
					<td class="text-right">
						<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/BackEnd/news.do" style="margin-bottom: 0px;">
							<input class="btn btn-danger" type="submit" value="刪除">
							<input type="hidden" name="news_no" value="${newsVO.news_no}">
							<input type="hidden" name="action" value="delete">
						</FORM>
					</td>
				</tr>
			</tbody>
			</c:forEach>
		</table>
    </div>

</body>

</html>
