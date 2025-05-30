package com.psk.autoproject.service;

import com.psk.autoproject.entity.Feature;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

@RequestScoped
public class FeatureService {

    @PersistenceContext
    private EntityManager em;

    public List<Feature> findAll() {
        return em.createQuery("SELECT f FROM Feature f", Feature.class)
                .getResultList();
    }

    public List<Feature> findAllWithCars() {
        return em.createQuery(
                "SELECT DISTINCT f FROM Feature f LEFT JOIN FETCH f.cars",
                Feature.class
        ).getResultList();
    }

    public Feature findById(Long id) {
        return em.find(Feature.class, id);
    }

    public Feature findByIdWithCars(Long id) {
        return em.createQuery(
                        "SELECT f FROM Feature f LEFT JOIN FETCH f.cars WHERE f.id = :id",
                        Feature.class
                ).setParameter("id", id)
                .getSingleResult();
    }

    @Transactional
    public void save(Feature feature) {
        if (feature.getId() == null) {
            em.persist(feature);
        } else {
            em.merge(feature);
        }
    }
}
