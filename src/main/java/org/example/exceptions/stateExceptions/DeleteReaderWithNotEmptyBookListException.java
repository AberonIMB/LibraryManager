package org.example.exceptions.stateExceptions;

import org.example.model.Reader;

public class DeleteReaderWithNotEmptyBookListException extends StateValidationException {

    public DeleteReaderWithNotEmptyBookListException(Reader reader) {
        super("Невозможно удалить читателя %s, так как у него есть выданные книги."
                .formatted(reader.getReaderShortInfo()));
    }
}
