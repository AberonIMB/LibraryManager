package org.example.DAO;

import org.example.model.Book;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

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
        try (Session session = factory.getCurrentSession()) {
            session.persist(book);
        }
    }

    /**
     * Получает список всех книг из базы данных
     */
    public List<Book> getAll() {
        try (Session session = factory.getCurrentSession()) {
            return session.createQuery("from Book", Book.class).getResultList();
        }
    }

    /**
     * Получает книгу по её ID
     */
    public Book getById(Long id) {
        try (Session session = factory.getCurrentSession()) {
            return session.get(Book.class, id);
        }
    }

    /**
     * Обновляет данные существующей книги в базе данных
     */
    public void update(Book book) {
        try (Session session = factory.getCurrentSession()) {
            session.merge(book);
        }
    }

    /**
     * Удаляет книгу из базы данных по ID
     */
    public void deleteBook(Book book) {
        try (Session session = factory.getCurrentSession()) {
            session.remove(book);
        }
    }
}

