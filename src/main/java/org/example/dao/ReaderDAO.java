package org.example.dao;

import org.example.model.Reader;
import org.hibernate.SessionFactory;

/**
 * DAO класс читателей для доступа к данным
 */
public class ReaderDAO extends GenericDAO<Reader>{


    /**
     * Конструктор, задающий SessionFactory
     */
    public ReaderDAO(SessionFactory factory) {
        super(factory, Reader.class);
    }
}
