package org.example.exceptions.commandExceptions;

/**
 * Ошибка, связанная с некорректным ID
 */
public class InvalidIdException extends CommandValidationException {

    /**
     * Конструктор, в котором задается сообщение об ошибке "ID должен быть представлен числом."
     */
    public InvalidIdException() {
        super("ID должен быть представлен числом.");
    }
}