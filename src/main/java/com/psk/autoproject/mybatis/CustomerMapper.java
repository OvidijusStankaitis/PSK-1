package com.psk.autoproject.mybatis;

import com.psk.autoproject.entity.Customer;
import org.mybatis.cdi.Mapper;

import java.util.List;

@Mapper
public interface CustomerMapper {
    List<Customer> findAll();

    Customer findById(Long id);

    void insert(Customer customer);
}
