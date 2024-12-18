package org.example.stateValidators;

import org.example.Command;
import org.example.exceptions.stateExceptions.BookIsNullException;
import org.example.model.Book;

public class EditBookStateValidator {

    public void validateState(Command command, Book book) throws BookIsNullException {
        if (book == null) {
            throw new BookIsNullException(command.getParams().get(0));
        }
    }
}