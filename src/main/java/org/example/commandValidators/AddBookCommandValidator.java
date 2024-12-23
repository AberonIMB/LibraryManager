package org.example.commandValidators;

import org.example.Command;
import org.example.exceptions.commandExceptions.ArgumentsCountException;
import org.example.exceptions.commandExceptions.InvalidYearException;

/**
 * Проверяет корректность команды добавления книги
 */
public class AddBookCommandValidator extends CommandValidator {

    @Override
    public void validateCommand(Command command) throws ArgumentsCountException, InvalidYearException {
        int paramsCount = command.getParams().size();

        if (!validateArgsCount(3, paramsCount)) {
            throw new ArgumentsCountException(3, paramsCount);
        }

        if (!validateArgIsNumber(command.getParams().get(2))) {
            throw new InvalidYearException();
        }
    }
}