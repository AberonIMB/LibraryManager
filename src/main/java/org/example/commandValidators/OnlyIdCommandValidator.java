package org.example.commandValidators;

import org.example.Command;
import org.example.exceptions.commandExceptions.ArgumentsCountException;
import org.example.exceptions.commandExceptions.InvalidIdException;

/**
 * Проверяет корректность команды удаления книги
 */
public class OnlyIdCommandValidator extends CommandValidator {

    @Override
    public void validateCommand(Command command) throws ArgumentsCountException, InvalidIdException {
        int paramsCount = command.getParams().size();

        if (!validateArgsCount(1, paramsCount)) {
            throw new ArgumentsCountException(1, paramsCount);
        }

        if (!validateArgIsNumber(command.getParams().get(0))) {
            throw new InvalidIdException();
        }
    }
}