package org.example.stateValidators;

import org.example.exceptions.stateExceptions.BookAlreadyCheckedOutException;
import org.example.exceptions.stateExceptions.BookIsNullException;
import org.example.exceptions.stateExceptions.ReaderIsNullException;
import org.example.model.Book;
import org.example.model.Reader;

/**
 * Проверяет состояние книги и читателя перед операцией выдачи книги
 * Книга и читатель не должны быть null и книга не должна быть уже выдана
 */
public class CheckoutStateValidator {

    /**
     * Валидировать состояние
     */
    public void validateState(Long bookId, Long readerId, Book book, Reader reader) throws BookIsNullException, ReaderIsNullException, BookAlreadyCheckedOutException {
        if (book == null) {
            throw new BookIsNullException(bookId);
        }

        if (reader == null) {
            throw new ReaderIsNullException(readerId);
        }

        if (book.getReader() != null) {
            throw new BookAlreadyCheckedOutException(book);
        }
    }
}