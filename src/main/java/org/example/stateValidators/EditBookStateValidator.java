package org.example.stateValidators;

import org.example.exceptions.stateExceptions.BookIsNullException;
import org.example.model.Book;

/**
 * Проверяет состояние книги перед редактированием
 * Книга не должна быть null
 */
public class EditBookStateValidator {

    /**
     * Валидировать состояние
     */
    public void validateState(Long bookId, Book book) throws BookIsNullException {
        if (book == null) {
            throw new BookIsNullException(bookId);
        }
    }
}