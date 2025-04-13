package com.psk.autoproject.mybatis;

import com.psk.autoproject.entity.Car;
import org.mybatis.cdi.Mapper;

import java.util.List;

@Mapper
public interface CarMapper {
    List<Car> findAll();
    Car findById(Long id);
    void insert(Car car);
}
