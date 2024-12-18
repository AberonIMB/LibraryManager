package org.example.exceptions.commandExceptions;

/**
 * Ошибка, связанная с некорректным типом значения года
 */
public class InvalidYearException extends CommandValidationException {

    /**
     * Конструктор, в котором задается сообщение об ошибке "Год должен быть представлен числом."
     */
    public InvalidYearException() {
        super("Год должен быть представлен числом.");
    }
}