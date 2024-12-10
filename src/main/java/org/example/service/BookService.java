package org.example.service;

import org.example.model.Book;
import org.example.dao.BookDAO;

import java.util.*;

/**
 * Класс для работы с книгами в библиотеки, который включает в себя
 * добавление, удаление, редактирование, получение и просмотр всех книг
 */
public class BookService {

    private final BookDAO bookDAO;

    /**
     * Конструктор, в котором присваивается bookDAO
     */
    public BookService(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    /**
     * Сохранить книгу
     */
    public void addBook(Book book) {
        bookDAO.save(book);
    }

    /**
     * Получить список всех книг
     */
    public List<Book> getListBooks() {
        return bookDAO.getAll();
    }

    /**
     * Редактировать книгу по id
     */
    public void editBook(Book book, String title, String author, int publicationYear) {
        if (book != null) {
            book.setNewData(title, author, publicationYear);
            bookDAO.update(book);
        }
    }

    /**
     * Удалить книгу по id
     */
    public void deleteBook(Book book) {
        if (book != null) {
            bookDAO.deleteBook(book);
        }
    }

    /**
     * Получить книгу по id
     */
    public Book getBook(Long id) {
        return bookDAO.getById(id);
    }
}