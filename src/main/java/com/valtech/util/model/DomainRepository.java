package com.valtech.util.model;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class DomainRepository {
    @Autowired
    private EntityManager entityManager;

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public Session getHibernateSession() {
        return getEntityManager().unwrap(Session.class);
    }
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
