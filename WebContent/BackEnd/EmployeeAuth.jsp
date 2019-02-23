<!doctype html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
        <html>

        <head>
            <meta charset="UTF-8">
            <title>員工帳號管理</title>
            <!-- Latest compiled and minified CSS -->
            <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css">
            <!-- Optional theme -->
            <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.2/css/bootstrap.min.css" integrity="sha384-Smlep5jCw/wG7hdkwQ/Z5nLIefveQRIY9nfy6xoR1uRYBtpZgI6339F5dgvm/e9B" crossorigin="anonymous">
            <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
            <link rel="stylesheet" href="<%=request.getContextPath()%>/BackEnd/css/BackEnd.css">
            <!-- Latest compiled and minified JavaScript -->
            <script src="js/active.js"></script>
            <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
            <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
            <script src="http://code.jquery.com/jquery-1.11.3.min.js"></script>
            <script src="<%=request.getContextPath()%>/BackEnd/js/Logout.js"></script>
            <style>
                .table td {}

            </style>
            <script>
                $(document).ready(function() {

                    function InputLoadImageToBindImageElement(inputEl, imgEl) { //顯示預覽圖的方法
                        if (inputEl.files && inputEl.files[0]) { //傳入改變的input和要改變的img標籤
                            var reader = new FileReader();
                            reader.onload = function(e) {
                                $(imgEl).attr('src', e.target.result);
                            }
                            reader.readAsDataURL(inputEl.files[0]);
                        }
                    }

                    function showAllEmp() { //印出所有員工的方法
                        let i = 0;
                        $.ajax({
                            url: "<%=request.getContextPath()%>" + "/showAllEmp",
                            type: "post",
                            dataType: "json",
                            success: function(objarray) {
                                $.each(objarray, function() {
                                    if (objarray[i].emp_level == "1")
                                        objarray[i].emp_level = "後台管理員";
                                    else if (objarray[i].emp_level == "2")
                                        objarray[i].emp_level = "一般員工";
                                    if(objarray[i].emp_status == "1")
                                    	objarray[i].emp_status = "啟用";
                                    else if(objarray[i].emp_status == "0")
                                    	objarray[i].emp_status = "停用";
                                    $("tbody").append("<tr><td style='vertical-align: middle;'>" + objarray[i].emp_no + "</td>" +
                                        "<td style='vertical-align: middle;'>" + objarray[i].emp_name + "</td><td style='vertical-align: middle;'>" + objarray[i].emp_id + "</td>" +
                                        "<td style='vertical-align: middle;'>" + objarray[i].emp_level + "</td>" +
                                        "<td style='vertical-align: middle;'>" + objarray[i].emp_status + "</td>" +
                                        "<td><img src='data:image/jpg;base64," + objarray[i].emp_icon + "' style='width:90px;height:80px;'></td>" +
                                        "<td style='vertical-align: middle;'><button type='button' class='btn btn-danger authViewBtn' data-toggle='modal'" +
                                        "data-target='#myModal'>修改</button></td></tr>");
                                    i++;
                                });

                            },

                        });
                    }

                    $(window).on("load", function() { //重新刷新頁面時call showAllEmp方法
                        showAllEmp();
                    });

                    //使用append的產生的元素，要以$(document)的寫法，方法才會生效
                    $(document).on("click", ".authViewBtn", function() {

                        $.ajax({
                            url: "<%=request.getContextPath()%>" + "/findEmp",
                            type: "post",
                            dataType: "json",
                            data: {
                                emp_no: $(this).parent().parent().children("td:eq(0)").text()
                            }, //傳入要修改的會員編號
                            success: function(obj) { //顯示員工的基本資料
                                $("#empNo").text(obj.emp_no);
                                $("#empName input").val(obj.emp_name);
                                $("#empId input").val(obj.emp_id);
                                $("#empIcon").val(null);
                                $("#empIconShow").attr({
                                    "width": "0px",
                                    "height": "0px"
                                }); //每次看完圖片先清空圖片的長寬
                                if (obj.emp_icon != null) //如果員工是有設定大頭貼的，給予其圖片屬性
                                    $("#empIconShow").attr({
                                        "width": "300px",
                                        "height": "300px",
                                        "src": "data:image/jpg;base64," + obj.emp_icon
                                    });
                                $("#empAuthLevel  option").filter("[value=" + obj.emp_level + "]").prop("selected", true); //屬性設定最好使用prop
                                $("input:radio[name='isMemAuth']").filter("[value=" + obj.emp_mem_auth + "]").prop("checked", true);
                                $("input:radio[name='isCarousel']").filter("[value=" + obj.emp_carousel_auth + "]").prop("checked", true);
                                $("input:radio[name='isReport']").filter("[value=" + obj.emp_report_auth + "]").prop("checked", true);
                                $("input:radio[name='isChat']").filter("[value=" + obj.emp_chat_auth + "]").prop("checked", true);
                                $("input:radio[name='empAccountStatus']").filter("[value=" + obj.emp_status + "]").prop("checked", true);
                            },
                            error: function() {
                                alert("資料載入失敗");
                            }
                        });
                    });

                    $("#empIcon").on("change", function() {
                        $(this).next().css({
                            "width": "300px",
                            "height": "300px"
                        });
                        InputLoadImageToBindImageElement(this, $("#empIconShow"));
                    });

                    $("#newEmpIcon").on("change", function() {
                        $(this).next().css({
                            "width": "300px",
                            "height": "300px"
                        });
                        InputLoadImageToBindImageElement(this, $("#newEmpIconShow"));
                    });

                    $(document).on("click", "#updateEmp", function() { // 點下確定修改後，會修改員工的資料

                        var updateEmpForm = new FormData();
                        updateEmpForm.append("emp_no", $("#empNo").text());
                        updateEmpForm.append("emp_id", $("#empId input").val());
                        updateEmpForm.append("emp_name", $("#empName input").val());
                        updateEmpForm.append("emp_level", $("#empAuthLevel option:selected").val());

                        updateEmpForm.append("emp_mem_auth", $("input:radio[name='isMemAuth']:checked").val());
                        updateEmpForm.append("emp_report_auth", $("input:radio[name='isCarousel']:checked").val());
                        updateEmpForm.append("emp_carousel_auth", $("input:radio[name='isReport']:checked").val());
                        updateEmpForm.append("emp_chat_auth", $("input:radio[name='isChat']:checked").val());
                        updateEmpForm.append("emp_status", $("input:radio[name='empAccountStatus']:checked").val());
                        let base64code = $("#empIconShow").attr("src");
                        updateEmpForm.append("emp_icon", base64code.substring(base64code.indexOf(",") + 1)); //更新員工資料      	

                        $.ajax({
                            url: "<%=request.getContextPath()%>" + "/updateEmp",
                            type: "post",
                            data: updateEmpForm,
                            enctype: "multipart/form-data",
                            dataType: "json",
                            contentType: false,
                            processData: false
                            //success:function(obj){	
                            //},
                            //error:function(){
                            // }     	                 		
                        });
                        alert("資料更新成功");
                        location.reload();
                    });


                    //新增資料時會重新載入員工列表
                    $("#send").on("click", function() {

                        var newEmpForm = new FormData();
                        newEmpForm.append("emp_id", $("#newEmpId").val());
                        newEmpForm.append("emp_name", $("#newEmpName").val());
                        newEmpForm.append("emp_pwd", $("#newEmpPwd").val());
                        newEmpForm.append("emp_level", $("#newEmpAuthLevel option:selected").val());
                        $("#newEmpIcon").val(null); //清空圖片的值
                        $("#newEmpIconShow").attr({
                            "width": "0px",
                            "height": "0px"
                        }); //每次看完圖片先清空圖片的長寬

                        newEmpForm.append("emp_mem_auth", $("input:radio[name='isMemAuthforNewEmp']:checked").val());
                        newEmpForm.append("emp_report_auth", $("input:radio[name='isCarouselforNewEmp']:checked").val());
                        newEmpForm.append("emp_carousel_auth", $("input:radio[name='isReportforNewEmp']:checked").val());
                        newEmpForm.append("emp_chat_auth", $("input:radio[name='isChatforNewEmp']:checked").val());
                        let base64code = $("#newEmpIconShow").attr("src");
                        newEmpForm.append("emp_icon", base64code.substring(base64code.indexOf(",") + 1)); //新增員工資料   

                        $.ajax({
                            url: "<%=request.getContextPath()%>" + "/addEmp",
                            enctype: "multipart/form-data",
                            data: newEmpForm,
                            type: "post",
                            dataType: "json",
                            contentType: false,
                            processData: false,
                            success: function(obj) {
                                alert("資料新增成功");
                                $("tbody").html(null);
                                location.reload(); //新增完畢後重新載入資料

                            },
                            error: function() {
                                alert("資料新增失敗");
                            }
                        });

                        //新增完員工後所有輸入的值及選項都被清除
                        $("#addEmpForm input:text").val("");
                        $("#addEmpForm input:file").val("");
                        $("#addEmpForm input:radio").attr("checked", false);
                        $('#newEmpAuthLevel option').prop("selected", function() {
                            return this.defaultSelected;
                        });

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
                    <span class="list-group-item list-group-item-action active">員工帳號管理</span>
                    <a href="#" class="list-group-item list-group-item-action" data-toggle="modal" data-target="#addEmp">新增員工</a>
                    <a href="BackEnd.jsp" class="list-group-item list-group-item-action">回首頁</a>
                </div>

            </div>

            <div class="operationArea" style="overflow-y:scroll;border: none;">
                <p><b>員工帳號列表</b></p>

                <table class="table table-striped table-bordered">
                    <thead>
                        <tr>
                            <th>員工編號</th>
                            <th>員工姓名</th>
                            <th>員工帳號</th>
                            <th>員工職等</th>
                            <th>員工帳號狀態</th>
                            <th>員工大頭照</th>
                            <th>修改員工資料</th>
                        </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
            <div id="myModal" class="modal fade" role="dialog">
                <div class="modal-dialog">
                    <!-- Modal content-->
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title">帳號詳情</h4>
                        </div>
                        <div class="modal-body">
                            <span>員工編號:</span><span id="empNo"></span><br>
                            <span>員工姓名:</span><span id="empName"><input type="text"></span><br>
                            <span>員工帳號:</span><span id="empId"><input type="text"></span><br>
                            <span>員工職等:</span><select id="empAuthLevel"><option value="1" selected="selected">後台管理員</option><option value="2">一般員工</option></select><br>
                            <span>員工大頭照:</span><span><input type="file" id="empIcon"><img id="empIconShow" style="margin-left:80px;border:solid 1px #A9A9A9;"></span><br>
                            <span>操作權限:</span>
                            <ul type="disc">
                                <li>審核會員<label class="yes">是<input name="isMemAuth" type="radio" value="1"></label><label class="no">否<input name="isMemAuth" type="radio" value="0"></label></li>
                                <li>編輯幻燈片排程<label class="yes">是<input name="isCarousel" type="radio" value="1"></label><label class="no">否<input name="isCarousel" type="radio" value="0"></label></li>
                                <li>審核檢舉<label class="yes">是<input name="isReport" type="radio" value="1"></label><label class="no">否<input name="isReport" type="radio" value="0"></label></li>
                                <li>回應客服<label class="yes">是<input name="isChat" type="radio" value="1"></label><label class="no">否<input name="isChat" type="radio" value="0"></label></li>
                            </ul>
                            <span>員工帳號狀態:<label class="yes">啟用<input name="empAccountStatus" type="radio" value="1"></label><label class="no">停用<input name="empAccountStatus" type="radio" value="0"></label></span>
                        </div>
                        <div class="modal-footer">
                            <span style="color: red;font-weight: bold;">確定修改?</span>
                            <button type="button" id="updateEmp" class="btn btn-default" data-dismiss="modal">是</button>
                            <button type="button" class="btn btn-default" data-dismiss="modal">否</button>
                        </div>
                    </div>
                </div>
            </div>
            
            <div id="addEmp" class="modal fade" role="dialog">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title">新增員工資料</h4>
                        </div>
                        <form id="addEmpForm" name="newEmpForm" method="post" enctype="multipart/form-data">
                            <div class="modal-body">
                                <span>員工姓名:<input class="newEmp" id="newEmpName" type="text"></span><br>
                                <span>員工帳號:<input class="newEmp" id="newEmpId" type="text"></span><br>
                                <span>員工密碼:<input class="newEmp" id="newEmpPwd" type="password"></span><br>
                                <span>員工職等:<select class="newEmp" id="newEmpAuthLevel"><option value="1" selected="selected">後台管理員</option><option value="2">一般員工</option></select></span><br>
                                <span>員工照片:<input class="newEmp" id="newEmpIcon" type="file"><img id="newEmpIconShow" style="margin-left:120px;"></span><br>
                                <span>操作權限:</span>
                                <span>
                        <ul type="disc">
                            <li>審核會員<label class="yes">是<input name="isMemAuthforNewEmp" type="radio" value="1"></label><label class="no">否<input name="isMemAuthforNewEmp" type="radio" value="0"></label></li>
                            <li>編輯幻燈片排程<label class="yes">是<input name="isCarouselforNewEmp" type="radio" value="1"></label><label class="no">否<input name="isCarouselforNewEmp" type="radio"  value="0"></label></li>
                            <li>審核檢舉<label class="yes">是<input name="isReportforNewEmp" type="radio" value="1"></label><label class="no">否<input name="isReportforNewEmp" type="radio"  value="0"></label></li>
                            <li>回應客服<label class="yes">是<input name="isChatforNewEmp" type="radio" value="1"></label><label class="no">否<input name="isChatforNewEmp" type="radio"  value="0"></label></li>
                        </ul>
                        </span>
                            </div>
                            <div class="modal-footer">
                                <span style="color: red;font-weight: bold;">確定新增員工?</span>
                                <button type="submit" id="send" class="btn btn-default" data-dismiss="modal">是</button>
                                <button type="button" class="btn btn-default" data-dismiss="modal">否</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>


        </body>

        </html>
