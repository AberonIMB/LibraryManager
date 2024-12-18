package org.example.stateValidators;

import org.example.Command;
import org.example.exceptions.stateExceptions.ReaderIsNullException;
import org.example.model.Reader;

public class ReaderNotNullStateValidator {

    public void validateState(Command command, Reader reader) throws ReaderIsNullException {
        if (reader == null) {
            throw new ReaderIsNullException(command.getParams().get(0));
        }
    }
}