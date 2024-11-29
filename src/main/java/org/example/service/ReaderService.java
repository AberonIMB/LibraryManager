package org.example.service;


import org.example.DAO.ReaderDAO;
import org.example.model.Reader;
import org.example.util.Printer;

import java.util.List;

public class ReaderService {
    private final ReaderDAO readerDAO;
    private final Printer printer;

    /**
     * Конструктор, в котором присваивается readerDAO и printer
     */
    public ReaderService(ReaderDAO readerDAO, Printer printer) {
        this.readerDAO = readerDAO;
        this.printer = printer;
    }

    /**
     * Создать читателя и сохранить его
     */
    public void addReader(String name) {
        //TODO
    }

    /**
     * Получить список всех читателей
     */
    public List<Reader> getListReaders() {
        //TODO;
        return null;
    }

    /**
     * Редактировать читателя по id
     */
    public void editReader(Long id, String name) {
        //TODO
    }

    /**
     * Удалить читателя по id
     */
    public void deleteReader(Long id) {
        //TODO;
    }

    /**
     * Получить читателя
     */
    public Reader getReader(Long id) {
        //TODO
        return null;
    }
}
