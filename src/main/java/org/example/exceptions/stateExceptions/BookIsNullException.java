package org.example.exceptions.stateExceptions;

/**
 * Ошибка, когда книга не существует
 */
public class BookIsNullException extends StateValidationException {

    /**
     * Конструктор, в котором присваивается сообщение
     * "Книга с ID %s не найдена"
     */
    public BookIsNullException(Long bookId) {
        super("Книга с ID %d не найдена.".formatted(bookId));
    }
}