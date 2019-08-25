package com.qklt.controller;

import com.qklt.domain.LoginData;
import com.qklt.service.Impl.RegisterServiceImpl;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class RegisterServlet extends HttpServlet {

    // 创建service对象
    RegisterServiceImpl registerServiceImpl = new RegisterServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        // 防止乱码
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");

        HttpSession session = req.getSession();
        String userName = req.getParameter("username");
        String password = req.getParameter("password");
        LoginData loginData = new LoginData(userName, password);

        if (registerServiceImpl.registerAccount(loginData)) {
            resp.getWriter().write("<script>alert('注册成功');" +
                    "window.location.href='" + req.getContextPath() +
                    "/JSP/login.jsp' </script>");
        } else {
            session.setAttribute("message", "用户名已使用");
            resp.sendRedirect(req.getContextPath() + "/JSP/register.jsp");
        }
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        // 防止乱码
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");

        String userName = req.getParameter("username");
        String result = null;

        if(registerServiceImpl.findUserByName(userName)) {
            result = "<font color='red'>用户名已使用</font>";
        } else {
            result = "<font color='green'>用户名可用</font>";
        }
        resp.setContentType("text/html");
        resp.getWriter().print(result);
    }

}