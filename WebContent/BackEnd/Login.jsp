<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <!doctype html>
    <html>

    <head>
        <meta charset="UTF-8">
        <title>InstaBuy後台人員登入頁面</title>
        <!-- Latest compiled and minified CSS -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css">
        <!-- Optional theme -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.2/css/bootstrap.min.css" integrity="sha384-Smlep5jCw/wG7hdkwQ/Z5nLIefveQRIY9nfy6xoR1uRYBtpZgI6339F5dgvm/e9B" crossorigin="anonymous">
        <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
        <!-- Latest compiled and minified JavaScript -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
        <script src="//code.jquery.com/jquery-1.11.3.min.js"></script>
        <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
        
        <script>
            $(document).ready(function() {
            	
            	var accountActive;
            	
                $("#empNo").selectmenu();
                $("#empNo").selectmenu({
                    width: 110
                });
      
                $("#accountError").hide();

                function getAllEmpNo() {
                    let i = 0;
                    $.ajax({ //先取的所有員工編號
                        url: "<%=request.getContextPath()%>/showAllEmp",
                        type: "post",
                        dataType: "json",
                        success: function(jarray) {
                            $.each(jarray, function() {
                                $("#empNo").append("<option value='" + jarray[i].emp_no + "'>" + jarray[i].emp_no + "</option>");
                                i++;
                            });
                        }
                    });
                }
                $(window).on("load", function() { //load會擋住之後所有的動作，要小心，要做的事得寫在另外的方法內
                    getAllEmpNo();
                });
             
				 $("#login").click(function(e) { //按下登入後
					 event.preventDefault(); 	//避免直接提交，先驗證
					 
					if($("#empNo option:selected").val() == "請選擇"){
						 alert("請選擇員工編號");
						 return;
					}
					 
					if($("#validationServer01").val() == "" || $("#validationServer02").val() == ""){
						 alert("帳號或密碼不可空白");
						 return;
					}else if(!accountActive){
						alert("此帳號已停用，請選擇其他帳號");
						return;
					}			
					
					$("input:hidden[name='emp_no']").val($("#empNo option:selected").val());
					
                	if($("#validationServer01").hasClass("is-valid") 
                			&& $("#validationServer02").hasClass("is-valid")) {  //若帳號和密碼都顯示正確，則登入
                		$("form").submit();           	
                	}else{
                		alert("帳號或密碼輸入錯誤");
                	}	
                });
				 
				$("#cancelLogin").click(function(){
					$("#validationServer01").val("");
					$("#validationServer02").val("");
				});

                $("#validationServer01").on("focusout", function() { //如果帳號欄不為空字串且失焦的時候，驗證員工編號對應的帳號是否正確
                    if ($("#validationServer01").val() != null) {
                        $.ajax({
                            url: "<%=request.getContextPath() %>"+"/EmpLogin",
                            type: "post",
                            dataType: "json",
                            data: {
                                emp_no: $("#empNo option:selected").val(),
                                emp_account: $("#validationServer01").val(),
                                verification:"Account"
                            },
                            success: function(obj) {
                            	if(obj.isAccountActive == "false"){
                            		accountActive = false;
                            		return;
                            	}
                            		accountActive = true;
                            	if (obj.isAccountCorrect == "false") {
                                   $("#validationServer01").addClass("is-invalid");
                                   $("#validationServer01").removeClass("is-valid");
                                }else if (obj.isAccountCorrect == "true") {
                                   $("#validationServer01").addClass("is-valid");
                                   $("#validationServer01").removeClass("is-invalid");
                                }

                            }
                        });
                    }
                });
                
                $("#validationServer02").on("focusout", function() { //如果密碼欄不為空字串且失焦的時候，驗證員工編號對應的密碼是否正確
                	
                    if ($("#validationServer02").val() != null) {
                        $.ajax({
                            url: "<%=request.getContextPath()%>/EmpLogin",
                            type: "post",
                            dataType: "json",
                            data: {
                            	emp_no: $("#empNo option:selected").val(),
                                emp_password: $("#validationServer02").val(),
                                verification:"Password"
                            },
                            success: function(obj) {
                            	if(obj.isAccountActive == "false"){
                            		accountActive = false;
                            		return;
                            	}
                            		accountActive = true;
                            	if (obj.isPassWordCorrect == "false") {
                                   $("#validationServer02").addClass("is-invalid");
                                   $("#validationServer02").removeClass("is-valid");
                                   $(".hide:eq(0)").show();
                                   $(".hide:eq(1)").hide();
                                }else if (obj.isPassWordCorrect == "true") {
                                   $("#validationServer02").addClass("is-valid");
                                   $("#validationServer02").removeClass("is-invalid");
                                   $(".hide:eq(1)").show();
                                   $(".hide:eq(0)").hide();
                                }

                            }
                        });
                    }
                });
                
                $("#validationServer01").click(function(){	//點帳號欄帳號驗證字消掉
                	 $("#validationServer01").removeClass("is-invalid");
                	 $("#validationServer01").removeClass("is-valid");
                });
                if (!$("#validationServer01").is(":focus") && !$("#validationServer02").is(":focus")){
                	$("br:last").remove();              	
                }else{
                	$("br:last").after("<br>");
                }
                $("#validationServer01").on("click",function(){
                	$(".hide").hide();
                });
                
                $("#validationServer02").click(function(){	//點密碼欄密碼驗證字消掉
               	 $("#validationServer02").removeClass("is-invalid");
               	 $("#validationServer02").removeClass("is-valid");
               	               
               });
                
            });

        </script>
        <style>
        	.ui-selectmenu-menu .ui-menu {
        		max-height: 300px;
        	}
        </style>
    </head>

    <body background="img/background/wall.jpeg" align="center" style="font-family:微軟正黑體">
        <div align="center">
            <div align="center" style="font-size: 50px;margin-top: 100px;width: 1000px;font-weight: bold;"><i>InstaBuy</i> 後台人員登入頁面</div>
        </div>
		
        <div class="container" style="margin-top: 30px;">
        <form action="<%=request.getContextPath()%>/EmpLogin" method="post">
            <div class="form-group" align="center">
                <label style="font-size: 20px;">請選擇員工編號:</label>
                <select id="empNo">
                    <option selected="selected">請選擇</option>
                </select>
            </div>
            <div class="form-group" align="center">
                <label for="validationServer01" style="font-size:20px;">帳號:</label>
                <input type="text" class="form-control" name="emp_account" id="validationServer01" style="width: 300px;display: inline;" required>
                <span class="invalid-feedback" style="font-size:20px;"><b>帳號錯誤</b></span>
                <span class="valid-feedback" style="font-size:20px;"><b>帳號正確</b></span>
            </div>
            <br>
            <br>
            <br>
            <div class="form-group" align="center">
                <label for="validationServer02" style="font-size:20px;position: absolute;top: 350px;left:787px">密碼:</label>
                <input type="password" class="form-control" name="emp_password" id="validationServer02" style="width: 300px;display: inline;position: absolute;top: 350px;left:835px;" required>
				<span class="invalid-feedback hide" style="font-size:20px;" style="position: absolute;top: 850px;left:787px;"><b>密碼錯誤</b></span>
                <span class="valid-feedback hide" style="font-size:20px;" style="position: absolute;top: 850px;left:787px;"><b>密碼正確</b></span>
            </div>
            <div style="position: absolute;top: 450px;left:835px;">
            <div align="center">
            		<input type="hidden" class="form-control" name="emp_no">
            		<input type="hidden" class="form-control" name="verification" value="Login">
                	<button type="submit" class="btn btn-dark" style="margin-left:50px;" id="login">登入</button>
                	<button type="button" class="btn btn-dark" style="margin-left:75px;" id="cancelLogin">取消</button>
            </div>
            <!--  <div align="center" style="margin-top: 20px;">
                <button type="button" class="btn btn-success">管理員快速登入</button>
                <button type="button" class="btn btn-success">一般員工快速登入</button>
            </div> -->
            
            </div>	
           </form>
        </div>
    </body>

    </html>
