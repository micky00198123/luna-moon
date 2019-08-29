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

    <style>
        #uploadLabel{
            background: green;
            color: white;
            border-radius: 16px;
            cursor: pointer;
        }
    </style>

    <script>
        window.onload = function(){

            // 绑定注销按钮
            var logout = document.getElementById("logout");
            logout.onclick = function logoutAccount(){
                if(confirm("是否登出?")){
                    var xmlhttp = new XMLHttpRequest();
                    var url = "${pageContext.request.contextPath }/JSP/logout.do";
                    url += "?case=logout";
                    xmlhttp.open("GET",url,true);
                    xmlhttp.send();
                    xmlhttp.onreadystatechange = function() {
                        if (xmlhttp.readyState==4 && xmlhttp.status==200){
                            location.reload();
                        }
                    };
                }
            };

            // 绑定修改信息按钮
            var setting = document.getElementById("settingButton");
            setting.onclick = function doSetting(){
                document.getElementById("settingButton").style.display="none";
                document.getElementById("details").style.display="none";
                document.getElementById("setting").style.display="inline";
            };

            // 个人信息赋值
            if(${user != null}){
                document.getElementById("img").style.display="inline";
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

    <br><br>

    <div id="img" style="display: none">
        <img src="${pageContext.request.contextPath }/img/${user.portrait }"
             id="portrait" width="80" height="80" style="border-radius: 100px;">

        <input type="file" id="imgToUpload" style="display: none"
               onchange="uploadImg(this)" accept="image/*">
        <label for="imgToUpload" id="uploadLabel">选择头像</label>
        <script type="text/javascript">
            // 待上传图片显示
            function uploadImg(obj) {
                var file = obj.files[0];
                var reader = new FileReader();
                reader.onload = function () {
                    var img = document.getElementById("portrait");
                    img.src = this.result;
                };
                reader.readAsDataURL(file);
            }
        </script>

        <input type="button" onclick="uploadFile()" value="上传头像">
        <a id="progressNumber"></a>
        <script>
            // 上传图片
            function uploadFile() {
                var fd = new FormData();
                fd.append("imgToUpload", document.getElementById('imgToUpload').files[0]);
                var xhr = new XMLHttpRequest();
                xhr.upload.addEventListener("progress", uploadProgress, false);
                xhr.addEventListener("load", uploadComplete, false);
                xhr.open("POST", "uploadImg.do");
                xhr.send(fd);
            }

            // 上传进度百分比
            function uploadProgress(evt) {
                if (evt.lengthComputable) {
                    var percentComplete = Math.round(evt.loaded * 100 / evt.total);
                    document.getElementById('progressNumber').innerHTML = percentComplete.toString() + '%';
                } else {
                    document.getElementById('progressNumber').innerHTML = 'unable to compute';
                }
            }

            // 上传完成弹窗提示
            function uploadComplete(evt) {
                alert("上传完成!");
            }
        </script>
    </div>

    <div id="information" style="display: none">
        <h1>个人信息</h1>
        <button id="settingButton" type="button">修改信息</button>

        <div id="details">
            <p id="name"></p>
            <p id="sex"></p>
            <p id="age"></p>
        </div>

        <form id="setting" action="setting.do" method="get" style="display: none">
            <!-- 隐藏域放case -->
            <input type="hidden" name="case" value="setting">
            <div>
                性别:
                <input type="radio" name="setSex" value="保密">保密
                <input type="radio" name="setSex" value="雄性">雄性
                <input type="radio" name="setSex" value="雌性">雌性
                <script>
                    // 设置默认性别
                    (function (rName,rValue){
                        var rObj = document.getElementsByName(rName);
                        for(var i = 0; i < rObj.length; i ++){
                            if(rObj[i].value == rValue){
                                rObj[i].checked =  "checked";
                            }
                        }
                    })("setSex","${user.sex }");
                </script>
            </div>
            <br>
            <div>
                年龄:
                <select id="setAge" name="setAge">
                    <script>
                        // 设置默认年龄
                        (function(){
                            var set = document.getElementById("setAge");
                            var age = "${user.age }";
                            for(var i = 0; i < 100; i ++){
                                var opt = document.createElement("option");
                                if(i == 0){
                                    opt.value = "保密";
                                    opt.innerHTML = "保密";
                                } else {
                                    opt.value = i;
                                    opt.innerHTML = i;
                                }
                                if(age == i){
                                    opt.selected = true;
                                }
                                set.appendChild(opt);
                            }
                        })();
                    </script>
                </select>
            </div>
            <br>
            <input type="submit" value="保存修改" id="saveSetting">
        </form>

    </div>

</body>
</html>
