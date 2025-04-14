package com.psk.autoproject.jsf;

import com.psk.autoproject.entity.Car;
import com.psk.autoproject.entity.Feature;
import com.psk.autoproject.service.CarService;
import com.psk.autoproject.service.FeatureService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;

@Named
@ViewScoped
public class FeatureBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private FeatureService featureService;

    @Inject
    private CarService carService;

    private List<Feature> features;
    private Feature newFeature = new Feature();
    private Feature selectedFeature;
    private Long selectedFeatureId;
    private Long selectedCarId;

    @PostConstruct
    public void init() {
        features = featureService.findAllWithCars();
    }

    public String saveFeature() {
        featureService.save(newFeature);
        newFeature = new Feature();
        features = featureService.findAllWithCars();
        return null;
    }

    public String addFeatureToCar() {
        if (selectedFeatureId == null || selectedCarId == null) {
            return null;
        }

        try {
            Feature feature = featureService.findByIdWithCars(selectedFeatureId);
            Car car = carService.findByIdWithFeatures(selectedCarId);

            if (feature != null && car != null) {
                if (car.getFeatures() == null) {
                    car.setFeatures(new HashSet<>());
                }
                if (feature.getCars() == null) {
                    feature.setCars(new HashSet<>());
                }
                car.getFeatures().add(feature);
                feature.getCars().add(car);
                carService.save(car);
                selectedFeatureId = null;
                selectedCarId = null;
                features = featureService.findAllWithCars();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Car> getAvailableCars() {
        return carService.findAll();
    }

    public List<Feature> getFeatures() {
        return features;
    }

    public Feature getNewFeature() {
        return newFeature;
    }

    public void setNewFeature(Feature newFeature) {
        this.newFeature = newFeature;
    }

    public Feature getSelectedFeature() {
        return selectedFeature;
    }

    public void setSelectedFeature(Feature selectedFeature) {
        this.selectedFeature = selectedFeature;
    }

    public Long getSelectedFeatureId() {
        return selectedFeatureId;
    }

    public void setSelectedFeatureId(Long selectedFeatureId) {
        this.selectedFeatureId = selectedFeatureId;
    }

    public Long getSelectedCarId() {
        return selectedCarId;
    }

    public void setSelectedCarId(Long selectedCarId) {
        this.selectedCarId = selectedCarId;
    }
}
