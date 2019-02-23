<!doctype html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
        <%@ page import="java.util.*,com.item_report.model.ItemReportVO,com.member.model.*"%>
            <%@ page autoFlush="true" buffer="1094kb"%>
                <%! List<ItemReportVO> itemReportList;%>
                    <%! List<String> reportItemNameList;%>
                    <%! String itemPic;%>
                  
                        <html>

                        <head>
                            <meta charset="UTF-8">
                            <title>商品檢舉管理</title>
                            <!-- Latest compiled and minified CSS -->
                            <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css">
                            <!-- Optional theme -->
                            <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.2/css/bootstrap.min.css" integrity="sha384-Smlep5jCw/wG7hdkwQ/Z5nLIefveQRIY9nfy6xoR1uRYBtpZgI6339F5dgvm/e9B" crossorigin="anonymous">
                            <link rel="stylesheet" href="<%=request.getContextPath()%>/BackEnd/css/BackEnd.css">
                            <!-- Latest compiled and minified JavaScript -->
                            <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
                            <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
                            <script src="http://code.jquery.com/jquery-1.11.3.min.js"></script>
                            <script src="<%=request.getContextPath()%>/BackEnd/js/Logout.js"></script>

                        </head>

                        <body>

                            <%! int i=0; %>
                                <% itemReportList = (List<ItemReportVO>)request.getAttribute("itemReportList");%>
                                    <% reportItemNameList = (List<String>)request.getAttribute("reportItemNameList");%>
                                    <% MemService memService = new MemService(); 
                                    //放在某一個scope讓EL才能找到
                                       pageContext.setAttribute("memService",memService);
                                    %>
                                        <%@ include file="/BackEnd/header/header.jsp" %>
                                        <script>
                                            $(function() {

                                                $(".reportViewBtn").click(function() {
                                                    let reasons = $(this).next().val().split(",");
                                                    $("#reportNo").text($(this).parent().parent().children("td:eq(0)").text());
                                                    $("#reportMem").text($(this).parent().parent().children("td:eq(1)").text());
                                                    $("#reportItemNo").text($(this).parent().parent().children("td:eq(2)").text());
                                                    if ($(this).next().val() != "") {
                                                        $("#reportReasons").show();
                                                        $("#reportReasons ul").html(null);
                                                        for (let i = 0; i < reasons.length; i++) {
                                                            $("#reportReasons ul").append("<li>" + reasons[i] + "</li>");
                                                        }
                                                    } else if ($(this).next().val() == "") {
                                                        $("#reportReasons").hide();
                                                        $("#reportReasons ul").html(null);
                                                    }
                                                    $("#reportDescription").text($(this).parent().parent().children("td:eq(3)").text());
                                                    $("#reportItemPic").attr("src",$(this).parent().parent().children("td:eq(4)").children("img").attr("src"));
                                                    $("#reportStatus").text($(this).parent().parent().children("td:eq(5)").text());
                                                    $("input:hidden[name='itemReportNo']").val($("#reportNo").text());
                                                    if ($(this).parent().parent().children("td:eq(5)").text() == "未處理") {
                                                        $(".modal-footer").show();
                                                    } else {
                                                        $(".modal-footer").hide();
                                                    }


                                                });
                                                $("form[name='itemReportCheckForm']").submit(function(){
                                                	alert("審核狀態已更新");
                                                });

                                            });

                                        </script>
                                        <div class="list-group">
                                            <div class="col-2">
                                                <div class="card border-primary mb-3" style="max-width: 18rem;margin-top:30px;">
                                                    <div class="card-header">員工資訊</div>
                                                    <div class="card-body">
                                                        <h5 class="card-title">員工編號:${emp.emp_no}</h5>
                                                        <h5 class="card-title">員工姓名:${emp.emp_name}</h5>
                                                    </div>
                                                </div>
                                                <span class="list-group-item list-group-item-action active">商品檢舉管理</span>
                                                <a href="<%=request.getContextPath()%>/BackEnd/BackEnd.jsp" class="list-group-item list-group-item-action">回首頁</a>
                                            </div>

                                        </div>
                                        <div class="operationArea" style="overflow-y:auto;border: none;width:1100px;">
                                            <p><b>商品檢舉列表</b></p>
                                            <form name="itemReportForm" method="POST">
                                                <table class="table table-striped table-bordered" style="vertical-align: middle;">
                                                    <thead>
                                                        <tr>
                                                            <th>檢舉編號</th>
                                                            <th>檢舉人姓名</th>
                                                            <th>商品名稱</th>
                                                            <th>檢舉描述</th>
                                                            <th>商品圖片</th>
                                                            <th>處理狀態</th>
                                                            <th>檢視</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <c:forEach var="ItemReportVO" items="${itemReportList}">
                                                            <tr>
                                                                <td style="vertical-align: middle;">${ItemReportVO.item_Report_NO}</td>
                                                                <td style="vertical-align: middle;">${memService.findByPK(ItemReportVO.mem_NO).mem_name}</td>
                                                                <td style="vertical-align: middle;">
                                                                    <%=reportItemNameList.get(i)%>
                                                                </td>
                                                                <% i++; %>
                                                                    <td style="vertical-align: middle;max-width: 260px;" class="text-truncate">${ItemReportVO.report_Description}</td>
                                                                    <c:if test="${ItemReportVO.reportPic != null}">
                                                                    	<td><img src='data:image/jpg;base64,${Base64.getEncoder().encodeToString(ItemReportVO.reportPic)}' style='width:90px;height:80px;'></td>	
                                                                    </c:if>
                                                                    <c:if test="${ItemReportVO.reportPic == null}">
                                                                    	<td><img src='' style='width:90px;height:80px;'></td>	
                                                                    </c:if>		
                                                                    <c:if test="${ItemReportVO.report_Status == '0'}">
                                                                        <td style="vertical-align: middle;">未處理</td>
                                                                    </c:if>
                                                                    <c:if test="${ItemReportVO.report_Status == '1'}">
                                                                        <td style="vertical-align: middle;">已審核</td>
                                                                    </c:if>
                                                                    <c:if test="${ItemReportVO.report_Status == '2'}">
                                                                        <td style="vertical-align: middle;">已駁回</td>
                                                                    </c:if>
                                                                    <td style="vertical-align: middle;"><button type="button" id="reportReason<%=i %>" class="btn btn-danger reportViewBtn" data-toggle="modal" data-target="#myModal">檢視</button><input class="reasons" type="hidden" value="${ItemReportVO.report_reasons}"></td>
                                                            </tr>
                                                        </c:forEach>
                                                        <% i=0; %>
                                                            <%if(itemReportList == null){%>
                                                                <jsp:forward page="/showAllItemReport" />
                                                            <%}%>
                                                    </tbody>
                                                </table>
                                            </form>
                                        </div>
                                        <div id="myModal" class="modal fade" role="dialog">
                                            <div class="modal-dialog">
                                                <!-- Modal content-->
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <h4 class="modal-title">檢舉詳情</h4>
                                                    </div>
                                                    <div class="modal-body">
                                                        <span>檢舉編號:</span><span id="reportNo"></span><br>
                                                        <span>檢舉人:</span><span id="reportMem"></span><br>
                                                        <span>檢舉商品名稱:</span><span id="reportItemNo"></span><br>
                                                        <div id="reportReasons">
                                                            <span>檢舉原因:</span>
                                                            <ul type="disc"></ul>
                                                        </div>
                                                        <span>檢舉描述:</span><br><textarea id="reportDescription" cols="44" rows="5" style="resize:none;" disabled></textarea><br>
                                                        <span>檢舉的商品圖片:</span><br><span><img id="reportItemPic" style="width: 300px;height: 300px;margin-left:80px;border:solid 1px #A9A9A9;"></span><br>	
                                                        <span>處理狀態:</span><span id="reportStatus"></span><br>
                                                    </div>
                                                    <div class="modal-footer">
                                                        <span style="color: red;font-weight: bold;">確定審核通過?</span>
                                                        <form name="itemReportCheckForm" action="<%=request.getContextPath()%>/ItemReport.jsp" method="post">
                                                            <!--取得要檢舉的商品編號-->
                                                            <input type="hidden" name="itemReportNo">
                                                            <!--取得處理該檢舉的員工編號-->
                                                            <input type="hidden" name="itemReportHandler" value="${emp.emp_no}">
                                                            <input type="hidden" name="itemReportStatus" value="1">
                                                            <button type="submit" class="btn btn-default">通過</button>
                                                        </form>
                                                        <form name="itemReportCheckForm" action="<%=request.getContextPath()%>/ItemReport.jsp" method="post">
                                                            <!--取得要檢舉的商品編號-->
                                                            <input type="hidden" name="itemReportNo"> 
                                                            <!--取得處理該檢舉的員工編號-->
                                                            <input type="hidden" name="itemReportHandler" value="${emp.emp_no}">
                                                            <input type="hidden" name="itemReportStatus" value="2">
                                                            <button type="submit" class="btn btn-default">駁回</button>
                                                        </form>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                        </body>

                        </html>
