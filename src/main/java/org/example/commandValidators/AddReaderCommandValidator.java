package org.example.commandValidators;

import org.example.Command;
import org.example.exceptions.commandExceptions.ArgumentsCountException;
import org.example.exceptions.commandExceptions.InvalidIdException;
import org.example.exceptions.commandExceptions.InvalidYearException;

public class AddReaderCommandValidator extends CommandValidator {

    @Override
    public void validateCommand(Command command) throws ArgumentsCountException, InvalidYearException, InvalidIdException {
        int paramsCount = command.getParams().size();

        if (!validateArgsCount(1, paramsCount)) {
            throw new ArgumentsCountException(1, paramsCount);
        }
    }
}
