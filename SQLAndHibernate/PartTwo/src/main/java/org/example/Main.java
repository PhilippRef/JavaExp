package org.example;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml").build();
        Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();
        SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<PurchaseList> query = builder.createQuery(PurchaseList.class);

        List<PurchaseList> list = session.createQuery(query).getResultList();

        for (PurchaseList lists : list) {
            LinkedPurchaseList linkedPurchaseList = new LinkedPurchaseList();
            LinkedPurchaseListKey key = new LinkedPurchaseListKey();

            key.setStudentId(Integer.toString(lists.getStudents().getId()));
            key.setCourseId(Integer.toString(lists.getCourses().getId()));
            linkedPurchaseList.setId(key);

            session.persist(linkedPurchaseList);
        }
        transaction.commit();
        sessionFactory.close();
    }
}
