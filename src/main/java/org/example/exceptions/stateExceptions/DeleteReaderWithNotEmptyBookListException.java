package org.example.exceptions.stateExceptions;

import org.example.model.Reader;

/**
 * Ошибка, при попытке удалить читателя, который имеет выданные книги
 */
public class DeleteReaderWithNotEmptyBookListException extends StateValidationException {

    /**
     * Конструктор, в котором присваивается сообщение
     * "Невозможно удалить читателя %s, так как у него есть выданные книги."
     */
    public DeleteReaderWithNotEmptyBookListException(Reader reader) {
        super("Невозможно удалить читателя %s, так как у него есть выданные книги."
                .formatted(reader.getReaderShortInfo()));
    }
}
