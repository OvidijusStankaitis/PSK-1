package com.psk.autoproject.dao.jpa;

import com.psk.autoproject.entity.Car;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class CarJpaDao {

    @PersistenceContext
    private EntityManager em;

    public List<Car> findAll() {
        return em.createQuery("SELECT c FROM Car c", Car.class).getResultList();
    }

    public Car findById(Long id) {
        return em.find(Car.class, id);
    }

    @Transactional
    public void save(Car car) {
        if (car.getId() == null) {
            em.persist(car);
        } else {
            em.merge(car);
        }
    }
}
