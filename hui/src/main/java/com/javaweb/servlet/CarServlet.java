package com.javaweb.servlet;

import com.google.gson.JsonSerializer;
import com.javaweb.dao.CarDao;
import com.javaweb.pojo.Car;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CarServlet extends HttpServlet {
    private static final long serialVersionUID = 5432256061074887933L;

    /**
     * 查询所有
     * url:http://127.0.0.1.8086/javaweb/CarServlet-get
     * 根据id查询 http://127.0.0.1.8086/javaweb/CarServlet?id=10 -get
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out=response.getWriter();

        JsonConfig jsonConfig=new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());

        JSON json=null;
        CarDao dao=new CarDao();
        String id=request.getParameter("id");
        if (id==null||"".equals(id)){
            List<Car> cars=dao.find();
            json= JSONSerializer.toJSON(cars,jsonConfig);
        }else{
            Car car=dao.find(Integer.parseInt(id));
            json=JSONSerializer.toJSON(car,jsonConfig);
        }
        out.println(json.toString());
        out.flush();
        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out=response.getWriter();

        String name=request.getParameter("name");
        Double price=Double.parseDouble(request.getParameter("price"));
        String screatedate=request.getParameter("createDate");
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Date cretatedate=new Date();
        try {
            cretatedate=sdf.parse(screatedate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Car car=new Car();
        car.setName(name);
        car.setPrice(price);
        car.setCreateDate(cretatedate);
        int count=new CarDao().add(car);

        JSONObject jo=new JSONObject();
        jo.put("count",count);
        out.println(jo.toString());

        out.flush();
        out.close();
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out=response.getWriter();

        Integer id=Integer.parseInt(request.getParameter("id"));
        String name=request.getParameter("name");
        Double price=Double.parseDouble(request.getParameter("price"));
        String screatedate=request.getParameter("createDate");
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Date cretatedate=new Date();
        try {
            cretatedate=sdf.parse(screatedate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Car car=new Car();
        car.setId(id);
        car.setName(name);
        car.setPrice(price);
        car.setCreateDate(cretatedate);

        int count=new CarDao().modify(car);
        JSONObject jo=new JSONObject();
        jo.put("count",count);
        out.println(jo.toString());

        out.flush();
        out.close();
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out=response.getWriter();

        Integer id=Integer.parseInt(request.getParameter("id"));
        int count=new CarDao().remove(id);
        JSONObject jo=new JSONObject();
        jo.put("count",count);
        out.println(jo.toString());

        out.flush();
        out.close();
    }
}
