package com.example.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.ejb.HibernateEntityManager;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class TransactionTokenMapper {

    private HibernateEntityManager entityManager;

    public TransactionTokenMapper() throws HibernateException {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("persistence-test");
        entityManager = entityManagerFactory.createEntityManager().unwrap(HibernateEntityManager.class);
    }

    public void startTransaction() {
        entityManager.getTransaction().begin();
    }

    public void commitTransaction() {
        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().commit();
        }
    }

    public void rollbackTransaction() {
        entityManager.getTransaction().rollback();
    }

    public TransactionToken getById(long id) {
        return entityManager.find(TransactionToken.class, id);
    }

    public void insert(TransactionToken record) {
        entityManager.persist(record);
    }

    public long count() {
        return (Long) entityManager.createQuery("select count(*) as counter from TransactionToken").getSingleResult();
    }

    public void delete(TransactionToken token) {
        entityManager.remove(token);
        entityManager.flush();
    }

    public void deleteByTransaction(TransactionToken token) {
        entityManager.createQuery("delete from TransactionToken where transaction = :trans_id").setParameter("trans_id", token.getTransaction()).executeUpdate();
    }

    public void update(TransactionToken record) {
        entityManager.merge(record);
    }

    public TransactionToken selectByTransaction(String transaction) {
        return (TransactionToken) entityManager.createQuery("select t from TransactionToken t where t.transaction = :trans_id").setParameter("trans_id", transaction).getSingleResult();
    }
}
