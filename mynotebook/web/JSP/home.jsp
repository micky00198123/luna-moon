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

            if(${user != null}){
                document.getElementById("test").innerHTML="欢迎: ${user.userName}";
                document.getElementById("name").innerHTML="${user.userName}";
                document.getElementById("sex").innerHTML="${user.sex}";
                document.getElementById("age").innerHTML="${user.age}";
            }

        };
    </script>

</head>
<body>

    <div>
        <p id="test"></p>
        <h1>个人信息</h1>
        昵称:<p id="name">请先登录！</p>
        性别:<p id="sex">请先登录！</p>
        年龄:<p id="age">请先登录！</p>
    </div>

</body>
</html>
