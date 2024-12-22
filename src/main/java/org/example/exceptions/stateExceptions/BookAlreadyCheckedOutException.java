package org.example.exceptions.stateExceptions;

import org.example.model.Book;

/**
 * Ошибка, при попытке выдать или удалить книгу, которая уже выдана читателю
 */
public class BookAlreadyCheckedOutException extends StateValidationException {

    /**
     * Конструктор, в котором присваивается сообщение
     * "Невозможно выполнить операцию, так как книга \"%s\" выдана читателю %s"
     */
    public BookAlreadyCheckedOutException(Book book) {
        super("Невозможно выполнить операцию, так как книга \"%s\" выдана читателю %s."
                .formatted(book.getTitle(), book.getReader().getReaderShortInfo()));
    }
}
