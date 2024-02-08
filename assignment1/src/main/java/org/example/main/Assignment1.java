package org.example.main;

import org.example.beans.Person;
import org.example.beans.Vehicle;
import org.example.config.ProjectConfig;
import org.example.interfaces.Speakers;
import org.example.interfaces.Tyres;
import org.example.services.VehicleServices;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Assignment1 {
    // @Autowired
    public static void main(String[] args) {

        var context = new AnnotationConfigApplicationContext(ProjectConfig.class);
        Person person = context.getBean(Person.class);
        System.out.println("Person name from Spring Context is: " + person.getName());
        System.out.println("Vehicle that Person own is: " + person.getVehicle().getVehicleName());
        String[] persons = context.getBeanNamesForType(Person.class);
        String[] vehicles = context.getBeanNamesForType(Vehicle.class);

        Vehicle vehicle = context.getBean(Vehicle.class);
        vehicle.getVehicleServices().playMusic();// person.getVehicle().getVehicleServices().playMusic();
        vehicle.getVehicleServices().moveVehicle();// person.getVehicle().getVehicleServices().moveVehicle();
        vehicle.getVehicleServices().getSpeakers();

    }
}
