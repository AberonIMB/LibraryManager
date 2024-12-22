package org.example.exceptions.stateExceptions;

/**
 * Класс исключения при валидации состояния
 */
public class StateValidationException extends Exception {

    /**
     * Конструктор, в котором присваивается сообщение
     */
    public StateValidationException(String message) {
        super(message);
    }
}