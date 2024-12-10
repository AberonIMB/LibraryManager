package org.example.dao;

import org.example.model.Book;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;
import java.util.function.Consumer;

/**
 * DAO класс книг для доступа к данным
 */
public class BookDAO {
    private final SessionFactory factory;

    /**
     * Конструктор, задающий SessionFactory
     */
    public BookDAO(SessionFactory factory) {
        this.factory = factory;
    }

    /**
     * Сохраняет книгу в базе данных
     */
    public void save(Book book) {
        doInTransaction(session -> session.persist(book));
    }

    /**
     * Получает список всех книг из базы данных
     */
    public List<Book> getAll() {
        try (Session session = factory.openSession()) {
            return session.createQuery("from Book", Book.class).getResultList();
        }
    }

    /**
     * Получает книгу по её ID
     */
    public Book getById(Long id) {
        try (Session session = factory.openSession()) {
            return session.get(Book.class, id);
        }
    }

    /**
     * Обновляет данные существующей книги в базе данных
     */
    public void update(Book book) {
        doInTransaction(session -> session.merge(book));

    }

    /**
     * Удаляет книгу из базы данных по ID
     */
    public void deleteBook(Book book) {
        doInTransaction(session -> session.remove(book));
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

