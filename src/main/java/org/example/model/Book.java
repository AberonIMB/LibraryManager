package org.example.model;

/**
 * Класс книги
 */
public class Book {
    /**
     * Идентификатор книги
     */
    private final int id;
    /**
     * Название книги
     */
    private String title;
    /**
     * Автор книги
     */
    private String author;
    /**
     * Год издания книги
     */
    private int publicationYear;

    /**
     * Конструктор класса, который принимает название, автора, год издания и id книги
     */
    public Book(String title, String author, int publicationYear, int id) {
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.id = id;
    }

    /**
     * Получить id книги
     */
    public int getId() {
        return id;
    }

    /**
     * Получить название книги
     */
    public String getTitle() {
        return title;
    }

    /**
     * Получить автора книги
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Получить год издания книги
     */
    public int getPublicationYear() {
        return publicationYear;
    }

    /**
     * Установить новые значения для назвавния, автора и года публикации книги
     */
    public void setNewData(String title, String author, int publicationYear) {
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
    }

    /**
     * Вывод полной информации о книге
     */
    public void printBook() {
        System.out.printf("""
                        ID: %d
                        Название: %s
                        Автор: %s
                        Год издания: %d
                        """,
                id, title, author, publicationYear);
    }

    /**
     * Возвращает краткую информуцию о книге
     */
    public String getBookShortInfo() {
        return String.format("[%d] %s - %s (%d)", id, title, author, publicationYear);
    }
}