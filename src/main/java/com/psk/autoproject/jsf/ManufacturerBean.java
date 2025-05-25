package com.psk.autoproject.jsf;

import com.psk.autoproject.entity.Manufacturer;
import com.psk.autoproject.service.ManufacturerService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class ManufacturerBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private ManufacturerService manufacturerService;

    private List<Manufacturer> manufacturers;
    private Manufacturer newManufacturer = new Manufacturer();
    private Manufacturer selectedManufacturer;

    @PostConstruct
    public void init() {
        // Instead of "findAll()", we now call "findAllWithCars()".
        manufacturers = manufacturerService.findAllWithCars();
    }

    public void saveManufacturer() {
        manufacturerService.save(newManufacturer);
        newManufacturer = new Manufacturer();
        manufacturers = manufacturerService.findAllWithCars(); // reload
    }

    public void deleteManufacturer(Manufacturer manufacturer) {
        manufacturerService.delete(manufacturer);
        manufacturers = manufacturerService.findAllWithCars(); // reload
    }

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
}
