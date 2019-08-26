package com.qklt.controller;

import com.qklt.domain.LoginData;
import com.qklt.service.Impl.RegisterServiceImpl;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterServlet extends HttpServlet {

    // ����service����
    private RegisterServiceImpl registerServiceImpl = new RegisterServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        // ��ֹ����
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");

        String userName = req.getParameter("username");
        String password = req.getParameter("password");
        LoginData loginData = new LoginData(userName, password);

        if(registerServiceImpl.findUserByName(userName)) {
            resp.getWriter().write("<script>alert('�û�����ʹ��!');");
        }

        if (registerServiceImpl.registerAccount(loginData)) {
            resp.getWriter().write("<script>alert('ע��ɹ�');" +
                    "window.location.href='" + req.getContextPath() +
                    "/JSP/login.jsp' </script>");
        } else {
            resp.getWriter().write("<script>alert('����������,ע��ʧ��');");
        }
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        // ��ֹ����
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");

        String userName = req.getParameter("username");
        String result;

        if(registerServiceImpl.findUserByName(userName)) {
            result = "<font color='red'>�û�����ʹ��!</font>";
        } else {
            result = "<font color='green'>�û�������</font>";
        }
        resp.getWriter().print(result);
    }

}