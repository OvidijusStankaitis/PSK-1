package com.psk.autoproject.jsf;

import com.psk.autoproject.entity.Car;
import com.psk.autoproject.entity.Manufacturer;
import com.psk.autoproject.service.CarService;
import com.psk.autoproject.service.ManufacturerService;
import com.psk.autoproject.service.OptimisticLockingDemoService;
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

    @Inject
    private OptimisticLockingDemoService optimisticLockingDemoService;

    private List<Car> cars;
    private List<Manufacturer> manufacturers;
    private Car newCar = new Car();
    private Car selectedCar;

    private Long selectedManufacturerId;

    // Demo 1: Optimistic locking
    private Long demoCarId;
    private String demoNewModel;
    private String demoConflictingModel;
    private String demoResult;
    private boolean demoInProgress = false;

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
        newCar = new Car();
        selectedManufacturerId = null;
        cars = carService.findAll();
        return null;
    }

    public void runOptimisticLockingDemo() {
        if (demoCarId == null) {
            demoResult = "Please select a car for the demo";
            return;
        }

        if (demoNewModel == null || demoNewModel.trim().isEmpty()) {
            demoResult = "Please enter a new model name";
            return;
        }

        if (demoConflictingModel == null || demoConflictingModel.trim().isEmpty()) {
            demoResult = "Please enter a conflicting model name";
            return;
        }

        demoInProgress = true;
        try {
            demoResult = optimisticLockingDemoService.demonstrateOptimisticLocking(
                    demoCarId, demoNewModel, demoConflictingModel);
        } finally {
            demoInProgress = false;
            cars = carService.findAll();
        }
    }

    public void retryAfterOptimisticLock() {
        if (demoCarId == null || demoNewModel == null || demoNewModel.trim().isEmpty()) {
            demoResult = "Please set car ID and new model name first";
            return;
        }

        try {
            String retryResult = optimisticLockingDemoService.retryAfterOptimisticLockException(
                    demoCarId, demoNewModel);
            demoResult = demoResult + "\n\nRETRY RESULT:\n" + retryResult;

            cars = carService.findAll();
        } catch (Exception e) {
            demoResult = demoResult + "\n\nRETRY FAILED: " + e.getMessage();
        }
    }

    public void resetDemo() {
        demoCarId = null;
        demoNewModel = null;
        demoConflictingModel = null;
        demoResult = null;
        demoInProgress = false;

        cars = carService.findAll();
    }

    // Getters
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

    public Long getDemoCarId() {
        return demoCarId;
    }

    public void setDemoCarId(Long demoCarId) {
        this.demoCarId = demoCarId;
    }

    public String getDemoNewModel() {
        return demoNewModel;
    }

    public void setDemoNewModel(String demoNewModel) {
        this.demoNewModel = demoNewModel;
    }

    public String getDemoConflictingModel() {
        return demoConflictingModel;
    }

    public void setDemoConflictingModel(String demoConflictingModel) {
        this.demoConflictingModel = demoConflictingModel;
    }

    public String getDemoResult() {
        return demoResult;
    }

    public boolean isDemoInProgress() {
        return demoInProgress;
    }
}
