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
            session.beginTransaction();
            session.persist(book);
            session.getTransaction().commit();
        }
    }

    /**
     * Получает список всех книг из базы данных
     */
    public List<Book> getAll() {
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            List<Book> books = session.createQuery("from Book", Book.class).getResultList();
            session.getTransaction().commit();
            return books;
        }
    }

    /**
     * Получает книгу по её ID
     */
    public Book getById(Long id) {
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            Book book = session.get(Book.class, id);
            session.getTransaction().commit();
            return book;
        }
    }

    /**
     * Обновляет данные существующей книги в базе данных
     */
    public void update(Book book) {
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            session.merge(book);
            session.getTransaction().commit();
        }
    }


    /**
     * Удаляет книгу из базы данных по ID
     */
    public void deleteBook(Long id) {
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            Book book = session.get(Book.class, id);
            if (book != null) {
                session.remove(book);
            }
            session.getTransaction().commit();
        }
    }
}

