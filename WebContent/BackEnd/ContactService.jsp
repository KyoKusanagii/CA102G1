<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*,com.member.model.*"%>
        <!doctype html>
<jsp:useBean id="memService" scope="page" class="com.member.model.MemService"/>	
        <html>
        <head>
            <meta charset="UTF-8">
            <title>聯絡客服</title>
            <!-- Latest compiled and minified CSS -->
            <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css">
            <!-- Optional theme -->
            <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.2/css/bootstrap.min.css" integrity="sha384-Smlep5jCw/wG7hdkwQ/Z5nLIefveQRIY9nfy6xoR1uRYBtpZgI6339F5dgvm/e9B" crossorigin="anonymous">
            <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
            <link rel="stylesheet" href="css/BackEnd.css">
            <!-- Latest compiled and minified JavaScript -->
            <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
            <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
			<!--             jquery的引用遇到https如果本身引用網址不是https，就改成 // -->
            <script src="//code.jquery.com/jquery-1.11.3.min.js"></script>
            <script src="js/Logout.js"></script>
            <style>
                #consulterList .list-group {
                    width: 10px;
                }

                #consulterList .list-group a {
                    padding-left: 10px;
                }

                #consulterList .list-group a:hover {
                    background-color: lightgray;
                }

                #consulterList .list-group a span {
                    color: black;
                }

                .list-group-item {
                    padding-left: 15px;
                }

      			#consultMemberIcon{
      				border-radius: 500px;
    				width: 50px;
    				height: 50px; 
    				margin-left: 5px;
      			}

                /* Chat containers */

                .message {
                    background-color: #f1f1f1;
                    border-radius: 5px;
                    padding: 10px;
                    margin: 10px 0;
                    margin-right:20px;
                   
                }

                /* Darker chat container */

                .darker {
                    border-color: #ccc;
                    background-color: #007bff;
                    color:white;
                }

                /* Clear floats */

                .message::after {
                    content: "";
                    clear: both;
                    display: table;
                }

                /* Style images */

                .message img {
                    float: left;
                    width: 60px;
                    height: 60px;
                    margin-right: 20px;
                    border-radius: 50%;
                    margin-top:15px;
                }

                /* Style the right image */

                .message img.right {
                    float: right;
                    margin-left: 20px;
                    margin-right: 0;
                    width: 60px;
                    height: 60px;
                    margin-top:15px;
                }

                /* Style time text */

                .time-right {
                    float: right;
                    color: black;
                }

                /* Style time text */

                .time-left {
                    float: left;
                    color: white;
                }

            </style>
            <script>
                $(document).ready(function() {
                    var MyPoint = "/TalkWS/${emp.emp_no}"; //只要是連到同一個MyPoint，就可以互相聊天
                    var host = window.location.host;
                    var videoSrc; //存放影片的src
                    //var host =  "10.120.26.5:8081";
                    var path = window.location.pathname;
                    var webCtx = path.substring(0, path.indexOf('/', 1));
                    //var endPointURL = "ws://" + window.location.host + webCtx + MyPoint; //這行等於localhost:8081/CA102G1
                    var endPointURL = "wss://" + window.location.host + webCtx + MyPoint; //這行等於localhost:8081/CA102G1 HTTPS專用
                    var webSocket;
                    var messageType = "text";
                    var mem_name;	//紀錄當前點擊會員的名字
                    var mem_icon;	//紀錄當前點擊會員的大頭貼
                    var indexOfConsulterList; //會員列表的會員位置索引
                    var presentConsulterNo; //用來記錄當前是哪個會員在聊天，以會員編號做依據
                    var consulterNoList = [];//存客服列表裡所有會員編號的陣列，沒有E00000
					
						
                	function InputLoadImageToBindImageElement(inputEl, imgEl) {
               		 
                	    if (inputEl.files && inputEl.files[0]) {
                	        var reader = new FileReader();
                	 
                	        reader.onload = function (e) {
                	            $(imgEl).attr('src', e.target.result);
                	        }
                	 
                	        reader.readAsDataURL(inputEl.files[0]);
                	    }
                	}
                    
                    
                    function connect() {
                        // create a websocket
                        webSocket = new WebSocket(endPointURL);

                        webSocket.onopen = function(event) {
                        	webSocket.binaryType = "arraybuffer";
                        };

                        webSocket.onmessage = function(event) {
                            var messagesArea = $(".messagesArea:eq(" + indexOfConsulterList + ")");
                            let jsonObj;
                            if (event.data instanceof ArrayBuffer){	//如果訊息類型是byte陣列
                         	   	var dataView = new DataView(event.data);
                         	    var decoder = new TextDecoder('utf8');
                         	    var response = JSON.parse(decoder.decode(dataView));
                            		console.log("收到陣列");
                            		jsonObj = response;
                            }else{		//如果訊息類型是一般字串
                         	   jsonObj = JSON.parse(event.data);
                            }
                            //var message = /*jsonObj.userName + ": " + */jsonObj.message + "\r\n";
                         	if (jsonObj.messageType== "text" ) { 
                         		//alert(consulterNoList.indexOf(jsonObj.sender))
                            	//如果發訊息的是會員
                            	$(".messagesArea:eq(" + consulterNoList.indexOf(jsonObj.sender) + ")").append(
                                    "<div class='message'>" +
                                    "<div class='text-left'>" + $("#consulterList .list-group img").filter("[title=" + jsonObj.sender + "]").next().text() + "</div>" +
                                    "<img class='chatWindowIcon left' src='" + $("#consulterList .list-group img").filter("[title=" + jsonObj.sender + "]").attr("src") + "'>" +
                                    "<p class='text-right' style='color:black;'>" + jsonObj.content + "</p>" +
                                    "<span class='time-right'>" + ((new Date().getHours()))+":"+ ((new Date().getMinutes()<10?'0':'') + new Date().getMinutes()) +"</span>" +
                                    "</div>");
                            }else if(jsonObj.messageType=="image"){	//如果訊息類型是圖片
								$(".messagesArea:eq(" + consulterNoList.indexOf(jsonObj.sender) + ")").append(	
							        "<div class='message light'>" +
							        "<div class='text-left'>" + (($("#consulterList img").filter("[title=" + jsonObj.sender + "]").next().text())) + "</div>" +
							        "<img class='chatWindowIcon left' src='" + $("#consulterList img").filter("[title=" + jsonObj.sender + "]").attr("src") + "'>" +		//btoa JS encode base64 function
							        "<span class='time-right'><img style='height:200px;width:200px;border;border-radius:0px;' src='data:image/jpg;base64," + jsonObj.content + "'>" + ((new Date().getHours()))+":"+ ((new Date().getMinutes()<10?'0':'') + new Date().getMinutes()) +"</span>" +
							        "</div>");	
									 $(".messagesArea:eq(" + consulterNoList.indexOf(jsonObj.sender) + ")").animate({scrollTop: $(".messagesArea:eq(" + consulterNoList.indexOf(jsonObj.sender) + ")").prop("scrollHeight")},0);
											//如果得到的是歷史訊息
                            }else if(jsonObj.messageType=="video"){			//如果訊息類型是影片
								$(".messagesArea:eq(" + consulterNoList.indexOf(jsonObj.sender)  + ")").append(	
									"<div class='message light'>" +
									"<div class='text-left'>" + (($("#consulterList img").filter("[title=" + jsonObj.sender + "]").next().text())) + "</div>" +
									"<img class='chatWindowIcon left' src='" + $("#consulterList img").filter("[title=" + jsonObj.sender + "]").attr("src") + "'>" +	
									"<video width='320' height='240' controls><source src='data:video/mp4;base64," + jsonObj.content + "' type='video/mp4'></video>"+//btoa JS encode base64 function
									"<span class='time-right'>" + ((new Date().getHours()))+":"+ ((new Date().getMinutes()<10?'0':'') + new Date().getMinutes()) +"</span>" +
									"</div>");	
								    $(".messagesArea:eq(" + consulterNoList.indexOf(jsonObj.sender) + ")").animate({scrollTop: $(".messagesArea:eq(" + consulterNoList.indexOf(jsonObj.sender) + ")").prop("scrollHeight")},0);
                            }else if (jsonObj.messageType== "history"){
                            	let historyMsg = JSON.parse(jsonObj.content);	//一長串的歷史訊息，把content抓出來是為gson，裡面儲存所有聊天歷史資訊，再把content轉成json
								console.log(historyMsg);	//歷史訊息的json字串	
								//先把每一筆的content抓出來
								$.each(historyMsg,function(index,aSingleHistoryMsg){ //aSingleHistoryMsg 仍然是一個JSON字串，要轉成JSON物件
									if(JSON.parse(aSingleHistoryMsg).messageType == "text"){
										if(JSON.parse(aSingleHistoryMsg).sender != "E00000"){	//如果歷史訊息的發送者不是自己	
											console.log(JSON.parse(aSingleHistoryMsg).sender);
											$(".messagesArea:eq(" + consulterNoList.indexOf(JSON.parse(aSingleHistoryMsg).sender) + ")").append(	
								              "<div class='message light'>" +
								              "<div class='text-left'>" + $("#consulterList .list-group img").filter("[title=" + JSON.parse(aSingleHistoryMsg).sender + "]").next().text() + "</div>" +
								              "<img class='chatWindowIcon left' src='" + $("#consulterList .list-group img").filter("[title=" + JSON.parse(aSingleHistoryMsg).sender + "]").attr("src") + "'>" +
								              "<p class='text-right'>" + JSON.parse(aSingleHistoryMsg).content + "</p>" +
								              "</div>");	
											 $(".messagesArea:eq(" + consulterNoList.indexOf(JSON.parse(aSingleHistoryMsg).sender) + ")").animate({scrollTop: $(".messagesArea:eq(" + consulterNoList.indexOf(JSON.parse(aSingleHistoryMsg).sender) + ")").prop("scrollHeight")},0);
										}else if(JSON.parse(aSingleHistoryMsg).sender == "E00000"){	//如果歷史訊息的發送者是自己，看接收者是誰，顯示與接收者的視窗	
											 $(".messagesArea:eq(" + consulterNoList.indexOf(JSON.parse(aSingleHistoryMsg).receiver) + ")").append(	//自己發訊息，顯示給自己
								               "<div class='message darker'>"+
								               "<div class='text-right'>" + "${emp.emp_name}" + "</div>" +
								               "<img class='chatWindowIcon right' src='data:image/jpg;base64,${Base64.getEncoder().encodeToString(emp.getEmp_icon())}'>" +
								               "<p class='text-left'>" + JSON.parse(aSingleHistoryMsg).content + "</p>" +
								               "</div>");
								               $(".messagesArea:eq(" + consulterNoList.indexOf(JSON.parse(aSingleHistoryMsg).receiver) + ")").animate({scrollTop: $(".messagesArea:eq(" + consulterNoList.indexOf(JSON.parse(aSingleHistoryMsg).receiver) + ")").prop("scrollHeight")},0);
										}
									}else if(JSON.parse(aSingleHistoryMsg).messageType == "image"){		//如果歷史訊息該訊息是文字圖片
										if(JSON.parse(aSingleHistoryMsg).sender != "E00000"){	//如果歷史訊息的發送者不是自己	
											$(".messagesArea:eq(" + consulterNoList.indexOf(JSON.parse(aSingleHistoryMsg).sender) + ")").append(	
								              "<div class='message light'>" +
								              "<div class='text-left'>" + (($("#consulterList img").filter("[title=" + JSON.parse(aSingleHistoryMsg).sender + "]").next().text())) + "</div>" +
								              "<img class='chatWindowIcon left' src='" + $("#consulterList img").filter("[title=" + JSON.parse(aSingleHistoryMsg).sender + "]").attr("src") + "'>" +
								              "<span class='time-right'><img style='height:200px;width:200px;border;border-radius:0px;; ' src='data:image/jpg;base64," +  JSON.parse(aSingleHistoryMsg).content + "'></span>" +
								              "</div>");	
											 $(".messagesArea:eq(" + consulterNoList.indexOf(JSON.parse(aSingleHistoryMsg).sender) + ")").animate({scrollTop: $(".messagesArea:eq(" + consulterNoList.indexOf(JSON.parse(aSingleHistoryMsg).sender) + ")").prop("scrollHeight")},0);
										}else if(JSON.parse(aSingleHistoryMsg).sender == "E00000"){	//如果歷史訊息的發送者是自己，看接收者是誰，顯示與接收者的視窗	
											 $(".messagesArea:eq(" +consulterNoList.indexOf(JSON.parse(aSingleHistoryMsg).receiver) + ")").append(	//自己發訊息，顯示給自己
								               "<div class='message darker'>"+
								               "<div class='text-right'>" + "${emp.emp_name}" + "</div>" +
								               "<img class='chatWindowIcon right' src='data:image/jpg;base64,${Base64.getEncoder().encodeToString(emp.getEmp_icon())}'>" +
								               "<span class='time-left'><img style='height:200px;width:200px;border;border-radius:0px;display:block;word-wrap:break-word; ' src='data:image/jpg;base64," +  JSON.parse(aSingleHistoryMsg).content + "'></span>" +
								               "</div>");
								               $(".messagesArea:eq(" + consulterNoList.indexOf(JSON.parse(aSingleHistoryMsg).receiver) + ")").animate({scrollTop: $(".messagesArea:eq(" + consulterNoList.indexOf(JSON.parse(aSingleHistoryMsg).receiver) + ")").prop("scrollHeight")},0);
										}		
									}else if(JSON.parse(aSingleHistoryMsg).messageType == "video"){		//如果歷史訊息該訊息是文字圖片
										if(JSON.parse(aSingleHistoryMsg).sender != "E00000"){	//如果歷史訊息的發送者不是自己	
											$(".messagesArea:eq(" + consulterNoList.indexOf(JSON.parse(aSingleHistoryMsg).sender) + ")").append(	
								              "<div class='message light'>" +
								              "<div class='text-left'>" + (($("#consulterList img").filter("[title=" + JSON.parse(aSingleHistoryMsg).sender + "]").next().text())) + "</div>" +
								              "<img class='chatWindowIcon left' src='" + $("#consulterList img").filter("[title=" + JSON.parse(aSingleHistoryMsg).sender + "]").attr("src") + "'>" +
								              "<video width='320' height='240' controls><source src='data:video/mp4;base64," + JSON.parse(aSingleHistoryMsg).content + "' type='video/mp4'></video>"+
								              "</div>");	
											 $(".messagesArea:eq(" + consulterNoList.indexOf(JSON.parse(aSingleHistoryMsg).sender) + ")").animate({scrollTop: $(".messagesArea:eq(" + consulterNoList.indexOf(JSON.parse(aSingleHistoryMsg).sender) + ")").prop("scrollHeight")},0);
										}else if(JSON.parse(aSingleHistoryMsg).sender == "E00000"){	//如果歷史訊息的發送者是自己，看接收者是誰，顯示與接收者的視窗	
											 $(".messagesArea:eq(" +consulterNoList.indexOf(JSON.parse(aSingleHistoryMsg).receiver) + ")").append(	//自己發訊息，顯示給自己
								               "<div class='message darker'>"+
								               "<div class='text-right'>" + "${emp.emp_name}" + "</div>" +
								               "<img class='chatWindowIcon right' src='data:image/jpg;base64,${Base64.getEncoder().encodeToString(emp.getEmp_icon())}'>" +
								               "<video width='320' height='240' controls><source src='data:video/mp4;base64," + JSON.parse(aSingleHistoryMsg).content + "' type='video/mp4'></video>"+
								               "</div>");
								               $(".messagesArea:eq(" + consulterNoList.indexOf(JSON.parse(aSingleHistoryMsg).receiver) + ")").animate({scrollTop: $(".messagesArea:eq(" + consulterNoList.indexOf(JSON.parse(aSingleHistoryMsg).receiver) + ")").prop("scrollHeight")},0);
										}
									}
                            	});	
                            }
                           	$(".messagesArea:eq(" + consulterNoList.indexOf(jsonObj.sender) + ")").animate({scrollTop: $(".messagesArea:eq(" + consulterNoList.indexOf(jsonObj.sender) + ")").prop("scrollHeight")},0);
                        };

                        webSocket.onclose = function(event) {
                            $(".messagesArea:eq(" + consulterNoList.indexOf(presentConsulterNo) + ")").append("<br><div align='center'>-----已離線-----</div>");
                        };
                    }

                    function disconnect() {

                        webSocket.close();

                    }

                    function sendMessage() {
             
                        var inputMessage = document.getElementById("messageText");
                        var content;
                        
                        if(messageType == "text"){
                        	content = inputMessage.value.trim();   	
                        }else if(messageType == "image"){
                        	var base64code = $("#hiddenImg").attr("src");
                        	base64code = base64code.substring(base64code.indexOf(",") + 1);
                    		// Convert data to base64
        					content = base64code;
                        }else if(messageType == "video"){
                    		// Convert data to base64
                    	    videoSrc = $("#hiddenVideo").attr("src");
                        	videoSrc = videoSrc.substring(videoSrc.indexOf(",") + 1);
        					content = videoSrc;
                    		
                        }
                    	console.log("訊息類型是:"+messageType); 
                        
                        if (content === "" && messageType=="text") {
                            alert("請輸入訊息");
                            inputMessage.focus();
                        }else {
                            var jsonObj = {
                                "sender": "E00000", //sender
                                "receiver":presentConsulterNo,	//要發給誰 receiver
                                "content": content,
                                "messageType":messageType	//訊息類型，判斷訊息是文字還是圖片
                            };
                            
                            if(messageType == "text"){
                            $(".messagesArea:eq(" + consulterNoList.indexOf(jsonObj.receiver) + ")").append(	//自己發訊息，顯示給自己
                              "<div class='message darker'>" +
                              "<div class='text-right'>" + "${emp.emp_name}" + "</div>" +
                              "<img class='chatWindowIcon right' src='data:image/jpg;base64,${Base64.getEncoder().encodeToString(emp.getEmp_icon())}'>" +
                              "<p class='text-left'>" + content + "</p>" +
                              "<span class='time-left'>" + ((new Date().getHours()))+":"+ ((new Date().getMinutes()<10?'0':'') + new Date().getMinutes()) + "</span>" +
                              "</div>");
                            }else if(messageType == "image"){
                              $(".messagesArea:eq(" + consulterNoList.indexOf(jsonObj.receiver) + ")").append(	//自己發訊息，顯示給自己
                              "<div class='message darker'>"+
                              "<div class='text-right'>" + "${emp.emp_name}" + "</div>" +
                              "<img class='chatWindowIcon right' src='data:image/jpg;base64,${Base64.getEncoder().encodeToString(emp.getEmp_icon())}'>" +
                              "<span class='time-left'>" + ((new Date().getHours()))+":"+ ((new Date().getMinutes()<10?'0':'') + new Date().getMinutes()) + "   <img style='float:right;margin-left:10px;height:200px;width:200px;border;border-radius:0px;' src='data:image/jpg;base64," + content + "'></span>" +
                              "</div>"); 	
                            }else if(messageType == "video"){
                              $(".messagesArea:eq(" + consulterNoList.indexOf(jsonObj.receiver) + ")").append(	//自己發訊息，顯示給自己
                              "<div class='message darker'>"+
                              "<div class='text-right'>" + "${emp.emp_name}" + "</div>" +
                              "<img class='chatWindowIcon right' src='data:image/jpg;base64,${Base64.getEncoder().encodeToString(emp.getEmp_icon())}'>" +
                              "<video width='320' height='240' controls align='center'>" +	
                              "<source src='data:video/mp4;base64," + content + "' type='video/mp4'></video>" +
                              "<span class='time-left'>" + ((new Date().getHours()))+":"+ ((new Date().getMinutes()<10?'0':'') + new Date().getMinutes()) + "</span>" +
                              "</div>"); 	
                            }
                         	$(".messagesArea:eq(" + consulterNoList.indexOf(jsonObj.receiver) + ")").animate({scrollTop: $(".messagesArea:eq(" + consulterNoList.indexOf(jsonObj.receiver) + ")").prop("scrollHeight")},0);
                            
                            webSocket.send(JSON.stringify(jsonObj));
                            inputMessage.value = "";
                            $("#hiddenImg").attr("src","<%=request.getContextPath()%>/FrontEnd/images/no_image.jpg");	//圖片預覽圖要歸回原樣
                            messageType="text";   
                            inputMessage.focus();
                            $("#file-input").val("");
                        }
                    }

                    function showAllConsulter() {	//秀出客服列表
                        let multipleMessageArea = document.getElementById("multipleMessageArea");
                    	$.ajax({
                            url: "<%=request.getContextPath()%>" + "/showAllConsulter",
                            type: "post",
                            dataType: "json",
                            success: function(jarray) {
                                $.each(jarray, function(index, json) {
                                    $("#consulterList .list-group").append("<a href='#' class='list-group-item'>" +
                                        "<img src='data:image/jpg;base64," + json.consulter_icon + "' class='list-icon'" +
                                        "title='" + json.consulter_no + "'><span>" + json.consulter_name + "</span></a>");
                                	if(index == 0){
                                		presentConsulterNo = $("#consulterList .list-group-item:eq(1)").children("img").attr("title");
                                        $("#consultMemberIcon").attr("src", "data:image/jpg;base64,"+json.consulter_icon);
                                        $("#consultMemberName").text(json.consulter_name);
                                	}
                                    //建立每個人的聊天區域並且隱藏
                                    multipleMessageArea.innerHTML += "<div style='height:580px;overflow-y:auto;display:none' class='messagesArea'></div>";
                                    consulterNoList.push(json.consulter_no);
                                  });
                            }
                        });

                    }
                    $("#sendMessage").on("click", function() {
                        sendMessage();
                        messageType="text";	//無論送出的訊息類型是什麼，最後都會變回text
                        $("input:file").val("");
                    });
                    $("#messageText").on("keydown", function(e) {
                        if (e.keyCode == 13) {
                            sendMessage();
                        }
                    })
                    $("#file-input").on("change",function(){
                    	var mimeType= document.getElementById("file-input").files[0].type.split("/");	//判斷上傳的檔案類型
                    	if(mimeType[0] == "video"){
                    		InputLoadImageToBindImageElement(this,$("#hiddenVideo"));
         	           		messageType	= "video";
         	           	    $("#hiddenImg").attr("src","<%=request.getContextPath()%>/FrontEnd/images/video.jpg");
                    	}else if(mimeType[0] == "image"){
                    		InputLoadImageToBindImageElement(this,$("#hiddenImg"));
         	           		messageType	= "image";
                    	}
                    	console.log(mimeType);
                    	console.log("訊息類型為:"+messageType);
                    	$("#messageText").focus(); //上傳完圖後自動聚焦input 才能enter送出圖片		
                    })
                    $(window).on("load", function() {	//網頁一載入時，不但列出所有的客服清單，針對每個客服清單各給一個
                        showAllConsulter();				//聊天訊息的區域，然後那些聊天區域都先隱藏
							//圖片先給預設的
                        $("#hiddenImg").attr("src","<%=request.getContextPath()%>/FrontEnd/images/no_image.jpg");
                        connect(); //載入時建立websocket
                    });
                    $(window).on("unload", function() {
                        disconnect(); //關閉網頁時斷開websocket
                    });
                    $(document).on("click", "#consulterList .list-group-item", function() { //點一下客服列表裡面的會員
                        $("#consultMemberName").text($(this).children("span").text());
                        $("#consultMemberIcon").attr("src",$(this).children("img").attr("src"));
                        indexOfConsulterList = $("#consulterList a").index(this); //取得點擊該會員時的會員列表的索引
                        //點了該會員之後，順便紀錄一下當前聊天會員對象是誰，把存在圖片標題屬性的會員編號取出來
                        presentConsulterNo = $(this).children("img").attr("title");	
                        mem_icon = $(this).children("img").attr("src");	//取出會員大頭貼
                        mem_name = $(this).children("span").text();	//取出會員姓名
                        
                        $(".messagesArea").hide();
                        $(".messagesArea:eq(" + indexOfConsulterList + ")").show();
                        //點了某個人且對話窗是空的話，表示是點他且尚未載入歷史訊息，就把歷史訊息載入到視窗
                        if($(".messagesArea:eq(" +  consulterNoList.indexOf(presentConsulterNo) + ")").text() == ""){
                        	  messageType = "history";
                        	  jsonObj = {
                                   	"sender":"E00000",
                                    "receiver":presentConsulterNo, //點選的會員編號
                                    "messageType":messageType	//訊息類型
                              };
                              webSocket.send(JSON.stringify(jsonObj));	//載完歷史訊息後，訊息類型又變成text
                              messageType = "text";
                        }

                    });

                });

            </script>
        </head>

        <body>
            <%@ include file="/BackEnd/header/header.jsp" %>
            <div class="container-fluid">
                <div class="row">
                    <div class="col-2">
                        <div class="card border-primary mb-3" style="max-width: 18rem;margin-top:30px;">
                            <div class="card-header">員工資訊</div>
                            <div class="card-body">
                                <h5 class="card-title">員工編號:${emp.emp_no}</h5>
                                <h5 class="card-title">員工姓名:${emp.emp_name}</h5>
                            </div>
                        </div>
                        <div class="list-group">
                            <span class="list-group-item list-group-item-action active">聯絡客服</span>
                            <a href="BackEnd.jsp" class="list-group-item list-group-item-action">回首頁</a>
                        </div>
                    </div>
                    <div class="col-2 mt-4">
                        <div id="consulterList">
                            <div class="list-group" style="width: 250px;">
                                <span class="list-group-item list-group-item-action active">客服列表</span>
                            </div>
                        </div>
                    </div>
                    <div class="col-6 mt-4">
                        <div class="card" style="width: 750px;height: 800px;margin-left: -50px;">
                            <div class="card-body">
                                <span class="card-title" id="consultMember"><img id="consultMemberIcon"><span id="consultMemberName" class="ml-2"></span></span><br>
                                <hr>
                                 <div id="multipleMessageArea">
								<!--                                   		這行留給客服用的 -->
                                 	<div style="height:580px;overflow-y:auto;" class="messagesArea"></div>
                                 </div>
                                <hr>
                                <div class="input-group ml-8" style="height: 50px;margin-left: 10px;margin-bottom:10px;width:650px;">
                                    <input type="text" class="form-control" style="margin-top:10px;display:inline-block;" placeholder="請輸入訊息" id="messageText" autofocus>
                                    <div class="image-upload pt-2">
                                    	<img id="hiddenImg" class="ml-3" style="height:50px;width:50px;"> 
											<!--藏影片src用 -->
                                    	<img id="hiddenVideo" class="ml-3" style="display:none;">
                                		<label for="file-input" id="labelforfile-input" class="fa fa-folder-open" aria-hidden="true" style="font-size:1.6em;margin-left:20px;"></label>
                                    	<!--     隱藏檔案上傳的鈕，以圖片取代                    -->
                                		<input type="file" id="file-input" name="uploadImage" style="display: none;"> 
                                    <span class="input-group-btn ml-2" style="margin-top:10px;">
                            			<button class="btn btn-primary mb-1" type="button" id="sendMessage" style="margin-left:20px;width: 60px;height: 38px;"><i class="fa fa-paper-plane" aria-hidden="true"></i></button>
                        			</span>
                        			</div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </body>

        </html>
