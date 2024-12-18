package org.example.exceptions.stateExceptions;

public class BookIsNullException extends StateValidationException {

    public BookIsNullException(String bookId) {
        super("Книга с ID %s не найдена".formatted(bookId));
    }
}
