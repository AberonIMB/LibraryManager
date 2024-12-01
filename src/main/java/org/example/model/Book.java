package org.example.model;

import jakarta.persistence.*;

import java.util.Objects;

/**
 * Класс книги
 */
@Entity
@Table(name = "books")
public class Book {

    /**
     * Идентификатор книги
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Название книги
     */
    @Column(name = "title")
    private String title;

    /**
     * Автор книги
     */
    @Column(name = "author")
    private String author;

    /**
     * Год издания книги
     */
    @Column(name = "publication_year")
    private int publicationYear;

    /**
     * Пустой конструктор для Hibernate
     */
    public Book() {
    }

    /**
     * Конструктор класса, который принимает название, автора, год издания
     */
    public Book(String title, String author, int publicationYear) {
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
    }

    /**
     * Получить id книги
     */
    public Long getId() {
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
     * Возвращает информацию о книге
     */
    public String getBookInfo() {
        return String.format("""
                        ID: %d
                        Название: %s
                        Автор: %s
                        Год издания: %d
                        Статус: %s""",
                id, title, author, publicationYear, reader == null ? "в библиотеке" : "выдана");
    }

    /**
     * Возвращает информуцию о книге в кратком виде для просмотра в списке книг читателя
     */
    public String getBookShortInfoForReaderList() {
        return String.format("[%d] %s - %s (%d)", id, title, author, publicationYear);
    }

    public String getBookShortInfo() {
        return String.format("[%d] %s - %s (%d) - %s", id, title, author, publicationYear,
                reader == null ? "в библиотеке" : "выдана");
    }
    /**
     * Сравнивает этот объект с другим объектом на равенство
     * @return true - если эти объекты равны
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id);
    }

    /**
     * Возвращает hashCode объекта
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}