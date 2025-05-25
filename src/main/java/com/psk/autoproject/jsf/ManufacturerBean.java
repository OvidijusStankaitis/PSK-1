package com.psk.autoproject.jsf;

import com.psk.autoproject.entity.Manufacturer;
import com.psk.autoproject.service.ManufacturerService;
import com.psk.autoproject.service.AsyncManufacturerService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.Future;

@Named
@ViewScoped
public class ManufacturerBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private ManufacturerService manufacturerService;

    @Inject
    private AsyncManufacturerService asyncManufacturerService;

    private List<Manufacturer> manufacturers;
    private Manufacturer newManufacturer = new Manufacturer();
    private Manufacturer selectedManufacturer;

    private Future<Integer> totalCarsCalculation;
    private Integer totalCars;
    private boolean calculatingTotalCars = false;

    @PostConstruct
    public void init() {
        manufacturers = manufacturerService.findAllWithCars();
    }

    public void saveManufacturer() {
        manufacturerService.save(newManufacturer);
        newManufacturer = new Manufacturer();
        manufacturers = manufacturerService.findAllWithCars();
    }

    public void deleteManufacturer(Manufacturer manufacturer) {
        manufacturerService.delete(manufacturer);
        manufacturers = manufacturerService.findAllWithCars();
        clearAsyncResults();
    }

    public void startTotalCarsCalculation() {
        if (!calculatingTotalCars) {
            calculatingTotalCars = true;
            totalCars = null;
            totalCarsCalculation = asyncManufacturerService.calculateTotalCarsAsync();
        }
    }

    public void checkAsyncResults() {
        if (calculatingTotalCars && totalCarsCalculation != null && totalCarsCalculation.isDone()) {
            try {
                totalCars = totalCarsCalculation.get();
                calculatingTotalCars = false;
            } catch (Exception e) {
                calculatingTotalCars = false;
                totalCars = -1;
            }
        }
    }

    private void clearAsyncResults() {
        totalCars = null;
        calculatingTotalCars = false;
    }

    // Getters and setters
    public List<Manufacturer> getManufacturers() {
        return manufacturers;
    }

    public Manufacturer getNewManufacturer() {
        return newManufacturer;
    }

    public void setNewManufacturer(Manufacturer newManufacturer) {
        this.newManufacturer = newManufacturer;
    }

    public Manufacturer getSelectedManufacturer() {
        return selectedManufacturer;
    }

    public void setSelectedManufacturer(Manufacturer selectedManufacturer) {
        this.selectedManufacturer = selectedManufacturer;
    }

    public Integer getTotalCars() {
        return totalCars;
    }

    public boolean isCalculatingTotalCars() {
        return calculatingTotalCars;
    }

    public Future<Integer> getTotalCarsCalculation() {
        return totalCarsCalculation;
    }
}
