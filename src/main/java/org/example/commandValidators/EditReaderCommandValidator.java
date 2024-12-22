package org.example.commandValidators;

import org.example.Command;
import org.example.exceptions.commandExceptions.ArgumentsCountException;
import org.example.exceptions.commandExceptions.InvalidIdException;

/**
 * Проверяет корректность команды изменения читателя
 */
public class EditReaderCommandValidator extends CommandValidator {

    @Override
    public void validateCommand(Command command) throws ArgumentsCountException, InvalidIdException {
        int paramsCount = command.getParams().size();

        if (!validateArgsCount(2, paramsCount)) {
            throw new ArgumentsCountException(2, paramsCount);
        }

        if (!validateArgIsNumber(command.getParams().get(0))) {
            throw new InvalidIdException();
        }
    }
}
