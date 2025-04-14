package com.psk.autoproject.dao.jpa;

import com.psk.autoproject.entity.Customer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class CustomerJpaDao {

    @PersistenceContext
    private EntityManager em;

    public List<Customer> findAll() {
        return em.createQuery("SELECT c FROM Customer c", Customer.class).getResultList();
    }

    public Customer findById(Long id) {
        return em.find(Customer.class, id);
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
