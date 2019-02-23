<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*,com.article.model.*, com.arti_reply.model.*,com.member.model.MemVO,com.artireply_report.model.*"%>
<jsp:useBean id="MemService" scope="page" class="com.member.model.MemService" /> 
<jsp:useBean id="artireplyReportService" scope="page" class="com.artireply_report.model.ArtiReplyReportService" />
  <%
    MemVO memVO = (MemVO)session.getAttribute("memVO");
    String arti_no = request.getParameter("arti_no");	//從get過來的編號，沒有controller
	boolean hide_dropdown = false;	//隱藏所有可操作的按鈕
	boolean hide_reply_dropdown = false;	//隱藏留言操作的按鈕
	boolean article_deletable = true;
	//boolean reply_deletable = true;
	if(memVO == null){
		 hide_dropdown = true;
		 hide_reply_dropdown = true;
	}
	//pageContext.setAttribute("hide_reply_dropdown", hide_reply_dropdown);
	ArticleService articleSvc = new ArticleService();
	ArticleVO articleVO = articleSvc.getOneArticle(arti_no);
	pageContext.setAttribute("articleVO", articleVO);
	
	Arti_replyService arti_replySvc = new Arti_replyService();
	List<Arti_replyVO> list = arti_replySvc.getRepByArtiNO(arti_no);	
	pageContext.setAttribute("list", list);
	
	
	//存放所有未處理檢舉的留言編號
	Set<Integer> artireply_no_of_report = new HashSet<>();
	//存放所有已審核檢舉的留言編號
	Set<Integer> artireply_no_of_report_done = new HashSet<>();
	//得到所有被檢舉的文章留言
	List<ArtiReplyReportVO> artiReplyeportList = (List<ArtiReplyReportVO>)artireplyReportService.getAllArtiReply();
	
	for(int i=0;i<artiReplyeportList.size();i++){
		if(artiReplyeportList.get(i).getReport_Status() == 0)//如果檢舉留言的狀態為未處理，加到集合裡
			artireply_no_of_report.add(artiReplyeportList.get(i).getRep_NO());
		else if(artiReplyeportList.get(i).getReport_Status() == 1)
			artireply_no_of_report_done.add(artiReplyeportList.get(i).getRep_NO());
	}
	
	pageContext.setAttribute("artireply_no_of_report", artireply_no_of_report);	//讓EL能找到
	pageContext.setAttribute("artireply_no_of_report_done",artireply_no_of_report_done);
	System.out.println("這篇文章編號為:"+arti_no);
	System.out.println("這篇文章的檢舉編號為:"+((Set<String>)session.getAttribute("read_only_articles")));
	if(((Set<String>)session.getAttribute("read_only_articles")).contains(arti_no)){
		 article_deletable = false;
	}
	System.out.println("這篇文章的檢舉的留言編號為:"+artireply_no_of_report);
%>

<!DOCTYPE html>
<html>

<head>
    <title>InstaBuy</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=big5" />
    <meta name="keywords" content="" />
    <script type="application/x-javascript">
        addEventListener("load", function() {
            setTimeout(hideURLbar, 0);
        }, false);

        function hideURLbar() {
            window.scrollTo(0, 1);
        }

    </script>
    <!-- Custom Theme files -->
    <link href="<%=request.getContextPath()%>/FrontEnd/css/bootstrap.css" rel="stylesheet" type="text/css" media="all" />
    <link href="<%=request.getContextPath()%>/FrontEnd/css/style.css" rel="stylesheet" type="text/css" media="all" />
    <link href="<%=request.getContextPath()%>/FrontEnd/css/menu.css" rel="stylesheet" type="text/css" media="all" />
    <!-- menu style -->
    <link href="<%=request.getContextPath()%>/FrontEnd/css/ken-burns.css" rel="stylesheet" type="text/css" media="all" />
    <!-- banner slider -->
    <link href="<%=request.getContextPath()%>/FrontEnd/css/animate.min.css" rel="stylesheet" type="text/css" media="all" />
    <link href="<%=request.getContextPath()%>/FrontEnd/css/owl.carousel.css" rel="stylesheet" type="text/css" media="all">
    <!-- carousel slider -->
    <!-- //Custom Theme files -->
    <!-- font-awesome icons -->
    <link href="<%=request.getContextPath()%>/FrontEnd/css/font-awesome.css" rel="stylesheet">
    <!-- //font-awesome icons -->
    <!-- js -->
    <script src="<%=request.getContextPath()%>/FrontEnd/js/jquery-2.2.3.min.js"></script>
    <!-- //js -->
    <!-- web-fonts -->
    <link href='https://fonts.googleapis.com/css?family=Roboto+Condensed:400,300,300italic,400italic,700,700italic' rel='stylesheet' type='text/css'>
    <link href='https://fonts.googleapis.com/css?family=Lovers+Quarrel' rel='stylesheet' type='text/css'>
    <link href='https://fonts.googleapis.com/css?family=Offside' rel='stylesheet' type='text/css'>
    <link href='https://fonts.googleapis.com/css?family=Tangerine:400,700' rel='stylesheet' type='text/css'>
    <!-- web-fonts -->
    <script src="<%=request.getContextPath()%>/FrontEnd/js/owl.carousel.js"></script>
    <script>
        $(document).ready(function() {
            $("#owl-demo").owlCarousel({
                autoPlay: 3000, //Set AutoPlay to 3 seconds 
                items: 4,
                itemsDesktop: [640, 5],
                itemsDesktopSmall: [480, 2],
                navigation: true

            });
        });

    </script>
    <script src="<%=request.getContextPath()%>/FrontEnd/js/jquery-scrolltofixed-min.js" type="text/javascript"></script>
    <script>
        $(document).ready(function() {

            // Dock the header to the top of the window when scrolled past the banner. This is the default behaviour.

            $('.header-two').scrollToFixed();
            // previous summary up the page.

            var summaries = $('.summary');
            summaries.each(function(i) {
                var summary = $(summaries[i]);
                var next = summaries[i + 1];

                summary.scrollToFixed({
                    marginTop: $('.header-two').outerHeight(true) + 10,
                    zIndex: 999
                });
            });
        });

    </script>
    <!-- start-smooth-scrolling -->
    <script type="text/javascript" src="<%=request.getContextPath()%>/FrontEnd/js/move-top.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/FrontEnd/js/easing.js"></script>
    <script type="text/javascript">
        jQuery(document).ready(function($) {
            $(".scroll").click(function(event) {
                event.preventDefault();
                $('html,body').animate({
                    scrollTop: $(this.hash).offset().top
                }, 1000);
            });
        });

    </script>
    <!-- //end-smooth-scrolling -->
    <!-- smooth-scrolling-of-move-up -->
    <script type="text/javascript">
        $(document).ready(function() {

            var defaults = {
                containerID: 'toTop', // fading element id
                containerHoverID: 'toTopHover', // fading element hover id
                scrollSpeed: 1200,
                easingType: 'linear'
            };

            $().UItoTop({
                easingType: 'easeOutQuart'
            });

        });

    </script>
    <!-- //smooth-scrolling-of-move-up -->
    <script src="<%=request.getContextPath()%>/FrontEnd/js/bootstrap.js"></script>



    <style type="text/css">
        .text {
            word-wrap: break-word;
            word-break: break-all;

        }
		
		#articleContent p{
			color:black;
		}	
      

        hr {
            border-width: 3px;
            width: 100%
        }

    </style>
    <script>
    	var artireply_no_of_report = [];	 //已檢舉的留言編號陣列
        var reply_deletable = true;
        var rep_no;
        $(document).ready(function() {
        	
          var report_reply_no;	 //檢舉的留言編號
          $("#reportOpinion").focus(function() {
                 $(this).attr("placeholder", "");
          });
          $("#reportOpinion").blur(function() {
                 $(this).attr("placeholder", "請輸入其他意見");
          });		
            
          $("#articleReportBtn").click(function(){  //按下檢舉文章後
              $("#artiReplyOfReport").parent().hide();
              $("#articleTopicOfReport").text("${articleVO.arti_topic}");
              $("#sendforumReport").removeClass("sendArtiReplyReport");     //跳窗的送出按鈕變成是檢舉文章的，不是檢舉留言的
              $("#sendforumReport").addClass("sendArticleReport");
              
          }); 
            
         $(".artiReplyReportBtn").click(function(){  //按下檢舉留言後
              $("#artiReplyOfReport").parent().show();
              $("#articleTopicOfReport").text("${articleVO.arti_topic}");
              report_reply_no = $(this).parent().prev().val();
              $("#artiReplyOfReport").text($(this).attr("value"));
              $("#sendforumReport").addClass("sendArtiReplyReport");     //跳窗的送出按鈕變成是檢舉留言的，不是檢舉文章的
              $("#sendforumReport").removeClass("sendArticleReport");
            
         });
            
            //按下送出檢舉後
          $("#sendforumReport").click(function(){
              let reasons = document.getElementsByName("reportReason");	//取的所有的checkbox
              let Reasons="";	//把所有的檢舉原因變成一個字串
              for(let i=0;i<reasons.length;i++){		//如果checkbox被勾取
              if(reasons[i].checked){
                    Reasons+=$("#reason"+(i+1)).text();	//將原因加入至字串                                       
                    Reasons+=",";                        
                    }
              }
              if(Reasons.charAt(Reasons.length-1)==","){  //如果原因的最後一個字是逗號
                    Reasons = Reasons.slice(0,Reasons.length-1); //原因的字串擷取逗號至前一個字元
              }
              
              if($(this).hasClass("sendArticleReport")){
                                                        
                    $.ajax({
                 
                        url: "<%=request.getContextPath()%>/addArticleReport",
                        type: "post",      
                        data:{
                        	articleReportDescription:$("#reportOpinion").val(),	//textarea打的字一樣要用val()非text()
                        	articleNo:"${articleVO.arti_no}",
                        	articleReporter:"${memVO.mem_no}",
                        	articleReasons:Reasons,
                        	articleTopic:"${articleVO.arti_topic}"	//取出被檢舉的文章標題
                        },
                    success: function() {
                        alert("檢舉成功");
                        location.reload();
                    }                                  
                 
                  });
                  
            	}else if($(this).hasClass("sendArtiReplyReport")){
                    $.ajax({
                        url: "<%=request.getContextPath()%>/addArtiReplyReport",
                        type: "post",      
                        data:{
                        	artiReplyReportDescription:$("#reportOpinion").val(),	//textarea打的字一樣要用val()非text()
                        	artiReplyNo:report_reply_no,
                        	artiReplyReporter:"${memVO.mem_no}",
                        	articleNo:"${articleVO.arti_no}",
                        	artiReplyReasons:Reasons
                        },
                    success: function() {
                        alert("檢舉成功");
                        location.reload();
                    }                                  
                  });
                }else{
            		$("input:checkbox[name='ReportReason']").attr("checked",false);
            		$("#reportOpinion").val("");        		
            	}                      
          });
        });

    </script>
	

</head>

<body>
	<c:forEach var="artireply_no_of_reportVO" items="${artireply_no_of_report}">
           <script>
            	artireply_no_of_report.push("${artireply_no_of_reportVO}");
             </script>
    </c:forEach>
    <!-- header -->
    <div class="header">
		<jsp:include page="/FrontEnd/headerFooter/header1.jsp"/>
    </div>
    <!-- //header-->
   

    <div class="container col">
        <div class="header-logo col-md-12">
            <h1><a href="<%=request.getContextPath()%>/FrontEnd/index.jsp"><span>Insta</span>Buy <i style="font-family: 微軟正黑體;font-size: 14px">in直拍</i></a></h1>
        </div>
        <div class="col-md-2"></div>
        <div class="col-md-10 ">

            <form action="article.do" method="post">

                <div class="col-12 col-md-10">
                    <div class="input-group">
                        <input type="text" class="form-control" name="arti_topic" placeholder="搜尋文章 ..." style="height:46px" />
                        <div class="input-group-btn">
                            <button class="btn btn-danger btn-lg" type="submit">
				            <span class="glyphicon glyphicon-search"></span>
				          </button>
                            <input type="hidden" name="action" value="findByTopic">
                        </div>
                    </div>
                </div>
            </form>
        </div>

    </div>
    <%! boolean editAndDeleteReply;%>
    <%! boolean editAndDeleteArticle;%>
    <%! String login_mem_no;%> 
    <%! String article_mem_no;%> 
    <%! String artiReply_mem_no;%>             	
      
    <div class="container" style="font-family:微軟正黑體; font-weight:bold">

        <div class="row" style="margin-top:5%">
            <div style="">
                <a href="forum.jsp"><button class="btn btn-default">返回</button></a>
            </div>
        </div>


        <div class="row">
            <div class="col-2 col-md-2" style="margin-top:50px">
                <img src="data:image/*;base64,${MemService.findByPK(articleVO.mem_no).profilepicEncoded}" class="img-responsive img-circle">
                <div class="text-center" style="margin-top:20px;font-size:20px;">
                   ${MemService.findByPK(articleVO.mem_no).mem_name}
                </div>
            </div>
            <div class="col-10 col-md-10">
                <div class="col">
                    <div>
                        <div class="col-md-9">
                            <h1 class="text" style="padding-top:50px" id="articleTopic">${articleVO.arti_topic} <% if(!article_deletable){%><span style="font-size:25px;color:red">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;此文章檢舉審核中</span><%} %></h1>
                        </div>
                        <div class="text-right col-md-3" style="margin-top:77.5px; padding-right:0px">
                            發表於
                            <fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${articleVO.po_time}" />
                            <!--文章的編號-->
                            <% if(!hide_dropdown){%>
                            <div class="btn-group" style="margin-left:10px">
                                <button type="button" class="btn btn-xs btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
							    <i class="glyphicon glyphicon-option-horizontal"></i>
							  </button>
                                	<%					//可以編輯或刪除嗎?可以表示為本人
                                	  
                                	if(memVO != null){	
                                	  login_mem_no = ((MemVO)session.getAttribute("memVO")).getMem_no(); //先得到正在登入的員工邊號
                                	  article_mem_no = articleSvc.getOneArticle(arti_no).getMem_no();	//得到寫這個文章的會員編號
                                	  editAndDeleteArticle = (login_mem_no.equals(article_mem_no));	//是本人嗎?
                                	 
                                	}   
                                	 %> 
	                                <ul class="dropdown-menu edit">
                                    <li style="display:<%=editAndDeleteArticle ? "" :  "none" %>"><a href="update_arti.jsp?arti_no=${articleVO.arti_no}">編輯文章</a></li>
                                    <li style="display:<%=editAndDeleteArticle ? "" :  "none" %>"><a href="#" data-toggle="modal" data-target="#deleteArticle">刪除</a></li>
                                    <!--    文章檢舉編號藏這裡                                -->
<!--                                     可以編輯或刪除文章嗎?可以表示為本人，是本人就不檢舉自己的文章 -->
                                    <li style="display:<%=(!editAndDeleteArticle) ? "" :  "none" %>" value="${articleVO.arti_no}" id="articleReportBtn"><a href="#" data-toggle="modal" data-target="#forumReport">檢舉</a></li>
                                    <!-- 文章檢舉 -->
                                </ul>
                                
                            </div>
                            <%}%>
                        </div>
                    </div>

                </div>
                <hr>
                <div class="col">
                    <span class="text" id="articleContent" style="font-size:20px;">${articleVO.arti_content}</span>
                </div>
            </div>
        </div>

        <!-- 刪除文章跳窗 -->
        <div class="modal fade" id="deleteArticle" role="dialog">
            <div class="modal-dialog modal-sm">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">刪除文章</h4>
                    </div>
                    <div class="modal-body">
                    	<% if(article_deletable) {%>
                        	<p style="font-size:15px">刪除後將無法復原，確定要刪除？</p>
                        <%}else{ %>
                        	<p style="font-size:15px">此文章檢舉審核中，無法刪除</p>
                        <%} %>
                    </div>
                    <div class="modal-footer">
                        <form method="post" action="article.do">
                        	<% if(article_deletable) {%>
	                            <input type="submit" class="btn btn-danger" value="刪除">
	                            <input type="hidden" name="arti_no" value="${articleVO.arti_no}">
	                            <input type="hidden" name="action" value="delete">
                            <%} %>
                            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                        </form>

                    </div>
                </div>
            </div>
        </div>
        <!--    刪除文章跳窗-->
        <!--    檢舉文章跳窗-->
        <div id="forumReport" class="modal fade" role="dialog" style="font-family: 微軟正黑體;font-size:20px;">
            <div class="modal-dialog">
                <!-- Modal content-->
                <div class="modal-content">
                    <div class="modal-header">
                        <h4 class="modal-title">輸入檢舉資訊</h4>
                    </div>
                    <div class="modal-body">
                        檢舉的文章主題:<span id="articleTopicOfReport"></span><br>        
                        <span>檢舉的留言:<span id="artiReplyOfReport"></span><br></span>            
                        <span>請選擇檢舉原因:</span><br>
                        <input type="checkbox" name="reportReason"><span id="reason1">這含有不當內容，如：色情、粗俗字眼、暴力等訊息...</span><br>
                        <input type="checkbox" name="reportReason"><span id="reason2">這是廣告訊息</span><br>
                        <input type="checkbox" name="reportReason"><span id="reason3">內文與實際不符</span><br>
                        <input type="checkbox" name="reportReason"><span id="reason4">我覺得這不應該出現</span><br>
                        <textarea id="reportOpinion" cols="50" rows="10" style="resize: none;" placeholder="請輸入其他意見"></textarea><br>
                    </div>
                    <div class="modal-footer">
                        <span style="color: red;font-weight: bold;">確定檢舉?</span>
                        <button type="button" class="btn btn-default" id="sendforumReport" data-dismiss="modal">是</button>
                        <button type="button" class="btn btn-default" data-dismiss="modal">否</button>
                    </div>
                </div>
            </div>
        </div>
        <!-- 檢舉文章跳窗-->
        <div class="container col-12 col-md-10 col-md-offset-1" style="margin-top:10%">
            <div class="row">
              <% if(!hide_dropdown){%>
                <div class="col-12 col-md-1">
                   <!--判斷若不是會員或者是會員但沒有放大頭照,則留言的頭像會顯示預設圖片 -->
                
					<%
					memVO = (MemVO)session.getAttribute("memVO");
					if(memVO != null){
						String picture = memVO.getProfilepicEncoded();	
						if(picture != null){ 
					%>
					<img src="data:image/*;base64,${memVO.profilepicEncoded}" class="img-circle" style="height: 50px; width: 50px; margin:auto">
					<%}else{ %>
					<img src="<%=request.getContextPath()%>/FrontEnd/images/visitor.jpg" class="img-circle" style="height: 50px; width: 50px; margin:auto">
					<%} %>
					<%}else{ %>
					<img src="<%=request.getContextPath()%>/FrontEnd/images/visitor.jpg" class="img-circle" style="height: 50px; width: 50px; margin:auto">
					<%}%>
                </div>
                <%} %>
                <!--                         文章操作區 -->
                <% if(!hide_dropdown){%>
                <form method="post" action="arti_reply.do">
                    <div>
                        <div class="input-group">
                            <div class="col-md-12">
                                <textarea rows="4" style="resize:none;" class="form-control" placeholder="留言......" name="rep_content"></textarea>
                            </div>
                            <div class="input-group-btn">
                                <input type="hidden" name="arti_no" value="${articleVO.arti_no}">
					          	<input type="hidden" name="mem_no" value="${memVO.mem_no}">
					          	<input type="hidden" name="action" value="insert">
                                <button type="submit" class="btn btn-default" style="margin-top:100%">送出</button>
                                
                            </div>
                            
                        </div>
                        
                    </div>
                    <p style="color:red; margin-left:100px ;font-family:微軟正黑體;">${errorMsgs.rep_content}</p>
                </form>
              <%} %>
              <!--                         文章操作區 -->
            </div>

            <div style="margin-top:50px">
            	<%! int artiIndexInArticle;%>
                <c:forEach var="arti_replyVO" items="${list}">
                <c:if test="${!artireply_no_of_report_done.contains(arti_replyVO.rep_no)}">
                	<!--                           		如果這個留言是被檢舉但尚未處理 -->
                    <div class="replyContentArea">
                        <div class="media col-md-11">
                            <div class="media-left">
                                <a href="#">
						      <img src="data:image/*;base64,${MemService.findByPK(arti_replyVO.mem_no).profilepicEncoded}" class="img-circle" style="height: 50px; width: 50px; margin:auto;">
						    </a>
                            </div>
                            <div class="media-body">
                                <h4 class="media-heading">${MemService.findByPK(arti_replyVO.mem_no).mem_name} &#12288;<small>回覆於&nbsp;<i><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${arti_replyVO.rep_time}" /></i></small></h4>
                                <div>
                                    <p class="text" style="margin-left:15px;font-size:20px;color:black;">
                                        ${arti_replyVO.rep_content} 
                                        <c:if test="${memVO != null && artireply_no_of_report.contains(arti_replyVO.rep_no)}">
                                        	<span style="margin-left:15%; font-size:18px;color:red">此留言檢舉審核中</span>
                                        </c:if>
                                    </p>
                                </div>
                            </div>

                        </div>

                        <div class="col-md-1" style="margin-top: 20px">	
						<!-- 留言操作區 -->
                          <% if(!hide_dropdown && !hide_reply_dropdown){%>
                            <div class="btn-group" style="margin-left:10px">
                                <button type="button" class="btn btn-xs btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
							    <i class="glyphicon glyphicon-option-horizontal"></i>
							  </button>
								<input type="hidden" value="${arti_replyVO.rep_no}"> 	  <!-- 藏留言編號-->
								<ul class="dropdown-menu edit">
									<%  
                    	 				artiReply_mem_no = list.get(artiIndexInArticle).getMem_no();	//得到當下這個回覆的留言者
                                  	 %>
                                  	 <c:if test="${arti_replyVO.mem_no != memVO.mem_no}">
                                  	 	<li class="artiReplyReportBtn" value="${arti_replyVO.rep_content}"><a href="#" data-toggle="modal" data-target="#forumReport">檢舉</a></li>
                                  	 	<li style="display:none"><a href="#" data-toggle="modal" id="${arti_replyVO.rep_no}" onclick="getRep_no(this)" data-target="#deleteRep">刪除</a></li>
                                  	 </c:if>
                                  	 <c:if test="${arti_replyVO.mem_no == memVO.mem_no}">
                                  	 	<li style="display:none" class="artiReplyReportBtn" value="${arti_replyVO.rep_content}"><a href="#" data-toggle="modal" data-target="#forumReport">檢舉</a></li>
                                  	 	<li><a href="#" data-toggle="modal" id="${arti_replyVO.rep_no}" onclick="getRep_no(this)" data-target="#deleteRep">刪除</a></li>
                                  	 </c:if>
                                </ul>
                                <% artiIndexInArticle++;%>
                            </div>
                            <%}%>
							<!-- 每判斷完一次是否為未處理的檢舉留言，判斷值要改回預設的 -->
                            <% hide_reply_dropdown = false;%>		
                            <!--留言操作區 -->
                        </div>
                    </div>
                    <hr>
                    </c:if>
                </c:forEach>
                <% artiIndexInArticle = 0;%>
            </div>
           
            <script>
                function getRep_no(event) {
                	console.log(artireply_no_of_report);
                    rep_no = event.id;
                    console.log(rep_no);
                    console.log(artireply_no_of_report.includes(rep_no.toString()));
                    if(artireply_no_of_report.includes(rep_no.toString())){	//如果有找到的話
                		reply_deletable = false;
               	 	}
                    $('input[name="rep_no"]').val(rep_no);
                    if(!reply_deletable){
                    	$("#deleteRep p").text("此留言檢舉審核中，不可刪除");
                    	$("#deleteRep form input").hide();
                    }else{
                    	$("#deleteRep p").text("你確定要刪除這個留言嗎？");
                    	$("#deleteRep form input").show();
                    }
                    reply_deletable = true;
                }
				
            </script>
           
            <div class="modal fade" id="deleteRep" role="dialog">
                <div class="modal-dialog modal-sm">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                            <h4 class="modal-title">刪除</h4>
                        </div>
                        <div class="modal-body">
                            	<p style="font-size:15px">你確定要刪除這個留言嗎？</p>
                        </div>
                        <div class="modal-footer">
                            <form method="post" action="arti_reply.do">
                                <input type="submit" class="btn btn-danger" value="刪除">
                                <input type="hidden" name="arti_no" value="${articleVO.arti_no}">
                                <input type="hidden" name="rep_no" value="${arti_replyVO.rep_no}">
                                <input type="hidden" name="action" value="delete">
                                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div style="margin-top: 10%; ">
       <%@ include file="/FrontEnd/headerFooter/footer.html" %>
    </div>

</body>


</html>
