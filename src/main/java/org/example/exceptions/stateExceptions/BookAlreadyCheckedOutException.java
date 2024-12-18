package org.example.exceptions.stateExceptions;

import org.example.model.Book;

public class BookAlreadyCheckedOutException extends StateValidationException {

    public BookAlreadyCheckedOutException(Book book) {
        super("Выполнение операции невозможно, так как книга \"%s\" выдана читателю %s"
                .formatted(book.getTitle(), book.getReader().getReaderShortInfo()));
    }
}
