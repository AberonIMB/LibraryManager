package org.example.service;

import org.example.dao.BookDAO;
import org.example.model.Book;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

/**
 * Класс для работы с библеотекой, который объединяет другие сервсисы для удобного расширения
 */
public class LibraryService {

    private final BookService bookService;

    /**
     * Конструктор для создания других сервисов и присваивании им SessionFactory
     */
    public LibraryService() {
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Book.class)
                .buildSessionFactory();
        BookDAO bookDAO = new BookDAO(factory);
        bookService = new BookService(bookDAO);
    }

    /**
     * Конструктор для тестирования без поднятия базы данных
     */
    public LibraryService(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * Добавить книгу в библиотеку.
     */
    public Book addBook(String title, String author, int publicationYear) {
        Book book = createBook(title, author, publicationYear);
        bookService.addBook(book);

        return book;
    }

    /**
     * Редактировать книгу по id
     */
    public Book editBook(Long id, String title, String author, int publicationYear) {
        Book book = getBookById(id);

        if (book != null) {
            book.setNewData(title, author, publicationYear);
            bookService.editBook(book);
        }

        return book;
    }

    /**
     * Удалить книгу из библиотеки.
     */
    public Book deleteBook(Long id) {
        Book book = getBookById(id);

        if (book != null) {
            bookService.deleteBook(book);
        }

        return book;
    }

    /**
     * Получить список всех книг
     */
    public List<Book> getListBooks() {
        return bookService.getListBooks();
    }

    /**
     * Получает книгу по id
     */
    private Book getBookById(Long id) {
        return bookService.getBook(id);
    }

    /**
     * Создает книгу
     */
    private Book createBook(String title, String author, int publicationYear) {
        return new Book(title, author, publicationYear);
    }
}