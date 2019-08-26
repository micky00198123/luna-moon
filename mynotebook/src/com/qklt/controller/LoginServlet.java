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

		// ��ֹ����
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;charset=utf-8");

		HttpSession session = req.getSession();
		String userName = req.getParameter("username");
		String password = req.getParameter("password");
		LoginData loginData = new LoginData(userName, password);

		// ����service����
		LoginServiceImpl loginServiceImpl = new LoginServiceImpl();

		// ����Service�����CheckAccount()�������û�������֤����ȡUserʵ��
		User user = loginServiceImpl.checkAccount(loginData);

		if(user == null) {
			resp.getWriter().write("<script>alert('�û������������');" +
					"window.location.href='" + req.getContextPath() +
					"/JSP/login.jsp' </script>");
		} else {
		    session.setAttribute("user", user);
			resp.getWriter().write("<script>alert('��ӭ��½');" +
					"window.location.href='" + req.getContextPath() +
					"/JSP/home.jsp' </script>");
		}


	}
}
