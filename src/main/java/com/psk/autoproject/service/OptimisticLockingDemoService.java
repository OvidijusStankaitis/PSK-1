package com.psk.autoproject.service;

import com.psk.autoproject.entity.Car;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.OptimisticLockException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.logging.Logger;

@RequestScoped
public class OptimisticLockingDemoService {

    private static final Logger logger = Logger.getLogger(OptimisticLockingDemoService.class.getName());

    @PersistenceContext
    private EntityManager em;
    
    @Transactional
    public String demonstrateOptimisticLocking(Long carId, String newModel, String conflictingModel) {
        try {
            Car car1 = em.find(Car.class, carId);
            if (car1 == null) {
                return "Car not found with ID: " + carId;
            }

            logger.info("Transaction 1: Loaded car with version: " + car1.getVersion());

            simulateConcurrentModification(carId, conflictingModel);

            String originalModel = car1.getModel();
            car1.setModel(newModel);

            logger.info("Transaction 1: About to flush changes...");
            em.flush();

            return "SUCCESS: Car updated successfully (this shouldn't happen in demo)";

        } catch (OptimisticLockException ole) {
            logger.info("OptimisticLockException caught as expected!");

            return handleOptimisticLockException(ole, carId, newModel);
        }
    }

    private String handleOptimisticLockException(OptimisticLockException ole, Long carId, String newModel) {
        StringBuilder result = new StringBuilder();
        result.append("OptimisticLockException occurred as expected!\n\n");
        try {
            Car detachedCar = (Car) ole.getEntity();
            if (detachedCar != null) {
                result.append("Detached entity info:\n");
                result.append(" - Entity ID: ").append(detachedCar.getId()).append("\n");
                result.append(" - Entity model: ").append(detachedCar.getModel()).append("\n");
                result.append(" - Entity version: ").append(detachedCar.getVersion()).append("\n");
                result.append(" - Entity is detached: ").append(!em.contains(detachedCar)).append("\n\n");
            }
        } catch (Exception e) {
            result.append("Could not access detached entity details\n\n");
        }

        result.append("To recover: Start a new transaction, reload the entity, and reapply changes\n");

        return result.toString();
    }

    @Transactional
    public String retryAfterOptimisticLockException(Long carId, String newModel) {
        try {
            Car car = em.find(Car.class, carId);
            if (car == null) {
                return "Car not found with ID: " + carId;
            }

            logger.info("Retry: Loaded fresh car with version: " + car.getVersion());

            String oldModel = car.getModel();
            car.setModel(newModel);
            em.flush();

            return String.format("SUCCESS: Car model updated from '%s' to '%s' (version: %d)",
                    oldModel, newModel, car.getVersion());

        } catch (OptimisticLockException ole2) {
            return "OptimisticLockException occurred again during retry. Another concurrent modification detected.";
        }
    }

    private void simulateConcurrentModification(Long carId, String conflictingModel) {
        try {
            logger.info("Simulating concurrent modification...");

            int updated = em.createNativeQuery(
                            "UPDATE cars SET model = :model, version = version + 1 WHERE id = :id")
                    .setParameter("model", conflictingModel)
                    .setParameter("id", carId)
                    .executeUpdate();

            if (updated > 0) {
                logger.info("Concurrent modification completed: updated model to " + conflictingModel);
            }

        } catch (Exception e) {
            logger.warning("Failed to simulate concurrent modification: " + e.getMessage());
        }
    }

    public Car getCarDetails(Long carId) {
        return em.find(Car.class, carId);
    }
}