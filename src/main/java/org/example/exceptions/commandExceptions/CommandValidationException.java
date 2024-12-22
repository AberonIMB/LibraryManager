package org.example.exceptions.commandExceptions;

/**
 * Класс исключения при валидации команды
 */
public class CommandValidationException extends Exception {

    /**
     * Конструктор, в котором присваивается сообщение
     */
    public CommandValidationException(String message) {
        super(message);
    }
}
