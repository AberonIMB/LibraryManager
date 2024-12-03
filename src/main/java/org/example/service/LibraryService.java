package org.example.service;

import org.example.dao.ReaderDAO;
import org.example.model.Book;
import org.example.dao.BookDAO;
import org.example.model.Reader;
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

    private final ReaderService readerService;

    private final SessionFactory factory;

    private final SyntaxChecker syntaxChecker;

    /**
     * Конструктор для создания других сервисов и присваивании им SessionFactory
     */
    public LibraryService(Printer printer) {
        factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Book.class)
                .addAnnotatedClass(Reader.class)
                .buildSessionFactory();
        BookDAO bookDAO = new BookDAO(factory);
        ReaderDAO readerDAO = new ReaderDAO(factory);
        bookService = new BookService(bookDAO, printer);
        readerService = new ReaderService(readerDAO, printer);
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

    /**
     * Добавить читателя в библиотеку.
     */
    public void addReader(String name) {
        readerService.addReader(name);
    }

    /**
     * Редактировать книгу по id
     */
    public void editReader(Long id, String name) {
        readerService.editReader(id, name);
    }

    /**
     * Удалить читателя
     */
    public void deleteReader(long id) {
        readerService.deleteReader(id);
    }

//    /**
//     * Получить читателя
//     */
//    public Reader getReader(Long id) {
//        return readerService.getReader(id);
//    }

    /**
     * Вывести информацию о читателе
     */
    public void showReader(long id) {
        readerService.showReader(id);
    }

    /**
     * Получить список читателей
     */
    public List<Reader> getReaderList() {
        return readerService.getListReaders();
    }

    /**
     * Выдать книгу читателю
     */
    public void checkoutBook(long bookID, long readerID) {
        Reader reader = readerService.getReader(readerID);
        if (reader == null) {
            return;
        }
        bookService.checkoutBook(bookID, reader);
    }

    /**
     * Вернуть книгу
     */
    public void returnBook(long bookID) {
        bookService.returnBook(bookID);
    }
}