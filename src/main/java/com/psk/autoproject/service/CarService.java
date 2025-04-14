package com.psk.autoproject.service;

import com.psk.autoproject.entity.Car;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

@RequestScoped
public class CarService {

    @PersistenceContext
    private EntityManager em;

    public List<Car> findAll() {
        return em.createQuery("SELECT c FROM Car c", Car.class).getResultList();
    }

    public List<Car> findAllWithFeatures() {
        return em.createQuery(
                "SELECT DISTINCT c FROM Car c LEFT JOIN FETCH c.features",
                Car.class
        ).getResultList();
    }

    public Car findById(Long id) {
        return em.find(Car.class, id);
    }

    public Car findByIdWithOwners(Long id) {
        return em.createQuery(
                        "SELECT c FROM Car c LEFT JOIN FETCH c.owners WHERE c.id = :id",
                        Car.class
                )
                .setParameter("id", id)
                .getSingleResult();
    }

    public Car findByIdWithFeatures(Long id) {
        return em.createQuery(
                        "SELECT c FROM Car c LEFT JOIN FETCH c.features WHERE c.id = :id",
                        Car.class
                )
                .setParameter("id", id)
                .getSingleResult();
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
