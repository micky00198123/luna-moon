package com.qklt.controller;


import com.qklt.domain.LoginData;
import com.qklt.domain.User;
import com.qklt.service.Impl.LoginServiceImpl;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		HttpSession session = req.getSession();
		String userName = req.getParameter("username");
		String password = req.getParameter("password");
		LoginData loginData = new LoginData(userName, password);

		// 创建service对象
		LoginServiceImpl loginServiceImpl = new LoginServiceImpl();

		// 调用Service对象的CheckAccount()方法对用户进行验证并获取User实例
		User user = loginServiceImpl.checkAccount(loginData);

		if(user == null) {
			session.setAttribute("message", "用户名或密码错误");
			resp.sendRedirect(req.getContextPath() + "/JSP/login.jsp");
		} else {
		    session.setAttribute("user", user);
		    resp.sendRedirect(req.getContextPath() + "/JSP/home.jsp");
		}


	}
}
