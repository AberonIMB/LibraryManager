package org.example.util;

import org.example.model.Book;

import java.util.List;

/**
 * Класс для вывода информации в консоль
 */
public class Printer {

    /**
     * Выводит сообщение об ошибке: ID и год публикации должны быть представлены числом
     */
    public void printNotNumberError() {
        System.out.println("ID и год публикации должны быть представлены числом.");
    }

    /**
     * Выводит сообщение об ошибке из-за неверного количества аргументов
     */
    public void printNotEnoughArgsError(int argsCount, int expectedArgsCount) {
        System.out.printf("Неверное количество аргументов команды: должно быть %d, представлено %d.\n",
                expectedArgsCount,
                argsCount);
    }

    /**
     * Выводит сообщение об ошибке при вводе несуществующей команды
     */
    public void printWrongCommand() {
        System.out.println("Введена несуществующая команда.");
    }

    /**
     * Выводит справку по всем доступным командам
     */
    public void printHelp() {
        System.out.println("""
                Доступные команды:
                \t- add-book "<название>" "<автор>" <год издания> – Добавить книгу
                \t- list-books – Просмотреть список книг
                \t- edit-book <ID книги> “<название>” “<автор>” <год издания> - Изменить книгу
                \t- delete-book <ID книги> – Удалить книгу
                \t- help – Справка""");
    }

    /**
     * Выводит список всех книг
     */
    public void printBookList(List<Book> books) {
        if (books.isEmpty()) {
            System.out.println("Список книг пуст.");
            return;
        }
        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            System.out.printf("%d) %s\n", i + 1, book.getBookShortInfo());
        }
    }

    /**
     * Выводит информацию о добавленной книге
     */
    public void printBookAdded(Book book) {
        System.out.println("Добавлена книга:");
        System.out.println(book.getBookInfo());
    }

    /**
     * Выводит информацию об измененной книге
     */
    public void printBookEdited(Book book) {
        System.out.println("Изменена книга:");
        System.out.println(book.getBookInfo());
    }

    /**
     * Выводит сообщение об успешном удалении книги
     */
    public void printBookDeleted(int id) {
        System.out.printf("Книга с ID %d успешно удалена.\n", id);
    }

    /**
     * Выводит сообщение, если книга с указанным ID не найдена
     */
    public void printBookNotFound(int id) {
        System.out.printf("Книга с ID %d не найдена.\n", id);
    }

    /**
     * Выводит информацию о найденной книге
     */
    public void printBookInfo(Book book) {
        System.out.println("Книга с ID:");
        System.out.println(book.getBookInfo());
    }
}