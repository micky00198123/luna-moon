package com.qklt.controller;

import com.qklt.domain.User;
import com.qklt.service.Impl.HomeServiceImpl;
import com.qklt.util.ServiceUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

public class HomeServlet extends HttpServlet {

    private HomeServiceImpl hs = new HomeServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        this.getMethod(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        this.getMethod(req, resp);
    }

    private void getMethod(HttpServletRequest req, HttpServletResponse resp) {
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");

        //获取url-pattern : /html/方法.do
        String servletPath = req.getServletPath();
        //去掉/及/前的所有字符
        int lastCharIndex = servletPath.lastIndexOf("/");
        String methodName = servletPath.substring(lastCharIndex + 1);
        //去掉.do
        methodName = methodName.substring(0, methodName.length() - 3);

        //通过反射,一个Servlet对应多个请求
        Method method;
        try {
            //反射获取methodName对应的方法
            method = this.getClass().getDeclaredMethod(
                    methodName, HttpServletRequest.class,
                    HttpServletResponse.class);
            //反射调用对应的方法
            method.invoke(this, req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 修改信息
    private void setting(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String sex = req.getParameter("setSex");
        String age = req.getParameter("setAge");
        sex = new String (sex.getBytes("iso8859-1"),"UTF-8");
        age = new String (age.getBytes("iso8859-1"),"UTF-8");

        HttpSession session = req.getSession();
        User user = (User)session.getAttribute("user");

        user.setSex(sex);
        user.setAge(age);

        if(hs.updateInformation(user)) {
            session.setAttribute("user", user);
            resp.getWriter().write("<script>window.location.href='" +
                    req.getContextPath() + "/JSP/home.jsp' </script>");
        } else {
            resp.getWriter().write("<script>alert('服务器错误,注册失败');");
        }

    }

    // 注销用户
    private void logout(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        session.removeAttribute("user");
    }

    // 上传图片
    private void uploadImg(HttpServletRequest req, HttpServletResponse resp) {

        // 文件存储目录
        String dir = "img";
        // 文件存储目录绝对路径(暂时使用自定义位置)
        String uploadPath = req.getServletContext().getRealPath("/") + dir + "\\";

        // 如果目录不存在则创建
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        // 配置上传参数
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        // 中文处理
        upload.setHeaderEncoding("UTF-8");

        String fileName = null;

        boolean isStore = false;
        try {
            // 解析请求的内容提取文件数据
            List<FileItem> formItems = upload.parseRequest(req);
            if (formItems != null && formItems.size() > 0) {
                for (FileItem item : formItems) {
                    // 处理不在表单中的字段
                    if (!item.isFormField()) {
                        // 防重名八位字符
                        String randomStr = ServiceUtils.getRandomString(8);
                        // 文件名
                        fileName = randomStr + new File(item.getName()).getName();
                        // 文件绝对路径
                        String filePath = uploadPath + fileName;
                        File storeFile = new File(filePath);
                        // 保存文件到硬盘
                        item.write(storeFile);
                        isStore = true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(isStore) {
            // 数据库变更头像
            User user = (User)req.getSession().getAttribute("user");
            String oldName = user.getPortrait();
            // 先删除旧头像
            if(hs.delOldPortrait(uploadPath + oldName, oldName)) {
                // 换新头像
                user = hs.uploadPortrait(user, fileName);
                req.getSession().setAttribute("user", user);
            }
        }


    }




}