<%--
  Created by IntelliJ IDEA.
  User: MLP
  Date: 2019/8/23
  Time: 20:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>home</title>

    <script>
        window.onload = function(){

            var logout = document.getElementById("logout");
            logout.onclick = function logoutAccount(){
                if(confirm("是否登出?")){
                    var xmlhttp = new XMLHttpRequest();
                    var url = "${pageContext.request.contextPath }/JSP/logout";
                    xmlhttp.open("GET",url,true);
                    xmlhttp.send();
                    alert("注销成功!");
                    location.reload();
                }
            };

            if(${user != null}){
                document.getElementById("href").style.display="none";
                document.getElementById("user").style.display="inline";
                document.getElementById("information").style.display="inline";

                document.getElementById("welcome").innerHTML="欢迎: ${user.userName}";
                document.getElementById("name").innerHTML="昵称:${user.userName}";
                document.getElementById("sex").innerHTML="性别:${user.sex}";
                document.getElementById("age").innerHTML="年龄:${user.age}";
            }

        };
    </script>

</head>
<body>

    <div id="href">
        <a id="login" href="login.jsp">登录</a>
        <a id="register" href="register.jsp">注册</a>
    </div>

    <div id="user" style="display: none">
        <p id="welcome"></p>
        <button id="logout" type="button">注销</button>
    </div>

    <div id="information" style="display: none">
        <h1>个人信息</h1>
        <p id="name"></p>
        <p id="sex"></p>
        <p id="age"></p>
    </div>

</body>
</html>
