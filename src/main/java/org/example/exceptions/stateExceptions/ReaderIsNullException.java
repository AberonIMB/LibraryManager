package org.example.exceptions.stateExceptions;

/**
 * Ошибка, когда читатель не существует
 */
public class ReaderIsNullException extends StateValidationException {

    /**
     * Конструктор, в котором присваивается сообщение
     * "Читатель с ID %s не найден"
     */
    public ReaderIsNullException(Long readerId) {
        super("Читатель с ID %d не найден.".formatted(readerId));
    }
}