<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*,com.member.model.*"%>
<jsp:useBean id="memService" scope="page" class="com.member.model.MemService"/>
<!DOCTYPE html>
<html>
<head>
	
	<%@ include file="/FrontEnd/headerFooter/head.jsp"%>
    <title>${memVO.mem_name}的聊天室</title>
    <!-- load header footer script -->
    <!-- //load header footer script -->
    <style>
        .list-group-item {
            border-top-right-radius: 0px !important;
            border-top-left-radius: 0px !important;
            border-left: none;
            border-right: none;
            font-size: 18px;
        }

        /*        改寫bs4 btn的樣式*/
        .media-list{
            font-size: 18px;
        }
        .btn-primary {
            height: 40px;
            padding-top: 8px;
            padding-bottom: 8px;
            padding-left: 16px;
            padding-right: 16px;
            background-color: rgb(0, 123, 255) !important;
        }

        .panel-heading {
            background-color: rgb(0, 123, 255) !important;
            font-size: 18px;
        }

        .panel {
            background-color: #fff;
            border: 1px solid #ccc;
        }

        .header-search {
            width: 600px;
        }
         /* Chat containers */

                .message {
                    background-color: #f1f1f1;
                    border-radius: 5px;
                    padding: 10px;
                    margin: 10px 0;
                    margin-right:15px;
                    font-size:18px;
                   
                }
                .message p{
                	margin-bottom:20px;
                
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
                    margin-top:30px;
                }

                /* Style time text */

                .time-left {
                    float: left;
                    color: white;
                    margin-top:30px;
                }
                .list-icon,#friendIcon{
   				 	border-radius: 500px;
    			 	width: 50px;
    			 	height: 50px; 
    		     	margin-left: 5px;
				}

    </style>
    <script>
    	var messageType;
        $(document).ready(function(){
        	function InputLoadImageToBindImageElement(inputEl, imgEl) {
        		 
        	    if (inputEl.files && inputEl.files[0]) {
        	        var reader = new FileReader();
        	 
        	        reader.onload = function (e) {
        	            $(imgEl).attr('src', e.target.result);
        	        }
        	 
        	        reader.readAsDataURL(inputEl.files[0]);
        	    }
        	}
        	
            $("#sendMessage").click(function(){
                if($("input:file").val() != ""){
                    $(".media-list").append("<li><div class='alert alert-info' role='alert'>"+"上傳了一份檔案"+"</div></li>");
                }
  				sendMessage();
                //messageType="text";	//無論送出的訊息類型是什麼，最後都會變回text
                $("input:file").val("");
            });
            
            $(".list-group-item").click(function(){
                 $(".media-list").append("<li>" + $(this).text() + "</li>");
                
            });
            
                      
            var MyPoint = "/TalkWS/${memVO.mem_no}";//只要是連到同一個MyPoint，就可以互相聊天，以會員編號作依據
            //var host = "10.120.26.5:8081";
            //var host =  192.168.1.1;
            var host = window.location.host;
            var path = window.location.pathname;
            var videoSrc; //存放影片的src
            var webCtx = path.substring(0, path.indexOf('/', 1));
            //var endPointURL = "ws://" + window.location.host + webCtx + MyPoint; //這行等於localhost:8081/CA102G1
            var endPointURL = "wss://" + window.location.host + webCtx + MyPoint; //這行等於localhost:8081/CA102G1		HTTPS專用
            var webSocket;
            var indexOfFriendList; //會員列表的會員位置索引
            var presentFriendNo; //用來記錄當前是哪個會員在聊天，以會員編號做依據
            var friendNoList = []; //存好友列表裡所有對象編號的陣列
            
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
            });
            function connect() {
                // create a websocket
                webSocket = new WebSocket(endPointURL);

                webSocket.onopen = function(event) {
                    console.log(endPointURL);
                    //$(".messagesArea:eq(0)").append("<div align='center'>-----已連線-----</div><br>");
                    webSocket.binaryType = "arraybuffer";
                };

                webSocket.onmessage = function(event) {
                	
                	console.log(event.data);
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
                       
                	var messagesArea = $(".messagesArea");

							if(jsonObj.messageType=="text"){	//如果訊息類型是文字
								$(".messagesArea:eq(" + friendNoList.indexOf(jsonObj.sender) + ")").append(	
			                      "<div class='message light'>" +
			                      "<div class='text-left'>" + (($("#friendList img").filter("[title=" + jsonObj.sender + "]").next().text())) + "</div>" +
			                      "<img class='chatWindowIcon left' src='" + $("#friendList img").filter("[title=" + jsonObj.sender + "]").attr("src") + "'>" +
			                      "<h4 class='text-right'>" + jsonObj.content + "</h4>" +
			                      "<span class='time-right'>" + ((new Date().getHours()))+":"+ ((new Date().getMinutes()<10?'0':'') + new Date().getMinutes()) +"</span>" +
			                      "</div>");	
								 $(".messagesArea:eq(" + friendNoList.indexOf(jsonObj.sender) + ")").animate({scrollTop: $(".messagesArea:eq(" + friendNoList.indexOf(jsonObj.sender) + ")").prop("scrollHeight")},0);
							}else if(jsonObj.messageType=="image"){	//如果訊息類型是圖片
								$(".messagesArea:eq(" + friendNoList.indexOf(jsonObj.sender) + ")").append(	
					              "<div class='message light'>" +
					              "<div class='text-left'>" + (($("#friendList img").filter("[title=" + jsonObj.sender + "]").next().text())) + "</div>" +
					              "<img class='chatWindowIcon left' src='" + $("#friendList img").filter("[title=" + jsonObj.sender + "]").attr("src") + "'>" +		//btoa JS encode base64 function
					              "<span class='time-right'><img style='height:200px;width:200px;border;border-radius:0px;' src='data:image/jpg;base64," + jsonObj.content + "'>" + ((new Date().getHours()))+":"+ ((new Date().getMinutes()<10?'0':'') + new Date().getMinutes()) +"</span>" +
					              "</div>");	
							      $(".messagesArea:eq(" + friendNoList.indexOf(jsonObj.sender) + ")").animate({scrollTop: $(".messagesArea:eq(" + friendNoList.indexOf(jsonObj.sender) + ")").prop("scrollHeight")},0);
									
							}else if(jsonObj.messageType=="video"){			//如果訊息類型是影片
								$(".messagesArea:eq(" + friendNoList.indexOf(jsonObj.sender) + ")").append(	
							      "<div class='message light'>" +
							      "<div class='text-left'>" + (($("#friendList img").filter("[title=" + jsonObj.sender + "]").next().text())) + "</div>" +
							      "<img class='chatWindowIcon left' src='" + $("#friendList img").filter("[title=" + jsonObj.sender + "]").attr("src") + "'>" +	
							      "<video width='320' height='240' controls><source src='data:video/mp4;base64," + jsonObj.content + "' type='video/mp4'></video>"+//btoa JS encode base64 function
							      "<span class='time-right'>" + ((new Date().getHours()))+":"+ ((new Date().getMinutes()<10?'0':'') + new Date().getMinutes()) +"</span>" +
							      "</div>");	
								  $(".messagesArea:eq(" + friendNoList.indexOf(jsonObj.sender) + ")").animate({scrollTop: $(".messagesArea:eq(" + friendNoList.indexOf(jsonObj.sender) + ")").prop("scrollHeight")},0);
							}else if (jsonObj.messageType=="history"){
								//如果得到的是歷史訊息
								let historyMsg = JSON.parse(jsonObj.content);	//一長串的歷史訊息，把content抓出來是為gson，裡面儲存所有聊天歷史資訊，再把content轉成json
								
								console.log(historyMsg);	//歷史訊息的json字串	
								//先把每一筆的content抓出來
								$.each(historyMsg,function(index,aSingleHistoryMsg){ //aSingleHistoryMsg 仍然是一個JSON字串，要轉成JSON物件
									if(JSON.parse(aSingleHistoryMsg).messageType == "text"){	//如果歷史訊息該訊息是文字
										if(JSON.parse(aSingleHistoryMsg).sender != "${memVO.mem_no}"){	//如果歷史訊息的發送者不是自己	
											console.log(JSON.parse(aSingleHistoryMsg).sender);
											$(".messagesArea:eq(" + friendNoList.indexOf(JSON.parse(aSingleHistoryMsg).sender) + ")").append(	
								              "<div class='message light'>" +
								              "<div class='text-left'>" + (($("#friendList img").filter("[title=" + JSON.parse(aSingleHistoryMsg).sender + "]").next().text())) + "</div>" +
								              "<img class='chatWindowIcon left' src='" + $("#friendList img").filter("[title=" + JSON.parse(aSingleHistoryMsg).sender + "]").attr("src") + "'>" +
								              "<h4 class='text-right'>" + JSON.parse(aSingleHistoryMsg).content + "</h4>" +
								              "</div>");	
											 $(".messagesArea:eq(" + friendNoList.indexOf(JSON.parse(aSingleHistoryMsg).sender) + ")").animate({scrollTop: $(".messagesArea:eq(" + friendNoList.indexOf(JSON.parse(aSingleHistoryMsg).sender) + ")").prop("scrollHeight")},0);
										}else if(JSON.parse(aSingleHistoryMsg).sender == "${memVO.mem_no}"){	//如果歷史訊息的發送者是自己，看接收者是誰，顯示與接收者的視窗	
											 $(".messagesArea:eq(" +friendNoList.indexOf(JSON.parse(aSingleHistoryMsg).receiver) + ")").append(	//自己發訊息，顯示給自己
								               "<div class='message darker'>"+
								               "<div class='text-right'>" + "${memVO.mem_name}" + "</div>" +
								               "<img class='chatWindowIcon right' src='data:image/jpg;base64,${memVO.profilepicEncoded}'>" +
								               "<h4 class='text-left'>" + JSON.parse(aSingleHistoryMsg).content + "</h4>" +
								               "</div>");
								               $(".messagesArea:eq(" + friendNoList.indexOf(JSON.parse(aSingleHistoryMsg).receiver) + ")").animate({scrollTop: $(".messagesArea:eq(" + friendNoList.indexOf(JSON.parse(aSingleHistoryMsg).receiver) + ")").prop("scrollHeight")},0);
										}				
									}else if(JSON.parse(aSingleHistoryMsg).messageType == "image"){		//如果歷史訊息該訊息是文字圖片
										if(JSON.parse(aSingleHistoryMsg).sender != "${memVO.mem_no}"){	//如果歷史訊息的發送者不是自己	
											$(".messagesArea:eq(" + friendNoList.indexOf(JSON.parse(aSingleHistoryMsg).sender) + ")").append(	
								              "<div class='message light'>" +
								              "<div class='text-left'>" + (($("#friendList img").filter("[title=" + JSON.parse(aSingleHistoryMsg).sender + "]").next().text())) + "</div>" +
								              "<img class='chatWindowIcon left' src='" + $("#friendList img").filter("[title=" + JSON.parse(aSingleHistoryMsg).sender + "]").attr("src") + "'>" +
								              "<span class='time-right'><img style='height:200px;width:200px;border;border-radius:0px;; ' src='data:image/jpg;base64," +  JSON.parse(aSingleHistoryMsg).content + "'></span>" +
								              "</div>");	
											 $(".messagesArea:eq(" + friendNoList.indexOf(JSON.parse(aSingleHistoryMsg).sender) + ")").animate({scrollTop: $(".messagesArea:eq(" + friendNoList.indexOf(JSON.parse(aSingleHistoryMsg).sender) + ")").prop("scrollHeight")},0);
										}else if(JSON.parse(aSingleHistoryMsg).sender == "${memVO.mem_no}"){	//如果歷史訊息的發送者是自己，看接收者是誰，顯示與接收者的視窗	
											 $(".messagesArea:eq(" +friendNoList.indexOf(JSON.parse(aSingleHistoryMsg).receiver) + ")").append(	//自己發訊息，顯示給自己
								               "<div class='message darker'>"+
								               "<div class='text-right'>" + "${memVO.mem_name}" + "</div>" +
								               "<img class='chatWindowIcon right' src='data:image/jpg;base64,${memVO.profilepicEncoded}'>" +
								               "<span class='time-left'><img style='height:200px;width:200px;border;border-radius:0px;display:block;word-wrap:break-word; ' src='data:image/jpg;base64," +  JSON.parse(aSingleHistoryMsg).content + "'></span>" +
								               "</div>");
								               $(".messagesArea:eq(" + friendNoList.indexOf(JSON.parse(aSingleHistoryMsg).receiver) + ")").animate({scrollTop: $(".messagesArea:eq(" + friendNoList.indexOf(JSON.parse(aSingleHistoryMsg).receiver) + ")").prop("scrollHeight")},0);
										}		
									}else if(JSON.parse(aSingleHistoryMsg).messageType == "video"){		//如果歷史訊息該訊息是文字圖片
										if(JSON.parse(aSingleHistoryMsg).sender != "${memVO.mem_no}"){	//如果歷史訊息的發送者不是自己	
											$(".messagesArea:eq(" + friendNoList.indexOf(JSON.parse(aSingleHistoryMsg).sender) + ")").append(	
								              "<div class='message light'>" +
								              "<div class='text-left'>" + (($("#friendList img").filter("[title=" + JSON.parse(aSingleHistoryMsg).sender + "]").next().text())) + "</div>" +
								              "<img class='chatWindowIcon left' src='" + $("#friendList img").filter("[title=" + JSON.parse(aSingleHistoryMsg).sender + "]").attr("src") + "'>" +
								              "<video width='320' height='240' controls><source src='data:video/mp4;base64," + JSON.parse(aSingleHistoryMsg).content + "' type='video/mp4'></video>"+
								              "</div>");	
											 $(".messagesArea:eq(" + friendNoList.indexOf(JSON.parse(aSingleHistoryMsg).sender) + ")").animate({scrollTop: $(".messagesArea:eq(" + friendNoList.indexOf(JSON.parse(aSingleHistoryMsg).sender) + ")").prop("scrollHeight")},0);
										}else if(JSON.parse(aSingleHistoryMsg).sender == "${memVO.mem_no}"){	//如果歷史訊息的發送者是自己，看接收者是誰，顯示與接收者的視窗	
											 $(".messagesArea:eq(" +friendNoList.indexOf(JSON.parse(aSingleHistoryMsg).receiver) + ")").append(	//自己發訊息，顯示給自己
								               "<div class='message darker'>"+
								               "<div class='text-right'>" + "${memVO.mem_name}" + "</div>" +
								               "<img class='chatWindowIcon right' src='data:image/jpg;base64,${memVO.profilepicEncoded}'>" +
								               "<video width='320' height='240' controls><source src='data:video/mp4;base64," + JSON.parse(aSingleHistoryMsg).content + "' type='video/mp4'></video>"+
								               "</div>");
								               $(".messagesArea:eq(" + friendNoList.indexOf(JSON.parse(aSingleHistoryMsg).receiver) + ")").animate({scrollTop: $(".messagesArea:eq(" + friendNoList.indexOf(JSON.parse(aSingleHistoryMsg).receiver) + ")").prop("scrollHeight")},0);
										}
									}
								});
 							
							}

            	}
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
					content = base64code;
                }else if(messageType == "video"){
                	videoSrc = $("#hiddenVideo").attr("src");
                  	videoSrc = videoSrc.substring(videoSrc.indexOf(",") + 1);
  					content = videoSrc;
                }
            	console.log("訊息類型是:"+messageType);
                if (content === "" && messageType=="text") {
                    alert("請輸入訊息");
                    inputMessage.focus();
                } else {
                    var jsonObj = {
                    	"sender":"${memVO.mem_no}",
                        "receiver":presentFriendNo, //點選的會員編號
                        "content": content,	//訊息內容
                        "messageType":messageType	//訊息類型
                    };
                    if(messageType == "text"){
                    	 $(".messagesArea:eq(" +friendNoList.indexOf(jsonObj.receiver) + ")").append(	//自己發訊息，顯示給自己
                         "<div class='message darker'>"+
                         "<div class='text-right'>" + "${memVO.mem_name}" + "</div>" +
                         "<img class='chatWindowIcon right' src='data:image/jpg;base64,${memVO.profilepicEncoded}'>" +
                         "<h4 class='text-left'>" + content + "</h4>" +
                         "<span class='time-left'>" + ((new Date().getHours()))+":"+ ((new Date().getMinutes()<10?'0':'') + new Date().getMinutes()) + "</span>" +
                         "</div>"); 	
                    }else if(messageType == "image"){
                    	 $(".messagesArea:eq(" +friendNoList.indexOf(jsonObj.receiver) + ")").append(	//自己發訊息，顯示給自己
                         "<div class='message darker'>"+
                         "<div class='text-right'>" + "${memVO.mem_name}" + "</div>" +
                         "<img class='chatWindowIcon right' src='data:image/jpg;base64,${memVO.profilepicEncoded}'>" +
                         "<span class='time-left'>" + ((new Date().getHours()))+":"+ ((new Date().getMinutes()<10?'0':'') + new Date().getMinutes()) + "   <img style='float:right;margin-left:10px;height:200px;width:200px;border;border-radius:0px;' src='data:image/jpg;base64," + content + "'></span>" +
                         "</div>"); 	
                    }else if(messageType == "video"){
                   	 $(".messagesArea:eq(" +friendNoList.indexOf(jsonObj.receiver) + ")").append(	//自己發訊息，顯示給自己
                         "<div class='message darker'>"+
                         "<div class='text-right'>" + "${memVO.mem_name}" + "</div>" +
                         "<img class='chatWindowIcon right' src='data:image/jpg;base64,${memVO.profilepicEncoded}'>" +
                         "<video width='320' height='240' controls>" +	
                         "<source src='data:video/mp4;base64," + content + "' type='video/mp4'></video>" +
                         "<span class='time-left'>" + ((new Date().getHours()))+":"+ ((new Date().getMinutes()<10?'0':'') + new Date().getMinutes())+"</span>" +
                         "</div>"); 
                    }
                   
                    $(".messagesArea:eq(" + friendNoList.indexOf(jsonObj.receiver) + ")").animate({scrollTop: $(".messagesArea:eq(" + friendNoList.indexOf(jsonObj.receiver) + ")").prop("scrollHeight")},0);
                    
                    webSocket.send(JSON.stringify(jsonObj));
                    inputMessage.value = "";
                    $("#hiddenImg").attr("src","<%=request.getContextPath()%>/FrontEnd/images/no_image.jpg");	//圖片預覽圖要歸回原樣
                    messageType="text";          
                    inputMessage.focus();
                    $("#file-input").val("");
                }
            }

            function showAllFriends() {
            	let multipleMessageArea = document.getElementById("multipleMessageArea");
                $.ajax({
                    url: "<%=request.getContextPath()%>/showAllFriends",
                    type: "post",
                    dataType: "json",
                    data:{
                    	chat_master_no:"${memVO.mem_no}" //找出當前登入會員所有的朋友
                    },
                    success: function(jarray) {
                        $.each(jarray, function(index, json) {
                        	if(index == 0){	//第0個的時候，印客服並給id official
                        		$("#friendList").append("<a href='#' class='list-group-item' id='official'>" +
                                "<img src='" + "<%=request.getContextPath()%>/FrontEnd/images/Service.png" + "' class='list-icon'" +
                                "title='" + json.friend_no + "' style='margin-left:0px;'><span style='margin-left:40px;'>" + json.friend_name + "</span></a>");
                        	}else if(index > 0){
                        	     $("#friendList").append("<a href='#' class='list-group-item'>" +
                                 "<img src='data:image/jpg;base64," + json.friend_icon + "' class='list-icon'" +
                                 "title='" + json.friend_no + "' style='margin-left:0px;'><span style='margin-left:40px;'>" + json.friend_name + "</span></a>"); 		
                        	}
                            //建立每個人的聊天區域並且隱藏
                            multipleMessageArea.innerHTML += "<div style='height:500px;overflow-y:auto;display:" +(index == 0 ? "" : "none") + "' class='messagesArea'></div>";
                            friendNoList.push(json.friend_no);
                        });
                    }
                });

            }
            $("#messageText").on("keydown",function(e){
           	 if(e.keyCode == 13){
           		 sendMessage();
           	 	}           	 
            })
            
//             $("#labelforfile-input").click(function(){
//             	messageType	= "image";	//只要一上傳圖片，訊息類型就變成image
            
//             });
            $(window).on("load", function() { //載入的時候預設跟客服人員聊天，名字、頭貼都顯示客服的
            	showAllFriends();
            	indexOfFriendList = 0;
            	presentFriendNo = 'E00000';
            	messageType = "text";	//載入時預設訊息類型為text
            	$("#friendIcon").attr("src","<%=request.getContextPath()%>/FrontEnd/images/Service.png");
            	$("#hiddenImg").attr("src","<%=request.getContextPath()%>/FrontEnd/images/no_image.jpg");
            	$("#friendName").text("客服人員");
                connect(); //載入時建立websocket
            });
            $(window).on("unload", function() {
                disconnect(); //關閉網頁時斷開websocket
            });
            $(document).on("click", "#friendList .list-group-item", function() {
                $("#friendName").text($(this).children("span").text());
                if($(this).children("img").attr("src") != ""){ //如果圖片不為空值表示為會員
                	$("#friendIcon").attr("src",$(this).children("img").attr("src"));  	
                }
                $("#friendIcon").css("visibility","visible");
                indexOfFriendList = $("#friendList a").index(this); //取得點擊該會員時的會員列表的索引
                
                presentFriendNo = $(this).children("img").attr("title");	//取得點擊那個人的會員編號
                
               
                //點了該會員之後，順便紀錄一下當前聊天會員對象是誰，把存在圖片標題屬性的會員編號取出來
                $(".messagesArea").hide();
                $(".messagesArea:eq(" + indexOfFriendList + ")").show();
                
                //點了某個人且對話窗是空的話，表示是點他且尚未載入歷史訊息，就把歷史訊息載入到視窗
                if($(".messagesArea:eq(" + friendNoList.indexOf(presentFriendNo) + ")").text() == ""){
                	  messageType = "history";
                	  jsonObj = {
                            "sender":"${memVO.mem_no}",
                            "receiver":presentFriendNo, //點選的會員編號
                            "messageType":messageType	//訊息類型
                      };
                      webSocket.send(JSON.stringify(jsonObj));	//載完歷史訊息後，訊息類型又變成text
                      messageType = "text";
                }
            });
            
            $(document).on("click","#official",function(){	//點客服人員後，動態新增客服列表到後端以及資料庫
            	$.ajax({
            		 url: "<%=request.getContextPath()%>/addConsulter",
                     data: {
                    	 cosulterNO:"${memVO.mem_no}",
                    	 consulterName:"${memVO.mem_name}",
                    	 //將大頭貼解碼成base64傳到controller
                    	 consulterIcon:"${memVO.profilepicEncoded}"
                     },
                     type: "post"
            	});
            	$("#friendIcon").attr("src","<%=request.getContextPath()%>/FrontEnd/images/Service.png");
            	 presentFriendNo = "E00000";
            });
//             $("#file-input").click(function(){
//             	messageType = "image";
//             });
       });
            
    </script>
</head>

<body>
    <!-- header -->
    <div id="header">
    	<%@ include file="/FrontEnd/headerFooter/header.jsp"%>
    </div>
    <div class="container">
        <hr>
        <div style="height: 600px;font-size: 20x;font-family:微軟正黑體;" align="center"> 
            <h3 class="text-center">個人聊天室</h3>
            <br/>
            <!--            聊天列表-->
            <div class="col-3 col-md-3" style="display: inline;overflow-y: auto;">
                <div class="panel panel-info">
                    <div class="panel-heading" style="color: white;box-shadow: 1px 1px 3px gray">聊天列表</div>
                    <div class="panel-body fixed-panel" style="height:705px;padding: 0px;box-shadow: 1px 1px 3px gray">
                        <div class="list-group" id="friendList">
                        </div>
                    </div>
                </div>
            </div>
            <!--                        聊天視窗-->
            <div class="col-9 col-md-9" style="display: inline;">
                <div class="panel panel-info" style="box-shadow: 1px 1px 3px gray">
                    <div class="panel-heading" style="color: white;"> 聊天紀錄</div>
                    <div id="friend" align="left" style="padding-top:10px;padding-left:10px;"><img id="friendIcon"><span id="friendName" class="ml-2" style="font-size:18px;margin-left:20px;"></span></div>
                    <hr>
                    <div class="panel-body fixed-panel" style="height: 400px;font-size:18px;" id="multipleMessageArea">
                    </div>
                    <div class="panel-footer" style="margin-bottom:0px;padding-top:120px;background-color:white;border-top:none;">
                        <hr>
                        <div class="input-group">
                            <input type="text" class="form-control" placeholder="請輸入訊息" id="messageText" style="width: 650px;" autofocus>
                            <div class="image-upload">
                            	<img id="hiddenImg" style="height:50px;width:50px;"> 
                            	<!--藏影片src用 -->
                                <img id="hiddenVideo" class="ml-3" style="display:none;">
                                <label for="file-input" id="labelforfile-input" class="glyphicon glyphicon-paperclip" style="margin-left:10px;margin-top: 7px;"></label>
                                    <!--     隱藏檔案上傳的鈕，以迴紋針取代                    -->
                                <input type="file" id="file-input" name="uploadImage" style="display: none;"> 
                            </div>
                            <span class="input-group-btn">                             
                                    <button class="btn btn-primary" type="button" id="sendMessage" style="height: 34px;padding-top: 3px;"><span class="fa fa-paper-plane" style="color:white"></span></button>
                            </span>
                        </div>
                    </div>
                </div>
            </div>

        </div>


    </div>

    <!-- footer -->
    <div class="hover_shadow" id="footer">
    	<%@ include file="/FrontEnd/headerFooter/footer.html"%>
    </div>
    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="<%=request.getContextPath()%>/FrontEnd/js/bootstrap.js"></script>
</body>

</html>
