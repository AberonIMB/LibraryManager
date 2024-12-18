package org.example.service;


import org.example.dao.ReaderDAO;
import org.example.model.Reader;

import java.util.List;

public class ReaderService {
    private final ReaderDAO readerDAO;

    /**
     * Конструктор, в котором присваивается readerDAO и printer
     */
    public ReaderService(ReaderDAO readerDAO) {
        this.readerDAO = readerDAO;
    }

    /**
     * Создать читателя и сохранить его
     */
    public void addReader(Reader reader) {
        readerDAO.save(reader);
    }

    /**
     * Получить список всех читателей
     */
    public List<Reader> getListReaders() {
        return readerDAO.getAll();
    }

    /**
     * Редактировать читателя по id
     */
    public void editReader(Reader reader, String name) {
        reader.setName(name);
        readerDAO.update(reader);
    }

    /**
     * Удалить читателя по id
     */
    public void deleteReader(Reader reader) {
        readerDAO.deleteReader(reader);
    }

    /**
     * Получить читателя
     */
    public Reader getReader(Long id) {
        return readerDAO.getById(id);
    }
}