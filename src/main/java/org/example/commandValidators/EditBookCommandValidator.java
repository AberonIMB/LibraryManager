package org.example.commandValidators;

import org.example.Command;
import org.example.exceptions.commandExceptions.ArgumentsCountException;
import org.example.exceptions.commandExceptions.InvalidIdException;
import org.example.exceptions.commandExceptions.InvalidYearException;

/**
 * Проверяет корректность команды редактирования книги
 */
public class EditBookCommandValidator extends CommandValidator {

    @Override
    public void validateCommand(Command command) throws ArgumentsCountException, InvalidIdException, InvalidYearException {
        int paramsCount = command.getParams().size();

        if (!validateArgsCount(4, paramsCount)) {
            throw new ArgumentsCountException(4, paramsCount);
        }

        if (!validateArgIsNumber(command.getParams().get(0))) {
            throw new InvalidIdException();
        }

        if (!validateArgIsNumber(command.getParams().get(3))) {
            throw new InvalidYearException();
        }
    }
}