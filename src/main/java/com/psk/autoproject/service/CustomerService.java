package com.psk.autoproject.service;

import com.psk.autoproject.entity.Customer;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;

@RequestScoped
public class CustomerService {

    @PersistenceContext
    private EntityManager em;

    public List<Customer> findAll() {
        return em.createQuery("SELECT c FROM Customer c", Customer.class)
                .getResultList();
    }

    // New method: load all customers together with their cars eagerly.
    public List<Customer> findAllWithCars() {
        return em.createQuery(
                "SELECT DISTINCT c FROM Customer c LEFT JOIN FETCH c.cars",
                Customer.class
        ).getResultList();
    }

    public Customer findById(Long id) {
        return em.find(Customer.class, id);
    }

    // New method: load a Customer together with its cars eagerly.
    public Customer findByIdWithCars(Long id) {
        return em.createQuery(
                        "SELECT c FROM Customer c LEFT JOIN FETCH c.cars WHERE c.id = :id",
                        Customer.class
                ).setParameter("id", id)
                .getSingleResult();
    }

    @Transactional
    public void save(Customer customer) {
        if (customer.getId() == null) {
            em.persist(customer);
        } else {
            em.merge(customer);
        }
    }
}
