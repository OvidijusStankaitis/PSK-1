package com.psk.autoproject.dao.jpa;

import com.psk.autoproject.entity.Manufacturer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class ManufacturerJpaDao {

    @PersistenceContext
    private EntityManager em;

    public List<Manufacturer> findAll() {
        return em.createQuery("SELECT m FROM Manufacturer m", Manufacturer.class).getResultList();
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
}
