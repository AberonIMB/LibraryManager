package org.example.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Класс читателя
 */
@Entity
@Table(name = "readers")

public class Reader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "reader", fetch = FetchType.EAGER)
    private List<Book> books;

    /**
     * Пустой конструктор для Hibernate
     */
    public Reader() {
    }

    /**
     * Конструктор класса, который принимает название, автора, год издания
     */
    public Reader(String name) {
        this.name = name;
        books = new ArrayList<>();
    }

    /**
     * Получить id
     */
    public Long getId() {
        return id;
    }

    /**
     * Получить имя
     */
    public String getName() {
        return name;
    }

    /**
     * Получить взятые книги
     */
    public List<Book> getBooks() {
        return books;
    }

    /**
     * Установить новые новое значение имени
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Возвращает информуцию о читателе в полном виде
     */
    public String getReaderInfo() {
        StringBuilder booksString = new StringBuilder();

        if (books.isEmpty()) {
            booksString.append("Нет выданных книг");
        } else {
            for (Book book : books) {
                booksString.append("\n\t%s".formatted(book.getBookShortInfo()));
            }
        }

        return String.format("""
                        ID: %d
                        ФИО: %s
                        Выданные книги: %s""",
                id, name, booksString);
    }

    /**
     * Возвращает информуцию о читателе в кратком виде
     */
    public String getReaderShortInfo() {
        return String.format("ID: %d ФИО: %s", id, name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reader reader = (Reader) o;
        return Objects.equals(id, reader.id) && Objects.equals(name, reader.name) && Objects.equals(books, reader.books);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, books);
    }
}
