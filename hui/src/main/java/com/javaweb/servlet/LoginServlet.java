package com.javaweb.servlet;

import com.javaweb.dao.CarDao;
import com.javaweb.pojo.Car;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 5338132271240559757L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out=response.getWriter();

        String username=request.getParameter("username");
        String password=request.getParameter("password");

        HttpSession session=request.getSession();
        CarDao dao=new CarDao();
        Car user=dao.login(username,password);
        System.out.println(user);
        JSONObject jo=new JSONObject();
        if(user!=null){
            session.setAttribute("user",user);
            JsonConfig jsonConfig=new JsonConfig();
            //jsonConfig.setRootClass(Car.class);
            jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
            jo= (JSONObject)JSONSerializer.toJSON(user,jsonConfig);
        }else{
            jo.put("code",400);
            jo.put("message","错误的用户名和密码");
        }
out.println(jo.toString());
        out.flush();
        out.close();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
