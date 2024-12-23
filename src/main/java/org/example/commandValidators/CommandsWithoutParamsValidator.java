package org.example.commandValidators;

import org.example.Command;
import org.example.exceptions.commandExceptions.ArgumentsCountException;

/**
 * Проверяет корректность команд без параметров
 */
public class CommandsWithoutParamsValidator extends CommandValidator {

    @Override
    public void validateCommand(Command command) throws ArgumentsCountException {
        int paramsCount = command.getParams().size();

        if (!validateArgsCount(0, paramsCount)) {
            throw new ArgumentsCountException(0, paramsCount);
        }
    }
}