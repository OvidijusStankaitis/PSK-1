package com.psk.autoproject.dao.mybatis;

import com.psk.autoproject.entity.Manufacturer;
import com.psk.autoproject.mybatis.ManufacturerMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class ManufacturerMyBatisDao {

    @Inject
    private ManufacturerMapper manufacturerMapper;

    public List<Manufacturer> findAll() {
        return manufacturerMapper.findAll();
    }

    public Manufacturer findById(Long id) {
        return manufacturerMapper.findById(id);
    }

    @Transactional
    public void save(Manufacturer manufacturer) {
        // Insert vs update logic could go here if needed.
        // For now, we call insert(...) always.
        manufacturerMapper.insert(manufacturer);
    }
}
