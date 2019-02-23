<%@ page contentType="text/html; charset=UTF-8" pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.item.model.*"%>


<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>直播快手登入</title>
<!-- ===原版型設定============================================================ -->
<%@ include file="/FrontEnd/headerFooter/head.jsp"%>
<!-- 思源黑體設定-->

</head>

<body>
<body>
	<!-- header -->
	<div id="header">
		<%@ include file="/FrontEnd/headerFooter/header.jsp"%>
	</div>
<div class="container">
	<h3><div id="status"></div></h3>
	                        <fb:login-button scope="public_profile,email" onlogin="checkLoginState();">
                        </fb:login-button>
	<input type="button" class="btn btn-primary btn-lg" id="go" value="Go Get Prize" 
	onclick=window.open("https://www.facebook.com/dialog/oauth?client_id=599955533703878&redirect_uri=https://localhost:8443<%=request.getContextPath()%>/FrontEnd/webSocketTest(local).jsp&scope=email&response_type=token")>

	
		<img src="images/3.jpg" class="img-responsive img-circle">
	</div>

	<script>
  // This is called with the results from from FB.getLoginStatus().
  function statusChangeCallback(response) {
    console.log('statusChangeCallback');
    console.log(response);
    // The response object is returned with a status field that lets the
    // app know the current login status of the person.
    // Full docs on the response object can be found in the documentation
    // for FB.getLoginStatus().
    if (response.status === 'connected') {
      // Logged into your app and Facebook.
      testAPI();
    } else {
      // The person is not logged into your app or we are unable to tell.
      document.getElementById('status').innerHTML = 'Please log ' +
        'into this app.';
    }
  }

  // This function is called when someone finishes with the Login
  // Button.  See the onlogin handler attached to it in the sample
  // code below.
function checkLoginState() {
    FB.getLoginStatus(function(response) {
        statusChangeCallback(response);
        console.log('Welcome!  Fetching your information.... ');
        var url = '/me?fields=id,name,email';
        FB.api(url, function(response) {
        	document.getElementById('status').innerHTML =(response.name + " " + response.id + " " +response.email);
        }, {scope: 'email'});
    });
}
  window.fbAsyncInit = function() {
    FB.init({
      appId      : '599955533703878',
      cookie     : true,  // enable cookies to allow the server to access 
                          // the session
      xfbml      : true,  // parse social plugins on this page
      version    : 'v3.1' // use graph api version 2.8
    });

    // Now that we've initialized the JavaScript SDK, we call 
    // FB.getLoginStatus().  This function gets the state of the
    // person visiting this page and can return one of three states to
    // the callback you provide.  They can be:
    //
    // 1. Logged into your app ('connected')
    // 2. Logged into Facebook, but not your app ('not_authorized')
    // 3. Not logged into Facebook and can't tell if they are logged into
    //    your app or not.
    //
    // These three cases are handled in the callback function.

    FB.getLoginStatus(function(response) {
      statusChangeCallback(response);
    });

  };

  // Load the SDK asynchronously
  (function(d, s, id) {
    var js, fjs = d.getElementsByTagName(s)[0];
    if (d.getElementById(id)) return;
    js = d.createElement(s); js.id = id;
    js.src = "https://connect.facebook.net/en_US/sdk.js";
    fjs.parentNode.insertBefore(js, fjs);
  }(document, 'script', 'facebook-jssdk'));

  // Here we run a very simple test of the Graph API after login is
  // successful.  See statusChangeCallback() for when this call is made.
  function testAPI() {
    console.log('Welcome!  Fetching your information.... ');
    FB.api('/me', function(response) {
      console.log('Successful login for: ' + response.name);
      document.getElementById('status').innerHTML =
        'Thanks for logging in, ' + response.name + response.id + '!';
      $("#status").append("<img src = 'http://graph.facebook.com/" + response.id
			+ "/picture'>");
    });
  }

  
  
  function cklogout(){
	    FB.getLoginStatus(function(response) {
	        if (response.status === 'connected') {
	            FB.logout(function(response) {
	                // this part just clears the $_SESSION var
	                // replace with your own code
	                $.get("/Member/logout/127337714").done(function() {
	                    //$('#status').html('<p>Logged out.</p>');
	                    alert('登出臉書');
	                    location.reload();
	                });
	            });
	        }
	    });
	}
</script>

	<!--
  Below we include the Login Button social plugin. This button uses
  the JavaScript SDK to present a graphical Login button that triggers
  the FB.login() function when clicked.
-->

	<!--
<fb:login-button scope="public_profile,email" onlogin="checkLoginState();">
</fb:login-button>
-->

	
<script src="<%=request.getContextPath()%>/FrontEnd/js/bootstrap.js "></script>

</body>
</html>
