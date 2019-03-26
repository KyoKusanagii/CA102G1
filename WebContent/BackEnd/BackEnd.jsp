<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*,com.emp.model.EmpVO,com.item.model.*,com.category.model.*,com.mallorder.model.*"%>
<%@ page import="com.util.doOnServletStart"%>
            <!doctype html>
            <%
						//取得所有商品大項的分類            
						ItemService itemService = new ItemService();
            			//得到大項分類的數量
            			CategoryService catService = new CategoryService();
            			int  catSize = ((List<CategoryVO>)catService.getAll()).size();
            			//key為大項編號，value為大項名稱
            			Map<Integer,String> itemCatMap = catService.getNameByNO();
            			
            			//key為大項名稱，value為大項裡商品數量
            			Map<String,Integer> itemQuantityMap = new HashMap<>();
            			//key為大項名稱，value各大項的銷售額
            			Map<String,Integer> itemRevenuesMap = new HashMap<>();
            			//把取到的項目數量放到另一個集合裡
            			for(int catNo : itemCatMap.keySet()){
            				itemQuantityMap.put(itemCatMap.get(catNo),((List<ItemVO>)itemService.findByCat(catNo)).size());
            			}
            			pageContext.setAttribute("itemQuantityMap",itemQuantityMap);
            			
            			MallOrderService dao = new MallOrderService();
            			List<List<Integer>> revenuesListByCat = dao.revenuesByCat();
            			
            			
            			int[] revenuesList = new int[itemCatMap.size()]; //項目銷售額陣列的大小取決於有幾個大分類
            			for(int i=0;i<revenuesListByCat.get(1).size();i++) {	//價錢集合
            				int key = revenuesListByCat.get(0).get(i);	//得到商品大項編號
            				revenuesList[key-1] += revenuesListByCat.get(1).get(i);
            			}
            			for(int catNo : itemCatMap.keySet()){
            				itemQuantityMap.put(itemCatMap.get(catNo),((List<ItemVO>)itemService.findByCat(catNo)).size());
            				itemRevenuesMap.put(itemCatMap.get(catNo),revenuesList[catNo-1]);
            			}
            			pageContext.setAttribute("itemRevenuesMap",itemRevenuesMap);
            			
            			
            
            %>
            <html>
            <head>
                <meta charset="UTF-8">
                <title>管理頁面</title>
                <!-- Latest compiled and minified CSS -->
                <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css">
                <!-- Optional theme -->
                <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.2/css/bootstrap.min.css" integrity="sha384-Smlep5jCw/wG7hdkwQ/Z5nLIefveQRIY9nfy6xoR1uRYBtpZgI6339F5dgvm/e9B" crossorigin="anonymous">
                <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
                <link rel="stylesheet" href="css/BackEnd.css">
                <!-- Latest compiled and minified JavaScript -->
                <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
                <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
                <script src="//code.jquery.com/jquery-1.12.4.min.js"></script>
                <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.4.0/Chart.min.js"></script>
                <script>
                    var ctx = document.getElementById('myChart').getContext('2d');
                    var chart = new Chart(ctx, {
                        // The type of chart we want to create
                        type: 'line',

                        // The data for our dataset
                        data: {
                            labels: ["January", "February", "March", "April", "May", "June", "July"],
                            datasets: [{
                                label: "My First dataset",
                                backgroundColor: 'rgb(255, 99, 132)',
                                borderColor: 'rgb(255, 99, 132)',
                                data: [0, 10, 5, 2, 20, 30, 45],
                            }]
                        },

                        // Configuration options go here
                        options: {}
                    });

                </script>
                <script>
                    $(document).ready(function() {

                        $(function() {
                            if (${emp.emp_mem_auth == 0}){
                            	   $("#emp_mem_auth").hide();
                            }
                              
                            if (${emp.emp_carousel_auth == 0} ){
                            	   $("#emp_carousel_auth").hide();
                            }
                             
                            if (${emp.emp_chat_auth == 0} ){
                            	 $("#emp_chat_auth").hide();
                            }
                               
                            if (${emp.emp_report_auth == 0}){
                            	 $(".emp_report_auth").hide();
                            }
                               
                            if (${emp.emp_level == 2} ){
                            	 $("#emp_level").hide();
                            }
                               
                        });

                    });

                </script>
                <style>
                    .chart {
                        height: 300px;
                        width: 450px;
                    }

                </style>

            </head>

            <body>
                <script src="js/Logout.js"></script>
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
                        <span class="list-group-item list-group-item-action active">主要功能配置</span>
                        <a href="<%=request.getContextPath()%>/BackEnd/limitSale/getAllLimitSale.jsp" class="list-group-item list-group-item-action">設置限時特賣</a>
                        <a href="<%=request.getContextPath()%>/BackEnd/carousel/carouselIndex.jsp" class="list-group-item list-group-item-action" id="emp_carousel_auth">設置幻燈片</a>
                        <a href="<%=request.getContextPath()%>/BackEnd/news/News.jsp" class="list-group-item list-group-item-action">設置最新消息</a>
                        <a href="ItemReport.jsp" class="list-group-item list-group-item-action emp_report_auth">商品檢舉管理</a>
                        <a href="ForumReport.jsp" class="list-group-item list-group-item-action emp_report_auth">討論區檢舉管理</a>
                        <a href="<%=request.getContextPath()%>/BackEnd/member/Member.jsp" class="list-group-item list-group-item-action" id="emp_mem_auth">會員帳號管理</a>
                        <a href="EmployeeAuth.jsp" class="list-group-item list-group-item-action" id="emp_level">員工帳號管理</a>
                        <a href="ContactService.jsp" class="list-group-item list-group-item-action" id="emp_chat_auth">聯絡客服</a>
                    </div>

                </div>

                <div class="col-10 operationArea" style="border: none;width: 1400px;height: 850px;overflow-y:auto;">
                    <div class="row">
                        <h2>網站統計數據</h2>
                    </div>
                    <div class="row">
                        <div class="col-6 chart">
                            <canvas id="myChart"></canvas>
                            <script>
                            	var lables = []; //存放柱狀圖標籤名稱
                            	var catQuantity = []; //存放每個大項目的商品數量
                                var ctx = document.getElementById("myChart");
                                var ctx = document.getElementById("myChart").getContext("2d");
                                var ctx = $("#myChart");
                                var ctx = "myChart";
                            </script>
                            <c:forEach var="itemQuantity" items="${itemQuantityMap}">
                                  <script>
                            	 	lables.push("${itemQuantity.key}");
                            	 	catQuantity.push(${itemQuantity.value});
                            	 </script>
                           	</c:forEach>
                            <script>
                                //柱狀圖類別及屬性
                                var ctx = document.getElementById("myChart");
                                var myChart = new Chart(ctx, {
                                    "type": 'bar',
                                    "data": {
                                        "labels": lables,
                                        "datasets": [{
                                        	"label": '商品數量統計',
                                        	"data": catQuantity,
                                            "backgroundColor": [
                                                'rgba(255, 99, 132, 0.2)',
                                                'rgba(54, 162, 235, 0.2)',
                                                'rgba(255, 206, 86, 0.2)',
                                                'rgba(75, 192, 192, 0.2)',
                                                'rgba(153, 102, 255, 0.2)',
                                                'rgba(255, 159, 64, 0.2)'
                                            ],
                                            "borderColor": [
                                                'rgba(255,99,132,1)',
                                                'rgba(54, 162, 235, 1)',
                                                'rgba(255, 206, 86, 1)',
                                                'rgba(75, 192, 192, 1)',
                                                'rgba(153, 102, 255, 1)',
                                                'rgba(255, 159, 64, 1)'
                                            ],
                                            "borderWidth": 1
                                        }]
                                    },
                                    options: {
                                        scales: {
                                            yAxes: [{
                                                ticks: {
                                                    beginAtZero: true
                                                }
                                            }]
                                        }
                                    }
                                });

                            </script>
								<!--key為大項名稱，value為大項裡商品數量，lables存放柱狀圖的標籤名稱 -->
								 <script>
                           	  console.log(lables);
                           	  console.log(catQuantity);
                         	  	$.extend(myChart,lables,catQuantity);
                           	  </script>
                        </div>
                        <div class="col-4 ml-8 mb-4 chart" style="margin-left:100px;margin-top:50px;">
                        	<h6 style="margin-top:10px;">商品種類銷售額統計(單位:元)</h6>
                            <canvas id="myPieChart" style="height:300px;width:300px;"></canvas>
                            <script>
                                var ctx = document.getElementById("myPieChart");
                                var ctx = document.getElementById("myPieChart").getContext("2d");
                                var ctx = $("#myPieChart");
                                var ctx = "myPieChart";
                                var revenuesList = [];

                            </script>
							<!--itemCatMap 可映射項目編號:項目名稱-->
                              <c:forEach var="itemRevenues" items="${itemRevenuesMap}">
                                  <script>
                                  	revenuesList.push(${itemRevenues.value});
                            	 </script>
                           	</c:forEach>
                            <script>
                                new Chart(document.getElementById("myPieChart"), {
                                    "type": "pie",
                                    "data": {
                                        "labels":  lables,
                                        "datasets": [{
                                            "label": "商品種類銷售額統計",
                                            "data": revenuesList,
                                            "backgroundColor": ["rgb(255, 99, 132)", "rgb(54, 162, 235)", "rgb(255, 205, 86)","#00FFFF","#77FF00","#B088FF"]
                                        }]
                                    }
                                });
                                //                        // And for a doughnut chart
                                //                        var myDoughnutChart = new Chart(ctx, {
                                //                            type: 'doughnut',
                                //                            data: data,
                                //                           
                                //                        });

                            </script>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-6 chart ml-8" style="margin-top:80px;height: 300px;width: 450px;">
                            <canvas id="myLineChart"></canvas>
                            <script>
                                var ctx = document.getElementById("myLineChart");
                                var ctx = document.getElementById("myLineChart").getContext("2d");
                                var ctx = $("#myLineChart");
                                var ctx = "myLineChart";
                                var cycle = [];	//每日拜訪人氣的週期顯示時間
                                var visitTimes = [];
                            </script>
                            <c:forEach var="cycleVO" items="${cycle}">
                            	<script>
                            		cycle.push("${cycleVO}");	//加入時間標籤
                            	</script>
                            </c:forEach>
                            
                            <script>cycle.reverse();</script>
                            
                             <c:forEach var="visitTimesVO" items="${visitTimes}">
                            	<script>
                            		visitTimes.push(${visitTimesVO});	//加入時間標籤
                            	</script>
                            </c:forEach>
                            
                              <script>visitTimes.reverse();</script>
                              
                            <script>
                            	 cycle.reverse();
                            	 console.log()
                            	 visitTimes.reverse();
                            	 console.log(cycle);
                            	 console.log(visitTimes);
                                //線條折線圖類別及屬性
                                
                                new Chart(document.getElementById("myLineChart"), {
                                    "type": "line",
                                    "data": {
                                        "labels": cycle,
                                        "datasets": [{
                                            "label": "每分鐘的網站拜訪人次",
                                            "data": visitTimes,
                                            "fill": false,
                                            "borderColor": "rgb(75, 192, 192)",
                                            "lineTension": 0.1
                                        }]
                                    },
                                    "options": {}
                                });
								

                                //                    	var stackedLine = new Chart(ctx, {
                                //                       	 	type: 'line',
                                //                        	data: data,
                                //                        	options: {
                                //                            scales: {
                                //                                yAxes: [{
                                //                                    stacked: true
                                //                                }]
                                //                            }
                                //                        }
                                //                    	});

                            </script>
                        </div>
<!--                         <div class="col-4 ml-8 mb-4 chart" style="margin-left:150px;margin-top:60px;"> -->
<%--                             <canvas id="myRadarChart" style="height:100px;width:100px;"></canvas> --%>
<!--                             <script> -->
<!-- //                                 var ctx = document.getElementById("myRadarChart"); -->
<!-- //                                 var ctx = document.getElementById("myRadarChart").getContext("2d"); -->
<!-- //                                 var ctx = $("#myRadarChart"); -->
<!-- //                                 var ctx = "myRadarChart"; -->

<!--                             </script> -->
<!--                             <script> -->
<!-- //                                 data = { -->
<!-- //                                     datasets: [{ -->
<!-- //                                         data: [100, 100, 100] -->
<!-- //                                     }], -->

<!-- //                                     // These labels appear in the legend and in the tooltips when hovering different arcs -->
<!-- //                                     labels: [ -->
<!-- //                                         'Red', -->
<!-- //                                         'Yellow', -->
<!-- //                                         'Blue' -->
<!-- //                                     ] -->
<!-- //                                 }; -->
<!-- //                                 new Chart(document.getElementById("myRadarChart"), { -->
<!-- //                                     "type": "radar", -->
<!-- //                                     "data": { -->
<!-- //                                         "labels": ["Eating", "Drinking", "Sleeping", "Designing", "Coding", "Cycling", "Running"], -->
<!-- //                                         "datasets": [{ -->
<!-- //                                             "label": "My First Dataset", -->
<!-- //                                             "data": [98, 59, 90, 81, 56, 90, 70], -->
<!-- //                                             "fill": true, -->
<!-- //                                             "backgroundColor": "rgba(255, 99, 132, 0.2)", -->
<!-- //                                             "borderColor": "rgb(255, 99, 132)", -->
<!-- //                                             "pointBackgroundColor": "rgb(255, 99, 132)", -->
<!-- //                                             "pointBorderColor": "#fff", -->
<!-- //                                             "pointHoverBackgroundColor": "#fff", -->
<!-- //                                             "pointHoverBorderColor": "rgb(255, 99, 132)" -->
<!-- //                                         }, { -->
<!-- //                                             "label": "My Second Dataset", -->
<!-- //                                             "data": [28, 48, 40, 19, 96, 27, 100], -->
<!-- //                                             "fill": true, -->
<!-- //                                             "backgroundColor": "rgba(54, 162, 235, 0.2)", -->
<!-- //                                             "borderColor": "rgb(54, 162, 235)", -->
<!-- //                                             "pointBackgroundColor": "rgb(54, 162, 235)", -->
<!-- //                                             "pointBorderColor": "#fff", -->
<!-- //                                             "pointHoverBackgroundColor": "#fff", -->
<!-- //                                             "pointHoverBorderColor": "rgb(54, 162, 235)" -->
<!-- //                                         }] -->
<!-- //                                     }, -->
<!-- //                                     "options": { -->
<!-- //                                         "elements": { -->
<!-- //                                             "line": { -->
<!-- //                                                 "tension": 0, -->
<!-- //                                                 "borderWidth": 3 -->
<!-- //                                             } -->
<!-- //                                         } -->
<!-- //                                     } -->
<!-- //                                 }); -->
                            
<!--                             </script> -->
<!--                         </div> -->
                    </div>
                </div>

            </body>

            </html>
