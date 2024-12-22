package org.example.stateValidators;

import org.example.exceptions.stateExceptions.BookAlreadyCheckedOutException;
import org.example.exceptions.stateExceptions.BookIsNullException;
import org.example.model.Book;

/**
 * Проверяет состояние книги перед удалением
 * Книга не должна быть null и не должна быть выдана читателю
 */
public class DeleteBookStateValidator {

    /**
     * Валидировать состояние
     */
    public void validateState(Long bookId, Book book) throws BookIsNullException, BookAlreadyCheckedOutException {
        if (book == null) {
            throw new BookIsNullException(bookId);
        }

        if (book.getReader() != null) {
            throw new BookAlreadyCheckedOutException(book);
        }
    }
}
