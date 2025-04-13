package com.psk.autoproject.jsf;

import com.psk.autoproject.entity.Feature;
import com.psk.autoproject.service.FeatureService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class FeatureBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private FeatureService featureService;

    private List<Feature> features;
    private Feature newFeature = new Feature();
    private Feature selectedFeature;

    @PostConstruct
    public void init() {
        features = featureService.findAll();
    }

    public void saveFeature() {
        featureService.save(newFeature);
        newFeature = new Feature();
        features = featureService.findAll();
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
}
