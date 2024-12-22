package org.example.exceptions.stateExceptions;

import org.example.model.Book;

/**
 * Ошибка, при попытке вернуть книгу, которая уже находится в библиотеке
 */
public class BookAlreadyInLibraryException extends StateValidationException {

    /**
     * Конструктор, в котором присваивается сообщение
     * "Невозможно выполнить операцию, так как книга \"%s\" уже находится в библиотеке"
     */
    public BookAlreadyInLibraryException(Book book) {
        super("Невозможно выполнить операцию, так как книга \"%s\" уже находится в библиотеке."
                .formatted(book.getTitle()));
    }
}
