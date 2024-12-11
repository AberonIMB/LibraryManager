package org.example.commandValidators;

import org.example.Command;
import org.example.util.IOHandler;

/**
 * Проверяет корректность команд без параметров
 */
public class CommandsWithoutParamsValidator extends DefaultCommandValidator implements CommandValidator {

    /**
     * Конструктор, в котором присваивается обработчик ввода/вывода
     */
    public CommandsWithoutParamsValidator(IOHandler ioHandler) {
        super(ioHandler);
    }

    @Override
    public boolean validateCommand(Command command) {
        return validateArgsCount(command.getParams().size(), 0);
    }
}