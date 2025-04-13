package com.psk.autoproject.dao.mybatis;

import com.psk.autoproject.entity.Feature;
import com.psk.autoproject.mybatis.FeatureMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class FeatureMyBatisDao {

    @Inject
    private FeatureMapper featureMapper;

    public List<Feature> findAll() {
        return featureMapper.findAll();
    }

    public Feature findById(Long id) {
        return featureMapper.findById(id);
    }

    @Transactional
    public void save(Feature feature) {
        featureMapper.insert(feature);
    }
}
