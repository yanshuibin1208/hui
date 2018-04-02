package com.javaweb.test;

import com.javaweb.dao.CarDao;
import com.javaweb.dao.DBUtil;
import com.javaweb.pojo.Car;
import org.junit.Test;

import java.util.Date;

public class CarDaoTest {

  @Test
  public void db() {
    System.out.println(DBUtil.getConnection());
  }

  @Test
  public void cars() {
    CarDao dao = new CarDao();
    for (Car car : dao.find()) {
      System.out.println(car.getName());
    }
  }

  @Test
  public void add() {
    CarDao dao = new CarDao();
    for (int i = 0; i <= 60; i++) {
      Car car = new Car();
      car.setName("car" + i);
      car.setPrice(1000d + i);
      car.setCreateDate(new Date());
      dao.add(car);
    }
  }
}
