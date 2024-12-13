package org.example.exceptions;

/**
 * Ошибка, связанная с некорректным ID
 */
public class InvalidIdException extends Exception {

    /**
     * Конструктор, в котором задается сообщение об ошибке "ID должен быть представлен числом."
     */
    public InvalidIdException() {
        super("ID должен быть представлен числом.");
    }
}