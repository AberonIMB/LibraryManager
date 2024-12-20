package org.example.exceptions;

/**
 * Ошибка, связанная с некорректным типом значения года
 */
public class InvalidYearException extends Exception {

    /**
     * Конструктор, в котором задается сообщение об ошибке "Год должен быть представлен числом."
     */
    public InvalidYearException() {
        super("Год должен быть представлен числом.");
    }
}