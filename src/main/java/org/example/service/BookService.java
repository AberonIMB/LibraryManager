package org.example.service;

import org.example.model.Book;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

import java.util.*;

/**
 * Класс для работы с библиотекой, который включает в себя
 * добавление, удаление, редактирование, получение и просмотр всех книг
 */
public class BookService {

    private final SessionFactory factory;

    /**
     * Конструктор, в котором задаётся конфигурация SessionFactory для Hibernate
     */
    public BookService() {
        factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Book.class)
                .buildSessionFactory();
    }

    /**
     * Создать книгу и сохранить книгу
     */
    public void addBook(String title, String author, int publicationYear) {
        Book book = new Book(title, author, publicationYear);
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            session.persist(book);
            session.getTransaction().commit();
        }
        System.out.println("Добавлена книга");
        System.out.println(book.getBookInfo());
    }

    /**
     * Получить список всех книг
     */
    public List<Book> getListBooks() {
        List<Book> books;
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            books = session.createQuery("from Book", Book.class).getResultList();
            session.getTransaction().commit();
        }
        return books;
    }

    /**
     * Редактировать книгу по id
     */
    public void editBook(int id, String title, String author, int publicationYear) {
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            Book book = session.get(Book.class, id);
            if (book == null) {
                System.out.println("Не найдено книги с данным ID");
                session.getTransaction().commit();
                return;
            }
            book.setNewData(title, author, publicationYear);
            session.merge(book);
            session.getTransaction().commit();
            System.out.println("Изменена книга:");
            System.out.println(book.getBookInfo());
        }
    }

    /**
     * Удалить книгу по id
     */
    public void deleteBook(int id) {
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            Book book = session.get(Book.class, id);
            if (book == null) {
                System.out.println("Книга не найдена");
                session.getTransaction().commit();
                return;
            }
            session.remove(book);
            session.getTransaction().commit();
            System.out.printf("Книга с ID %d успешно удалена\n", id);
        }
    }

        /**
         * Получить и вывести книгу по id
         */
    public Book getBook(int id) {
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            Book book = session.get(Book.class, id);
            session.getTransaction().commit();

            if (book == null) {
                System.out.printf("Книга c ID %d не найдена:", id);
                return null;
            }
            System.out.println("Книга с ID:");
            System.out.println(book.getBookInfo());
            return book;
        }
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
}
