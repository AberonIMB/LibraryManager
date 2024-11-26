package org.example.service;

import org.example.model.Book;
import org.example.DAO.BookDAO;
import org.example.util.Printer;
import org.example.util.SyntaxChecker;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

/**
 * Класс для работы с библеотекой, который объединяет другие сервсисы для удобного расширения
 */
public class LibraryService {

    private final BookService bookService;

    private final SessionFactory factory;

    private final SyntaxChecker syntaxChecker;

    /**
     * Конструктор для создания других сервисов и присваивании им SessionFactory
     */
    public LibraryService(Printer printer) {
        factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Book.class)
                .buildSessionFactory();
        BookDAO bookDAO = new BookDAO(factory);
        bookService = new BookService(bookDAO, printer);
        syntaxChecker = new SyntaxChecker();
    }

    /**
     * Добавить книгу в библиотеку.
     */
    public void addBook(String title, String author, int publicationYear) {
        bookService.addBook(title, author, publicationYear);
    }

    /**
     * Редактировать книгу по id
     */
    public void editBook(Long id, String title, String author, int publicationYear) {
        bookService.editBook(id, title, author, publicationYear);
    }

    /**
     * Удалить книгу из библиотеки.
     */
    public void deleteBook(Long id) {
        bookService.deleteBook(id);
    }

    /**
     * Получить список всех книг
     */
    public List<Book> getListBooks() {
        return bookService.getListBooks();
    }

    /**
     * Получить SyntaxChecker
     * @return SyntaxChecker
     */
    public SyntaxChecker getSyntaxChecker() {
        return syntaxChecker;
    }
}