package com.javaweb.servlet;

import com.javaweb.dao.CarDao;
import com.javaweb.pojo.Car;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class ChangePwdServlet extends HttpServlet {
    private static final long serialVersionUID = 7583555641867692565L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out=response.getWriter();
        HttpSession session=request.getSession();
       Car car =(Car) session.getAttribute("user");
       String username=car.getName();

        String password=request.getParameter("password");
        String newpassword=request.getParameter("newpassword");
        //System.out.println(username);
        CarDao dao=new CarDao();

        if(password.equals(newpassword)){
            out.println("新密码和原密码相同，请修改");
            response.setHeader("refresh","2;url=changepwd.html");
        }else{
            int count=0;
            count=dao.modify(username,password,newpassword);
            if (count>0){
                out.println("密码修改成功");
                response.setHeader("refresh","2;url=index.html");
            }else{
                out.println("用户名或者密码错误");
                response.setHeader("refresh","2;url=changepwd.html");
            }

        }

        out.flush();
        out.close();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
