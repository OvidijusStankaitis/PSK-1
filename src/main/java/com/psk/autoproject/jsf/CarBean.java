package com.psk.autoproject.jsf;

import com.psk.autoproject.entity.Car;
import com.psk.autoproject.service.CarService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class CarBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private CarService carService;

    private List<Car> cars;
    private Car newCar = new Car();
    private Car selectedCar;

    @PostConstruct
    public void init() {
        // Instead of just carService.findAll(), load with features:
        cars = carService.findAllWithFeatures();
    }

    public void saveCar() {
        carService.save(newCar);
        newCar = new Car();
        // Re-load to ensure lazy data is fetched again:
        cars = carService.findAllWithFeatures();
    }

    // Getters/Setters
    public List<Car> getCars() {
        return cars;
    }

    public Car getNewCar() {
        return newCar;
    }

    public void setNewCar(Car newCar) {
        this.newCar = newCar;
    }

    public Car getSelectedCar() {
        return selectedCar;
    }

    public void setSelectedCar(Car selectedCar) {
        this.selectedCar = selectedCar;
    }
}
