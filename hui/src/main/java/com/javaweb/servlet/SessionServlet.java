package com.javaweb.servlet;

import com.javaweb.pojo.Car;
import net.sf.json.JSON;
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

public class SessionServlet extends HttpServlet {
    private static final long serialVersionUID = -8669304908599706329L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out=response.getWriter();

        HttpSession session=request.getSession();
        Car user=(Car)session.getAttribute("user");
        JsonConfig jc=new JsonConfig();
        jc.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
        JSON jo=JSONSerializer.toJSON(user,jc);
        out.println(jo);

        out.flush();
        out.close();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
