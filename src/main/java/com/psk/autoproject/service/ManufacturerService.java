package com.psk.autoproject.service;

import com.psk.autoproject.entity.Car;
import com.psk.autoproject.entity.Customer;
import com.psk.autoproject.entity.Manufacturer;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.HashSet;
import java.util.List;

@RequestScoped
public class ManufacturerService {

    @PersistenceContext
    private EntityManager em;

    public List<Manufacturer> findAll() {
        return em.createQuery("SELECT m FROM Manufacturer m", Manufacturer.class).getResultList();
    }

    public List<Manufacturer> findAllWithCars() {
        return em.createQuery(
                "SELECT DISTINCT m FROM Manufacturer m LEFT JOIN FETCH m.cars",
                Manufacturer.class
        ).getResultList();
    }

    public Manufacturer findById(Long id) {
        return em.find(Manufacturer.class, id);
    }

    @Transactional
    public void save(Manufacturer manufacturer) {
        if (manufacturer.getId() == null) {
            em.persist(manufacturer);
        } else {
            em.merge(manufacturer);
        }
    }

    @Transactional
    public void delete(Manufacturer manufacturer) {
        Manufacturer managed = em.merge(manufacturer);
        for (Car car : new HashSet<>(managed.getCars())) {
            for (Customer owner : new HashSet<>(car.getOwners())) {
                owner.getCars().remove(car);
            }
            car.getOwners().clear();
            managed.getCars().remove(car);
            em.remove(car);
        }
        em.remove(managed);
    }
}
