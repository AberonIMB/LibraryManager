package org.example.stateValidators;

import org.example.Command;
import org.example.exceptions.stateExceptions.DeleteReaderWithNotEmptyBookListException;
import org.example.exceptions.stateExceptions.ReaderIsNullException;
import org.example.model.Reader;

public class DeleteReaderStateValidator {

    public void validateState(Command command, Reader reader) throws ReaderIsNullException, DeleteReaderWithNotEmptyBookListException {
        if (reader == null) {
            throw new ReaderIsNullException(command.getParams().get(0));
        }

        if (!reader.getBooks().isEmpty()) {
            throw new DeleteReaderWithNotEmptyBookListException(reader);
        }
    }
}