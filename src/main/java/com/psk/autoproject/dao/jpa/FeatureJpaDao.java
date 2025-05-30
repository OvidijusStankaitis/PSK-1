package com.psk.autoproject.dao.jpa;

import com.psk.autoproject.entity.Feature;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class FeatureJpaDao {

    @PersistenceContext
    private EntityManager em;

    public List<Feature> findAll() {
        return em.createQuery("SELECT f FROM Feature f", Feature.class).getResultList();
    }

    public Feature findById(Long id) {
        return em.find(Feature.class, id);
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
