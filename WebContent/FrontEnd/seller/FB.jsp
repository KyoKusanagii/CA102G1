<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html lang="en">

<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="https://fonts.googleapis.com/earlyaccess/notosanstc.css">


    <script type="text/javascript">
        (function(d, s, id) {
            var js, fjs = d.getElementsByTagName(s)[0];
            if (d.getElementById(id)) return;
            js = d.createElement(s);
            js.id = id;
            js.src = 'https://connect.facebook.net/zh_TW/sdk.js#xfbml=1&version=v3.0&appId=246507402569803&autoLogAppEvents=1';
            fjs.parentNode.insertBefore(js, fjs);
        }(document, 'script', 'facebook-jssdk'));
    </script>


    <style type="text/css">
        .product {
            font-family: Noto Sans TC, sans-serif;
            background-color: #59f;
            color: #fff;
            height: 40px;
            font-size: 20px;
            font-weight: 800;
        }

        .product .rftime {
            color: yellow;
            float: right;
            font-weight: bold;
            text-align: right;
            font-size: 20px;
        }

        thead {
            background: #aaa;
        }

        table {
            border: 3px;
        }

        tbody tr {

            height: 100px;
            text-align: center;
            font-family: Noto Sans TC, sans-serif;
            font-weight: 500;
        }

        .left {
            text-align: center;

        }
    </style>
</head>

<body>
    <div class="container-fluid">
        <div class="row">
            <div class="col-6">
                <div class="product">
                    商品編號: 20180716-1
                </div>
            </div>
            <div class="col-6">
                <div class="product">
                    即時訊息<span class=rftime>最後更新時間 : 20180716 21:09:06</span>
                </div>
            </div>
        </div>
        <div class="row mt-1">
            <div class="col-6">
                <div>

                </div>
                <div class="left">
                    <div class="fb-video" data-href="https://www.facebook.com/InstaBuy.tw/videos/235206010428478/" data-width="800" data-height="400" data-show-text="false">
                    </div>
                    <div class="btn-group my-2 btn-group-lg">
                        <button type="button" class="btn btn-danger">競標開始 <i class="fa fa-play"></i></button>
                        <button type="button" class="btn btn-success">競標結束 <i class="fa fa-pause"></i></button>
                    </div>
                </div>
            </div>


            <div class="col-6">
                <nav class="navbar-light bg-light">
                    <form>
                        <div class="form-row">
                            <div class="col">
                                <input type="text" class="form-control" placeholder="送出留言">
                            </div>
                            <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Post</button>
                            <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Refreshe </button>
                        </div>
                    </form>
                </nav>
                <div class="bid my-2">
                    <table border="8px">
                        <thead>
                            <tr>
                                <th style="width:500px">FB名稱</th>
                                <th style="width:500px">留言內容</th>
                                <th style="width:100px">得標</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td><img src="http://graph.facebook.com/271693200056719/picture?type=small"><br>大衛海鮮</td>
                                <td>
                                    <h3>100+1</h3>
                                </td>
                                <td><button class="btn btn-outline-primary">得標</button></td>
                            </tr>
                            <tr>
                                <td><img src="http://graph.facebook.com/2503217576362461/picture?type=small"><br>林宗宗</td>
                                <td>
                                    <h3>200+1</h3>
                                </td>
                                <td>
                                    <button class="btn btn-outline-primary">得標</button>
                                </td>
                            </tr>
                            <tr>
                                <td><img src="http://graph.facebook.com/130616477851402/picture?type=small"><br>賴旺旺</td>
                                <td>
                                    <h3>500+1</h3>
                                </td>
                                <td>
                                    <button class="btn btn-outline-primary">得標</button>
                                </td>
                            <tr>
                                <td><img src="http://graph.facebook.com/134572234070838/picture?type=small"><br>李柴柴</td>
                                <td>
                                    <h3>1000+1</h3>
                                </td>
                                <td>
                                    <button class="btn btn-outline-primary">得標</button>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js" integrity="sha384-smHYKdLADwkXOn1EmN1qk/HfnUcbVRZyYmZ4qpPea6sjB/pTJ0euyQp0Mk8ck+5T" crossorigin="anonymous"></script>
</body>

</html>