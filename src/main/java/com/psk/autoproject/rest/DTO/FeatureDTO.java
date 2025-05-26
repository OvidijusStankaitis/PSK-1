package com.psk.autoproject.rest.DTO;

import com.psk.autoproject.entity.Feature;

public class FeatureDTO {
    public Long id;
    public String name;

    public FeatureDTO(Feature feature) {
        this.id = feature.getId();
        this.name = feature.getName();
    }
}

