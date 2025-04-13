package com.psk.autoproject.mybatis;

import com.psk.autoproject.entity.Manufacturer;
import org.mybatis.cdi.Mapper;

import java.util.List;

@Mapper
public interface ManufacturerMapper {
    List<Manufacturer> findAll();
    Manufacturer findById(Long id);
    void insert(Manufacturer manufacturer);
}
