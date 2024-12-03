package org.example.dao;

import org.example.model.Reader;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

/**
 * DAO класс читателей для доступа к данным
 */
public class ReaderDAO {

    private final SessionFactory factory;

    /**
     * Конструктор, задающий SessionFactory
     */
    public ReaderDAO(SessionFactory factory) {
        this.factory = factory;
    }

    /**
     * Сохраняет читателя в базе данных
     */
    public void save(Reader reader) {
        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(reader);
            transaction.commit();
        }
    }

    /**
     * Получает список всех читателей из базы данных
     */
    public List<Reader> getAll() {
        try (Session session = factory.openSession()) {
            return session.createQuery("from Reader", Reader.class).getResultList();
        }
    }

    /**
     * Получает читателя по его ID
     */
    public Reader getById(Long id) {
        try (Session session = factory.openSession()) {
            return session.get(Reader.class, id);
        }
    }

    /**
     * Обновляет данные существующего читателя в базе данных
     */
    public void update(Reader reader) {
        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.merge(reader);
            transaction.commit();
        }
    }

    /**
     * Удаляет книгу из базы данных по ID
     */
    public void deleteReader(Reader reader) {
        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.remove(reader);
            transaction.commit();
        }
    }
}
