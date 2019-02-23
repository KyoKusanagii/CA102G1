<%@ page contentType="text/html; charset=UTF-8" pageEncoding="big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.item.model.*"%>

<%
	MemVO memvo = (MemVO) session.getAttribute("memVO");
 	if(memvo == null){
 		session.setAttribute("location",request.getRequestURI());
 		response.sendRedirect(request.getContextPath()+"/FrontEnd/member/login.jsp");
 		return;
		
 	}

	String mem_no = memvo.getMem_no();

	
	ItemService itemService = new com.item.model.ItemService();
	List<ItemVO> list = itemService.getAll(mem_no);
	pageContext.setAttribute("list", list);


%>


<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>�����֤�</title>

<%@ include file="/FrontEnd/headerFooter/head.jsp"%>
<!-- �䷽����]�w-->

    <script type="text/javascript">
        (function(d, s, id) {
            var js, fjs = d.getElementsByTagName(s)[0];
            if (d.getElementById(id)) return;
            js = d.createElement(s);
            js.id = id;
            js.src = 'https://connect.facebook.net/zh_TW/sdk.js#xfbml=1&version=v3.0&appId=599955533703878&autoLogAppEvents=1';
            fjs.parentNode.insertBefore(js, fjs);
        }(document, 'script', 'facebook-jssdk'));
    </script>


    <style type="text/css">
        .product, .list {
            font-family: Noto Sans TC, sans-serif;
            background-color: #59f;
            color: #fff;
            height: 40px;
            font-size: 20px;
            font-weight: 500;
        }
        .static{
            font-family: Noto Sans TC, sans-serif;
            height: auto;
            color:#0087cb;
            font-size: 15px;
            font-weight: 400;
            display: inline-block;
			letter-spacing: normal;
			word-spacing: normal;
			vertical-align: top;
			text-rendering: auto;
        
        }


        .product .rftime  {
            color: yellow;
            float: right;
            font-weight: bold;
            text-align: right;
            font-size: 20px;
        }

        th {
            background: #aaa;
            text-align: center;
        }

        table {
            border: 3px;
            text-align: center;
        }

        tbody tr {
            height: 100px;
            text-align: center;
            font-family: Noto Sans TC, sans-serif;
            font-weight: 500;
        }
        
        tr:hover{
      	background-color: #CDFFFF;
      	font-weight:bold
        }


        
        .form-control {
            width:50%; 
            float:left;
        
        }
        
        .bid{
        
        	height:830px;
        	overflow-y:auto;
        
        }
        
        
        .glyphicon:hover{
        	color:red;	
        	box-shadow: 1px 1px 2px gray
        }
        

        
        
    </style>
</head>

<body>
		<!-- header -->
	<div id="header">
		<%@ include file="/FrontEnd/headerFooter/header1.jsp"%>
	</div>


<div class="container-fluid">
        <div class="row">
            <div class="col-md-6">
                <div class="product">
                    	LIVE�s��: ${nextLiveNo}
                </div>

            </div>
            <div class="col-md-6 comment">
                <div class="product">
          	          �̫��s�ɶ�:
          	        <span class='rftime'>�Y�ɰT��</span>${liveAddress}
                    <span class='final'>  </span>
                </div>
            </div>
        </div>
        <div class="row mt-1">
        
<!--******************************************** ���b���T***************************************************************************** -->
            <div class="col-md-6">
            <FORM class="form-horizontal">
                <div class="left" style="text-align:center">
                    <div class="fb-video" data-href="${liveAddr}" data-width="800" data-height="400" data-show-text="false">
                    </div>
                </div>
                    <div class="input-group">
				      <input type="text" class="form-control" id="postId" name="postId" value="${liveAddr}">
					      <span class="input-group-btn">
					        <button id="start" type="button" class="btn btn-info">Go!</button>
					      </span>
					</div>
					<div class='info col-md-8' style="margin-top: 25px;font-family: '�L�n������';font-weight: bold;box-shadow: 1px 1px 3px gray">
                            <div class="form-group">
                              <label class="control-label col-sm-2" for="item_name">�ӫ~�W��<font color="red">*</font></label>
                              <div class="col-sm-10">
                                <input type="text" class="form-control" id="item_name" placeholder="�п�J�W��" required="required">
                                <span><button type="button" class="btn btn-primary" id="insert_item">�ֳt�W�[</button></span>
                              </div>
                            </div>
                            <div class="form-group">
                              <label class="control-label col-sm-2" for="item_price">������B<font color="red">*</font></label>
                              <div class="col-sm-10"> 
                                <input type="number" class="form-control" id="item_price" placeholder="�п�J���B" required="required">
                              </div>
                            </div>
                            <div class="form-group">
                              <label class="control-label col-sm-2" for="item_no">�ӫ��ӫ~</label>
                              <div class="col-sm-10">
            					<select id="mall_item" size="1" name="item_no">
									<c:forEach var="itemVO" items="${list}">
										<c:if test="${itemVO.is_fb_launch==1}">
										<option value="${itemVO.item_no}" >${itemVO.item_name}</option>
										</c:if>
									</c:forEach>
								</select>
								</div>
							</div>
                            
                            <div class="form-group">
                            
                                <label class="control-label col-sm-2" for="remark">�ӫ~�s��</label>
                                <div class="col-sm-10" id="item_id"> 
                                 
                                </div>
                            </div>  
                             
                            <div class="form-group">
                             <label class="control-label col-sm-2" for="upfile">�ӫ~�Ϥ�<font color="red">*</font></label>
                             <div class="col-sm-10">
										<input type="file" id="upfile" name="upfile" accept="image/*" required="required" >
								<div id="pic" style="float:left"></div>

                            </div>
                            </div>
                                             
                            <div class="form-group" style="padding: 2px">
                                <label class="control-label col-sm-2"></label>
                                <div class="col-sm-10"> 
                                   <span class="btn-group my-2 btn-group-md" style="float:left">
                				  		<button type="button" class="btn btn-danger">�v�ж}�l <i class="fa fa-play"></i></button>
					                  <button type="button" class="btn btn-success">�v�е��� <i class="fa fa-pause"></i></button>
					              </span>
                                </div>
                            </div> 
					</div>
					</FORM>
					<div class='info col-md-4 static'>
						<div>
							�s�u���A:<span id="connect" style="color:#E86850;font-size: 18px;font-weight: bold"></span>
						</div>
						<div style="margin-top: 20px">
							�`�d���H��:<span id="fb_headCount" style="color:#E86850;font-size: 18px;font-weight: bold"></span>
						</div>
						<div style="margin-top: 20px">
							�`�d����:<span id="fb_comCount" style="color:#E86850;font-size: 18px;font-weight: bold"></span>
						</div>
						<div style="margin-top: 20px">
							<button id="racing" class="btn" type="button" style="margin-bottom:3px; background-color: #9068be;color: white">�v�мҦ�<i class="glyphicon glyphicon-flash"></i></button>
						</div>
						
						<div style="margin-top: 20px" id="high" style="display:none">
							�̰��X����:<span id="winner" style="color:#E86850;font-size: 18px;font-weight: bold"></span>
							
						</div>
					</div>
            </div>
            
<!--******************************************** //���b���T***************************************************************************** -->

            <div class="col-md-6 message">
                <nav class="navbar-light bg-light">
                        <div class="input-group">
                           <input type="text" id = 'sendMessage' class="form-control" placeholder="�e�X�d��">
                            <span class="input-group-btn">
                            <button class="btn btn-success my-2 my-sm-0" type="submit" style="border-radius: 5px">Post</button>
                            <button class="btn btn-warning my-2 my-sm-0 refresh" type="submit">Refresh<i class='glyphicon glyphicon-refresh'></i></button>
                        </span>
                       </div>
                </nav>
                <div class="bid my-2">
                    <table>
                        <thead>
                            <tr>
                            	<th style="width:250px">FB�d���ɶ�</th>
                                <th style="width:250px">FB�W��</th>
                                <th style="width:500px">�d�����e</th>
                                <th style="width:100px">�o��</th>
                            </tr>
                        </thead>
                        <tbody id="fb_buyer" style="visibility: hidden">
                            <tr>
                            	<td>2018-08-23 05:54:58</td>
                                <td><img src="http://graph.facebook.com/271693200056719/picture?type=small"><br>�j�î��A</td>
                                <td>
                                    <h3>100+1</h3>
                                </td>
                                <td><button class="btn btn-outline-primary">�o��</button></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        
        
<!--   *********************************************�U�b�C**************************************************************       -->

          <div class="row mt-2">
                <div class="list" >
                    	�����o�вM��<button id="kill" style="background-color: red;height:100%">�M���M��</button>
                    	<span style="float:right;"><button id = "checkout" style="background-color: #350608;height:100%">
                    	<i class="glyphicon glyphicon-shopping-cart"></i>
                    	�e�X�����q��</button></span>
                </div>
                <div class="col-md-12" style="align:center">
                
                <table style="width:100%;text-align: center">
                        <thead>
                            <tr>
                            	<th style="width:250px;background-color: #cff09e">FB�d���ɶ�</th>
                                <th style="width:250px;background-color: #cff09e">FB�W��</th>
                                <th style="width:500px;background-color: #cff09e">�ӫ~�W��</th>
                                <th style="width:500px;background-color: #cff09e">�o�л���</th>
                                <th style="width:250px;background-color: #cff09e">�d�����e</th>
                            </tr>
                        </thead>
                        <tbody id="fb_list" >
                            <tr>
                            	<td>2018-08-23 05:54:58</td>
                                <td><img src="http://graph.facebook.com/271693200056719/picture?type=small"><br>�j�î��A</td>
                                <td>�s���F��</td>
                                <td>500</td>
                                <td>
                                    <h3>500+1</h3>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
          
          
          
          </div>
        
        
    </div>
	

<!-- ===================================websocket=========================================================== -->

	<script type="text/javascript">
		var str_value = ""; //�ѼƤ������k�䪺��  
		var max = 0;

		window.onload = function() {
			var url = window.location.toString(); //���o��e���}   
			var str = ""; //�ѼƤ��������䪺��   
			if (url.indexOf("?") != -1) {
				//�p�G���}��"?"�Ÿ�   
				var ary = url.split("?")[1].split("&");
				//���o"?"�k����}��Q��"&"���Φr��s�Jary�}�C ["a=1","b=2","c=3"]   
				for ( var i in ary) {
					//���o�}�C���ץh�]�j��A�p:���}���T�ӰѼơA�h�|�]�T��   
					str = ary[i].split("=")[0];
					//���o�Ѽ�"="���䪺�Ȧs�Jstr�ܼƤ�   
					if (str == "#access_token") {
						//�Ystr����Q�n����Ѽ� �p:b   
						str_value = decodeURI(ary[i].split("=")[1]);
						//���ob�����k�䪺�Ȩøg�L������X��s�Jstr_value   
					}
				}
			}
		}
		
	    var MyPoint = "/echo";
	    var host = window.location.host;
	    var path = window.location.pathname;
	    var webCtx = path.substring(0, path.indexOf('/', 1));
	    var endPointURL = "wss://" + window.location.host + webCtx + MyPoint;
		var webSocket = new WebSocket(endPointURL);//ServerEndpoint��ť��URL.

		webSocket.onerror = function(event) {
			onError(event)
		};

		webSocket.onopen = function(event) {
			onOpen(event)
		};

		webSocket.onmessage = function(event) {
			onMessage(event)
		};
		
		
//  ������server�T����Ĳ�o.
		function onMessage(event) {
			var jsonObj = JSON.parse(event.data);
			var fb_headCount = parseInt(jsonObj.fb_headCount);
			var fb_comCount = parseInt(jsonObj.fb_comCount);
			$("#fb_headCount").text("  " + fb_headCount);
			$("#fb_comCount").text("  " + fb_comCount);

			$("#fb_buyer").append("<tr>"+
					"<td class='1'>" + jsonObj.fb_order_time + "</td>" +
					"<td class='2'>" + jsonObj.fb_buyer + "</td>" +
					"<td class='3'>" + jsonObj.fb_comment + "</td>"+
					"<td class='4' style='display:none'>" + jsonObj.fb_id + "</td>"+
					"<td class='5'><button class=' got btn btn-primary'>�o��</button></td>"+
					"</tr>");
			
			
			$(".bid").scrollTop(99999999);
			
			var bid_price = jsonObj.fb_comment.substr(0,jsonObj.fb_comment.indexOf('+'))
			if(!isNaN(bid_price) && bid_price > parseInt(max)){
					$("#winner").text(jsonObj.fb_buyer+' : '+ bid_price);
					max = bid_price;
		 			$("#winner").append("<span>"+
		 					"<span id='winner_order_time' style='display:none'>" + jsonObj.fb_order_time + "</span>" +
		 					"<span id='winner_buyer' style='display:none' >" + jsonObj.fb_buyer + "</span>" +
		 					"<span id='winner_comment' style='display:none' >" +jsonObj.fb_comment + "</span>"+
		 					"<span id='winner_id' style='display:none' >" + jsonObj.fb_id + "</span><br>"+
		 					"<span id='winner_price' style='display:none'>" + bid_price + "</span><br>"+
		 					"<button type='button' id='sendWinner' style='color:black'>�e�X</button>"+
		 					"</span>");
				}
			else {
				bid_price = 0;
			}
				
		}
		//  �إ߻Pserver���s��.
		function onOpen(event) {
 			$('#connect').text('Connection established');
		}
		//  �s�u���~��Ĳ�o
		function onError(event) {
			alert(event.data);
		}
		//  ���U���s��Ĳ�o,�o�e�T����server
		$(document).on("click","#start",function(event) {
 			$("#fb_buyer").empty();
			$("#fb_buyer").css("visibility","visible");
 			$("#winner").empty();
			$("#winner").css("visibility","visible");
			webSocket.send(str_value + "?" + document.getElementById('postId').value);
			console.log(document.getElementById('postId').value);
			console.log(str_value + "?" + document.getElementById('postId').value);
				
			$("#start").prop("disabled","disabled");
			$('#postId').prop("disabled","disabled");
			
			
			return false;
		});
		
// 		function winner(){
// 			if(document.getElementById('winner').value.is){
// 				webSocket.send(document.getElementById('winner').value);
// 				console.log(document.getElementById('winner').value);
// 			}
// 			else
// 				alert("input can not be empty!");
			
// 		}
	// toggle
	$(document).ready(function(){
		 $("#high").hide();
	  $("#racing").click(function(){
	 	 $("#high").slideToggle();
	  });
	});

//  =====================================ajax========================================================= 

	//�M�ŲM��
	$(document).on("click","#kill",function(event){
		var action = "kill";
	
		$.ajax({
       	url : "<%=request.getContextPath()%>/FrontEnd/FbLive.do",
       	method : "POST",
      	data : {
      		action:action
   		},
   	    	
	 	success: function (data) {
		  alert("�M��M�Ŧ��\");
		  $("#fb_list").empty();
		},
		
		error: function (data) {
		  alert("�M��M�ť���");
		}
				
		});
	});
	
	//�ӫ~�W�ٴO�J
		$(document).on("change","#mall_item",function(event){
			var txt=$("#mall_item :selected").text();
			$("#item_name").val(txt);
			$("#item_id").html($("#mall_item").val());
			
		});

 	//�ӫ~�ֳt�W�[
 		$(document).on("click","#insert_item",function(event){
 			var item_name = $("#item_name").val();
 			console.log(item_name)
 			var item_price = $("#item_price").val();
 			console.log(item_price);
 			var action = "insert_item";
 			var item_owner = "${memVO.mem_no}";
 			console.log(item_owner);
 			
 		
 			$.ajax({
		       	url : "<%=request.getContextPath()%>/FrontEnd/FbLive.do",
		       	method : "POST",
		      	data : {
		      	action:action,
		      	item_name:item_name,
		      	item_price:item_price,
		      	item_owner:item_owner
		      	
	       },
		       	
		  success: function (data) {
			 var newData = data.replace(/\r\n/g,'')
			  $("#item_id").text(newData);
			  alert("�ֳt�W�[���\");
		  },

           error: function (data) {
               alert("�ֳt�W�[����");
           }
 			
 		});
 	});
 	
 		//Ĺ�a�[�J�ʪ���
 		$(document).on("click","#sendWinner",function(event){
 			if($("#item_price").val() == null ||$("#item_price").val() =="")
 				$("#item_price").val(parseInt($(this).siblings("#winner_price").text()));
 			
 			if( $("#item_price").val()>+10000000)
 				alert("�����L���ݽT�{!!")
 				
 			else{		
 			
	 			var fb_order_time = $(this).siblings("#winner_order_time").text();
	 			var fb_buyer = $(this).siblings("#winner_buyer").text();
	 			var fb_comment = $(this).siblings("#winner_comment").text();
	 			var fb_id = $(this).siblings("#winner_id").text();
 	 			var fb_item_price =  $("#item_price").val();
	 			var fb_item_no = $("#item_id").html();
	     		var fb_item_name = $("#item_name").val();
	     		var fb_item_pic =$("#upfile").val();
	     		var action = "fb_register";
	     		let base64code = $("#pic img").attr("src");
	     		var fb_pic = base64code.substring(base64code.indexOf(",") + 1);
	     		
// 	     		$("#item_price").val(fb_item_price);
	     		
	 			$("#fb_list").prepend("<tr>"+
	 					"<td class='fb_order_time'><span style='float:left'><i class='glyphicon glyphicon-remove-sign btn delete' title='�����o��'></i></span><sapn class='fb_order_time'>" + fb_order_time + "</span></td>" +
	 					"<td class='fb_buyer'>" + fb_buyer + "</td>" +
	 					"<td class='fb_item'>" +  fb_item_name + "</td>" +
	 					"<td class='fb_price'>" +  fb_item_price + "</td>" +
	 					"<td class='fb_comment'>" + fb_comment + "</td>"+
	 					"<td class='fb_id' style='display:none'>" + fb_id + "</td>"+
	 					"<td class='fb_item_no' style='display:none'>" + fb_item_no + "</td>"+
	 					"</tr>");
	 			
	 				
	 	    	$.ajax({
	 		       	url : "<%=request.getContextPath()%>/FrontEnd/member/fb.do",
	 		       	method : "POST",
	 		      	data :{
	 		      		fb_order_time:fb_order_time,
	 	 		      	fb_buyer:fb_buyer,
	 	 		      	fb_comment:fb_comment,
	 	 		      	fb_item_no:fb_item_no,
	 	 		      	fb_id:fb_id,
	 	 		      	fb_item_price:fb_item_price,
	 	 		      	action:action,
	 	 		      	fb_pic:	fb_pic
	 		      	},
	 		      	 success: function (data) {
	 			      		//$(this).attr("disabled","disabled");
	 			      		
	 			      	 }
	 		      		      	
	 	       
	 	      	 });
 	    	
 			}
 		});
 	
 	
 			
 		//�[�J�ʪ���
 	   $(document).on("click",".got", function (event) {
 		   // let formdata = new FormData();
 		  
 			var fb_order_time = $(this).parent().siblings("td.1").text();
 			var fb_buyer = $(this).parent().siblings("td.2").text();
 			var fb_comment = $(this).parent().siblings("td.3").text();
 			var fb_id = $(this).parent().siblings("td.4").text();
     		var fb_item_no = $("#item_id").html();
     		console.log("fb_item_no"+fb_item_no)
     		var fb_item_name = $("#item_name").val();
     		var fb_item_price =  $("#item_price").val();
     		var fb_item_pic =$("#upfile").val();
     		var action = "fb_register";
     		let base64code = $("#pic img").attr("src");
     		var fb_pic = base64code.substring(base64code.indexOf(",") + 1);
     		
//      		formdata.append("fb_order_time",fb_order_time);
//      		formdata.append("fb_buyer",fb_buyer);
//      		formdata.append("fb_comment",fb_comment);
//      		formdata.append("fb_item_name",fb_item_name);
//      		formdata.append("fb_id",fb_id);
//      		formdata.append("fb_item_price",fb_item_price);
//      		formdata.append("action",action);
     		
     		//let base64code = $("#pic img").attr("src");
     		//formdata.append("fb_pic",base64code.substring(base64code.indexOf(",") + 1));
     		
 			$("#fb_list").prepend("<tr>"+
 					"<td class='fb_order_time'><span style='float:left'><i class='glyphicon glyphicon-remove-sign btn delete' title='�����o��'></i></span><sapn class='fb_order_time'>" + fb_order_time + "</span></td>" +
 					"<td class='fb_buyer'>" + fb_buyer + "</td>" +
 					"<td class='fb_item'>" +  fb_item_name + "</td>" +
 					"<td class='fb_price'>" +  fb_item_price + "</td>" +
 					"<td class='fb_comment'>" + fb_comment + "</td>"+
 					"<td class='fb_id' style='display:none'>" + fb_id + "</td>"+
 					"<td class='fb_item_no' style='display:none'>" + fb_item_no + "</td>"+
 					"</tr>");
     		
     		
     		
     		$(this).parent().parent().css("background-color","#f3e8d6");
     		$(this).attr("disabled","disabled");	
 	    	$.ajax({
 		       	url : "<%=request.getContextPath()%>/FrontEnd/member/fb.do",
 		       	method : "POST",
 		      	data :{
 		      		fb_order_time:fb_order_time,
 	 		      	fb_buyer:fb_buyer,
 	 		      	fb_comment:fb_comment,
 	 		      	fb_item_no:fb_item_no,
 	 		      	fb_id:fb_id,
 	 		      	fb_item_price:fb_item_price,
 	 		      	action:action,
 	 		      	fb_pic:	fb_pic
 		      	},
 		      	 success: function (data) {
 			      		$(this).attr("disabled","disabled");
 			      	 }
 		      		      	
 	       
 	       });
 	    	
 	    });			



//�ʪ����e�X�q��
		$(document).on("click","#checkout",function(event){
				var action = "insertOrder";
			$.ajax({
		       	url : "<%=request.getContextPath()%>/FrontEnd/FbLive.do",
		       	method : "POST",
		      	data : {
		      	action:action
	       },
		       	
		  success: function (data) {
			   alert("FB�q��s�W���\");
		  },

           error: function (data) {
               alert("FB�q��s�W����");
               
           }
	       });

			$("#fb_list").empty();
		});
		

	//refresh
		$(document).on("click",".refresh", function (event) {
			$("#fb_buyer").css("visibility","hidden");
			$("#fb_buyer").empty();
			$("#start").removeAttr("disabled");
			var date = new Date().Format("yyyy-MM-dd hh:mm:ss");
			console.log(date);
			$(".final").text(new Date().Format("yyyy-MM-dd hh:mm:ss"));
			max = 0;
			$("#winner").css("visibility","hidden");
	
	
	
		});


	    
	    
	    
	 //�q�ʪ����R��
	 $(document).on("click",".delete", function (event) {
			
			var fb_buyer_d = $(this).parent().parent().siblings(".fb_buyer").text();
			console.log("gg"+fb_buyer_d);
			var fb_comment_d = $(this).parent().parent().siblings("td.fb_comment").text();
			console.log(fb_comment_d);
			var fb_id_d = $(this).parent().parent().siblings("td.fb_id").text();
			console.log(fb_id_d);
			var fb_item_name_d = $(this).parent().parent().siblings("td.fb_item").text();
			console.log(fb_item_name_d);
			var fb_item_price_d =  $(this).parent().parent().siblings("td.fb_price").text();
			console.log(fb_item_price_d);
			var action = "DELETE";
			var fb_item_no =  $(this).parent().parent().siblings("td.fb_item_no").text();
		
	 
	    $.ajax({
	     url : "<%=request.getContextPath()%>/FrontEnd/FbLive.do",
	     method : "POST",
	     data : {
	      action:action,
	      fb_buyer:fb_buyer_d,
	      fb_comment:fb_comment_d,
	      fb_id:fb_id_d,
	      fb_item_name:fb_item_name_d,
	      fb_item_price:fb_item_price_d,
	      fb_item_no:fb_item_no
	      
	     },
	     success: function(response) {
		       
	         alert("�w���\�R�������o��");
	  
	     },
	     error: function(xhr) {
	         alert('Ajax request �o�Ϳ��~');
	       }
	    });
 	    $(this).parent().parent().parent().css("display","none");
	   });
	    	

	    	
	</script>	

<script type="text/javascript">
		$("#upfile").change(
				function() {
					$(".rollback").show();
					$("#pic").empty();
					if (this.files && this.files[0]) {
						for (var i = 0; i < this.files.length; i++) {
							var reader = new FileReader();
							reader.onload = function(e) {
								var img = $("<img width='90' height='90'>")
										.attr("src", e.target.result);
								$("#pic").append(img);
							}
							reader.readAsDataURL(this.files[i]);
						}
					}
				});

		$(".rollback").click(function() {
			$(".rollback").hide();
			$("#upfile").val(null); //��W���ɮ�
			$("#pic").empty(); //��w����
		});
	</script>
<script type="text/javascript">
Date.prototype.Format = function (fmt) {  
    var o = {
        "M+": this.getMonth() + 1,  
        "d+": this.getDate(),  
        "h+": this.getHours(), 
        "m+": this.getMinutes(), 
        "s+": this.getSeconds(), 
        "q+": Math.floor((this.getMonth() + 3) / 3),
        "S": this.getMilliseconds() 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}


</script>
	<div id="footer">
		<%@ include file="/FrontEnd/headerFooter/footer.html"%>
	</div>
	<script src="<%=request.getContextPath()%>/FrontEnd/js/bootstrap.js "></script>
</body>

</html>