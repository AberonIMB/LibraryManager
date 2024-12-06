package org.example.commandValidators;

import org.example.Command;
import org.example.util.IOHandler;

/**
 * Конструктор, в котором присваивается обработчик ввода/вывода
 */
public class CommandsWithoutParamsValidator implements CommandValidator {
    private final IOHandler ioHandler;

    /**
     * Конструктор, в котором присваивается обработчик ввода/вывода
     */
    public CommandsWithoutParamsValidator(IOHandler ioHandler) {
        this.ioHandler = ioHandler;
    }

    @Override
    public boolean validateCommand(Command command) {
        if (!command.getParams().isEmpty()) {
            ioHandler.printFormatted("Неверное количество аргументов команды: должно быть %d, представлено %d.",
                    0, command.getParams().size());
            return false;
        }
        return true;
    }
}