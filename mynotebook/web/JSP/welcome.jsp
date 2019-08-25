<%@ page language="java" import="java.util.*, java.io.PrintWriter, javax.servlet.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
  <head>
  
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>欢迎页面</title>
    
    <style type="text/css">

	body{
		margin: 0;
		padding: 0;
		background: #487eb0;
	}

	.welcome {
		width: 300px;
		padding: 15px;
		text-align: center;
		background: #191970;
		position: absolute;
		top: 50%;
		left: 50%;
		transform: translate(-50%, -50%);
		overflow: hidden;
		border-radius: 12px;
		z-index: 1;
	}
	
	#wel {
		margin: 20px;
		font-family: "Celestia Redux";
		color: #fff;
		font-size: 48px;
	}
	
	.welcome a {
		font-family: "Celestia Redux";
		color: #fff;
		font-size: 14px;
	}
	
</style>

	<!-- 泡泡背景 -->
	<link rel="stylesheet" href="../css/Bubble.css">

  </head>
  
  <body>
  
  <!-- 这里是欢迎界面,如果在未登录状态下直接访问该界面,会重定向至登录界面 -->
  	
  	<% 
	  	Boolean isLogin = false;
	  	String cookieName = "user";
	  	String userName = request.getParameter("username");
	  	
	  	//如果是从登录界面转发过来,userName就不会为空
	  	if(userName != null){
	  	
	  		isLogin = true;
	  		//cookie里只放用户名
			Cookie cookie = new Cookie(cookieName, userName);
			//关闭浏览器前这个cookie都有效
			response.addCookie(cookie);
			
	  	} else{
	  	
	  		//如果不是从登录界面转发过来的,检查一下有没有cookie信息
	  		Cookie[] cookies = request.getCookies();
	  		if(cookies != null && cookies.length > 0){
	  			for(Cookie c : cookies){
	  				if(cookieName.equals(c.getName())){
	  					isLogin = true;
	  					//把cookie中的username拿出来
	  					userName = c.getValue();
	  				}
	  			}
	  		}
	  		
	  	}
	  	
		//如果不是从登录界面转发过来的,也没有cookie信息,那么重定向至登录界面
	  	if(!isLogin){
	  		response.setCharacterEncoding("UTF-8");
	  		PrintWriter pw = response.getWriter();
	  		pw.print("<script> alert('您尚未登录，请先登录...');"
	  			+ "window.location.href='login.html' </script>");
	  	}
	%>
  	
  	<div class="welcome">
		<h1 id="wel">
			Welcome!
			<%= userName %>
		</h1>
		<a href="../html/login.html">返回登录</a>
		<a href="../html/register.html">返回注册</a>
	</div>

	<!-- 泡泡背景 -->
	<div class="wrap">
		<div class="bubble b1"></div>
		<div class="bubble b2"></div>
		<div class="bubble b3"></div>
		<div class="bubble b4"></div>
		<div class="bubble b5"></div>
		<div class="bubble b6"></div>
		<div class="bubble b7"></div>
		<div class="bubble b8"></div>
		<div class="bubble b9"></div>
		<div class="bubble b10"></div>
		<div class="bubble b11"></div>
		<div class="bubble b12"></div>
		<div class="bubble b13"></div>
		<div class="bubble b14"></div>
		<div class="bubble b15"></div>
	</div>
  
  </body>
  
</html>
