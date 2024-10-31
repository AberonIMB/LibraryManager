package org.example.service;

import org.example.model.Book;

import java.util.*;

/**
 * Класс для работы с библиотекой, который включает в себя
 * добавление, удаление, редактирование, получение и просмотр всех книг
 */
public class BookService {

    private final Map<Integer, Book> books = new LinkedHashMap<>();

    private Integer lastID = 0;

    /**
     * Создать книгу и сохранить книгу
     */
    public void addBook(String title, String author, int publicationYear) {
        int id = findFreeId();
        Book book = new Book(title, author, publicationYear, id);
        books.put(id, book);
        System.out.println("Добавлена книга:");
        book.printBook();
    }

    /**
     * Получить список всех книг
     */
    public List<Book> getListBooks() {
        return books.values()
                .stream()
                .toList();
    }

    /**
     * Редактировать книгу по id
     */
    public void editBook(int id, String title, String author, int publicationYear) {
        Book book = books.get(id);
        if (book == null) {
            System.out.println("Не найдено книги с данным ID\n");
            return;
        }
        book.setNewData(title, author, publicationYear);
        System.out.println("Изменена книга:");
        book.printBook();
    }

    /**
     * Удалить книгу по id
     */
    public void deleteBook(int id) {
        if (!books.containsKey(id)) {
            System.out.println("Книга не найдена\n");
            return;
        }
        books.remove(id);
        System.out.printf("Книга с ID %d успешно удалена\n", id);
    }

    /**
     * Получить и вывести книгу по id
     */
    public Book getBook(int id) {
        Book book = books.get(id);
        if (book == null) {
            System.out.printf("Книга c ID %d не найдена:", id);
            return null;
        }
        System.out.println("Добавлена книга:");
        book.printBook();
        return book;
    }

    /**
     * Вывод списка книг
     */
    public void printBooks() {
        List<Book> bookList = getListBooks();
        for (int i = 1; i <= bookList.size(); i++) {
            Book book = bookList.get(i - 1);
            System.out.printf("%d) %s\n", i, book.getBookShortInfo());
        }
    }

    /**
     * Получить следующий свободный идентификатор
     */
    private int findFreeId() {
        lastID++;
        return lastID;
    }
}