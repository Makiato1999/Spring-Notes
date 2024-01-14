package com.example.main;

import com.example.beans.Vehicle;
import com.example.config.ProjectConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Example1 {
    public static void main(String[] args) {
        Vehicle vehicle = new Vehicle();
        vehicle.setName("BMW 3");
        System.out.println("Vehicle name from non-spring context is: "+vehicle.getName());
        /*
        the new name "BMW" is not tracked by spring context, so nothing will change in Bean
         */

        var context = new AnnotationConfigApplicationContext(ProjectConfig.class);
        Vehicle veh = context.getBean(Vehicle.class);
        System.out.println("Vehicle name from spring context is "+veh.getName());
        String hello = context.getBean(String.class);
        System.out.println("String value from spring context is "+hello);
        Integer integer = context.getBean(Integer.class);
        System.out.println("Integer value from spring context is "+integer);
    }
}
