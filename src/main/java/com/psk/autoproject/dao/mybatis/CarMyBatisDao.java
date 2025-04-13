package com.psk.autoproject.dao.mybatis;

import com.psk.autoproject.entity.Car;
import com.psk.autoproject.mybatis.CarMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class CarMyBatisDao {

    @Inject
    private CarMapper carMapper;

    public List<Car> findAll() {
        return carMapper.findAll();
    }

    public Car findById(Long id) {
        return carMapper.findById(id);
    }

    @Transactional
    public void save(Car car) {
        carMapper.insert(car);
    }
}
