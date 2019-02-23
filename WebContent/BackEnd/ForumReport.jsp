<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*,com.article_report.model.ArticleReportVO,com.artireply_report.model.ArtiReplyReportVO,com.member.model.*"%>
        <!doctype html>
        <jsp:useBean id="articleService" scope="page" class="com.article.model.ArticleService"/>
        <jsp:useBean id="artiService " scope="page" class="com.arti_reply.model.Arti_replyService"/>
            <html>
            <head>
                <meta charset="UTF-8">
                <title>討論區檢舉管理</title>
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
                <script>
                    $(document).ready(function() {
                    	
                        function showAllArticleReport() { //顯示所有檢舉文章的方法
                            $.ajax({
                                url: "<%=request.getContextPath()%>" + "/showAllArticleReport",
                                type: "post",
                                dataType: "json",
                                success: function(jarray) {
                                    $.each(jarray, function(index, json) {
                                        if (json.report_status == 0)
                                            json.report_status = "未處理";
                                        else if (json.report_status == 1)
                                            json.report_status = "已審核";
                                        else if (json.report_status == 2)
                                            json.report_status = "已駁回";
                                        $("#reportPostTable tbody").append("<tr><td>" + json.article_report_no +
                                            "</td><td>"+ json.mem_name + "</td><td style='max-width: 260px;' class='text-truncate'>" + json.arti_topic + "</td><td>" + json.report_description + "</td><td>" + json.report_status +
                                            "</td><td><button type='button' class='btn btn-danger articleReportViewBtn' data-toggle='modal'" +
                                            "data-target='#myModal'>檢視</button><input class='reasons' type='hidden' value=" + json.article_report_reasons + "></td></tr>");

                                    });
                                }
                            });

                        }
                        $(window).on("load", function() {
                            showAllArticleReport();
                        });

                        $("#reportReplyTable").hide();
                        $("#reportArtiReplyContent").parent().hide(); //一開始先把文章留言內容先隱藏
                        $("#reportPostBtn").click(function() {
                            $("#reportReplyTable").hide();
                            $("#reportPostTable").show();
                        });

                        $("#reportReplyBtn").click(function() {		//顯示所有檢舉留言的方法
                            $("#reportReplyTable tbody").html(null);
                            $("#reportPostTable").hide();
                            $("#reportReplyTable").show();

                            $.ajax({
                                url: "<%=request.getContextPath()%>" + "/showAllArtiReplyReport",
                                type: "post",
                                dataType: "json",
                                success: function(jarray) {
                                    $.each(jarray, function(index, json) {
                                        if (json.report_status == 0)
                                            json.report_status = "未處理";
                                        else if (json.report_status == 1)
                                            json.report_status = "已審核";
                                        else if (json.report_status == 2)
                                            json.report_status = "已駁回";
                                        $("#reportReplyTable tbody").append("<tr><td>" + json.artiReply_report_no +
                                            "</td><td>" + json.mem_name + "</td><td style='max-width: 260px;' class='text-truncate'>" + 
                                            json.arti_topic + "</td><td style='max-width: 260px;' class='text-truncate'>" + json.rep_content +
                                            "</td><td>" + json.report_description + "</td><td>" + json.report_status +
                                            "</td><td><button type='button' class='btn btn-danger artireplyReportViewBtn' data-toggle='modal'" +
                                            "data-target='#myModal'>檢視</button><input class='reasons' type='hidden' value=" + json.artiReply_report_reasons + "></td></tr>");

                                    });
                                }
                            });
                        });

                        $(document).on("click", ".articleReportViewBtn", function() { //文章檢舉檢視
                        	let reasons = $(this).next().val().split(",");
                            $("#reportArtiReplyContent").parent().hide(); //把留言編號隱藏
                            $(".modal-footer button").removeClass("artiReplyReportHandleBtn");
                            $(".modal-footer button").addClass("articleReportHandleBtn");
                            $("#reportNo").text(($(this).parent().parent().children("td:eq(0)").text())); //如果只查單一文章，導至/findArticleReport
                            $("#reportMember").text(($(this).parent().parent().children("td:eq(1)").text()));
                            $("#reportArtiNum").text(($(this).parent().parent().children("td:eq(2)").text()))
                            $("#reportDescription").text(($(this).parent().parent().children("td:eq(3)").text()));
                            $("#reportStatus").text(($(this).parent().parent().children("td:eq(4)").text()));
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
                            if ($(this).parent().parent().children("td:eq(4)").text() == "未處理") {
                                $(".modal-footer").show();
                            } else {
                                $(".modal-footer").hide();
                            }
                        });

                        $(document).on("click", ".artireplyReportViewBtn", function() { //留言檢舉檢視，按鈕是append出來的要用document監聽事件
                         	let reasons = $(this).next().val().split(",");
                            $("#reportArtiReplyContent").parent().show(); //把留言內容顯示
                            $(".modal-footer button").removeClass("articleReportHandleBtn");
                            $(".modal-footer button").addClass("artiReplyReportHandleBtn");
                            $("#reportNo").text(($(this).parent().parent().children("td:eq(0)").text())); //如果只查單一員工，導至/findArticleReport
                            $("#reportMember").text(($(this).parent().parent().children("td:eq(1)").text()));
                            $("#reportArtiNum").text(($(this).parent().parent().children("td:eq(2)").text()));
                            $("#reportArtiReplyContent").text(($(this).parent().parent().children("td:eq(3)").text()));
                            $("#reportDescription").text(($(this).parent().parent().children("td:eq(4)").text()));
                            $("#reportStatus").text(($(this).parent().parent().children("td:eq(5)").text()));
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

                            if ($(this).parent().parent().children("td:eq(5)").text() == "未處理") {
                                $(".modal-footer").show();
                            } else {
                                $(".modal-footer").hide();
                            }

                        });

                        $(".modal-footer button").click(function() {
                            //如果按下的button是文章檢舉檢視的按鈕
                            if ($(this).hasClass("articleReportHandleBtn"))
                                $.ajax({
                                    url: "<%=request.getContextPath()%>" + "/updateArticleReport",
                                    type: "post",
                                    dataType: "json",
                                    data: {
                                        articleReportStatus: $(this).val(),
                                        articleReportNo: $("#reportNo").text()
                                    }
                                });
                            //如果按下的button是留言檢舉檢視的按鈕
                            else if ($(this).hasClass("artiReplyReportHandleBtn"))
                                $.ajax({
                                    url: "<%=request.getContextPath()%>" + "/updateArtiReplyReport",
                                    type: "post",
                                    dataType: "json",
                                    data: {
                                        artiReplyReportStatus: $(this).val(),
                                        artiReplyReportNo: $("#reportNo").text()
                                    }
                                });
                            alert("審核狀態已更新");
                            location.reload();
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
                        <span class="list-group-item list-group-item-action active">討論區檢舉管理</span>
                        <a href="#" id="reportPostBtn" class="list-group-item list-group-item-action">文章檢舉管理</a>
                        <a href="#" id="reportReplyBtn" class="list-group-item list-group-item-action">留言檢舉管理</a>
                        <a href="<%=request.getContextPath()%>/BackEnd/BackEnd.jsp" class="list-group-item list-group-item-action">回首頁</a>
                    </div>
                    <!--有forward路徑要這樣寫 <%=request.getContextPath()%>/BackEnd/BackEnd.html-->
                </div>
                <div class="operationArea" style="border: none;overflow-y:auto;width:1100px;">
                    <div id="reportPostTable">
                        <p><b>文章檢舉列表</b></p>
                        <table class="table table-striped table-bordered" style="overflow-y:scroll;">
                            <thead>
                                <tr>
                                    <th>檢舉編號</th>
                                    <th>檢舉人姓名</th>
                                    <th>檢舉文章標題</th>
                                    <th>檢舉描述</th>
                                    <th>處理狀態</th>
                                    <th>檢視</th>
                                </tr>
                            </thead>
                            <tbody>
                            </tbody>
                        </table>
                    </div>
                    <div id="reportReplyTable">
                        <p><b>留言檢舉列表</b></p>
                        <table class="table table-striped table-bordered" style="overflow-y:scroll;">
                            <thead>
                                <tr>
                                    <th>檢舉編號</th>
                                    <th>檢舉人姓名</th>
                                    <th>文章標題</th>
                                    <th>檢舉留言內容</th>
                                    <th>檢舉描述</th>
                                    <th>處理狀態</th>
                                    <th>檢視</th>
                                </tr>
                            </thead>
                            <tbody>
                            </tbody>
                        </table>
                    </div>
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
                                <span>檢舉人:</span><span id="reportMember"></span><br>
                                <span>檢舉文章標題:</span><span id="reportArtiNum"></span><br>
                                <span><span>檢舉留言內容:</span><span id="reportArtiReplyContent"></span><br></span>
                                <div id="reportReasons">
                                       <span>檢舉原因:</span>
                                       <ul type="disc"></ul>
                                </div>
                                <span>檢舉描述:</span><br><textarea id="reportDescription" cols="44" rows="10" resize="none;" disabled></textarea><br>
                                <span>處理狀態:</span><span id="reportStatus"></span><br>
                            </div>
                            <div class="modal-footer">
                                <span style="color: red;font-weight: bold;">確定審核通過?</span>
                                <button type="button" class="btn btn-default" data-dismiss="modal" id="accept" value="1">通過</button>
                                <button type="button" class="btn btn-default" data-dismiss="modal" id="reject" value="2">駁回</button>
                            </div>
                        </div>
                    </div>
                </div>
            </body>

            </html>
