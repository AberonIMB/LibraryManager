package org.example.stateValidators;

import org.example.exceptions.stateExceptions.ReaderIsNullException;
import org.example.model.Reader;

/**
 * Проверяет состояние читателя
 * Читатель не должен быть null
 */
public class ReaderNotNullStateValidator {

    public void validateState(Long readerId, Reader reader) throws ReaderIsNullException {
        if (reader == null) {
            throw new ReaderIsNullException(readerId);
        }
    }
}