package org.example.exceptions.stateExceptions;

import org.example.model.Book;

public class BookAlreadyInLibraryException extends StateValidationException {

    public BookAlreadyInLibraryException(Book book) {
        super("Невозможно выполнить операцию, так как книга \"%s\" уже находится в библиотеке"
                .formatted(book.getTitle()));
    }
}
