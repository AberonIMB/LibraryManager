package org.example.stateValidators;

import org.example.Command;
import org.example.exceptions.stateExceptions.BookAlreadyInLibraryException;
import org.example.exceptions.stateExceptions.BookIsNullException;
import org.example.model.Book;

public class ReturnStateValidator {

    public void validateState(Command command, Book book) throws BookIsNullException, BookAlreadyInLibraryException {
        if (book == null) {
            throw new BookIsNullException(command.getParams().get(0));
        }

        if (book.getReader() == null) {
            throw new BookAlreadyInLibraryException(book);
        }
    }
}