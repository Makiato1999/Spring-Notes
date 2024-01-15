package com.example.config;

import com.example.beans.Vehicle;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;


@Configuration
public class ProjectConfig {
    /*
    @Bean annotation lets Spring know that it needs to call this method
    when it initializes its context and adds the returned object/value
    at the Spring context/Springloc Container.
     */
    @Bean(value = "audiVehicle")
    Vehicle vehicle1() {
        var veh = new Vehicle();
        veh.setName("Audi 8");
        return veh;
    }

    @Bean(value = "hondaVehicle")
    Vehicle vehicle2() {
        var veh = new Vehicle();
        veh.setName("Honda crv");
        return veh;
    }

    @Bean(value = "bmwVehicle")
    Vehicle vehicle3() {
        var veh = new Vehicle();
        veh.setName("BMW x1");
        return veh;
    }
}
