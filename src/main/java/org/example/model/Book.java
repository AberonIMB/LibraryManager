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
     * @param title
     * @param author
     * @param publicationYear
     * @param id
     */
    public Book(String title, String author, int publicationYear, int id) {
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.id = id;
    }

    /**
     * Получить id книги
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * Получить название книги
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     * Получить автора книги
     * @return
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Получить год издания книги
     * @return
     */
    public int getPublicationYear() {
        return publicationYear;
    }

    /**
     * Установить новые значения для назвавния, автора и года публикации книги
     * @param title
     * @param author
     * @param publicationYear
     */
    public void setNewData(String title, String author, int publicationYear) {
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
    }

    //        /**
//     * Метод для удобного вывода информации о книге
//     * @return
//     */
//    @Override
//    public String toString() {
//        return "ID: " + id +
//                "\nНазвание: " + title +
//                "\nАвтор: " + author +
//                "\nГод издания: " + publicationYear;
//    }
}