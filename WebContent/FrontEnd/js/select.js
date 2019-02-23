  $(document).ready(function(){
                var sectors=new Array();
                sectors[0]=['長袖上衣','短袖上衣','背心','長褲','短褲','裙子','外套大衣','帽子','圍巾','其他'];
                sectors[1]=['休閒零食','進口零食','生鮮食品','米食/麵食','飲料/沖泡品','蛋糕/甜點','南北雜貨','伴手禮','其他'];
                sectors[2]=['主機遊戲','掌上型電玩','益智遊戲','手機遊戲','電腦遊戲','遊戲周邊商品','其他'];
                sectors[3]=['登山/露營','戶外休閒用品','運動用品','運動健身','自行車/腳踏車','運動服飾','雨衣/雨具','鞋子','旅行相關用品','其他'];
                sectors[4]=['生活家電','電視機','美容家電','影音設備','廚房家電','音響/喇叭','大型家電','其他'];
                sectors[5]=['筆記型電腦','電腦零組件','鍵盤滑鼠','電腦周邊配件','儲存裝置','單眼/相機','鏡頭','相機周邊配件','穿戴/智能裝置','耳機/麥克風','其他'];
                $("#primary").change(function(){
                    index=this.selectedIndex;  
                    switch(index){
                    case 0:
                    	plus = 0;
                    	break;
                    case 1:
                    	plus = 11;
                    	break;
                    case 2:
                    	plus = 20;
                    	break;
                    case 3:
                    	plus = 27;
                    	break;
                    case 4:
                    	plus = 37;
                    	break;
                    default:
                    	plus = 45;
                    }
                    var Sinner="";
                    for(var i=0;i<sectors[index].length;i++){
                    	var second = i + plus;
                        Sinner=Sinner+'<option value='+second+'>'+sectors[index][i]+'</option>';
                    }
                    $("#secondary").html(Sinner);
                });
                $("#primary").change();
            });