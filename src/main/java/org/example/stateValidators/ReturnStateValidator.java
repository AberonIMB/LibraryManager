package org.example.stateValidators;

import org.example.exceptions.stateExceptions.BookAlreadyInLibraryException;
import org.example.exceptions.stateExceptions.BookIsNullException;
import org.example.model.Book;

/**
 * Проверяет состояние книги перед возвратом
 * Книга не должна быть null и должна быть выдана читателю
 */
public class ReturnStateValidator {

    /**
     * Валидировать состояние
     */
    public void validateState(Long bookId, Book book) throws BookIsNullException, BookAlreadyInLibraryException {
        if (book == null) {
            throw new BookIsNullException(bookId);
        }

        if (book.getReader() == null) {
            throw new BookAlreadyInLibraryException(book);
        }
    }
}