package org.example.service;

import org.example.model.Book;
import org.example.DAO.BookDAO;
import org.example.util.Printer;

import java.util.*;

/**
 * Класс для работы с книгами в библиотеки, который включает в себя
 * добавление, удаление, редактирование, получение и просмотр всех книг
 */
public class BookService {

    private final BookDAO bookDAO;
    private final Printer printer;

    /**
     * Конструктор, в котором присваивается bookDAO и printer
     */
    public BookService(BookDAO bookDAO, Printer printer) {
        this.bookDAO = bookDAO;
        this.printer = printer;
    }

    /**
     * Создать книгу и сохранить книгу
     */
    public void addBook(String title, String author, int publicationYear) {
        Book book = new Book(title, author, publicationYear);
        bookDAO.save(book);
        printer.printBookAdded(book);
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
    public void editBook(int id, String title, String author, int publicationYear) {
        Book book = bookDAO.getById(id);
        if (book == null) {
            printer.printBookNotFound(id);
            return;
        }
        book.setNewData(title, author, publicationYear);
        bookDAO.update(book);
        printer.printBookEdited(book);
    }

    /**
     * Удалить книгу по id
     */
    public void deleteBook(int id) {
        Book book = bookDAO.getById(id);
        if (book == null) {
            printer.printBookNotFound(id);
            return;
        }
        bookDAO.deleteBook(id);
        printer.printBookDeleted(id);
    }

    /**
     * Получить и вывести книгу по id
     */
    public Book getBook(int id) {
        Book book = bookDAO.getById(id);
        if (book == null) {
            printer.printBookNotFound(id);
        } else {
            printer.printBookInfo(book);
        }
        return book;
    }

    /**
     * Вывести спискок книг
     */
    public void printBooks() {
        List<Book> bookList = getListBooks();
        printer.printBookList(bookList);
    }
}