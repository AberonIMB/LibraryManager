package org.example.util;

import org.example.model.Book;
import org.example.model.Reader;

import java.util.List;

/**
 * Класс для вывода информации в консоль
 */
public class Printer {

    /**
     * Выводит сообщение об ошибке: ID должен быть представлен числом
     */
    public void printIdNotNumberError() {
        System.out.println("ID должен быть представлен числом.");
    }

    /**
     * Выводит сообщение об ошибке: Год публикации должен быть представлен числом
     */
    public void printYearNotNumberError() {
        System.out.println("Год публикации должен быть представлен числом.");
    }

    /**
     * Выводит сообщение об ошибке из-за неверного количества аргументов
     */
    public void printNotEnoughArgsError(int argsCount, int expectedArgsCount) {
        System.out.printf("Неверное количество аргументов команды: должно быть %d, представлено %d.\n", expectedArgsCount, argsCount);
    }

    /**
     * Выводит сообщение об ошибке из-за попытки выдачи уже выданной книги
     */
    public void printBookAlreadyCheckoutError() {
        System.out.println("Невозможно выполнить команду, так как книга уже выдана.");
    }

    /**
     * Выводит сообщение об ошибке из-за попытки вернуть книгу, которая и так находится в библиотеке
     */
    public void printBookAlreadyReturnedError() {
        System.out.println("Невозможно выполнить команду, так как книга уже находится в библиотеке.");
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
                \t- add-reader "<ФИО>" - Добавить читателя
                \t- edit-reader <ID читателя> "<ФИО читателя>" - Изменить читателя
                \t- delete-reader <ID читателя> - Удалить читателя
                \t- show-reader <ID читателя> - Просмотреть данные читателя и выданные ему книги
                \t- list-readers - Вывести список читателей
                \t- checkout-book <ID книги> <id читателя> - Выдать книгу читателю
                \t- return-book <ID книги> - Вернуть книгу
                \t- help – Справка
                """);
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
    public void printBookDeleted(Long id) {
        System.out.printf("Книга с ID %d успешно удалена.\n", id);
    }

    /**
     * Выводит сообщение, если книга с указанным ID не найдена
     */
    public void printBookNotFound(Long id) {
        System.out.printf("Книга с ID %d не найдена.\n", id);
    }

    /**
     * Выводит информацию о найденной книге
     */
    public void printBookInfo(Book book) {
        System.out.println("Книга с ID:");
        System.out.println(book.getBookInfo());
    }

    /**
     * Выводит информацию о добавленном читателе
     */
    public void printReaderAdded(Reader reader) {
        System.out.println("Читатель добавлен в систему:");
        System.out.println(reader.getReaderShortInfo());
    }

    /**
     * Выводит информацию о изменении читателя
     */
    public void printReaderEdited(Reader reader) {
        System.out.printf("Имя читателя %d изменено на %s.\n", reader.getId(), reader.getName());
    }

    /**
     * Выводит сообщение, если читатель с указанным ID не найден
     */
    public void printReaderNotFound(Long id) {
        System.out.printf("Читатель с ID %d не найден.\n", id);
    }

    /**
     * Выводит информацию о найденном читателе
     */
    public void printReaderInfo(Reader reader) {
        System.out.println(reader.getReaderInfo());
    }

    /**
     * Выводит сипсок всех читателей
     */
    public void printReaderList(List<Reader> readers) {
        if (readers.isEmpty()) {
            System.out.println("Список читателей пуст.");
            return;
        }

        System.out.println("Читатели:");
        for (int i = 0; i < readers.size(); i++) {
            Reader reader = readers.get(i);
            System.out.printf("%d) %s\n", i + 1, reader.getReaderShortInfo());
        }
    }

    /**
     * Выводит сообщение, что книга с указанным ID успешно выдана читателю
     */
    public void printBookCheckout(Book book, Reader reader) {
        System.out.printf("Книга \"%s\" успешно выдана читателю %s\n", book.getTitle(), reader.getReaderShortInfo());
    }

    /**
     * Выводит сообщение, что с указанным ID книга успешно возвращена
     */
    public void printBookReturned(Book book) {
        System.out.printf("Книга \"%s\" возвращена в библиотеку.\n", book.getTitle());
    }

    /**
     * Выводит сообщение об успешном удалении читателя
     */
    public void printReaderDeleted(Reader reader) {
        System.out.printf("Читатель %s успешно удален.\n", reader.getReaderShortInfo());
    }

    /**
     * Выводит сообщение, что читатель не может быть удален, так как у него есть выданные книги
     */
    public void printReaderDeleteWithNotEmptyBooks(Reader reader) {
        System.out.printf("Читатель %s не может быть удален, так как у него есть выданные книги.\n", reader.getReaderShortInfo());
    }
}