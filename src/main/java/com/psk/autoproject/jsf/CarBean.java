package com.psk.autoproject.jsf;

import com.psk.autoproject.entity.Car;
import com.psk.autoproject.entity.Manufacturer;
import com.psk.autoproject.service.CarService;
import com.psk.autoproject.service.ManufacturerService;
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

    @Inject
    private ManufacturerService manufacturerService;

    private List<Car> cars;
    private List<Manufacturer> manufacturers;
    private Car newCar = new Car();
    private Car selectedCar;

    private Long selectedManufacturerId;

    @PostConstruct
    public void init() {
        cars = carService.findAll();
        manufacturers = manufacturerService.findAll();
    }

    public String saveCar() {
        if (selectedManufacturerId != null) {
            Manufacturer manufacturer = manufacturerService.findById(selectedManufacturerId);
            newCar.setManufacturer(manufacturer);
        }
        carService.save(newCar);
        newCar = new Car(); // reset the form
        selectedManufacturerId = null; // reset the selected manufacturer
        cars = carService.findAll();
        return null; // stay on the same page
    }

    // Getters & Setters
    public List<Car> getCars() {
        return cars;
    }

    public List<Manufacturer> getManufacturers() {
        return manufacturers;
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

    public Long getSelectedManufacturerId() {
        return selectedManufacturerId;
    }

    public void setSelectedManufacturerId(Long selectedManufacturerId) {
        this.selectedManufacturerId = selectedManufacturerId;
    }
}
