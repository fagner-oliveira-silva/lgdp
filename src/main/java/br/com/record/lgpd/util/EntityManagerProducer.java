package br.com.record.lgpd.util;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Disposes;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.Produces;

import org.glassfish.jersey.process.internal.RequestScoped;

@ApplicationScoped
public class EntityManagerProducer {

    @Inject
    private PersistenceProperties properties;
    
    private EntityManagerFactory factory;

    @PostConstruct
    public void postConstruct() {
        this.factory = Persistence.createEntityManagerFactory("PU", properties.get());
    }

    @Produces
    @RequestScoped
    public EntityManager createEntityManager() {
        return this.factory.createEntityManager();
    }

    public void closeEntityManager(@Disposes EntityManager manager) {
        manager.close();
    }
}