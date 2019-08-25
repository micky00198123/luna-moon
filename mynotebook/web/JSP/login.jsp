<%--
  Created by IntelliJ IDEA.
  User: MLP
  Date: 2019/8/23
  Time: 9:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>

    <style type="text/css">

        body{
            margin: 0;
            padding: 0;
            background: #487eb0;
        }

        .login_form {
            width: 330px;
            padding: 20px;
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

        .login_form h1{
            margin: 40px;
            font-family: "Celestia Redux", serif;
            color: #fff;
            font-size: 48px;
        }

        .login_form input{
            display: block;
            width: 100%;
            height: 45px;
            padding: 0 16px;
            text-align: center;
            box-sizing: border-box;
            outline: none;
            font-size: 16px;
            font-family: "Celestia Redux", serif;
        }

        .login_form a{
            text-decoration: none;
            color: #fff;
            font-family: "Celestia Redux", serif;
            font-size: 14px;
            padding: 10px;
            transition: 0.8s;
            display: block;
        }

        .login_form a:hover{
            background: rgba(0,0,0,.3);
        }

        .txtb{
            margin: 20px 0;
            background: rgba(255,255,255,.6);
            border-radius: 8px;
        }

        .sub{
            margin-top: 20px;
            margin-bottom: 10px;
            background: #487eb0;
            color: #fff;
            border-radius: 18px;
            cursor: pointer;
            transition: 0.8s;
        }

        .sub:hover{
            transform: scale(0.96);
        }

        .tip{
            color: #fff;
            font-size: 12px;
            margin-left: -6px;
            margin-right: -6px;
        }

        .checkAccount{
            color: crimson;
            font-size: 12px;
        }

    </style>

    <script src="${pageContext.request.contextPath }/L2D/3Dpony/lib/L2Dwidget.min.js"></script>
    <script type="text/javascript">
        L2Dwidget.init({
            "model": {
                "jsonPath": "../L2D/settings.json",
                "scale": 1
            },
            "display": {
                "position": "right",
                "width": 80,
                "height": 160,
                "hOffset": 0,
                "vOffset": -20
            },
            "mobile": {
                "show": true,
                "scale": 0.5
            },
            "react": {
                "opacityDefault": 0.7,
                "opacityOnHover": 0.2
            }
        });
    </script>

    <script>

        //待网页加载完毕后再执行的方法
        window.onload = function(){

            //表单提交前先交由js检查
            var logButton = document.getElementById("login");

            logButton.onclick = function check(){
                var uName = document.getElementById("username");
                var pw = document.getElementById("password");
                if(uName.value == ""){
                    document.getElementById('checkAccount').innerHTML="请输入用户名！";
                    return false;
                }
                if(pw.value == ""){
                    document.getElementById('checkAccount').innerHTML="请输入密码！";
                    return false;
                }
                return true;
            };

        };

    </script>
</head>
<body>

    <div class="login_form">

        <h1>Login</h1>

        <!-- 表单 -->
        <form action="userLogin" method="post">

            <p>
                <input type="text" name="username"
                       placeholder="请输入用户名" id="username" class="txtb">
            </p>
            <p>
                <input type="password" name="password"
                       placeholder="请输入密码" id="password" class="txtb">
            </p>

            <p class="tip">【欢迎光临一个还没做完的登录界面~~】</p>
            <p class="checkAccount" id="checkAccount">${message }</p>
            <% session.removeAttribute("message"); %>

            <input type="submit" value="登录" id="login"
                   class="sub" onsubmit="return check()">
            <a href="register.jsp">还没有账号？点此注册</a>

        </form>
    </div>

</body>
</html>
