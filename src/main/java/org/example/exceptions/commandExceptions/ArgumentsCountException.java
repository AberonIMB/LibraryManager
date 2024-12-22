package org.example.exceptions.commandExceptions;

/**
 * Ошибка, связанная с неправильным количеством аргументов
 */
public class ArgumentsCountException extends CommandValidationException {

    /**
     * Конструктор, в котором происходит задается сообщение об ошибке
     * @param expectedParamsCount ожидаемое количество аргументов
     * @param actualParamsCount предоставленное количество аргументов
     */
    public ArgumentsCountException(int expectedParamsCount, int actualParamsCount) {
        super("Неверное количество аргументов команды: должно быть %d, представлено %d."
                .formatted(expectedParamsCount, actualParamsCount));
    }
}