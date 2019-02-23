var DifferenceHour = -1;
var DifferenceMinute = -1;
var DifferenceSecond = -1;
var Tday = new Date("JUL 02, 2018 23:00:00");
var daysms = 24 * 60 * 60 * 1000;
var hoursms = 60 * 60 * 1000;
var Secondms = 60 * 1000;
var microsecond = 1000;

function clock() {
    //取得現在的時間資料
    var time = new Date()
    var hour = time.getHours()

    var minute = time.getMinutes()
    var second = time.getSeconds()
    var timevalue = "" + ((hour > 12) ? hour - 12 : hour)

    //設定日期格式
    timevalue += ((minute < 10) ? ":0" : ":") + minute
    timevalue += ((second < 10) ? ":0" : ":") + second
    timevalue += ((hour > 12) ? " PM" : " AM")

    //計算現在的時間資料與倒數標的之差異
    var convertHour = DifferenceHour
    var convertMinute = DifferenceMinute
    var convertSecond = DifferenceSecond

    //透過getTime出來會是毫秒的數字
    var Diffms = Tday.getTime() - time.getTime()

    //根據不同情況以相除得到不同標準的數字
    DifferenceHour = Math.floor(Diffms / daysms)
    Diffms -= DifferenceHour * daysms
    DifferenceMinute = Math.floor(Diffms / hoursms)
    Diffms -= DifferenceMinute * hoursms
    DifferenceSecond = Math.floor(Diffms / Secondms)
    Diffms -= DifferenceSecond * Secondms

    //如果 hour 資料有改變，則 form 那邊也要改變
    if (convertHour != DifferenceHour) {
        document.formnow.dd.value = DifferenceHour
    }

    //如果 Minute 資料有改變，則 form 那邊也要改變
    if (convertMinute != DifferenceMinute) {
        document.formnow.hh.value = DifferenceMinute
    }

    //如果 Second 資料有改變，則 form 那邊也要改變
    if (convertSecond != DifferenceSecond) {
        document.formnow.mm.value = DifferenceSecond
    }

    //如果 Second 資料有改變，則 form 那邊也要改變
    var dSecs = Math.floor(Diffms / microsecond)
    document.formnow.ss.value = dSecs

    //每1000毫秒(即1秒)不斷自動呼叫 clock() 以更新秒數資料
    setTimeout("clock()", 1000)
}

$(document).ready(function () {
    var state = true;
    var addToCartTimes = 0; //加入購物車的次數
    $("#follow").click(function () {
        $(this).attr("class", state ? "glyphicon glyphicon-heart-empty" : "glyphicon glyphicon-heart");
        state = !state;
    });
    $("#addItem").click(function () {
        if (parseInt($("#itemInventory").text()) == 0) {
            alert("該項商品目前缺貨中");
            return;
        }

        if (parseInt($("#buyQuantity").text()) == parseInt($("#itemInventory").text())) {
            alert("購買數量大於庫存數量，請重新選擇數量");
            return;
        }
        $("#buyQuantity").text(parseInt($("#buyQuantity").text()) + 1);
    });
    $("#deleteItem").click(function () {
        if ($("#buyQuantity").text() == "1") {
            return;
        }
        $("#buyQuantity").text(parseInt($("#buyQuantity").text()) - 1);
    });
    $("#resetQuantity").click(function () {
        $("#buyQuantity").text("1");
    });

    $("#thisItemAddToCart").click(function () {
        if ($("#itemInventory").text() == 0) {
            alert("該項商品目前缺貨中");
            return;
        }
        alert("已加入至購物車");
        addToCartTimes++;
        $("#cartNotify").text(addToCartTimes);
        let thisItemInventory = $("#itemInventory").text();
        $("#itemInventory").text(parseInt(thisItemInventory) - parseInt($("#buyQuantity").text()));
        $("#pay").before("<li class='list-group-item item-canclear'><a href='#' style='color: black;" +
            "font-size: 15px;'>商品名稱" + $("#itemName").text() + "<br>商品價格:" +
            $("#itemPrice").text() + "<br>購買數量:" + $("#buyQuantity").text() + "</a></li>");
    });

    $("#shoppingCart").click(function () {
        if ($("#cartNotify").text() != "")
            $("#nothingInCart").hide();
        else if ($("#cartNotify").text() == "")
            $("#nothingInCart").show();
    });
    $("#cleanCart").click(function () { //清空購物車
        $(".item-canclear").remove();
        //$("#itemInventory").text(parseInt($("#buyQuantity").text()+)
        addToCartTimes = 0;
        $("#cartNotify").text("");
    });
});
