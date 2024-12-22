package org.example.stateValidators;

import org.example.exceptions.stateExceptions.DeleteReaderWithNotEmptyBookListException;
import org.example.exceptions.stateExceptions.ReaderIsNullException;
import org.example.model.Reader;

/**
 * Проверяет состояние читателя перед удалением
 * Читатель не должен быть null и не должен иметь выданных книг
 */
public class DeleteReaderStateValidator {

    public void validateState(Long id, Reader reader) throws ReaderIsNullException, DeleteReaderWithNotEmptyBookListException {
        if (reader == null) {
            throw new ReaderIsNullException(id);
        }

        if (!reader.getBooks().isEmpty()) {
            throw new DeleteReaderWithNotEmptyBookListException(reader);
        }
    }
}