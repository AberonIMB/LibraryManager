package org.example.stateValidators;

import org.example.Command;
import org.example.exceptions.stateExceptions.BookAlreadyCheckedOutException;
import org.example.exceptions.stateExceptions.BookIsNullException;
import org.example.exceptions.stateExceptions.ReaderIsNullException;
import org.example.model.Book;
import org.example.model.Reader;

public class CheckoutStateValidator {

    public void validateState(Command command, Book book, Reader reader) throws BookIsNullException, ReaderIsNullException, BookAlreadyCheckedOutException {
        if (book == null) {
            throw new BookIsNullException(command.getParams().get(0));
        }

        if (reader == null) {
            throw new ReaderIsNullException(command.getParams().get(1));
        }

        if (book.getReader() != null) {
            throw new BookAlreadyCheckedOutException(book);
        }
    }
}