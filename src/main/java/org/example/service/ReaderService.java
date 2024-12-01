package org.example.service;


import org.example.dao.ReaderDAO;
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
        Reader reader = new Reader(name);
        readerDAO.save(reader);
        printer.printReaderAdded(reader);
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
    public void editReader(Long id, String name) {
        Reader reader = getReader(id);
        if (reader != null) {
            reader.setName(name);
            readerDAO.update(reader);
            printer.printReaderEdited(reader);
        }
    }

    /**
     * Удалить читателя по id
     */
    public void deleteReader(Long id) {
        Reader reader = getReader(id);
        if (reader == null) {
            return;
        }
        if (!reader.getBooks().isEmpty()) {
            printer.printReaderDeleteWithNotEmptyBooks(reader);
            return;
        }
        readerDAO.deleteReader(reader);
        printer.printReaderDeleted(reader);
    }

    /**
     * Вывести информацию о читателе
     */
    public void showReader(Long id) {
        Reader reader = getReader(id);
        if (reader != null) {
            printer.printReaderInfo(reader);
        }
    }

    /**
     * Получить читателя
     */
    public Reader getReader(Long id) {
        Reader reader = readerDAO.getById(id);
        if (reader == null) {
            printer.printReaderNotFound(id);
        }

        return reader;
    }
}