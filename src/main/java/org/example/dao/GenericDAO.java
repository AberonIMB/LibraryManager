package org.example.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;
import java.util.function.Consumer;

/**
 * Обощенный DAO класс для работы с сущностями
 */
public class GenericDAO<T> {

    private final SessionFactory factory;
    private final Class<T> entityClass;

    /**
     * Констурктор, задающий SessionFactory и класс сущности, для которой создается DAO
     */
    public GenericDAO(SessionFactory factory, Class<T> entityClass) {
        this.factory = factory;
        this.entityClass = entityClass;
    }

    /**
     * Сохраняет сущность в базе данных
     */
    public void save(T entity) {
        doInTransaction(session -> session.persist(entity));
    }

    /**
     * Получает список всех сущностей одного типа из базы данных
     */
    public List<T> getAll() {
        try (Session session = factory.openSession()) {
            return session.createQuery("from " + entityClass.getSimpleName(), entityClass).getResultList();
        }
    }

    /**
     * Получает сущность по её ID
     */
    public T getById(Long id) {
        try (Session session = factory.openSession()) {
            return session.get(entityClass, id);
        }
    }

    /**
     * Обновляет данные сущности в базе данных
     */
    public void update(T entity) {
        doInTransaction(session -> session.merge(entity));
    }

    /**
     * Удаляет сущность из базы данных по ID
     */
    public void delete(T entity) {
        doInTransaction(session -> session.remove(entity));
    }

    /**
     * Выполнить действие в транзакции
     * <p>Открывает сессию на время выполнения Consumer'а
     * и выполняет Hibernate транзакцию правильным образом (открытие, затем коммит
     * или откат при исключении)</p>
     */
    public void doInTransaction(Consumer<Session> consumer) {
        try (Session session = factory.getCurrentSession()) {
            final Transaction transaction = session.beginTransaction();
            try {
                consumer.accept(session);
                transaction.commit();
            } catch (final Exception e) {
                transaction.rollback();
                throw new RuntimeException("Во время выполнения транзакции произошла ошибка", e);
            }
        }
    }
}