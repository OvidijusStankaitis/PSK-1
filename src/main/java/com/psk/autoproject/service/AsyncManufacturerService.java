package com.psk.autoproject.service;

import com.psk.autoproject.entity.Manufacturer;
import jakarta.ejb.AsyncResult;
import jakarta.ejb.Asynchronous;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.concurrent.Future;

@Stateless
public class AsyncManufacturerService {

    @PersistenceContext
    private EntityManager em;

    @Asynchronous
    public Future<Integer> calculateTotalCarsAsync() {
        try {
            Thread.sleep(3000);
            Long totalCars = em.createQuery(
                    "SELECT COUNT(c) FROM Car c", Long.class
            ).getSingleResult();
            return new AsyncResult<>(totalCars.intValue());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return new AsyncResult<>(0);
        }
    }
}
