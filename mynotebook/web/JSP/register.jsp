<%--
  Created by IntelliJ IDEA.
  User: MLP
  Date: 2019/8/23
  Time: 9:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>register</title>

    <style type="text/css">

        body{
            margin: 0;
            padding: 0;
            background: #487eb0;
        }

        .register_form {
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

        .register_form h1{
            margin: 30px;
            font-family: "Celestia Redux";
            color: #fff;
            font-size: 48px;
        }

        .register_form input{
            display: block;
            width: 100%;
            height: 45px;
            padding: 0 16px;
            text-align: center;
            box-sizing: border-box;
            outline: none;
            font-size: 16px;
            font-family: "Celestia Redux";
        }

        .register_form a{
            text-decoration: none;
            color: #fff;
            font-family: "Celestia Redux";
            font-size: 14px;
            padding: 10px;
            transition: 0.8s;
            display: block;
        }

        .register_form a:hover{
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

        #checkAccount{
            color: red;
            font-size: 12px;
        }

    </style>

    <script src="../L2D/3Dpony/lib/L2Dwidget.min.js"></script>
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

            var regButton = document.getElementById("register");
            regButton.onclick = function check(){
                //通过id获取标签对象
                var pw1 = document.getElementById("password");
                var pw2 = document.getElementById("password2");

                var rightPassword = /^[a-zA-Z0-9]{6,16}$/;//密码正则

                //获取input内容用value
                if(!rightPassword.test(pw1.value)){
                    document.getElementById("checkAccount").innerHTML="密码不合法！";
                    return false;
                }
                if(pw1.value != pw2.value){
                    document.getElementById("checkAccount").innerHTML="两次密码输入不一致！";
                    return false;
                }
                return true;
            };

        };

        // 实时检查名字是否可用
        function checkName(){

            // 检查用户名是否合规范
            var rightUserName = /^[a-zA-Z0-9_]{4,16}$/;
            var name = document.getElementById("username").value;
            if(!rightUserName.test(name)){
                document.getElementById("checkAccount").innerHTML="用户名不合法！";
                return;
            }

            // 检查用户名是否重名
            var xmlhttp = new XMLHttpRequest();

            // 直接将用户名附在url之后,使用get请求访问服务器
            var url = "${pageContext.request.contextPath }/JSP/userRegister";
            url += "?username=" + name;
            xmlhttp.open("GET",url,true);
            xmlhttp.send();

            // 待状态码改变,请求响应完成,且处于"OK"状态时
            xmlhttp.onreadystatechange = function() {
                if (xmlhttp.readyState==4 && xmlhttp.status==200){
                    document.getElementById("checkAccount").innerHTML
                        = xmlhttp.responseText;
                }
            };

        }

    </script>
</head>
<body>

    <div class="register_form">

        <h1>Register</h1>

        <form action="userRegister" method="post">

            <p>
                <input type="text" name="username" oninput="checkName()"
                       placeholder="请输入用户名" id="username" class="txtb">
            </p>
            <p>
                <input type="password" name="password"
                       placeholder="请输入密码" id="password" class="txtb">
            </p>
            <p>
                <input type="password" name="password2"
                       placeholder="确认密码" id="password2" class="txtb">
            </p>

            <p class="tip">【用户名长度在4~16字符之间，由字母、数字、下划线组成】</p>
            <p class="tip">【密码长度在6~16字符之间，由字母、数字组成】</p>

            <p id="checkAccount"></p>

            <input type="submit" value="提交注册" id="register"
                   class="sub" onsubmit="return check()">
            <a href="login.jsp">已有账号？点此登录</a>

        </form>
    </div>

</body>
</html>
