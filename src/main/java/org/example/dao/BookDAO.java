package org.example.dao;

import org.example.model.Book;
import org.hibernate.SessionFactory;

/**
 * DAO класс книг для доступа к данным
 */
public class BookDAO extends GenericDAO<Book> {

    /**
     * Конструктор, задающий SessionFactory
     */
    public BookDAO(SessionFactory factory) {
        super(factory, Book.class);
    }
}