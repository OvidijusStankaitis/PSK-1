package com.psk.autoproject.service;

import com.psk.autoproject.entity.Car;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.OptimisticLockException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.logging.Logger;

@RequestScoped
public class CarService {

    private static final Logger logger = Logger.getLogger(CarService.class.getName());

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

    @Transactional
    public String saveWithOptimisticLockHandling(Car car) {
        try {
            if (car.getId() == null) {
                em.persist(car);
                return "Car created successfully";
            } else {
                Car merged = em.merge(car);
                em.flush();
                return String.format("Car updated successfully (new version: %d)", merged.getVersion());
            }
        } catch (OptimisticLockException ole) {
            logger.info("OptimisticLockException caught during save operation");

            Car detachedCar = (Car) ole.getEntity();
            if (detachedCar != null) {
                logger.info("Conflicted car - ID: " + detachedCar.getId() +
                        ", Version: " + detachedCar.getVersion());
            }

            return "Save failed due to concurrent modification. Please reload and try again.";
        }
    }

    @Transactional
    public String updateCarWithVersionCheck(Long carId, String newModel, Long expectedVersion) {
        try {
            Car car = em.find(Car.class, carId);
            if (car == null) {
                return "Car not found";
            }

            if (!car.getVersion().equals(expectedVersion)) {
                return String.format("Car was modified by another user. Expected version: %d, Current version: %d",
                        expectedVersion, car.getVersion());
            }

            String oldModel = car.getModel();
            car.setModel(newModel);
            em.flush();

            return String.format("Car updated successfully: '%s' -> '%s' (version: %d)",
                    oldModel, newModel, car.getVersion());

        } catch (OptimisticLockException ole) {
            return "Update failed due to concurrent modification detected by JPA";
        }
    }

    public Car getCarWithVersion(Long carId) {
        return em.createQuery(
                        "SELECT c FROM Car c WHERE c.id = :id", Car.class)
                .setParameter("id", carId)
                .getSingleResult();
    }
}