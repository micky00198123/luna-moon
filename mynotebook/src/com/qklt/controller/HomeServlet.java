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

        //��ȡurl-pattern : /html/����.do
        String servletPath = req.getServletPath();
        //ȥ��/��/ǰ�������ַ�
        int lastCharIndex = servletPath.lastIndexOf("/");
        String methodName = servletPath.substring(lastCharIndex + 1);
        //ȥ��.do
        methodName = methodName.substring(0, methodName.length() - 3);

        //ͨ������,һ��Servlet��Ӧ�������
        Method method;
        try {
            //�����ȡmethodName��Ӧ�ķ���
            method = this.getClass().getDeclaredMethod(
                    methodName, HttpServletRequest.class,
                    HttpServletResponse.class);
            //������ö�Ӧ�ķ���
            method.invoke(this, req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // �޸���Ϣ
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
            resp.getWriter().write("<script>alert('����������,ע��ʧ��');");
        }

    }

    // ע���û�
    private void logout(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        session.removeAttribute("user");
    }

    // �ϴ�ͼƬ
    private void uploadImg(HttpServletRequest req, HttpServletResponse resp) {

        // �ļ��洢Ŀ¼
        String dir = "img";
        // �ļ��洢Ŀ¼����·��(��ʱʹ���Զ���λ��)
        String uploadPath = req.getServletContext().getRealPath("/") + dir + "\\";

        // ���Ŀ¼�������򴴽�
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        // �����ϴ�����
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        // ���Ĵ���
        upload.setHeaderEncoding("UTF-8");

        String fileName = null;

        boolean isStore = false;
        try {
            // ���������������ȡ�ļ�����
            List<FileItem> formItems = upload.parseRequest(req);
            if (formItems != null && formItems.size() > 0) {
                for (FileItem item : formItems) {
                    // �����ڱ��е��ֶ�
                    if (!item.isFormField()) {
                        // ��������λ�ַ�
                        String randomStr = ServiceUtils.getRandomString(8);
                        // �ļ���
                        fileName = randomStr + new File(item.getName()).getName();
                        // �ļ�����·��
                        String filePath = uploadPath + fileName;
                        File storeFile = new File(filePath);
                        // �����ļ���Ӳ��
                        item.write(storeFile);
                        isStore = true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(isStore) {
            // ���ݿ���ͷ��
            User user = (User)req.getSession().getAttribute("user");
            String oldName = user.getPortrait();
            // ��ɾ����ͷ��
            if(hs.delOldPortrait(uploadPath + oldName, oldName)) {
                // ����ͷ��
                user = hs.uploadPortrait(user, fileName);
                req.getSession().setAttribute("user", user);
            }
        }


    }




}