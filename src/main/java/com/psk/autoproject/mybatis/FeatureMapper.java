package com.psk.autoproject.mybatis;

import com.psk.autoproject.entity.Feature;
import org.mybatis.cdi.Mapper;

import java.util.List;

@Mapper
public interface FeatureMapper {
    List<Feature> findAll();

    Feature findById(Long id);

    void insert(Feature feature);
}
