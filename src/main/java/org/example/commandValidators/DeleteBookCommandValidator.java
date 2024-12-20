package org.example.commandValidators;

import org.example.Command;
import org.example.exceptions.ArgumentsCountException;
import org.example.exceptions.InvalidIdException;

/**
 * Проверяет корректность команды удаления книги
 */
public class DeleteBookCommandValidator extends CommandValidator {

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
