package org.example.commandValidators;

import org.example.util.IOHandler;

/**
 * Содержит общую логику проверки параметров команд для валидаторов
 */
public abstract class DefaultCommandValidator {

    private final IOHandler ioHandler;

    /**
     * Конструктор, в котором присваивается обработчик ввода/вывода
     */
    public DefaultCommandValidator(IOHandler ioHandler) {
        this.ioHandler = ioHandler;
    }

    /**
     * Проверяет, что количество аргументов команды соответствует ожидаемому
     * @return true, если количество аргументов команды соответствует ожидаемому
     */
    protected boolean validateArgsCount(int paramsCount, int expectedCount) {
        if (paramsCount != expectedCount) {
            ioHandler.printFormatted("Неверное количество аргументов команды: должно быть %d, представлено %d.",
                    expectedCount, paramsCount);
            return false;
        }

        return true;
    }

    /**
     * Проверяет, что аргумент команды является числом
     * @return true, если аргумент команды является числом
     */
    protected boolean validateArgIsNumber(String arg, String errorMessage) {
        if (!arg.matches("\\d+")) {
            ioHandler.print(errorMessage);
            return false;
        }
        return true;
    }
}