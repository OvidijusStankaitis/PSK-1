package com.psk.autoproject.dao.mybatis;

import com.psk.autoproject.entity.Customer;
import com.psk.autoproject.mybatis.CustomerMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class CustomerMyBatisDao {

    @Inject
    private CustomerMapper customerMapper;

    public List<Customer> findAll() {
        return customerMapper.findAll();
    }

    public Customer findById(Long id) {
        return customerMapper.findById(id);
    }

    @Transactional
    public void save(Customer customer) {
        customerMapper.insert(customer);
    }
}
