package org.example.stateValidators;

import org.example.Command;
import org.example.exceptions.stateExceptions.BookAlreadyCheckedOutException;
import org.example.exceptions.stateExceptions.BookIsNullException;
import org.example.model.Book;

public class DeleteBookStateValidator {

    public void validateState(Command command, Book book) throws BookIsNullException, BookAlreadyCheckedOutException {
        if (book == null) {
            throw new BookIsNullException(command.getParams().get(0));
        }

        if (book.getReader() != null) {
            throw new BookAlreadyCheckedOutException(book);
        }
    }
}
