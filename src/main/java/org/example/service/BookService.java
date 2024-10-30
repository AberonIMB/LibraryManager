package org.example.service;

import org.example.model.Book;

import java.util.List;

/**
 * Класс для работы с библиотекой, который включает в себя
 * добавление, удаление, редактирование, получение и просмотр всех книг
 */
public class BookService {

    /**
     * Создать книгу и сохранить книгу
     * @param title
     * @param author
     * @param publicationYear
     */
    public void addBook(String title, String author, int publicationYear) {
        //TODO
    }

    /**
     * Получить список всех книг
     * @return
     */
    public List<Book> getListBooks() {
        return null;
        //TODO
    }

    /**
     * Редактировать книгу по id
     * @param id
     * @param title
     * @param author
     * @param publicationYear
     */
    public void editBook(int id, String title, String author, int publicationYear) {
        //TODO
    }

    /**
     * Удалить книгу по id
     * @param id
     */
    public void deleteBook(int id) {
        //TODO
    }

    /**
     * Получить книгу по id
     * @param id
     * @return
     */
    public Book getBook(int id) {
        return null;
        //TODO
    }

    /**
     * Получить следующий свободный идентификатор
     * @return
     */
    private int fingFreeId() {
        return 0;
        //TODO
    }
}