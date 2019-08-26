package com.qklt.controller;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class HomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");

        String methodCase = req.getParameter("case");
        if(methodCase == null) {
            return;
        }
        if("logout".equals(methodCase)) {
            this.logout(req, resp);
            return;
        }

    }

    private void logout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // ×¢ÏúÓÃ»§
        HttpSession session = req.getSession();
        session.removeAttribute("user");
    }

}