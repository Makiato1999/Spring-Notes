package org.example.beans;

import org.example.services.VehicleServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("vehicleBean")
public class Vehicle {
    private VehicleServices vehicleServices;
    private String vehicleName = "Audi Q3";

    @Autowired
    public Vehicle(VehicleServices vehicleServices) {
        System.out.println("Vehicle has been created by Spring");
        this.vehicleServices = vehicleServices;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public VehicleServices getVehicleServices() {
        return vehicleServices;
    }

    public void setVehicleServices(VehicleServices vehicleServices) {
        this.vehicleServices = vehicleServices;
    }
}
