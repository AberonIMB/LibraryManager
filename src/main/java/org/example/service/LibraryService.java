package org.example.service;

import org.example.dao.BookDAO;
import org.example.dao.ReaderDAO;
import org.example.model.Book;
import org.example.model.Reader;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

/**
 * Класс для работы с библеотекой, который объединяет другие сервсисы для удобного расширения
 */
public class LibraryService {

    private final BookService bookService;
    private final ReaderService readerService;

    /**
     * Конструктор для создания других сервисов и присваивании им SessionFactory
     */
    public LibraryService() {
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Book.class)
                .buildSessionFactory();
        BookDAO bookDAO = new BookDAO(factory);
        ReaderDAO readerDAO = new ReaderDAO(factory);
        bookService = new BookService(bookDAO);
        readerService = new ReaderService(readerDAO);
    }

    /**
     * Конструктор для тестирования без поднятия базы данных
     */
    public LibraryService(BookService bookService, ReaderService readerService) {
        this.bookService = bookService;
        this.readerService = readerService;
    }

    /**
     * Добавить книгу в библиотеку.
     */
    public Book addBook(String title, String author, int publicationYear) {
        Book book = new Book(title, author, publicationYear);
        bookService.addBook(book);

        return book;
    }

    /**
     * Редактировать книгу по id
     */
    public void editBook(Book book, String title, String author, int publicationYear) {
        book.setNewData(title, author, publicationYear);
        bookService.editBook(book);
    }

    /**
     * Удалить книгу из библиотеки.
     */
    public void deleteBook(Book book) {
        bookService.deleteBook(book);
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
    public Book getBookById(Long id) {
        return bookService.getBook(id);
    }

    /**
     * Получить читателя по id
     */
    public Reader getReaderById(Long id) {
        return readerService.getReader(id);
    }

    /**
     * Добавить читателя
     */
    public Reader addReader(String name) {
        Reader reader = new Reader(name);
        readerService.addReader(reader);
        return reader;
    }

    /**
     * Выдать книгу
     */
    public void checkoutBook(Book book, Reader reader) {
        book.setReader(reader);
        bookService.editBook(book);
    }

    /**
     * Удалить читателя
     */
    public void deleteReader(Reader reader) {
        readerService.deleteReader(reader);
    }

    /**
     * Редактировать читателя
     */
    public void editReader(Reader reader, String name) {
        reader.setName(name);
        readerService.editReader(reader);
    }

    /**
     * Получить список читателей
     */
    public List<Reader> getListReaders() {
        return readerService.getListReaders();
    }

    /**
     * Вернуть книгу в библиотеку
     */
    public void returnBook(Book book) {
        book.setReader(null);
        bookService.editBook(book);
    }
}