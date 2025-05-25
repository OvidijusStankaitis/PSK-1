package com.psk.autoproject.service;

import com.psk.autoproject.entity.Customer;
import com.psk.autoproject.dao.mybatis.CustomerMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class CustomerService {

    @Inject
    private CustomerMapper customerMapper;

    @PersistenceContext
    private EntityManager em;

    public List<Customer> findAll() {
        return customerMapper.findAll();
    }

    // Restores the old behavior for fetching customers with their cars
    public List<Customer> findAllWithCars() {
        return em.createQuery(
                "SELECT DISTINCT c FROM Customer c LEFT JOIN FETCH c.cars",
                Customer.class
        ).getResultList();
    }

    public Customer findById(Long id) {
        return customerMapper.findById(id);
    }

    @Transactional
    public void save(Customer customer) {
        if (customer.getId() == null) {
            customerMapper.insert(customer);
        } else {
            customerMapper.update(customer);
        }
    }
}
