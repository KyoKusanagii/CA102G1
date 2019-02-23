<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*,com.follow_mem.model.*,com.live.model.*,com.trailer.model.*,java.text.SimpleDateFormat"%>
<!DOCTYPE html>
<%
    LiveService liveSvc = new LiveService();
	List<LiveVO> otherSellerLivedList = liveSvc.getAllLivesByASeller(((MemVO)request.getAttribute("otherSeller")).getMem_no());
    pageContext.setAttribute("otherSellerLivedList",otherSellerLivedList);
    
    //預告片段
    String otherSeller_Memno = ((MemVO)request.getAttribute("otherSeller")).getMem_no();
    TrailerService trailerSvc = new TrailerService();
    TrailerVO trailer = trailerSvc.findByPK(otherSeller_Memno);	//放入會員編號
    //System.out.println("T"+(otherSeller_Memno.substring(1,otherSeller_Memno.length())));
    //找出現在這個頁面的賣家預告資訊
    SimpleDateFormat simpleformat = new SimpleDateFormat("yyyy-MM-dd	hh:mm:ss");
    pageContext.setAttribute("trailer", trailer);
    pageContext.setAttribute("simpleformat", simpleformat);
%>
<html>
<head>
<%@ include file="/FrontEnd/headerFooter/head.jsp"%>
    <title>${otherSeller.mem_name}的賣場</title>
    <script>
        $(function(d, s, id) {			//這一段和facebook有關
            var js, fjs = d.getElementsByTagName(s)[0];
            if (d.getElementById(id)) return;
            js = d.createElement(s);
            js.id = id;
            js.src = 'https://connect.facebook.net/zh_TW/sdk.js#xfbml=1&version=v3.0&appId=221160448483712&autoLogAppEvents=1';
            fjs.parentNode.insertBefore(js, fjs);
        }(document, 'script', 'facebook-jssdk'));
    </script>
    <script>
        $(function() {
            //直接用jquery設定封面圖的css來源
           let base64MallCoverString = "${Base64.getEncoder().encodeToString(otherSeller.getMem_martcover())}";
           $("#MallCover").css("background-image","url('data:image/jpg;base64," + base64MallCoverString + "')");
           $("#sellerMartIntro a:eq(1)").click(function(){	//按下商品列表後
        	   $("#pagination").html(null);
           	   let pageCount = 0;
        	   $.ajax({
        		   url: "<%=request.getContextPath()%>/showAllItemSeller",
                   type: "post",
                   dataType: "json",
                   data:{
                	   doWhat:"findItemCount",
                	   mem_no:"${otherSeller.mem_no}"
                   },
                   success: function(jarray) {
                	   let changeRow;
                	   let changePage;
                	   let itemList = document.getElementById("itemList");
                       for(let index=0;index < jarray.count;index++) {
                    	   if(index % 6 ==0){
                    		   $("#pagination").append("<li><a href='#page"+ (pageCount+1) +"'>" + (pageCount+1) + "<span class='sr-only'></span></a></li>");
                    		   chnagePage = index;
                    	   }
                           if(index % 6 == 0 && index != changePage){
                    		   changePage = index;
                    		   pageCount++;
                    	   }
        	   			}
               		}
          		 });
        	});
           $("#sellerMartIntro a:eq(1)").on("click",function(){
        	    $("#itemList").html("");
        	 	 $.ajax({
          		      url: "<%=request.getContextPath()%>/showAllItemSeller",
         		      type: "post",
         		      dataType: "json",
         		      data:{
         		    	 	doWhat:"listItem",
         		            pageIndex:"1",
         		          	mem_no:"${otherSeller.mem_no}"
         		      },
         		     success: function(jarray) {
         		    	 $.each(jarray,function(index,json){
         		    		 $("#itemList").append("<div class='col-sm-6 col-md-4'>" +
                              "<div class='thumbnail' style='height:438px;'><img style='width:320px;height:320px;'src=' data:image/jpg;base64,"+json.item_pic+"'>"+
                              "<div class='caption'><h4 style='max-width:580px;text-overflow: ellipsis;white-space: nowrap;overflow:hidden;'>"+json.item_name+"</h4>" + 
                              "<p style='max-width:580px;text-overflow: ellipsis;white-space: nowrap;overflow:hidden;'>"+json.item_description+"</p>" +
                              "<form method='post' target='_blank' action='<%=request.getContextPath()%>/FrontEnd/query.do'>" +
                              "<input type='hidden' name='action' value='findOneItem'>"+
                              "<input type='hidden' name='itemNo' value='"+json.item_no+"'>"+
                              "<p><button class='btn btn-danger viewItemInfo' value="+json.item_no+">檢視</button></p>"+
                              "</form></div></div></div>");
                          });
         		    	}
         		    });
        	   
           });
           	$(document).on("click","#pagination a",function(){
           	 $("#itemList").html("");
           	 $.ajax({
           		      url: "<%=request.getContextPath()%>/showAllItemSeller",
          		      type: "post",
          		      dataType: "json",
          		      data:{
          		    	 	doWhat:"listItem",
          		            pageIndex:$(this).text(),
          		          	mem_no:"${otherSeller.mem_no}"
          		      },
          		     success: function(jarray) {
          		    	 $.each(jarray,function(index,json){
          		    		 $("#itemList").append("<div class='col-sm-6 col-md-4'>" +
                               "<div class='thumbnail' style='height:438px;'><img style='width:320px;height:320px;'src=' data:image/jpg;base64,"+json.item_pic+"'>"+
                               "<div class='caption'><h4 style='max-width:580px;text-overflow: ellipsis;white-space: nowrap;overflow:hidden;'>"+json.item_name+"</h4>" + 
                               "<p style='max-width:580px;text-overflow: ellipsis;white-space: nowrap;overflow:hidden;'>"+json.item_description+"</p>" +
                               "<form method='post' target='_blank' action='<%=request.getContextPath()%>/FrontEnd/query.do'>" +
                               "<input type='hidden' name='action' value='findOneItem'>"+
                               "<input type='hidden' name='itemNo' value='"+json.item_no+"'>"+
                               "<p><button class='btn btn-danger viewItemInfo' value="+json.item_no+">檢視</button></p>"+
                               "</form></div></div></div>");
                           });
          		    	}
          		    });
           	});
          
           $("#addContact").click(function(){	//按下聯絡賣家後
        	   $.ajax({
          		 url: "<%=request.getContextPath()%>/addFriend",
                   data: {
                  	 myMem_no:"${memVO.mem_no}",
                  	 otherMem_no:"${otherSeller.mem_no}"
                   },
                   type: "post",
                   dataType:"json",	//回傳類型這行一定要加
                   success:function(json){
                	   if(json.alreadyFriend == "true"){
                		 alert("已經成為好友了");
                	   }else if(json.alreadyFriend == "false"){
                		 alert("已成功新增至聊天清單");
                        	//把畫面導至聊天室	
                         location.href = "<%=request.getContextPath()%>/FrontEnd/chat/Chat.jsp";	
                	   }
                   }
          		});
           });
        });
    </script>
    <script type="text/javascript">
	 //關注賣家
	$(document).ready(function () {
	 
	      $("#addFolo_mem").on("click",function(){
	       var action = "folo_mem";
	       var folo_mem_no = "${memVO.mem_no}";
	   	   var foloed_mem_no = "${otherSeller.mem_no}";
	   	   
	    	$.ajax({
	     		url : "<%=request.getContextPath()%>/follow.do",
	     		method : "POST",
	     			data : {
	      			action:action,
	      			folo_mem_no:folo_mem_no,
	      			foloed_mem_no:foloed_mem_no 
	     		}
	    	});
	   	});
	  });
	 </script>
    
    
    <!-- //load header footer script -->
    <style>
        #sellerIcon {
            opacity: 1;
            border-radius: 500px;
            width: 60px;
            height: 60px;
            display: inline-block;
        }

        #MallCover {
            height: 250px;
            padding-top: 65px;
            margin-top: 20px;
        }


        #sellerBar {
            width: 500px;
            height: 85px;
            display: inline-block;
            background: white;
            padding-top: 8px;
        }

        /*        改寫bs4 btn的樣式*/

        .btn-primary {
            height: 40px;
            padding-top: 8px;
            padding-bottom: 8px;
            padding-left: 16px;
            padding-right: 16px;
            background-color: rgb(0, 123, 255) !important;
        }
        .btn-danger{
         	height: 40px;
            padding-top: 8px;
            padding-bottom: 8px;
            padding-left: 16px;
            padding-right: 16px;
        }


        .nav-pills>li.active>a,
        .nav-pills>li.active>a:hover,
        .nav-pills>li.active>a:focus {
            color: #fff;
            background-color: rgb(0, 123, 255) !important;
        }
        .header-search{
            width: 600px;
        }
        .tab-content > div {
        	padding-top:30px;
        }
        #sellerMartIntro li,
        .tab-pane p{
        	font-size:20px;
        }
		.tab-content p{
			color:black;
		}
    </style>
</head>
<body>
    <!-- header -->
    <div id="header">
    	<%@ include file="/FrontEnd/headerFooter/header.jsp"%>
    </div>
    <div class="container">
        <div class="sellerInfo" align="center" style="font-family:微軟正黑體;">
            <div id="MallCover">
                <div id="sellerBar" align="center">
                    <div style="display: inline-block;">
                        <div><img src="data:image/jpg;base64,${Base64.getEncoder().encodeToString(otherSeller.getMem_profilepic())}" id="sellerIcon">
                            <span id="sellerId" style="font-size: 20px;">${otherSeller.mem_id}</span>
                            <span id="sellerName" style="font-size: 20px;">${otherSeller.mem_name}</span>
                            <button type="button" class="btn btn-primary" id="addContact"><span class="glyphicon glyphicon-comment"></span> 聯絡賣家</button>
                                  <!-- 有登入才能關注賣家,並判斷是否加入過以顯示不同的按鈕 -->
                        <% 
                        	if(memVO != null){
                        	Follow_memService folo_memSvc = new Follow_memService();
                        	String folo_mem_no = memVO.getMem_no();
                        	String foloed_mem_no = request.getParameter("memNo");
                        	Follow_memVO cnt = folo_memSvc.findByPK(folo_mem_no, foloed_mem_no);
                        
                        %>
                            <span>
                            <button type="button" class="btn btn-primary aaa"  id="addFolo_mem"  style="font-weight:<%=(cnt == null) ? "1" : "2"%>"><span class="<%=(cnt == null) ? "glyphicon glyphicon-plus" : "glyphicon glyphicon-remove"%>"></span><%=(cnt == null) ? "關注賣家" : "取消關注"%></button>
                            </span>
                        	<script type="text/javascript">
	                       	
	                       	$("#addFolo_mem").click(function () {
	                            if ($(".aaa").css('font-weight') == 1) {
	                            	$(".aaa").css('font-weight', '2')
	                            	$(".aaa").html('<span class="glyphicon glyphicon-remove"></span>取消關注');
	                            	
	                                
	                            } else {
	                            	$(".aaa").css('font-weight', '1')
	                            	$(".aaa").html('<span class="glyphicon glyphicon-plus"></span>關注賣家');
	                            }
	                        });
	                       	
                       		</script>
                        <%}else{ %>
						<span><button type="button" class="btn btn-primary aaa"><span class="glyphicon glyphicon-plus bbb" id="addFolo_mem"></span>關注賣家</button>
                        </span>                        
                       
                        <%}%>
                            
                        </div>
                    </div>
                </div>
            </div>
            
            <br>
            <div class="alert alert-info" role="alert" style="font-size:40px;">賣場名稱:${otherSeller.mem_martname}</div>
            <script>console.log("${otherSeller.mem_martname}")</script>
            <hr>
            <div style="height: 700px;font-size: 20x;font-family:微軟正黑體;">
                <ul class="nav nav-pills" id="sellerMartIntro">
                    <li class="active"><a href="#home" data-toggle="pill">賣場簡介</a></li>
                    <li><a href="#item" data-toggle="pill">商品列表</a></li>
                    <li><a href="#live" data-toggle="pill">直播區</a></li>
                    <li><a href="#note" data-toggle="pill">備註</a></li>
                </ul>
                <div class="tab-content">
                    <div id="home" class="tab-pane fade in active" style="overflow-y:auto;height:600px;">
					<c:if test="${trailer != null}">
                        <p>${otherSeller.mem_martinfo}</p>
                        <div class="alert alert-danger" role="alert">
                        	<h2>直播預告</h2>
							<h3>主題:${trailer.trailer_topic}</h3>   
							<h3>時間:${simpleformat.format(trailer.trailer_time)}</h3>                
                        </div>
                          <div class="fb-page" data-href="${memVO.mem_activecode}" data-tabs="timeline" data-width="500" data-height="500" data-small-header="false" data-adapt-container-width="true" data-hide-cover="false" data-show-facepile="true"><blockquote cite="${memVO.mem_activecode}" class="fb-xfbml-parse-ignore"><a href="${memVO.mem_activecode}"></a></blockquote></div>
                    </c:if>
					</div>
                    <div id="item" class="tab-pane fade">
                    <div>
                    <nav aria-label="Page navigation">
  					<ul class="pagination" id="pagination">
 					 </ul>
					</nav>
					</div>
                    <div id="itemList"></div>
                    </div>
                    <div id="note" class="tab-pane fade" style="overflow-y:auto;">
                        <p>這是備註</p>
                    </div>
                    	<div id="live" class="tab-pane fade">
                    		<% int i = 0;%>
                    		<c:forEach var="liveVO" items="${otherSellerLivedList}">
                    		<% if(i % 3==0){%><div class="row"><%} %> 
							<div class='col-sm-6 col-md-4'>
									<h4>開始時間:${liveVO.live_start_time}</h4>
    								<h4>結束時間:<c:if test="${liveVO.live_end_time != null}">${liveVO.live_end_time}</c:if>
    										   <c:if test="${liveVO.live_end_time == null}">尚無資料</c:if></h4>
								<div class='thumbnail fb-video' style='height:260px;' data-href="${liveVO.live_address}" data-width="300" data-height="250" data-show-text="false"
    							 		style="padding-left:20px; padding-right:20px; padding-bottom:25px;">
                        	    </div>
                        	</div>
                        	<%i++; %> 
                    		<% if(i % 3==0 && i != 0){%></div><%} %>
   						</c:forEach>
                    	</div>
                </div>
            </div>
        </div>
    </div>

    <!-- footer -->
    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="<%=request.getContextPath()%>/FrontEnd/js/bootstrap.js "></script>
</body>

</html>
